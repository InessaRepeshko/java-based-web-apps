-- MySQL dump 10.13  Distrib 8.0.39, for Linux (aarch64)
--
-- Host: localhost    Database: webappsrivlab2
-- ------------------------------------------------------
-- Server version	8.0.39

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
-- Table structure for table `entrants`
--

DROP TABLE IF EXISTS `entrants`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `entrants` (
  `birthday` date NOT NULL,
  `gender` bit(1) NOT NULL DEFAULT b'1',
  `is_deleted` bit(1) NOT NULL DEFAULT b'0',
  `rating_score` double NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `case_number` varchar(20) NOT NULL,
  `name` varchar(50) NOT NULL,
  `patronymic` varchar(50) NOT NULL,
  `surname` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKj36ja5h4xym91tmpdw81xno84` (`case_number`),
  CONSTRAINT `entrants_chk_1` CHECK ((`birthday` between _utf8mb4'1914-01-01' and _utf8mb4'2008-01-01')),
  CONSTRAINT `entrants_chk_2` CHECK (((`rating_score` > 120.00) and (`rating_score` <= 200.00))),
  CONSTRAINT `entrants_chk_3` CHECK ((regexp_like(`case_number`,_utf8mb4'^[А-ЩЮЯҐЄІЇ]{1,10}[0-9]{2}-[0-9]{1,4}$',_utf8mb4'c') = 1)),
  CONSTRAINT `entrants_chk_4` CHECK ((regexp_like(`name`,_utf8mb4'^(?=.{1,50}$)[А-ЩЮЯҐЄІЇ](?:[А-ЯҐЄІЇа-яґєіїʼ]*[А-ЯҐЄІЇа-яґєії])?(?:[ -][А-ЩЮЯҐЄІЇ](?:[А-ЯҐЄІЇа-яґєіїʼ]*[А-ЯҐЄІЇа-яґєії])?)*$',_utf8mb4'c') = 1)),
  CONSTRAINT `entrants_chk_5` CHECK ((regexp_like(`patronymic`,_utf8mb4'^(?=.{1,50}$)[А-ЩЮЯҐЄІЇ](?:[А-ЯҐЄІЇа-яґєіїʼ]*[А-ЯҐЄІЇа-яґєії])?(?:[ -][А-ЩЮЯҐЄІЇ](?:[А-ЯҐЄІЇа-яґєіїʼ]*[А-ЯҐЄІЇа-яґєії])?)*$',_utf8mb4'c') = 1)),
  CONSTRAINT `entrants_chk_6` CHECK ((regexp_like(`surname`,_utf8mb4'^(?=.{1,50}$)[А-ЩЮЯҐЄІЇ](?:[А-ЯҐЄІЇа-яґєіїʼ]*[А-ЯҐЄІЇа-яґєії])?(?:[ -][А-ЩЮЯҐЄІЇ](?:[А-ЯҐЄІЇа-яґєіїʼ]*[А-ЯҐЄІЇа-яґєії])?)*$',_utf8mb4'c') = 1))
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entrants`
--

LOCK TABLES `entrants` WRITE;
/*!40000 ALTER TABLE `entrants` DISABLE KEYS */;
INSERT INTO `entrants` (`birthday`, `gender`, `is_deleted`, `rating_score`, `id`, `case_number`, `name`, `patronymic`, `surname`) VALUES ('2005-05-22',_binary '',_binary '\0',168.119,1,'КН22-4814','Олексій','Вікторович','Бабічєв'),('2005-07-16',_binary '',_binary '\0',151.2,2,'КН22-5835','Дмитро','Олександрович','Волков'),('2005-12-03',_binary '\0',_binary '\0',150.01,3,'КН22-5511','Анна','Олексіївна','Гєєнко'),('2005-11-07',_binary '',_binary '\0',161.48,4,'КН22-9299','Микита','Сергійович','Голопьоров'),('2005-12-04',_binary '',_binary '\0',191.941,5,'КН22-9012','Максим','Георгійович','Гриценко'),('2005-10-25',_binary '',_binary '\0',128.349,6,'КН22-6636','Вадим','Віталійович','Жарий'),('2005-06-22',_binary '',_binary '\0',183.626,7,'КН22-5140','Андрій','Володимирович','Жупанов'),('2005-01-15',_binary '',_binary '\0',127.286,8,'КН22-4896','Михайло','Романович','Кльоз'),('2005-10-11',_binary '',_binary '\0',139.736,9,'КН22-4694','Микита','Артемович','Коваль'),('2005-04-10',_binary '',_binary '\0',187.794,10,'КН22-5529','Єгор','Вячеславович','Ковтун'),('2005-03-26',_binary '',_binary '\0',165.604,11,'КН22-6874','Денис','Євгенович','Колесниченко'),('2005-09-05',_binary '',_binary '\0',175.399,12,'КН22-2947','Андрій','Сергійович','Ласкевич'),('2005-11-24',_binary '',_binary '\0',184.473,13,'КН22-3856','Антон','Олександрович','Лукаш'),('2005-05-05',_binary '',_binary '\0',168,14,'КН22-7534','Нікіта','Андрійович','Палій'),('2004-12-12',_binary '',_binary '\0',164.66,15,'КН22-3843','Владислав','Сергійович','Прокопов'),('2005-05-06',_binary '\0',_binary '\0',185.3,16,'КН22-7819','Інесса','Віталіївна','Репешко'),('2004-02-26',_binary '',_binary '\0',125.909,17,'КН22-9115','Андрій','Григорович','Скиба'),('2004-05-19',_binary '\0',_binary '\0',190.298,18,'КН22-3812','Ольга','Юріївна','Цибань'),('2004-10-25',_binary '',_binary '\0',166.847,19,'КН22-1828','Данило','Валерійович','Шевченко'),('2005-01-06',_binary '\0',_binary '\0',168.467,20,'КН22-9514','Катерина','Віталіївна','Антипенко'),('2005-10-01',_binary '\0',_binary '\0',157.631,21,'КН22-2929','Марина','Русланівна','Багрянцева'),('2005-04-24',_binary '',_binary '\0',199.53,22,'КН22-1637','Ярослав','Юрійович','Бондаренко'),('2005-05-17',_binary '',_binary '\0',153.68,23,'КН22-6681','Віктор','Олександрович','Булгаков'),('2005-01-25',_binary '',_binary '\0',170.867,24,'КН22-7557','Михайло','Олександрович','Васильєв'),('2005-03-03',_binary '\0',_binary '\0',171.711,25,'КН22-8759','Дарина','Євгенівна','Деркач'),('2005-01-24',_binary '',_binary '\0',125.771,26,'КН22-7309','Тимур','Ігорович','Ілюхін'),('2005-08-29',_binary '\0',_binary '\0',138.746,27,'КН22-8254','Аліна','Степанівна','Криженко'),('2000-02-25',_binary '',_binary '\0',160.715,28,'КН22-3825','Ярослав','Дмитрович','Посмашний'),('1993-10-04',_binary '\0',_binary '\0',188.304,29,'КН22-8153','Вікторія','Андріївна','Рожкова'),('1997-02-17',_binary '\0',_binary '\0',124.241,30,'КН22-4788','Єлизавета','Олександрівна','Сизоненко'),('1996-06-06',_binary '\0',_binary '',200,31,'КН22-9998','Регіна','Віталіївна','Марчук'),('1996-06-06',_binary '\0',_binary '\0',200,32,'АБВГҐІЇ99-0000','Іван Петро','Петрович Іванович-Оʼбраєн','Микол-Іванович Петренко-Оʼбраєн');
/*!40000 ALTER TABLE `entrants` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `students`
--

DROP TABLE IF EXISTS `students`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `students` (
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  `entrant_id` bigint DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `corporate_email` varchar(123) NOT NULL,
  `funding_type` enum('BUDGET','CONTRACT') NOT NULL,
  `scholarship_status` enum('INCREASED','NONE','ORDINARY') NOT NULL DEFAULT 'NONE',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKng7nfmdg9laddmqsllbe38mkx` (`corporate_email`),
  UNIQUE KEY `UKebgu37lcg62sdwu3n7d7gre0v` (`entrant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `students`
--

LOCK TABLES `students` WRITE;
/*!40000 ALTER TABLE `students` DISABLE KEYS */;
/*!40000 ALTER TABLE `students` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-10-23 10:05:48
