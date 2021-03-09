package com.hust.searchengine.Utils;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileUtil {
    //文件上传工具类服务方法

    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception{

        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(targetFile.getAbsolutePath()+"/"+fileName);
        out.write(file);
        out.flush();
        out.close();
    }

    public static String getUpLoadFilePath()
    {
        File path = null;
        try {
            path = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(!path.exists()) path = new File("");
        File filePath = new File(path.getAbsolutePath(),"static/files/upload/");
        return filePath.getAbsolutePath();
    }

    //得到图片在服务器上的保存路径
    public static String getUpLoadPicPath()
    {
        File path = null;
        try {
            path = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(!path.exists()) path = new File("");
        File filePath = new File(path.getAbsolutePath(),"static/pics/upload/");
        return filePath.getAbsolutePath();
    }

    //判断是否为图片格式
    public static boolean isPicture(String fileName){
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

        String[] suffixArray = new String[]{"bmp","jpg","jpeg","png","tif","gif","pcx","tga","exif","fpx","svg","psd","cdr","pcd","dxf","ufo","eps","ai","raw","WMF","webp"};
        List<String> picSuffixList = new ArrayList<>();
        Collections.addAll(picSuffixList, suffixArray);
        return picSuffixList.contains(suffix);
    }
}
