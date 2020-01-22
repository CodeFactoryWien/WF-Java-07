-- MariaDB dump 10.17  Distrib 10.4.10-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: university
-- ------------------------------------------------------
-- Server version	10.4.10-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `course_events`
--

DROP TABLE IF EXISTS `course_events`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `course_events` (
  `course_event_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL,
  `room_id` int(11) NOT NULL,
  `registration_deadline` date DEFAULT NULL,
  PRIMARY KEY (`course_event_id`),
  KEY `fk_course_events_courses1_idx` (`course_id`),
  KEY `fk_course_events_rooms1_idx` (`room_id`),
  CONSTRAINT `course_events_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`),
  CONSTRAINT `fk_course_events_rooms1` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`room_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_events`
--

LOCK TABLES `course_events` WRITE;
/*!40000 ALTER TABLE `course_events` DISABLE KEYS */;
INSERT INTO `course_events` VALUES (1,1,1,'2020-03-03');
/*!40000 ALTER TABLE `course_events` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course_types`
--

DROP TABLE IF EXISTS `course_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `course_types` (
  `course_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `course_type_name` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `compulsory_attendance` tinyint(1) DEFAULT 0,
  PRIMARY KEY (`course_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_types`
--

LOCK TABLES `course_types` WRITE;
/*!40000 ALTER TABLE `course_types` DISABLE KEYS */;
INSERT INTO `course_types` VALUES (1,'lecture',0),(2,'seminar',1);
/*!40000 ALTER TABLE `course_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course_units`
--

DROP TABLE IF EXISTS `course_units`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `course_units` (
  `course_unit_id` int(11) NOT NULL,
  `course_event_id` int(11) DEFAULT NULL,
  `course_unit_date` date NOT NULL,
  `course_unit_time` time DEFAULT NULL,
  PRIMARY KEY (`course_unit_id`),
  KEY `course_event_id` (`course_event_id`),
  CONSTRAINT `course_units_ibfk_1` FOREIGN KEY (`course_event_id`) REFERENCES `course_events` (`course_event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_units`
--

LOCK TABLES `course_units` WRITE;
/*!40000 ALTER TABLE `course_units` DISABLE KEYS */;
INSERT INTO `course_units` VALUES (1,1,'2020-03-07','14:30:00'),(2,1,'2020-03-14','14:30:00'),(3,1,'2020-03-21','14:30:00');
/*!40000 ALTER TABLE `course_units` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `courses`
--

DROP TABLE IF EXISTS `courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `courses` (
  `course_id` int(11) NOT NULL AUTO_INCREMENT,
  `course_title` varchar(45) NOT NULL,
  `course_type_id` int(11) DEFAULT NULL,
  `course_description` varchar(1000) DEFAULT NULL,
  `course_module_code` varchar(10) DEFAULT NULL,
  `course_credit_points` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`course_id`),
  KEY `course_type_id` (`course_type_id`),
  CONSTRAINT `courses_ibfk_1` FOREIGN KEY (`course_type_id`) REFERENCES `course_types` (`course_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses`
--

LOCK TABLES `courses` WRITE;
/*!40000 ALTER TABLE `courses` DISABLE KEYS */;
INSERT INTO `courses` VALUES (1,'Introduction into Magic Practice',1,'Learn the basics on how to cast magic spels','MGC-1',4),(2,'Killing Two Birds With One Stone',1,'How to do the deed and do it right..........','MKSD-1',7),(4,'Loud Shouting 101',2,'How to be very loud to great effect and benefit','SHHH-3',3);
/*!40000 ALTER TABLE `courses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gradings`
--

DROP TABLE IF EXISTS `gradings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gradings` (
  `student_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL,
  `grade` tinyint(4) DEFAULT NULL,
  `grading_comment` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`student_id`,`course_id`),
  KEY `course_id` (`course_id`),
  CONSTRAINT `gradings_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`),
  CONSTRAINT `gradings_ibfk_2` FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`),
  CONSTRAINT `CONSTRAINT_1` CHECK (`grade` >= 0 and `grade` <= 5)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gradings`
--

LOCK TABLES `gradings` WRITE;
/*!40000 ALTER TABLE `gradings` DISABLE KEYS */;
INSERT INTO `gradings` VALUES (1,1,3,'fucking shit person'),(3,1,5,'Dude sucks deak');
/*!40000 ALTER TABLE `gradings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `professors`
--

DROP TABLE IF EXISTS `professors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `professors` (
  `professor_id` int(11) NOT NULL,
  `professor_name` varchar(45) NOT NULL,
  `professor_surname` varchar(45) NOT NULL,
  `professor_email` varchar(45) DEFAULT NULL,
  `professor_phone_number` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`professor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `professors`
--

LOCK TABLES `professors` WRITE;
/*!40000 ALTER TABLE `professors` DISABLE KEYS */;
INSERT INTO `professors` VALUES (1,'Guggi','Guggenheim','guggi@guggenheim.gugg','923902402'),(2,'Margo','Morkendork','margo@mork.at','218483'),(3,'Fridolin','Fontane','frido@fro.at','12934018204'),(4,'Hildegard','Hart','hildehart@gmail.com','18249121');
/*!40000 ALTER TABLE `professors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `professors_doing_course_events`
--

DROP TABLE IF EXISTS `professors_doing_course_events`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `professors_doing_course_events` (
  `course_event_id` int(11) NOT NULL,
  `professor_id` int(11) NOT NULL,
  PRIMARY KEY (`course_event_id`,`professor_id`),
  KEY `fk_course_events_has_professors_professors1_idx` (`professor_id`),
  KEY `fk_course_events_has_professors_course_events_idx` (`course_event_id`),
  CONSTRAINT `fk_course_events_has_professors_course_events` FOREIGN KEY (`course_event_id`) REFERENCES `course_events` (`course_event_id`),
  CONSTRAINT `fk_course_events_has_professors_professors1` FOREIGN KEY (`professor_id`) REFERENCES `professors` (`professor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `professors_doing_course_events`
--

LOCK TABLES `professors_doing_course_events` WRITE;
/*!40000 ALTER TABLE `professors_doing_course_events` DISABLE KEYS */;
INSERT INTO `professors_doing_course_events` VALUES (1,1),(1,4);
/*!40000 ALTER TABLE `professors_doing_course_events` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rooms`
--

DROP TABLE IF EXISTS `rooms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rooms` (
  `room_id` int(11) NOT NULL,
  `room_name` varchar(45) NOT NULL,
  PRIMARY KEY (`room_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rooms`
--

LOCK TABLES `rooms` WRITE;
/*!40000 ALTER TABLE `rooms` DISABLE KEYS */;
INSERT INTO `rooms` VALUES (1,'Blauer Raum');
/*!40000 ALTER TABLE `rooms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `students`
--

DROP TABLE IF EXISTS `students`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `students` (
  `student_id` int(11) NOT NULL,
  `student_name` varchar(45) NOT NULL,
  `student_surname` varchar(45) NOT NULL,
  `student_email` varchar(45) DEFAULT NULL,
  `student_phone_number` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `students`
--

LOCK TABLES `students` WRITE;
/*!40000 ALTER TABLE `students` DISABLE KEYS */;
INSERT INTO `students` VALUES (1,'Morgo','Pupskopf','pupsi@pupserei.at','2309482039'),(2,'Pepe','Froggenthal','pepe@quack.at','283942348'),(3,'Karolinus','Plenk','karo@gmail.com','83491248124');
/*!40000 ALTER TABLE `students` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `students_attending_course_events`
--

DROP TABLE IF EXISTS `students_attending_course_events`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `students_attending_course_events` (
  `student_id` int(11) NOT NULL,
  `course_event_id` int(11) NOT NULL,
  PRIMARY KEY (`student_id`,`course_event_id`),
  KEY `fk_students_has_course_events_course_events1_idx` (`course_event_id`),
  KEY `fk_students_has_course_events_students1_idx` (`student_id`),
  CONSTRAINT `fk_students_has_course_events_course_events1` FOREIGN KEY (`course_event_id`) REFERENCES `course_events` (`course_event_id`),
  CONSTRAINT `fk_students_has_course_events_students1` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `students_attending_course_events`
--

LOCK TABLES `students_attending_course_events` WRITE;
/*!40000 ALTER TABLE `students_attending_course_events` DISABLE KEYS */;
/*!40000 ALTER TABLE `students_attending_course_events` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-01-22 10:24:29
