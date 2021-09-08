-- MySQL dump 10.13  Distrib 8.0.17, for Win64 (x86_64)
--
-- Host: localhost    Database: akura
-- ------------------------------------------------------
-- Server version	8.0.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `class`
--

DROP TABLE IF EXISTS `class`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `class` (
  `Class_ID` varchar(100) NOT NULL,
  `Class_Name` varchar(100) DEFAULT NULL,
  `Grade` varchar(100) NOT NULL,
  `Schedule_date` varchar(50) NOT NULL,
  `Sche_time` time NOT NULL,
  `Teacher` varchar(100) NOT NULL,
  `Subject` varchar(100) NOT NULL,
  `Class_Fee` double NOT NULL,
  `Commision_Rate` double NOT NULL,
  PRIMARY KEY (`Class_ID`),
  KEY `class_FK` (`Teacher`),
  KEY `class_FK_1` (`Subject`),
  CONSTRAINT `class_FK` FOREIGN KEY (`Teacher`) REFERENCES `teacher` (`Teacher_ID`),
  CONSTRAINT `class_FK_1` FOREIGN KEY (`Subject`) REFERENCES `subject` (`Subject_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class`
--

LOCK TABLES `class` WRITE;
/*!40000 ALTER TABLE `class` DISABLE KEYS */;
INSERT INTO `class` VALUES ('APPLIEDMATH-21AL-0001','Aplied Maths 21AL','2021AL','Wednesday','08:00:00','JANAKA0001','APMATH0001',1400,20),('APPLIEDMATH0001CLS','Applied Mathematics A/L','2022 A/L','Sunday','08:00:00','janaka0001','apmath0001',1400,20),('BUSINESS0001CLS','Business Studies','2022 A/L','Monday','08:00:00','ramesh0001','bs0001',1500,20),('CHEMISTRY0001CLS','Chemistry A/L','2022 A/L','Thursday','08:00:00','anushka0001','cem0001',2000,20),('ECON0001CLS','Echonomics','2022 A/L','Friday','08:00:00','vijith0001','econ0001',1500,20),('ENGLISH0002CLS','English O/L','11','Monday','08:30:00','SAMAN0002','engLISH0002',1000,15),('PHY0001CLS','Physics A/L','2022 A/L','Tuesday','08:00:00','rohan0001','phy0001',1400,20),('PUREMATH0001CLS','Pure Mathematics A/L','2022 A/L','Sunday','08:00:00','jagoda0001','PUREMATH0001',1400,20);
/*!40000 ALTER TABLE `class` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `enroll`
--

DROP TABLE IF EXISTS `enroll`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `enroll` (
  `Enroll_ID` int(11) NOT NULL AUTO_INCREMENT,
  `Std_Reg_No` varchar(100) NOT NULL,
  `Class` varchar(100) NOT NULL,
  PRIMARY KEY (`Enroll_ID`),
  KEY `enroll_FK` (`Class`),
  KEY `enroll_FK_1` (`Std_Reg_No`),
  CONSTRAINT `enroll_FK` FOREIGN KEY (`Class`) REFERENCES `class` (`Class_ID`),
  CONSTRAINT `enroll_FK_1` FOREIGN KEY (`Std_Reg_No`) REFERENCES `student` (`Reg_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `enroll`
--

LOCK TABLES `enroll` WRITE;
/*!40000 ALTER TABLE `enroll` DISABLE KEYS */;
INSERT INTO `enroll` VALUES (1,'1933442PHD','PUREMATH0001CLS'),(2,'1933442PHD','PUREMATH0001CLS'),(3,'1980181PHD','PUREMATH0001CLS'),(4,'1967592PHD','PUREMATH0001CLS'),(5,'1965247PHD','CHEMISTRY0001CLS');
/*!40000 ALTER TABLE `enroll` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exam`
--

DROP TABLE IF EXISTS `exam`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exam` (
  `Exam_ID` varchar(100) NOT NULL,
  `ExamDate` date NOT NULL,
  `Time` time NOT NULL,
  `Class` varchar(100) NOT NULL,
  PRIMARY KEY (`Exam_ID`),
  KEY `Exam_FK` (`Class`),
  CONSTRAINT `Exam_FK` FOREIGN KEY (`Class`) REFERENCES `class` (`Class_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exam`
--

LOCK TABLES `exam` WRITE;
/*!40000 ALTER TABLE `exam` DISABLE KEYS */;
INSERT INTO `exam` VALUES ('CHEM0001E','2021-06-13','08:00:00','CHEMISTRY0001CLS'),('ECON0001E','2021-06-14','14:00:00','ECON0001CLS');
/*!40000 ALTER TABLE `exam` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mark`
--

DROP TABLE IF EXISTS `mark`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mark` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Reg_ID` varchar(100) NOT NULL,
  `Exam_ID` varchar(100) NOT NULL,
  `Marks` int(11) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mark`
--

LOCK TABLES `mark` WRITE;
/*!40000 ALTER TABLE `mark` DISABLE KEYS */;
INSERT INTO `mark` VALUES (2,'1965247PHD','CHEM0001E',68),(4,'1965247PHD','CHEM0001E',90);
/*!40000 ALTER TABLE `mark` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Reg_ID` varchar(100) NOT NULL,
  `Class` varchar(100) NOT NULL,
  `Payment` double NOT NULL,
  `Month` varchar(45) NOT NULL,
  `Date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  KEY `Class_idx` (`Class`),
  KEY `Reg_ID_idx` (`Reg_ID`),
  CONSTRAINT `Class` FOREIGN KEY (`Class`) REFERENCES `enroll` (`Class`),
  CONSTRAINT `Reg_ID` FOREIGN KEY (`Reg_ID`) REFERENCES `enroll` (`Std_Reg_No`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
INSERT INTO `payment` VALUES (5,'1933442PHD','PUREMATH0001CLS',1400,'2021-April','2021-04-13 10:07:47'),(6,'1933442PHD','PUREMATH0001CLS',1400,'2021-May','2021-05-06 10:07:47'),(7,'1967592PHD','PUREMATH0001CLS',1400,'2021-April','2021-04-13 17:01:26'),(8,'1980181PHD','PUREMATH0001CLS',1400,'2021-June','2021-06-13 17:14:05'),(10,'1980181PHD','PUREMATH0001CLS',1400,'2021-May','2021-05-01 18:30:00'),(11,'1980181PHD','PUREMATH0001CLS',1400,'2021-April','2021-03-31 18:30:00');
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `std_view`
--

DROP TABLE IF EXISTS `std_view`;
/*!50001 DROP VIEW IF EXISTS `std_view`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `std_view` AS SELECT 
 1 AS `Reg_ID`,
 1 AS `Name_with_initials`,
 1 AS `City`,
 1 AS `Gender`,
 1 AS `Address1`,
 1 AS `Address2`,
 1 AS `Mobile_No`,
 1 AS `NIC`,
 1 AS `Grade`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `Reg_ID` varchar(100) NOT NULL,
  `Full_Name` varchar(255) NOT NULL,
  `Name_with_initials` varchar(100) NOT NULL,
  `DoB` varchar(50) NOT NULL,
  `City` varchar(100) NOT NULL,
  `Gender` varchar(100) NOT NULL,
  `Address1` varchar(100) NOT NULL,
  `Address2` varchar(100) DEFAULT NULL,
  `Address3` varchar(100) DEFAULT NULL,
  `Mobile_No` varchar(100) DEFAULT NULL,
  `NIC` varchar(100) NOT NULL,
  `ParentsName` varchar(100) NOT NULL,
  `ParentsMobile` varchar(100) DEFAULT NULL,
  `Grade` varchar(100) NOT NULL,
  `Category` varchar(100) NOT NULL,
  `Stream` varchar(100) DEFAULT NULL,
  `Exam_Year` varchar(10) NOT NULL,
  `School_Name` varchar(255) NOT NULL,
  PRIMARY KEY (`Reg_ID`),
  UNIQUE KEY `NIC` (`NIC`),
  UNIQUE KEY `Reg_ID_UNIQUE` (`Reg_ID`),
  KEY `stdReg_id` (`Reg_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES ('1933094PHD','MAYURI LAKMALI','M.LAKMALI','May 17, 1999','Ambalangoda','FeMale','456/25','Wadumulla Road','','0773254698','1933094','Jayani Uthpala','0714568923','12','Advance Level (A/L)','Technology','2021','G/Karandeniya Central College'),('1933442PHD','THILINI MADHUSHIKA','T.MADUSHIKA','July 11, 2000','Galle','Male','369/S/5','Galle Road','','0773265984','1933442V','Rashmi Anuththara','0717364895','12','Advance Level (A/L)','Commerce','2020','G/Dharmasoka College'),('1935216PHD','UPEKSHA SEWWANDHI','U.SEWWANDHI','May 11, 1999','Balangoda','Male','222/D/8','Wathugedara','','0752413964','1935216V','Chanaka Jayalal','0761587463','13','Advance Level (A/L)','Commerce','2021','G/Krandenita central college'),('1964887PHD','SACHIN KAVINDA','S.KAVINDA','March 10, 1999','Ambalangoda','Male','856/25','Polwattha Road','','0123654871','1964887V','Kamala Kumari','0123654871','12','Advance Level (A/L)','Commerce','2022','Dharmashoka College'),('1965247PHD','UDATYA KUMARA','U.KUMARA','June 28, 1999','Ambalangoda','Male','956/32','Bogahawaththa Road','','0773265981','1965247V','Gihana Chathurangi','0771152321','6','Ordinary Level (O/L)','Physical','2020','G/Dharmasoka College'),('1965590PHD','THIHARA HASHENI','T.HASHENI','October 9, 2000','Ambalangoda','FeMale','566/B/6','Galle Road','','0779851236','1965590V','Thilini Nisansala','0779541124','12','Advance Level (A/L)','Biology','2021','G/Dharmasoka college'),('1966014PHD','HARSHANI CHATHURANGI','H.CHATHURANGI','November 2, 2000','Ambalangoda','FeMale','985/52','kandegoda','','0713265478','1966014V','Ishara Ranasenhe','0715214895','13','Advance Level (A/L)','Technology','2020','G/Dharmasoka Maha Vidyalaya'),('1966057PHD','PIYUMI CHAMILKA','P.CHAMIKA','September 21, 1998','Ambalangoda','FeMale','886/37','Galle Road','','0773359685','1966057V','VIdurangi Himantha','0771156982','12','Advance Level (A/L)','Physical','2020','G/Karandeniya Central College'),('1966278PHD','NADEESHA MADHUSHANI','N.MADHUSHANI','May 26, 2000','karandeniya','FeMale','158/6/k','Mahedanda','','0718954763','1966278V','Kumuduni Kalani','0754896231','12','Advance Level (A/L)','Physical','2021','G/Aluthwala Maha Viddyalaya'),('1967592PHD','KAVINDHI PARTHANA','K.PARTHANA','January 5, 2001','Ambalangoda','FeMale','333/25','Polwaththa Road','','0777145258','1967592V','Samadhi Uthpala','0777321548','8','Ordinary Level (O/L)','Commerce','2021','G/Dewananda College'),('1968106PHD','SHANIKA NISHADI','S.NISHADI','September 14, 2001','Ambalangoda','FeMale','963/45','Wathugedara','','0753248723','1968106V','Sonali Hansika','0719877521','12','Advance Level (A/L)','Commerce','2021','G/Dharmasoka College'),('1971709PHD','CHAMATH RANINDU','C.RANINDU','September 22, 1999','Ambalangoda','Male','785/65','Wathugedara','','0771452369','1971709V','Hansika Siriwardhana','0771458963','12','Advance Level (A/L)','Technology','2020','G/Karandeniya Central College'),('1973304PHD','NISHADHI SANJULA WEERAKOON','N.S.WEERAKOON','May 11, 1999','Colombo','FeMale','197/B/35','Bambalapitiya','','0745896358','1973304V','Kumudhuni Weerakon','0745896358','12','Advance Level (A/L)','Technology','2022','Devananda College'),('1979221PHD','NISALA DILSHAN','N.DILSHAN','October 30, 2001','Ambalangoda','Male','759/36','Polwaththa Road','','0774812362','1979221V','Yasitha Prabath','Mahimi Nimanthika','10','Ordinary Level (O/L)','Commerce','2021','GF/Dewananda College'),('1980181PHD','AMALI IDURANGI','A.IDURANGI','February 22, 2001','Ambalangoda','FeMale','752/26','Polwaththa Road','','0771425893','1980181','Nirosha Tharuka','0751248569','13','Advance Level (A/L)','Technology','2021','G/Karandeniya Central Collge'),('1988115PHD','KASUNI THARUKA','K.THARUKA','July 26, 1999','Ambalangoda','Male','456/H/7','Wathugedara','','0771458792','1988115V','Senuri Sasara','0774879652','13','Advance Level (A/L)','Physical','2021','G/Dewananda College');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subject`
--

DROP TABLE IF EXISTS `subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subject` (
  `Subject_ID` varchar(100) NOT NULL,
  `Subject_Name` varchar(100) NOT NULL,
  `Stream` varchar(100) NOT NULL,
  PRIMARY KEY (`Subject_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subject`
--

LOCK TABLES `subject` WRITE;
/*!40000 ALTER TABLE `subject` DISABLE KEYS */;
INSERT INTO `subject` VALUES ('','',''),('ACC0001','Accounting','A/L'),('APMATH0001','Applied Maths','A/L'),('BS0001','Business Studies','A/L'),('CEM0001','Chemistry','A/L'),('ECON0001','Echonomics','A/L'),('ENGLISH0002','English','O/L'),('HISTORY0002','History','Ordinary Level'),('MATH0002','Mathematics','O/L'),('PHY0001','Physics','A/L'),('PUREMATH0001','Pure Maths','A/L'),('SCIENCE0002','Science','O/L'),('SIN0001','Sinhala','A/L'),('SIN0002','Sinhala','O/L');
/*!40000 ALTER TABLE `subject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacher`
--

DROP TABLE IF EXISTS `teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teacher` (
  `Teacher_ID` varchar(100) NOT NULL,
  `FullName` varchar(200) NOT NULL,
  `Name_with_initials` varchar(100) NOT NULL,
  `Address` varchar(255) NOT NULL,
  `NIC` varchar(100) NOT NULL,
  `Mobile_No` varchar(100) NOT NULL,
  `Other` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Teacher_ID`),
  UNIQUE KEY `NIC` (`NIC`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher`
--

LOCK TABLES `teacher` WRITE;
/*!40000 ALTER TABLE `teacher` DISABLE KEYS */;
INSERT INTO `teacher` VALUES ('ANUSHKA0001','ANUSHKA INDUNIL','A.INDUNIL','Batapola','8645789125V','0769853254',NULL),('JAGODA0001','AJITH JAGODA','A.JAGODA','Galle','54789632547V','0714568957',''),('JANAKA0001','JANAKA ABEWARDHANA','J.ABEWARDHANA','Mathara','7589632514V','0701235897',NULL),('RAMESH0001','RAMESH A PRIYANKARA','R.A.PRIYANKARA','Nindana. Batapola','8456971301V','7890142556',NULL),('ROHAN0001','ROHAN BANDARA','R.BANDARA','Uragasmanhandhiya. Karandeniya','6598746325V','0478956325',''),('SAMAN0002','SAMAN ATHAPATTHU','S.ATHPATTHU','Galle','8056321478V','0769852314',NULL),('VIJITH0001','VIJITH A KUMARA','V.A.KUMARA','Kandhegoda, Ambalangoda','789654123V','0789654123',NULL);
/*!40000 ALTER TABLE `teacher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `timetbl_class`
--

DROP TABLE IF EXISTS `timetbl_class`;
/*!50001 DROP VIEW IF EXISTS `timetbl_class`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `timetbl_class` AS SELECT 
 1 AS `Class_ID`,
 1 AS `Class_Name`,
 1 AS `Grade`,
 1 AS `Schedule_date`,
 1 AS `Sche_time`,
 1 AS `Teacher`,
 1 AS `Subject`,
 1 AS `subjectName`,
 1 AS `teacherName`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Email` varchar(255) NOT NULL,
  `UserName` varchar(100) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `AuthLevel` varchar(45) NOT NULL,
  `FirstName` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `Email` (`Email`),
  UNIQUE KEY `Password` (`Password`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin@gmail.com','admin','21232f297a57a5a743894a0e4a801fc3','Admin','Dilshan'),(2,'user4@gmail.com','user4','3f02ebe3d7929b091e3d8ccfde2f3bc6','User','User4'),(13,'user1@gmail.com','user1','24c9e15e52afc47c225b757e7bee1f9d','User','User01');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Final view structure for view `std_view`
--

/*!50001 DROP VIEW IF EXISTS `std_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `std_view` AS select `student`.`Reg_ID` AS `Reg_ID`,`student`.`Name_with_initials` AS `Name_with_initials`,`student`.`City` AS `City`,`student`.`Gender` AS `Gender`,`student`.`Address1` AS `Address1`,`student`.`Address2` AS `Address2`,`student`.`Mobile_No` AS `Mobile_No`,`student`.`NIC` AS `NIC`,`student`.`Grade` AS `Grade` from `student` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `timetbl_class`
--

/*!50001 DROP VIEW IF EXISTS `timetbl_class`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `timetbl_class` AS select `class`.`Class_ID` AS `Class_ID`,`class`.`Class_Name` AS `Class_Name`,`class`.`Grade` AS `Grade`,`class`.`Schedule_date` AS `Schedule_date`,`class`.`Sche_time` AS `Sche_time`,`class`.`Teacher` AS `Teacher`,`class`.`Subject` AS `Subject`,(select `subject`.`Subject_Name` from `subject` where (`class`.`Subject` = `subject`.`Subject_ID`)) AS `subjectName`,(select `teacher`.`Name_with_initials` from `teacher` where (`class`.`Teacher` = `teacher`.`Teacher_ID`)) AS `teacherName` from `class` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-06-18 12:15:23
