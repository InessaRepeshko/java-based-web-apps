-- MySQL dump 10.13  Distrib 8.0.39, for Linux (aarch64)
--
-- Host: localhost    Database: webappsrivlab4
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
-- Current Database: `webappsrivlab4`
--

/*!40000 DROP DATABASE IF EXISTS `webappsrivlab4`*/;

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `webappsrivlab4` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `webappsrivlab4`;

--
-- Table structure for table `entrants`
--

DROP TABLE IF EXISTS `entrants`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `entrants` (
  `birthday` date NOT NULL DEFAULT '2000-01-01',
  `gender` bit(1) NOT NULL DEFAULT b'1',
  `rating_score` double NOT NULL DEFAULT '120.001',
  `id` bigint NOT NULL AUTO_INCREMENT,
  `student_id` bigint DEFAULT NULL,
  `case_number` varchar(20) NOT NULL,
  `name` varchar(50) NOT NULL,
  `patronymic` varchar(50) NOT NULL,
  `surname` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKj36ja5h4xym91tmpdw81xno84` (`case_number`),
  CONSTRAINT `entrants_chk_1` CHECK ((`birthday` between _utf8mb4'1914-01-01' and _utf8mb4'2008-01-01')),
  CONSTRAINT `entrants_chk_2` CHECK (((`rating_score` > 120.000) and (`rating_score` <= 200.000))),
  CONSTRAINT `entrants_chk_3` CHECK ((regexp_like(`case_number`,_utf8mb4'^[А-ЩЮЯҐЄІЇ]{1,10}[0-9]{2}-[0-9]{1,4}$',_utf8mb4'c') = 1)),
  CONSTRAINT `entrants_chk_4` CHECK ((regexp_like(`name`,_utf8mb4'^(?=.{1,50}$)[А-ЩЮЯҐЄІЇ](?:[А-ЯҐЄІЇа-яґєіїʼ]*[А-ЯҐЄІЇа-яґєії])?(?:[ -][А-ЩЮЯҐЄІЇ](?:[А-ЯҐЄІЇа-яґєіїʼ]*[А-ЯҐЄІЇа-яґєії])?)*$',_utf8mb4'c') = 1)),
  CONSTRAINT `entrants_chk_5` CHECK ((regexp_like(`patronymic`,_utf8mb4'^(?=.{1,50}$)[А-ЩЮЯҐЄІЇ](?:[А-ЯҐЄІЇа-яґєіїʼ]*[А-ЯҐЄІЇа-яґєії])?(?:[ -][А-ЩЮЯҐЄІЇ](?:[А-ЯҐЄІЇа-яґєіїʼ]*[А-ЯҐЄІЇа-яґєії])?)*$',_utf8mb4'c') = 1)),
  CONSTRAINT `entrants_chk_6` CHECK ((regexp_like(`surname`,_utf8mb4'^(?=.{1,50}$)[А-ЩЮЯҐЄІЇ](?:[А-ЯҐЄІЇа-яґєіїʼ]*[А-ЯҐЄІЇа-яґєії])?(?:[ -][А-ЩЮЯҐЄІЇ](?:[А-ЯҐЄІЇа-яґєіїʼ]*[А-ЯҐЄІЇа-яґєії])?)*$',_utf8mb4'c') = 1))
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entrants`
--

LOCK TABLES `entrants` WRITE;
/*!40000 ALTER TABLE `entrants` DISABLE KEYS */;
INSERT INTO `entrants` (`birthday`, `gender`, `rating_score`, `id`, `student_id`, `case_number`, `name`, `patronymic`, `surname`) VALUES ('2005-05-22',_binary '',168.111,1,1,'КН22-4814','Олексій','Вікторович','Бабічєв'),('2005-07-16',_binary '',151.202,2,2,'КН22-5835','Дмитро','Олександрович','Волков'),('2005-12-03',_binary '\0',150.013,3,3,'КН22-5511','Анна','Олексіївна','Гєєнко'),('2005-11-07',_binary '',161.484,4,4,'КН22-9299','Микита','Сергійович','Голопьоров'),('2005-12-04',_binary '',191.945,5,5,'КН22-9012','Максим','Георгійович','Гриценко'),('2005-10-25',_binary '',128.346,6,6,'КН22-6636','Вадим','Віталійович','Жарий'),('2005-06-22',_binary '',183.627,7,7,'КН22-5140','Андрій','Володимирович','Жупанов'),('2005-01-15',_binary '',127.288,8,8,'КН22-4896','Михайло','Романович','Кльоз'),('2005-10-11',_binary '',139.739,9,9,'КН22-4694','Микита','Артемович','Коваль'),('2005-04-10',_binary '',187.79,10,10,'КН22-5529','Єгор','Вячеславович','Ковтун'),('2005-03-26',_binary '',165.641,11,11,'КН22-6874','Денис','Євгенович','Колесниченко'),('2005-09-05',_binary '',175.392,12,12,'КН22-2947','Андрій','Сергійович','Ласкевич'),('2005-11-24',_binary '',184.473,13,13,'КН22-3856','Антон','Олександрович','Лукаш'),('2005-05-05',_binary '',168.004,14,14,'КН22-7534','Нікіта','Андрійович','Палій'),('2004-12-12',_binary '',164.665,15,15,'КН22-3843','Владислав','Сергійович','Прокопов'),('2005-05-06',_binary '\0',185.306,16,16,'КН22-7819','Інесса','Віталіївна','Репешко'),('2004-02-26',_binary '',125.907,17,17,'КН22-9115','Андрій','Григорович','Скиба'),('2004-05-19',_binary '\0',190.298,18,18,'КН22-3812','Ольга','Юріївна','Цибань'),('2004-10-25',_binary '',166.849,19,19,'КН22-1828','Данило','Валерійович','Шевченко'),('2005-01-06',_binary '\0',168.46,20,20,'КН22-9514','Катерина','Віталіївна','Антипенко'),('2005-10-01',_binary '\0',157.631,21,21,'КН22-2929','Марина','Русланівна','Багрянцева'),('2005-04-24',_binary '',199.532,22,22,'КН22-1637','Ярослав','Юрійович','Бондаренко'),('2005-05-17',_binary '',153.683,23,23,'КН22-6681','Віктор','Олександрович','Булгаков'),('2005-01-25',_binary '',170.864,24,24,'КН22-7557','Михайло','Олександрович','Васильєв'),('2005-03-03',_binary '\0',171.715,25,25,'КН22-8759','Дарина','Євгенівна','Деркач'),('2005-01-24',_binary '',125.776,26,26,'КН22-7309','Тимур','Ігорович','Ілюхін'),('2005-08-29',_binary '\0',138.747,27,27,'КН22-8254','Аліна','Степанівна','Криженко'),('2000-02-25',_binary '',160.718,28,28,'КН22-3825','Ярослав','Дмитрович','Посмашний'),('1993-10-04',_binary '\0',188.309,29,29,'КН22-8153','Вікторія','Андріївна','Рожкова'),('1997-02-17',_binary '\0',124.24,30,30,'КН22-4788','Єлизавета','Олександрівна','Сизоненко'),('2005-08-29',_binary '\0',138.747,31,NULL,'КН22-8255','Аліна','Степанівна','Криженко'),('2000-02-25',_binary '',160.718,32,NULL,'КН22-3826','Ярослав','Дмитрович','Посмашний'),('1993-10-04',_binary '\0',188.309,33,NULL,'КН22-8154','Вікторія','Андріївна','Рожкова'),('2005-05-17',_binary '',153.683,34,NULL,'КН22-6682','Віктор','Олександрович','Булгаков'),('1996-06-06',_binary '\0',200,35,NULL,'КН22-9998','Регіна','Віталіївна','Марчук');
/*!40000 ALTER TABLE `entrants` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `password_reset_tokens`
--

DROP TABLE IF EXISTS `password_reset_tokens`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `password_reset_tokens` (
  `expiry_date` datetime(6) NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `token` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKla2ts67g4oh2sreayswhox1i6` (`user_id`),
  UNIQUE KEY `UK71lqwbwtklmljk3qlsugr1mig` (`token`),
  CONSTRAINT `fk_password_reset_token_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `password_reset_tokens`
--

LOCK TABLES `password_reset_tokens` WRITE;
/*!40000 ALTER TABLE `password_reset_tokens` DISABLE KEYS */;
INSERT INTO `password_reset_tokens` (`expiry_date`, `id`, `user_id`, `token`) VALUES ('2024-12-03 22:01:39.383674',1,2,'8817e71a-51d4-4ee6-aeb8-2312092ccc7f'),('2024-12-03 22:01:52.374877',2,1,'3d1e639f-d764-485b-85fb-e71288857740'),('2024-12-03 22:01:59.428835',3,3,'283d8d15-3e1d-4ae9-b66c-f01865d87b34');
/*!40000 ALTER TABLE `password_reset_tokens` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `students`
--

DROP TABLE IF EXISTS `students`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `students` (
  `entrant_id` bigint DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `corporate_email` varchar(123) NOT NULL,
  `funding_type` enum('BUDGET','CONTRACT') NOT NULL DEFAULT 'CONTRACT',
  `scholarship_status` enum('ENHANCED','NONE','ORDINARY') NOT NULL DEFAULT 'NONE',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKng7nfmdg9laddmqsllbe38mkx` (`corporate_email`),
  UNIQUE KEY `UKebgu37lcg62sdwu3n7d7gre0v` (`entrant_id`),
  CONSTRAINT `fk_student_entrant` FOREIGN KEY (`entrant_id`) REFERENCES `entrants` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `students_chk_1` CHECK ((regexp_like(`corporate_email`,_utf8mb4'^(?=.{17,123}$)[a-z](.?[a-z]+){1,99}@[a-z]{1,10}.khpi.edu.ua$',_utf8mb4'c') = 1))
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `students`
--

LOCK TABLES `students` WRITE;
/*!40000 ALTER TABLE `students` DISABLE KEYS */;
INSERT INTO `students` (`entrant_id`, `id`, `corporate_email`, `funding_type`, `scholarship_status`) VALUES (1,1,'oleksii.babichiev@cs.khpi.edu.ua','BUDGET','ORDINARY'),(2,2,'dmytro.o.volkov@cs.khpi.edu.ua','BUDGET','NONE'),(3,3,'anna.hieienko@cs.khpi.edu.ua','BUDGET','NONE'),(4,4,'mykyta.holoporov@cs.khpi.edu.ua','BUDGET','ORDINARY'),(5,5,'maksym.hrytsenko@cs.khpi.edu.ua','BUDGET','ENHANCED'),(6,6,'vadym.zharyi@cs.khpi.edu.ua','CONTRACT','NONE'),(7,7,'andrii.zhupanov@cs.khpi.edu.ua','BUDGET','ORDINARY'),(8,8,'mykhailo.kloz@cs.khpi.edu.ua','CONTRACT','NONE'),(9,9,'mykyta.koval@cs.khpi.edu.ua','BUDGET','NONE'),(10,10,'yehor.kovtun@cs.khpi.edu.ua','BUDGET','ENHANCED'),(11,11,'denys.kolesnychenko@cs.khpi.edu.ua','BUDGET','ORDINARY'),(12,12,'andrii.laskevych@cs.khpi.edu.ua','BUDGET','ORDINARY'),(13,13,'anton.lukash@cs.khpi.edu.ua','BUDGET','ORDINARY'),(14,14,'nikita.palii@cs.khpi.edu.ua','BUDGET','NONE'),(15,15,'vladyslav.prokopov@cs.khpi.edu.ua','BUDGET','ORDINARY'),(16,16,'inessa.repeshko@cs.khpi.edu.ua','BUDGET','ENHANCED'),(17,17,'andrii.h.skyba@cit.khpi.edu.ua','CONTRACT','NONE'),(18,18,'olha.tsyban@cs.khpi.edu.ua','BUDGET','NONE'),(19,19,'danylo.v.shevchenko@cs.khpi.edu.ua','BUDGET','ORDINARY'),(20,20,'kateryna.antypenko@cs.khpi.edu.ua','BUDGET','ORDINARY'),(21,21,'marina.bahriantseva@cs.khpi.edu.ua','BUDGET','ORDINARY'),(22,22,'yaroslav.y.bondarenko@cs.khpi.edu.ua','BUDGET','ENHANCED'),(23,23,'viktor.bulhakov@cs.khpi.edu.ua','BUDGET','NONE'),(24,24,'mykhailo.vasyliev@cs.khpi.edu.ua','BUDGET','NONE'),(25,25,'daryna.derkach@cs.khpi.edu.ua','BUDGET','ORDINARY'),(26,26,'tymur.iliukhin@cs.khpi.edu.ua','CONTRACT','NONE'),(27,27,'alina.kryzhenko@cs.khpi.edu.ua','BUDGET','NONE'),(28,28,'yaroslav.posmashnyi@cs.khpi.edu.ua','BUDGET','ORDINARY'),(29,29,'viktoriia.rozhkova@cs.khpi.edu.ua','BUDGET','ENHANCED'),(30,30,'yelyzaveta.syzonenko@cs.khpi.edu.ua','CONTRACT','NONE');
/*!40000 ALTER TABLE `students` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL,
  `name` varchar(50) NOT NULL,
  `patronymic` varchar(50) NOT NULL,
  `surname` varchar(50) NOT NULL,
  `corporate_email` varchar(123) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` enum('ADMIN','ENTRANT_MANAGER','ENTRANT_VIEWER','STUDENT_MANAGER','STUDENT_VIEWER') NOT NULL DEFAULT 'STUDENT_VIEWER',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`),
  UNIQUE KEY `UKaxfb69mtv3gvjwg69lj0jb93x` (`corporate_email`),
  CONSTRAINT `users_chk_1` CHECK ((regexp_like(`username`,_utf8mb4'^(?=.{8,32}$)[a-zA-Z0-9._-]+$',_utf8mb4'c') = 1)),
  CONSTRAINT `users_chk_2` CHECK ((regexp_like(`name`,_utf8mb4'^(?=.{1,50}$)[А-ЩЮЯҐЄІЇ](?:[А-ЯҐЄІЇа-яґєіїʼ]*[А-ЯҐЄІЇа-яґєії])?(?:[ -][А-ЩЮЯҐЄІЇ](?:[А-ЯҐЄІЇа-яґєіїʼ]*[А-ЯҐЄІЇа-яґєії])?)*$',_utf8mb4'c') = 1)),
  CONSTRAINT `users_chk_3` CHECK ((regexp_like(`patronymic`,_utf8mb4'^(?=.{1,50}$)[А-ЩЮЯҐЄІЇ](?:[А-ЯҐЄІЇа-яґєіїʼ]*[А-ЯҐЄІЇа-яґєії])?(?:[ -][А-ЩЮЯҐЄІЇ](?:[А-ЯҐЄІЇа-яґєіїʼ]*[А-ЯҐЄІЇа-яґєії])?)*$',_utf8mb4'c') = 1)),
  CONSTRAINT `users_chk_4` CHECK ((regexp_like(`surname`,_utf8mb4'^(?=.{1,50}$)[А-ЩЮЯҐЄІЇ](?:[А-ЯҐЄІЇа-яґєіїʼ]*[А-ЯҐЄІЇа-яґєії])?(?:[ -][А-ЩЮЯҐЄІЇ](?:[А-ЯҐЄІЇа-яґєіїʼ]*[А-ЯҐЄІЇа-яґєії])?)*$',_utf8mb4'c') = 1)),
  CONSTRAINT `users_chk_5` CHECK ((regexp_like(`corporate_email`,_utf8mb4'^(?=.{17,123}$)[a-z](.?[a-z]+){1,99}@[a-z]{1,10}.khpi.edu.ua$',_utf8mb4'c') = 1))
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`id`, `username`, `name`, `patronymic`, `surname`, `corporate_email`, `password`, `role`) VALUES (1,'shavlii_oleksii','Олексій','Олександрович','Шавлій','shavlii.oleksii@ntu.khpi.edu.ua','$2a$10$rw2shX0X8op96Ln/In95UuHeOCrB2hhE1sG0kqEtvPzCwqMp9cBWi','ADMIN'),(2,'burulka-liudmyla','Людмила','Ярославівна','Бурулька','burulka.liudmyla@ntu.khpi.edu.ua','$2a$10$0jgn1uma0CEVUvVDoSqCFu4S803IxlaCOadlaKQKmdsnf40cvHJN6','ENTRANT_MANAGER'),(3,'voloshkov-tymur','Тимур','Сергійович','Волошков','voloshkov.tymur@ntu.khpi.edu.ua','$2a$10$JSrcpKeZJwXllB1oFNrZY.HFE53Ymd1QgYE9qLc7kl22t9AEBs7PG','STUDENT_MANAGER'),(4,'kulish.volodymyr','Володимир','Анатолійович','Куліш','kulish.volodymyr@cs.khpi.edu.ua','$2a$10$XeOn76jnGsvCDUSrJW3JE.XJE8c0iRVLeJLTWYBCLjPJJoYFN3YYe','ENTRANT_VIEWER'),(5,'stepova.alina','Аліна','Трофимівна','Степова','stepova.alina@cs.khpi.edu.ua','$2a$10$V1.ZIdiEhADsFF3IyYt1YOFwfxDDd13IEpoS0e.HB/oSrCgMMTTrW','STUDENT_VIEWER'),(6,'ivanenko_anna','Анна','Петрівна','Іваненко','ivanenko.anna@ntu.khpi.edu.ua','$2a$10$2WFLmreMo6jQEVY5j.9HPurdIZNsK/ojPhXRPLYeYxE22ZTil2PRK','ADMIN'),(7,'tarasenko-oleh','Олег','Іванович','Тарасенко','tarasenko.oleh@ntu.khpi.edu.ua','$2a$10$Fv..mVUJB3bbWwjzGx6/0.pa6viejweZAOwOPFL63NxvPhHC26ygO','ENTRANT_MANAGER'),(8,'melnyk_daryna','Дарина','Анатоліївна','Мельник','melnyk.daryna@ntu.khpi.edu.ua','$2a$10$skyG3TKSSD2j2Dfd381AKe7LTPu9Itds4VYFpY1cOyw6loVGHJt1u','STUDENT_MANAGER'),(9,'horbunov.vladyslav','Владислав','Михайлович','Горбунов','horbunov.vladyslav@cs.khpi.edu.ua','$2a$10$VHwQVn17o7/aSBPa8dxu9evkKVrbRYvWXBJ.CG5.yDwa/zPL6k6wq','ENTRANT_VIEWER'),(10,'romanova-olena','Олена','Олексіївна','Романова','romanova.olena@cs.khpi.edu.ua','$2a$10$dvoPA.RTXaKbCQ3Imv6lfeRM/zFOu.bYgt12U4C26Aa9zbKPkhT4e','STUDENT_VIEWER'),(11,'vasylchenko.mykhailo','Михайло','Олександрович','Васильченко','vasylchenko.mykhailo@ntu.khpi.edu.ua','$2a$10$vguSRdQ08PlP16fQCmAj/uMohKX2KdWMe/Qu3mh6QPShoSSs7sD9G','ADMIN'),(12,'shapoval-kseniia','Ксенія','Василівна','Шаповал','shapoval.kseniia@ntu.khpi.edu.ua','$2a$10$fzEmxaBKskooEnuKMbxYHeHmpfgMbwJ5VXMnpiXr1QsNYd/s1ItvG','ENTRANT_MANAGER'),(13,'kozak.yevhenii','Євгеній','Олегович','Козак','kozak.yevhenii@ntu.khpi.edu.ua','$2a$10$Pbti3NgX5/46PS/hCL2ccevHFQ2iqYR0hZo4TcxjG2BRYhFO2NBXG','STUDENT_MANAGER'),(14,'bondar.inna','Інна','Ігорівна','Бондар','bondar.inna@cs.khpi.edu.ua','$2a$10$NwNzFn/H9/srWhfX9e.xXOadkByGeFrRRdyBq.VxFlOdpB2uEc3QO','ENTRANT_VIEWER'),(15,'kravchenko-artem','Артем','Богданович','Кравченко','kravchenko.artem@cs.khpi.edu.ua','$2a$10$knc9FdCAvRD6Bc1bvtGOmeVPJcR06U83CFo4lLYkL3hVfnqwet6.S','STUDENT_VIEWER');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-02 20:04:08
