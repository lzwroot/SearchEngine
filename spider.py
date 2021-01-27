#该脚本实现对arix.org网站论文数据的爬取，并提取相关信息写入数据库，在控制台输入python spider.py即可运行
#该脚本需要安装依赖:requests,pymysql(可通过pip安装)
#该脚本只操作article表，article表结构为：(doi(varchar(32)),abstract(varchar(3000)),articlename(varchar(500)),author(varchar(10000)),journal(varchar(32)),link(varchar(256)),time(DATATIME),field(varchar(100)))

import requests
import time
import random
import pymysql
from bs4 import BeautifulSoup
from threading import Thread  

Basepath = 'https://arxiv.org'
field_pool = ['astro-ph','cond-mat','gr-qc','hep-ex','hep-lat','hep-ph','hep-th','math-ph','nlin','nucl-ex','nucl-th','physics','quant-ph','math','cs','q-bio','q-fin','stat','eess','econ']
url_pool = []
url_pool_sec = []
all_article = []
proxy_pool = []
HEADERS = {'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9',
   'Accept-Language': 'zh-CN,zh;q=0.8',
   'Accept-Encoding': 'gzip, deflate',}
headers = {'User-Agent':'Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36'}

class MyThread(Thread):  
    def __init__(self, func, args=()):  
        super(MyThread, self).__init__()  
        self.func = func  
        self.args = args  
  
    def run(self):  
        self.res,self.url = self.func(*self.args) 

  
    def get_result(self):  
        try:  
            return self.res  
        except Exception as e:  
            return None  
    def get_url(self):
        try:  
            return self.url  
        except Exception as e:  
            return None 

def get_proxy_pool():
    for i in requests.get(" http://118.24.52.95/get_all/").json():
        proxy_pool.append(i.get('proxy'))

def download(url,proxy):   
        
                                                                       
        try:                           
                                                               
            r = requests.get(url,proxies={"http": "http://{}".format(proxy)},allow_redirects=True,timeout=30.0, headers=headers)

            
        except Exception as e:   
            
            print(e)   
            return None,url
                                                                                   
        else: 
            if r.status_code == 200:
                print(r.status_code,url)
                return r,None
            print(r.status_code,url)
            return None,url
            
                                                                                                                                                                                             
def get_url_pool():
    url_pool_page = []
    num = 0
    for field in field_pool:
        url_pool_page.append("https://arxiv.org/list/"+field+"/2001?skip=0&show=2000")
        url_pool_page.append("https://arxiv.org/list/"+field+"/1912?skip=0&show=2000")
    url_pool_sec = url_pool_page[:]
    while(len(url_pool_sec) != 0 and num < 1):
        num += 1
        urls = url_pool_sec[:]
        url_pool_sec = []
        res,url_pool_sec = thread(urls)
        for r in res:
            cont = r.content.decode("utf-8")
            soup = BeautifulSoup(cont,features="html5lib") 
            dlpage = soup.find('div',id='dlpage')
            small = dlpage.find_all('small')[0]
            total_string = get_generator_content(small.stripped_strings,0)
            total = total_string[11:-9]
            dl_list = dlpage.find_all('dl')
            for dl in dl_list:
                a_list = dl.find_all('a',title='Abstract')
                for a in a_list:
                    url_pool.append(Basepath+a.get('href'))
            if int(total)>2000:
                    res2,url2 = download(r.url[:-11]+"2000&show=2000",'127.0.0.1')
                    if res2 == None:
                        url_pool_sec.append(url2)
                        continue
                    cont = res2.content.decode("utf-8")
                    soup = BeautifulSoup(cont, features="html5lib")
                    dlpage = soup.find('div', id='dlpage')
                    dl_list = dlpage.find_all('dl')
                    for dl in dl_list:
                        a_list = dl.find_all('a', title='Abstract')
                        for a in a_list:
                            url_pool.append(Basepath+a.get('href'))
            
def get_articleinfo(article):
    #获取文章信息
    author = ""
    cont = article.content.decode('utf-8')
    soup = BeautifulSoup(cont, features='html5lib')
    title_mathjax = soup.find('h1', class_='title mathjax')
    title_generator = title_mathjax.stripped_strings
    title = get_generator_content(title_generator,1).replace('$','')
    authors = soup.find('div',class_='authors')
    a = authors.find_all('a')
    for i in a:
        author+=(i.string+',')
    abstract_mathjax = soup.find('blockquote', class_='abstract mathjax')
    abstract_generator = abstract_mathjax.stripped_strings
    abstract = get_generator_content(abstract_generator,1).replace('$','')
    arixid = soup.find('span',class_='arxivid')
    doi = arixid.find('a').string
    field = soup.find('span',class_='primary-subject').string
    journal = '<Preprint>'
    link = 'https://arxiv.org/pdf/'+doi[6:]
    submission = soup.find('div',class_='submission-history').stripped_strings
    pretime = get_generator_content(submission,5)
    time = pretime[11:16].replace(' ','')+'.'+doi[8:10]+'.'+pretime[5:7]
    print(doi)
  
    #写入数据库
    conn = pymysql.connect(
        host='localhost',
        user='root',
        password='19990905..gjx',
        db='search',
        charset='utf8',

    )

    cur = conn.cursor()

    try:
        
        cur.execute("insert into article(doi,article_abstract,articlename,author,journal,link,time,field) values(%s,%s,%s,%s,%s,%s,%s,%s)",(doi,abstract,title,author,journal,link,time,field))
    except Exception as e:
        print(e)
    else:
        
        conn.commit()
        print('sql success')
        

    cur.close()

    conn.close()

def get_generator_content(generator,index):
    i = 0
    for g in generator:
        if i == index :
            return g
        i+=1 

def thread(urls):                                                                                              
    print(u'mulitile thread downloading...')                                                                                              
    headers = HEADERS                                                                                      
    headers['user-agent'] = "Mozilla/5.0+(Windows+NT+6.2;+WOW64)+AppleWebKit/537.36(KHTML,+like+Gecko)+Chrome/45.0.2454.101+Safari/537.36"                       
    ts = []
    res = []
    url_sec = []
    for url in urls:
        ts.append(MyThread(download, args=(url,proxy_pool[random.randint(0,len(proxy_pool)-1)],)))
         
    starttime= time.time()                                                                                 
    for t in ts:                                                                                           
        (t.start())                                                                                          
    for t in ts:                                                                                           
        (t.join())         
        if t.get_result() != None:
            res.append(t.get_result())  
        if t.get_url() != None:
            url_sec.append(t.get_url())                                                                                  
    endtime=time.time()  
    return res,url_sec                                                                                 
                                                                                                                                                                        
#主函数入口                                                                                                                                           
if __name__ == '__main__':
    get_proxy_pool()
    get_url_pool()    
    for i in range(int(len(url_pool)/50)+1): 
        num = 0
        if i == int(len(url_pool)/50):
            url_pool_sec = url_pool[i*50:][:]   
            while(len(url_pool_sec) != 0 and num < 5):
                num += 1
                urls = url_pool_sec[:]
                url_pool_sec = []
                res, url_pool_sec = thread(urls)
                for article in res:
                    get_articleinfo(article)
            break
        url_pool_sec = url_pool[i*50:(i+1)*50][:]   
        while(len(url_pool_sec)!=0 and num < 5):    
            num += 1 
            urls = url_pool_sec[:]
            url_pool_sec = []   
            res,url_pool_sec = thread(urls)
            for article in res:
                get_articleinfo(article) 