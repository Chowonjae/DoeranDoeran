-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: doeran.kr    Database: hello
-- ------------------------------------------------------
-- Server version	8.0.31

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
-- Table structure for table `alarms`
--

DROP TABLE IF EXISTS `alarms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alarms` (
  `alarm_id` bigint NOT NULL AUTO_INCREMENT,
  `alarm_type` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `create_at` datetime(6) DEFAULT NULL,
  `dst_user_id` bigint DEFAULT NULL,
  `room_id` bigint DEFAULT NULL,
  `src_user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`alarm_id`),
  KEY `FKn44e2nce2pfvcpnq6xppw560y` (`dst_user_id`),
  KEY `FKny0omptywdpe4bnsu7iqv3fal` (`room_id`),
  KEY `FK9mvqjfwbx2gexlmh3ifpitrw7` (`src_user_id`),
  CONSTRAINT `FK9mvqjfwbx2gexlmh3ifpitrw7` FOREIGN KEY (`src_user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `FKn44e2nce2pfvcpnq6xppw560y` FOREIGN KEY (`dst_user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `FKny0omptywdpe4bnsu7iqv3fal` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`room_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alarms`
--

LOCK TABLES `alarms` WRITE;
/*!40000 ALTER TABLE `alarms` DISABLE KEYS */;
/*!40000 ALTER TABLE `alarms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feeds`
--

DROP TABLE IF EXISTS `feeds`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feeds` (
  `feed_id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `create_at` datetime(6) DEFAULT NULL,
  `feed_type` varchar(255) DEFAULT NULL,
  `feed_url` varchar(255) DEFAULT NULL,
  `user_room_id` bigint DEFAULT NULL,
  PRIMARY KEY (`feed_id`),
  KEY `FKclqegi9tbkslam74gle8uqtes` (`user_room_id`),
  CONSTRAINT `FKclqegi9tbkslam74gle8uqtes` FOREIGN KEY (`user_room_id`) REFERENCES `user_rooms` (`user_room_id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feeds`
--

LOCK TABLES `feeds` WRITE;
/*!40000 ALTER TABLE `feeds` DISABLE KEYS */;
INSERT INTO `feeds` VALUES (30,'찌그렁 오리','2023-05-12 00:53:57.695000',NULL,'https://doeran.s3.ap-northeast-2.amazonaws.com/feed/20/fc1b4300-b127-416f-8736-b8cab6d74cc0dragon.png',20),(35,NULL,'2023-05-14 17:32:38.898000',NULL,'https://doeran.s3.ap-northeast-2.amazonaws.com/feed/21/921b73a5-aa05-4144-8dbe-34b33c18bff5image.jpg',22),(36,NULL,'2023-05-15 02:01:42.986000',NULL,'https://doeran.s3.ap-northeast-2.amazonaws.com/feed/21/f3e4774c-e619-49ce-93c6-1aa7d422fca9image.jpg',22),(37,NULL,'2023-05-15 02:04:19.980000',NULL,'https://doeran.s3.ap-northeast-2.amazonaws.com/feed/20/ff4a1006-c278-4884-974f-369bbdc42dd1image.jpg',20),(39,NULL,'2023-05-15 03:44:00.028000',NULL,'https://doeran.s3.ap-northeast-2.amazonaws.com/feed/21/a9bcc16f-7d32-4ebb-a1a8-ca5701f86502image.jpg',21),(43,NULL,'2023-05-16 11:03:50.812000',NULL,'https://doeran.s3.ap-northeast-2.amazonaws.com/feed/21/a0335409-d45c-419c-ac7a-4ee380e748b9image.jpg',22),(51,NULL,'2023-05-17 11:03:55.891000',NULL,'https://doeran.s3.ap-northeast-2.amazonaws.com/feed/21/911cc974-ad98-44da-8b7b-fbe931c38dc8image.jpg',22),(53,'고양이','2023-05-17 11:40:04.012000','FEED2','https://doeran.s3.ap-northeast-2.amazonaws.com/feed/21/c1a1335d-57ce-4528-b033-09599712e409KakaoTalk_20230517_203903763.jpg',34),(54,NULL,'2023-05-17 12:24:49.457000',NULL,'https://doeran.s3.ap-northeast-2.amazonaws.com/feed/45/0cdb0951-4165-4976-81ae-ff59320f5050image.jpg',48),(55,NULL,'2023-05-17 12:26:25.873000',NULL,'https://doeran.s3.ap-northeast-2.amazonaws.com/feed/45/39e89c73-33cf-4862-9a0f-026f4dc6b6f4image.jpg',51),(56,NULL,'2023-05-17 12:39:19.649000',NULL,'https://doeran.s3.ap-northeast-2.amazonaws.com/feed/45/ae9806ca-2634-4cc6-b02a-bb79994ad7d6image.jpg',50);
/*!40000 ALTER TABLE `feeds` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `histories`
--

DROP TABLE IF EXISTS `histories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `histories` (
  `history_id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `query_id` bigint DEFAULT NULL,
  `room_id` bigint DEFAULT NULL,
  `question_id` bigint DEFAULT NULL,
  PRIMARY KEY (`history_id`),
  KEY `FK6n3wxnx7m2irfsr1bug7brkhc` (`query_id`),
  KEY `FK6toc2nwvd6uowrfbg3yhvf9wv` (`room_id`),
  KEY `FKem1g3kj5e0vc5x2oh21ok7rf` (`question_id`),
  CONSTRAINT `FK6n3wxnx7m2irfsr1bug7brkhc` FOREIGN KEY (`query_id`) REFERENCES `questions` (`question_id`),
  CONSTRAINT `FK6toc2nwvd6uowrfbg3yhvf9wv` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`room_id`),
  CONSTRAINT `FKem1g3kj5e0vc5x2oh21ok7rf` FOREIGN KEY (`question_id`) REFERENCES `questions` (`question_id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `histories`
--

LOCK TABLES `histories` WRITE;
/*!40000 ALTER TABLE `histories` DISABLE KEYS */;
INSERT INTO `histories` VALUES (2,'2023-05-16 05:00:00.229000',NULL,5,10),(17,'2023-05-16 05:00:00.417000',NULL,20,6),(18,'2023-05-16 05:00:00.429000',NULL,21,5),(24,'2023-05-16 11:23:25.066000',NULL,27,1),(30,'2023-05-16 15:00:00.124000',NULL,5,10),(44,'2023-05-16 15:00:00.249000',NULL,20,6),(45,'2023-05-16 15:00:00.257000',NULL,21,5),(46,'2023-05-16 15:00:00.266000',NULL,27,2),(59,'2023-05-17 12:15:18.557000',NULL,45,1);
/*!40000 ALTER TABLE `histories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questions`
--

DROP TABLE IF EXISTS `questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `questions` (
  `question_id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(255) NOT NULL,
  `no` bigint NOT NULL,
  `question_type` varchar(255) NOT NULL,
  PRIMARY KEY (`question_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions`
--

LOCK TABLES `questions` WRITE;
/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
INSERT INTO `questions` VALUES (1,'지금 뭐하는지 찍기',1,'DAILY'),(2,'오늘 먹은 것 공유하기',2,'DAILY'),(3,'내 주변에 있는 가장 비싼 물건 찍기',1001,'GAME'),(4,'오늘 날 기분 좋게 한 것 찍기',3,'DAILY'),(5,'나에게 편안함을 주는 것을 찍어보세요',2001,'KNOW'),(6,'익숙한 무언가를 가장 독창적으로 찍어보세요',1002,'GAME'),(7,'하늘 사진 찍기',4,'DAILY'),(8,'내가 찍을 수 있는 것 중 가장 오래된 무언가를 찍어보세요',1003,'GAME'),(9,'내가 좋아하는 풍경 찍기',5,'DAILY'),(10,'내가 소중하게 여기는 무언가를 공유해주세요',2002,'KNOW'),(11,'오늘 날 웃게 한 것 찍기',6,'DAILY'),(12,'다른 사람은 찍기 어려울 것 같은 무언가를 찍어보세요',1004,'GAME'),(13,'내가 찍을 수 있는 것 중 가장 특이한 무언가를 찍어보세요',1005,'GAME'),(14,'우리 가족을 상징하는 것을 찍어보세요',2003,'KNOW'),(15,'최근에 가장 즐거웠던 순간은 어느 순간인가요?',2004,'KNOW'),(16,'내 인생에서 행복한 순간은 언제였나요?',2005,'KNOW'),(17,'인상 깊게 본 영화는?',2006,'KNOW'),(18,'가족과 같이 하기 좋아하는 것은?',2007,'KNOW'),(19,'주변 사람들에게 어떤 사람이 되고 싶나요?',2008,'KNOW'),(20,'세상 그 누구와도 식사를 할 수 있다면, 누구와 먹고 싶은가요?',2009,'KNOW'),(21,'내 인생에서 중요했던 조언은?',2010,'KNOW'),(22,'최근에 가족에게 고마웠던 것은?',2011,'KNOW'),(23,'자신의 장점은 뭐라고 생각하나요?',2012,'KNOW'),(24,'가족이 잘 모르는 나의 모습은?',2013,'KNOW'),(25,'당신이 소심해지는 순간은?',2014,'KNOW'),(26,'우리 가족의 특이한 점 써보기',2015,'KNOW'),(27,'자신의 단점은 뭐라고 생각하나요?',2016,'KNOW'),(28,'행복하기 위해선 돈이 얼마나 있어야 한다고 생각하나요?',2017,'KNOW'),(29,'올해에 가족과 꼭 해보고 싶은 것은?',2018,'KNOW'),(30,'자신이 가장 자신다워지는 순간은 언제인가요?',2019,'KNOW'),(31,'현실성 고려하지 않고, 가족과 꼭 해보고 싶은 것은?',2020,'KNOW'),(32,'가족에게 어떤 존재가 되고 싶나요?',2021,'KNOW');
/*!40000 ALTER TABLE `questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rooms`
--

DROP TABLE IF EXISTS `rooms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rooms` (
  `room_id` bigint NOT NULL AUTO_INCREMENT,
  `begin_time` int NOT NULL,
  `create_at` datetime(6) DEFAULT NULL,
  `room_code` varchar(255) DEFAULT NULL,
  `room_password` varchar(255) DEFAULT NULL,
  `room_question` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`room_id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rooms`
--

LOCK TABLES `rooms` WRITE;
/*!40000 ALTER TABLE `rooms` DISABLE KEYS */;
INSERT INTO `rooms` VALUES (1,0,'2023-05-04 00:32:22.778000',NULL,'$2a$10$xKQkyARYKzLmZ3ZtZe3pU.d7/v6/IFAUlo1Z3/mmRMfPkeyuFE.ky',NULL),(3,0,'2023-05-04 01:16:08.435000','https://doeran.page.link/2P5cxV38t6Aimw1Q8','$2a$10$GxewDOJO4/zG/s18DIuTDunj/NcUOi14nBPQVcC2EQalD9mbwOGOa',NULL),(5,0,'2023-05-08 05:58:48.994000',NULL,'$2a$10$jkuDUOwigJptpePwAy7fdu3yfgVTtucKYnWRvf6uKi6vl4Ff3XUua',NULL),(20,8,'2023-05-12 00:52:38.724000','https://doeran2.page.link/LePr9AQAftzj5uVD9','$2a$10$4gq7v33EgOB9jhRc4S6x6evIc/u/av5vY10JAC7WveOKcdYuez9Ze','1234'),(21,8,'2023-05-13 14:24:17.826000','https://doeran2.page.link/6D2hbXpfhaSVdHWq8','$2a$10$x8eVqEITPONTjWox8ZYNyeiue3LqDdmhBxPA2IfdxAb6D.32MRIKu','1234'),(27,8,'2023-05-16 11:23:25.047000','https://doeran2.page.link/xMeaU8S6QdULXZfs9','$2a$10$0p6mJTg2fwklS/Qe16LaQe75xbi/l4K90LahrFqAyxaFDak6WOAsm','ㄷㄷㄷ'),(45,8,'2023-05-17 12:15:18.537000','https://doeran2.page.link/MRJDEinn6appej1H9','$2a$10$hcT7dXyl71CGHzUl9TuplePzLoQVeF4x.dYFL9E/662m0WTrqgqZ2','현관비밀번호는?');
/*!40000 ALTER TABLE `rooms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_rooms`
--

DROP TABLE IF EXISTS `user_rooms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_rooms` (
  `user_room_id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `day_alarm` varchar(255) DEFAULT NULL,
  `move_alarm` varchar(255) DEFAULT NULL,
  `room_name` varchar(255) DEFAULT NULL,
  `safe_alarm` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `user_room_role` varchar(255) DEFAULT NULL,
  `room_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`user_room_id`),
  KEY `FK8jtunsjv82pjvbgr6tbga9aw4` (`room_id`),
  KEY `FKlp3u9gpy3sne8ibijkmvnukin` (`user_id`),
  CONSTRAINT `FK8jtunsjv82pjvbgr6tbga9aw4` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`room_id`),
  CONSTRAINT `FKlp3u9gpy3sne8ibijkmvnukin` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_rooms`
--

LOCK TABLES `user_rooms` WRITE;
/*!40000 ALTER TABLE `user_rooms` DISABLE KEYS */;
INSERT INTO `user_rooms` VALUES (20,'2023-05-12 00:52:38.758000','Y','Y','백테스트방','Y','하위','ROLE3',20,4),(21,'2023-05-13 14:24:17.878000','Y','Y','PURPLE','Y','phang','ROLE1',21,2),(22,'2023-05-14 11:40:32.924000','Y','Y','PURPLE','Y','쨔잔','ROLE3',21,4),(28,'2023-05-16 11:23:25.080000','Y','Y','ㄱㄱㄱ','Y','ㄴㄴㄴ','ROLE1',27,2),(34,'2023-05-17 01:53:48.891000','Y','Y','우리방','Y','태현','ROLE2',21,7),(48,'2023-05-17 12:15:18.574000','Y','Y','우리 가족 ㅎ','Y','호랑이대장','ROLE1',45,4),(50,'2023-05-17 12:23:29.576000','Y','Y','우리 가족 ㅎㅎ','Y','소소장','ROLE2',45,7),(51,'2023-05-17 12:25:54.203000','Y','Y','우리 가족 ㅎ','Y','핑와','ROLE2',45,2);
/*!40000 ALTER TABLE `user_rooms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `access_token` varchar(255) DEFAULT NULL,
  `birth` datetime(6) DEFAULT NULL,
  `create_at` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `refresh_token` varchar(255) DEFAULT NULL,
  `user_profile_url` varchar(255) DEFAULT NULL,
  `oauth_id` varchar(255) DEFAULT NULL,
  `device_token` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (2,NULL,'1993-07-15 00:00:00.000000',NULL,NULL,NULL,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyIiwidXNlcklkIjoiMiIsImV4cCI6MTY4NDM3MTA4OX0.v0dl5bUn3E0BEtLyWrzwuF-oa6Bj22g9H5YOnnMUkec','https://doeran.s3.ap-northeast-2.amazonaws.com/profile/zodiac/rooster.png','2757910879','fTcxs9OLRqe_Njj6SXX8Kt:APA91bEH9guJ-mJMhisiaouEHWOyqpbnNYNEFCqSy7RvpMaLQGvrbEz3ljluihausDVIydBWD9K3DnITgi06EW7vUWYuY-ROX7IaR_XbHqEzJPd_7lbHaVfwHS6Njh5z6T89NlgOjv7X'),(4,NULL,'1998-08-11 00:00:00.000000',NULL,NULL,NULL,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI0IiwidXNlcklkIjoiNCIsImV4cCI6MTY4NDM2ODA2N30.MgAgpSgS3qmp_SVn_BgvI8LQBmosPyNVqISrttXcetE','https://doeran.s3.ap-northeast-2.amazonaws.com/profile/zodiac/tiger.png','2773446643','cvhfETifTzukaqB3ZMSoKA:APA91bFu7FGIt4-XCigBPPGiLnMR7EjIGNDGHSV15C0VXmpwQH1LtMhkApV2KSKGjm7j3k8F5RyNLC9K373Bq7HtS7se22hRyjMcGrYJgE8tjNEGaKVPAE4O3av39ncCA--B0tTT746X'),(6,NULL,NULL,NULL,NULL,NULL,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2IiwidXNlcklkIjoiNiIsImV4cCI6MTY4NDM0ODM3NX0.hFrc-FaZm_B73JfnfIKh4QnO6TJ9yyzI9NzoAE_ll2I',NULL,'106828336055213662926','dTBkYlJmTSmqE9dmfKjOwk:APA91bEC2lwzADHMZkPjCa-HlErN5AcCZSKKXCQbWKRjYQF-j9teNFUWMX30gPjIsbrsRS38K2gN0TLsp0hr0niaZJxXD3geu3L-uLM__G7gJo5Xuj8XObxKX3vpUUx3tsSpYUk-OEGq'),(7,NULL,'1970-05-17 00:00:00.000000',NULL,NULL,NULL,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3IiwidXNlcklkIjoiNyIsImV4cCI6MTY4NDM2OTE0MH0.uRavx4nnuWcel-bZCFnfG82V_jIB6qkqUSHuD-kHZFU','https://doeran.s3.ap-northeast-2.amazonaws.com/profile/zodiac/cow.png','2793384996','efd1iewuTESggX7P8lrmvB:APA91bEQZ9hsfuIsfuJZb-b7EgvBfR82gppQH75uMVIv5oenn6mXl9OXNnmnXXgSTh5m3fnherP_QMj2Y7MeRW41lPGX853JLNJPQaErwomYvDOtmY4Eq5KopOMHXJEmcSJc-loefsGO'),(8,NULL,NULL,NULL,NULL,NULL,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI4IiwidXNlcklkIjoiOCIsImV4cCI6MTY4NDM1MTk3NX0.DVRH8t3zdzJ50Gv-HE_drjTvW4tNKcdP5jlxuSWu46c',NULL,'2794038457','fPK82O4FTpq4v9Z270o-zg:APA91bHRLw8uKu-5ja_iwofjsPVfOnmzv5l8AaqsN9MEVTPbFVHXQccC9y7Ng7EJ4lZSA3gnS426vV2yEx16w9wzIMElNF5Hb1NPdzZJncjWwBxT2V013sjdPqqHPGIcSi233THICPKW');
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

-- Start Insert
INSERT INTO `questions` (content, no, question_type) VALUES ("지금 뭐하는지 찍기", 1, "DAILY");
INSERT INTO `questions` (content, no, question_type) VALUES ("오늘 먹은 것 공유하기", 2, "DAILY");
INSERT INTO `questions` (content, no, question_type) VALUES ("내 주변에 있는 가장 비싼 물건 찍기", 1001, "GAME");
INSERT INTO `questions` (content, no, question_type) VALUES ("오늘 날 기분 좋게 한 것 찍기", 3, "DAILY");
INSERT INTO `questions` (content, no, question_type) VALUES ("나에게 편안함을 주는 것을 찍어보세요", 2001, "KNOW");
INSERT INTO `questions` (content, no, question_type) VALUES ("익숙한 무언가를 가장 독창적으로 찍어보세요", 1002, "GAME");
INSERT INTO `questions` (content, no, question_type) VALUES ("하늘 사진 찍기", 4, "DAILY");
INSERT INTO `questions` (content, no, question_type) VALUES ("내가 찍을 수 있는 것 중 가장 오래된 무언가를 찍어보세요", 1003, "GAME");
INSERT INTO `questions` (content, no, question_type) VALUES ("내가 좋아하는 풍경 찍기", 5, "DAILY");
INSERT INTO `questions` (content, no, question_type) VALUES ("내가 소중하게 여기는 무언가를 공유해주세요", 2002, "KNOW");
INSERT INTO `questions` (content, no, question_type) VALUES ("오늘 날 웃게 한 것 찍기", 6, "DAILY");
INSERT INTO `questions` (content, no, question_type) VALUES ("다른 사람은 찍기 어려울 것 같은 무언가를 찍어보세요", 1004, "GAME");
INSERT INTO `questions` (content, no, question_type) VALUES ("내가 찍을 수 있는 것 중 가장 특이한 무언가를 찍어보세요", 1005, "GAME");
INSERT INTO `questions` (content, no, question_type) VALUES ("우리 가족을 상징하는 것을 찍어보세요", 2003, "KNOW");
INSERT INTO `questions` (content, no, question_type) VALUES ("최근에 가장 즐거웠던 순간은 어느 순간인가요?", 2004, "KNOW");
INSERT INTO `questions` (content, no, question_type) VALUES ("내 인생에서 행복한 순간은 언제였나요?", 2005, "KNOW");
INSERT INTO `questions` (content, no, question_type) VALUES ("인상 깊게 본 영화는?", 2006, "KNOW");
INSERT INTO `questions` (content, no, question_type) VALUES ("가족과 같이 하기 좋아하는 것은?", 2007, "KNOW");
INSERT INTO `questions` (content, no, question_type) VALUES ("주변 사람들에게 어떤 사람이 되고 싶나요?", 2008, "KNOW");
INSERT INTO `questions` (content, no, question_type) VALUES ("세상 그 누구와도 식사를 할 수 있다면, 누구와 먹고 싶은가요?", 2009, "KNOW");
INSERT INTO `questions` (content, no, question_type) VALUES ("내 인생에서 중요했던 조언은?", 2010, "KNOW");
INSERT INTO `questions` (content, no, question_type) VALUES ("최근에 가족에게 고마웠던 것은?", 2011, "KNOW");
INSERT INTO `questions` (content, no, question_type) VALUES ("자신의 장점은 뭐라고 생각하나요?", 2012, "KNOW");
INSERT INTO `questions` (content, no, question_type) VALUES ("가족이 잘 모르는 나의 모습은?", 2013, "KNOW");
INSERT INTO `questions` (content, no, question_type) VALUES ("당신이 소심해지는 순간은?", 2014, "KNOW");
INSERT INTO `questions` (content, no, question_type) VALUES ("우리 가족의 특이한 점 써보기", 2015, "KNOW");
INSERT INTO `questions` (content, no, question_type) VALUES ("자신의 단점은 뭐라고 생각하나요?", 2016, "KNOW");
INSERT INTO `questions` (content, no, question_type) VALUES ("행복하기 위해선 돈이 얼마나 있어야 한다고 생각하나요?", 2017, "KNOW");
INSERT INTO `questions` (content, no, question_type) VALUES ("올해에 가족과 꼭 해보고 싶은 것은?", 2018, "KNOW");
INSERT INTO `questions` (content, no, question_type) VALUES ("자신이 가장 자신다워지는 순간은 언제인가요?", 2019, "KNOW");
INSERT INTO `questions` (content, no, question_type) VALUES ("현실성 고려하지 않고, 가족과 꼭 해보고 싶은 것은?", 2020, "KNOW");
INSERT INTO `questions` (content, no, question_type) VALUES ("가족에게 어떤 존재가 되고 싶나요?", 2021, "KNOW");
-- End Insert

-- Dump completed on 2023-05-17 21:58:16
