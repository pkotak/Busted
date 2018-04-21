-- MySQL dump 10.13  Distrib 5.7.21, for Linux (x86_64)
--
-- Host: team212.chh6n0jvv6vr.us-east-2.rds.amazonaws.com    Database: plagiarismDetector
-- ------------------------------------------------------
-- Server version	5.6.37-log

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
-- Table structure for table `Activitylog`
--

DROP TABLE IF EXISTS `Activitylog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Activitylog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `personid` int(11) NOT NULL,
  `recipient` varchar(45) DEFAULT NULL,
  `summary` varchar(700) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `activitylog_recipient_fk_idx` (`recipient`),
  KEY `activitylog_person_fk_idx` (`personid`),
  CONSTRAINT `activitylog_person_fk` FOREIGN KEY (`personid`) REFERENCES `Person` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `activitylog_recipient_fk` FOREIGN KEY (`recipient`) REFERENCES `Reportrecipient` (`recipient`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Activitylog`
--

LOCK TABLES `Activitylog` WRITE;
/*!40000 ALTER TABLE `Activitylog` DISABLE KEYS */;
/*!40000 ALTER TABLE `Activitylog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Assignment`
--

DROP TABLE IF EXISTS `Assignment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Assignment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `studentid` int(11) NOT NULL,
  `uploaddate` date NOT NULL,
  `duedate` date NOT NULL,
  `metadatamismatch` tinyint(4) NOT NULL,
  `ischecked` tinyint(4) DEFAULT NULL,
  `shaid` varchar(256) DEFAULT NULL,
  `plagiarismcount` int(11) DEFAULT NULL,
  `githublink` varchar(200) DEFAULT NULL,
  `courseid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `assignment_course_fk_idx` (`courseid`),
  KEY `student_person_fk_idx` (`studentid`),
  CONSTRAINT `assignment_course_fk` FOREIGN KEY (`courseid`) REFERENCES `Course` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `student_person_fk` FOREIGN KEY (`studentid`) REFERENCES `Person` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2539 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Assignment`
--

LOCK TABLES `Assignment` WRITE;
/*!40000 ALTER TABLE `Assignment` DISABLE KEYS */;

/*!40000 ALTER TABLE `Assignment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Course`
--

DROP TABLE IF EXISTS `Course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Course` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `semester` varchar(45) NOT NULL,
  `code` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1475 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Course`
--

LOCK TABLES `Course` WRITE;
/*!40000 ALTER TABLE `Course` DISABLE KEYS */;

/*!40000 ALTER TABLE `Course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CourseRole`
--

DROP TABLE IF EXISTS `CourseRole`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CourseRole` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `personid` int(11) NOT NULL,
  `roletype` varchar(45) NOT NULL,
  `courseid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `CourseRole_Person_fk_idx` (`personid`),
  KEY `CourseRole_Role_fk_idx` (`roletype`),
  KEY `CourseRole_Course_fk_idx` (`courseid`),
  CONSTRAINT `CourseRole_Course_fk` FOREIGN KEY (`courseid`) REFERENCES `Course` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `CourseRole_Person_fk` FOREIGN KEY (`personid`) REFERENCES `Person` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `CourseRole_Role_fk` FOREIGN KEY (`roletype`) REFERENCES `Role` (`type`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5442 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CourseRole`
--

LOCK TABLES `CourseRole` WRITE;
/*!40000 ALTER TABLE `CourseRole` DISABLE KEYS */;

/*!40000 ALTER TABLE `CourseRole` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Person`
--

DROP TABLE IF EXISTS `Person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Person` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(45) NOT NULL,
  `lastname` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `phone` varchar(10) DEFAULT NULL,
  `type` varchar(45) NOT NULL,
  `isApproved` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `person_role_enum_idx` (`type`),
  CONSTRAINT `person_role_enum` FOREIGN KEY (`type`) REFERENCES `Role` (`type`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1498 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Person`
--

LOCK TABLES `Person` WRITE;
/*!40000 ALTER TABLE `Person` DISABLE KEYS */;

/*!40000 ALTER TABLE `Person` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Report`
--

DROP TABLE IF EXISTS `Report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Report` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `assignment1` int(11) NOT NULL,
  `assignment2` int(11) NOT NULL,
  `similarityscore` int(11) NOT NULL,
  `downloadlink` varchar(200) NOT NULL,
  `isResolved` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `assignment1_assignment2` (`assignment1`,`assignment2`),
  KEY `report_assignment1_fk_idx` (`assignment1`),
  KEY `report_assignment2_fk_idx` (`assignment2`),
  CONSTRAINT `report_assignment1_fk` FOREIGN KEY (`assignment1`) REFERENCES `Assignment` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `report_assignment2_fk` FOREIGN KEY (`assignment2`) REFERENCES `Assignment` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=24318 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Report`
--

LOCK TABLES `Report` WRITE;
/*!40000 ALTER TABLE `Report` DISABLE KEYS */;

/*!40000 ALTER TABLE `Report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Reportrecipient`
--

DROP TABLE IF EXISTS `Reportrecipient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Reportrecipient` (
  `recipient` varchar(45) NOT NULL,
  PRIMARY KEY (`recipient`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Reportrecipient`
--

LOCK TABLES `Reportrecipient` WRITE;
/*!40000 ALTER TABLE `Reportrecipient` DISABLE KEYS */;

/*!40000 ALTER TABLE `Reportrecipient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Role`
--

DROP TABLE IF EXISTS `Role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Role` (
  `type` varchar(45) NOT NULL,
  PRIMARY KEY (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Role`
--

LOCK TABLES `Role` WRITE;
/*!40000 ALTER TABLE `Role` DISABLE KEYS */;

/*!40000 ALTER TABLE `Role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-17 20:17:11
