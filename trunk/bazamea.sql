CREATE DATABASE  IF NOT EXISTS `shop4j` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `shop4j`;
-- MySQL dump 10.13  Distrib 5.5.16, for Win32 (x86)
--
-- Host: 127.0.0.1    Database: shop4j
-- ------------------------------------------------------
-- Server version	5.5.28

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
-- Table structure for table `adress`
--

DROP TABLE IF EXISTS `adress`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `adress` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_locality` bigint(20) NOT NULL,
  `street` varchar(20) NOT NULL,
  `number` bigint(20) NOT NULL,
  `staircase` varchar(3) DEFAULT NULL,
  `block` bigint(20) DEFAULT NULL,
  `flat` bigint(20) DEFAULT NULL,
  `id_client` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `adress_locality` (`id_locality`),
  KEY `adress_ibfk_2` (`id_client`),
  CONSTRAINT `adress_ibfk_1` FOREIGN KEY (`id_locality`) REFERENCES `locality` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `adress_ibfk_2` FOREIGN KEY (`id_client`) REFERENCES `client` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `adress`
--

LOCK TABLES `adress` WRITE;
/*!40000 ALTER TABLE `adress` DISABLE KEYS */;
INSERT INTO `adress` VALUES (5,1,'in dependentei',1,'a',1,1,1);
/*!40000 ALTER TABLE `adress` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `id_parent` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_category_id_parent` (`id_parent`),
  CONSTRAINT `category_ibfk_1` FOREIGN KEY (`id_parent`) REFERENCES `category` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Clothes',NULL),(2,'Woman',1),(3,'Men',1),(4,'Children',1),(5,'TShirt',2),(6,'Shoes',2),(7,'Boots',6),(8,'Flip flop',6);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categoryname`
--

DROP TABLE IF EXISTS `categoryname`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categoryname` (
  `id_category` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `language` varchar(45) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id_category`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  KEY `fkk_idx` (`language`),
  CONSTRAINT `fkk` FOREIGN KEY (`language`) REFERENCES `language` (`name`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoryname`
--

LOCK TABLES `categoryname` WRITE;
/*!40000 ALTER TABLE `categoryname` DISABLE KEYS */;
/*!40000 ALTER TABLE `categoryname` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `client` (
  `id` bigint(20) NOT NULL,
  `first_name` varchar(20) NOT NULL,
  `last_name` varchar(20) NOT NULL,
  `phone_number` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `client_ibfk_1` FOREIGN KEY (`id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` VALUES (1,'Iuga','Delia','0765432123');
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client_product`
--

DROP TABLE IF EXISTS `client_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `client_product` (
  `id_client_product` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_command` bigint(20) NOT NULL,
  `id_pcs` bigint(20) NOT NULL,
  `nr_pieces` int(11) NOT NULL,
  `perc_reduction` double DEFAULT NULL,
  `price` double NOT NULL,
  PRIMARY KEY (`id_client_product`),
  KEY `id_prod_col_size` (`id_pcs`),
  KEY `client_product_ibfk_1` (`id_command`),
  CONSTRAINT `client_product_ibfk_1` FOREIGN KEY (`id_command`) REFERENCES `command` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `client_product_ibfk_2` FOREIGN KEY (`id_pcs`) REFERENCES `product_color_size` (`id_pcs`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client_product`
--

LOCK TABLES `client_product` WRITE;
/*!40000 ALTER TABLE `client_product` DISABLE KEYS */;
INSERT INTO `client_product` VALUES (1,14,1,10,0,34),(2,14,2,1,0,12);
/*!40000 ALTER TABLE `client_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `color`
--

DROP TABLE IF EXISTS `color`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `color` (
  `id_color` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(15) NOT NULL,
  `description` varchar(30) NOT NULL,
  `code` varchar(20) NOT NULL,
  PRIMARY KEY (`id_color`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `color`
--

LOCK TABLES `color` WRITE;
/*!40000 ALTER TABLE `color` DISABLE KEYS */;
INSERT INTO `color` VALUES (1,'Red','Red product',''),(2,'Green','Greean product',''),(3,'Blue','Blue product','');
/*!40000 ALTER TABLE `color` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `command`
--

DROP TABLE IF EXISTS `command`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `command` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_date` date NOT NULL,
  `delivery_date` date DEFAULT NULL,
  `id_adress` bigint(20) NOT NULL,
  `id_client` bigint(20) NOT NULL,
  `id_status` bigint(20) NOT NULL,
  `id_operator` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `command_status` (`id_status`),
  KEY `command_operator` (`id_operator`),
  KEY `command_ibfk_2` (`id_client`),
  KEY `command_ibfk_1` (`id_adress`),
  CONSTRAINT `command_ibfk_1` FOREIGN KEY (`id_adress`) REFERENCES `adress` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `command_ibfk_2` FOREIGN KEY (`id_client`) REFERENCES `client` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `command_ibfk_3` FOREIGN KEY (`id_status`) REFERENCES `command_status` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `command_ibfk_4` FOREIGN KEY (`id_operator`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `command`
--

LOCK TABLES `command` WRITE;
/*!40000 ALTER TABLE `command` DISABLE KEYS */;
INSERT INTO `command` VALUES (13,'2014-05-04','2014-06-10',5,1,2,7),(14,'2014-06-07','2014-06-10',5,1,1,7);
/*!40000 ALTER TABLE `command` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `command_status`
--

DROP TABLE IF EXISTS `command_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `command_status` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `status` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `command_status`
--

LOCK TABLES `command_status` WRITE;
/*!40000 ALTER TABLE `command_status` DISABLE KEYS */;
INSERT INTO `command_status` VALUES (1,'In progress'),(2,'delivered');
/*!40000 ALTER TABLE `command_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `country`
--

DROP TABLE IF EXISTS `country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `country` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `country`
--

LOCK TABLES `country` WRITE;
/*!40000 ALTER TABLE `country` DISABLE KEYS */;
INSERT INTO `country` VALUES (1,'Romania');
/*!40000 ALTER TABLE `country` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `county`
--

DROP TABLE IF EXISTS `county`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `county` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `id_country` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `county_country` (`id_country`),
  CONSTRAINT `county_ibfk_1` FOREIGN KEY (`id_country`) REFERENCES `country` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `county`
--

LOCK TABLES `county` WRITE;
/*!40000 ALTER TABLE `county` DISABLE KEYS */;
INSERT INTO `county` VALUES (1,'Brasov',1);
/*!40000 ALTER TABLE `county` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `language`
--

DROP TABLE IF EXISTS `language`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `language` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `language` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `language`
--

LOCK TABLES `language` WRITE;
/*!40000 ALTER TABLE `language` DISABLE KEYS */;
/*!40000 ALTER TABLE `language` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `locality`
--

DROP TABLE IF EXISTS `locality`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `locality` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `id_county` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `locality_county` (`id_county`),
  CONSTRAINT `locality_ibfk_1` FOREIGN KEY (`id_county`) REFERENCES `county` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `locality`
--

LOCK TABLES `locality` WRITE;
/*!40000 ALTER TABLE `locality` DISABLE KEYS */;
INSERT INTO `locality` VALUES (1,'Brasov',1);
/*!40000 ALTER TABLE `locality` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messages`
--

DROP TABLE IF EXISTS `messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `messages` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `subject` varchar(20) NOT NULL,
  `message` varchar(900) NOT NULL,
  `send_date` date NOT NULL,
  `is_replied` bit(1) NOT NULL,
  `id_user` bigint(20) DEFAULT NULL,
  `id_client` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `messages_user` (`id_user`),
  KEY `messages_client` (`id_client`),
  CONSTRAINT `messages_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `messages_ibfk_2` FOREIGN KEY (`id_client`) REFERENCES `client` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messages`
--

LOCK TABLES `messages` WRITE;
/*!40000 ALTER TABLE `messages` DISABLE KEYS */;
INSERT INTO `messages` VALUES (5,'ddd','ddddddddd','2014-06-07','\0',7,1),(7,'size','hello! can you tell me please , when s clothing size will be in stock?','2014-06-08','',7,1);
/*!40000 ALTER TABLE `messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_category` bigint(20) NOT NULL,
  `name` varchar(20) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  `price` float NOT NULL,
  `perc_reduction` float NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `product_category` (`id_category`),
  CONSTRAINT `product_ibfk_1` FOREIGN KEY (`id_category`) REFERENCES `category` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,5,'V TShirt','cotton 100%',20,0),(2,5,'Polo TShirt','',30,0),(3,7,'Macys','',75,0),(4,7,'H & M','',110,0),(5,8,'Sport flip flop','',35.99,0),(6,8,'H & M flip flop','',20,0);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_color`
--

DROP TABLE IF EXISTS `product_color`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_color` (
  `id_prod_col` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_product` bigint(20) NOT NULL,
  `id_color` bigint(20) NOT NULL,
  PRIMARY KEY (`id_prod_col`),
  KEY `id_product` (`id_product`),
  KEY `id_color` (`id_color`),
  CONSTRAINT `product_color_ibfk_1` FOREIGN KEY (`id_product`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `product_color_ibfk_2` FOREIGN KEY (`id_color`) REFERENCES `color` (`id_color`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_color`
--

LOCK TABLES `product_color` WRITE;
/*!40000 ALTER TABLE `product_color` DISABLE KEYS */;
INSERT INTO `product_color` VALUES (1,1,1),(2,1,2),(3,1,3),(4,2,1),(5,2,3);
/*!40000 ALTER TABLE `product_color` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_color_size`
--

DROP TABLE IF EXISTS `product_color_size`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_color_size` (
  `id_pcs` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_prod_col` bigint(20) NOT NULL,
  `nr_pieces` int(11) NOT NULL,
  `id_size` bigint(20) NOT NULL,
  PRIMARY KEY (`id_pcs`),
  KEY `id_prod_col` (`id_prod_col`),
  KEY `id_size` (`id_size`),
  CONSTRAINT `product_color_size_ibfk_1` FOREIGN KEY (`id_prod_col`) REFERENCES `product_color` (`id_prod_col`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `product_color_size_ibfk_2` FOREIGN KEY (`id_size`) REFERENCES `size` (`id_size`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_color_size`
--

LOCK TABLES `product_color_size` WRITE;
/*!40000 ALTER TABLE `product_color_size` DISABLE KEYS */;
INSERT INTO `product_color_size` VALUES (1,1,4,1),(2,2,5,2);
/*!40000 ALTER TABLE `product_color_size` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `returnedorders`
--

DROP TABLE IF EXISTS `returnedorders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `returnedorders` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_command` bigint(20) DEFAULT NULL,
  `returnDate` date NOT NULL,
  `addDate` date DEFAULT NULL,
  `returned` bit(1) NOT NULL,
  `retreived` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_command_UNIQUE` (`id_command`),
  CONSTRAINT `returnedorders_ibfk_1` FOREIGN KEY (`id_command`) REFERENCES `command` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `returnedorders`
--

LOCK TABLES `returnedorders` WRITE;
/*!40000 ALTER TABLE `returnedorders` DISABLE KEYS */;
INSERT INTO `returnedorders` VALUES (1,13,'2014-06-06',NULL,'\0','');
/*!40000 ALTER TABLE `returnedorders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `returnedproducts`
--

DROP TABLE IF EXISTS `returnedproducts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `returnedproducts` (
  `idreturnedproducts` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_command` bigint(20) NOT NULL,
  `id_product` bigint(20) NOT NULL,
  PRIMARY KEY (`idreturnedproducts`),
  UNIQUE KEY `idreturnedproducts_UNIQUE` (`idreturnedproducts`),
  KEY `fk6_idx` (`id_command`),
  KEY `fk5` (`id_product`),
  KEY `fk7_idx` (`id_command`),
  CONSTRAINT `fk5` FOREIGN KEY (`id_product`) REFERENCES `product_color_size` (`id_pcs`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk7` FOREIGN KEY (`id_command`) REFERENCES `returnedorders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `returnedproducts`
--

LOCK TABLES `returnedproducts` WRITE;
/*!40000 ALTER TABLE `returnedproducts` DISABLE KEYS */;
/*!40000 ALTER TABLE `returnedproducts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `size`
--

DROP TABLE IF EXISTS `size`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `size` (
  `id_size` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) NOT NULL,
  `id_cat` bigint(20) NOT NULL,
  PRIMARY KEY (`id_size`),
  KEY `id_cat` (`id_cat`),
  CONSTRAINT `size_ibfk_1` FOREIGN KEY (`id_cat`) REFERENCES `category` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `size`
--

LOCK TABLES `size` WRITE;
/*!40000 ALTER TABLE `size` DISABLE KEYS */;
INSERT INTO `size` VALUES (1,'xs',1),(2,'s',1);
/*!40000 ALTER TABLE `size` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `user_password` char(32) NOT NULL,
  `rolename` varchar(50) NOT NULL,
  `email` varchar(30) NOT NULL,
  `passwordStatus` int(11) NOT NULL,
  `ban` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'delia','delia','client','deliutzzz_23@yahoo.com',1,1),(2,'operator1','op1','operator','gigi2@yahoo.com',0,0),(3,'admin1','admin1','admin','gigi3@yahoo.com',1,0),(4,'superadmin1','superadmin1','superadmin','gigi4@yahoo.com',1,0),(5,'operator2','dd','operator','gigi5@yahoo.com',0,0),(7,'op2','op2','operator','ddd@yahoo.com',0,0),(8,'op1','op1','operator','ccc@yahoo.com',0,0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-06-18 12:47:14
