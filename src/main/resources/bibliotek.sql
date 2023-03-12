CREATE DATABASE  IF NOT EXISTS `bibliotek` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `bibliotek`;
-- MySQL dump 10.13  Distrib 8.0.17, for macos10.14 (x86_64)
--
-- Host: 127.0.0.1    Database: bibliotek
-- ------------------------------------------------------
-- Server version	8.0.26

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bog`
--

DROP TABLE IF EXISTS `bog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bog` (
  `bog_id` int NOT NULL AUTO_INCREMENT,
  `titel` varchar(45) DEFAULT NULL,
  `udgivelsesaar` int DEFAULT NULL,
  `forfatter_id` int DEFAULT NULL,
  PRIMARY KEY (`bog_id`),
  KEY `fk_bog_forfatter1_idx` (`forfatter_id`),
  CONSTRAINT `fk_bog_forfatter1` FOREIGN KEY (`forfatter_id`) REFERENCES `forfatter` (`forfatter_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bog`
--

LOCK TABLES `bog` WRITE;
/*!40000 ALTER TABLE `bog` DISABLE KEYS */;
INSERT INTO `bog` VALUES (1,'Den lange rejse',1977,2),(2,'Vintereventyr',1964,1),(3,'Sofies Verden for små filosoffer',1981,3);
/*!40000 ALTER TABLE `bog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bruger`
--

DROP TABLE IF EXISTS `bruger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bruger` (
  `email` varchar(45) NOT NULL,
  `kodeord` varchar(45) NOT NULL,
  `rolle` varchar(10) NOT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bruger`
--

LOCK TABLES `bruger` WRITE;
/*!40000 ALTER TABLE `bruger` DISABLE KEYS */;
INSERT INTO `bruger` VALUES ('a@a.dk','1234','laaner'),('b@b.dk','1234','admin');
/*!40000 ALTER TABLE `bruger` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `forfatter`
--

DROP TABLE IF EXISTS `forfatter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `forfatter` (
  `forfatter_id` int NOT NULL AUTO_INCREMENT,
  `navn` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`forfatter_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `forfatter`
--

LOCK TABLES `forfatter` WRITE;
/*!40000 ALTER TABLE `forfatter` DISABLE KEYS */;
INSERT INTO `forfatter` VALUES (1,'Karen Blixen'),(2,'Johannes V. Jensen'),(3,'Jostein Gaader');
/*!40000 ALTER TABLE `forfatter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `laaner`
--

DROP TABLE IF EXISTS `laaner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `laaner` (
  `laaner_id` int NOT NULL AUTO_INCREMENT,
  `navn` varchar(45) DEFAULT NULL,
  `adresse` varchar(45) DEFAULT NULL,
  `postnr` int DEFAULT NULL,
  PRIMARY KEY (`laaner_id`),
  KEY `fk_laaner_postnummer_idx` (`postnr`),
  CONSTRAINT `fk_laaner_postnummer` FOREIGN KEY (`postnr`) REFERENCES `postnummer` (`postnr`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `laaner`
--

LOCK TABLES `laaner` WRITE;
/*!40000 ALTER TABLE `laaner` DISABLE KEYS */;
INSERT INTO `laaner` VALUES (1,'Peter Jensen','Ringgaden 10',7500),(2,'Mathilde Nielsen','Østervej 22',7500),(3,'Mattias Bruun','Ellevang 12',7490),(4,'Ole Jensen','Ahlgade 48',7500);
/*!40000 ALTER TABLE `laaner` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `postnummer`
--

DROP TABLE IF EXISTS `postnummer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `postnummer` (
  `postnr` int NOT NULL,
  `by` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`postnr`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `postnummer`
--

LOCK TABLES `postnummer` WRITE;
/*!40000 ALTER TABLE `postnummer` DISABLE KEYS */;
INSERT INTO `postnummer` VALUES (7490,'Aulum'),(7500,'Hobro'),(8520,'Lystrup');
/*!40000 ALTER TABLE `postnummer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `udlaan`
--

DROP TABLE IF EXISTS `udlaan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `udlaan` (
  `bog_id` int NOT NULL,
  `laaner_id` int NOT NULL,
  `dato` date DEFAULT NULL,
  PRIMARY KEY (`bog_id`,`laaner_id`),
  KEY `fk_udlaan_laaner1_idx` (`laaner_id`),
  CONSTRAINT `fk_udlaan_bog1` FOREIGN KEY (`bog_id`) REFERENCES `bog` (`bog_id`),
  CONSTRAINT `fk_udlaan_laaner1` FOREIGN KEY (`laaner_id`) REFERENCES `laaner` (`laaner_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `udlaan`
--

LOCK TABLES `udlaan` WRITE;
/*!40000 ALTER TABLE `udlaan` DISABLE KEYS */;
INSERT INTO `udlaan` VALUES (1,1,'2000-11-21'),(1,2,'2000-11-24'),(2,1,'2000-11-21'),(2,3,'2000-11-25'),(3,1,'2000-11-21'),(3,2,'2000-11-24');
/*!40000 ALTER TABLE `udlaan` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-03-16 12:21:22
