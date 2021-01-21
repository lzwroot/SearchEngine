/*
Navicat MySQL Data Transfer

Source Server         : t1
Source Server Version : 50559
Source Host           : localhost:3306
Source Database       : search

Target Server Type    : MYSQL
Target Server Version : 50559
File Encoding         : 65001

Date: 2021-01-21 17:37:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `doi` varchar(255) NOT NULL,
  `articlename` varchar(255) NOT NULL,
  `author` text NOT NULL,
  `article_abstract` text NOT NULL,
  `journal` varchar(255) NOT NULL,
  `link` varchar(255) NOT NULL,
  `time` date NOT NULL,
  `field` varchar(255) NOT NULL,
  PRIMARY KEY (`doi`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES ('1', '京津冀绿色金融发展综合评价及影响因素研究——基于DEA-Tobit模型的实证分析', '王文静; 何泰屹; 武慧敏; 史娅婷', '本文以京津冀地区作为研究对象,利用熵值法以及DEA-Malmquist对三地2007年-2018年的绿色金融发展水平进行综合评价,在此基础上使用Tobit模型对相关影响因素进行研究。实证结果表明:京津冀三地绿色金融发展水平同异并存,发展态势积极向上但发展水平参差不齐;京津冀三地绿色金融发展效率特征显著,效率随时间发展波动式前进;京津冀三地绿色金融发展效率影响因素结果鲜明,影响因素随发展格局的变化动态调整。基于以上结论本文提出充分发挥京津冀都市经济区协同发展的联动效应、合理借鉴绿色金融改革创新试验区的先进经验、做好顶层设计、科学合理量化绿色金融体系的建议。', '华北金融', 'https://kns.cnki.net/kcms/download.aspx?filename=xEFcxF0UVZ2SKp0LudFWt5GW4lFU48SWvZmVFBjWxUUeptkem5EeBFWcvZ0csdma090byJkQ2NFc0FWSSNUZrd1bYxUdtdWNvUkTxBjcLNUM6ZHRnxmcQVGVthHOIVFNFNUWpJ0N15UTppUVuR0Ukh3QwV2Kx42U&tablename=CAPJLAST&dflag=pdfdown', '2021-01-06', '经济与管理');
INSERT INTO `article` VALUES ('2', '加快推进产业体系绿色现代化：模式与路径', '张友国', '加快推进产业体系绿色现代化既符合党的十九届五中全会提出的加快发展现代产业体系的要求，又能促进经济社会全面绿色转型。结合理论分析与实践考察，本文提出了三种产业体系绿色现代化模式，即绿色经济主导型、低碳经济主导型、循环经济主导型模式。不过，当前产业体系绿色现代化还面临一系列问题与挑战，包括绿色转型原动力不足、产业结构优化升级乏力、政策体系不完善、体制机制不健全、生态环保欠账严重等。最后，本文认为加快产业结构绿色转型、不断增强绿色技术创新能力、大力发展绿色金融、积极创建绿色低碳循环生活体系、大力推进生态环境治理、持续完善体制机制引导社会力量参与，是加快推进产业体系绿色现代化的重要途径。', '企业经济', 'https://kns.cnki.net/kcms/detail/detail.aspx?dbcode=CAPJ&dbname=CAPJLAST&filename=QUIT20210112002&v=IxuD1Tjf9N7cwQaKDUpUgmAIEb1glWEpu7vsEFn5EUTUF%25mmd2BjdCXw0hDaYsu5E7T21', '2021-01-13', '经济与管理');
INSERT INTO `article` VALUES ('3', 'SARS-CoV-2 genomic and subgenomic RNAs in diagnostic samples are not an indicator of active replication', 'Soren Alexandersen, Anthony Chamings & Tarka Raj Bhatta', 'Severe acute respiratory syndrome coronavirus-2 (SARS-CoV-2) was first detected in late December 2019 and has spread worldwide. Coronaviruses are enveloped, positive sense, single-stranded RNA viruses and employ a complicated pattern of virus genome length RNA replication as well as transcription of genome length and leader containing subgenomic RNAs. Although not fully understood, both replication and transcription are thought to take place in so-called double-membrane vesicles in the cytoplasm of infected cells. Here we show detection of SARS-CoV-2 subgenomic RNAs in diagnostic samples up to 17 days after initial detection of infection and provide evidence for their nuclease resistance and protection by cellular membranes suggesting that detection of subgenomic RNAs in such samples may not be a suitable indicator of active coronavirus replication/infection.', 'Nature', 'https://www.nature.com/articles/s41467-020-19883-7', '2020-11-27', 'Coronavirus');
INSERT INTO `article` VALUES ('4', 'Cellular events of acute, resolving or progressive COVID-19 in SARS-CoV-2 infected non-human primates', 'M. D. Fahlberg, R. V. Blair, L. A. Doyle-Meyers', 'Understanding SARS-CoV-2 associated immune pathology is crucial to develop pan-effective vaccines and treatments. Here we investigate the immune events from the acute state up to four weeks post SARS-CoV-2 infection, in non-human primates (NHP) with heterogeneous pulmonary pathology. We show a robust migration of CD16 expressing monocytes to the lungs occurring during the acute phase, and we describe two subsets of interstitial macrophages (HLA-DR+CD206−): a transitional CD11c+CD16+ cell population directly associated with IL-6 levels in plasma, and a long-lasting CD11b+CD16+ cell population. Trafficking of monocytes is mediated by TARC (CCL17) and associates with viral load measured in bronchial brushes. We also describe associations between disease outcomes and high levels of cell infiltration in lungs including CD11b+CD16hi macrophages and CD11b+ neutrophils. Accumulation of macrophages is long-lasting and detectable even in animals with mild or no signs of disease. Interestingly, animals with anti-inflammatory responses including high IL-10:IL-6 and kynurenine to tryptophan ratios show less severe illness. Our results unravel cellular mechanisms of COVID-19 and suggest that NHP may be appropriate models to test immune therapies.', 'Nature', 'https://www.nature.com/articles/s41467-020-19967-4', '2020-11-27', 'Coronavirus');
INSERT INTO `article` VALUES ('5', 'Response2covid19, a dataset of governments’ responses to COVID-19 all around the world', 'Simon Porcher', 'Following the COVID-19 outbreak, governments all around the world have implemented public health and economic measures to contain the spread of the virus and to support the economy. Public health measures include domestic lockdown, school closures and bans on mass gatherings among others. Economic measures cover wage support, cash transfers, interest rates cuts, tax cuts and delays, and support to exporters or importers. This paper introduces ‘Response2covid19’, a living dataset of governments’ responses to COVID-19. The dataset codes the various policy interventions with their dates at the country-level for more than 200 countries from January 1 to October 1, 2020 and is updated every month. The production of detailed data on the measures taken by governments can help generate robust evidence to support public health and economic decision making.', 'Nature', 'https://www.nature.com/articles/s41597-020-00757-y', '2020-11-25', 'Coronavirus');
