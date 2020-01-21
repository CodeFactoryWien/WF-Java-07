CREATE DATABASE  IF NOT EXISTS `university` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `university`;
-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: localhost    Database: university
-- ------------------------------------------------------
-- Server version	8.0.18

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
-- Table structure for table `course_events`
--

DROP TABLE IF EXISTS `course_events`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course_events` (
  `course_event_id` int(11) NOT NULL AUTO_INCREMENT,
  `course_id` int(11) NOT NULL,
  `room_id` int(11) NOT NULL,
  `registration_deadline` date NOT NULL,
  PRIMARY KEY (`course_event_id`),
  KEY `course_id` (`course_id`),
  KEY `room_id` (`room_id`),
  CONSTRAINT `course_events_ibfk_2` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`room_id`),
  CONSTRAINT `course_events_ibfk_3` FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_events`
--

LOCK TABLES `course_events` WRITE;
/*!40000 ALTER TABLE `course_events` DISABLE KEYS */;
INSERT INTO `course_events` VALUES (1,1,1,'2020-02-19'),(2,2,5,'2020-08-25'),(3,3,5,'2020-03-12'),(4,4,3,'2020-06-11'),(5,5,2,'2020-03-08'),(6,6,6,'2019-08-13'),(7,7,4,'2020-03-14'),(8,8,3,'2020-06-26'),(9,9,6,'2020-02-18'),(10,10,4,'2020-08-18');
/*!40000 ALTER TABLE `course_events` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course_types`
--

DROP TABLE IF EXISTS `course_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course_types` (
  `course_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `course_type_name` varchar(45) NOT NULL,
  `compulsory_attendance` tinyint(1) NOT NULL,
  PRIMARY KEY (`course_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_types`
--

LOCK TABLES `course_types` WRITE;
/*!40000 ALTER TABLE `course_types` DISABLE KEYS */;
INSERT INTO `course_types` VALUES (1,'Seminar',1),(2,'Lecture',0);
/*!40000 ALTER TABLE `course_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course_units`
--

DROP TABLE IF EXISTS `course_units`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course_units` (
  `course_unit_id` int(11) NOT NULL AUTO_INCREMENT,
  `course_event_id` int(11) NOT NULL,
  `course_unit_date` date NOT NULL,
  `course_unit_time` time NOT NULL,
  PRIMARY KEY (`course_unit_id`) USING BTREE,
  KEY `course_event_id` (`course_event_id`),
  CONSTRAINT `course_units_ibfk_1` FOREIGN KEY (`course_event_id`) REFERENCES `course_events` (`course_event_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_units`
--

LOCK TABLES `course_units` WRITE;
/*!40000 ALTER TABLE `course_units` DISABLE KEYS */;
INSERT INTO `course_units` VALUES (1,1,'2020-02-23','10:30:00'),(2,2,'2020-01-27','17:00:00'),(3,1,'2019-12-15','09:00:00'),(4,4,'2019-12-08','18:00:00'),(5,3,'2019-10-06','16:00:00'),(6,5,'2020-01-05','16:30:00'),(7,6,'2019-11-10','00:00:00'),(8,7,'2020-01-02','10:30:00'),(9,8,'2019-10-21','07:20:00'),(10,9,'2020-01-10','17:00:00'),(11,10,'2019-12-22','18:30:00'),(12,6,'2019-11-26','11:00:00');
/*!40000 ALTER TABLE `course_units` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `courses`
--

DROP TABLE IF EXISTS `courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `courses` (
  `course_id` int(11) NOT NULL AUTO_INCREMENT,
  `course_title` varchar(45) NOT NULL,
  `course_type_id` int(11) NOT NULL,
  `course_description` varchar(1000) NOT NULL,
  `course_module_code` varchar(10) NOT NULL,
  `course_credit_points` tinyint(4) NOT NULL,
  PRIMARY KEY (`course_id`),
  KEY `course_type_id` (`course_type_id`),
  CONSTRAINT `courses_ibfk_1` FOREIGN KEY (`course_type_id`) REFERENCES `course_types` (`course_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses`
--

LOCK TABLES `courses` WRITE;
/*!40000 ALTER TABLE `courses` DISABLE KEYS */;
INSERT INTO `courses` VALUES (1,'Javascript-Types of Data',1,'You deal with different types of information every day. You make decisions about what to do based on this information. Computer programs are no different.','JS-01',3),(2,'Javascript-Arrays',1,'Onedimensial, Twodimensial, Array Methods','JS-02',2),(3,'HTML-Basic',2,'Structure, Tables, Lists,\r\nForms','HTML5-01',2),(4,'CSS-Basic',2,'Anatomy of CSS, Selectors, Display Properties','CSS3-01',2),(5,'Bootstrap-Typography',1,'Headings, Colors, Spacing, Border,\r\nText-alignment and transform,...\r\n','BSTYP-01',3),(6,'Typescipt-Introduction',2,'Types, Let and Const, Destructuring, For Of, Functions','TSINT-01',2),(7,'JAVA-Arrays',2,'Array Variable in Java, Array Literals, Array Lenght','JAVAAR-01',2),(8,'JAVA-If-Statements',1,'Conditional Operators, \r\nComparing Variables and Constants, \r\nChaining if Statements','JAVAIF-01',2),(9,'JAVA-ArrayList',1,'Add and Remove Items, Access an Item,Change an Item, ArrayList Size, Loop Through an ArrayList','JAVAAR-02',3),(10,'JAVA-Interface',2,'Implementing an Interface,\r\nImplementing Multiple Interfaces, Overlapping Method Signatures, Interfaces Variable and Methods','JAVAIN-01',2);
/*!40000 ALTER TABLE `courses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gradings`
--

DROP TABLE IF EXISTS `gradings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gradings` (
  `student_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL,
  `grade` tinyint(4) NOT NULL,
  `grading_comment` text NOT NULL,
  PRIMARY KEY (`student_id`,`course_id`) USING BTREE,
  KEY `course_id` (`course_id`),
  CONSTRAINT `gradings_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`),
  CONSTRAINT `gradings_ibfk_2` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gradings`
--

LOCK TABLES `gradings` WRITE;
/*!40000 ALTER TABLE `gradings` DISABLE KEYS */;
INSERT INTO `gradings` VALUES (1,1,1,'Perfect Work!'),(1,3,4,'You have to learn!'),(2,1,1,'Perfect!'),(2,7,3,'Not that bad!'),(3,7,1,'Congrats!'),(3,9,1,'Wonderful!'),(4,7,4,'Very Bad'),(5,7,2,'It gets better!'),(6,7,1,'Very Good!'),(7,6,2,'Good Work'),(8,9,1,'Very good!'),(9,7,5,'You failed!'),(9,10,3,'Well done.'),(10,1,1,'Your work was fantastic!'),(11,3,1,'Very good'),(12,1,2,'Not That bad'),(12,4,1,'Amazing!'),(12,9,5,'You have to learn!'),(12,10,5,'Failed!'),(13,8,3,'Not that bad!'),(14,2,1,'Very Good'),(16,6,3,'Learn a little bit more'),(17,6,1,'Good Work!'),(18,6,2,'Well done!'),(20,4,1,'Perfect!'),(20,9,1,'Amazing!'),(21,4,3,'Not that bad!'),(21,5,5,'You failed!'),(23,4,2,'Nice Work'),(24,4,5,'Failed'),(25,4,1,'Amazing'),(26,6,3,'No comment'),(26,10,1,'Really good'),(27,5,2,'Well done..'),(27,6,4,'You have to take a look at it '),(30,8,1,'perfect'),(30,9,5,'Failed');
/*!40000 ALTER TABLE `gradings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `professors`
--

DROP TABLE IF EXISTS `professors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `professors` (
  `professor_id` int(11) NOT NULL AUTO_INCREMENT,
  `professor_name` varchar(45) NOT NULL,
  `professor_surname` varchar(45) NOT NULL,
  `professor_email` varchar(45) NOT NULL,
  `professor_phone_number` varchar(45) NOT NULL,
  PRIMARY KEY (`professor_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `professors`
--

LOCK TABLES `professors` WRITE;
/*!40000 ALTER TABLE `professors` DISABLE KEYS */;
INSERT INTO `professors` VALUES (1,'Prof.Mickey','Mouse','mickey.mouse@disney.com','02878924'),(2,'Prof.Minnie','Mouse','minniemouse@disney.com','07872893'),(3,'Prof.Cruella','De Vil','devil@disney.com','0738472893'),(4,'Prof.Thomas','O´Malley','Thomas@disney.com','03862378'),(5,'Prof.Evil','Queen','evilqueen@disney.com','0516375723'),(6,'Prof.Robin','Hood','Hood.robin@disney.com','023872894'),(7,'Prof.Princ','Charming','charming2342@disney.com','047638721'),(8,'Prof.Donald','Duck','duckdonald@disney.com','07384298'),(9,'Prof.Winnie','Pooh','winniethepooh@disney.com','037467832'),(10,'Prof.Shere','Khan','khan.shere@disney.com','03482783'),(11,'Prof.Jiminy','Cricket','cricket.jiminy@disney.com','0283782'),(12,'Prof.Nani','Pelekai','nanipel@disney.com','02837289');
/*!40000 ALTER TABLE `professors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `professors_doing_course_events`
--

DROP TABLE IF EXISTS `professors_doing_course_events`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `professors_doing_course_events` (
  `course_event_id` int(11) NOT NULL,
  `professor_id` int(11) NOT NULL,
  PRIMARY KEY (`course_event_id`,`professor_id`) USING BTREE,
  KEY `course_event_id` (`course_event_id`),
  KEY `professor_id` (`professor_id`),
  CONSTRAINT `professors_doing_course_events_ibfk_1` FOREIGN KEY (`professor_id`) REFERENCES `professors` (`professor_id`),
  CONSTRAINT `professors_doing_course_events_ibfk_2` FOREIGN KEY (`course_event_id`) REFERENCES `course_events` (`course_event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `professors_doing_course_events`
--

LOCK TABLES `professors_doing_course_events` WRITE;
/*!40000 ALTER TABLE `professors_doing_course_events` DISABLE KEYS */;
INSERT INTO `professors_doing_course_events` VALUES (1,2),(2,2),(3,1),(4,5),(5,8),(6,10),(7,3),(8,3),(9,5),(10,12);
/*!40000 ALTER TABLE `professors_doing_course_events` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rooms`
--

DROP TABLE IF EXISTS `rooms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rooms` (
  `room_id` int(11) NOT NULL AUTO_INCREMENT,
  `room_name` varchar(45) NOT NULL,
  PRIMARY KEY (`room_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rooms`
--

LOCK TABLES `rooms` WRITE;
/*!40000 ALTER TABLE `rooms` DISABLE KEYS */;
INSERT INTO `rooms` VALUES (1,'DragonMagic'),(2,'Wonderroom'),(3,'LionKing'),(4,'Aladins Carpet'),(5,'1001 Night'),(6,'Mystery');
/*!40000 ALTER TABLE `rooms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `students`
--

DROP TABLE IF EXISTS `students`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `students` (
  `student_id` int(11) NOT NULL AUTO_INCREMENT,
  `student_name` varchar(45) NOT NULL,
  `student_surname` varchar(45) NOT NULL,
  `student_email` varchar(45) NOT NULL,
  `student_phone_number` varchar(45) NOT NULL,
  PRIMARY KEY (`student_id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `students`
--

LOCK TABLES `students` WRITE;
/*!40000 ALTER TABLE `students` DISABLE KEYS */;
INSERT INTO `students` VALUES (1,'Donald ','Duck','duckidonald@donaldo.com','0742827938'),(2,'Goofy','Goooof','Goof231@goofy.com','0263476238'),(3,'Tick','Duck','TickDuck@Ducki.com','023787289'),(4,'Trick','Duck','TrickiDuck@tricki.com','0627836782'),(5,'Track','Duck','Trackdack@track.com','0647236478'),(6,'Bambi','Deer','DeerBambi@voel.com','063728638'),(7,'Thumber','Rabbit','thumber@rabbit.at','07689235342'),(8,'Tinker','Bell','TinkerBell@peterpan.at','07872893'),(9,'John','Darling','DarlingJohn@peterpan.at','0623476234'),(10,'Wendy ','Darling','DarlingWendy@peterpan.at','036726378'),(11,'Alice','Wonderland','Alicewonder@wonderland.com','06347628'),(12,'Snow ','White','snowwhite@dwarf.com','064376578'),(13,'Sleepy','Dwarf','sleeeepyyy@zzzzz.com','073485854'),(14,'Crumpy','Dwarf','crumpy@grummel.com','034664784'),(15,'Pinocchio','liar','Liar@withlongnoise.com','06237462378'),(16,'Poca','Hontas','Pocahontas@Indian.com','062736842'),(17,'Baloo','the Bear','Ballooo@bear.at','0263742368'),(18,'Dumbo','BigEar','Dumbo@bigear.com','02673628'),(19,'Quasi','Modo','Quasi@Modo.com','07382378'),(20,'Lilo','Stitch','Stitchlilo@lilostitch.at','0267367824'),(21,'Lily','Tiger','tiger@roaar.com','062478623784'),(22,'Happy ','Dwarf','DwarfHappy@hahaha.com','063472683'),(23,'Bashful','Dwarf','Dwarf.Bashful@dwarfi.com','0367823678'),(24,'Sneezy','Dwarf','sneezy@hatschi.com','0246378232'),(25,'Dopey','Dwarf','Dopey@ähmm.com','023762783'),(26,'Cinder','Ella','cinderella@cindi.com','06237618'),(27,'Jaq','Jaq','JaqJaq@mouse,com','038127989'),(28,'Gus','Gus','GusGus@mouse.com','07236784'),(29,'Male','Ficent','maleficent@hexhex.com','037627842'),(30,'Punzel','Ra','rapunzel@punzelra.com','073287221');
/*!40000 ALTER TABLE `students` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `students_attending_course_events`
--

DROP TABLE IF EXISTS `students_attending_course_events`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `students_attending_course_events` (
  `student_id` int(11) NOT NULL,
  `course_event_id` int(11) NOT NULL,
  PRIMARY KEY (`student_id`,`course_event_id`) USING BTREE,
  KEY `student_id` (`student_id`),
  KEY `course_event_id` (`course_event_id`),
  CONSTRAINT `students_attending_course_events_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`),
  CONSTRAINT `students_attending_course_events_ibfk_2` FOREIGN KEY (`course_event_id`) REFERENCES `course_events` (`course_event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `students_attending_course_events`
--

LOCK TABLES `students_attending_course_events` WRITE;
/*!40000 ALTER TABLE `students_attending_course_events` DISABLE KEYS */;
INSERT INTO `students_attending_course_events` VALUES (1,1),(1,3),(2,1),(2,7),(3,7),(3,9),(4,7),(5,7),(6,7),(7,6),(8,9),(9,7),(9,10),(10,1),(11,3),(12,1),(12,4),(12,9),(12,10),(13,8),(14,2),(15,7),(16,6),(17,6),(18,6),(19,7),(20,4),(20,9),(21,4),(21,5),(22,9),(23,2),(23,4),(24,4),(25,4),(25,5),(26,6),(26,10),(27,5),(27,6),(28,8),(29,10),(30,8),(30,9);
/*!40000 ALTER TABLE `students_attending_course_events` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'university'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-01-21 15:33:44
