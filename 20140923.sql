-- MySQL dump 10.13  Distrib 5.6.10, for Win32 (x86)
--
-- Host: localhost    Database: KXW
-- ------------------------------------------------------
-- Server version	5.6.10

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `t_kx_albums`
--

DROP TABLE IF EXISTS `t_kx_albums`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_kx_albums` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(50) COLLATE utf8_bin NOT NULL,
  `album_name` varchar(30) COLLATE utf8_bin NOT NULL,
  `description` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_2` (`email`,`album_name`),
  KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_kx_albums`
--

LOCK TABLES `t_kx_albums` WRITE;
/*!40000 ALTER TABLE `t_kx_albums` DISABLE KEYS */;
INSERT INTO `t_kx_albums` VALUES (31,'newuser@126.com','同学','从高中到研究生的同学照片'),(32,'newuser@126.com','新相册','3343434'),(60,'newuser@126.com','test',''),(61,'newuser@126.com','xxx',''),(62,'lntoto@126.com','abcd',''),(63,'x@aa.com','相册1',''),(64,'androidguy@189.com','相册1','新浪微博Android客户端截图'),(65,'androidguy@189.com','风景',''),(70,'androidguy@189.com','album1',''),(71,'androidguy@189.com','album2',''),(72,'androidguy@189.com','shjxj','jxjjc'),(73,'bill@189.com','手机相册','手机相册'),(74,'bill@189.com','åæ¥å¥½','åæ¥å¥½'),(75,'bill@189.com','精神集中','精神集中'),(76,'bill@189.com','人体艺术','人体艺术'),(77,'bill@189.com','余额','余额'),(78,'bill@189.com','测试新相册','测试新相册'),(79,'dtu','手机相册','手机相册'),(80,'GGYY','手机相册','手机相册'),(81,'ggyy1','手机相册','手机相册'),(82,'ggyy','手机相册','手机相册'),(83,'98A24A0F9E11BABE863BD2264DFE6603','手机相册','手机相册');
/*!40000 ALTER TABLE `t_kx_albums` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_kx_cities`
--

DROP TABLE IF EXISTS `t_kx_cities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_kx_cities` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `province_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `province_id` (`province_id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_kx_cities`
--

LOCK TABLES `t_kx_cities` WRITE;
/*!40000 ALTER TABLE `t_kx_cities` DISABLE KEYS */;
INSERT INTO `t_kx_cities` VALUES (1,'哈尔滨',5),(2,'齐齐哈尔',5),(3,'鸡西',5),(4,'鹤岗',5),(5,'双鸭山',5),(6,'大庆',5),(7,'伊春',5),(8,'佳木斯',5),(9,'七台河',5),(10,'牡丹江',5),(11,'黑河',5),(12,'绥化',5),(13,'大兴安岭地区',5),(28,'长春',6),(29,'吉林',6),(30,'四平',6),(31,'辽源',6),(32,'通化',6),(33,'白山',6),(34,'松原',6),(35,'白城',6),(44,'延边朝鲜族自治州',6),(45,'北京',0),(46,'上海',0),(47,'天津',0),(48,'重庆',0);
/*!40000 ALTER TABLE `t_kx_cities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_kx_comments`
--

DROP TABLE IF EXISTS `t_kx_comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_kx_comments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `photo_id` int(11) DEFAULT NULL,
  `album_id` int(11) DEFAULT NULL,
  `content` varchar(1000) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `diary_id` int(11) DEFAULT NULL,
  `nickname` varchar(45) DEFAULT NULL,
  `is_reply` int(11) DEFAULT '0',
  `has_reply` int(11) DEFAULT '0',
  `reply_user` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_kx_comments`
--

LOCK TABLES `t_kx_comments` WRITE;
/*!40000 ALTER TABLE `t_kx_comments` DISABLE KEYS */;
INSERT INTO `t_kx_comments` VALUES (1,178,76,'怪怪的','2014-06-27 03:42:30','bill@189.com',0,NULL,0,0,NULL),(2,178,76,'v他应该','2014-06-27 15:43:33','bill@189.com',0,NULL,0,0,NULL),(3,178,76,'给他贵妇人','2014-06-27 16:10:51','bill@189.com',0,'',0,0,NULL),(4,178,76,'刚刚入土','2014-06-27 16:15:13','bill@189.com',0,'bill gates',0,0,NULL),(5,178,76,'DJ快下课','2014-06-27 16:42:46','bill@189.com',0,'bill gates',0,0,NULL),(6,178,76,'耳机地[face_9]','2014-06-27 16:43:16','bill@189.com',0,'bill gates',0,0,NULL),(7,260,78,'ID看看','2014-06-30 17:01:46','bill@189.com',0,'bill gates',0,0,NULL),(8,260,78,'肥肠粉','2014-06-30 17:33:25','bill@189.com',0,'bill gates',0,0,NULL),(9,260,78,'付强徐军','2014-06-30 17:35:34','bill@189.com',0,'bill gates',0,0,NULL),(10,260,78,'idiikkx','2014-06-30 17:40:51','bill@189.com',0,'bill gates',0,0,NULL),(11,260,78,'付强看看先看','2014-06-30 17:43:56','bill@189.com',0,'bill gates',0,0,NULL),(12,260,78,'CC吃','2014-06-30 17:45:48','bill@189.com',0,'bill gates',0,0,NULL),(13,260,78,'吹吹风','2014-06-30 17:47:29','bill@189.com',0,'bill gates',0,0,NULL),(14,260,78,'看的开徐军','2014-06-30 17:49:11','bill@189.com',0,'bill gates',0,0,NULL),(15,260,78,'就大舅舅','2014-06-30 17:51:25','bill@189.com',0,'bill gates',0,0,NULL),(16,260,78,'房产','2014-06-30 17:53:08','bill@189.com',0,'bill gates',0,0,NULL),(17,260,78,'还好吧','2014-06-30 17:56:07','bill@189.com',0,'bill gates',0,0,NULL),(18,260,78,'发广告@bill gates还好吧','2014-07-01 09:53:55','bill@189.com',0,'bill gates',1,0,'bill gates'),(19,0,0,'的看看先看','2014-07-01 10:20:07','bill@189.com',21,'bill gates',0,0,''),(20,0,0,'地看到没<font color=red>@bill gates</font>的看看先看','2014-07-01 11:01:21','bill@189.com',21,'bill gates',1,0,'bill gates'),(21,0,0,'看的开专科<font color=red>@bill gates</font>地看到没<font color=red>@bill gates</font>的看看先看','2014-07-01 11:03:17','bill@189.com',21,'bill gates',1,0,'bill gates'),(22,0,0,'在就是卡','2014-07-01 11:05:52','bill@189.com',21,'bill gates',0,0,''),(23,0,0,'肥肠粉<font color=red>@bill gates</font>在就是卡','2014-07-01 11:06:00','bill@189.com',21,'bill gates',1,0,'bill gates'),(24,260,78,'蛋炒饭<font color=#ff397cbf>@bill gates</font>发广告@bill gates还好吧','2014-07-01 11:07:25','bill@189.com',0,'bill gates',1,0,'bill gates'),(25,0,0,'飞车党是<font color=\'#ff397cbf\'>@bill gates</font>肥肠粉<font color=red>@bill gates</font>在就是卡','2014-07-01 11:10:01','bill@189.com',21,'bill gates',1,0,'bill gates'),(26,0,0,'飞车党@bill gates飞车党是<font color=\'#ff397cbf\'>@bill gates</font>肥肠粉<font color=red>@bill gates</font>在就是卡','2014-07-01 11:19:59','bill@189.com',21,'bill gates',1,0,'bill gates'),(27,266,80,'调出','2014-07-09 10:16:47','GGYY',0,'ggyy',0,0,''),(28,264,80,'尝尝','2014-07-09 10:17:22','GGYY',0,'ggyy',0,0,''),(29,264,80,'兴冲冲','2014-07-09 10:27:46','GGYY',0,'ggyy',0,0,''),(30,266,80,'下次聊','2014-07-09 10:40:24','GGYY',0,'ggyy',0,0,''),(31,264,80,'吹吹风','2014-07-09 10:40:43','GGYY',0,'ggyy',0,0,''),(32,266,80,'等等','2014-07-09 10:43:33','GGYY',0,'ggyy',0,0,''),(33,44,80,'奖学金小科','2014-07-17 11:59:44','ggyy',0,'ggyy',0,0,''),(34,275,82,'东街口','2014-07-17 15:04:17','ggyy',0,'ggyy',0,0,''),(35,266,80,'那些妈妈','2014-07-18 11:05:24','ggyy',0,'ggyy',0,0,''),(36,266,80,'街道口','2014-07-18 11:24:44','ggyy',0,'ggyy',0,0,''),(37,268,80,'想们看看','2014-07-18 11:30:35','ggyy',0,'ggyy',0,0,''),(38,275,82,'操','2014-07-28 10:26:04','ggyy',0,'ggyy',0,0,''),(39,276,83,'毛燥','2014-07-29 13:51:48','98A24A0F9E11BABE863BD2264DFE6603',0,'æ±åå°ç¬ç ',0,0,''),(40,0,0,'手机就直接','2014-08-22 16:37:19','98A24A0F9E11BABE863BD2264DFE6603',26,'æ±åå°ç¬ç ',0,0,''),(41,286,83,'牛逼啊！','2014-08-25 15:21:22','98A24A0F9E11BABE863BD2264DFE6603',0,'江南小笑生 ',0,0,'');
/*!40000 ALTER TABLE `t_kx_comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_kx_desktop_icons`
--

DROP TABLE IF EXISTS `t_kx_desktop_icons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_kx_desktop_icons` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(30) NOT NULL,
  `settings` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_kx_desktop_icons`
--

LOCK TABLES `t_kx_desktop_icons` WRITE;
/*!40000 ALTER TABLE `t_kx_desktop_icons` DISABLE KEYS */;
INSERT INTO `t_kx_desktop_icons` VALUES (1,'3@126.com','0:10:10'),(2,'hello@126.com','0:10:10'),(3,'newuser@126.com','0:10:10,1:88:10,2:166:10,3:244:10,4:322:10,5:400:10,6:478:10,7:556:10,8:634:10,'),(4,'1@126.com','0:10:10,1:88:10,'),(5,'2@126.com','0:10:10,1:88:10,2:166:10,3:244:10,4:322:10,5:400:10,6:478:10,7:556:10,8:634:10,'),(6,'lntoto@126.com','0:10:10,1:88:10,2:166:10,3:244:10,4:322:10,5:400:10,6:478:10,7:556:10,'),(7,'x@aa.com','0:10:10,1:88:10,2:166:10,3:244:10,4:322:10,5:400:10,6:478:10,7:556:10,8:634:10,'),(8,'androidguy@189.com','0:10:10,1:88:10,2:166:10,3:244:10,4:322:10,5:400:10,6:478:10,7:556:10,8:634:10,'),(9,'bill@189.com','0:10:10,1:88:10,2:166:10,3:244:10,4:322:10,5:400:10,6:478:10,7:556:10,8:634:10,'),(10,'mike@126.com','0:10:10'),(11,'bill@126.com','0:10:10'),(12,'229637012@qq.com','0:10:10');
/*!40000 ALTER TABLE `t_kx_desktop_icons` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_kx_diary`
--

DROP TABLE IF EXISTS `t_kx_diary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_kx_diary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(50) COLLATE utf8_bin NOT NULL,
  `title` varchar(100) COLLATE utf8_bin NOT NULL,
  `modify_date` varchar(50) COLLATE utf8_bin NOT NULL,
  `filename` varchar(20) COLLATE utf8_bin NOT NULL,
  `username` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `content` varchar(1000) COLLATE utf8_bin DEFAULT NULL,
  `comment_count` int(11) DEFAULT '0',
  `like_count` int(11) DEFAULT '0',
  `competence` int(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_2` (`email`,`filename`),
  KEY `email` (`email`),
  KEY `modify_date` (`modify_date`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_kx_diary`
--

LOCK TABLES `t_kx_diary` WRITE;
/*!40000 ALTER TABLE `t_kx_diary` DISABLE KEYS */;
INSERT INTO `t_kx_diary` VALUES (3,'newuser@126.com','abcdd','2009-10-15 10:29:54','ocsfdohqq4',NULL,NULL,0,0,NULL),(4,'newuser@126.com','abcd','2009-10-15 12:25:25','1cfcif23dz',NULL,NULL,0,0,NULL),(7,'2@126.com','中国','2009-10-16 13:21:37','u597n1hx6j',NULL,NULL,0,0,NULL),(8,'androidguy@189.com','今天买了几本书','2010-04-25 10:09:31','ws2byjwruj',NULL,NULL,0,0,NULL),(9,'androidguy@189.com','打算去上海看世博会','2010-04-25 10:11:00','ujflb0m0qx',NULL,NULL,0,0,NULL),(10,'androidguy@189.com','天真热','2010-04-25 10:11:15','pbcs5wo5hs',NULL,NULL,0,0,NULL),(11,'androidguy@189.com','五一了，去哪里休闲一下呢？','2010-04-25 10:11:43','ie0uukj5y1',NULL,NULL,0,0,NULL),(12,'androidguy@189.com','gbnn','2014-06-16 00:00:00','wljknytr4m',NULL,NULL,0,0,NULL),(13,'androidguy@189.com','Wed Jun 18 16:18:51 格林尼治标准时间+0800 2014','2014-06-18 00:00:00','lbcpuszdlv',NULL,NULL,0,0,NULL),(14,'androidguy@189.com','度UC奖学金蒋姐','2014-06-19 00:00:00','s1ne87euri',NULL,NULL,0,0,NULL),(15,'androidguy@189.com','福匡华','2014-06-23 00:00:00','1tsegckdvt',NULL,NULL,0,0,NULL),(16,'androidguy@189.com','苏小鸡鸡','2014-06-23 00:00:00','thm9waol4m',NULL,'洗脚水几',0,0,NULL),(17,'androidguy@189.com','一定会的基督教','2014-06-23 00:00:00','1ncrp2xeqc',NULL,'大好河山蒋姐',0,0,NULL),(18,'androidguy@189.com','的看看先','2014-06-24 00:00:00','g34ni4qtaj',NULL,'大舅舅地',0,0,NULL),(19,'bill@189.com','基督教大舅舅','2014-06-24 00:00:00','5u3ffn0e06',NULL,'相互杜鹃',0,0,NULL),(20,'bill@189.com','u杜鹃[face_21]','2014-06-27 00:00:00','tkw747z1bf',NULL,'u杜鹃[face_21]',0,0,NULL),(21,'bill@189.com','蒋姐大舅舅','2014-06-27 10:42:16','6qtxx0x1fg',NULL,'蒋姐大舅舅',7,3,NULL),(22,'GGYY','你自己想就','2014-07-11 14:35:26','xtxd5sj3hk',NULL,'你自己想就',0,2,0),(23,'98A24A0F9E11BABE863BD2264DFE6603','几十级就','2014-08-11 14:31:31','orso6d5qse',NULL,'几十级就',0,0,0),(24,'98A24A0F9E11BABE863BD2264DFE6603','记得记得','2014-08-14 15:32:45','a1dgkrp8dj',NULL,'记得记得',0,0,0),(25,'98A24A0F9E11BABE863BD2264DFE6603','出现的','2014-08-14 16:34:46','13cf3uadvt',NULL,'出现的',0,0,0),(26,'98A24A0F9E11BABE863BD2264DFE6603','测试','2014-08-14 16:57:30','8vnm2if7np',NULL,'测试',1,0,0);
/*!40000 ALTER TABLE `t_kx_diary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_kx_dir`
--

DROP TABLE IF EXISTS `t_kx_dir`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_kx_dir` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dir_name` varchar(200) COLLATE utf8_bin NOT NULL,
  `parent_dir_name` varchar(200) COLLATE utf8_bin NOT NULL,
  `create_date` date NOT NULL,
  `email` varchar(200) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `dir_name` (`dir_name`,`parent_dir_name`,`email`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_kx_dir`
--

LOCK TABLES `t_kx_dir` WRITE;
/*!40000 ALTER TABLE `t_kx_dir` DISABLE KEYS */;
INSERT INTO `t_kx_dir` VALUES (5,'fgfg','\\','2009-11-05','newuser@126.com'),(6,'rff','\\','2009-11-26','lntoto@126.com'),(7,'ddffd','\\fgfg\\','2010-01-01','newuser@126.com');
/*!40000 ALTER TABLE `t_kx_dir` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_kx_feedback`
--

DROP TABLE IF EXISTS `t_kx_feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_kx_feedback` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(50) DEFAULT NULL,
  `time` varchar(45) DEFAULT NULL,
  `content` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_kx_feedback`
--

LOCK TABLES `t_kx_feedback` WRITE;
/*!40000 ALTER TABLE `t_kx_feedback` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_kx_feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_kx_files`
--

DROP TABLE IF EXISTS `t_kx_files`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_kx_files` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dir` varchar(200) COLLATE utf8_bin NOT NULL,
  `email` varchar(50) COLLATE utf8_bin NOT NULL,
  `filename` varchar(200) COLLATE utf8_bin NOT NULL,
  `upload_date` date NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `dir` (`dir`,`email`,`filename`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_kx_files`
--

LOCK TABLES `t_kx_files` WRITE;
/*!40000 ALTER TABLE `t_kx_files` DISABLE KEYS */;
INSERT INTO `t_kx_files` VALUES (1,'\\fgfg\\','newuser@126.com','艺术签.jpg','2009-11-10'),(2,'\\fgfg\\','newuser@126.com','game_107.swf','2009-12-16'),(3,'\\fgfg\\ddffd\\','newuser@126.com','dxva_sig.txt','2010-01-05'),(4,'\\fgfg\\','newuser@126.com','dxva_sig.txt','2010-01-05'),(7,'\\fgfg\\ddffd\\','newuser@126.com','于洪电信营业厅.txt','2010-01-05'),(8,'\\fgfg\\','newuser@126.com','于洪电信营业厅.txt','2010-01-05'),(9,'\\fgfg\\','newuser@126.com','test.txt','2010-01-05'),(10,'\\fgfg\\ddffd\\','newuser@126.com','plan.txt','2010-01-05'),(12,'\\fgfg\\','newuser@126.com','plan.txt','2010-01-05'),(14,'\\fgfg\\ddffd\\','newuser@126.com','mydb.txt','2010-01-05'),(15,'\\fgfg\\','newuser@126.com','.project','2010-01-05'),(17,'\\fgfg\\','newuser@126.com','.classpath','2010-01-05'),(20,'\\fgfg\\','newuser@126.com','applicationContext.xml','2010-01-05'),(21,'\\fgfg\\','newuser@126.com','aaa.xmind','2010-01-05'),(22,'\\fgfg\\','newuser@126.com','abc.properties','2010-01-05'),(23,'\\fgfg\\','newuser@126.com','abcd.java','2010-01-05'),(24,'\\fgfg\\','newuser@126.com','hibernate.cfg.xml','2010-01-05'),(25,'\\fgfg\\','newuser@126.com','新建 文本文档.txt','2010-01-05'),(27,'\\fgfg\\','newuser@126.com','新建文本文档.txt','2010-01-05'),(28,'\\fgfg\\','newuser@126.com','新建文本文档1.txt','2010-01-05'),(29,'\\fgfg\\ddffd\\','newuser@126.com','新建文本文档1.txt','2010-01-05'),(30,'\\fgfg\\','newuser@126.com','新建文本文档2.txt','2010-01-05'),(31,'\\fgfg\\','newuser@126.com','新建文本文档23txt','2010-01-05'),(33,'\\fgfg\\','newuser@126.com','readme.txt','2010-01-05');
/*!40000 ALTER TABLE `t_kx_files` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_kx_friends`
--

DROP TABLE IF EXISTS `t_kx_friends`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_kx_friends` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `my_email` varchar(50) COLLATE utf8_bin NOT NULL,
  `friend_email` varchar(50) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  KEY `my_email` (`my_email`),
  KEY `friend_email` (`friend_email`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_kx_friends`
--

LOCK TABLES `t_kx_friends` WRITE;
/*!40000 ALTER TABLE `t_kx_friends` DISABLE KEYS */;
INSERT INTO `t_kx_friends` VALUES (3,'newuser@126.com','lntoto@126.com'),(4,'lntoto@126.com','newuser@126.com'),(6,'newuser@126.com','2@126.com'),(8,'2@126.com','newuser@126.com'),(9,'androidguy@189.com','bill@189.com'),(10,'androidguy@189.com','mike@126.com'),(11,'bill@189.com','androidguy@189.com'),(12,'dtu','bill@189.com'),(13,'dtu','bill@189.com'),(14,'gh','bill@189.com'),(15,'gh','mike@126.com'),(16,'gh','aaa@hotmail.com'),(17,'gh','abcd'),(18,'gh','xyz@126.com'),(19,'gh','666@126.com'),(20,'gh','dddd@126.com'),(21,'gh','666@126.com'),(22,'gh','3@126.com'),(23,'gh','x@aa.com'),(24,'gh','hello@126.com'),(25,'gh','111@126.com'),(26,'gh','kkk@126.com'),(27,'gh','2@126.com'),(28,'androidguy@189.com','gh'),(29,'gh','androidguy@189.com'),(30,'ganzi1234','gh'),(31,'gh','ganzi1234'),(32,'GGYY','ganzi1234'),(33,'ganzi1234','GGYY'),(34,'gg','GGYY'),(35,'GGYY','gg'),(36,'ggyy','GGYY'),(37,'ggyy1','GGYY'),(38,'ggyy1','ggyy'),(39,'ggyy1','androidguy@189.com'),(40,'ggyy1','dtu'),(41,'ggyy1','mike@126.com'),(42,'ggyy1','229637012@qq.com'),(43,'ggyy1','ganzi'),(44,'ggyy1','ganziq'),(45,'98A24A0F9E11BABE863BD2264DFE6603','GGYY'),(46,'ggyy',''),(47,'ggyy','98A24A0F9E11BABE863BD2264DFE6603'),(48,'ggyy','ganzi1234'),(49,'ggyy','bill@189.com'),(50,'liujun1','98A24A0F9E11BABE863BD2264DFE6603');
/*!40000 ALTER TABLE `t_kx_friends` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_kx_gift`
--

DROP TABLE IF EXISTS `t_kx_gift`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_kx_gift` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `friend_email` varchar(30) COLLATE utf8_bin NOT NULL,
  `my_email` varchar(30) COLLATE utf8_bin NOT NULL,
  `gift_code` int(11) NOT NULL,
  `gift_time` datetime NOT NULL,
  `gift_postscript` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `new_gift` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `email` (`friend_email`),
  KEY `new_gift` (`new_gift`)
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_kx_gift`
--

LOCK TABLES `t_kx_gift` WRITE;
/*!40000 ALTER TABLE `t_kx_gift` DISABLE KEYS */;
INSERT INTO `t_kx_gift` VALUES (66,'newuser@126.com','xyz@126.com',0,'2009-11-26 13:08:27','a',0),(67,'newuser@126.com','xyz@126.com',1,'2009-11-26 13:08:45','b',0),(68,'newuser@126.com','xyz@126.com',11,'2009-11-26 13:08:50','c',0),(74,'newuser@126.com','xyz@126.com',1,'2009-11-26 13:16:23','aaa',0),(77,'newuser@126.com','xyz@126.com',7,'2009-11-26 13:22:00','xxx',0),(78,'newuser@126.com','xyz@126.com',10,'2009-11-26 13:22:04','xxxdfsdfsdf',0),(82,'newuser@126.com','xyz@126.com',0,'2009-11-26 13:24:59','a',0),(83,'newuser@126.com','xyz@126.com',5,'2009-11-26 13:25:04','b',0),(84,'newuser@126.com','xyz@126.com',6,'2009-11-26 13:25:08','c',0),(85,'newuser@126.com','xyz@126.com',3,'2009-11-26 13:25:12','d',0),(93,'newuser@126.com','abc@126.com',2,'2010-01-16 19:39:42','生日快乐',0),(94,'newuser@126.com','xyz@126.com',1,'2010-01-16 19:40:38','情人节快乐',0),(95,'bill@189.com','androidguy@189.com',0,'2010-04-24 19:56:32','dfd',0),(96,'bill@189.com','androidguy@189.com',3,'2010-04-25 00:00:00','啊啊啊',0),(97,'bill@189.com','androidguy@189.com',4,'2010-04-25 00:00:00','李宁',0),(98,'bill@189.com','androidguy@189.com',8,'2010-04-25 00:00:00','的的的',0),(99,'bill@189.com','androidguy@189.com',11,'2010-04-25 00:00:00','',0),(100,'bill@189.com','androidguy@189.com',5,'2010-04-25 00:00:00','礼物',0),(101,'androidguy@189.com','bill@189.com',7,'2010-04-25 00:00:00','你好',1),(102,'androidguy@189.com','bill@189.com',4,'2010-04-25 00:00:00','礼物1',1),(103,'androidguy@189.com','bill@189.com',6,'2010-04-25 00:00:00','喝一杯',1),(104,'androidguy@189.com','bill@189.com',11,'2010-04-25 00:00:00','情人节快乐',1),(105,'androidguy@189.com','bill@189.com',3,'2010-04-25 00:00:00','恭喜发财',1),(106,'bill@189.com','androidguy@189.com',0,'2014-06-16 00:00:00','',1);
/*!40000 ALTER TABLE `t_kx_gift` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_kx_location`
--

DROP TABLE IF EXISTS `t_kx_location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_kx_location` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(50) NOT NULL,
  `location` varchar(100) DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `time` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_kx_location`
--

LOCK TABLES `t_kx_location` WRITE;
/*!40000 ALTER TABLE `t_kx_location` DISABLE KEYS */;
INSERT INTO `t_kx_location` VALUES (1,'98A24A0F9E11BABE863BD2264DFE6603','四川省成都市武侯区盛世路118号',30.635717,104.030878,'2014-08-21 15:57:50'),(2,'98A24A0F9E11BABE863BD2264DFE6603','四川省成都市武侯区佳灵路20',30.635784,104.031295,'2014-08-21 15:59:36'),(3,'98A24A0F9E11BABE863BD2264DFE6603','四川省成都市武侯区佳灵路20',30.635836,104.031427,'2014-08-22 16:36:57'),(4,'98A24A0F9E11BABE863BD2264DFE6603','四川省成都市武侯区长益路118号-20-、21',30.63525,104.031811,'2014-08-22 16:41:51'),(5,'98A24A0F9E11BABE863BD2264DFE6603','四川省成都市武侯区佳灵路20',30.635758,104.031435,'2014-08-22 16:43:32'),(6,'98A24A0F9E11BABE863BD2264DFE6603','四川省成都市武侯区长益路118号-20-、21',30.63525,104.031811,'2014-08-22 16:45:12'),(7,'98A24A0F9E11BABE863BD2264DFE6603','四川省成都市武侯区佳灵路20',30.635761,104.031334,'2014-08-26 16:46:52'),(8,'98A24A0F9E11BABE863BD2264DFE6603','四川省成都市武侯区长益路118号-20-、21',30.635263,104.031796,'2014-08-26 17:00:24'),(9,'98A24A0F9E11BABE863BD2264DFE6603','四川省成都市武侯区盛世路118号',30.63569,104.030899,'2014-08-26 12:53:34'),(10,'98A24A0F9E11BABE863BD2264DFE6603','四川省成都市武侯区佳灵路20',30.635615,104.03111,'2014-08-26 12:55:15'),(11,'98A24A0F9E11BABE863BD2264DFE6603','四川省成都市武侯区盛世路118号',30.635785,104.030861,'2014-08-26 13:05:16'),(12,'98A24A0F9E11BABE863BD2264DFE6603','四川省成都市武侯区佳灵路20',30.635679,104.03132,'2014-08-26 13:07:19'),(13,'98A24A0F9E11BABE863BD2264DFE6603','四川省成都市武侯区盛世路118号',30.635696,104.03095,'2014-08-26 13:17:19'),(14,'98A24A0F9E11BABE863BD2264DFE6603','四川省成都市武侯区佳灵路20',30.63588,104.031025,'2014-08-26 13:18:59'),(15,'98A24A0F9E11BABE863BD2264DFE6603','四川省成都市武侯区盛世路118号',30.635661,104.03101,'2014-08-27 14:09:08'),(16,'98A24A0F9E11BABE863BD2264DFE6603','四川省成都市武侯区佳灵路20',30.635801,104.03142,'2014-08-27 14:10:47');
/*!40000 ALTER TABLE `t_kx_location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_kx_photos`
--

DROP TABLE IF EXISTS `t_kx_photos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_kx_photos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `album_id` int(11) NOT NULL,
  `photo_filename` varchar(200) COLLATE utf8_bin NOT NULL,
  `content_type` varchar(20) COLLATE utf8_bin NOT NULL,
  `comment_count` int(11) DEFAULT '0',
  `like_count` int(11) DEFAULT '0',
  `time` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `description` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `message_time` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `sign` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `content` varchar(1000) COLLATE utf8_bin DEFAULT NULL,
  `location` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `competence` int(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `album_id` (`album_id`,`photo_filename`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=301 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_kx_photos`
--

LOCK TABLES `t_kx_photos` WRITE;
/*!40000 ALTER TABLE `t_kx_photos` DISABLE KEYS */;
INSERT INTO `t_kx_photos` VALUES (41,24,60,'96c90eoexi','image/bmp',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(42,24,60,'c8caendprf','image/pjpeg',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(43,24,60,'3iat5c1v3y','image/pjpeg',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(44,7,62,'7l27os724o','image/pjpeg',1,12,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(45,7,62,'6pfmhxq9ce','image/pjpeg',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(46,7,62,'b7edpfpl0i','image/pjpeg',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(47,27,63,'ynx41gnsb4','image/pjpeg',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(48,28,64,'wmrz1761cg','image/x-png',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(49,28,64,'msxkrme8sd','image/x-png',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(50,28,64,'qvkiha7hs9','image/x-png',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(51,28,64,'yaor4c3giz','image/x-png',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(52,28,64,'knalbivcy5','image/x-png',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(53,28,64,'nf8khd9s3v','image/x-png',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(54,28,64,'2xhlpf9g2e','image/pjpeg',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(55,28,64,'z34orahlwx','image/pjpeg',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(56,28,64,'frqabln1ci','image/pjpeg',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(57,28,64,'rhvmnvyy0u','image/gif',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(58,28,64,'qlp9gz49si','image/pjpeg',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(59,28,64,'o4m81hvavc','image/pjpeg',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(60,28,65,'wqdblrosea','image/pjpeg',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(61,28,65,'4p4hkw2u8z','image/pjpeg',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(62,28,65,'onj90f7mv8','image/pjpeg',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(63,28,65,'s496g896jc','image/gif',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(64,28,65,'mo8ziwdibx','image/pjpeg',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(65,28,65,'if7v8pwfq2','image/pjpeg',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(66,28,65,'tiidygr6dc','image/pjpeg',0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(67,28,72,'iaubqynhoe','image/pjpeg',0,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(68,28,1,'9sobcy548z','image/pjpeg',0,0,NULL,NULL,'1',NULL,NULL,NULL,NULL,NULL,NULL),(69,28,1,'xid9u6rnhp','image/pjpeg',0,0,NULL,NULL,'1',NULL,NULL,NULL,NULL,NULL,NULL),(70,28,1,'5etbu7ndpm','image/pjpeg',0,0,NULL,NULL,'1',NULL,NULL,NULL,NULL,NULL,NULL),(71,28,1,'as1bb3hrb2','image/pjpeg',0,0,NULL,NULL,'1',NULL,NULL,NULL,NULL,NULL,NULL),(72,28,64,'gi2yld3lfu','image/pjpeg',0,0,NULL,NULL,'Fri Jun 20 15:33:00 格林尼治标准时间+0800 2014',NULL,NULL,NULL,NULL,NULL,NULL),(73,28,64,'zchoihe36m','image/pjpeg',0,0,NULL,NULL,'Fri Jun 20 15:38:23 格林尼治标准时间+0800 2014',NULL,NULL,NULL,NULL,NULL,NULL),(74,28,72,'36gfvr73qi','image/pjpeg',0,0,NULL,NULL,'Fri Jun 20 15:44:53 格林尼治标准时间+0800 2014',NULL,NULL,NULL,NULL,NULL,NULL),(75,28,72,'dcr0dk0ran','image/pjpeg',0,0,NULL,NULL,'Fri Jun 20 15:46:59 格林尼治标准时间+0800 2014',NULL,NULL,NULL,NULL,NULL,NULL),(76,28,65,'99p1eiqxh8','image/pjpeg',0,0,NULL,NULL,'Fri Jun 20 15:50:19 格林尼治标准时间+0800 2014',NULL,NULL,NULL,NULL,NULL,NULL),(77,28,65,'7s1lrrgdqx','image/pjpeg',0,0,NULL,NULL,'Fri Jun 20 15:50:19 格林尼治标准时间+0800 2014',NULL,NULL,NULL,NULL,NULL,NULL),(78,28,70,'x24axy0t76','image/pjpeg',0,0,NULL,NULL,'Fri Jun 20 16:13:25 格林尼治标准时间+0800 2014',NULL,NULL,NULL,NULL,NULL,NULL),(79,28,72,'ukyn8dygnn','image/pjpeg',0,0,NULL,NULL,'Fri Jun 20 16:15:18 格林尼治标准时间+0800 2014',NULL,NULL,NULL,NULL,NULL,NULL),(80,28,71,'8whhfv5gkl','image/pjpeg',0,0,NULL,NULL,'Fri Jun 20 16:21:37 格林尼治标准时间+0800 2014',NULL,NULL,NULL,NULL,NULL,NULL),(81,28,71,'wumepmdwbg','image/pjpeg',0,0,NULL,NULL,'Fri Jun 20 16:29:24 格林尼治标准时间+0800 2014',NULL,NULL,NULL,NULL,NULL,NULL),(82,28,71,'dph1a3gu9c','image/pjpeg',0,0,NULL,NULL,'Fri Jun 20 16:29:24 格林尼治标准时间+0800 2014',NULL,NULL,NULL,NULL,NULL,NULL),(83,28,71,'2yghf7bt9j','image/pjpeg',0,0,NULL,NULL,'Fri Jun 20 16:29:24 格林尼治标准时间+0800 2014',NULL,NULL,NULL,NULL,NULL,NULL),(84,28,71,'74kequa8bi','image/pjpeg',0,0,NULL,NULL,'Fri Jun 20 16:29:24 格林尼治标准时间+0800 2014',NULL,NULL,NULL,NULL,NULL,NULL),(85,28,71,'603dffmz4p','image/pjpeg',0,0,NULL,NULL,'Fri Jun 20 16:34:08 格林尼治标准时间+0800 2014',NULL,NULL,NULL,NULL,NULL,NULL),(86,28,71,'k829jn6nku','image/pjpeg',0,0,NULL,NULL,'Fri Jun 20 16:34:08 格林尼治标准时间+0800 2014',NULL,NULL,NULL,NULL,NULL,NULL),(87,28,71,'7rtcxcqtvf','image/pjpeg',0,0,NULL,NULL,'Fri Jun 20 16:34:08 格林尼治标准时间+0800 2014',NULL,NULL,NULL,NULL,NULL,NULL),(88,28,71,'yhxv0v7ljg','image/pjpeg',0,0,NULL,NULL,'Fri Jun 20 16:34:08 格林尼治标准时间+0800 2014',NULL,NULL,NULL,NULL,NULL,NULL),(89,29,73,'m02r47zz8t','image/pjpeg',0,0,NULL,NULL,'Tue Jun 24 17:36:15 格林尼治标准时间+0800 2014',NULL,NULL,NULL,NULL,NULL,NULL),(90,29,73,'l5qijovwhd','image/pjpeg',0,0,NULL,NULL,'Wed Jun 25 10:52:20 æ ¼æå°¼æ²»æ åæ¶é´+0800 2014',NULL,NULL,NULL,NULL,NULL,NULL),(91,29,73,'16shtw321q','image/pjpeg',0,3,NULL,NULL,'Wed Jun 25 11:09:40 æ ¼æå°¼æ²»æ åæ¶é´+0800 2014',NULL,NULL,NULL,NULL,NULL,NULL),(92,29,73,'zv3i90amhe','image/pjpeg',0,0,NULL,NULL,'Wed Jun 25 11:18:34 æ ¼æå°¼æ²»æ åæ¶é´+0800 2014',NULL,NULL,NULL,NULL,NULL,NULL),(93,29,73,'nc79xosgr9','image/pjpeg',0,0,NULL,NULL,'Wed Jun 25 11:26:01 æ ¼æå°¼æ²»æ åæ¶é´+0800 2014',NULL,NULL,NULL,NULL,NULL,NULL),(94,29,73,'ldidhg0msk','image/pjpeg',0,0,NULL,NULL,'Wed Jun 25 11:26:01 æ ¼æå°¼æ²»æ åæ¶é´+0800 2014',NULL,NULL,NULL,NULL,NULL,NULL),(95,29,73,'cr5y164kzi','image/pjpeg',0,0,NULL,NULL,'Wed Jun 25 11:26:01 æ ¼æå°¼æ²»æ åæ¶é´+0800 2014',NULL,NULL,NULL,NULL,NULL,NULL),(96,29,73,'oiqnqjwuaa','image/pjpeg',0,0,NULL,NULL,'Wed Jun 25 11:26:01 æ ¼æå°¼æ²»æ åæ¶é´+0800 2014',NULL,NULL,NULL,NULL,NULL,NULL),(97,29,73,'fntvnj8i4n','image/pjpeg',0,0,NULL,NULL,'Wed Jun 25 11:48:07 æ ¼æå°¼æ²»æ åæ¶é´+0800 2014',NULL,NULL,NULL,NULL,NULL,NULL),(98,29,73,'epkqxpnjvn','image/pjpeg',0,0,NULL,NULL,'Wed Jun 25 12:01:20 æ ¼æå°¼æ²»æ åæ¶é´+0800 2014',NULL,NULL,NULL,NULL,NULL,NULL),(99,29,73,'8wfmsrl91g','image/pjpeg',0,0,NULL,NULL,'Wed Jun 25 12:08:01 æ ¼æå°¼æ²»æ åæ¶é´+0800 2014',NULL,NULL,NULL,NULL,NULL,NULL),(100,29,73,'8wfmsrl91g','image/pjpeg',0,0,NULL,NULL,'Wed Jun 25 12:08:01 æ ¼æå°¼æ²»æ åæ¶é´+0800 2014',NULL,NULL,NULL,NULL,NULL,NULL),(101,29,73,'8wfmsrl91g','image/pjpeg',0,0,NULL,NULL,'Wed Jun 25 12:08:01 æ ¼æå°¼æ²»æ åæ¶é´+0800 2014',NULL,NULL,NULL,NULL,NULL,NULL),(102,29,73,'8wfmsrl91g','image/pjpeg',0,0,NULL,NULL,'Wed Jun 25 12:08:01 æ ¼æå°¼æ²»æ åæ¶é´+0800 2014',NULL,NULL,NULL,NULL,NULL,NULL),(103,29,73,'8wfmsrl91g','image/pjpeg',0,0,NULL,NULL,'Wed Jun 25 12:08:01 æ ¼æå°¼æ²»æ åæ¶é´+0800 2014',NULL,NULL,NULL,NULL,NULL,NULL),(104,29,73,'8wfmsrl91g','image/pjpeg',0,0,NULL,NULL,'Wed Jun 25 12:08:01 æ ¼æå°¼æ²»æ åæ¶é´+0800 2014',NULL,NULL,NULL,NULL,NULL,NULL),(105,29,73,'8wfmsrl91g','image/pjpeg',0,0,NULL,NULL,'Wed Jun 25 12:08:01 æ ¼æå°¼æ²»æ åæ¶é´+0800 2014',NULL,NULL,NULL,NULL,NULL,NULL),(106,29,73,'8wfmsrl91g','image/pjpeg',0,0,NULL,NULL,'Wed Jun 25 12:08:01 æ ¼æå°¼æ²»æ åæ¶é´+0800 2014',NULL,NULL,NULL,NULL,NULL,NULL),(107,29,73,'8wfmsrl91g','image/pjpeg',0,0,NULL,NULL,'Wed Jun 25 12:08:01 æ ¼æå°¼æ²»æ åæ¶é´+0800 2014',NULL,NULL,NULL,NULL,NULL,NULL),(108,29,73,'4pgswy0y83','image/pjpeg',0,0,NULL,NULL,'Wed Jun 25 12:12:24 æ ¼æå°¼æ²»æ åæ¶é´+0800 2014',NULL,NULL,NULL,NULL,NULL,NULL),(109,29,73,'9ydmhl6fkh','image/pjpeg',0,0,NULL,NULL,'Wed Jun 25 12:12:24 æ ¼æå°¼æ²»æ åæ¶é´+0800 2014',NULL,NULL,NULL,NULL,NULL,NULL),(110,29,73,'qp7cpyk6hv','image/pjpeg',0,0,NULL,NULL,'Wed Jun 25 12:12:24 æ ¼æå°¼æ²»æ åæ¶é´+0800 2014',NULL,NULL,NULL,NULL,NULL,NULL),(111,29,73,'yzhrzwsiop','image/pjpeg',0,0,NULL,NULL,'Wed Jun 25 12:12:24 æ ¼æå°¼æ²»æ åæ¶é´+0800 2014',NULL,NULL,NULL,NULL,NULL,NULL),(112,29,73,'km073ru1pg','image/pjpeg',0,0,NULL,NULL,'Wed Jun 25 12:12:24 æ ¼æå°¼æ²»æ åæ¶é´+0800 2014',NULL,NULL,NULL,NULL,NULL,NULL),(113,29,73,'ub6xzvoo31','image/pjpeg',0,0,NULL,NULL,'Wed Jun 25 12:12:24 æ ¼æå°¼æ²»æ åæ¶é´+0800 2014',NULL,NULL,NULL,NULL,NULL,NULL),(114,29,73,'1zo72cibfo','image/pjpeg',0,3,NULL,NULL,'Wed Jun 25 12:12:24 æ ¼æå°¼æ²»æ åæ¶é´+0800 2014',NULL,NULL,NULL,NULL,NULL,NULL),(115,29,73,'nhawjqg8d7','image/pjpeg',0,0,NULL,NULL,'Wed Jun 25 12:12:24 æ ¼æå°¼æ²»æ åæ¶é´+0800 2014',NULL,NULL,NULL,NULL,NULL,NULL),(116,29,73,'p12ntsv18h','image/pjpeg',0,1,NULL,NULL,'Wed Jun 25 12:12:24 æ ¼æå°¼æ²»æ åæ¶é´+0800 2014',NULL,NULL,NULL,NULL,NULL,NULL),(117,29,76,'4u1zqlqxk9','image/pjpeg',0,0,NULL,NULL,'2014-06-26','bgojz68bxd',NULL,NULL,NULL,NULL,NULL),(118,29,76,'o7ixga11ni','image/pjpeg',0,0,NULL,NULL,'2014-06-26','bgojz68bxd',NULL,NULL,NULL,NULL,NULL),(119,29,76,'rubcpqo8q7','image/pjpeg',0,0,NULL,NULL,'2014-06-26','ash8wn7lsz',NULL,NULL,NULL,NULL,NULL),(120,29,76,'y8mtr5u750','image/pjpeg',0,0,NULL,NULL,'2014-06-26','ash8wn7lsz',NULL,NULL,NULL,NULL,NULL),(121,29,76,'nrwfak0g15','image/pjpeg',0,0,NULL,NULL,'2014-06-26','vjjy45e2va',NULL,NULL,NULL,NULL,NULL),(122,29,76,'3p9ru16y6d','image/pjpeg',0,0,NULL,NULL,'2014-06-26','vjjy45e2va',NULL,NULL,NULL,NULL,NULL),(123,29,76,'azmiy3oz96','image/pjpeg',0,0,NULL,NULL,'2014-06-26','0c5kgyrovw',NULL,NULL,NULL,NULL,NULL),(124,29,76,'563o0oe277','image/pjpeg',0,0,NULL,NULL,'2014-06-26','0c5kgyrovw',NULL,NULL,NULL,NULL,NULL),(125,29,76,'7yb67z7dqc','image/pjpeg',0,0,NULL,NULL,'2014-06-26','yy57wtqwc8',NULL,NULL,NULL,NULL,NULL),(126,29,76,'siq5txe86j','image/pjpeg',0,0,NULL,NULL,'2014-06-26','yy57wtqwc8',NULL,NULL,NULL,NULL,NULL),(127,29,76,'ouoba8pcz7','image/pjpeg',0,0,NULL,NULL,'2014-06-26','yy57wtqwc8',NULL,NULL,NULL,NULL,NULL),(128,29,76,'v2zyask9hp','image/pjpeg',0,0,NULL,NULL,'2014-06-26','yy57wtqwc8',NULL,NULL,NULL,NULL,NULL),(129,29,76,'5v4zv31kd9','image/pjpeg',0,0,NULL,NULL,'2014-06-26','yy57wtqwc8',NULL,NULL,NULL,NULL,NULL),(130,29,76,'89auvg12v7','image/pjpeg',0,0,NULL,NULL,'2014-06-26','yy57wtqwc8',NULL,NULL,NULL,NULL,NULL),(131,29,76,'rh7dn89768','image/pjpeg',0,0,NULL,NULL,'2014-06-26','yy57wtqwc8',NULL,NULL,NULL,NULL,NULL),(132,29,76,'qn6vpg6i76','image/pjpeg',0,0,NULL,NULL,'2014-06-26','yy57wtqwc8',NULL,NULL,NULL,NULL,NULL),(133,29,76,'chitm9b5py','image/pjpeg',0,0,NULL,NULL,'2014-06-26','yy57wtqwc8',NULL,NULL,NULL,NULL,NULL),(134,29,75,'0d3hdvoxpk','image/pjpeg',0,0,NULL,NULL,'2014-06-26','bx93nqvjju',NULL,NULL,NULL,NULL,NULL),(135,29,75,'w9rzkqs9ot','image/pjpeg',0,0,NULL,NULL,'2014-06-26','bx93nqvjju',NULL,NULL,NULL,NULL,NULL),(136,29,75,'1lcxmcsfhs','image/pjpeg',0,0,NULL,NULL,'2014-06-26','bx93nqvjju',NULL,NULL,NULL,NULL,NULL),(137,29,75,'jk7grc1a3f','image/pjpeg',0,0,NULL,NULL,'2014-06-26','bx93nqvjju',NULL,NULL,NULL,NULL,NULL),(138,29,75,'yq5jy476th','image/pjpeg',0,0,NULL,NULL,'2014-06-26','bx93nqvjju',NULL,NULL,NULL,NULL,NULL),(139,29,75,'rb6wp9r9l2','image/pjpeg',0,0,NULL,NULL,'2014-06-26','bx93nqvjju',NULL,NULL,NULL,NULL,NULL),(140,29,75,'bmu5akuigo','image/pjpeg',0,0,NULL,NULL,'2014-06-26','bx93nqvjju',NULL,NULL,NULL,NULL,NULL),(141,29,75,'i71tdg4lrp','image/pjpeg',0,0,NULL,NULL,'2014-06-26','bx93nqvjju',NULL,NULL,NULL,NULL,NULL),(142,29,75,'pewz7z776w','image/pjpeg',0,0,NULL,NULL,'2014-06-26','bx93nqvjju',NULL,NULL,NULL,NULL,NULL),(143,29,74,'fd0b946p3v','image/pjpeg',0,0,NULL,NULL,'2014-06-26','kr6vhsufzu',NULL,NULL,NULL,NULL,NULL),(144,29,74,'av9vfryx9r','image/pjpeg',0,0,NULL,NULL,'2014-06-26','kr6vhsufzu',NULL,NULL,NULL,NULL,NULL),(145,29,74,'shle0cyo2c','image/pjpeg',0,0,NULL,NULL,'2014-06-26','kr6vhsufzu',NULL,NULL,NULL,NULL,NULL),(146,29,74,'koyh6fxko2','image/pjpeg',0,0,NULL,NULL,'2014-06-26','kr6vhsufzu',NULL,NULL,NULL,NULL,NULL),(147,29,74,'u6etz3kte4','image/pjpeg',0,0,NULL,NULL,'2014-06-26','kr6vhsufzu',NULL,NULL,NULL,NULL,NULL),(148,29,74,'e7wcsomqdh','image/pjpeg',0,0,NULL,NULL,'2014-06-26','kr6vhsufzu',NULL,NULL,NULL,NULL,NULL),(149,29,74,'974krfeia9','image/pjpeg',0,0,NULL,NULL,'2014-06-26','kr6vhsufzu',NULL,NULL,NULL,NULL,NULL),(150,29,74,'govrx7npzd','image/pjpeg',0,0,NULL,NULL,'2014-06-26','kr6vhsufzu',NULL,NULL,NULL,NULL,NULL),(151,29,74,'95sn8g5nqe','image/pjpeg',0,0,NULL,NULL,'2014-06-26','kr6vhsufzu',NULL,NULL,NULL,NULL,NULL),(152,29,74,'hjqr1vuqrg','image/pjpeg',0,0,NULL,NULL,'2014-06-26','aurpsbba4p',NULL,NULL,NULL,NULL,NULL),(153,29,74,'ttkuj4b8q3','image/pjpeg',0,0,NULL,NULL,'2014-06-26','aurpsbba4p',NULL,NULL,NULL,NULL,NULL),(154,29,74,'kd5z6ik8cr','image/pjpeg',0,0,NULL,NULL,'2014-06-26','aurpsbba4p',NULL,NULL,NULL,NULL,NULL),(155,29,74,'h5zhylywud','image/pjpeg',0,0,NULL,NULL,'2014-06-26','aurpsbba4p',NULL,NULL,NULL,NULL,NULL),(156,29,74,'jmhlnc9kgt','image/pjpeg',0,0,NULL,NULL,'2014-06-26','aurpsbba4p',NULL,NULL,NULL,NULL,NULL),(157,29,74,'kevrtv3krn','image/pjpeg',0,0,NULL,NULL,'2014-06-26','aurpsbba4p',NULL,NULL,NULL,NULL,NULL),(158,29,74,'8ke75ubgp8','image/pjpeg',0,0,NULL,NULL,'2014-06-26','aurpsbba4p',NULL,NULL,NULL,NULL,NULL),(159,29,74,'3wwu9uwj12','image/pjpeg',0,0,NULL,NULL,'2014-06-26','aurpsbba4p',NULL,NULL,NULL,NULL,NULL),(160,29,74,'g0uxjmft25','image/pjpeg',0,0,NULL,NULL,'2014-06-26','aurpsbba4p',NULL,NULL,NULL,NULL,NULL),(161,29,74,'m0ipem6fkj','image/pjpeg',0,0,NULL,NULL,'2014-06-26','ulflopr62h',NULL,NULL,NULL,NULL,NULL),(162,29,74,'z71ctjk0wm','image/pjpeg',0,0,NULL,NULL,'2014-06-26','ulflopr62h',NULL,NULL,NULL,NULL,NULL),(163,29,74,'7vuf322gcg','image/pjpeg',0,0,NULL,NULL,'2014-06-26','ulflopr62h',NULL,NULL,NULL,NULL,NULL),(164,29,74,'lsz3qb2mf5','image/pjpeg',0,0,NULL,NULL,'2014-06-26','ulflopr62h',NULL,NULL,NULL,NULL,NULL),(165,29,74,'yq2peymjos','image/pjpeg',0,0,NULL,NULL,'2014-06-26','ulflopr62h',NULL,NULL,NULL,NULL,NULL),(166,29,74,'bj5nsi56ld','image/pjpeg',0,0,NULL,NULL,'2014-06-26','ulflopr62h',NULL,NULL,NULL,NULL,NULL),(167,29,74,'zrk5brnam1','image/pjpeg',0,0,NULL,NULL,'2014-06-26','ulflopr62h',NULL,NULL,NULL,NULL,NULL),(168,29,74,'0umtz4rw8t','image/pjpeg',0,0,NULL,NULL,'2014-06-26','ulflopr62h',NULL,NULL,NULL,NULL,NULL),(169,29,74,'eh7yy7a8nd','image/pjpeg',0,0,NULL,NULL,'2014-06-26','ulflopr62h',NULL,NULL,NULL,NULL,NULL),(170,29,76,'f9ai0eh1xq','image/pjpeg',0,0,NULL,NULL,'2014-06-26','19hg8vfonx',NULL,NULL,NULL,NULL,NULL),(171,29,76,'3lo2t8qpe0','image/pjpeg',0,0,NULL,NULL,'2014-06-26','19hg8vfonx',NULL,NULL,NULL,NULL,NULL),(172,29,76,'p6o2noi6gc','image/pjpeg',0,0,NULL,NULL,'2014-06-26','19hg8vfonx',NULL,NULL,NULL,NULL,NULL),(173,29,76,'cpnso46p6l','image/pjpeg',0,0,NULL,NULL,'2014-06-26','19hg8vfonx',NULL,NULL,NULL,NULL,NULL),(174,29,76,'ewwu8se4xn','image/pjpeg',0,0,NULL,NULL,'2014-06-26','19hg8vfonx',NULL,NULL,NULL,NULL,NULL),(175,29,76,'ltnaei14vb','image/pjpeg',0,0,NULL,NULL,'2014-06-26','19hg8vfonx',NULL,NULL,NULL,NULL,NULL),(176,29,76,'mzpl8gfg2c','image/pjpeg',0,0,NULL,NULL,'2014-06-26','19hg8vfonx',NULL,NULL,NULL,NULL,NULL),(177,29,76,'ikr2dhu5u3','image/pjpeg',0,0,NULL,NULL,'2014-06-26','19hg8vfonx',NULL,NULL,NULL,NULL,NULL),(178,29,76,'13q7nqofp5','image/pjpeg',6,2,NULL,NULL,'2014-06-26','19hg8vfonx',NULL,NULL,NULL,NULL,NULL),(179,29,76,'2g4zm48vwy','image/pjpeg',0,0,NULL,NULL,'2014-06-26','quemi8db5c',NULL,NULL,NULL,NULL,NULL),(180,29,76,'nk5dkk5526','image/pjpeg',0,0,NULL,NULL,'2014-06-26','quemi8db5c',NULL,NULL,NULL,NULL,NULL),(181,29,76,'zz9p6tzi1i','image/pjpeg',0,0,NULL,NULL,'2014-06-26','quemi8db5c',NULL,NULL,NULL,NULL,NULL),(182,29,76,'lxyi3u0pqj','image/pjpeg',0,0,NULL,NULL,'2014-06-26','quemi8db5c',NULL,NULL,NULL,NULL,NULL),(183,29,76,'6c00s92vqy','image/pjpeg',0,0,NULL,NULL,'2014-06-26','quemi8db5c',NULL,NULL,NULL,NULL,NULL),(184,29,76,'oesk0p7g76','image/pjpeg',0,0,NULL,NULL,'2014-06-26','quemi8db5c',NULL,NULL,NULL,NULL,NULL),(185,29,76,'laapd4c1vw','image/pjpeg',0,0,NULL,NULL,'2014-06-26','quemi8db5c',NULL,NULL,NULL,NULL,NULL),(186,29,76,'2yy2zt7fmt','image/pjpeg',0,0,NULL,NULL,'2014-06-26','quemi8db5c',NULL,NULL,NULL,NULL,NULL),(187,29,76,'j5np6i19lb','image/pjpeg',0,0,NULL,NULL,'2014-06-26','quemi8db5c',NULL,NULL,NULL,NULL,NULL),(188,29,74,'iippzbzbai','image/pjpeg',0,0,NULL,NULL,'2014-06-26','lvvyfjy5ab',NULL,NULL,NULL,NULL,NULL),(189,29,74,'3oi96z7rtr','image/pjpeg',0,0,NULL,NULL,'2014-06-26','lvvyfjy5ab',NULL,NULL,NULL,NULL,NULL),(190,29,74,'9020k0w2du','image/pjpeg',0,0,NULL,NULL,'2014-06-26','lvvyfjy5ab',NULL,NULL,NULL,NULL,NULL),(191,29,74,'h9ei4dd7mv','image/pjpeg',0,0,NULL,NULL,'2014-06-26','lvvyfjy5ab',NULL,NULL,NULL,NULL,NULL),(192,29,74,'bj6ux0ise6','image/pjpeg',0,0,NULL,NULL,'2014-06-26','lvvyfjy5ab',NULL,NULL,NULL,NULL,NULL),(193,29,74,'z55fmzkdp2','image/pjpeg',0,0,NULL,NULL,'2014-06-26','lvvyfjy5ab',NULL,NULL,NULL,NULL,NULL),(194,29,74,'9bvf8vn6i3','image/pjpeg',0,0,NULL,NULL,'2014-06-26','lvvyfjy5ab',NULL,NULL,NULL,NULL,NULL),(195,29,74,'w56lq7t43b','image/pjpeg',0,0,NULL,NULL,'2014-06-26','lvvyfjy5ab',NULL,NULL,NULL,NULL,NULL),(196,29,74,'3947jh9zwq','image/pjpeg',0,0,NULL,NULL,'2014-06-26','lvvyfjy5ab',NULL,NULL,NULL,NULL,NULL),(197,29,76,'4cbkeyx5t2','image/pjpeg',0,0,NULL,NULL,'2014-06-26','uy30xshxcv',NULL,NULL,NULL,NULL,NULL),(198,29,76,'6s044jc3ug','image/pjpeg',0,0,NULL,NULL,'2014-06-26','uy30xshxcv',NULL,NULL,NULL,NULL,NULL),(199,29,76,'el0jkkhu5a','image/pjpeg',0,0,NULL,NULL,'2014-06-26','uy30xshxcv',NULL,NULL,NULL,NULL,NULL),(200,29,76,'1pxvzv2hpx','image/pjpeg',0,0,NULL,NULL,'2014-06-26','uy30xshxcv',NULL,NULL,NULL,NULL,NULL),(201,29,76,'2cjye3e32x','image/pjpeg',0,0,NULL,NULL,'2014-06-26','uy30xshxcv',NULL,NULL,NULL,NULL,NULL),(202,29,76,'24y1k1nmox','image/pjpeg',0,0,NULL,NULL,'2014-06-26','uy30xshxcv',NULL,NULL,NULL,NULL,NULL),(203,29,76,'9tvo7576p9','image/pjpeg',0,0,NULL,NULL,'2014-06-26','uy30xshxcv',NULL,NULL,NULL,NULL,NULL),(204,29,76,'pnv51ew3jq','image/pjpeg',0,0,NULL,NULL,'2014-06-26','uy30xshxcv',NULL,NULL,NULL,NULL,NULL),(205,29,76,'67lyni67ts','image/pjpeg',0,0,NULL,NULL,'2014-06-26','uy30xshxcv',NULL,NULL,NULL,NULL,NULL),(206,29,74,'0yh1ohw1f4','image/pjpeg',0,0,NULL,NULL,'2014-06-26','u9iaoc951y',NULL,NULL,NULL,NULL,NULL),(207,29,74,'l47uzx1yl7','image/pjpeg',0,0,NULL,NULL,'2014-06-26','u9iaoc951y',NULL,NULL,NULL,NULL,NULL),(208,29,74,'rpfb0yvwhr','image/pjpeg',0,0,NULL,NULL,'2014-06-26','u9iaoc951y',NULL,NULL,NULL,NULL,NULL),(209,29,74,'0auqdrt0iy','image/pjpeg',0,0,NULL,NULL,'2014-06-26','u9iaoc951y',NULL,NULL,NULL,NULL,NULL),(210,29,74,'rany0urop1','image/pjpeg',0,0,NULL,NULL,'2014-06-26','u9iaoc951y',NULL,NULL,NULL,NULL,NULL),(211,29,74,'4gp1w2e3ok','image/pjpeg',0,0,NULL,NULL,'2014-06-26','u9iaoc951y',NULL,NULL,NULL,NULL,NULL),(212,29,74,'32lvmlg6ot','image/pjpeg',0,0,NULL,NULL,'2014-06-26','u9iaoc951y',NULL,NULL,NULL,NULL,NULL),(213,29,74,'g300bzm1gm','image/pjpeg',0,0,NULL,NULL,'2014-06-26','u9iaoc951y',NULL,NULL,NULL,NULL,NULL),(214,29,74,'x9wa0fy10y','image/pjpeg',0,0,NULL,NULL,'2014-06-26','u9iaoc951y',NULL,NULL,NULL,NULL,NULL),(215,29,74,'87bed7ugnp','image/pjpeg',0,0,NULL,NULL,'2014-06-26','0ht5wljbs6',NULL,NULL,NULL,NULL,NULL),(216,29,74,'1a6wzsxu5t','image/pjpeg',0,0,NULL,NULL,'2014-06-26','0ht5wljbs6',NULL,NULL,NULL,NULL,NULL),(217,29,74,'in0pcy0txx','image/pjpeg',0,0,NULL,NULL,'2014-06-26','0ht5wljbs6',NULL,NULL,NULL,NULL,NULL),(218,29,74,'tz88nnvsgf','image/pjpeg',0,0,NULL,NULL,'2014-06-26','0ht5wljbs6',NULL,NULL,NULL,NULL,NULL),(219,29,74,'96z2n6cv1i','image/pjpeg',0,0,NULL,NULL,'2014-06-26','0ht5wljbs6',NULL,NULL,NULL,NULL,NULL),(220,29,74,'9fo4z328d1','image/pjpeg',0,0,NULL,NULL,'2014-06-26','0ht5wljbs6',NULL,NULL,NULL,NULL,NULL),(221,29,74,'1i1ps7izca','image/pjpeg',0,0,NULL,NULL,'2014-06-26','0ht5wljbs6',NULL,NULL,NULL,NULL,NULL),(222,29,74,'56wz0pqcz2','image/pjpeg',0,0,NULL,NULL,'2014-06-26','0ht5wljbs6',NULL,NULL,NULL,NULL,NULL),(223,29,74,'u3aaryxuhq','image/pjpeg',0,0,NULL,NULL,'2014-06-26','0ht5wljbs6',NULL,NULL,NULL,NULL,NULL),(224,29,76,'1xh7k33sec','image/pjpeg',0,0,NULL,NULL,'2014-06-26','dmj1u3yycu',NULL,NULL,NULL,NULL,NULL),(225,29,76,'fu2obnjydw','image/pjpeg',0,0,NULL,NULL,'2014-06-26','dmj1u3yycu',NULL,NULL,NULL,NULL,NULL),(226,29,76,'ultjohzc81','image/pjpeg',0,0,NULL,NULL,'2014-06-26','dmj1u3yycu',NULL,NULL,NULL,NULL,NULL),(227,29,76,'osu14so4hk','image/pjpeg',0,0,NULL,NULL,'2014-06-26','dmj1u3yycu',NULL,NULL,NULL,NULL,NULL),(228,29,76,'tlxvcdy2kc','image/pjpeg',0,0,NULL,NULL,'2014-06-26','dmj1u3yycu',NULL,NULL,NULL,NULL,NULL),(229,29,76,'lma7cv6vmm','image/pjpeg',0,0,NULL,NULL,'2014-06-26','dmj1u3yycu',NULL,NULL,NULL,NULL,NULL),(230,29,76,'hi5jg16ajz','image/pjpeg',0,0,NULL,NULL,'2014-06-26','dmj1u3yycu',NULL,NULL,NULL,NULL,NULL),(231,29,76,'bjrdz3xtmi','image/pjpeg',0,0,NULL,NULL,'2014-06-26','dmj1u3yycu',NULL,NULL,NULL,NULL,NULL),(232,29,76,'alvv8ey7yb','image/pjpeg',0,0,NULL,NULL,'2014-06-26','dmj1u3yycu',NULL,NULL,NULL,NULL,NULL),(233,29,0,'8i1t0twdlo','image/pjpeg',0,0,NULL,NULL,'2014-06-27','zicz7s7l4y','åå¥é»å®',NULL,NULL,NULL,NULL),(234,29,77,'8dgdlt56p6','image/pjpeg',0,0,NULL,NULL,'2014-06-30 15:01:56','6bbfo1bp32','iææº',NULL,NULL,NULL,NULL),(235,29,77,'z3wvkicp3p','image/pjpeg',0,0,NULL,NULL,'2014-06-30 15:01:56','6bbfo1bp32','iææº',NULL,NULL,NULL,NULL),(236,29,77,'1objx5zy4x','image/pjpeg',0,0,NULL,NULL,'2014-06-30 15:01:57','6bbfo1bp32','iææº',NULL,NULL,NULL,NULL),(237,29,77,'kngbrwb6zo','image/pjpeg',0,0,NULL,NULL,'2014-06-30 15:01:57','6bbfo1bp32','iææº',NULL,NULL,NULL,NULL),(238,29,77,'idf0txw6gt','image/pjpeg',0,0,NULL,NULL,'2014-06-30 15:11:12','jqfl1msw7l','æ¹æ³',NULL,NULL,NULL,NULL),(239,29,77,'sxqf0v9sqc','image/pjpeg',0,0,NULL,NULL,'2014-06-30 15:11:12','jqfl1msw7l','æ¹æ³',NULL,NULL,NULL,NULL),(240,29,77,'449758540\\77\\jekie787ei','image/pjpeg',0,0,NULL,NULL,'2014-06-30 15:27:32','dij0d71jqv','å°å®¶',NULL,NULL,NULL,NULL),(241,29,77,'449758540\\77\\otlunw556a','image/pjpeg',0,0,NULL,NULL,'2014-06-30 15:27:53','ia7u0m95sq','å°å®¶',NULL,NULL,NULL,NULL),(242,29,77,'449758540/77/2t1wmipb5z','image/pjpeg',0,0,NULL,NULL,'2014-06-30 15:44:27','s56irt2f31','IDå°±',NULL,NULL,NULL,NULL),(243,29,78,'449758540/78/7reqh4xgpl','image/pjpeg',0,0,NULL,NULL,'2014-06-30 16:05:38','fe9z1zewjw','å åçº§æ¥å£',NULL,NULL,NULL,NULL),(244,29,78,'449758540/78/y5rg4qhvj4','image/pjpeg',0,0,NULL,NULL,'2014-06-30 16:05:38','fe9z1zewjw','å åçº§æ¥å£',NULL,NULL,NULL,NULL),(245,29,78,'449758540/78/lxntw7su7o','image/pjpeg',0,0,NULL,NULL,'2014-06-30 16:05:38','fe9z1zewjw','å åçº§æ¥å£',NULL,NULL,NULL,NULL),(246,29,78,'449758540/78/ypmey7k9w3','image/pjpeg',0,0,NULL,NULL,'2014-06-30 16:05:38','fe9z1zewjw','å åçº§æ¥å£',NULL,NULL,NULL,NULL),(247,29,78,'449758540/78/ttg1grh5p9','image/pjpeg',0,0,NULL,NULL,'2014-06-30 16:05:38','fe9z1zewjw','å åçº§æ¥å£',NULL,NULL,NULL,NULL),(248,29,78,'449758540/78/y1rp1lv787','image/pjpeg',0,0,NULL,NULL,'2014-06-30 16:05:38','fe9z1zewjw','å åçº§æ¥å£',NULL,NULL,NULL,NULL),(249,29,78,'449758540/78/gb9ubz0dcl','image/pjpeg',0,0,NULL,NULL,'2014-06-30 16:05:38','fe9z1zewjw','å åçº§æ¥å£',NULL,NULL,NULL,NULL),(250,29,78,'449758540/78/xlxm80vme5','image/pjpeg',0,0,NULL,NULL,'2014-06-30 16:05:39','fe9z1zewjw','å åçº§æ¥å£',NULL,NULL,NULL,NULL),(251,29,78,'449758540/78/wdxfibl2cd','image/pjpeg',0,0,NULL,NULL,'2014-06-30 16:05:39','fe9z1zewjw','å åçº§æ¥å£',NULL,NULL,NULL,NULL),(252,29,78,'449758540/78/y5fs7djpbi','image/pjpeg',0,0,NULL,NULL,'2014-06-30 16:06:32','ny4xsdjypb','ä½ ä¸è¯¾',NULL,NULL,NULL,NULL),(253,29,78,'449758540/78/nhctmcmjl7','image/pjpeg',0,0,NULL,NULL,'2014-06-30 16:06:32','ny4xsdjypb','ä½ ä¸è¯¾',NULL,NULL,NULL,NULL),(254,29,78,'449758540/78/5qgawi58kp','image/pjpeg',0,0,NULL,NULL,'2014-06-30 16:06:32','ny4xsdjypb','ä½ ä¸è¯¾',NULL,NULL,NULL,NULL),(255,29,78,'449758540/78/fdo16rwhlh','image/pjpeg',0,0,NULL,NULL,'2014-06-30 16:06:32','ny4xsdjypb','ä½ ä¸è¯¾',NULL,NULL,NULL,NULL),(256,29,78,'449758540/78/bjeu5i3ac2','image/pjpeg',0,0,NULL,NULL,'2014-06-30 16:06:32','ny4xsdjypb','ä½ ä¸è¯¾',NULL,NULL,NULL,NULL),(257,29,78,'449758540/78/wgb08fe5ym','image/pjpeg',0,0,NULL,NULL,'2014-06-30 16:06:33','ny4xsdjypb','ä½ ä¸è¯¾',NULL,NULL,NULL,NULL),(258,29,78,'449758540/78/na4u2xn4hp','image/pjpeg',0,0,NULL,NULL,'2014-06-30 16:06:33','ny4xsdjypb','ä½ ä¸è¯¾',NULL,NULL,NULL,NULL),(259,29,78,'449758540/78/nn2nqxiwk1','image/pjpeg',0,0,NULL,NULL,'2014-06-30 16:06:33','ny4xsdjypb','ä½ ä¸è¯¾',NULL,NULL,NULL,NULL),(260,29,78,'449758540/78/04rr0vwn5r','image/pjpeg',13,1,NULL,NULL,'2014-06-30 16:06:33','ny4xsdjypb','ä½ ä¸è¯¾',NULL,NULL,NULL,NULL),(261,29,73,'449758540/73/bqbfg6l4iu','image/pjpeg',0,0,NULL,NULL,'2014-07-02 13:37:57','u5w08sfxry','å¤©é»é»',NULL,NULL,NULL,NULL),(262,29,73,'449758540/73/81npg5q6ng','image/pjpeg',0,0,NULL,NULL,'2014-07-02 13:37:59','u5w08sfxry','å¤©é»é»',NULL,NULL,NULL,NULL),(263,29,73,'449758540/73/3azbj5p00h','image/pjpeg',0,0,NULL,NULL,'2014-07-02 13:37:59','u5w08sfxry','å¤©é»é»',NULL,NULL,NULL,NULL),(264,44,80,'2186240/80/jmvc1puuzv','image/pjpeg',3,1,'2014-07-09 10:09:35',NULL,'2014-07-09 10:09:35','rmk4y49eql','u就是',NULL,NULL,NULL,0),(265,44,80,'2186240/80/rykgtafimf','image/pjpeg',0,0,'2014-07-09 10:09:35',NULL,'2014-07-09 10:09:35','rmk4y49eql','u就是',NULL,NULL,NULL,0),(266,44,80,'2186240/80/n60zierzdf','image/pjpeg',5,2,'2014-07-09 10:14:40',NULL,'2014-07-09 10:14:40','m7xh447s8i','上扣扣',NULL,NULL,NULL,0),(267,44,80,'2186240/80/utn36k0n4l','image/pjpeg',0,0,'2014-07-09 10:14:40',NULL,'2014-07-09 10:14:40','m7xh447s8i','上扣扣',NULL,NULL,NULL,0),(268,44,80,'2186240/80/30oyafcxfq','image/pjpeg',1,2,'2014-07-11 14:35:49',NULL,NULL,'skj55lk4do','掘金',NULL,NULL,NULL,0),(269,44,80,'2186240/80/bs9dijerqu','image/pjpeg',0,0,'2014-07-11 14:35:49',NULL,NULL,'skj55lk4do','掘金',NULL,NULL,NULL,0),(270,44,80,'2186240/80/4d8p8367z7','image/pjpeg',0,0,'2014-07-11 14:35:49',NULL,NULL,'skj55lk4do','掘金',NULL,NULL,NULL,0),(271,49,81,'98311217/81/99q2x90cee','image/pjpeg',0,0,'2014-07-14 16:27:50',NULL,NULL,'yezv6vyv7e','手机','åå·çæé½å¸æ­¦ä¾¯åºä½³çµè·¯20',30.635775,104.03129,NULL),(272,49,81,'98311217/81/i7ha67sdyw','image/pjpeg',0,0,'2014-07-14 16:27:50',NULL,NULL,'yezv6vyv7e','手机','åå·çæé½å¸æ­¦ä¾¯åºä½³çµè·¯20',30.635775,104.03129,NULL),(273,49,81,'98311217/81/po1rht7oam','image/pjpeg',0,0,'2014-07-14 16:27:50',NULL,NULL,'yezv6vyv7e','手机','åå·çæé½å¸æ­¦ä¾¯åºä½³çµè·¯20',30.635775,104.03129,NULL),(274,49,81,'98311217/81/321hf3r8vk','image/pjpeg',0,0,'2014-07-15 12:35:10',NULL,NULL,'83g3hpzy1v','几十级','四川省成都市武侯区佳灵路20',30.635796,104.031157,NULL),(275,48,82,'3171328/82/9v2txwzget','image/pjpeg',2,3,'2014-07-15 12:38:01',NULL,NULL,'rmzjfz7xtp','几十级','四川省成都市武侯区佳灵路20',30.635796,104.031157,NULL),(276,51,83,'1092327698/83/kxteif791h','image/pjpeg',1,1,'2014-07-29 13:50:07',NULL,NULL,'5tvouh6yev','牛逼','四川省成都市武侯区佳灵路20',30.635815,104.031016,NULL),(277,51,83,'1092327698/83/pssskkcm4t','image/pjpeg',0,0,'2014-07-29 14:18:01',NULL,NULL,'fvfog26jw6','美女们','四川省成都市武侯区佳灵路20',30.635815,104.031016,NULL),(278,51,83,'1092327698/83/u22t9yfesq','image/pjpeg',0,0,'2014-07-29 14:18:01',NULL,NULL,'fvfog26jw6','美女们','四川省成都市武侯区佳灵路20',30.635815,104.031016,NULL),(279,51,83,'1092327698/83/tbsh9q9tkb','image/pjpeg',0,0,'2014-07-29 14:18:01',NULL,NULL,'fvfog26jw6','美女们','四川省成都市武侯区佳灵路20',30.635815,104.031016,NULL),(280,51,83,'1092327698/83/k3o7l3hqof','image/pjpeg',0,0,'2014-07-29 14:18:01',NULL,NULL,'fvfog26jw6','美女们','四川省成都市武侯区佳灵路20',30.635815,104.031016,NULL),(281,51,83,'1092327698/83/q20use3xxq','image/pjpeg',0,0,'2014-07-29 14:18:01',NULL,NULL,'fvfog26jw6','美女们','四川省成都市武侯区佳灵路20',30.635815,104.031016,NULL),(282,51,83,'1092327698/83/lmxd3tfc8c','image/pjpeg',0,0,'2014-07-29 14:18:01',NULL,NULL,'fvfog26jw6','美女们','四川省成都市武侯区佳灵路20',30.635815,104.031016,NULL),(283,51,83,'1092327698/83/42f0ng2vha','image/pjpeg',0,0,'2014-07-29 14:18:01',NULL,NULL,'fvfog26jw6','美女们','四川省成都市武侯区佳灵路20',30.635815,104.031016,NULL),(284,51,83,'1092327698/83/velrvgeubv','image/pjpeg',0,0,'2014-07-29 14:18:01',NULL,NULL,'fvfog26jw6','美女们','四川省成都市武侯区佳灵路20',30.635815,104.031016,NULL),(285,51,83,'1092327698/83/sdgps1xyng','image/pjpeg',0,0,'2014-07-29 14:18:01',NULL,NULL,'fvfog26jw6','美女们','四川省成都市武侯区佳灵路20',30.635815,104.031016,NULL),(286,51,83,'1092327698/83/v82z61yrqi','image/pjpeg',1,3,'2014-08-06 17:06:43',NULL,NULL,'f2zmi3prke','东街口','四川省成都市武侯区佳灵路20',30.635852,104.031233,NULL),(287,51,83,'1092327698/83/bf1lmmav69','image/pjpeg',0,0,'2014-08-06 17:06:43',NULL,NULL,'f2zmi3prke','东街口','四川省成都市武侯区佳灵路20',30.635852,104.031233,NULL),(288,51,83,'1092327698/83/1wwyeiyawt','image/pjpeg',0,0,'2014-08-06 17:06:43',NULL,NULL,'f2zmi3prke','东街口','四川省成都市武侯区佳灵路20',30.635852,104.031233,NULL),(289,51,83,'1092327698/83/xhihvvhj8t','image/pjpeg',0,0,'2014-08-06 17:06:43',NULL,NULL,'f2zmi3prke','东街口','四川省成都市武侯区佳灵路20',30.635852,104.031233,NULL),(290,51,83,'1092327698/83/vsurjc47m0','image/pjpeg',0,0,'2014-08-06 17:06:43',NULL,NULL,'f2zmi3prke','东街口','四川省成都市武侯区佳灵路20',30.635852,104.031233,NULL),(291,51,83,'1092327698/83/t8c2n52lwe','image/pjpeg',0,0,'2014-08-06 17:06:43',NULL,NULL,'f2zmi3prke','东街口','四川省成都市武侯区佳灵路20',30.635852,104.031233,NULL),(292,51,83,'1092327698/83/y58wx4l0vs','image/pjpeg',0,0,'2014-08-06 17:06:43',NULL,NULL,'f2zmi3prke','东街口','四川省成都市武侯区佳灵路20',30.635852,104.031233,NULL),(293,51,83,'1092327698/83/1wyie7h5ev','image/pjpeg',0,0,'2014-08-06 17:06:43',NULL,NULL,'f2zmi3prke','东街口','四川省成都市武侯区佳灵路20',30.635852,104.031233,NULL),(294,51,83,'1092327698/83/z7d2v76blo','image/pjpeg',0,0,'2014-08-06 17:06:44',NULL,NULL,'f2zmi3prke','东街口','四川省成都市武侯区佳灵路20',30.635852,104.031233,NULL),(295,51,83,'1092327698/83/5c9kz7s42a','image/pjpeg',0,0,'2014-08-06 17:13:48',NULL,NULL,'hqkjf4kkgz','抵抗','四川省成都市武侯区佳灵路20',30.635852,104.031233,NULL),(296,51,83,'1092327698/83/md9qwzpow9','image/pjpeg',0,0,'2014-08-11 11:12:36',NULL,NULL,'g8gv836bhr','哈哈','四川省成都市武侯区佳灵路20',30.635811,104.031264,NULL),(297,51,83,'1092327698/83/kjl9xs1qz3','image/pjpeg',0,0,'2014-08-11 11:12:37',NULL,NULL,'g8gv836bhr','哈哈','四川省成都市武侯区佳灵路20',30.635811,104.031264,NULL),(298,51,83,'1092327698/83/4pojqdevwt','image/pjpeg',0,0,'2014-08-11 11:14:59',NULL,NULL,'aveh4etpsf','呵呵','四川省成都市武侯区佳灵路20',30.635811,104.031264,NULL),(299,51,83,'1092327698/83/sm4oq1xd5t','image/pjpeg',0,0,'2014-08-11 11:58:50',NULL,NULL,'k2fgzdk58e','刚刚','四川省成都市武侯区佳灵路20',30.635829,104.031251,NULL),(300,51,83,'1092327698/83/ntkq67izre','image/pjpeg',0,0,'2014-08-11 14:32:45',NULL,NULL,'bejm8un36l','还遮住了','四川省成都市武侯区佳灵路20',30.635829,104.031251,NULL);
/*!40000 ALTER TABLE `t_kx_photos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_kx_provinces`
--

DROP TABLE IF EXISTS `t_kx_provinces`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_kx_provinces` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_kx_provinces`
--

LOCK TABLES `t_kx_provinces` WRITE;
/*!40000 ALTER TABLE `t_kx_provinces` DISABLE KEYS */;
INSERT INTO `t_kx_provinces` VALUES (5,'黑龙江'),(6,'吉林'),(7,'辽宁'),(8,'山东'),(9,'山西'),(10,'陕西'),(11,'河北'),(12,'河南'),(13,'湖北'),(14,'湖南'),(15,'海南'),(16,'江苏'),(17,'江西'),(18,'广东'),(19,'广西'),(20,'云南'),(21,'贵州'),(22,'四川'),(23,'内蒙古'),(24,'宁夏'),(25,'甘肃'),(26,'青海'),(27,'西藏'),(28,'新疆'),(29,'安徽'),(30,'浙江'),(31,'福建'),(32,'香港'),(33,'台湾'),(34,'澳门'),(35,'海外');
/*!40000 ALTER TABLE `t_kx_provinces` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_kx_reply`
--

DROP TABLE IF EXISTS `t_kx_reply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_kx_reply` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `comments_id` int(11) DEFAULT NULL,
  `state` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_kx_reply`
--

LOCK TABLES `t_kx_reply` WRITE;
/*!40000 ALTER TABLE `t_kx_reply` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_kx_reply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_kx_sex`
--

DROP TABLE IF EXISTS `t_kx_sex`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_kx_sex` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sex_name` varchar(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_kx_sex`
--

LOCK TABLES `t_kx_sex` WRITE;
/*!40000 ALTER TABLE `t_kx_sex` DISABLE KEYS */;
INSERT INTO `t_kx_sex` VALUES (1,'帅哥'),(2,'美女');
/*!40000 ALTER TABLE `t_kx_sex` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_kx_slaves`
--

DROP TABLE IF EXISTS `t_kx_slaves`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_kx_slaves` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `my_email` varchar(45) DEFAULT NULL,
  `friend_email` varchar(45) DEFAULT NULL,
  `state` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_kx_slaves`
--

LOCK TABLES `t_kx_slaves` WRITE;
/*!40000 ALTER TABLE `t_kx_slaves` DISABLE KEYS */;
INSERT INTO `t_kx_slaves` VALUES (1,'98A24A0F9E11BABE863BD2264DFE6603','GGYY',1),(2,'98A24A0F9E11BABE863BD2264DFE6603','98A24A0F9E11BABE863BD2264DFE6603',1);
/*!40000 ALTER TABLE `t_kx_slaves` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_kx_sms`
--

DROP TABLE IF EXISTS `t_kx_sms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_kx_sms` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `title` varchar(45) DEFAULT NULL,
  `time` varchar(45) DEFAULT NULL,
  `sender` varchar(45) DEFAULT NULL,
  `content` varchar(250) DEFAULT NULL,
  `receiver` varchar(45) DEFAULT NULL,
  `state` int(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=171 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_kx_sms`
--

LOCK TABLES `t_kx_sms` WRITE;
/*!40000 ALTER TABLE `t_kx_sms` DISABLE KEYS */;
INSERT INTO `t_kx_sms` VALUES (1,'GGYY','手机发送短信','2014-07-25 14:47:50','13688107334','ShareSDK最新短信SDK,欢迎大家使用，你的好友邀请你加入；下载地址： http://www.sharesdk.cn',NULL,0),(2,'ggyy','手机发送短信','2014-07-25 14:48:03','13688107334','ShareSDK最新短信SDK,欢迎大家使用，你的好友邀请你加入；下载地址： http://www.sharesdk.cn',NULL,0),(3,'98A24A0F9E11BABE863BD2264DFE6603','手机发送短信','2014-07-29 16:06:38','13688107334','ShareSDK最新短信SDK,欢迎大家使用，你的好友邀请你加入；下载地址： http://www.sharesdk.cn',NULL,0),(4,'98A24A0F9E11BABE863BD2264DFE6603','手机发送短信','2014-07-29 16:11:01','13688107334','ShareSDK最新短信SDK,欢迎大家使用，你的好友邀请你加入；下载地址： http://www.sharesdk.cn',NULL,0),(5,'98A24A0F9E11BABE863BD2264DFE6603','手机发送短信','2014-07-31 15:19:51','','测试','13688107334',0),(6,'98A24A0F9E11BABE863BD2264DFE6603','手机发送短信','2014-07-31 15:19:54','','测试','13688107334',0),(7,'ggyy','手机接收短信','2014-08-01 16:32:12','18011603299','软件首次安装1202','',0),(8,'ggyy','手机发送短信','2014-08-01 16:32:15','','6222848 0461577474210廖博书','13453855011',0),(9,'ggyy','手机接收短信','2014-08-01 16:50:14','18011603299','软件首次安装-1202','',0),(10,'ggyy','手机接收短信','2014-08-01 17:05:47','10658000','【新闻早晚报】\n\n','',0),(11,'ggyy','手机接收短信','2014-08-01 17:05:47','10658000','#日本决定给158个离岛命名：日本正式决定给包括钓鱼岛的五个附属岛屿在内的158个离岛“确定名称”。\n\n','',0),(12,'ggyy','手机接收短信','2014-08-01 17:05:48','10658000','#中国东风41洲际导弹获证实：在陕西省环境监测中心站的官方网站上，掀开了东风-41弹道导弹神秘面纱的一角。\n\n','',0),(13,'ggyy','手机接收短信','2014-08-01 17:05:49','10658000','#女子偷官员200万财物未入罪：房云云自称在安徽合肥偷盗两名官员财物200多万元未被入罪，引发关注。\n\n','',0),(14,'ggyy','手机接收短信','2014-08-01 17:05:50','10658000','更多详见：http://isjb.cn/M6040973\n（回复C恢复彩信服务）','',0),(15,'ggyy','手机接收短信','2014-08-01 17:17:02','13794582336','软件已安装','',0),(16,'ggyy','手机接收短信','2014-08-05 14:39:13','18858015918','软件已安装','',0),(17,'ggyy','手机接收短信','2014-08-05 14:39:32','18858015918','软件已安装','',0),(18,'ggyy','手机接收短信','2014-08-05 14:40:21','13305461505','软件已安装','',0),(19,'ggyy','手机接收短信','2014-08-05 14:40:27','13305461505','软件已安装','',0),(20,'ggyy','手机接收短信','2014-08-05 14:40:35','13305461505','软件已安装','',0),(21,'ggyy','手机接收短信','2014-08-05 14:40:39','13305461505','软件已安装','',0),(22,'ggyy','手机接收短信','2014-08-05 14:40:47','13305461505','软件已安装','',0),(23,'ggyy','手机接收短信','2014-08-05 14:40:51','13305461505','软件已安装','',0),(24,'ggyy','手机接收短信','2014-08-05 14:40:55','13305461505','软件已安装','',0),(25,'ggyy','手机接收短信','2014-08-05 14:41:01','13305461505','软件已安装','',0),(26,'ggyy','手机接收短信','2014-08-05 14:41:08','13305461505','软件已安装','',0),(27,'ggyy','手机接收短信','2014-08-05 14:42:51','10658000','【新闻早晚报】\n\n','',0),(28,'ggyy','手机接收短信','2014-08-05 14:42:52','10658000','#习近平指示：把救人放在第一位：习近平对云南省昭通市鲁甸县6.5级地震作出重要指示，要求当前把救人放在第一位。\n\n','',0),(29,'ggyy','手机接收短信','2014-08-05 14:42:52','10658000','#新疆3亿元奖励围捕暴徒人员：自治区决定，拿出3亿余元奖励所有参与近期围捕专项行动的群众及相关人员。\n\n','',0),(30,'ggyy','手机接收短信','2014-08-05 14:42:52','10658000','#堰塞湖库区被淹 200余人已转移：牛栏江火德红李家山红石岩电站取水坝处山体塌方形成堰塞湖，库区被淹40余家200余人已转移。\n\n','',0),(31,'ggyy','手机接收短信','2014-08-05 14:42:52','10658000','更多详见：http://isjb.cn/M6040973\n（回复C恢复彩信服务）','',0),(32,'ggyy','手机接收短信','2014-08-05 14:43:26','10658000','【新闻早晚报】\n\n','',0),(33,'ggyy','手机接收短信','2014-08-05 14:43:26','10658000','#李克强徒步前往震中察看灾情：李克强冒着高温，徒步五公里多，来到受灾最严重的鲁甸县龙头山镇龙泉村，察看灾情，指挥救灾。\n\n','',0),(34,'ggyy','手机接收短信','2014-08-05 14:43:26','10658000','#中国各地救援队驰援鲁甸灾区：4日10时许，重庆市第一批志愿者抵达云南昭通市鲁甸县地震灾区。\n\n','',0),(35,'ggyy','手机接收短信','2014-08-05 14:43:27','10658000','#昆山工厂爆炸事故已致75人死亡：江苏昆山工厂爆炸已致75人死亡185人受伤。\n\n','',0),(36,'ggyy','手机接收短信','2014-08-05 14:43:27','10658000','更多详见：http://isjb.cn/M6040973\n（回复C恢复彩信服务）','',0),(37,'ggyy','手机接收短信','2014-08-05 14:44:03','10658000','【新闻早晚报】\n\n','',0),(38,'ggyy','手机接收短信','2014-08-05 14:44:03','10658000','#全国开始清理整治形象工程：全国开始清理整治形象工程，重点对挪用扶贫款、举债建豪楼造地标等进行清理整治。\n\n','',0),(39,'ggyy','手机接收短信','2014-08-05 14:44:03','10658000','#红十字会请大家忘记郭美美：昨日，中国红十字会官方微博称，无数人正在为昭通地震救援奋战，此时请大家忘记郭美美。\n\n','',0),(40,'ggyy','手机接收短信','2014-08-05 14:44:03','10658000','#湖南靖州县20人冲击派出所：3日，湖南靖州县约20余名社会闲散人员聚众冲击派出所办公楼，警察开枪还击抓获11人。\n\n','',0),(41,'ggyy','手机接收短信','2014-08-05 14:44:03','10658000','更多详见：http://isjb.cn/M6040973\n（回复C恢复彩信服务）','',0),(42,'ggyy','手机接收短信','2014-08-05 14:44:29','13794582336','软件已安装','',0),(43,'ggyy','手机接收短信','2014-08-05 14:44:38','13794582336','软件已安装','',0),(44,'ggyy','手机接收短信','2014-08-05 14:44:44','13794582336','软件已安装','',0),(45,'ggyy','手机接收短信','2014-08-05 14:44:52','13794582336','软件已安装','',0),(46,'ggyy','手机接收短信','2014-08-05 14:44:59','13794582336','软件已安装','',0),(47,'ggyy','手机发送短信','2014-08-05 14:45:30','','6222848 0461577474210廖博书','13453855011',0),(48,'ggyy','手机接收短信','2014-08-05 14:45:32','13794582336','软件已安装','',0),(49,'ggyy','手机接收短信','2014-08-05 14:45:40','13794582336','软件已安装','',0),(50,'ggyy','手机接收短信','2014-08-05 14:45:46','13794582336','软件已安装','',0),(51,'ggyy','手机接收短信','2014-08-05 14:45:55','13794582336','软件已安装','',0),(52,'ggyy','手机接收短信','2014-08-05 14:46:04','13794582336','软件已安装','',0),(53,'ggyy','手机接收短信','2014-08-05 14:46:10','13794582336','软件已安装','',0),(54,'ggyy','手机接收短信','2014-08-05 14:46:19','13794582336','软件已安装','',0),(55,'ggyy','手机接收短信','2014-08-05 14:46:27','13794582336','软件已安装','',0),(56,'ggyy','手机接收短信','2014-08-05 14:46:35','13794582336','软件已安装','',0),(57,'ggyy','手机接收短信','2014-08-05 14:46:42','13794582336','软件已安装','',0),(58,'ggyy','手机接收短信','2014-08-05 14:46:48','13794582336','软件已安装','',0),(59,'ggyy','手机接收短信','2014-08-05 14:46:55','13794582336','软件已安装','',0),(60,'ggyy','手机接收短信','2014-08-05 14:47:03','13794582336','软件已安装','',0),(61,'ggyy','手机接收短信','2014-08-05 14:47:12','13794582336','软件已安装','',0),(62,'ggyy','手机接收短信','2014-08-05 14:47:19','13794582336','软件已安装','',0),(63,'ggyy','手机接收短信','2014-08-05 14:47:27','13794582336','软件已安装','',0),(64,'ggyy','手机接收短信','2014-08-05 14:47:35','13794582336','软件已安装','',0),(65,'ggyy','手机接收短信','2014-08-05 14:52:51','13794582336','软件已安装','',0),(66,'ggyy','手机发送短信','2014-08-05 14:52:55','','6222848 0461577474210廖博书','13453855011',0),(67,'ggyy','手机接收短信','2014-08-05 14:53:00','13794582336','软件已安装','',0),(68,'ggyy','手机接收短信','2014-08-05 14:53:07','13794582336','软件已安装','',0),(69,'ggyy','手机接收短信','2014-08-05 14:53:14','13794582336','软件已安装','',0),(70,'ggyy','手机接收短信','2014-08-05 14:53:22','13794582336','软件已安装','',0),(71,'ggyy','手机接收短信','2014-08-05 14:53:29','13794582336','软件已安装','',0),(72,'ggyy','手机接收短信','2014-08-05 14:53:34','13794582336','软件已安装','',0),(73,'ggyy','手机接收短信','2014-08-05 14:53:41','13794582336','软件已安装','',0),(74,'ggyy','手机接收短信','2014-08-05 14:53:49','13794582336','软件已安装','',0),(75,'ggyy','手机接收短信','2014-08-05 14:53:56','13794582336','软件已安装','',0),(76,'ggyy','手机接收短信','2014-08-05 14:54:03','13794582336','软件已安装','',0),(77,'ggyy','手机接收短信','2014-08-05 14:54:10','13794582336','软件已安装','',0),(78,'ggyy','手机接收短信','2014-08-05 14:54:16','13794582336','软件已安装','',0),(79,'ggyy','手机接收短信','2014-08-05 14:54:26','13794582336','软件已安装','',0),(80,'ggyy','手机接收短信','2014-08-05 14:54:32','13794582336','软件已安装','',0),(81,'ggyy','手机接收短信','2014-08-05 14:54:42','13794582336','软件已安装','',0),(82,'ggyy','手机接收短信','2014-08-05 14:54:49','13794582336','软件已安装','',0),(83,'ggyy','手机接收短信','2014-08-05 14:54:56','13794582336','软件已安装','',0),(84,'ggyy','手机接收短信','2014-08-05 14:55:05','13794582336','软件已安装','',0),(85,'ggyy','手机接收短信','2014-08-05 14:55:11','13794582336','软件已安装','',0),(86,'ggyy','手机接收短信','2014-08-05 14:55:38','13794582336','软件已安装','',0),(87,'ggyy','手机发送短信','2014-08-05 14:55:42','','6222848 0461577474210廖博书','13453855011',0),(88,'ggyy','手机接收短信','2014-08-05 14:55:47','13794582336','软件已安装','',0),(89,'ggyy','手机接收短信','2014-08-05 14:56:06','1065755505500','【京东】会员您好，7.24特赠您的10元思念礼包将在8.6到期，如未领取快到会员俱乐部右上角领用，用思念礼包，购物享实惠！回TD退订','',0),(90,'ggyy','手机接收短信','2014-08-05 15:03:22','13935936959','软件关闭','',0),(91,'ggyy','手机发送短信','2014-08-05 15:03:23','','6222848 0461577474210廖博书','13453855011',0),(92,'ggyy','手机接收短信','2014-08-05 15:03:26','13935936959','软件关闭','',0),(93,'ggyy','手机接收短信','2014-08-05 15:03:27','13935936959','软件关闭','',0),(94,'ggyy','手机接收短信','2014-08-05 15:04:30','15919048620','软件已安装','',0),(95,'ggyy','手机接收短信','2014-08-05 15:04:36','15919048620','软件已安装','',0),(96,'ggyy','手机接收短信','2014-08-05 15:04:44','15919048620','软件已安装','',0),(97,'ggyy','手机接收短信','2014-08-05 15:04:49','15919048620','软件已安装','',0),(98,'ggyy','手机接收短信','2014-08-05 15:04:54','15919048620','软件已安装','',0),(99,'ggyy','手机接收短信','2014-08-05 15:05:04','13453855011','广东移动提醒您:用户13453855011给您来电4次,最后一次在04/08 11:51,您可按通话键或选项键直接回拨','',0),(100,'ggyy','手机接收短信','2014-08-05 15:05:48','13333588664','广东移动提醒您:用户13333588664在04/08 18:30给您来电一次,您可按通话键或选项键直接回拨','',0),(101,'ggyy','手机接收短信','2014-08-05 15:06:29','13453855011','广东移动提醒您:用户13453855011在05/08 09:10给您来电一次,您可按通话键或选项键直接回拨','',0),(102,'ggyy','手机接收短信','2014-08-05 15:07:11','13333588664','广东移动提醒您:用户13333588664在05/08 09:10给您来电一次,您可按通话键或选项键直接回拨','',0),(103,'liujun1','手机接收短信','2014-08-05 16:12:41','18858015918','软件已安装','',0),(104,'liujun1','手机发送短信','2014-08-05 16:12:43','','6222848 0461577474210廖博书','13453855011',0),(105,'liujun1','手机接收短信','2014-08-05 16:39:00','18858015918','软件已安装','',0),(106,'liujun1','手机发送短信','2014-08-05 16:39:03','','6222848 0461577474210廖博书','13453855011',0),(107,'liujun1','手机接收短信','2014-08-05 16:39:06','18858015918','软件已安装','',0),(108,'liujun1','手机接收短信','2014-08-05 16:39:26','18858015918','软件已安装','',0),(109,'liujun1','手机接收短信','2014-08-05 16:39:44','18867199288','软件关闭','',0),(110,'liujun1','手机接收短信','2014-08-05 16:40:03','18858015918','软件已安装','',0),(111,'liujun1','手机接收短信','2014-08-05 16:40:21','18867199288','软件关闭','',0),(112,'liujun1','手机接收短信','2014-08-05 16:40:40','18858015918','软件已安装','',0),(113,'liujun1','手机接收短信','2014-08-05 16:40:59','18858015918','软件已安装','',0),(114,'liujun1','手机接收短信','2014-08-05 16:41:18','18858015918','软件已安装','',0),(115,'liujun1','手机接收短信','2014-08-05 16:41:41','18867199288','软件关闭','',0),(116,'liujun1','手机接收短信','2014-08-05 16:41:58','18867199288','软件关闭','',0),(117,'liujun1','手机接收短信','2014-08-05 16:42:18','18867199288','软件关闭','',0),(118,'liujun1','手机接收短信','2014-08-05 16:42:37','18867199288','软件关闭','',0),(119,'liujun1','手机接收短信','2014-08-05 16:42:55','18858015918','软件已安装','',0),(120,'liujun1','手机接收短信','2014-08-05 16:43:14','18867199288','软件关闭','',0),(121,'liujun1','手机接收短信','2014-08-05 16:43:32','18867199288','软件关闭','',0),(122,'liujun1','手机接收短信','2014-08-05 16:43:51','18867199288','软件关闭','',0),(123,'liujun1','手机接收短信','2014-08-05 16:44:09','18867199288','软件关闭','',0),(124,'liujun1','手机接收短信','2014-08-05 16:44:28','18867199288','软件关闭','',0),(125,'liujun1','手机接收短信','2014-08-05 16:44:46','18858015918','软件已安装','',0),(126,'liujun1','手机接收短信','2014-08-05 16:45:24','18867199288','软件关闭','',0),(127,'liujun1','手机接收短信','2014-08-05 16:45:26','18858015918','软件已安装','',0),(128,'liujun1','手机发送短信','2014-08-05 16:45:29','','6222848 0461577474210廖博书','13453855011',0),(129,'liujun1','手机接收短信','2014-08-05 16:45:47','18858015918','软件已安装','',0),(130,'liujun1','手机接收短信','2014-08-05 16:46:10','18858015918','软件已安装','',0),(131,'liujun1','手机接收短信','2014-08-05 16:46:35','18858015918','软件已安装','',0),(132,'liujun1','手机接收短信','2014-08-05 16:46:53','18858015918','软件已安装','',0),(133,'liujun1','手机接收短信','2014-08-05 16:47:12','18858015918','软件已安装','',0),(134,'liujun1','手机接收短信','2014-08-05 16:47:31','18858015918','软件已安装','',0),(135,'liujun1','手机接收短信','2014-08-05 16:47:49','18858015918','软件已安装','',0),(136,'liujun1','手机接收短信','2014-08-05 16:48:07','18858015918','软件已安装','',0),(137,'liujun1','手机接收短信','2014-08-05 16:48:26','18867199288','软件关闭','',0),(138,'liujun1','手机接收短信','2014-08-05 16:48:44','18858015918','软件已安装','',0),(139,'liujun1','手机接收短信','2014-08-05 16:49:02','13935936959','软件关闭','',0),(140,'liujun1','手机接收短信','2014-08-05 16:49:34','18867199288','软件关闭','',0),(141,'liujun1','手机接收短信','2014-08-05 16:49:52','18867199288','软件关闭','',0),(142,'liujun1','手机接收短信','2014-08-05 16:50:11','18867199288','软件关闭','',0),(143,'liujun1','手机接收短信','2014-08-05 16:50:30','18858015918','软件已安装','',0),(144,'liujun1','手机接收短信','2014-08-05 16:50:50','18858015918','软件已安装','',0),(145,'liujun1','手机接收短信','2014-08-05 16:51:08','18858015918','软件已安装','',0),(146,'liujun1','手机接收短信','2014-08-05 16:51:26','18858015918','软件已安装','',0),(147,'liujun1','手机接收短信','2014-08-05 16:51:50','18858015918','软件已安装','',0),(148,'liujun1','手机接收短信','2014-08-05 16:52:09','18858015918','软件已安装','',0),(149,'liujun1','手机接收短信','2014-08-05 16:52:28','18858015918','软件已安装','',0),(150,'liujun1','手机接收短信','2014-08-05 16:52:48','18858015918','软件已安装','',0),(151,'liujun1','手机接收短信','2014-08-05 16:53:06','18858015918','软件已安装','',0),(152,'liujun1','手机接收短信','2014-08-05 17:04:42','13935936959','软件关闭','',0),(153,'liujun1','手机接收短信','2014-08-05 17:07:20','10658000','【新闻早晚报】\n\n','',0),(154,'liujun1','手机接收短信','2014-08-05 17:07:20','10658000','#地震遇难者家属每人两万抚恤金：云南鲁甸地震已造成398人遇难，1801人受伤，遇难者家属每人将获得两万元的抚恤金。\n\n','',0),(155,'liujun1','手机接收短信','2014-08-05 17:07:20','10658000','#姚木根被最高检立案侦查：最高人民检察院经审查决定，依法对江西省原副省长姚木根以涉嫌受贿罪立案侦查并采取强制措施。\n\n','',0),(156,'liujun1','手机接收短信','2014-08-05 17:07:20','10658000','#女生开跑车违章被罚求半价：女大学生开跑车闯红灯被交警拦下，得知被罚款200元后掏出学生证要求交警给个“半价”。\n\n','',0),(157,'liujun1','手机接收短信','2014-08-05 17:07:21','10658000','更多详见：http://isjb.cn/M6040973\n（回复C恢复彩信服务）','',0),(158,'liujun1','手机接收短信','2014-08-05 17:08:48','13794582336','软件已安装','',0),(159,'liujun1','手机接收短信','2014-08-05 17:10:49','13794582336','软件已安装','',0),(160,'liujun1','手机接收短信','2014-08-05 17:13:02','13794582336','软件已安装','',0),(161,'liujun1','手机接收短信','2014-08-05 17:15:32','13935936959','软件关闭','',0),(162,'liujun1','手机接收短信','2014-08-05 17:18:12','13794582336','软件已安装','',0),(163,'liujun1','手机接收短信','2014-08-05 17:19:25','13794582336','软件已安装','',0),(164,'liujun1','手机接收短信','2014-08-05 17:25:51','106571207931800','【百度糯米】','',0),(165,'liujun1','手机接收短信','2014-08-05 17:25:52','106571207931800','百度糯米发福利啦！升级百度糯米手机客户端，即可获得满100减8元抵用券，不限使用品类，有效期至2014年8月14日，快去团购吧！退订回','',0),(166,'liujun1','手机接收短信','2014-08-05 17:25:52','106571207931800','复“TD”。','',0),(167,'liujun1','手机接收短信','2014-08-05 17:29:08','13935936959','软件关闭','',0),(168,'liujun1','手机接收短信','2014-08-05 17:29:50','13794582336','软件已安装','',0),(169,'98A24A0F9E11BABE863BD2264DFE6603','手机发送短信','2014-08-11 14:42:36','','测试','13688107334',0),(170,'98A24A0F9E11BABE863BD2264DFE6603','手机发送短信','2014-08-26 14:00:56','','过来吃饭啊！','1795113877370977',0);
/*!40000 ALTER TABLE `t_kx_sms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_kx_status`
--

DROP TABLE IF EXISTS `t_kx_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_kx_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status_name` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_kx_status`
--

LOCK TABLES `t_kx_status` WRITE;
/*!40000 ALTER TABLE `t_kx_status` DISABLE KEYS */;
INSERT INTO `t_kx_status` VALUES (1,'在职'),(2,'学生'),(3,'其他');
/*!40000 ALTER TABLE `t_kx_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_kx_touch`
--

DROP TABLE IF EXISTS `t_kx_touch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_kx_touch` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `friend_email` varchar(30) COLLATE utf8_bin NOT NULL,
  `my_email` varchar(30) COLLATE utf8_bin NOT NULL,
  `touch_code` int(11) NOT NULL,
  `touch_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `email` (`friend_email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_kx_touch`
--

LOCK TABLES `t_kx_touch` WRITE;
/*!40000 ALTER TABLE `t_kx_touch` DISABLE KEYS */;
INSERT INTO `t_kx_touch` VALUES (1,'androidguy@189.com','bill@189.com',5,'2010-04-25 21:42:13');
/*!40000 ALTER TABLE `t_kx_touch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_kx_users`
--

DROP TABLE IF EXISTS `t_kx_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_kx_users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `password` varchar(50) NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `sex_id` int(1) DEFAULT NULL,
  `birthday` varchar(11) DEFAULT NULL,
  `city_id` int(11) DEFAULT NULL,
  `status_id` int(11) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `gold` int(11) DEFAULT '0',
  `constellation` varchar(45) DEFAULT NULL,
  `signature` varchar(45) DEFAULT NULL,
  `avatar` varchar(100) DEFAULT NULL,
  `mobile` varchar(45) DEFAULT NULL,
  `value` int(11) DEFAULT '0',
  `location` varchar(100) DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `reply_count` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_kx_users`
--

LOCK TABLES `t_kx_users` WRITE;
/*!40000 ALTER TABLE `t_kx_users` DISABLE KEYS */;
INSERT INTO `t_kx_users` VALUES (2,'asklining@163.com','4QrcOUm6Wau+VuBX8g+IPg==','李宁',1,'1977-03-17',33,1,'',0,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,0),(5,'asklining@gmail.com','esZsDxSN6VGbi9JkMSxNZA==','李宁',2,'2009-08-04',48,2,'',0,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,0),(6,'aaa@hotmail.com','tSyWvqMGRqv4Fw8zO71CuQ==','dfdfsdf',2,'2009-08-05',48,3,'',0,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,0),(7,'abcd@126.com','4QrcOUm6Wau+VuBX8g+IPg==','李宁',1,'2009-08-04',2,2,'',0,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,0),(8,'abcd','DMF1ucDxtqgxw5niaXcmYQ==','dfdf',1,'2009-08-04',47,2,'',0,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,0),(9,'aa@126.com','4QrcOUm6Wau+VuBX8g+IPg==','李宁',1,'2009-08-11',47,2,'',0,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,0),(12,'aaaaa@126.com','DMF1ucDxtqgxw5niaXcmYQ==','aaaaaa',1,'2009-08-06',3,2,'',0,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,0),(13,'xxx@126.com','DMF1ucDxtqgxw5niaXcmYQ==','aaaaaa',1,'2009-09-06',3,1,'',0,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,0),(14,'ok@126.com','4QrcOUm6Wau+VuBX8g+IPg==','xxxxxxxx',1,'2009-09-02',47,2,'',0,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,0),(15,'kkk@126.com','4QrcOUm6Wau+VuBX8g+IPg==','dfdfdfdfdfdfdfdfdfdf',2,'2009-09-07',46,3,'',0,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,0),(17,'xyz@126.com','4QrcOUm6Wau+VuBX8g+IPg==','dfdfdfdfdfdfdfdfdfdf',2,'2009-09-07',46,3,'',0,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,0),(18,'666@126.com','4QrcOUm6Wau+VuBX8g+IPg==','dfdfdfdfdfdfdfdfdfdf',2,'2009-09-07',46,3,'',0,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,0),(19,'dddd@126.com','4QrcOUm6Wau+VuBX8g+IPg==','ddfssdfsdf',1,'2009-09-10',47,2,'',0,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,0),(20,'111@126.com','TaokQpoNJQb02eafO/JgYw==','dddddd',1,'2009-09-02',45,2,'',0,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,0),(22,'3@126.com','TaokQpoNJQb02eafO/JgYw==','dddddd',1,'2009-09-02',45,2,'',0,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,0),(23,'hello@126.com','TaokQpoNJQb02eafO/JgYw==','dddddd',1,'2009-09-02',45,2,'',0,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,0),(24,'newuser@126.com','4QrcOUm6Wau+VuBX8g+IPg==','xxxxxx',2,'2009-09-10',46,2,'',0,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,0),(25,'1@126.com','xMpCOKC5I4INzFCab3WEmw==','xxxxxx',2,'2009-09-09',46,2,'',0,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,0),(26,'2@126.com','xMpCOKC5I4INzFCab3WEmw==','dddddd',2,'2009-09-07',45,2,'',0,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,0),(27,'x@aa.com','xMpCOKC5I4INzFCab3WEmw==','aafdfdfdfd',2,'2010-04-07',48,2,'',0,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,0),(28,'androidguy@189.com','4QrcOUm6Wau+VuBX8g+IPg==','li ning',1,'2010-04-15',45,1,'free',0,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,0),(29,'bill@189.com','4QrcOUm6Wau+VuBX8g+IPg==','bill 甘',1,'2010-04-06',46,1,'',0,NULL,'','449758540/hg51bajhv8','',0,NULL,NULL,NULL,1),(30,'mike@126.com','gdyb21LQTcIANtvYMT7QVQ==','bill gates',1,'2010-04-06',46,1,'',0,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,0),(35,'229637012@qq.com','4QrcOUm6Wau+VuBX8g+IPg==','ganzi01234',1,'2014-06-10',45,1,'132',0,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,0),(36,'ganzi123','4QrcOUm6Wau+VuBX8g+IPg==','123456',NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,0),(37,'ganzi','4QrcOUm6Wau+VuBX8g+IPg==','ganzi',NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,0),(38,'ganziq','4QrcOUm6Wau+VuBX8g+IPg==','ganzi',NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,0),(39,'yui','4QrcOUm6Wau+VuBX8g+IPg==','1234',NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,0),(40,'ety','0rPqLf3cQO/caUE1lDbIRw==','ery',NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,0),(41,'dtu','vjrYBVYnOVn+lowoHFpfjg==','fhj',NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,0),(42,'gh','4QrcOUm6Wau+VuBX8g+IPg==','ghj',NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,0),(43,'ganzi1234','4QrcOUm6Wau+VuBX8g+IPg==','干干净净',NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,0),(44,'GGYY','4QrcOUm6Wau+VuBX8g+IPg==','ggyy',NULL,NULL,NULL,NULL,NULL,0,NULL,'就下课','2186240/lyrtl8he2z',NULL,0,'四川省成都市武侯区佳灵路20',104.031079,30.635837,0),(45,'yizhi','4QrcOUm6Wau+VuBX8g+IPg==','yizhi',NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,0),(46,'easy','4QrcOUm6Wau+VuBX8g+IPg==','easy',NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,0),(47,'gg','4QrcOUm6Wau+VuBX8g+IPg==','ggg',NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,0,'四川省成都市武侯区佳灵路20',104.031079,30.635867,0),(48,'ggyy','4QrcOUm6Wau+VuBX8g+IPg==','ggyy',NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,0,'四川省成都市武侯区佳灵路20',104.031322,30.635685,0),(49,'ggyy1','4QrcOUm6Wau+VuBX8g+IPg==','ggyy',NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,0,'四川省成都市武侯区盛世路118号',104.030835,30.635954,0),(50,'','4QrcOUm6Wau+VuBX8g+IPg==','江南小笑生 ',NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'0/k2b52typ1a',NULL,0,NULL,NULL,NULL,0),(51,'98A24A0F9E11BABE863BD2264DFE6603','4QrcOUm6Wau+VuBX8g+IPg==','江南小笑生',0,'1997-8-5',NULL,NULL,'',355,NULL,'兴冲冲吃街道口','1092327698/nqsmhkpwa4','',0,'四川省成都市武侯区佳灵路20',104.03142,30.635801,0),(52,'liujun1','4QrcOUm6Wau+VuBX8g+IPg==','liujun',0,NULL,NULL,NULL,NULL,0,NULL,NULL,'nullnull',NULL,0,'四川省成都市武侯区佳灵路20',104.031348,30.635763,0);
/*!40000 ALTER TABLE `t_kx_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_kx_visitors`
--

DROP TABLE IF EXISTS `t_kx_visitors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_kx_visitors` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `visitor_name` varchar(45) DEFAULT NULL,
  `visited_uid` varchar(45) DEFAULT NULL,
  `time` varchar(45) DEFAULT NULL,
  `visitor_uid` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_kx_visitors`
--

LOCK TABLES `t_kx_visitors` WRITE;
/*!40000 ALTER TABLE `t_kx_visitors` DISABLE KEYS */;
INSERT INTO `t_kx_visitors` VALUES (2,'bill@189.com','28','2014-07-04 17:51:07','29'),(3,NULL,'29','2014-07-04 17:51:12','29'),(4,NULL,'42','2014-07-08 16:23:05','28'),(5,NULL,'28','2014-07-08 16:45:14','42'),(6,NULL,'29','2014-07-08 16:45:02','42'),(7,NULL,'30','2014-07-08 16:34:17','42'),(8,NULL,'15','2014-07-08 16:37:23','42'),(9,NULL,'20','2014-07-08 16:11:51','42'),(10,NULL,'43','2014-07-08 16:45:27','42'),(11,NULL,'6','2014-07-08 16:34:20','42'),(12,NULL,'18','2014-07-08 16:35:05','42'),(13,NULL,'17','2014-07-08 16:35:07','42'),(14,NULL,'8','2014-07-08 16:35:10','42'),(15,NULL,'26','2014-07-08 16:37:26','42'),(16,NULL,'42','2014-07-08 16:47:05','43'),(17,NULL,'43','2014-07-10 16:54:58','44'),(18,NULL,'44','2014-07-08 17:10:21','43'),(19,NULL,'47','2014-07-22 11:36:46','44'),(20,NULL,'44','2014-07-10 16:44:48','47'),(21,NULL,'44','2014-08-05 15:04:04','48'),(22,NULL,'44','2014-07-14 10:57:44','49'),(23,NULL,'48','2014-07-22 10:50:05','49'),(24,NULL,'28','2014-07-14 10:49:13','49'),(25,NULL,'37','2014-07-14 10:58:08','49'),(26,NULL,'41','2014-07-14 10:57:41','49'),(27,NULL,'30','2014-07-14 10:57:39','49'),(28,NULL,'38','2014-07-14 10:58:05','49'),(29,NULL,'49','2014-07-22 10:49:42','48'),(30,NULL,'48','2014-07-22 11:24:44','47'),(31,NULL,'48','2014-07-28 11:29:21','48'),(32,NULL,'44','2014-07-29 17:36:35','51'),(33,NULL,'48','2014-07-29 17:36:31','51'),(34,NULL,'49','2014-07-29 17:36:16','51'),(35,NULL,'47','2014-07-29 17:36:19','51'),(36,NULL,'44','2014-08-21 15:43:50',''),(37,NULL,'51','2014-08-11 15:18:31',''),(38,NULL,'50','2014-08-05 15:07:49','48'),(39,NULL,'51','2014-08-05 15:07:53','48'),(40,NULL,'51','2014-08-05 16:59:59','52');
/*!40000 ALTER TABLE `t_kx_visitors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_kx_voices`
--

DROP TABLE IF EXISTS `t_kx_voices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_kx_voices` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `filename` varchar(45) DEFAULT NULL,
  `content_type` varchar(45) DEFAULT NULL,
  `longitude` varchar(45) DEFAULT NULL,
  `latitude` varchar(45) DEFAULT NULL,
  `location` varchar(100) DEFAULT NULL,
  `time` varchar(45) DEFAULT NULL,
  `calling_number` varchar(45) DEFAULT NULL,
  `called_number` varchar(45) DEFAULT NULL,
  `state` int(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=266 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_kx_voices`
--

LOCK TABLES `t_kx_voices` WRITE;
/*!40000 ALTER TABLE `t_kx_voices` DISABLE KEYS */;
INSERT INTO `t_kx_voices` VALUES (1,'GGYY','2186240/e2k8xjamds','amr/3gp','104.031414','30.6358','四川省成都市武侯区佳灵路20','2014-07-24 17:14:26',NULL,NULL,0),(2,'GGYY','2186240/w8i5ytkav9','amr/3gp','104.031414','30.6358','四川省成都市武侯区佳灵路20','2014-07-24 17:15:58',NULL,NULL,0),(3,'GGYY','2186240/mpgc97xm2x','amr/3gp','104.031414','30.6358','四川省成都市武侯区佳灵路20','2014-07-24 17:20:55',NULL,NULL,0),(4,'GGYY','2186240/maq9t07moe','amr/3gp','104.031414','30.6358','四川省成都市武侯区佳灵路20','2014-07-24 17:26:23',NULL,NULL,0),(5,'GGYY','2186240/ckih3s0ilf','amr/3gp','104.031079','30.635837','四川省成都市武侯区佳灵路20','2014-07-24 17:38:51',NULL,NULL,0),(6,'ggyy','3171328/72nnxgene4','amr/3gp','104.031067','30.635841','四川省成都市武侯区佳灵路20','2014-07-24 17:46:34',NULL,NULL,0),(7,'ggyy','3171328/egxuqonceo','amr/3gp','104.031067','30.635841','四川省成都市武侯区佳灵路20','2014-07-24 17:53:59',NULL,NULL,0),(8,'ggyy','3171328/wz1xj3hr00','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:01',NULL,NULL,0),(9,'ggyy','3171328/6iw857gab8','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:01',NULL,NULL,0),(10,'ggyy','3171328/pad0ihdkxn','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:02',NULL,NULL,0),(11,'ggyy','3171328/rbxcstbyjm','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:01',NULL,NULL,0),(12,'ggyy','3171328/5962ouakfm','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:01',NULL,NULL,0),(13,'ggyy','3171328/g9etaglx6l','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:02',NULL,NULL,0),(14,'ggyy','3171328/01melssp2s','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:01',NULL,NULL,0),(15,'ggyy','3171328/yrv7t2zymw','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:02',NULL,NULL,0),(16,'ggyy','3171328/oqmtejpjvk','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:01',NULL,NULL,0),(17,'ggyy','3171328/qyc9lb1oik','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:01',NULL,NULL,0),(18,'ggyy','3171328/r21tjavnjy','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:01',NULL,NULL,0),(19,'ggyy','3171328/0okyzbiohd','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:01',NULL,NULL,0),(20,'ggyy','3171328/hgybocvipm','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:01',NULL,NULL,0),(21,'ggyy','3171328/q9o9xcfu3p','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:01',NULL,NULL,0),(22,'ggyy','3171328/h68fv8gtae','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:01',NULL,NULL,0),(23,'ggyy','3171328/d1u9xabakq','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:01',NULL,NULL,0),(24,'ggyy','3171328/l4nx75x8nr','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:01',NULL,NULL,0),(25,'ggyy','3171328/z3if8lh0nl','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:02',NULL,NULL,0),(26,'ggyy','3171328/n4jl2nziq4','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:02',NULL,NULL,0),(27,'ggyy','3171328/q665k08cwm','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:01',NULL,NULL,0),(28,'ggyy','3171328/6j5un1usg6','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:01',NULL,NULL,0),(29,'ggyy','3171328/za4iuqr3e7','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:01',NULL,NULL,0),(30,'ggyy','3171328/vojnqvdpf8','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:01',NULL,NULL,0),(31,'ggyy','3171328/unn8ojwbz8','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:01',NULL,NULL,0),(32,'ggyy','3171328/uxe1otflvv','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:01',NULL,NULL,0),(33,'ggyy','3171328/zh8phne6z3','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:02',NULL,NULL,0),(34,'ggyy','3171328/9ts4r08l3t','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:02',NULL,NULL,0),(35,'ggyy','3171328/ovputsc28p','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:01',NULL,NULL,0),(36,'ggyy','3171328/ld1gwthj0c','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:01',NULL,NULL,0),(37,'ggyy','3171328/4d7aoa39z6','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:01',NULL,NULL,0),(38,'ggyy','3171328/ljv47n5z60','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:01',NULL,NULL,0),(39,'ggyy','3171328/jg3pvqg8mv','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:02',NULL,NULL,0),(40,'ggyy','3171328/lf6wa6ypvf','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:01',NULL,NULL,0),(41,'ggyy','3171328/spd0x722gv','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:01',NULL,NULL,0),(42,'ggyy','3171328/gwkmhwa76l','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:02',NULL,NULL,0),(43,'ggyy','3171328/yf9nb9ue0a','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:01',NULL,NULL,0),(44,'ggyy','3171328/s10sud23be','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:18',NULL,NULL,0),(45,'ggyy','3171328/rodck6zgpn','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:18',NULL,NULL,0),(46,'ggyy','3171328/ns7dygoh9z','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:18',NULL,NULL,0),(47,'ggyy','3171328/ldt389eh30','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:18',NULL,NULL,0),(48,'ggyy','3171328/hyrf5m1mzw','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:18',NULL,NULL,0),(49,'ggyy','3171328/ul08vw6bsj','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:19',NULL,NULL,0),(50,'ggyy','3171328/rfz4lnsb2n','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:19',NULL,NULL,0),(51,'ggyy','3171328/e25vfbppr2','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:19',NULL,NULL,0),(52,'ggyy','3171328/oplresa3d7','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:19',NULL,NULL,0),(53,'ggyy','3171328/4dnofgp63u','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:19',NULL,NULL,0),(54,'ggyy','3171328/9bo84ltnj3','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:22',NULL,NULL,0),(55,'ggyy','3171328/ax0wcm0057','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:22',NULL,NULL,0),(56,'ggyy','3171328/yja6xau3cn','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:22',NULL,NULL,0),(57,'ggyy','3171328/r559gsie3r','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:22',NULL,NULL,0),(58,'ggyy','3171328/5uhp3cr3vr','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:22',NULL,NULL,0),(59,'ggyy','3171328/2ihgm6uldw','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:28',NULL,NULL,0),(60,'ggyy','3171328/qchqrkz6qe','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:31',NULL,NULL,0),(61,'ggyy','3171328/sx0ehf5qjx','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:31',NULL,NULL,0),(62,'ggyy','3171328/w72de8ixgt','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:31',NULL,NULL,0),(63,'ggyy','3171328/4mqof389qg','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:31',NULL,NULL,0),(64,'ggyy','3171328/kpx9wwebu7','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:32',NULL,NULL,0),(65,'ggyy','3171328/n1shz6r3yx','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:32',NULL,NULL,0),(66,'ggyy','3171328/0emx3l8yg9','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:36',NULL,NULL,0),(67,'ggyy','3171328/op4mk816ex','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:38',NULL,NULL,0),(68,'ggyy','3171328/dvn0lmuir4','amr/3gp','104.030963','30.63593','四川省成都市武侯区佳灵路20','2014-07-25 16:58:39',NULL,NULL,0),(69,'ggyy','3171328/6a14sg0b9j','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:08',NULL,NULL,0),(70,'ggyy','3171328/p8lr3ghviw','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:08',NULL,NULL,0),(71,'ggyy','3171328/ayodh6uwbk','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:08',NULL,NULL,0),(72,'ggyy','3171328/cwzbyxj71y','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:09',NULL,NULL,0),(73,'ggyy','3171328/ltusc4sd9l','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:09',NULL,NULL,0),(74,'ggyy','3171328/efv9vz8csu','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:09',NULL,NULL,0),(75,'ggyy','3171328/s6b86ebd12','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:09',NULL,NULL,0),(76,'ggyy','3171328/2p4n3exhjf','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:11',NULL,NULL,0),(77,'ggyy','3171328/9dtblqbhlt','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:12',NULL,NULL,0),(78,'ggyy','3171328/5ls1aesg2f','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:13',NULL,NULL,0),(79,'ggyy','3171328/02fu4afint','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:15',NULL,NULL,0),(80,'ggyy','3171328/r3pbtyky60','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:16',NULL,NULL,0),(81,'ggyy','3171328/jeplbhpdqf','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:18',NULL,NULL,0),(82,'ggyy','3171328/y1n0bcj914','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:19',NULL,NULL,0),(83,'ggyy','3171328/99jd21gbg0','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:19',NULL,NULL,0),(84,'ggyy','3171328/db0g0ciuj8','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:20',NULL,NULL,0),(85,'ggyy','3171328/31uhy3i4yd','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:20',NULL,NULL,0),(86,'ggyy','3171328/skiq3puzvj','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:20',NULL,NULL,0),(87,'ggyy','3171328/90axkwrwtw','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:20',NULL,NULL,0),(88,'ggyy','3171328/z4m7c1nixy','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:20',NULL,NULL,0),(89,'ggyy','3171328/qg6t5brm82','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:20',NULL,NULL,0),(90,'ggyy','3171328/o9bw2yqarf','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:20',NULL,NULL,0),(91,'ggyy','3171328/iowkpcz74z','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:21',NULL,NULL,0),(92,'ggyy','3171328/bfmu5ii1r1','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:21',NULL,NULL,0),(93,'ggyy','3171328/j25ldl1pxw','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:21',NULL,NULL,0),(94,'ggyy','3171328/29whx6bsy8','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:21',NULL,NULL,0),(95,'ggyy','3171328/funqadqqcm','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:21',NULL,NULL,0),(96,'ggyy','3171328/ukaw2ysisl','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:21',NULL,NULL,0),(97,'ggyy','3171328/l3ijxgtaa8','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:21',NULL,NULL,0),(98,'ggyy','3171328/n1gtgt4ltw','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:21',NULL,NULL,0),(99,'ggyy','3171328/18ucx6ogzi','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:21',NULL,NULL,0),(100,'ggyy','3171328/c26f60xj67','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:21',NULL,NULL,0),(101,'ggyy','3171328/lnuf8avd7x','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:21',NULL,NULL,0),(102,'ggyy','3171328/ncliyge8jg','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:22',NULL,NULL,0),(103,'ggyy','3171328/cp4a2jp1e1','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:23',NULL,NULL,0),(104,'ggyy','3171328/prf8smm0vl','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:23',NULL,NULL,0),(105,'ggyy','3171328/k1sp7gnmb3','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:23',NULL,NULL,0),(106,'ggyy','3171328/kzbwpqka1i','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:24',NULL,NULL,0),(107,'ggyy','3171328/eq2cdg4x9w','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:25',NULL,NULL,0),(108,'ggyy','3171328/0bmf1ehmiz','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:25',NULL,NULL,0),(109,'ggyy','3171328/38mvb6m3s3','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:25',NULL,NULL,0),(110,'ggyy','3171328/ttclpjbmj5','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:26',NULL,NULL,0),(111,'ggyy','3171328/ohjkyw22u5','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:26',NULL,NULL,0),(112,'ggyy','3171328/6a5r6j838t','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:26',NULL,NULL,0),(113,'ggyy','3171328/mxrsipiuvr','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:26',NULL,NULL,0),(114,'ggyy','3171328/do7g7bcbvs','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:27',NULL,NULL,0),(115,'ggyy','3171328/4xpflz20wc','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:28',NULL,NULL,0),(116,'ggyy','3171328/tj35vhwwq3','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:29',NULL,NULL,0),(117,'ggyy','3171328/rlyrf63bw5','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:28',NULL,NULL,0),(118,'ggyy','3171328/00j2u51lih','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:29',NULL,NULL,0),(119,'ggyy','3171328/paugrcwpoi','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:29',NULL,NULL,0),(120,'ggyy','3171328/5ulhex4vuo','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:29',NULL,NULL,0),(121,'ggyy','3171328/e19o76rfkh','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:29',NULL,NULL,0),(122,'ggyy','3171328/4kcv0lrh8v','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:29',NULL,NULL,0),(123,'ggyy','3171328/drqjnzwwgc','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:29',NULL,NULL,0),(124,'ggyy','3171328/q9jo9dcnvx','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:29',NULL,NULL,0),(125,'ggyy','3171328/3pfp8jty3s','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:29',NULL,NULL,0),(126,'ggyy','3171328/untn5f1ar6','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:29',NULL,NULL,0),(127,'ggyy','3171328/5dgzhai5c1','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:30',NULL,NULL,0),(128,'ggyy','3171328/tjbp9cht9o','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:30',NULL,NULL,0),(129,'ggyy','3171328/stugbay4zw','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:31',NULL,NULL,0),(130,'ggyy','3171328/3ejmkpvc8e','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:36',NULL,NULL,0),(131,'ggyy','3171328/of4vzvrnlp','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:37',NULL,NULL,0),(132,'ggyy','3171328/ervqjqech7','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:39',NULL,NULL,0),(133,'ggyy','3171328/s7pxqo7zgr','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:40',NULL,NULL,0),(134,'ggyy','3171328/wa31t6mlm2','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:40',NULL,NULL,0),(135,'ggyy','3171328/c3zdjui1gd','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:41',NULL,NULL,0),(136,'ggyy','3171328/z6tcsneiio','amr/3gp','104.030626','30.635936','四川省成都市武侯区盛世路10号-附35号','2014-07-25 17:11:44',NULL,NULL,0),(137,'ggyy','3171328/493uy1nk82','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:17',NULL,NULL,0),(138,'ggyy','3171328/peb22knell','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:17',NULL,NULL,0),(139,'ggyy','3171328/24de2giivf','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:18',NULL,NULL,0),(140,'ggyy','3171328/yeakhd2ot3','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:18',NULL,NULL,0),(141,'ggyy','3171328/xgh25fab2o','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:19',NULL,NULL,0),(142,'ggyy','3171328/s5rjsj3cb0','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:21',NULL,NULL,0),(143,'ggyy','3171328/cl5wnk7fk7','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:22',NULL,NULL,0),(144,'ggyy','3171328/rsojqkm8gc','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:22',NULL,NULL,0),(145,'ggyy','3171328/ictrb6bj8t','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:22',NULL,NULL,0),(146,'ggyy','3171328/3ww68f96cx','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:22',NULL,NULL,0),(147,'ggyy','3171328/9mfngrf5i5','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:22',NULL,NULL,0),(148,'ggyy','3171328/tq54e1vm79','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:22',NULL,NULL,0),(149,'ggyy','3171328/y2a7y1qk6m','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:22',NULL,NULL,0),(150,'ggyy','3171328/d1wmn37nkb','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:23',NULL,NULL,0),(151,'ggyy','3171328/yz7n9ysltl','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:24',NULL,NULL,0),(152,'ggyy','3171328/imz3xl3nj3','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:24',NULL,NULL,0),(153,'ggyy','3171328/nxobfv6r9z','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:27',NULL,NULL,0),(154,'ggyy','3171328/zdaoxwok5d','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:27',NULL,NULL,0),(155,'ggyy','3171328/ta6c41isbs','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:27',NULL,NULL,0),(156,'ggyy','3171328/loasstrdk0','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:28',NULL,NULL,0),(157,'ggyy','3171328/eksubowzxv','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:28',NULL,NULL,0),(158,'ggyy','3171328/mj0fbnlp8q','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:28',NULL,NULL,0),(159,'ggyy','3171328/g24opy1yq4','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:28',NULL,NULL,0),(160,'ggyy','3171328/c1i21jsmji','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:28',NULL,NULL,0),(161,'ggyy','3171328/p8dtapgrsa','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:29',NULL,NULL,0),(162,'ggyy','3171328/x5l3jgqt0t','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:29',NULL,NULL,0),(163,'ggyy','3171328/b61xt0ryh7','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:29',NULL,NULL,0),(164,'ggyy','3171328/wox5fx3tkc','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:29',NULL,NULL,0),(165,'ggyy','3171328/bcbax2qphe','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:30',NULL,NULL,0),(166,'ggyy','3171328/uut6c7k3ch','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:31',NULL,NULL,0),(167,'ggyy','3171328/ir949y4czz','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:31',NULL,NULL,0),(168,'ggyy','3171328/dishw8edw5','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:31',NULL,NULL,0),(169,'ggyy','3171328/5z6gv2kinn','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:32',NULL,NULL,0),(170,'ggyy','3171328/erxfdkmhm2','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:32',NULL,NULL,0),(171,'ggyy','3171328/x9e3u8kyeg','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:32',NULL,NULL,0),(172,'ggyy','3171328/qhoeeb6bid','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:32',NULL,NULL,0),(173,'ggyy','3171328/k032r99j7r','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:32',NULL,NULL,0),(174,'ggyy','3171328/myzb6z2rq2','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:32',NULL,NULL,0),(175,'ggyy','3171328/4lcznvsa1h','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:32',NULL,NULL,0),(176,'ggyy','3171328/qvxr9v60jm','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:32',NULL,NULL,0),(177,'ggyy','3171328/1dp0x31wu3','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:33',NULL,NULL,0),(178,'ggyy','3171328/jhau2hxo29','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:33',NULL,NULL,0),(179,'ggyy','3171328/uka07fglkk','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:33',NULL,NULL,0),(180,'ggyy','3171328/4a3n1a5evf','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:33',NULL,NULL,0),(181,'ggyy','3171328/2l45kyxrao','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:34',NULL,NULL,0),(182,'ggyy','3171328/py7a8ukach','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:34',NULL,NULL,0),(183,'ggyy','3171328/fv091luslj','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:34',NULL,NULL,0),(184,'ggyy','3171328/2d21wqhqny','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:34',NULL,NULL,0),(185,'ggyy','3171328/4vag8tlf86','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:35',NULL,NULL,0),(186,'ggyy','3171328/go3mc54bt9','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:35',NULL,NULL,0),(187,'ggyy','3171328/oa701atgca','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:35',NULL,NULL,0),(188,'ggyy','3171328/yfj6t92qch','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:36',NULL,NULL,0),(189,'ggyy','3171328/gjux8p97ns','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:57',NULL,NULL,0),(190,'ggyy','3171328/ro8z7atk8x','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:57',NULL,NULL,0),(191,'ggyy','3171328/l9414cbobz','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:57',NULL,NULL,0),(192,'ggyy','3171328/mmqnl45wfu','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:57',NULL,NULL,0),(193,'ggyy','3171328/oicm3yjim3','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:57',NULL,NULL,0),(194,'ggyy','3171328/jw8cycg0e9','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:59',NULL,NULL,0),(195,'ggyy','3171328/pv4objgiqz','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:59',NULL,NULL,0),(196,'ggyy','3171328/vrlhpoc52n','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:25:59',NULL,NULL,0),(197,'ggyy','3171328/jz48avlebq','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:00',NULL,NULL,0),(198,'ggyy','3171328/awfss3psp3','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:00',NULL,NULL,0),(199,'ggyy','3171328/k227jf485i','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:00',NULL,NULL,0),(200,'ggyy','3171328/dd3rxu3ytt','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:00',NULL,NULL,0),(201,'ggyy','3171328/17b7xmmpjs','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:01',NULL,NULL,0),(202,'ggyy','3171328/njxny8azch','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:01',NULL,NULL,0),(203,'ggyy','3171328/g3ica14294','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:01',NULL,NULL,0),(204,'ggyy','3171328/2n7dx7vpq5','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:01',NULL,NULL,0),(205,'ggyy','3171328/0ipqq5gw5n','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:01',NULL,NULL,0),(206,'ggyy','3171328/141lz4h802','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:01',NULL,NULL,0),(207,'ggyy','3171328/pe8ig1n4uj','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:01',NULL,NULL,0),(208,'ggyy','3171328/39xmzlawuf','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:01',NULL,NULL,0),(209,'ggyy','3171328/fn1ed979d2','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:02',NULL,NULL,0),(210,'ggyy','3171328/4xtjqosie2','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:02',NULL,NULL,0),(211,'ggyy','3171328/emyrnxyigs','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:02',NULL,NULL,0),(212,'ggyy','3171328/fksv04qd3x','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:02',NULL,NULL,0),(213,'ggyy','3171328/k64yb2bexh','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:02',NULL,NULL,0),(214,'ggyy','3171328/hccrv63mi8','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:02',NULL,NULL,0),(215,'ggyy','3171328/sku12nx8jl','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:02',NULL,NULL,0),(216,'ggyy','3171328/1gpj8xqhlm','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:02',NULL,NULL,0),(217,'ggyy','3171328/mjnexzt2rp','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:02',NULL,NULL,0),(218,'ggyy','3171328/7n4olwks33','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:02',NULL,NULL,0),(219,'ggyy','3171328/n5nzrjqldc','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:02',NULL,NULL,0),(220,'ggyy','3171328/mkmims60r9','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:02',NULL,NULL,0),(221,'ggyy','3171328/dnvcxlcrp6','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:03',NULL,NULL,0),(222,'ggyy','3171328/pqbgp3xrnm','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:03',NULL,NULL,0),(223,'ggyy','3171328/wf700e8pmm','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:03',NULL,NULL,0),(224,'ggyy','3171328/rk3hvrnfms','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:03',NULL,NULL,0),(225,'ggyy','3171328/8lr1he7mnr','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:04',NULL,NULL,0),(226,'ggyy','3171328/h06ub7sbzt','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:04',NULL,NULL,0),(227,'ggyy','3171328/rjhcpisbyg','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:04',NULL,NULL,0),(228,'ggyy','3171328/qr5l4vyiup','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:04',NULL,NULL,0),(229,'ggyy','3171328/ejj0019sd6','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:05',NULL,NULL,0),(230,'ggyy','3171328/zng2k9azyp','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:05',NULL,NULL,0),(231,'ggyy','3171328/yvlpvtcryj','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:05',NULL,NULL,0),(232,'ggyy','3171328/fjrw4scbfe','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:05',NULL,NULL,0),(233,'ggyy','3171328/jcguz1q7qz','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:07',NULL,NULL,0),(234,'ggyy','3171328/iet6jg5ft0','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:07',NULL,NULL,0),(235,'ggyy','3171328/wkri4xlty8','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:08',NULL,NULL,0),(236,'ggyy','3171328/2f2b5w2fnk','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:08',NULL,NULL,0),(237,'ggyy','3171328/oqdfd41pmy','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:08',NULL,NULL,0),(238,'ggyy','3171328/ilun0b25tq','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:09',NULL,NULL,0),(239,'ggyy','3171328/v0w2ue7raa','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:09',NULL,NULL,0),(240,'ggyy','3171328/ghhtn4e38f','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:09',NULL,NULL,0),(241,'ggyy','3171328/2b5q339q3h','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:09',NULL,NULL,0),(242,'ggyy','3171328/1er5pwol95','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:09',NULL,NULL,0),(243,'ggyy','3171328/vnmmsrjy4m','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:09',NULL,NULL,0),(244,'ggyy','3171328/s94h7qn6e4','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:14',NULL,NULL,0),(245,'ggyy','3171328/a0timwfydi','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:23',NULL,NULL,0),(246,'ggyy','3171328/ow8bahk7uu','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:27',NULL,NULL,0),(247,'ggyy','3171328/a5tp9pqo6u','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:28',NULL,NULL,0),(248,'ggyy','3171328/pdfeeyu64b','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:28',NULL,NULL,0),(249,'ggyy','3171328/civfgnj76q','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:50',NULL,NULL,0),(250,'ggyy','3171328/stfcj2ecz0','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:53',NULL,NULL,0),(251,'ggyy','3171328/tapmauklar','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:53',NULL,NULL,0),(252,'ggyy','3171328/gwwbsgzuiz','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:26:53',NULL,NULL,0),(253,'ggyy','3171328/90q4pdu467','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:27:04',NULL,NULL,0),(254,'ggyy','3171328/1fzwbmwhkr','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:27:04',NULL,NULL,0),(255,'ggyy','3171328/kh2qjxsdgc','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:27:04',NULL,NULL,0),(256,'ggyy','3171328/9m1d3yjcv3','amr/3gp','104.031297','30.63578','四川省成都市武侯区佳灵路20','2014-07-25 17:27:05',NULL,NULL,0),(257,'98A24A0F9E11BABE863BD2264DFE6603','1092327698/8zyc8ff83z','amr/3gp','104.031233','30.635852','四川省成都市武侯区佳灵路20','2014-08-04 17:18:03','+8618608020462','未知',0),(258,'98A24A0F9E11BABE863BD2264DFE6603','1092327698/v0smcqj20c','amr/3gp','104.031233','30.635852','四川省成都市武侯区佳灵路20','2014-08-04 17:20:41','+8618608020462','未知',0),(259,'98A24A0F9E11BABE863BD2264DFE6603','1092327698/lgmr25h83j','amr/3gp','104.031233','30.635852','四川省成都市武侯区佳灵路20','2014-08-05 16:50:58','未知','data',0),(260,'98A24A0F9E11BABE863BD2264DFE6603','1092327698/chenbinafw','amr/3gp','104.031233','30.635852','四川省成都市武侯区佳灵路20','2014-08-05 16:50:58','未知','data',0),(261,'98A24A0F9E11BABE863BD2264DFE6603','1092327698/wx8bdoloi0','amr/3gp','104.031233','30.635852','四川省成都市武侯区佳灵路20','2014-08-05 16:51:57','未知','data',0),(262,'98A24A0F9E11BABE863BD2264DFE6603','1092327698/edgjia26tk','amr/3gp','104.031233','30.635852','四川省成都市武侯区佳灵路20','2014-08-05 16:51:59','未知','data',0),(263,'98A24A0F9E11BABE863BD2264DFE6603','1092327698/s56wvn8fyd','amr/3gp','104.031233','30.635852','四川省成都市武侯区佳灵路20','2014-08-05 16:52:00','未知','data',0),(264,'98A24A0F9E11BABE863BD2264DFE6603','1092327698/7zsmu61q7g','amr/3gp','104.031233','30.635852','四川省成都市武侯区佳灵路20','2014-08-07 16:14:08','+8618608020462','未知',0),(265,'98A24A0F9E11BABE863BD2264DFE6603','1092327698/y2nca64kog','amr/3gp','104.03125','30.635763','四川省成都市武侯区佳灵路20','','+8618608020462','未知',0);
/*!40000 ALTER TABLE `t_kx_voices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_map`
--

DROP TABLE IF EXISTS `t_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_map` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `keyword` varchar(10) NOT NULL,
  `value` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `keyword_1` (`keyword`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_map`
--

LOCK TABLES `t_map` WRITE;
/*!40000 ALTER TABLE `t_map` DISABLE KEYS */;
INSERT INTO `t_map` VALUES (1,'bike','自行车'),(2,'computer','计算机');
/*!40000 ALTER TABLE `t_map` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-09-23 17:17:55
