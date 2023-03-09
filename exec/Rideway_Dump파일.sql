-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: 3.36.76.227    Database: rideway
-- ------------------------------------------------------
-- Server version	8.0.32-0ubuntu0.20.04.2

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
-- Table structure for table `achievement`
--

DROP TABLE IF EXISTS `achievement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `achievement` (
  `achievement_id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `terms` varchar(255) NOT NULL,
  `achievement` varchar(255) NOT NULL,
  PRIMARY KEY (`achievement_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `achievement`
--

LOCK TABLES `achievement` WRITE;
/*!40000 ALTER TABLE `achievement` DISABLE KEYS */;
INSERT INTO `achievement` VALUES (1,'마라톤','총 주행 거리','총 42.195km 이상 주행'),(2,'서울한바퀴','총 주행 거리','총 157km 이상 주행'),(3,'국토종주급','총 주행 거리','총 600km 이상 주행'),(4,'눈치보는 중','게시판 글 작성 수','게시판 글 작성 10개 이상'),(5,'함께해요','게시판 글 작성 수','게시판 글 작성 100개 이상'),(6,'지박령','게시판 글 작성 수','게시판 글 작성 2000개 이상'),(7,'이제 막 시작한','총 주행 시간','총 주행 시간 10이상'),(8,'자전거와 친해지는 중','총 주행 시간','총 주행 시간 100 이상'),(9,'자전거와 함께 사는','총 주행 시간','총 주행 시간 2000이상'),(10,'나만이 알던 길','코스 추천 게시판 작성 수','코스 추천 글 작성 5개 이상'),(11,'은둔 고수','코스 추천 게시판 작성 수','코스 추천 글 작성 30개 이상'),(12,'RideWay','코스 추천 게시판 작성 수','코스 추천 글 작성 100개 이상'),(13,'아나바다','중고거래 성사 수','중고거래 성사 5개 이상'),(14,'장사시작','중고거래 성사 수','중고거래 성사 30개 이상'),(15,'보부상','중고거래 성사 수','중고거래 성사 100개 이상');
/*!40000 ALTER TABLE `achievement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `board`
--

DROP TABLE IF EXISTS `board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `board` (
  `board_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `board_code` bigint NOT NULL,
  `count` bigint DEFAULT NULL,
  `title` varchar(255) NOT NULL COMMENT '게시판 제목',
  `content` varchar(16000) DEFAULT NULL COMMENT '게시판 내용',
  `visited` bigint NOT NULL DEFAULT '0' COMMENT '게시판 조회수 , 유저당 최대 1회 카운트',
  `like` bigint NOT NULL DEFAULT '0' COMMENT '게시판 추천 수 , 유저당 최대 1회 추천 가능',
  `hate` bigint DEFAULT NULL,
  `time` datetime(6) NOT NULL,
  PRIMARY KEY (`board_id`),
  KEY `FK_user_TO_board_1` (`user_id`),
  KEY `FK_board_code_TO_board_1` (`board_code`),
  CONSTRAINT `FK_board_code_TO_board_1` FOREIGN KEY (`board_code`) REFERENCES `board_code` (`code`),
  CONSTRAINT `FK_user_TO_board_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FKfyf1fchnby6hndhlfaidier1r` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board`
--

LOCK TABLES `board` WRITE;
/*!40000 ALTER TABLE `board` DISABLE KEYS */;
INSERT INTO `board` VALUES (29,10,300,1,'국토 종주 인증!','<p><img src=\"https://i8e102.p.ssafy.io/api/board/imageDownload/images/board/a6eaf3b3-43e0-4251-a4d8-7006b7159c05.jpg\" alt=\"image\" contenteditable=\"false\"><br></p><p><br></p><p>간만에 생각나서 한번 올려봅니다.</p><p>시작했을 때 2~3일째 안장통이 엄청 심했어서 대체 이걸 왜 시작했지 하면서 했던게 기억나네요</p><p>근데 4일째 부턴 그냥 적응되었고 그냥 묵묵히 계속 계속 갔던 기억이 납니다 ㅎㅎ</p><p><br></p><p>가장 기억에 남는건 제가 불쌍해보였는지 많은 사람들이 도와줄까요 하고 물어봐주셨는데 </p><p>그 중 태어나서 본 눈 중에 가장 눈이 새파랗던 외국인 노인분께서 \"혹시 괜차누신가요우? 도웨두릴까요우?\" 하고 물어봐주셨을 때</p><p>\'내가 글로벌하게 불쌍해보이는구나\' 라는 생각이 들어 기억에 가장 남네요</p>',8,3,0,'2023-02-14 15:52:16.000000'),(40,3,100,1,'제가 첫번째 글이네요^^','<p><img src=\"https://i8e102.p.ssafy.io/api/board/imageDownload/images/board/a6f8a88e-8fb2-4e42-a06f-5226c1d4e3e2.png\" alt=\"image\" contenteditable=\"false\"><strong>다들 만나서 반갑습니다!</strong></p>',3,0,0,'2023-02-15 17:34:45.000000'),(41,3,200,1,'다들 자전거 어디서 사요?','<p>추천 좀 해주세요..!!</p>',5,0,0,'2023-02-15 17:35:39.000000'),(42,3,300,1,'제 초상화 인증합니다','<p><img src=\"https://i8e102.p.ssafy.io/api/board/imageDownload/images/board/8fd23af6-e934-42d5-a59a-3e4e1875e2e8.png\" alt=\"image\" contenteditable=\"false\">보노보노!</p>',4,2,0,'2023-02-15 17:36:19.000000'),(43,3,400,1,'다들 요즘 핫한 캐릭터 보고가세요','<p><img src=\"https://i8e102.p.ssafy.io/api/board/imageDownload/images/board/38b0023b-648c-4318-b90d-7c8310de8c43.png\" alt=\"image\" contenteditable=\"false\">!!</p>',5,0,0,'2023-02-15 17:38:20.000000'),(44,3,100,1,'이 커뮤니티는 제겁니다','<p>알겠습니까?!</p>',5,0,0,'2023-02-15 17:38:51.000000'),(45,9,100,1,'자전거 타다가 생긴 물집 생김','<p><img src=\"https://i8e102.p.ssafy.io/api/board/imageDownload/images/board/f473604d-8a49-4f5f-93e4-7176ff7b6ee5.jpg\" alt=\"image\" contenteditable=\"false\"><br></p><p>겁나아픔</p>',4,0,0,'2023-02-15 17:43:28.000000'),(46,9,200,1,'서면 근처 자전거 파는곳 아시는분?','<p><img src=\"https://i8e102.p.ssafy.io/api/board/imageDownload/images/board/7ce32bf4-dd59-4b57-a74d-7fdf1190a88f.jpg\" alt=\"image\" contenteditable=\"false\"><br></p><p>급함 빨리</p>',3,0,0,'2023-02-15 17:44:47.000000'),(47,6,100,1,'햄스터 귀여운 사진 공유해요','<p><img src=\"https://i8e102.p.ssafy.io/api/board/imageDownload/images/board/4ad1788f-549b-4ecd-b960-eb0d7f9d9fc2.png\" alt=\"image\" contenteditable=\"false\"><br></p><p>우리가 화상 회의를 하면 이런 기분이겠죠??</p><p>왼쪽 위는 제 친구를 꼭 빼닮았네요</p><p>ㅎㅎㅎㅎ (っ °Д °;)っ</p><p><br></p><p>다들 오늘도 수고했어요!!!?</p>',4,2,0,'2023-02-15 20:30:01.000000'),(49,6,100,1,'오늘 자전거 타는 제 모습이에요','<p><br></p><p><br></p><p><img src=\"https://media.giphy.com/media/GYSMIs7kJzGCI/giphy.gif\" alt=\"image\" contenteditable=\"false\"><br></p><p><br></p><p>오늘 하루도 라이딩과 함께...?</p>',6,1,0,'2023-02-15 20:33:38.000000'),(50,6,100,1,'다들 좋은 밤 보내세요?','<p><img src=\"https://media.giphy.com/media/14jRWmyHsokyOY/giphy.gif\" alt=\"image\" contenteditable=\"false\"><br></p><p><br></p><p>꺄울~!!!!!</p><p>잘자요</p>',5,2,0,'2023-02-15 20:38:17.000000'),(51,6,200,1,'혹시 이런 자전거는 어디서 파나요??','<p><img src=\"https://i8e102.p.ssafy.io/api/board/imageDownload/images/board/a8e6d469-5981-4efa-a4fd-5e02ad065a11.png\" alt=\"image\" contenteditable=\"false\"><br></p><p><br></p><p>기계공학을 전공했지만 정말 타고 싶은 자전거네요..</p><p>:D</p><p><br></p><p>다들 어떻게 생각하시나요?</p>',3,1,0,'2023-02-15 20:40:02.000000'),(52,6,200,1,'이 자전거 구할 수 있는 곳 어딘지 아시는 분??','<p><img src=\"https://media.giphy.com/media/UvMwCOiwFFF4wRbMaC/giphy-downsized-large.gif\" alt=\"ㅎㅎ\" contenteditable=\"false\"><br></p><p><br></p><p>제 아들한테 사줄려고 하는데...</p><p>혹시 파는 곳 아시는 분 채팅 부탁해요...</p><p>?</p>',4,1,0,'2023-02-15 20:41:52.000000'),(53,6,100,1,'룰루랄라~ 우리집 강아지와 함께~~','<h2>다들 부럽쥬????</h2><p><br></p><p><img src=\"https://media.giphy.com/media/vLxGKdFWlOxhu/giphy-downsized-large.gif\" alt=\"image\" contenteditable=\"false\"><br></p><p><br></p><p>아이 귀여웡~~❤</p>',4,1,0,'2023-02-15 20:43:37.000000'),(54,8,300,1,'대회 나간 기념','<p><img src=\"https://i8e102.p.ssafy.io/api/board/imageDownload/images/board/9aaeeafc-0897-448b-867a-97db88c1aa34.jpg\" alt=\"image\" contenteditable=\"false\"><br></p><p>휴...</p>',5,1,0,'2023-02-15 20:44:39.000000'),(55,6,300,1,'자전거 대신 코딩하는 제 모습입니다.','<p><img src=\"https://blog.kakaocdn.net/dn/6rsER/btqEEfqxDrX/IGxZcknTuRJ4rWSfmPXNkk/img.gif\" alt=\"image\" contenteditable=\"false\"><br></p><p><br></p><p>오늘도 야근해버려서... 코딩이나 하고 있네요...</p><p>열심히 업무하는 모습 인증합니다..하...</p><p><br></p>',6,0,0,'2023-02-15 20:49:55.000000'),(56,8,100,1,'오늘은 월급날','<p>달다...</p>',4,1,0,'2023-02-15 21:39:37.000000'),(57,3,100,1,'다들 라이딩 자주 하시나요?','<p>전 매주 일요일마다 RideWay 모임게시판을 활용합니다ㅎㅎㅎ</p>',5,1,0,'2023-02-15 22:58:12.000000'),(58,4,100,1,'8시간은 힘드네요ㅋㅋㅋㅋ','<p>지리산 8시간 타고 왔는데 죽을거 같아요 살려주세요</p>',7,1,0,'2023-02-15 22:59:12.000000'),(59,4,200,1,'어떻게 하면 멋지게 탈 수 있을까요?','<h2>고수님들 조언 부탁드려요</h2><ul><li class=\"task-list-item\" data-task=\"true\"><p>장비를 좋은걸 쓴다</p></li><li class=\"task-list-item\" data-task=\"true\"><p>자전거를 꾸준히 오래 탄다</p></li><li class=\"task-list-item\" data-task=\"true\"><p>내가 멋있어져야 한다...</p></li></ul>',2,0,0,'2023-02-15 23:00:52.000000'),(60,3,200,1,'다들 보호대 착용하시나요?','<p>보호대는 잠스트!</p>',3,0,0,'2023-02-15 23:03:43.000000'),(61,3,100,1,'날이 좋아서... 날이 좋지 않아서...','<p>아무것도 하기가 싫다...</p>',8,1,0,'2023-02-15 23:20:58.000000'),(62,6,200,1,'동호회 처음하는데 조언 부탁드립니다.','<p>안녕하세요</p><p>동호회 막 시작한 초보입니다.</p><p>어디서부터 시작해야할지 아니면 초보자를 위한 추천 코스가 있는지 궁금합니다.</p><p><br></p><div contenteditable=\"false\"><hr></div><p>만약 아시는 분 있으시면 꼭 조언 부탁드립니다</p><p>감사합니다.</p>',1,0,0,'2023-02-16 01:01:53.000000'),(63,6,200,1,'자전거 고장 났을 경우 대처법이 무엇인가요?','<p> <img src=\"https://i8e102.p.ssafy.io/api/board/imageDownload/images/board/1fc4d964-80f8-4a87-a220-9e583dfa9e6d.png\" alt=\"image\" contenteditable=\"false\"><br></p><p>가끔 이렇게 벨트가 풀리는 경우가 있거든요</p><p>빠르게 조치하는 법 아시는 분 계신가요?</p>',3,2,0,'2023-02-16 01:03:21.000000'),(64,6,200,1,'다들 자전거 가격이 어느 정도 되나요????','<p>자전거 타는 것도 <strong>장비빨</strong>이라고 하던데....</p><p>혹시 얼마 정도면 괜찮다는 소리 들을까요?</p><p><br></p><p>저는 현재 아래 기종 쓰고 있습니다</p><p><img src=\"https://i8e102.p.ssafy.io/api/board/imageDownload/images/board/46bfac25-9cc1-47ab-acad-19a3420ae76d.png\" alt=\"image\" contenteditable=\"false\"><br></p><p>가성비 은근 좋은 거 같아서요.</p>',2,0,0,'2023-02-16 01:06:59.000000'),(65,6,200,1,'같이 자전거 타실 분 계신가요??','<p>이번에 인천공항 근처에서 자전거 타려고 합니다.</p><p>추천 경로 게시판에서 문득보고 가고 싶더라구요</p><p><br></p><p>같이 갈 분 있으면 댓글 주세요</p><p><br></p><p>모임방 바로 만들겠습니다!!!</p>',6,0,0,'2023-02-16 01:08:19.000000'),(70,9,100,1,'B6 을숙도 주행','<p><img src=\"https://i8e102.p.ssafy.io/api/board/imageDownload/images/board/4a51803d-3deb-4d78-a331-49410e181639.png\" alt=\"image\" contenteditable=\"false\"><br></p><p>열정! 열정! 열정!</p>',8,3,0,'2023-02-16 11:31:04.000000'),(73,6,300,1,'라이딩 후 찍은 제 모습이에요!!!','<blockquote><p><strong>뜨거운 열정이 느껴지지 않나요?</strong></p></blockquote><p><br></p><p><img src=\"https://i8e102.p.ssafy.io/api/board/imageDownload/images/board/2faf6420-bd00-4302-9aea-cec800342012.png\" alt=\"image\" contenteditable=\"false\"><br></p><p><br></p><p>다들 주말 잘 보내시고, 우리 B6 여러분들 사랑합니데이?</p><p><br></p>',2,1,0,'2023-02-17 02:32:41.000000');
/*!40000 ALTER TABLE `board` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `board_code`
--

DROP TABLE IF EXISTS `board_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `board_code` (
  `code` bigint NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `count` bigint DEFAULT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board_code`
--

LOCK TABLES `board_code` WRITE;
/*!40000 ALTER TABLE `board_code` DISABLE KEYS */;
INSERT INTO `board_code` VALUES (100,'자유게시판',0),(200,'질문게시판',0),(300,'인증게시판',0),(400,'정보게시판',0);
/*!40000 ALTER TABLE `board_code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `board_good`
--

DROP TABLE IF EXISTS `board_good`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `board_good` (
  `board_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `selected` bit(1) DEFAULT NULL,
  PRIMARY KEY (`board_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board_good`
--

LOCK TABLES `board_good` WRITE;
/*!40000 ALTER TABLE `board_good` DISABLE KEYS */;
INSERT INTO `board_good` VALUES (29,3,_binary ''),(29,6,_binary ''),(29,10,_binary ''),(42,3,_binary ''),(42,6,_binary ''),(47,3,_binary ''),(47,6,_binary ''),(49,6,_binary ''),(50,6,_binary ''),(50,8,_binary ''),(51,6,_binary ''),(52,5,_binary ''),(53,6,_binary ''),(54,6,_binary ''),(56,6,_binary ''),(57,3,_binary ''),(58,6,_binary ''),(61,6,_binary ''),(63,5,_binary ''),(63,6,_binary ''),(70,5,_binary ''),(70,6,_binary ''),(70,8,_binary '\0'),(70,9,_binary ''),(71,17,_binary ''),(71,19,_binary ''),(73,3,_binary '');
/*!40000 ALTER TABLE `board_good` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `board_image`
--

DROP TABLE IF EXISTS `board_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `board_image` (
  `board_image_id` bigint NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `board_id` bigint DEFAULT NULL,
  PRIMARY KEY (`board_image_id`),
  KEY `FKp567mlnww479xgirmd98kcqnp` (`board_id`),
  CONSTRAINT `FKp567mlnww479xgirmd98kcqnp` FOREIGN KEY (`board_id`) REFERENCES `board` (`board_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board_image`
--

LOCK TABLES `board_image` WRITE;
/*!40000 ALTER TABLE `board_image` DISABLE KEYS */;
/*!40000 ALTER TABLE `board_image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `board_visited`
--

DROP TABLE IF EXISTS `board_visited`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `board_visited` (
  `board_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`board_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board_visited`
--

LOCK TABLES `board_visited` WRITE;
/*!40000 ALTER TABLE `board_visited` DISABLE KEYS */;
INSERT INTO `board_visited` VALUES (29,3),(29,5),(29,6),(29,8),(29,9),(29,10),(29,11),(29,12),(34,11),(35,3),(35,11),(38,11),(39,11),(40,3),(40,6),(40,11),(41,3),(41,4),(41,5),(41,6),(41,11),(42,3),(42,5),(42,6),(42,8),(43,3),(43,5),(43,6),(43,8),(43,11),(44,3),(44,5),(44,6),(44,8),(44,11),(45,3),(45,5),(45,6),(45,9),(46,3),(46,5),(46,6),(47,3),(47,5),(47,6),(47,11),(49,3),(49,5),(49,6),(49,8),(49,11),(49,18),(50,3),(50,5),(50,6),(50,8),(50,11),(51,3),(51,6),(51,8),(52,3),(52,5),(52,6),(52,8),(53,3),(53,6),(53,8),(53,11),(54,3),(54,5),(54,6),(54,8),(54,10),(55,3),(55,5),(55,6),(55,8),(55,10),(55,11),(56,3),(56,5),(56,6),(56,8),(57,3),(57,6),(57,8),(57,11),(57,19),(58,3),(58,4),(58,6),(58,8),(58,9),(58,10),(58,11),(59,3),(59,4),(60,3),(60,5),(60,22),(61,3),(61,5),(61,6),(61,8),(61,9),(61,10),(61,11),(61,20),(62,10),(63,5),(63,6),(63,18),(64,3),(64,6),(65,3),(65,6),(65,11),(65,16),(65,18),(65,20),(70,3),(70,5),(70,6),(70,8),(70,9),(70,10),(70,11),(70,19),(71,6),(71,10),(71,11),(71,13),(71,17),(71,18),(71,19),(71,20),(73,3),(73,6);
/*!40000 ALTER TABLE `board_visited` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `board_vote`
--

DROP TABLE IF EXISTS `board_vote`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `board_vote` (
  `board_vote_id` bigint NOT NULL AUTO_INCREMENT,
  `board_id` bigint NOT NULL,
  `title` varchar(255) NOT NULL COMMENT '투표 제목',
  `content` varchar(255) NOT NULL COMMENT '투표 내용',
  `in_progress` bit(1) NOT NULL DEFAULT b'1' COMMENT '진행중인 투표인지 확인\n1(진행중) , 0(종료)',
  `num` bigint NOT NULL DEFAULT '0' COMMENT '총 투표한 사람 수',
  PRIMARY KEY (`board_vote_id`),
  KEY `FK_board_TO_board_vote_1` (`board_id`),
  CONSTRAINT `FK_board_TO_board_vote_1` FOREIGN KEY (`board_id`) REFERENCES `board` (`board_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board_vote`
--

LOCK TABLES `board_vote` WRITE;
/*!40000 ALTER TABLE `board_vote` DISABLE KEYS */;
/*!40000 ALTER TABLE `board_vote` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `board_voter`
--

DROP TABLE IF EXISTS `board_voter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `board_voter` (
  `vote_id` bigint NOT NULL COMMENT '중복 투표자 확인을 위한 엔티티',
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`vote_id`,`user_id`),
  KEY `FK_user_TO_board_voter_1` (`user_id`),
  CONSTRAINT `FK_board_vote_TO_board_voter_1` FOREIGN KEY (`vote_id`) REFERENCES `board_vote` (`board_vote_id`),
  CONSTRAINT `FK_user_TO_board_voter_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board_voter`
--

LOCK TABLES `board_voter` WRITE;
/*!40000 ALTER TABLE `board_voter` DISABLE KEYS */;
/*!40000 ALTER TABLE `board_voter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cert_info`
--

DROP TABLE IF EXISTS `cert_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cert_info` (
  `email` varchar(50) NOT NULL,
  `code` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cert_info`
--

LOCK TABLES `cert_info` WRITE;
/*!40000 ALTER TABLE `cert_info` DISABLE KEYS */;
INSERT INTO `cert_info` VALUES ('2d29f2132887@drmail.in','698040'),('32132132@3223123.com','622295'),('3e04e4fd0bd0@drmail.in','267846'),('6d137d8ec669@drmail.in','160223'),('758db5f86222@drmail.in','003081'),('7eca00f01eb5@drmail.in','766009'),('acdc1975@naver.com','164379'),('afe5880fa712@drmail.in','055194'),('ba84adfa9680@drmail.in','692477'),('bf12c10dd1a4@drmail.in','767840'),('dlbia2009@naver.com','011999'),('dltkdcksqkqh@naver.com','198866'),('fbbc25cae63e@drmail.in','014245'),('junzzamg9@gmail.com','340493'),('kkyjj123@gmail.com','248884'),('michaia0363@gmail.com','258937'),('michaia0363@naver.com','444101'),('nktion@naver.com','171873'),('nyj3230@naver.com','856784'),('poi5971@naver.com','871704'),('prwoorin@gmail.com','395852'),('rudgns9334@gmail.com','315215'),('sangchan0825@gmail.com','170767'),('tjdnfls12345@gmail.com','435441'),('tjrghks96@naver.com','258280'),('xkhg0611x@naver.com','510989'),('yjkwon1996@gmail.com','619204');
/*!40000 ALTER TABLE `cert_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chatting_message`
--

DROP TABLE IF EXISTS `chatting_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chatting_message` (
  `chat_message_id` bigint NOT NULL AUTO_INCREMENT,
  `sender` bigint NOT NULL,
  `type` int NOT NULL,
  `chatting_room_id` varchar(255) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `send_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `sender_nickname` varchar(255) NOT NULL,
  PRIMARY KEY (`chat_message_id`)
) ENGINE=InnoDB AUTO_INCREMENT=731 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chatting_message`
--

LOCK TABLES `chatting_message` WRITE;
/*!40000 ALTER TABLE `chatting_message` DISABLE KEYS */;
INSERT INTO `chatting_message` VALUES (5,3,1,'01cd6cbf-965f-461c-b913-a61b1022ec8c','zzz','2023-02-13 10:37:33','test1'),(6,3,1,'01cd6cbf-965f-461c-b913-a61b1022ec8c','ㅠㅠ','2023-02-13 10:37:43','test1'),(7,3,1,'940e2963-3f28-419d-8b77-b53f78011f5b','gd','2023-02-13 10:58:07','test1'),(8,3,1,'940e2963-3f28-419d-8b77-b53f78011f5b','됨?','2023-02-13 10:58:12','test1'),(9,3,1,'5b98050b-e886-4113-9882-dff7dbef0e29','ㅇㅇ','2023-02-13 10:58:28','test1'),(10,3,1,'5b98050b-e886-4113-9882-dff7dbef0e29','ㅂㅂ','2023-02-13 11:00:53','test1'),(11,3,1,'01cd6cbf-965f-461c-b913-a61b1022ec8c','ㅋㅋㅋ','2023-02-13 11:04:27','test1'),(12,4,1,'01cd6cbf-965f-461c-b913-a61b1022ec8c','ㅎㅇㅎㅇ','2023-02-13 11:37:29','test2'),(13,4,1,'4679d774-062f-402a-9630-829b6261602a','dd','2023-02-13 11:56:35','test2'),(14,4,1,'01cd6cbf-965f-461c-b913-a61b1022ec8c','아니..','2023-02-13 11:56:39','test2'),(15,3,1,'940e2963-3f28-419d-8b77-b53f78011f5b','dd','2023-02-13 12:07:51','test1'),(16,3,1,'940e2963-3f28-419d-8b77-b53f78011f5b','ㄴㄴ','2023-02-13 12:08:07','test1'),(17,4,1,'01cd6cbf-965f-461c-b913-a61b1022ec8c','ss','2023-02-13 12:08:27','test2'),(18,3,1,'01cd6cbf-965f-461c-b913-a61b1022ec8c','뭐야','2023-02-13 12:08:32','test1'),(19,3,1,'01cd6cbf-965f-461c-b913-a61b1022ec8c','ㄴㄴ','2023-02-13 12:10:42','test1'),(20,4,1,'01cd6cbf-965f-461c-b913-a61b1022ec8c','zzz','2023-02-13 12:12:17','test2'),(21,4,1,'01cd6cbf-965f-461c-b913-a61b1022ec8c','sss','2023-02-13 13:59:16','test2'),(22,4,1,'01cd6cbf-965f-461c-b913-a61b1022ec8c','아니','2023-02-13 14:05:44','test2'),(23,4,1,'01cd6cbf-965f-461c-b913-a61b1022ec8c','뭐야','2023-02-13 14:06:42','test2'),(24,3,1,'01cd6cbf-965f-461c-b913-a61b1022ec8c','하이','2023-02-13 14:26:35','test1'),(25,3,1,'01cd6cbf-965f-461c-b913-a61b1022ec8c','??','2023-02-13 14:30:50','test1'),(26,4,1,'4679d774-062f-402a-9630-829b6261602a','ㄴㄴ','2023-02-13 14:49:35','test2'),(27,3,1,'5b98050b-e886-4113-9882-dff7dbef0e29','bb','2023-02-13 15:42:44','test1'),(28,3,1,'5b98050b-e886-4113-9882-dff7dbef0e29','..','2023-02-13 15:43:03','test1'),(29,4,1,'4679d774-062f-402a-9630-829b6261602a',';;','2023-02-13 15:43:10','test2'),(30,4,1,'01cd6cbf-965f-461c-b913-a61b1022ec8c',';;','2023-02-13 15:43:18','test2'),(31,3,1,'01cd6cbf-965f-461c-b913-a61b1022ec8c','ㅎㅇ','2023-02-13 16:20:35','test1'),(32,4,1,'01cd6cbf-965f-461c-b913-a61b1022ec8c','ㅎㅇㅎㅇ','2023-02-13 16:20:56','test2'),(33,3,1,'01cd6cbf-965f-461c-b913-a61b1022ec8c','ㅇㅇ','2023-02-13 16:21:16','test1'),(34,3,1,'01cd6cbf-965f-461c-b913-a61b1022ec8c','뭐야 또','2023-02-13 16:24:38','test1'),(35,3,1,'01cd6cbf-965f-461c-b913-a61b1022ec8c','하','2023-02-13 16:25:04','test1'),(36,13,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','테스트','2023-02-13 16:27:11','mmmmm'),(37,4,1,'01cd6cbf-965f-461c-b913-a61b1022ec8c','하이','2023-02-13 16:27:31','test2'),(38,4,1,'01cd6cbf-965f-461c-b913-a61b1022ec8c','ㅎㅇㅎㅇ','2023-02-13 16:27:40','test2'),(39,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','하이','2023-02-13 16:28:24','test1'),(40,13,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','안녕','2023-02-13 16:29:17','mmmmm'),(41,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㅋㅋㅋㅋㅋ','2023-02-13 16:29:26','test1'),(42,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','알람이 안떠','2023-02-13 16:29:29','test1'),(43,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','비동기 지옥','2023-02-13 16:29:38','test1'),(44,3,1,'01cd6cbf-965f-461c-b913-a61b1022ec8c','ㅎㅇㅎㅇ','2023-02-13 16:30:18','test1'),(45,3,1,'01cd6cbf-965f-461c-b913-a61b1022ec8c','ㅋㅋㅋ','2023-02-13 16:30:20','test1'),(46,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','도현도현','2023-02-13 16:31:08','test1'),(47,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','나갔다가 다시 들어옴','2023-02-13 16:32:12','test1'),(48,4,1,'01cd6cbf-965f-461c-b913-a61b1022ec8c',';;;','2023-02-13 16:32:29','test2'),(49,13,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','테스트','2023-02-13 16:32:36','mmmmm'),(50,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','텟텟','2023-02-13 16:36:29','test1'),(51,4,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','테스트','2023-02-13 16:36:48','test2'),(52,4,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','테스트','2023-02-13 16:37:00','test2'),(53,5,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','하이','2023-02-13 16:41:00','test3'),(54,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㅎㅇ','2023-02-13 16:41:10','test1'),(55,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㅋㅋㅋ','2023-02-13 16:42:32','test1'),(56,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㅋㅋㅋ','2023-02-13 16:43:06','test1'),(57,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㅎㅎ','2023-02-13 16:43:33','test1'),(58,4,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','뭐가 이래 많아','2023-02-13 16:44:21','test2'),(59,5,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','아','2023-02-13 16:44:29','test3'),(60,5,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㅋㅋㅋ','2023-02-13 16:45:29','test3'),(61,5,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㄷㄷ','2023-02-13 16:47:22','test3'),(62,5,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','아','2023-02-13 16:48:52','test3'),(63,5,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㄷㄷ','2023-02-13 16:48:57','test3'),(64,5,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㄷㄷㄷ','2023-02-13 16:49:01','test3'),(65,5,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㅁ','2023-02-13 16:49:12','test3'),(66,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c',';l;;;;','2023-02-13 17:13:11','test1'),(67,4,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','제ㅐ발..','2023-02-13 17:14:56','test2'),(68,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','방을 나가셨습니다','2023-02-13 17:18:08','test1'),(69,3,0,'d639f45d-db98-4836-bbb1-c8c725f25d5c','방을 나가셨습니다  -------  test1님이 입장하셨습니다.','2023-02-13 17:18:43','test1'),(70,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','dd','2023-02-13 17:19:27','test1'),(71,4,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㅇㅇ','2023-02-13 17:19:28','test2'),(72,4,0,'d639f45d-db98-4836-bbb1-c8c725f25d5c','null  -------  test2님이 입장하셨습니다.','2023-02-13 17:19:56','test2'),(73,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ee','2023-02-13 17:20:10','test1'),(74,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㄷㄷ','2023-02-13 17:22:25','test1'),(75,4,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','......','2023-02-13 17:22:48','test2'),(76,4,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㅁㅁ','2023-02-13 17:23:22','test2'),(77,4,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㅂㅈㄷㄱ','2023-02-13 17:23:48','test2'),(78,4,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','가즈아','2023-02-13 17:53:41','test2'),(79,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','어딜가?','2023-02-13 17:53:47','test1'),(80,4,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㄹㅇㅋㅋ','2023-02-13 17:53:51','test2'),(81,4,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㅎㅇ','2023-02-14 08:59:30','test2'),(82,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','gd','2023-02-14 08:59:38','Curry'),(83,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㅎㅇ','2023-02-14 08:59:40','Curry'),(84,4,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','','2023-02-14 08:59:42','test2'),(85,4,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','??','2023-02-14 08:59:43','test2'),(86,4,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','','2023-02-14 08:59:45','test2'),(87,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','뭐야','2023-02-14 08:59:52','Curry'),(88,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㄷㄷ','2023-02-14 09:03:18','Curry'),(89,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㄷㄷ','2023-02-14 09:05:13','Curry'),(90,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㄷㄷ','2023-02-14 09:05:32','Curry'),(91,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㄷㄷ','2023-02-14 09:05:46','Curry'),(92,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㄷㄷ','2023-02-14 09:06:15','Curry'),(93,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㄷㄷ','2023-02-14 09:06:51','Curry'),(94,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㄷㄷ','2023-02-14 09:07:12','Curry'),(95,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㄷㄷ','2023-02-14 09:07:24','Curry'),(96,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㄷㄷ','2023-02-14 09:07:36','Curry'),(97,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ee','2023-02-14 09:10:12','Curry'),(98,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㄷㄷ','2023-02-14 09:10:44','Curry'),(99,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㅂㅂ','2023-02-14 09:11:21','Curry'),(100,4,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','뭐야','2023-02-14 09:13:04','test2'),(101,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㄷㄷ','2023-02-14 09:13:54','Curry'),(102,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㄴㄴ','2023-02-14 09:15:05','Curry'),(103,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㄴㄴ','2023-02-14 09:15:22','Curry'),(104,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㅈㅈ','2023-02-14 09:16:13','Curry'),(105,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㅈㅈ','2023-02-14 09:16:31','Curry'),(106,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㅈㅈ','2023-02-14 09:16:38','Curry'),(107,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㄷㄷ','2023-02-14 09:17:24','Curry'),(108,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㅈㅈ','2023-02-14 09:17:47','Curry'),(109,4,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㄱㄱ','2023-02-14 09:17:55','test2'),(110,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㅈㅈ','2023-02-14 09:17:58','Curry'),(111,4,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㅈㅈ','2023-02-14 09:20:15','test2'),(112,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㄷㄷ','2023-02-14 09:23:18','Curry'),(113,4,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㄷㄷ','2023-02-14 09:38:04','test2'),(114,4,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c',';;;','2023-02-14 09:40:46','test2'),(115,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㅎㅇ','2023-02-14 09:40:59','Curry'),(116,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','??','2023-02-14 09:42:45','Curry'),(117,4,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','??','2023-02-14 09:42:48','test2'),(118,4,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','뭔데 또','2023-02-14 09:43:00','test2'),(119,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','하..','2023-02-14 09:43:34','Curry'),(120,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','아','2023-02-14 09:43:38','Curry'),(121,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','..','2023-02-14 09:43:49','Curry'),(122,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㅋㅋㅋㅋ','2023-02-14 09:47:17','Curry'),(123,4,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㅎㅇ','2023-02-14 10:00:58','test2'),(124,4,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㅋㅋㅋㅋㅋ','2023-02-14 10:01:22','test2'),(125,3,1,'01cd6cbf-965f-461c-b913-a61b1022ec8c','??','2023-02-14 10:08:02','Curry'),(126,4,1,'01cd6cbf-965f-461c-b913-a61b1022ec8c','ㅎㅇ','2023-02-14 10:22:35','test2'),(127,3,1,'01cd6cbf-965f-461c-b913-a61b1022ec8c','ㅇㅇ','2023-02-14 10:23:03','Curry'),(128,4,1,'01cd6cbf-965f-461c-b913-a61b1022ec8c','ㅇㅇ','2023-02-14 10:26:57','test2'),(129,3,1,'01cd6cbf-965f-461c-b913-a61b1022ec8c','ㅎㅇ','2023-02-14 10:28:54','Curry'),(130,3,1,'01cd6cbf-965f-461c-b913-a61b1022ec8c','ㅋㅋㅋㅋㅋㅋ','2023-02-14 10:29:20','Curry'),(131,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','하이','2023-02-14 10:33:25','Curry'),(132,4,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','하이하이','2023-02-14 10:33:32','test2'),(133,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','하이','2023-02-14 10:39:02','Curry'),(134,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㄷㄷ','2023-02-14 10:41:51','Curry'),(135,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㄷㄷ','2023-02-14 10:42:28','Curry'),(136,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㅁㄴㅇㄻㄴㅇㄹ','2023-02-14 10:42:41','Curry'),(137,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c',';;','2023-02-14 10:43:01','Curry'),(138,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㅂㅈㄷㄱ','2023-02-14 10:43:11','Curry'),(139,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㅅㅅ','2023-02-14 10:44:22','Curry'),(140,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㄷㄷ','2023-02-14 10:45:17','Curry'),(141,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c',';;','2023-02-14 10:45:44','Curry'),(142,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㄷㄷ','2023-02-14 10:50:01','Curry'),(143,4,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㅎㅇ','2023-02-14 10:56:45','test2'),(144,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㄷㄷ','2023-02-14 10:57:06','Curry'),(145,4,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','안녕','2023-02-14 10:57:19','test2'),(146,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㅎㅇ','2023-02-14 11:23:05','Curry'),(147,13,1,'1bc5c842-5001-4389-b8bc-057405bcdc6d','안녕','2023-02-14 11:24:37','mmmmm'),(148,3,1,'1bc5c842-5001-4389-b8bc-057405bcdc6d','안녕','2023-02-14 11:24:46','Curry'),(149,4,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','하이','2023-02-14 12:15:26','test2'),(150,4,1,'940e2963-3f28-419d-8b77-b53f78011f5b',';;','2023-02-14 13:11:33','test2'),(151,14,1,'98adfd93-8619-4a4c-971d-19e2a538911b','gdgd','2023-02-14 15:01:21','동팔'),(152,14,1,'98adfd93-8619-4a4c-971d-19e2a538911b','gdgdgdg','2023-02-14 15:01:23','동팔'),(153,14,1,'98adfd93-8619-4a4c-971d-19e2a538911b','오오오','2023-02-14 15:01:25','동팔'),(154,14,1,'98adfd93-8619-4a4c-971d-19e2a538911b','쥑이누','2023-02-14 15:01:26','동팔'),(155,12,1,'940e2963-3f28-419d-8b77-b53f78011f5b','오와','2023-02-14 15:16:52','효코치'),(156,12,1,'940e2963-3f28-419d-8b77-b53f78011f5b','우와아','2023-02-14 15:16:59','효코치'),(157,3,1,'1bc5c842-5001-4389-b8bc-057405bcdc6d','zzzz','2023-02-14 17:22:39','Curry'),(158,3,1,'1bc5c842-5001-4389-b8bc-057405bcdc6d','zzz','2023-02-14 17:28:18','Curry'),(159,3,1,'1bc5c842-5001-4389-b8bc-057405bcdc6d','ㄷㄷ','2023-02-14 17:28:48','Curry'),(160,3,1,'1bc5c842-5001-4389-b8bc-057405bcdc6d','ㄷㄷ','2023-02-15 01:21:15','최형규'),(161,3,1,'1bc5c842-5001-4389-b8bc-057405bcdc6d','tt','2023-02-15 02:25:29','최형규'),(162,3,1,'1bc5c842-5001-4389-b8bc-057405bcdc6d','앱에서 한번 테스트로 써봅니다','2023-02-15 02:26:27','최형규'),(163,3,1,'1bc5c842-5001-4389-b8bc-057405bcdc6d','영상테스트 완료?','2023-02-15 09:11:07','최형규'),(164,3,1,'1bc5c842-5001-4389-b8bc-057405bcdc6d','아무거나','2023-02-15 09:31:16','최형규'),(165,3,1,'1bc5c842-5001-4389-b8bc-057405bcdc6d','sss','2023-02-15 09:31:21','최형규'),(166,3,1,'1bc5c842-5001-4389-b8bc-057405bcdc6d','qq','2023-02-15 09:36:07','최형규'),(167,3,1,'1bc5c842-5001-4389-b8bc-057405bcdc6d','dd','2023-02-15 09:36:37','최형규'),(168,3,1,'1bc5c842-5001-4389-b8bc-057405bcdc6d','하이요','2023-02-15 09:36:44','최형규'),(169,3,1,'1bc5c842-5001-4389-b8bc-057405bcdc6d','ㅇㅇㅇㅇㅇ','2023-02-15 09:36:54','최형규'),(170,4,1,'1bc5c842-5001-4389-b8bc-057405bcdc6d','ㅎㅇㅎㅇ','2023-02-15 09:37:05','test2'),(171,4,1,'1bc5c842-5001-4389-b8bc-057405bcdc6d','ㅎㅇㅎㅇ','2023-02-15 09:37:21','test2'),(172,4,1,'1bc5c842-5001-4389-b8bc-057405bcdc6d','안녕','2023-02-15 09:37:22','test2'),(173,3,1,'1bc5c842-5001-4389-b8bc-057405bcdc6d','ㄹㄹㄹ','2023-02-15 09:37:30','최형규'),(174,9,1,'1bc5c842-5001-4389-b8bc-057405bcdc6d','뭐임?','2023-02-15 09:37:38','michael0363'),(175,4,1,'1bc5c842-5001-4389-b8bc-057405bcdc6d','몰라','2023-02-15 09:37:42','test2'),(176,4,1,'1bc5c842-5001-4389-b8bc-057405bcdc6d','앱 이상해','2023-02-15 09:37:44','test2'),(177,9,1,'1bc5c842-5001-4389-b8bc-057405bcdc6d','00:37','2023-02-15 09:37:49','michael0363'),(178,3,1,'1bc5c842-5001-4389-b8bc-057405bcdc6d','ㄷㄷㄷ','2023-02-15 09:38:43','최형규'),(179,4,1,'1bc5c842-5001-4389-b8bc-057405bcdc6d','안녕','2023-02-15 09:43:06','test2'),(180,3,1,'1bc5c842-5001-4389-b8bc-057405bcdc6d','dd','2023-02-15 09:54:56','최형규'),(181,3,1,'1bc5c842-5001-4389-b8bc-057405bcdc6d','asdsd','2023-02-15 09:55:15','최형규'),(182,3,1,'1bc5c842-5001-4389-b8bc-057405bcdc6d','ss','2023-02-15 09:56:25','최형규'),(183,4,1,'1bc5c842-5001-4389-b8bc-057405bcdc6d','하이하이','2023-02-15 09:56:40','test2'),(184,3,1,'1bc5c842-5001-4389-b8bc-057405bcdc6d','안녕','2023-02-15 09:57:01','최형규'),(185,3,1,'1bc5c842-5001-4389-b8bc-057405bcdc6d','하이요','2023-02-15 09:57:06','최형규'),(186,4,1,'1bc5c842-5001-4389-b8bc-057405bcdc6d','코로나 저리가!','2023-02-15 09:57:15','test2'),(187,3,1,'1bc5c842-5001-4389-b8bc-057405bcdc6d','하하 잘도니다.','2023-02-15 09:58:05','최형규'),(188,4,1,'1bc5c842-5001-4389-b8bc-057405bcdc6d','좋아요~~','2023-02-15 09:58:14','test2'),(189,4,1,'1bc5c842-5001-4389-b8bc-057405bcdc6d','배포에서 테스트','2023-02-15 10:10:26','test2'),(190,3,1,'1bc5c842-5001-4389-b8bc-057405bcdc6d','ddd','2023-02-15 10:11:47','최형규'),(191,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','ㅎㅇ','2023-02-15 10:52:05','최형규'),(192,9,1,'1bc5c842-5001-4389-b8bc-057405bcdc6d','테스트테스트','2023-02-15 11:33:22','michael0363'),(193,3,1,'1bc5c842-5001-4389-b8bc-057405bcdc6d','테스트테스트','2023-02-15 11:33:47','최형규'),(194,9,1,'1bc5c842-5001-4389-b8bc-057405bcdc6d','뭐지','2023-02-15 15:40:25','michael0363'),(195,3,1,'1bc5c842-5001-4389-b8bc-057405bcdc6d','채팅','2023-02-15 15:40:28','최형규'),(196,3,1,'1bc5c842-5001-4389-b8bc-057405bcdc6d','하이','2023-02-15 15:40:34','최형규'),(197,3,1,'1bc5c842-5001-4389-b8bc-057405bcdc6d','뭐야','2023-02-15 15:40:37','최형규'),(198,3,1,'1bc5c842-5001-4389-b8bc-057405bcdc6d','헉','2023-02-15 15:40:38','최형규'),(199,9,1,'1bc5c842-5001-4389-b8bc-057405bcdc6d','어저구','2023-02-15 15:57:15','michael0363'),(200,3,1,'1bc5c842-5001-4389-b8bc-057405bcdc6d','저저구','2023-02-15 15:57:23','최형규'),(201,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','메이데이 메이데이','2023-02-15 23:08:14','최형규'),(202,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','응답하라 오바','2023-02-15 23:08:24','최형규'),(203,4,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','하이','2023-02-15 23:08:56','라이딩의 신'),(204,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','오 ㅎㅇ','2023-02-15 23:09:10','최형규'),(205,3,1,'01cd6cbf-965f-461c-b913-a61b1022ec8c','ddd','2023-02-16 05:10:52','최형규'),(206,3,1,'01cd6cbf-965f-461c-b913-a61b1022ec8c','ss','2023-02-16 05:24:24','최형규'),(207,3,1,'01cd6cbf-965f-461c-b913-a61b1022ec8c','ss','2023-02-16 05:25:02','최형규'),(208,9,1,'843726a6-60e6-4033-9aef-74f267b75126','안녕','2023-02-16 08:41:37','michael0363'),(209,3,1,'01cd6cbf-965f-461c-b913-a61b1022ec8c','ㅇ','2023-02-16 10:47:48','최형규'),(210,3,1,'01cd6cbf-965f-461c-b913-a61b1022ec8c','ㅇㅈ','2023-02-16 10:47:54','최형규'),(211,3,1,'01cd6cbf-965f-461c-b913-a61b1022ec8c','ㅇㅈ','2023-02-16 10:47:56','최형규'),(212,3,1,'01cd6cbf-965f-461c-b913-a61b1022ec8c','ㅈㅇ','2023-02-16 10:47:57','최형규'),(213,3,1,'01cd6cbf-965f-461c-b913-a61b1022ec8c','ㅇㅈ','2023-02-16 10:47:58','최형규'),(214,3,1,'01cd6cbf-965f-461c-b913-a61b1022ec8c','ㄹㅇㄹㅇ','2023-02-16 10:50:09','최형규'),(215,3,1,'01cd6cbf-965f-461c-b913-a61b1022ec8c','ㄹㅇㄹㅇ','2023-02-16 10:50:11','최형규'),(216,3,1,'01cd6cbf-965f-461c-b913-a61b1022ec8c','ㅇㄹㅇㄹ','2023-02-16 10:50:18','최형규'),(217,3,1,'01cd6cbf-965f-461c-b913-a61b1022ec8c','ㅇㄹㅇ','2023-02-16 10:50:19','최형규'),(218,3,1,'01cd6cbf-965f-461c-b913-a61b1022ec8c','ㅇㄹ','2023-02-16 10:50:19','최형규'),(219,3,1,'01cd6cbf-965f-461c-b913-a61b1022ec8c','ㅇㄹ','2023-02-16 10:50:20','최형규'),(220,3,1,'01cd6cbf-965f-461c-b913-a61b1022ec8c','ㅇㄹㅇㄹ','2023-02-16 10:50:21','최형규'),(221,3,1,'01cd6cbf-965f-461c-b913-a61b1022ec8c','ㅇ','2023-02-16 10:50:21','최형규'),(222,3,1,'01cd6cbf-965f-461c-b913-a61b1022ec8c','ㅇㄹㅇ','2023-02-16 10:50:27','최형규'),(223,3,1,'01cd6cbf-965f-461c-b913-a61b1022ec8c','ㅇㄹ','2023-02-16 10:50:27','최형규'),(224,3,1,'01cd6cbf-965f-461c-b913-a61b1022ec8c','ㅇㄹㅇㄹㅇ','2023-02-16 10:50:32','최형규'),(225,13,1,'843726a6-60e6-4033-9aef-74f267b75126','안녕하세요!','2023-02-16 11:02:08','mmmmm'),(226,13,1,'843726a6-60e6-4033-9aef-74f267b75126','내일 몇시에 모이나요?','2023-02-16 11:02:50','mmmmm'),(227,6,1,'843726a6-60e6-4033-9aef-74f267b75126','sss','2023-02-16 12:14:31','이상찬'),(228,11,1,'dab00c4d-1fcb-4a01-802e-7837a8702f0d','하이요!\n','2023-02-16 13:53:20','가나다라'),(229,10,1,'dab00c4d-1fcb-4a01-802e-7837a8702f0d','aa','2023-02-16 13:54:03','레어닉'),(230,11,1,'dab00c4d-1fcb-4a01-802e-7837a8702f0d','ㄴㄴㄴ','2023-02-16 14:06:35','가나다라'),(231,9,1,'843726a6-60e6-4033-9aef-74f267b75126','저는 되는거같아요','2023-02-16 14:06:41','michael0363'),(232,10,1,'dab00c4d-1fcb-4a01-802e-7837a8702f0d','sss','2023-02-16 14:06:45','레어닉'),(233,10,1,'dab00c4d-1fcb-4a01-802e-7837a8702f0d','sdsd','2023-02-16 14:06:49','레어닉'),(234,13,1,'843726a6-60e6-4033-9aef-74f267b75126','그러네요','2023-02-16 14:07:06','mmmmm'),(235,10,1,'dab00c4d-1fcb-4a01-802e-7837a8702f0d','ee','2023-02-16 14:09:33','레어닉'),(236,11,1,'dab00c4d-1fcb-4a01-802e-7837a8702f0d','ㅇㅇ','2023-02-16 14:11:01','가나다라'),(237,10,1,'dab00c4d-1fcb-4a01-802e-7837a8702f0d','ss','2023-02-16 14:11:12','레어닉'),(238,10,1,'dab00c4d-1fcb-4a01-802e-7837a8702f0d','zzz','2023-02-16 14:19:54','레어닉'),(239,11,1,'dab00c4d-1fcb-4a01-802e-7837a8702f0d','ㅇㅇ','2023-02-16 14:20:00','가나다라'),(240,13,1,'843726a6-60e6-4033-9aef-74f267b75126','안녕하세요','2023-02-16 14:55:58','mmmmm'),(241,9,1,'843726a6-60e6-4033-9aef-74f267b75126','안녕하세요','2023-02-16 15:07:00','도현'),(242,9,1,'843726a6-60e6-4033-9aef-74f267b75126','내일 언제 쯤 볼까요?','2023-02-16 15:07:16','도현'),(243,13,1,'843726a6-60e6-4033-9aef-74f267b75126','내일 오전에 뵙죠','2023-02-16 15:09:15','용진'),(244,13,1,'843726a6-60e6-4033-9aef-74f267b75126','zzz','2023-02-16 15:10:31','용진'),(245,9,1,'843726a6-60e6-4033-9aef-74f267b75126','용진씨 어디에요!','2023-02-16 15:12:31','우린'),(246,9,1,'843726a6-60e6-4033-9aef-74f267b75126','용진씨 빨리 오십시오!','2023-02-16 15:14:15','우린'),(247,13,1,'843726a6-60e6-4033-9aef-74f267b75126','아 지금 가고 있습니다','2023-02-16 15:14:26','용진'),(248,10,1,'843726a6-60e6-4033-9aef-74f267b75126','ss','2023-02-16 15:20:07','레어닉'),(249,9,1,'843726a6-60e6-4033-9aef-74f267b75126','안녕하세요','2023-02-16 15:20:20','우린'),(250,9,1,'843726a6-60e6-4033-9aef-74f267b75126','내일 오전 9시 까지입니다','2023-02-16 15:20:39','우린'),(251,9,1,'843726a6-60e6-4033-9aef-74f267b75126','날이 추우니 따뜻하게들 입고 오시고','2023-02-16 15:22:10','우린'),(252,9,1,'843726a6-60e6-4033-9aef-74f267b75126','보호장비 꼭 챙겨서 오십시오','2023-02-16 15:22:20','우린'),(253,10,1,'843726a6-60e6-4033-9aef-74f267b75126','ㅇㅇ','2023-02-16 15:23:18','레어닉'),(254,10,1,'843726a6-60e6-4033-9aef-74f267b75126','네 알겠습니다','2023-02-16 15:23:46','레어닉'),(255,9,1,'843726a6-60e6-4033-9aef-74f267b75126','용진씨 늦었잖아요','2023-02-16 15:24:09','우린'),(256,9,1,'843726a6-60e6-4033-9aef-74f267b75126','내일 오전 9시 까지입니다','2023-02-16 15:24:45','우린'),(257,9,1,'843726a6-60e6-4033-9aef-74f267b75126','날이 추우니 따뜻하게들 입고 오시고','2023-02-16 15:24:49','우린'),(258,9,1,'843726a6-60e6-4033-9aef-74f267b75126','보호장비 꼭 챙겨서 오십시오','2023-02-16 15:24:53','우린'),(259,10,1,'843726a6-60e6-4033-9aef-74f267b75126','네 알겠습니다.','2023-02-16 15:25:09','레어닉'),(260,9,1,'843726a6-60e6-4033-9aef-74f267b75126','용진씨 지금 어디에요?','2023-02-16 15:25:30','우린'),(261,9,1,'843726a6-60e6-4033-9aef-74f267b75126','늦었습니다','2023-02-16 15:25:32','우린'),(262,10,1,'843726a6-60e6-4033-9aef-74f267b75126','미안합니다. 지금 가고 있습니다!!','2023-02-16 15:25:39','레어닉'),(263,10,1,'dab00c4d-1fcb-4a01-802e-7837a8702f0d','ㅂㅂ','2023-02-16 16:52:52','레어닉'),(264,11,1,'dab00c4d-1fcb-4a01-802e-7837a8702f0d','dddd','2023-02-16 17:01:52','가나다라'),(265,10,1,'dab00c4d-1fcb-4a01-802e-7837a8702f0d','ㅇㅇ','2023-02-16 17:01:58','레어닉'),(266,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','됐죠?','2023-02-16 17:41:28','최형규'),(267,6,1,'7f51928c-5f26-4965-b42d-52d826745764','혹시 여기 방에 계시는분','2023-02-16 17:43:39','이상찬'),(268,6,1,'7f51928c-5f26-4965-b42d-52d826745764','저 채팅 처음 쳐보거든요','2023-02-16 17:43:43','이상찬'),(269,8,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','ㅇㅅㅇ','2023-02-16 17:44:58','권용'),(270,6,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','누구 있나요','2023-02-16 17:45:01','이상찬'),(271,6,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','ㅇㅅㅇ님','2023-02-16 17:45:07','이상찬'),(272,6,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','undefined뜨는데','2023-02-16 17:45:11','이상찬'),(273,6,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','누구세요?','2023-02-16 17:45:13','이상찬'),(274,8,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','??','2023-02-16 17:45:16','권용'),(275,8,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','!!','2023-02-16 17:46:08','권용'),(276,8,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','ㅁㅅㅁ','2023-02-16 17:47:43','권용'),(277,6,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','채팅도 되나요?','2023-02-16 17:50:42','이상찬'),(278,8,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','네','2023-02-16 17:50:45','권용'),(279,8,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','ㅔ','2023-02-16 17:50:53','권용'),(280,8,1,'18f261b9-5e4a-44eb-82de-a130dad425f1',';','2023-02-16 17:50:54','권용'),(281,6,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','음','2023-02-16 17:50:56','이상찬'),(282,8,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','ㅁ','2023-02-16 17:50:58','권용'),(283,8,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','머지','2023-02-16 17:51:19','권용'),(284,8,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','화상','2023-02-16 17:51:45','권용'),(285,8,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','ㅁㄹ','2023-02-16 17:52:01','권용'),(286,6,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','안오네요','2023-02-16 17:52:11','이상찬'),(287,8,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','123','2023-02-16 17:52:23','권용'),(288,8,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','1','2023-02-16 17:52:38','권용'),(289,8,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','2','2023-02-16 17:53:12','권용'),(290,6,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','333','2023-02-16 17:56:26','이상찬'),(291,6,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','44','2023-02-16 17:56:27','이상찬'),(292,6,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','1','2023-02-16 17:56:29','이상찬'),(293,6,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','2','2023-02-16 17:56:29','이상찬'),(294,6,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','34','2023-02-16 17:56:30','이상찬'),(295,6,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','3','2023-02-16 17:56:31','이상찬'),(296,6,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','2','2023-02-16 17:56:31','이상찬'),(297,8,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','123\\','2023-02-16 17:56:37','권용'),(298,6,1,'7f51928c-5f26-4965-b42d-52d826745764','채팅','2023-02-16 17:56:41','이상찬'),(299,8,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','4444','2023-02-16 17:56:45','권용'),(300,8,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','123','2023-02-16 17:57:12','권용'),(301,6,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','채팅','2023-02-16 17:57:15','이상찬'),(302,8,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','456','2023-02-16 17:57:18','권용'),(303,8,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','789','2023-02-16 17:57:19','권용'),(304,3,1,'d639f45d-db98-4836-bbb1-c8c725f25d5c','??','2023-02-16 17:57:37','최형규'),(305,6,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','이이야','2023-02-16 17:58:16','이상찬'),(306,8,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','5654','2023-02-16 17:58:24','권용'),(307,6,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','용진님이 안보인데','2023-02-16 17:58:25','이상찬'),(308,8,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','1234','2023-02-16 17:58:27','권용'),(309,6,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','안 가고 안 보임','2023-02-16 17:58:31','이상찬'),(310,8,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','98456','2023-02-16 17:58:56','권용'),(311,8,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','123','2023-02-16 17:58:59','권용'),(312,8,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','45963','2023-02-16 17:59:01','권용'),(313,8,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','ㅈㄷㅂㄱㄷ 23ㅇㄴㄹ 232 23ㄴㅇㄹ','2023-02-16 17:59:04','권용'),(314,8,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','32523 ㅅ234 ㄴㅇㅁㄴㅇ','2023-02-16 17:59:08','권용'),(315,6,1,'7f51928c-5f26-4965-b42d-52d826745764','채팅','2023-02-16 17:59:11','이상찬'),(316,8,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','ㅁㄴㅇ ㅈㄷ2345ㅠㄱ  ㄷㄱ ㅗㄳㄷ ㅁ','2023-02-16 17:59:12','권용'),(317,8,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','ㄷㅈㅈㄷㄹㅈㄷㄹㅈㅁㄷㄴㅇㅌㅋㅇㅍㅌㅇㅍㅇㅌㅍㅌㅊㅍㅌㅊㅍㅌㅊㅍㅌㅊㅍㅋㅌㅊㅍㅌㅊㅍㅍㄴㄴㅁㄴ ㅈㄷㄹㄷㅈ','2023-02-16 17:59:17','권용'),(318,6,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','ㅇㅇㅇ','2023-02-16 17:59:39','이상찬'),(319,6,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','들리나요','2023-02-16 17:59:42','이상찬'),(320,8,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','656152131232','2023-02-16 17:59:44','권용'),(321,6,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','헬로우','2023-02-16 17:59:47','이상찬'),(322,8,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','5646465132133','2023-02-16 17:59:48','권용'),(323,6,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','에브리바디','2023-02-16 17:59:50','이상찬'),(324,8,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','98797984456432120','2023-02-16 17:59:53','권용'),(325,3,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','네','2023-02-16 17:59:57','최형규'),(326,8,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','8998764564545612331','2023-02-16 17:59:58','권용'),(327,6,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','채팅이 되어야하는데','2023-02-16 17:59:59','이상찬'),(328,6,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','용진님이랑','2023-02-16 18:00:04','이상찬'),(329,8,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','45645','2023-02-16 18:00:05','권용'),(330,3,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','뭐야','2023-02-16 18:00:09','최형규'),(331,3,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','뭐지','2023-02-16 18:00:26','최형규'),(332,8,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','788978998','2023-02-16 18:00:29','권용'),(333,8,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','45665456','2023-02-16 18:00:30','권용'),(334,8,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','123321132','2023-02-16 18:00:31','권용'),(335,3,1,'18f261b9-5e4a-44eb-82de-a130dad425f1',';;','2023-02-16 18:00:33','최형규'),(336,3,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','왜 언디파인드지','2023-02-16 18:00:37','최형규'),(337,3,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','상찬님','2023-02-16 18:00:43','최형규'),(338,3,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','개발자도구','2023-02-16 18:00:46','최형규'),(339,3,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','보내봐요','2023-02-16 18:01:09','최형규'),(340,3,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','아니','2023-02-16 18:01:20','최형규'),(341,6,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','','2023-02-16 18:02:31','이상찬'),(342,6,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','sss','2023-02-16 18:02:33','이상찬'),(343,3,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','왜','2023-02-16 18:02:34','최형규'),(344,3,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','안돼','2023-02-16 18:02:35','최형규'),(345,3,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','갑자기 또 되는데?','2023-02-16 18:02:41','최형규'),(346,6,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','그니까 왜 안되네','2023-02-16 18:02:42','이상찬'),(347,3,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','또 ','2023-02-16 18:02:43','최형규'),(348,6,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','보임?','2023-02-16 18:02:45','이상찬'),(349,3,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','ㅇㅇ','2023-02-16 18:02:47','최형규'),(350,6,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','소켓이','2023-02-16 18:02:48','이상찬'),(351,6,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','왔다갔다하나?','2023-02-16 18:02:50','이상찬'),(352,3,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','그런듯','2023-02-16 18:02:52','최형규'),(353,6,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','내일 테스트할 때는','2023-02-16 18:02:55','이상찬'),(354,6,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','되는 것만 보여주자','2023-02-16 18:02:57','이상찬'),(355,3,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','네','2023-02-16 18:02:59','최형규'),(356,6,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','연결된것만','2023-02-16 18:03:00','이상찬'),(357,6,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','또 안된다','2023-02-16 18:03:07','이상찬'),(358,6,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','왜 안보내지지','2023-02-16 18:03:10','이상찬'),(359,3,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','아니','2023-02-16 18:03:13','최형규'),(360,3,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','소켓이','2023-02-16 18:03:14','최형규'),(361,3,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','계속','2023-02-16 18:03:15','최형규'),(362,3,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','꺼지네','2023-02-16 18:03:17','최형규'),(363,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','왜 안되는걸까','2023-02-16 20:12:59','최형규'),(364,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','아아아앙아','2023-02-16 20:13:02','최형규'),(365,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','아','2023-02-16 20:13:03','최형규'),(366,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','아','2023-02-16 20:13:04','최형규'),(367,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','아아아아','2023-02-16 20:13:06','최형규'),(368,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','뭐야','2023-02-16 20:13:08','최형규'),(369,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','야','2023-02-16 20:13:09','최형규'),(370,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','야야야','2023-02-16 20:13:10','최형규'),(371,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','양ㅑ','2023-02-16 20:13:11','최형규'),(372,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','??','2023-02-16 20:13:14','이상찬'),(373,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','야야야야','2023-02-16 20:13:25','최형규'),(374,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','아 이거 실시간으로 안돼요','2023-02-16 20:13:52','이상찬'),(375,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','나갔다 들어와야 형규씨 새로 쓴게 보이네','2023-02-16 20:14:09','이상찬'),(376,5,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','테스트1','2023-02-16 20:25:18','마우스피스던짐'),(377,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','테스트2','2023-02-16 20:25:25','라이딩의 신'),(378,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','테스트3','2023-02-16 20:25:42','최형규'),(379,5,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','테스트4','2023-02-16 20:25:49','마우스피스던짐'),(380,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','테스트5','2023-02-16 20:25:53','라이딩의 신'),(381,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','테스트6','2023-02-16 20:25:59','최형규'),(382,5,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','테스트7','2023-02-16 20:26:24','마우스피스던짐'),(383,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','8','2023-02-16 20:26:27','라이딩의 신'),(384,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','9','2023-02-16 20:26:29','최형규'),(385,5,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','10','2023-02-16 20:26:31','마우스피스던짐'),(386,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','11','2023-02-16 20:26:33','라이딩의 신'),(387,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','12','2023-02-16 20:26:35','최형규'),(388,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','아 또 왜 잘돼','2023-02-16 20:26:39','최형규'),(389,5,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ','2023-02-16 20:27:05','마우스피스던짐'),(390,5,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','','2023-02-16 20:27:05','마우스피스던짐'),(391,5,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','','2023-02-16 20:27:06','마우스피스던짐'),(392,5,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','','2023-02-16 20:27:06','마우스피스던짐'),(393,5,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','','2023-02-16 20:27:06','마우스피스던짐'),(394,5,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','','2023-02-16 20:27:06','마우스피스던짐'),(395,5,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','','2023-02-16 20:27:07','마우스피스던짐'),(396,5,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','','2023-02-16 20:27:07','마우스피스던짐'),(397,5,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','','2023-02-16 20:27:08','마우스피스던짐'),(398,5,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','','2023-02-16 20:27:08','마우스피스던짐'),(399,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','','2023-02-16 20:27:09','라이딩의 신'),(400,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','','2023-02-16 20:27:10','라이딩의 신'),(401,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','','2023-02-16 20:27:10','라이딩의 신'),(402,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','','2023-02-16 20:27:10','라이딩의 신'),(403,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','','2023-02-16 20:27:14','라이딩의 신'),(404,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','','2023-02-16 20:27:15','라이딩의 신'),(405,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','','2023-02-16 20:27:15','라이딩의 신'),(406,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','','2023-02-16 20:27:16','라이딩의 신'),(407,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','','2023-02-16 20:27:17','최형규'),(408,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','','2023-02-16 20:27:17','최형규'),(409,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','','2023-02-16 20:27:17','최형규'),(410,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','','2023-02-16 20:27:18','최형규'),(411,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','','2023-02-16 20:27:18','최형규'),(412,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','','2023-02-16 20:27:24','최형규'),(413,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','아니','2023-02-16 20:28:30','라이딩의 신'),(414,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','왜','2023-02-16 20:28:31','라이딩의 신'),(415,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅇ','2023-02-16 20:28:33','라이딩의 신'),(416,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅇㅇ','2023-02-16 20:28:33','라이딩의 신'),(417,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅇㅇ','2023-02-16 20:28:34','라이딩의 신'),(418,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅇ','2023-02-16 20:28:52','라이딩의 신'),(419,5,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㄷㄷ','2023-02-16 20:29:16','마우스피스던짐'),(420,5,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅇㅇ','2023-02-16 20:29:18','마우스피스던짐'),(421,5,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅇ','2023-02-16 20:29:19','마우스피스던짐'),(422,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅇ','2023-02-16 20:30:11','이상찬'),(423,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅇ','2023-02-16 20:30:11','이상찬'),(424,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅇ','2023-02-16 20:30:12','이상찬'),(425,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅇ','2023-02-16 20:30:12','이상찬'),(426,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅇ','2023-02-16 20:30:12','이상찬'),(427,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅇ','2023-02-16 20:30:12','이상찬'),(428,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅇ','2023-02-16 20:30:12','이상찬'),(429,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅇ','2023-02-16 20:30:13','이상찬'),(430,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅇ','2023-02-16 20:30:13','이상찬'),(431,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅇ','2023-02-16 20:30:13','이상찬'),(432,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅇ','2023-02-16 20:30:13','이상찬'),(433,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅇ','2023-02-16 20:30:13','이상찬'),(434,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㄴ','2023-02-16 20:30:13','이상찬'),(435,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㄴ','2023-02-16 20:30:14','이상찬'),(436,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㄴ','2023-02-16 20:30:14','이상찬'),(437,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㄴ','2023-02-16 20:30:14','이상찬'),(438,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㄴ','2023-02-16 20:30:14','이상찬'),(439,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㄴ','2023-02-16 20:30:14','이상찬'),(440,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㄴ','2023-02-16 20:30:15','이상찬'),(441,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㄴ','2023-02-16 20:30:15','이상찬'),(442,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅁ','2023-02-16 20:30:16','이상찬'),(443,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅁ','2023-02-16 20:30:17','이상찬'),(444,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅁ','2023-02-16 20:30:17','이상찬'),(445,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅇㅇㅇ','2023-02-16 20:30:17','라이딩의 신'),(446,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅁ','2023-02-16 20:30:17','이상찬'),(447,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅁㅁ','2023-02-16 20:30:17','이상찬'),(448,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅇㅇ','2023-02-16 20:30:17','라이딩의 신'),(449,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅁ','2023-02-16 20:30:18','이상찬'),(450,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅇ','2023-02-16 20:30:18','라이딩의 신'),(451,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅇ','2023-02-16 20:30:18','라이딩의 신'),(452,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅁㅁㅁㅁ','2023-02-16 20:30:18','이상찬'),(453,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅇ','2023-02-16 20:30:18','라이딩의 신'),(454,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅁㅁ','2023-02-16 20:30:19','이상찬'),(455,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅁㅁㅁ','2023-02-16 20:30:19','이상찬'),(456,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅇㅇ','2023-02-16 20:30:19','라이딩의 신'),(457,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅁ','2023-02-16 20:30:19','이상찬'),(458,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','보여요?','2023-02-16 20:30:24','라이딩의 신'),(459,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','보임?','2023-02-16 20:30:29','라이딩의 신'),(460,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','보이냐고','2023-02-16 20:30:30','라이딩의 신'),(461,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','대답','2023-02-16 20:30:31','라이딩의 신'),(462,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3',';','2023-02-16 20:30:40','이상찬'),(463,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','','2023-02-16 20:30:40','이상찬'),(464,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3',';','2023-02-16 20:30:40','이상찬'),(465,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3',';','2023-02-16 20:30:40','이상찬'),(466,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3',';','2023-02-16 20:30:40','이상찬'),(467,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3',';','2023-02-16 20:30:40','이상찬'),(468,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3',';','2023-02-16 20:30:41','이상찬'),(469,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3',';;','2023-02-16 20:30:41','이상찬'),(470,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3',';;','2023-02-16 20:30:41','이상찬'),(471,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3',';;','2023-02-16 20:30:41','이상찬'),(472,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3',';','2023-02-16 20:30:43','이상찬'),(473,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3',';','2023-02-16 20:30:43','이상찬'),(474,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3',';','2023-02-16 20:30:43','이상찬'),(475,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3',';','2023-02-16 20:30:43','이상찬'),(476,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3',';;','2023-02-16 20:30:44','이상찬'),(477,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3',';;','2023-02-16 20:30:44','이상찬'),(478,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3',';;','2023-02-16 20:30:44','이상찬'),(479,5,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','대답','2023-02-16 20:31:48','마우스피스던짐'),(480,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','너말고','2023-02-16 20:31:54','라이딩의 신'),(481,5,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅠㅠ','2023-02-16 20:32:01','마우스피스던짐'),(482,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ddd','2023-02-16 20:32:24','이상찬'),(483,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅇㅇ','2023-02-16 20:32:30','라이딩의 신'),(484,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅋㅋㅋ','2023-02-16 20:33:27','이상찬'),(485,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅎㅎㅎㅎ','2023-02-16 20:33:32','이상찬'),(486,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㄷㄷ','2023-02-16 20:33:43','라이딩의 신'),(487,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅋㅋ','2023-02-16 20:34:05','이상찬'),(488,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅎㅎ','2023-02-16 20:34:07','이상찬'),(489,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','보이니','2023-02-16 20:34:09','이상찬'),(490,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅎㅎㅎㅎ','2023-02-16 20:35:31','이상찬'),(491,10,1,'843726a6-60e6-4033-9aef-74f267b75126','보이시나요','2023-02-16 20:36:04','레어닉'),(492,6,1,'843726a6-60e6-4033-9aef-74f267b75126','네네','2023-02-16 20:36:33','이상찬'),(493,5,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㄷㄷ','2023-02-16 20:41:16','마우스피스던짐'),(494,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㄷㄷ','2023-02-16 20:41:25','최형규'),(495,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','a','2023-02-16 20:43:57','최형규'),(496,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','.','2023-02-16 20:44:58','최형규'),(497,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','.','2023-02-16 20:45:11','최형규'),(498,5,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','.','2023-02-16 20:45:30','마우스피스던짐'),(499,5,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','e','2023-02-16 20:46:26','마우스피스던짐'),(500,5,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅂ','2023-02-16 20:47:16','마우스피스던짐'),(501,23,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅇㅇ','2023-02-16 20:49:32','말포이'),(502,23,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅇㅇ','2023-02-16 20:49:58','말포이'),(503,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','하이','2023-02-16 20:50:22','최형규'),(504,23,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅇㅇ','2023-02-16 20:50:35','말포이'),(505,23,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','','2023-02-16 20:50:36','말포이'),(506,5,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㄷ','2023-02-16 20:53:52','마우스피스던짐'),(507,5,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㄷㄷㄷ','2023-02-16 20:54:05','마우스피스던짐'),(508,5,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㄷ','2023-02-16 20:54:05','마우스피스던짐'),(509,5,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㄷㄷ','2023-02-16 20:54:05','마우스피스던짐'),(510,5,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㄷㄷ','2023-02-16 20:54:06','마우스피스던짐'),(511,5,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㄷㄷㄷㄷㄷ','2023-02-16 20:54:07','마우스피스던짐'),(512,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㄷㄷ','2023-02-16 20:54:10','최형규'),(513,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㄷㄷㄷㄷㄷ','2023-02-16 20:54:12','최형규'),(514,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅋ','2023-02-16 20:55:37','최형규'),(515,3,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','ㄷ','2023-02-16 20:55:44','최형규'),(516,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅈ','2023-02-16 20:55:50','최형규'),(517,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅁ','2023-02-16 20:56:37','이상찬'),(518,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅈ','2023-02-16 20:56:42','최형규'),(519,10,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','하이','2023-02-16 20:57:38','레어닉'),(520,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅎㅇㅎㅇ','2023-02-16 20:57:46','최형규'),(521,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅎㅇ','2023-02-16 20:57:53','이상찬'),(522,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','뭐야','2023-02-16 20:58:00','최형규'),(523,10,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','어떻게 상찬님 아이디만 안보일수가 있지','2023-02-16 20:58:21','레어닉'),(524,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㄹㅇ?','2023-02-16 20:58:47','라이딩의 신'),(525,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','진짜 상찬님 아이디만 안뜸','2023-02-16 20:59:21','최형규'),(526,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','뭐지','2023-02-16 20:59:25','최형규'),(527,10,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','진짜로','2023-02-16 20:59:28','레어닉'),(528,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㄹㅇ ㅋㅋ','2023-02-16 20:59:30','라이딩의 신'),(529,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','??','2023-02-16 20:59:36','이상찬'),(530,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㄷㄷ','2023-02-16 20:59:42','최형규'),(531,10,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅋㅋㅋㅋ','2023-02-16 20:59:46','레어닉'),(532,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','저주받은 아이디다..','2023-02-16 20:59:51','라이딩의 신'),(533,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','무슨 소리...','2023-02-16 21:02:36','최형규'),(534,10,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','?','2023-02-16 21:02:43','레어닉'),(535,10,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','잘뜹니다','2023-02-16 21:02:45','레어닉'),(536,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','와;;','2023-02-16 21:02:48','최형규'),(537,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','뭐지','2023-02-16 21:02:49','최형규'),(538,10,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ','2023-02-16 21:02:51','레어닉'),(539,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','열정! 열정! ?','2023-02-16 21:02:57','최형규'),(540,10,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','그와중에 상찬님 아이디로 채팅 들어가있는것만','2023-02-16 21:03:04','레어닉'),(541,10,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','안뜸','2023-02-16 21:03:05','레어닉'),(542,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','오잉','2023-02-16 21:03:52','최형규'),(543,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','나갔다 들어오면 되려나','2023-02-16 21:04:01','최형규'),(544,10,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','채팅은 되는듯','2023-02-16 21:04:08','레어닉'),(545,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','음..','2023-02-16 21:05:00','최형규'),(546,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','밥은 잘 챙겨먹구?','2023-02-16 21:05:05','최형규'),(547,10,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','뭐가 문제야','2023-02-16 21:05:10','레어닉'),(548,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','모르지..','2023-02-16 21:05:19','최형규'),(549,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','일단 가장 좋은 방법은','2023-02-16 21:05:24','최형규'),(550,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','나갔다 들어오는 걸 자주하면서','2023-02-16 21:05:31','최형규'),(551,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','자연스럽게','2023-02-16 21:05:33','최형규'),(552,10,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','시연할때?','2023-02-16 21:05:50','레어닉'),(553,10,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','또 끊겼네','2023-02-16 21:06:04','레어닉'),(554,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅋㅋㅋㅋㅋㅋㅋ','2023-02-16 21:06:29','최형규'),(555,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','음...','2023-02-16 21:06:36','최형규'),(556,10,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','하 뭐야','2023-02-16 21:06:51','레어닉'),(557,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','뭐가','2023-02-16 21:06:53','최형규'),(558,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','문제지','2023-02-16 21:06:56','최형규'),(559,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','알 수가 없네..','2023-02-16 21:07:07','최형규'),(560,10,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','그러게요','2023-02-16 21:07:11','레어닉'),(561,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','또 안되는거 같은데','2023-02-16 21:07:24','최형규'),(562,10,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','제 말 안보이죠?','2023-02-16 21:07:39','레어닉'),(563,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','나갔다 들어오니까 보인다','2023-02-16 21:07:58','최형규'),(564,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','과연','2023-02-16 21:08:20','라이딩의 신'),(565,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','또 이건 되네','2023-02-16 21:08:24','라이딩의 신'),(566,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','이제 안됨','2023-02-16 21:08:28','최형규'),(567,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','오','2023-02-16 21:08:29','최형규'),(568,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','또 끊겼네','2023-02-16 21:08:46','라이딩의 신'),(569,10,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','해봅시다','2023-02-16 21:15:40','레어닉'),(570,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅇㅋ','2023-02-16 21:15:50','라이딩의 신'),(571,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','안됨','2023-02-16 21:15:53','라이딩의 신'),(572,10,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㄷㄷ','2023-02-16 21:15:59','레어닉'),(573,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','뭐지','2023-02-16 21:16:11','라이딩의 신'),(574,10,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','하하하하하','2023-02-16 21:16:17','레어닉'),(575,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㄷㄷ','2023-02-16 21:16:28','라이딩의 신'),(576,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','bb','2023-02-16 21:22:01','최형규'),(577,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅠㅠ','2023-02-16 21:22:02','최형규'),(578,5,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅠㅠ','2023-02-16 21:22:18','마우스피스던짐'),(579,5,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','?','2023-02-16 21:24:14','마우스피스던짐'),(580,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','?','2023-02-16 21:24:21','최형규'),(581,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㄷㄷ','2023-02-16 21:24:29','라이딩의 신'),(582,5,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','또 왜 됨?','2023-02-16 21:24:44','마우스피스던짐'),(583,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㄹㅇ..','2023-02-16 21:24:53','라이딩의 신'),(584,5,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','하','2023-02-16 21:25:03','마우스피스던짐'),(585,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅁㄴ어롸ㅓㅁ옴ㄴㅇㄹㅁㄴ아럼ㄴ;ㄹ어','2023-02-16 21:31:02','최형규'),(586,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅇㅇ','2023-02-16 21:32:14','최형규'),(587,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㄷㄷ','2023-02-16 21:32:15','최형규'),(588,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅁㄴㅇㄹㅁㄴㅇㄹ','2023-02-16 21:32:18','최형규'),(589,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅁㄴㅇㄹ','2023-02-16 21:32:24','최형규'),(590,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅁㄴㅇㄹ','2023-02-16 21:32:26','최형규'),(591,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅁㄴㅇㄹ','2023-02-16 21:32:31','최형규'),(592,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅁㄴㅇㄹ','2023-02-16 21:32:34','최형규'),(593,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅁㄴㅇㄻㄴㅇㄹ','2023-02-16 21:32:36','최형규'),(594,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㄴ멍라;ㅣㅓㅁ내야러ㅑ넘댜ㅐ러내먀나이ㅓ랴ㅐ더ㅑㅣㄴㅁ','2023-02-16 21:33:15','최형규'),(596,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','??','2023-02-16 21:34:03','최형규'),(597,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅁㄴㅇㄻㄴㅇㄻㄴㅇㄻㄴㅇㄻㄴㅇㄻㄴㅇㄻㄴㅇㄹㄴㅁㅇㄻㄴㅇㄹㄴㅁㅇㄻㄴㅇㄻㄴㅇㄻㄴㅇㄻㄴㅇㄻㄴㅇㄻㄴㅇㄻㄴㅇㄻㄴㅇㄻㄴㅇㄻㄴㅇㄻㄴㅇㄻㄴㅇㄻㄴㅇㄻㄴㅇㄻㄴㅇㄻㄴㅇㄻㄴㅇㄻㄴㅇㄻㄴㅇㄹㄴㅇ','2023-02-16 21:35:56','최형규'),(598,8,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','있나요','2023-02-16 21:42:21','권용'),(599,5,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3',';;','2023-02-16 21:42:55','마우스피스던짐'),(600,5,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅁㄴㅇㄻㄴㅇㄻㄴㅇㄻㄴㅇㄻㄴㅇㄹㄴㅁㅇㄹㄴㅁㅇㄻㄴㅇ라먼ㅇ랴ㅐㅓ댜ㅐㅓㄴ매랴ㅓ맨ㅇ','2023-02-16 21:43:24','마우스피스던짐'),(601,8,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','ㅇㄹㅇ','2023-02-16 21:51:47','권용'),(602,5,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅁㄴㅇㄹ','2023-02-16 22:05:13','마우스피스던짐'),(603,3,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','?','2023-02-16 22:06:30','최형규'),(604,3,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','ㅁㄴㅇㄻㄴㅇㄹㄴㅁㅇㄹㄴㅁㅇㄹㄴㅁㅇㄹㄴㅁㅇㄹㄴㅁㅇㄹㄴㅁㅇㄹㄴㅁㅇㄹㄴㅁㅇㄻㄴㅇㄹㄴㅁㅇㄹㄴㅁㅇㄹㄴㅁㅇㄻㄴㅇㄻㄴㅇㄻㄴㅇㄻㄴㅇㄻㄴㅇㄻㄴㅇㄻㄴ아럼ㄴ야ㅐㅑ더ㅐㅑ머ㅐㅑㄹ아댬ㄴㄹ어랴ㅐㅓ내퍼ㅐ먀러ㅐ댜ㅓㅐㅑㅁㄹ대ㅑ저ㅐ랴ㅓ쟈ㅐㄷ러','2023-02-16 22:07:26','최형규'),(605,3,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','ㄴㅁㅇㄹㄴㅁㅇㄹㄴㅁㅇㄹ','2023-02-16 22:08:27','최형규'),(606,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅋ','2023-02-16 22:10:15','최형규'),(607,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㄷ','2023-02-16 22:10:54','최형규'),(608,3,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','ㄷ','2023-02-16 22:11:33','최형규'),(609,3,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ','2023-02-16 22:18:30','최형규'),(610,3,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','ㄷ','2023-02-16 22:32:56','최형규'),(611,3,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','ㄷ','2023-02-16 22:33:23','최형규'),(612,5,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㄷ','2023-02-16 22:33:50','마우스피스던짐'),(613,5,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅠ','2023-02-16 22:34:00','마우스피스던짐'),(614,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3',';;','2023-02-16 22:44:24','최형규'),(615,16,1,'843726a6-60e6-4033-9aef-74f267b75126','안녕하세요!','2023-02-16 22:45:09','알겠으니까테핑이다'),(616,16,1,'843726a6-60e6-4033-9aef-74f267b75126','앱에서 상재가 테스트합니다.','2023-02-16 22:45:28','알겠으니까테핑이다'),(617,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3',';;','2023-02-16 22:45:58','최형규'),(618,10,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','.','2023-02-16 22:46:10','레어닉'),(619,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','.','2023-02-16 22:46:28','최형규'),(620,10,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','?','2023-02-16 22:46:33','레어닉'),(621,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','?','2023-02-16 22:46:39','최형규'),(622,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅇ','2023-02-16 23:00:29','최형규'),(623,8,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','a','2023-02-16 23:00:43','권용'),(624,10,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','d','2023-02-16 23:00:51','레어닉'),(625,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㄷ','2023-02-16 23:00:54','최형규'),(626,5,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅇ','2023-02-16 23:00:59','마우스피스던짐'),(627,5,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㄴ','2023-02-16 23:01:04','마우스피스던짐'),(628,10,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','?','2023-02-16 23:01:12','레어닉'),(629,10,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','bb','2023-02-16 23:01:33','레어닉'),(630,5,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㄴㄴ','2023-02-16 23:01:36','마우스피스던짐'),(631,10,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','eee','2023-02-16 23:01:39','레어닉'),(632,10,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3',';;','2023-02-16 23:01:56','레어닉'),(633,5,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅁㄴㅇ리ㅏㅓ','2023-02-16 23:02:07','마우스피스던짐'),(634,3,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','ㄷ','2023-02-16 23:04:36','최형규'),(635,3,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','ㅁ','2023-02-16 23:05:38','최형규'),(636,5,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','ㄷ','2023-02-16 23:05:56','마우스피스던짐'),(637,5,1,'18f261b9-5e4a-44eb-82de-a130dad425f1',';;','2023-02-16 23:06:22','마우스피스던짐'),(638,5,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','ㅠㅠ','2023-02-16 23:06:36','마우스피스던짐'),(639,3,1,'18f261b9-5e4a-44eb-82de-a130dad425f1',';;','2023-02-16 23:08:04','최형규'),(640,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','abc','2023-02-16 23:08:44','라이딩의 신'),(641,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㄷㄷ','2023-02-16 23:08:52','최형규'),(642,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','아니 왜','2023-02-16 23:09:06','라이딩의 신'),(643,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅣㅣ','2023-02-16 23:09:19','라이딩의 신'),(644,5,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','?','2023-02-16 23:09:23','마우스피스던짐'),(645,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','하이','2023-02-16 23:31:04','이상찬'),(646,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','하이','2023-02-16 23:31:09','이상찬'),(647,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','gkdl','2023-02-16 23:31:12','최형규'),(648,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','하이','2023-02-16 23:31:13','최형규'),(649,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','하..','2023-02-16 23:31:31','이상찬'),(650,10,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅂㅈㄷㄱ','2023-02-16 23:32:02','레어닉'),(651,10,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','왜 다 안돼','2023-02-16 23:32:07','레어닉'),(652,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','연결은 오는데','2023-02-16 23:32:16','최형규'),(653,10,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','아닌가','2023-02-16 23:32:41','레어닉'),(654,10,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','제ㅔ바류ㅠ','2023-02-16 23:33:03','레어닉'),(655,10,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','이제?','2023-02-16 23:33:54','레어닉'),(656,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','제발','2023-02-16 23:34:10','최형규'),(657,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ddd','2023-02-16 23:39:59','최형규'),(658,8,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','!!!','2023-02-16 23:43:07','권용'),(659,3,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','?','2023-02-16 23:47:22','최형규'),(660,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','됨?','2023-02-16 23:48:44','라이딩의 신'),(661,10,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㄴㄴ','2023-02-16 23:49:02','레어닉'),(662,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','?','2023-02-16 23:49:08','최형규'),(663,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','bb','2023-02-16 23:49:18','최형규'),(664,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅠㅠ','2023-02-16 23:49:25','최형규'),(665,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅎㅇ','2023-02-17 00:07:14','라이딩의 신'),(666,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅎㅇ','2023-02-17 00:07:53','최형규'),(667,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅎㅇㅎㅇ','2023-02-17 00:08:04','라이딩의 신'),(668,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','아','2023-02-17 00:08:08','라이딩의 신'),(669,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','안녕하세요!!!','2023-02-17 00:09:29','이상찬'),(670,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','다들 오늘 라이딩 한 번 하러 가야죠!','2023-02-17 00:09:48','이상찬'),(671,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','제가 경로 하나 저장해왔거든요','2023-02-17 00:10:18','이상찬'),(672,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','되려나','2023-02-17 00:12:27','최형규'),(673,10,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','과연','2023-02-17 00:12:46','레어닉'),(674,8,1,'18f261b9-5e4a-44eb-82de-a130dad425f1','123','2023-02-17 00:12:47','권용'),(675,10,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','진짜 미쳐버리겠다','2023-02-17 00:13:12','레어닉'),(676,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','도대체 어떻게 해야됨','2023-02-17 00:13:51','라이딩의 신'),(677,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','아니 또 왜 되는거?','2023-02-17 00:13:59','라이딩의 신'),(678,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㄴㅁㅇ라ㅓㅁ;ㄴㅇ러ㅏㄴㅇㅁㄹ','2023-02-17 00:14:02','최형규'),(679,10,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','asdfsdf','2023-02-17 00:14:22','레어닉'),(680,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','asdkfj;askldjfse','2023-02-17 00:14:44','이상찬'),(681,23,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','asdfkasjdfjasdf','2023-02-17 00:15:04','말포이'),(682,7,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','안녕하세요','2023-02-17 00:17:32','응애'),(683,7,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','안녕','2023-02-17 00:17:41','응애'),(684,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','안녕','2023-02-17 00:17:44','최형규'),(685,7,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','제발..','2023-02-17 00:19:32','응애'),(686,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','제 아이디들은 잘됩니다!','2023-02-17 00:20:55','나이키'),(687,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','굳','2023-02-17 00:21:08','최형규'),(688,5,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅎㅎ','2023-02-17 00:26:10','배달기사'),(689,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅋ','2023-02-17 00:26:25','나이키'),(690,5,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅋ','2023-02-17 00:26:31','배달기사'),(691,5,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅎㅎ','2023-02-17 00:26:38','배달기사'),(692,5,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','누구 있나요','2023-02-17 00:34:24','배달기사'),(693,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','있어요~','2023-02-17 00:34:50','나이키'),(694,25,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','가즈아','2023-02-17 02:42:30','홍길동'),(695,25,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','하이','2023-02-17 02:42:37','홍길동'),(696,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','하이','2023-02-17 02:42:39','최형규'),(697,25,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','하이하이','2023-02-17 02:50:33','홍길동'),(698,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅎㅇ','2023-02-17 02:50:43','최형규'),(699,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','굳','2023-02-17 02:50:45','최형규'),(700,3,1,'06fe327c-8438-4b2b-a189-8afad1121c8d','하이','2023-02-17 03:06:19','최형규'),(701,25,1,'06fe327c-8438-4b2b-a189-8afad1121c8d','하이','2023-02-17 03:06:32','홍길동'),(702,3,1,'06fe327c-8438-4b2b-a189-8afad1121c8d','안녕!','2023-02-17 04:15:08','최형규'),(703,25,1,'06fe327c-8438-4b2b-a189-8afad1121c8d','gjr','2023-02-17 04:15:17','홍길동'),(704,25,1,'06fe327c-8438-4b2b-a189-8afad1121c8d','헉','2023-02-17 04:15:19','홍길동'),(705,25,1,'06fe327c-8438-4b2b-a189-8afad1121c8d','안녕하세요','2023-02-17 04:15:20','홍길동'),(706,3,1,'06fe327c-8438-4b2b-a189-8afad1121c8d','ㅋㅋㅋ','2023-02-17 04:15:26','최형규'),(707,25,1,'06fe327c-8438-4b2b-a189-8afad1121c8d','하이하이','2023-02-17 04:17:45','홍길동'),(708,6,1,'48e209d3-f81e-42cc-bf63-92b641f84256','누구야','2023-02-17 04:19:42','이상찬'),(709,3,1,'cda841b3-9c44-436b-a35b-eaa5dc42baad','안녕하세요','2023-02-17 04:20:08','최형규'),(710,6,1,'cda841b3-9c44-436b-a35b-eaa5dc42baad','오','2023-02-17 04:20:13','이상찬'),(711,6,1,'cda841b3-9c44-436b-a35b-eaa5dc42baad','안녕하세요','2023-02-17 04:20:15','이상찬'),(712,3,1,'cda841b3-9c44-436b-a35b-eaa5dc42baad','반가워요 상찬씨','2023-02-17 04:20:23','최형규'),(713,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','아니 이게 뭐야','2023-02-17 04:31:33','나이키'),(714,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','하','2023-02-17 04:32:00','이상찬'),(715,6,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','또야?','2023-02-17 04:32:21','이상찬'),(716,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','제발','2023-02-17 04:32:49','최형규'),(717,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','?','2023-02-17 04:32:59','나이키'),(718,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅁㄴㅇㄹ','2023-02-17 04:33:25','최형규'),(719,3,1,'db3b0b74-0f8e-4c13-82b9-3555247133e5','나이키님 안녕하세요','2023-02-17 04:37:20','최형규'),(720,4,1,'db3b0b74-0f8e-4c13-82b9-3555247133e5','안녕','2023-02-17 04:37:28','나이키'),(721,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ee','2023-02-17 05:05:13','최형규'),(722,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㄷㄷ','2023-02-17 05:05:37','최형규'),(723,4,1,'db3b0b74-0f8e-4c13-82b9-3555247133e5','하이','2023-02-17 05:08:40','나이키'),(724,4,1,'db3b0b74-0f8e-4c13-82b9-3555247133e5','ㅂㅂ','2023-02-17 05:08:45','나이키'),(725,3,1,'db3b0b74-0f8e-4c13-82b9-3555247133e5','ejfejf','2023-02-17 05:08:59','최형규'),(726,3,1,'db3b0b74-0f8e-4c13-82b9-3555247133e5','덜덜','2023-02-17 05:09:00','최형규'),(727,3,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','?','2023-02-17 05:32:30','최형규'),(728,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ee','2023-02-17 05:32:54','나이키'),(729,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅎㅇ','2023-02-17 05:35:19','나이키'),(730,4,1,'f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','ㅎㅇㅎㅇ','2023-02-17 05:35:51','나이키');
/*!40000 ALTER TABLE `chatting_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chatting_room`
--

DROP TABLE IF EXISTS `chatting_room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chatting_room` (
  `chatting_room_id` varchar(255) NOT NULL,
  `name` varchar(25) NOT NULL,
  `room_type` int NOT NULL,
  PRIMARY KEY (`chatting_room_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chatting_room`
--

LOCK TABLES `chatting_room` WRITE;
/*!40000 ALTER TABLE `chatting_room` DISABLE KEYS */;
INSERT INTO `chatting_room` VALUES ('01cd6cbf-965f-461c-b913-a61b1022ec8c','울산에서 달리실 분~?',99),('06fe327c-8438-4b2b-a189-8afad1121c8d','test0test1',1),('07210636-31f7-4ec3-acde-b5dc45577244','서울한바퀴 하실분 오세요',99),('085a2793-f177-44bb-af0a-7752734ce902','벚꽃보러 갑시다',99),('18f261b9-5e4a-44eb-82de-a130dad425f1','B6 봄이 와도 외롭다',99),('1bc5c842-5001-4389-b8bc-057405bcdc6d','영상 테스트용 방',99),('1c6512d5-fa1d-4ba5-a1fc-5a304304d6bb','창원~부산 달립시다',99),('35cb3d7f-4781-4d30-9945-14db56ba691c','가나다라',1),('399b7ebf-f77a-474f-a682-976b124a7456','가나다라우린',1),('450fd589-5e2e-49bf-91c5-418b8913cdf4','asd',99),('4679d774-062f-402a-9630-829b6261602a','부산 ㄱㄱ',99),('484da7b7-297f-4cbb-9eda-948184b87c73','asd',99),('48e209d3-f81e-42cc-bf63-92b641f84256','dltkdcksqkqhtest1',1),('505885ff-29a8-4a43-ac2b-a8034e44172c','모임 갑시다',99),('5b98050b-e886-4113-9882-dff7dbef0e29','벚꽃보면서  달립시다',99),('6e8b00f3-75ff-47c8-9676-670a9a887666','인천 송도 가실분?',99),('7f51928c-5f26-4965-b42d-52d826745764','들어오셔요',99),('843726a6-60e6-4033-9aef-74f267b75126','을숙도에서 달려볼사람?',99),('940e2963-3f28-419d-8b77-b53f78011f5b','한강 달리실 분',99),('98adfd93-8619-4a4c-971d-19e2a538911b','ㅁㄴㄷㅇㄹ',99),('a3558919-3aec-4637-bea8-b57d6bdda81a','레어닉최형규',1),('b01c77f0-9547-4634-baea-78f832724614','권용최형규',1),('b8b57571-e542-4992-a8d5-b89676a8782e','권용최형규',1),('c04a7123-3155-4103-a607-428545efedce','충청도 놀러와유',99),('c5427fb9-7ca9-4104-9e3c-ebacfb828268','명지에서 사상까지 라이딩',99),('c8cd31fe-2c16-4433-a947-8b34b0050df7','의왕에서 인천까지 갑시다',99),('c8dc590c-32c4-46cc-969c-2f1673f26853','지리산 갑시다',99),('cd9b9513-1bca-4f88-bd77-b3fc8cc6d5bf','레어닉최형규',1),('cda841b3-9c44-436b-a35b-eaa5dc42baad','dltkdcksqkqhtest1',1),('d037a78d-b4c8-4abd-9e08-0ef97a2c7922','가나다라',1),('d23af146-0691-4826-b3b6-3a4c938b1920','동네 마실 나가실분?',99),('d2d9c726-b24c-4b2d-ae2b-6e6e1f14e0d6','asd',99),('d639f45d-db98-4836-bbb1-c8c725f25d5c','모임방 인원수 테스트',99),('dab00c4d-1fcb-4a01-802e-7837a8702f0d','가나다라',1),('db3b0b74-0f8e-4c13-82b9-3555247133e5','test2test1',1),('dfb024e5-65c0-4062-bb13-3d9e8fcb5db2','롯데타워 빙빙 돌 분?',99),('e69f6313-fd57-40d5-948e-2f8eb14f8d7b','ddd',99),('f0fd6860-6930-4df4-b82d-d7e74163b82a','앱',99),('f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3','한강 라이딩합시다',99);
/*!40000 ALTER TABLE `chatting_room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chatting_room_user`
--

DROP TABLE IF EXISTS `chatting_room_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chatting_room_user` (
  `chatting_room_user_pk` bigint NOT NULL AUTO_INCREMENT,
  `alarm` bit(1) NOT NULL DEFAULT b'0',
  `chatting_room_id` varchar(255) NOT NULL,
  `user_id` bigint NOT NULL,
  `recent_chatting_time` datetime DEFAULT NULL,
  PRIMARY KEY (`chatting_room_user_pk`)
) ENGINE=InnoDB AUTO_INCREMENT=136 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chatting_room_user`
--

LOCK TABLES `chatting_room_user` WRITE;
/*!40000 ALTER TABLE `chatting_room_user` DISABLE KEYS */;
INSERT INTO `chatting_room_user` VALUES (31,_binary '\0','d2d9c726-b24c-4b2d-ae2b-6e6e1f14e0d6',11,NULL),(59,_binary '\0','98adfd93-8619-4a4c-971d-19e2a538911b',14,'2023-02-14 15:01:23'),(61,_binary '\0','f0fd6860-6930-4df4-b82d-d7e74163b82a',11,NULL),(63,_binary '\0','940e2963-3f28-419d-8b77-b53f78011f5b',4,'2023-02-14 15:16:59'),(65,_binary '\0','35cb3d7f-4781-4d30-9945-14db56ba691c',11,NULL),(67,_binary '\0','940e2963-3f28-419d-8b77-b53f78011f5b',12,NULL),(68,_binary '\0','7f51928c-5f26-4965-b42d-52d826745764',6,'2023-02-16 17:43:43'),(69,_binary '\0','f0fd6860-6930-4df4-b82d-d7e74163b82a',6,NULL),(70,_binary '\0','d037a78d-b4c8-4abd-9e08-0ef97a2c7922',11,NULL),(71,_binary '\0','d037a78d-b4c8-4abd-9e08-0ef97a2c7922',6,NULL),(74,_binary '','01cd6cbf-965f-461c-b913-a61b1022ec8c',12,'2023-02-16 10:50:32'),(76,_binary '\0','f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3',3,'2023-02-17 05:32:30'),(78,_binary '\0','843726a6-60e6-4033-9aef-74f267b75126',6,'2023-02-16 22:45:28'),(82,_binary '\0','dab00c4d-1fcb-4a01-802e-7837a8702f0d',11,'2023-02-16 13:53:20'),(83,_binary '\0','dab00c4d-1fcb-4a01-802e-7837a8702f0d',10,'2023-02-16 13:53:20'),(85,_binary '','f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3',6,'2023-02-17 05:32:30'),(86,_binary '\0','450fd589-5e2e-49bf-91c5-418b8913cdf4',11,NULL),(88,_binary '\0','843726a6-60e6-4033-9aef-74f267b75126',10,'2023-02-16 22:45:28'),(89,_binary '\0','e69f6313-fd57-40d5-948e-2f8eb14f8d7b',11,NULL),(90,_binary '\0','484da7b7-297f-4cbb-9eda-948184b87c73',11,NULL),(95,_binary '\0','f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3',4,'2023-02-17 05:32:30'),(97,_binary '','f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3',23,'2023-02-17 05:32:30'),(98,_binary '','f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3',10,'2023-02-17 05:32:30'),(100,_binary '\0','dfb024e5-65c0-4062-bb13-3d9e8fcb5db2',5,NULL),(102,_binary '','f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3',5,'2023-02-17 05:32:30'),(106,_binary '\0','18f261b9-5e4a-44eb-82de-a130dad425f1',5,'2023-02-16 23:47:22'),(107,_binary '','f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3',7,'2023-02-17 05:32:30'),(110,_binary '','f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3',25,'2023-02-17 05:32:30'),(111,_binary '\0','06fe327c-8438-4b2b-a189-8afad1121c8d',3,NULL),(112,_binary '\0','06fe327c-8438-4b2b-a189-8afad1121c8d',25,NULL),(118,_binary '\0','cd9b9513-1bca-4f88-bd77-b3fc8cc6d5bf',10,NULL),(120,_binary '\0','a3558919-3aec-4637-bea8-b57d6bdda81a',10,NULL),(123,_binary '\0','cda841b3-9c44-436b-a35b-eaa5dc42baad',3,NULL),(124,_binary '\0','cda841b3-9c44-436b-a35b-eaa5dc42baad',6,NULL),(127,_binary '\0','db3b0b74-0f8e-4c13-82b9-3555247133e5',3,NULL),(128,_binary '\0','db3b0b74-0f8e-4c13-82b9-3555247133e5',4,NULL),(131,_binary '\0','c04a7123-3155-4103-a607-428545efedce',26,NULL),(132,_binary '\0','c8cd31fe-2c16-4433-a947-8b34b0050df7',26,NULL),(133,_binary '\0','6e8b00f3-75ff-47c8-9676-670a9a887666',26,NULL),(134,_binary '\0','399b7ebf-f77a-474f-a682-976b124a7456',9,NULL),(135,_binary '\0','399b7ebf-f77a-474f-a682-976b124a7456',11,NULL);
/*!40000 ALTER TABLE `chatting_room_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `comment_id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `board_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`comment_id`),
  KEY `FKlij9oor1nav89jeat35s6kbp1` (`board_id`),
  KEY `FK8kcum44fvpupyw6f5baccx25c` (`user_id`),
  CONSTRAINT `FK8kcum44fvpupyw6f5baccx25c` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FKlij9oor1nav89jeat35s6kbp1` FOREIGN KEY (`board_id`) REFERENCES `board` (`board_id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (12,'good','2023-02-14 16:13:38',29,11),(15,'멋져요 상찬씨','2023-02-15 13:08:10',29,3),(16,'아 상재님이구나','2023-02-15 13:08:14',29,3),(19,'반가워요!!!!!','2023-02-15 19:24:55',40,6),(20,'상재님 진짜 멋져요!!! 다음에 같이 한번 더 갈까요?','2023-02-15 21:40:30',29,6),(21,'음?!','2023-02-15 21:40:43',42,6),(22,'나둥..❤','2023-02-15 21:40:56',56,6),(23,'찍찍-','2023-02-15 22:04:39',47,3),(24,'조언좀요!!','2023-02-15 23:01:01',59,4),(25,'3번째 항목은 힘들거 같네요','2023-02-15 23:01:55',59,3),(26,'여기서 홍보하시면 안돼요','2023-02-15 23:04:03',60,5),(27,'요즘 황사가 심하더라구요... 그러니... 집에만 있읍시다...^_____^','2023-02-15 23:23:14',61,6),(28,'좋은 밤 되세요','2023-02-16 13:41:45',50,8),(29,'나 이상찬은 이대로 쓰러지지 않는다!','2023-02-16 13:49:30',70,6),(33,'빨리 나아서 백숙이나 드시러 오십소 형님','2023-02-16 14:42:32',70,9),(37,'F4 ㄷㄷ','2023-02-16 17:43:16',70,3),(39,'관심가져주세요!','2023-02-17 05:11:56',57,3),(40,'엄청나다..','2023-02-17 05:12:18',73,3);
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `community`
--

DROP TABLE IF EXISTS `community`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `community` (
  `community_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '모임을 생성한 유저',
  `title` varchar(255) NOT NULL COMMENT '모임 이름',
  `content` varchar(255) NOT NULL COMMENT '모임 설명',
  `si` varchar(50) NOT NULL,
  `gun` varchar(50) NOT NULL,
  `dong` varchar(50) NOT NULL,
  `current_person` int NOT NULL DEFAULT '1' COMMENT '현재 모임에 참여중인 인원 (호스트 포함)',
  `max_person` int NOT NULL DEFAULT '2' COMMENT '최대 모집 인원 (호스트 포함)',
  `date` datetime(6) NOT NULL,
  `in_progress` bit(1) DEFAULT NULL,
  `chatting_room_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`community_id`),
  KEY `FK_user_TO_community_1` (`user_id`),
  CONSTRAINT `FK_user_TO_community_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FKfsfwlfb2ummfsb30q78wo6se0` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `community`
--

LOCK TABLES `community` WRITE;
/*!40000 ALTER TABLE `community` DISABLE KEYS */;
INSERT INTO `community` VALUES (19,9,'을숙도에서 달려볼사람?','이번 주말 을숙도 모임','부산광역시','사하구','하단2동',6,10,'2023-02-20 01:00:00.000000',_binary '','843726a6-60e6-4033-9aef-74f267b75126'),(20,3,'한강 라이딩합시다','빠른 분들 환영!!','서울특별시','강남구','도곡1동',9,10,'2023-02-26 03:00:00.000000',_binary '','f63e49ed-61a1-442d-8d6f-e2dddf0aa8a3'),(21,3,'롯데타워 빙빙 돌 분?','어그로 한번 끌어보죠!','서울특별시','송파구','석촌동',3,10,'2023-03-05 02:30:00.000000',_binary '','dfb024e5-65c0-4062-bb13-3d9e8fcb5db2'),(26,6,'봄 맞이 라이딩(부산)','남자 6명이서 탑시다','부산광역시','서구','서대신1동',3,4,'2023-02-18 02:00:00.000000',_binary '','18f261b9-5e4a-44eb-82de-a130dad425f1'),(28,3,'지리산 갑시다','공기 좋고 날도 좋고 사람도 좋은 지리산으로 떠나자~!','경상북도','의성군','봉양면',1,5,'2023-03-05 17:00:00.000000',_binary '','c8dc590c-32c4-46cc-969c-2f1673f26853'),(29,3,'동네 마실 나가실분?','편하게 타면서 친해집시다','경상남도','창원시 의창구','팔룡동',1,3,'2023-02-22 07:00:00.000000',_binary '','d23af146-0691-4826-b3b6-3a4c938b1920'),(30,4,'명지에서 사상까지 라이딩','마실갔다와요~','부산광역시','강서구','명지1동',1,4,'2023-02-23 04:00:00.000000',_binary '','c5427fb9-7ca9-4104-9e3c-ebacfb828268'),(31,4,'서울한바퀴 하실분 오세요','한강따라 쭉 달립니다','서울특별시','강남구','논현1동',1,4,'2023-02-18 17:00:00.000000',_binary '','07210636-31f7-4ec3-acde-b5dc45577244'),(32,26,'충청도 놀러와유','느리게 탈랑께','충청남도','아산시','둔포면',1,3,'2023-02-25 16:00:00.000000',_binary '','c04a7123-3155-4103-a607-428545efedce'),(33,26,'의왕에서 인천까지 갑시다','고고','경기도','의왕시','부곡동',1,6,'2023-03-05 20:00:00.000000',_binary '','c8cd31fe-2c16-4433-a947-8b34b0050df7'),(34,26,'인천 송도 가실분?','인천입니다','인천광역시','중구','신포동',1,3,'2023-02-20 05:00:00.000000',_binary '','6e8b00f3-75ff-47c8-9676-670a9a887666');
/*!40000 ALTER TABLE `community` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `community_ban_user`
--

DROP TABLE IF EXISTS `community_ban_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `community_ban_user` (
  `community_ban_user_id` bigint NOT NULL AUTO_INCREMENT,
  `community_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`community_ban_user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `community_ban_user`
--

LOCK TABLES `community_ban_user` WRITE;
/*!40000 ALTER TABLE `community_ban_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `community_ban_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `community_participate`
--

DROP TABLE IF EXISTS `community_participate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `community_participate` (
  `community_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`community_id`,`user_id`),
  KEY `FK_user_TO_community_participate_1` (`user_id`),
  CONSTRAINT `FK_community_TO_community_participate_1` FOREIGN KEY (`community_id`) REFERENCES `community` (`community_id`),
  CONSTRAINT `FK_user_TO_community_participate_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `community_participate`
--

LOCK TABLES `community_participate` WRITE;
/*!40000 ALTER TABLE `community_participate` DISABLE KEYS */;
INSERT INTO `community_participate` VALUES (19,3),(20,3),(21,3),(28,3),(29,3),(20,4),(30,4),(31,4),(20,5),(21,5),(26,5),(19,6),(20,6),(26,6),(20,7),(26,8),(19,9),(20,9),(19,10),(20,10),(19,13),(19,17),(21,17),(20,23),(20,25),(32,26),(33,26),(34,26);
/*!40000 ALTER TABLE `community_participate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course_board`
--

DROP TABLE IF EXISTS `course_board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course_board` (
  `course_board_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `title` varchar(255) NOT NULL COMMENT '게시글의 제목',
  `course_id` bigint NOT NULL,
  `content` varchar(255) NOT NULL COMMENT '게시글 내용',
  `like` bigint NOT NULL COMMENT '게시글 추천 수',
  `hate` bigint DEFAULT NULL,
  `visited` bigint DEFAULT NULL,
  `time` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`course_board_id`),
  KEY `FK_user_TO_course_board_1` (`user_id`),
  KEY `FK_custom_course_TO_course_board_1` (`course_id`),
  CONSTRAINT `FK3oub7yrf769au350felc5ughk` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FK5krdru5y3rjc44sf9yk72xj13` FOREIGN KEY (`course_id`) REFERENCES `custom_course` (`course_id`),
  CONSTRAINT `FK_custom_course_TO_course_board_1` FOREIGN KEY (`course_id`) REFERENCES `custom_course` (`course_id`),
  CONSTRAINT `FK_user_TO_course_board_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_board`
--

LOCK TABLES `course_board` WRITE;
/*!40000 ALTER TABLE `course_board` DISABLE KEYS */;
INSERT INTO `course_board` VALUES (15,10,'테스트 주행이에요',26,'하하',1,0,10,'2023-02-14 12:31:52.000000'),(18,8,'지리산 안가',28,'<p>ㅇㅅㅇ</p>',2,0,8,'2023-02-14 15:52:52.000000'),(28,6,'인천 공항 주변 진짜 라이딩 하기 좋아요!!!',39,'<p>안녕하세요</p><p>인천 공항 주변은 진짜 라이딩 하기 좋습니다!!!</p><p><br></p><h4>자전거 타고 한 40분 쯤 걸리는데 시원하고 좋습니다</h4><p><br></p><p>다 같이 끝나고 맥주 한잔 하시면 ?아주<span style=\"color: #f90000\"> </span><strong><span style=\"color: #f90000\">끝내줍니다!!!!</span></strong></p>',2,0,7,'2023-02-14 20:00:44.000000'),(29,6,'이번에 전라남도 쪽 인증센터 돌았습니다. 경로 추천 드려요?',40,'<h1><strong> 한번 더 가보고 싶은 곳</strong></h1><p>안녕하세요</p><p>이번에도 경로 추천드립니다.</p><p>한 달전에 갔던 곳인데 경치가 좋더라구요</p><p><br></p><p>오르막, 내리막도 그리 심하지 않아서 초심자분들도 잘 갈 수 있을 거에요</p><p><br></p><h3>라이딩 파이팅!!!!?</h3>',2,0,6,'2023-02-15 08:47:56.000000'),(30,6,'강서구 산업 단지 경로입니다!!!?',41,'<p>안녕하세요</p><p>여러분들도 다 아시다시피 강서구 쪽 자전거 경로 입니다</p><p>직접 돌아다녀보니 평지라서 잠깐 자전거 타기에는 좋더라구요</p><p><br></p><p>헤헤 다들 좋은 라이딩 되세요!!!.</p>',4,0,10,'2023-02-15 13:00:04.000000'),(33,3,'춘천 갑시다',46,'<p>고고</p>',3,0,8,'2023-02-15 17:31:12.000000'),(34,6,'벽산 아파트 코스',47,'오르막이 있어서 상당히 힘드네요',3,0,8,'2023-02-15 19:41:24.000000'),(35,6,'사상역 산책로',48,'가볍게 달릴 수 있는 평지 코스에요',0,0,10,'2023-02-16 07:29:17.000000'),(37,10,'그린코아 앞 코스',50,'마실 나갔다 왔습니다.',0,0,5,'2023-02-16 20:31:36.000000'),(38,16,'밤 산책 코스입니다.',51,'차가 전혀 없어서 안전합니다!',0,0,4,'2023-02-16 23:50:23.000000'),(39,6,'인천 여주 여행(국토 종주 코스)?',52,'<h1>국토 종주 코스 모집합니다</h1><p><br></p><p>이번에 마음 잡고 한번 도전해 보려고 합니다.</p><p>같이 하실 분 연락주세요.</p>',0,0,1,'2023-02-17 02:34:21.000000'),(40,6,'설악산 쪽인데 조금 힘든 코스입니다',53,'<p>힘든 코스인데 경치가 좋아서 추천합니다!!</p>',0,0,2,'2023-02-17 02:35:08.000000'),(41,6,'동해안(상) 시원한 바다 느껴보실 분 추천!!!!!!!?',54,'<h1>동해안 코스인데 완전 좋아요</h1><p><strong><span style=\"color: #113dff\">하늘과 바다가 그냥 끝내줍니다.</span></strong></p>',0,0,2,'2023-02-17 02:36:50.000000'),(42,6,'송추 5고개입니다!! 가볍게 돌만하네요',55,'<p>사실 거짓말입니다</p><p>진짜 힘들어 죽을뻔...</p>',0,0,4,'2023-02-17 02:37:41.000000');
/*!40000 ALTER TABLE `course_board` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course_board_comment`
--

DROP TABLE IF EXISTS `course_board_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course_board_comment` (
  `course_board_comment_id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `course_board_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`course_board_comment_id`),
  KEY `FKeffhxmtchfd2gtli80m8kecnu` (`course_board_id`),
  KEY `FKe5408qelgdhvd1ry0j59nvlug` (`user_id`),
  CONSTRAINT `FKe5408qelgdhvd1ry0j59nvlug` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FKeffhxmtchfd2gtli80m8kecnu` FOREIGN KEY (`course_board_id`) REFERENCES `course_board` (`course_board_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_board_comment`
--

LOCK TABLES `course_board_comment` WRITE;
/*!40000 ALTER TABLE `course_board_comment` DISABLE KEYS */;
INSERT INTO `course_board_comment` VALUES (14,'차도로 다니면 위험해요','2023-02-14 13:23:21',15,4),(15,'여기서 의외의 반전은 자전거는 법률상 원래 차도로 다녀야 합니다.','2023-02-14 13:29:45',15,10),(16,'곰 나오니까 조심해야함','2023-02-14 15:57:36',18,10),(17,'ㅋㅋㅋ','2023-02-14 17:14:29',18,6),(19,'여기 좋아요~','2023-02-15 17:43:44',33,3),(20,'앗...저렇게 깊은 뜻이..','2023-02-15 21:44:22',15,6);
/*!40000 ALTER TABLE `course_board_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course_board_like`
--

DROP TABLE IF EXISTS `course_board_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course_board_like` (
  `course_board_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `selected` bit(1) DEFAULT NULL,
  PRIMARY KEY (`course_board_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_board_like`
--

LOCK TABLES `course_board_like` WRITE;
/*!40000 ALTER TABLE `course_board_like` DISABLE KEYS */;
INSERT INTO `course_board_like` VALUES (0,3,_binary ''),(15,6,_binary ''),(18,5,_binary ''),(18,6,_binary ''),(28,3,_binary ''),(28,6,_binary ''),(29,5,_binary ''),(29,6,_binary ''),(30,3,_binary ''),(30,5,_binary ''),(30,6,_binary ''),(33,3,_binary ''),(33,6,_binary ''),(33,9,_binary ''),(34,5,_binary ''),(34,6,_binary ''),(34,9,_binary ''),(35,11,_binary '\0');
/*!40000 ALTER TABLE `course_board_like` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course_board_visited`
--

DROP TABLE IF EXISTS `course_board_visited`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course_board_visited` (
  `course_board_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`course_board_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_board_visited`
--

LOCK TABLES `course_board_visited` WRITE;
/*!40000 ALTER TABLE `course_board_visited` DISABLE KEYS */;
INSERT INTO `course_board_visited` VALUES (15,3),(15,4),(15,5),(15,6),(15,8),(15,9),(15,10),(15,11),(15,12),(15,13),(16,6),(18,3),(18,5),(18,6),(18,8),(18,9),(18,10),(18,11),(18,12),(21,6),(28,3),(28,5),(28,6),(28,8),(28,9),(28,11),(28,15),(29,3),(29,5),(29,6),(29,8),(29,9),(29,11),(30,3),(30,4),(30,5),(30,6),(30,8),(30,9),(30,10),(30,11),(30,12),(30,13),(33,3),(33,5),(33,6),(33,8),(33,9),(33,10),(33,11),(33,15),(34,3),(34,5),(34,6),(34,8),(34,9),(34,10),(34,11),(34,13),(35,3),(35,6),(35,8),(35,9),(35,10),(35,11),(35,13),(35,15),(35,17),(35,20),(36,6),(36,10),(36,11),(37,3),(37,6),(37,8),(37,11),(37,23),(38,3),(38,6),(38,8),(38,10),(39,6),(40,6),(40,11),(41,6),(41,11),(42,3),(42,6),(42,11),(42,26);
/*!40000 ALTER TABLE `course_board_visited` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `custom_course`
--

DROP TABLE IF EXISTS `custom_course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `custom_course` (
  `course_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '경로을 업로드한 유저',
  `title` varchar(255) NOT NULL COMMENT '경로 이름',
  `name` varchar(255) NOT NULL COMMENT '파일의 원래 이름',
  `path` varchar(255) NOT NULL COMMENT '경로 파일 path 및 uuid',
  `type` varchar(50) NOT NULL,
  PRIMARY KEY (`course_id`),
  KEY `FK_user_TO_custom_course_1` (`user_id`),
  CONSTRAINT `FK71op9q0ovl5wrvkiamq9uyxrj` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FK_user_TO_custom_course_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `custom_course`
--

LOCK TABLES `custom_course` WRITE;
/*!40000 ALTER TABLE `custom_course` DISABLE KEYS */;
INSERT INTO `custom_course` VALUES (26,10,'테스트 주행이에요','gpxFile','GPXDIR/1376f6ff-d6bd-4c7b-a228-78b100d4de4b.gpx','gpx'),(28,8,'지리산','[첼린지도]_40._지리산투어','GPXDIR/d2c4103c-1d1f-4616-98c5-8bc83408307a.gpx','gpx'),(39,6,'인천공항','[첼린지도]_12._신시모도','GPXDIR/1280baa8-aa96-4315-94b3-8df017794296.gpx','gpx'),(40,6,'영산강 종주','[첼린지도]_44._영산강종주','GPXDIR/6fc33f9a-e789-435f-acb7-a030fbbfb95f.gpx','gpx'),(41,6,'강서구 산책 경로','gpxFile','GPXDIR/73c38109-3328-4bbd-80d7-d71b9e26994a.gpx','gpx'),(42,3,'d70f3339cd588911a2e25e014ffa0b5c53a036209b88c25ea3b09429c481f3fd','GPX_ (1)','GPXDIR/ae1c027e-cc52-4489-8000-fb6108e010e0.gpx','gpx'),(45,3,'d70f3339cd588911a2e25e014ffa0b5c53a036209b88c25ea3b09429c481f3fd','GPX_ (1)','GPXDIR/aa6f00b9-caee-4074-81ef-70e2030e1fd6.gpx','gpx'),(46,3,'춘천 갑시다','GPX_ (1)','GPXDIR/15ea1a5f-bc91-4418-abf7-de5a069a2243.gpx','gpx'),(47,6,'벽산 아파트 코스','gpxFile','GPXDIR/5bbd8ce5-22d1-4dff-84c3-949507808e06.gpx','gpx'),(48,6,'사상역 산책로','gpxFile','GPXDIR/f52bc8b9-a8f6-47ca-993a-54a44b8d9c07.gpx','gpx'),(50,10,'그린코아 앞 코스','gpxFile','GPXDIR/3ae1dc45-a6ca-47e3-9694-6c876d32dbe9.gpx','gpx'),(51,16,'밤 산책 코스입니다.','gpxFile','GPXDIR/93998bf0-b73f-4b11-9037-7557082cf5ab.gpx','gpx'),(52,6,'인천 여주 여행','국토종주_1일차_인천-여주','GPXDIR/1132dca2-6a95-40b8-a9d9-4b8c42cd4450.gpx','gpx'),(53,6,'설악 그락폰','[첼린지도]_22._설악그란폰도','GPXDIR/0a91988c-bf99-422b-8bc0-caf98bd083e7.gpx','gpx'),(54,6,'동해안(상)','[첼린지도]_29._동해강원(상)','GPXDIR/8c389b75-4a7a-431d-96cc-a1bb91a8d57e.gpx','gpx'),(55,6,'송추 5고개','송추5고개','GPXDIR/fed123bd-df1d-4c66-983f-60e3e63c74de.gpx','gpx');
/*!40000 ALTER TABLE `custom_course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `deal`
--

DROP TABLE IF EXISTS `deal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `deal` (
  `deal_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `kind` varchar(255) DEFAULT NULL,
  `title` varchar(255) NOT NULL COMMENT '거래 제목',
  `content` text NOT NULL COMMENT '거래 내용',
  `name` varchar(255) NOT NULL COMMENT '판매하는 물건의 이름',
  `price` bigint NOT NULL COMMENT '판매 가격',
  `on_sale` bit(1) NOT NULL DEFAULT b'1' COMMENT '판매 중인지 아닌지',
  `visited` bigint DEFAULT NULL,
  `time` datetime(6) NOT NULL,
  PRIMARY KEY (`deal_id`),
  KEY `FK_user_TO_deal_1` (`user_id`),
  CONSTRAINT `FK_user_TO_deal_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FKlrkitlk0jhhid7vo4irsy4f50` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `deal`
--

LOCK TABLES `deal` WRITE;
/*!40000 ALTER TABLE `deal` DISABLE KEYS */;
INSERT INTO `deal` VALUES (30,9,'완성차 / 프레임','얼마 안 썼어요','<p>6개월 썼어요<img src=\"https://i8e102.p.ssafy.io/api/deal/imageDownload/images/deal/6784f24d-c0e3-4fb7-8d02-0305dd183eed.jpg\" alt=\"image\" contenteditable=\"false\"><br></p>','완성차',150000,_binary '',4,'2023-02-15 17:40:35.000000'),(32,3,'완성차 / 프레임','출퇴근용 자전거 팔아요~','<p><img src=\"https://i8e102.p.ssafy.io/api/deal/imageDownload/images/deal/08188773-c99d-4c30-abe0-5026af9417d6.jpg\" alt=\"image\" contenteditable=\"false\">300만원에 샀습니다. 급처합니다.</p>','자전거',9000,_binary '',7,'2023-02-15 17:43:28.000000'),(33,9,'의류','헬멧팔아요 튼튼함','<p>원래ㅇ</p><p><br></p><p>ㅇ<img src=\"https://i8e102.p.ssafy.io/api/deal/imageDownload/images/deal/b07b1855-3f30-44f0-8ad6-8665d4450ace.jpg\" alt=\"image\" contenteditable=\"false\"><br></p><p><br></p><p>원래 바이크용으로 산거라 겁나 튼튼할듯</p>','라이딩헬멧',120000,_binary '',5,'2023-02-15 17:47:07.000000'),(35,4,'완성차 / 프레임','대회용 자전거 팝니다','<p>실제로 제가 대회에서 탔던 자전거입니다. 싸게 팝니다.<img src=\"https://i8e102.p.ssafy.io/api/deal/imageDownload/images/deal/da7954af-8007-4f00-acae-1630c816931c.jpg\" alt=\"image\" contenteditable=\"false\"><br></p>','자전거',900000,_binary '',7,'2023-02-15 17:50:42.000000'),(37,13,'의류','슈트 팔아요 깨끗하게 잘썼어요','<p><img src=\"https://i8e102.p.ssafy.io/api/deal/imageDownload/images/deal/c42b5cac-13d3-41b2-a238-ec8059219185.jpg\" alt=\"image\" contenteditable=\"false\"><br></p>','라이딩 슈트',80000,_binary '',5,'2023-02-15 17:51:02.000000'),(40,8,'완성차 / 프레임','미개봉 신품 팔아요','<p><img src=\"https://i8e102.p.ssafy.io/api/deal/imageDownload/images/deal/5552a381-d7e1-4c28-85ce-beda263d3cce.jpg\" alt=\"image\" contenteditable=\"false\"><br></p><p>이번에 새로 받은 미개봉 신품입니다~~~</p><p>필요가 없어져서 싸게 팔아요.</p>','외발자전거',130000,_binary '',7,'2023-02-15 21:38:32.000000'),(53,10,'완성차 / 프레임','알톤 썸원 팝니다.','<p><img src=\"https://i8e102.p.ssafy.io/api/deal/imageDownload/images/deal/d9c70ad2-b39b-418d-8cd2-503e3b30bc09.jpg\" alt=\"image\" contenteditable=\"false\"><br></p><p><br></p><p>22년식이고 8개월 정도 된 것 같습니다.</p><p>출퇴근용으로 좋아요 17만원에 팝니다!</p>','알톤 썸원',170000,_binary '',5,'2023-02-16 17:45:16.000000'),(54,10,'의류','자전거 헬멧 팝니다.','<p><img src=\"https://i8e102.p.ssafy.io/api/deal/imageDownload/images/deal/b3c6ef18-5921-4c54-a4ea-053ff660a1e6.jpg\" alt=\"image\" contenteditable=\"false\"><br></p><p>이번에 헬멧 새로 장만해서 예전에 쓰던 헬멧 팝니다.</p><p>넘어져 본 적 없어서 큰 기스는 없고 프리사이즈라 막 쓰기 편한 헬멧입니다.</p>','헬멧',20000,_binary '',1,'2023-02-16 19:09:10.000000'),(55,10,'의류','자전거 고글 팝니다.','<p><img src=\"https://i8e102.p.ssafy.io/api/deal/imageDownload/images/deal/b57c07a5-bc3e-4876-bb59-d5ec300e591d.jpg\" alt=\"image\" contenteditable=\"false\"><br></p><p>자전거 고글 새로 장만해서 기존에 쓰던거 싸게 팝니다.</p>','고글',5000,_binary '',3,'2023-02-16 19:10:44.000000'),(57,10,'의류','자전거 헬멧 세트로 팔아요','<p><img src=\"https://i8e102.p.ssafy.io/api/deal/imageDownload/images/deal/b9cf0a54-d403-4b89-a17b-efb254036bcd.jpg\" alt=\"image\" contenteditable=\"false\"><br></p><p>블랙 화이트 세트로 헬멧 판매합니다.</p><p>상태 완전 깨끗하고 매우 가벼워서 편하고 가볍게 쓰기 좋아요!</p>','자전거 헬멧 세트',15000,_binary '',4,'2023-02-16 19:21:42.000000'),(58,3,'완성차 / 프레임','자전거 완제품 팔아요','<p><img src=\"https://i8e102.p.ssafy.io/api/deal/imageDownload/images/deal/d2a764fd-8769-4725-8c02-c294c66fe31d.jpg\" alt=\"image\" contenteditable=\"false\"><br></p><p>메이커는 없고 집 앞에서 자전거 도로 탈 때 쓰던 자전거 입니다.</p><p>기어는 7단이고 산지 3개월 밖에 안됐습니다!</p>','일반 자전거',70000,_binary '',4,'2023-02-16 19:48:12.000000'),(59,3,'완성차 / 프레임','노메이커 일반 자전거 팝니다.','<p><img src=\"https://i8e102.p.ssafy.io/api/deal/imageDownload/images/deal/7387829b-70cc-4c61-96f7-1d6695a53492.jpg\" alt=\"image\" contenteditable=\"false\"><br></p><p>미국에서 샀었던 브랜드 없는 자전거 입니다.</p><p>당시 샀을 때 200달러 정도로 샀는데 9만원에 팝니다.</p><p>기어는 21단이고 MTB 타이어 입니다.</p>','일반 자전거',90000,_binary '',4,'2023-02-16 19:53:54.000000'),(60,6,'완성차 / 프레임','완제품 자전거 팔아요','<p><img src=\"https://i8e102.p.ssafy.io/api/deal/imageDownload/images/deal/527cfb6e-0233-470d-b6c6-22bf7d0143bb.jpg\" alt=\"image\" contenteditable=\"false\"><br></p><p>여동생이 쓰던 자전거인데 집앞에 다녀올 때 편합니다!</p><p>기스 거의 없고 싸게 판매할께요!</p>','자전거',65000,_binary '',5,'2023-02-16 19:57:27.000000'),(61,11,'완성차 / 프레임','거의 새 자전거 팝니다','<p><br></p><p><img src=\"https://i8e102.p.ssafy.io/api/deal/imageDownload/images/deal/b2cb43d3-45de-4d81-ba2f-4c5a853e2b72.jpg\" alt=\"image\" contenteditable=\"false\"><img src=\"https://i8e102.p.ssafy.io/api/deal/imageDownload/images/deal/3c897dcd-adaf-4b4c-977e-12afaae2a51c.jpg\" alt=\"image\" contenteditable=\"false\"><br></p><p>사용감 조금 있는 자전거입니다.</p><p>싸게 넘겨요</p>','자전거',130000,_binary '',1,'2023-02-16 22:29:21.000000'),(62,11,'완성차 / 프레임','오래 사용한 자전거 싸게 드려요','<p><img src=\"https://i8e102.p.ssafy.io/api/deal/imageDownload/images/deal/8c3fed0a-3099-43d6-ada3-3a9f0973685b.jpg\" alt=\"image\" contenteditable=\"false\"><img src=\"https://i8e102.p.ssafy.io/api/deal/imageDownload/images/deal/bb4383c8-e1c9-4228-a49a-dbe0c9c79195.jpg\" alt=\"image\" contenteditable=\"false\"><br></p><p>새로운 자전거를 구매해 기존에 사용하던 자전거 싸게 판매합니다!</p>','자전거',20000,_binary '',2,'2023-02-16 22:33:34.000000'),(63,11,'완성차 / 프레임','자전거 판매합니다!','<p><img src=\"https://i8e102.p.ssafy.io/api/deal/imageDownload/images/deal/634566f3-bdb9-4450-a2e9-75f5cdfe0644.jpg\" alt=\"image\" contenteditable=\"false\"><br></p><p>구매 문의 환영입니다!! 강아지도 보고가세용</p>','자전거',100000,_binary '',6,'2023-02-16 22:35:35.000000'),(64,8,'휠셋','얼마 안쓴 LED 바퀴','<p><img src=\"https://i8e102.p.ssafy.io/api/deal/imageDownload/images/deal/64a97788-b1e8-4191-af01-ddc48ba2d9d4.jpg\" alt=\"image\" contenteditable=\"false\"><br></p><p><br></p><p>멀쩡하게 잘 작동해요</p>','휠라이트',5000,_binary '',4,'2023-02-16 23:42:59.000000');
/*!40000 ALTER TABLE `deal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `deal_image`
--

DROP TABLE IF EXISTS `deal_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `deal_image` (
  `deal_image_id` bigint NOT NULL AUTO_INCREMENT,
  `deal_id` bigint NOT NULL,
  `name` varchar(255) NOT NULL COMMENT '이미지 원래 이름',
  `path` varchar(255) NOT NULL COMMENT '이미지의 경로 및 uuid',
  `type` varchar(255) NOT NULL,
  PRIMARY KEY (`deal_image_id`),
  KEY `FK_deal_TO_deal_image_1` (`deal_id`),
  CONSTRAINT `FK_deal_TO_deal_image_1` FOREIGN KEY (`deal_id`) REFERENCES `deal` (`deal_id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `deal_image`
--

LOCK TABLES `deal_image` WRITE;
/*!40000 ALTER TABLE `deal_image` DISABLE KEYS */;
INSERT INTO `deal_image` VALUES (27,50,'f2c76765-93e8-40c4-99e4-8c7c2bd20bc7.png','','png'),(30,53,'2ebb5edd-f4d1-487e-ac23-be3861748a33.jpg','images/deal/d9c70ad2-b39b-418d-8cd2-503e3b30bc09.jpg','jpg'),(31,54,'1494c763-e917-492b-8127-21531097a75b.jpg','images/deal/b3c6ef18-5921-4c54-a4ea-053ff660a1e6.jpg','jpg'),(32,55,'ed6f094a-813c-4e8c-a891-8253c1eb808d.jpg','images/deal/b57c07a5-bc3e-4876-bb59-d5ec300e591d.jpg','jpg'),(34,57,'7c3306dc-f4d8-4b83-9237-cb2515532e66.jpg','images/deal/b9cf0a54-d403-4b89-a17b-efb254036bcd.jpg','jpg'),(35,58,'bf634b9d-8e43-4a81-8ace-ee52ce729dd8.jpg','images/deal/d2a764fd-8769-4725-8c02-c294c66fe31d.jpg','jpg'),(36,59,'29fae0f7-8cae-41e1-acb5-c488e36c1a2f.jpg','images/deal/7387829b-70cc-4c61-96f7-1d6695a53492.jpg','jpg'),(37,60,'4c640915-0b6d-474e-86eb-6e3adfbe27e4.jpg','images/deal/527cfb6e-0233-470d-b6c6-22bf7d0143bb.jpg','jpg'),(38,61,'01d899be-2222-4398-bf5e-f2a13132327b.jpg','images/deal/b2cb43d3-45de-4d81-ba2f-4c5a853e2b72.jpg','jpg'),(39,61,'22749341-74bc-4ce2-a1fc-99cbede928ce.jpg','images/deal/3c897dcd-adaf-4b4c-977e-12afaae2a51c.jpg','jpg'),(40,62,'78090741-a07b-45fe-9c76-f8b237fc3b33.jpg','images/deal/8c3fed0a-3099-43d6-ada3-3a9f0973685b.jpg','jpg'),(41,62,'707852ae-65ba-4d82-af63-d94bd310755c.jpg','images/deal/bb4383c8-e1c9-4228-a49a-dbe0c9c79195.jpg','jpg'),(42,63,'ecd41381-7e01-4663-a269-fe4afcae94b6.jpg','images/deal/634566f3-bdb9-4450-a2e9-75f5cdfe0644.jpg','jpg'),(43,64,'8d8cd444-f219-483d-8940-50fcaaeada15.jpg','images/deal/64a97788-b1e8-4191-af01-ddc48ba2d9d4.jpg','jpg');
/*!40000 ALTER TABLE `deal_image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `deal_visited`
--

DROP TABLE IF EXISTS `deal_visited`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `deal_visited` (
  `deal_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`deal_id`,`user_id`),
  KEY `FK_user_TO_deal_visited_1` (`user_id`),
  CONSTRAINT `FK_deal_TO_deal_visited_1` FOREIGN KEY (`deal_id`) REFERENCES `deal` (`deal_id`),
  CONSTRAINT `FK_user_TO_deal_visited_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `deal_visited`
--

LOCK TABLES `deal_visited` WRITE;
/*!40000 ALTER TABLE `deal_visited` DISABLE KEYS */;
INSERT INTO `deal_visited` VALUES (32,3),(33,3),(35,3),(40,3),(55,3),(57,3),(58,3),(59,3),(62,3),(63,3),(64,3),(60,4),(63,4),(64,4),(60,5),(63,5),(30,6),(32,6),(33,6),(35,6),(37,6),(40,6),(53,6),(55,6),(57,6),(58,6),(59,6),(60,6),(61,6),(62,6),(63,6),(64,6),(32,8),(37,8),(40,8),(32,9),(37,9),(63,9),(30,10),(33,10),(35,10),(40,10),(53,10),(54,10),(55,10),(57,10),(58,10),(59,10),(60,10),(30,11),(32,11),(33,11),(35,11),(37,11),(40,11),(53,11),(57,11),(58,11),(59,11),(60,11),(63,11),(64,11),(40,15),(53,15),(32,18),(37,18),(35,20),(40,20),(53,25);
/*!40000 ALTER TABLE `deal_visited` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dong`
--

DROP TABLE IF EXISTS `dong`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dong` (
  `si_code` int NOT NULL,
  `gun_code` int NOT NULL,
  `code` int NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`si_code`,`gun_code`,`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dong`
--

LOCK TABLES `dong` WRITE;
/*!40000 ALTER TABLE `dong` DISABLE KEYS */;
INSERT INTO `dong` VALUES (11,10,530,'사직동'),(11,10,540,'삼청동'),(11,10,550,'부암동'),(11,10,560,'평창동'),(11,10,570,'무악동'),(11,10,580,'교남동'),(11,10,600,'가회동'),(11,10,610,'종로1·2·3·4가동'),(11,10,630,'종로5·6가동'),(11,10,640,'이화동'),(11,10,670,'창신1동'),(11,10,680,'창신2동'),(11,10,690,'창신3동'),(11,10,700,'숭인1동'),(11,10,710,'숭인2동'),(11,10,720,'청운효자동'),(11,10,730,'혜화동'),(11,20,520,'소공동'),(11,20,540,'회현동'),(11,20,550,'명동'),(11,20,570,'필동'),(11,20,580,'장충동'),(11,20,590,'광희동'),(11,20,600,'을지로동'),(11,20,650,'신당5동'),(11,20,670,'황학동'),(11,20,680,'중림동'),(11,20,690,'신당동'),(11,20,700,'다산동'),(11,20,710,'약수동'),(11,20,720,'청구동'),(11,20,730,'동화동'),(11,30,510,'후암동'),(11,30,520,'용산2가동'),(11,30,530,'남영동'),(11,30,570,'원효로2동'),(11,30,580,'효창동'),(11,30,590,'용문동'),(11,30,630,'이촌1동'),(11,30,640,'이촌2동'),(11,30,650,'이태원1동'),(11,30,660,'이태원2동'),(11,30,690,'서빙고동'),(11,30,700,'보광동'),(11,30,710,'청파동'),(11,30,720,'원효로1동'),(11,30,730,'한강로동'),(11,30,740,'한남동'),(11,40,520,'왕십리2동'),(11,40,540,'마장동'),(11,40,550,'사근동'),(11,40,560,'행당1동'),(11,40,570,'행당2동'),(11,40,580,'응봉동'),(11,40,590,'금호1가동'),(11,40,620,'금호4가동'),(11,40,650,'성수1가1동'),(11,40,660,'성수1가2동'),(11,40,670,'성수2가1동'),(11,40,680,'성수2가3동'),(11,40,690,'송정동'),(11,40,700,'용답동'),(11,40,710,'왕십리도선동'),(11,40,720,'금호2·3가동'),(11,40,730,'옥수동'),(11,50,530,'화양동'),(11,50,540,'군자동'),(11,50,550,'중곡1동'),(11,50,560,'중곡2동'),(11,50,570,'중곡3동'),(11,50,580,'중곡4동'),(11,50,590,'능동'),(11,50,600,'구의1동'),(11,50,610,'구의2동'),(11,50,620,'구의3동'),(11,50,630,'광장동'),(11,50,640,'자양1동'),(11,50,650,'자양2동'),(11,50,660,'자양3동'),(11,50,670,'자양4동'),(11,60,710,'회기동'),(11,60,720,'휘경1동'),(11,60,730,'휘경2동'),(11,60,800,'청량리동'),(11,60,810,'용신동'),(11,60,820,'제기동'),(11,60,830,'전농1동'),(11,60,840,'전농2동'),(11,60,860,'답십리2동'),(11,60,870,'장안1동'),(11,60,880,'장안2동'),(11,60,890,'이문1동'),(11,60,900,'이문2동'),(11,60,910,'답십리1동'),(11,70,520,'면목2동'),(11,70,540,'면목4동'),(11,70,550,'면목5동'),(11,70,570,'면목7동'),(11,70,590,'상봉1동'),(11,70,600,'상봉2동'),(11,70,610,'중화1동'),(11,70,620,'중화2동'),(11,70,640,'묵1동'),(11,70,650,'묵2동'),(11,70,680,'망우3동'),(11,70,690,'신내1동'),(11,70,700,'신내2동'),(11,70,710,'면목본동'),(11,70,720,'면목3·8동'),(11,70,730,'망우본동'),(11,80,580,'돈암1동'),(11,80,590,'돈암2동'),(11,80,600,'안암동'),(11,80,610,'보문동'),(11,80,620,'정릉1동'),(11,80,630,'정릉2동'),(11,80,640,'정릉3동'),(11,80,650,'정릉4동'),(11,80,660,'길음1동'),(11,80,680,'길음2동'),(11,80,710,'월곡1동'),(11,80,720,'월곡2동'),(11,80,760,'장위1동'),(11,80,770,'장위2동'),(11,80,780,'장위3동'),(11,80,810,'성북동'),(11,80,820,'삼선동'),(11,80,830,'동선동'),(11,80,840,'종암동'),(11,80,850,'석관동'),(11,90,600,'번1동'),(11,90,610,'번2동'),(11,90,620,'번3동'),(11,90,630,'수유1동'),(11,90,640,'수유2동'),(11,90,650,'수유3동'),(11,90,690,'삼양동'),(11,90,700,'미아동'),(11,90,710,'송중동'),(11,90,720,'송천동'),(11,90,730,'삼각산동'),(11,90,740,'우이동'),(11,90,750,'인수동'),(11,100,510,'쌍문1동'),(11,100,520,'쌍문2동'),(11,100,530,'쌍문3동'),(11,100,540,'쌍문4동'),(11,100,550,'방학1동'),(11,100,560,'방학2동'),(11,100,570,'방학3동'),(11,100,590,'창1동'),(11,100,600,'창2동'),(11,100,610,'창3동'),(11,100,620,'창4동'),(11,100,630,'창5동'),(11,100,640,'도봉1동'),(11,100,650,'도봉2동'),(11,110,510,'월계1동'),(11,110,520,'월계2동'),(11,110,530,'월계3동'),(11,110,560,'공릉2동'),(11,110,580,'하계1동'),(11,110,590,'하계2동'),(11,110,600,'중계본동'),(11,110,610,'중계1동'),(11,110,640,'중계4동'),(11,110,650,'상계1동'),(11,110,660,'상계2동'),(11,110,690,'상계5동'),(11,110,720,'상계8동'),(11,110,730,'상계9동'),(11,110,740,'상계10동'),(11,110,760,'상계3·4동'),(11,110,770,'상계6·7동'),(11,110,780,'중계2·3동'),(11,110,790,'공릉1동'),(11,120,510,'녹번동'),(11,120,520,'불광1동'),(11,120,550,'갈현1동'),(11,120,560,'갈현2동'),(11,120,570,'구산동'),(11,120,580,'대조동'),(11,120,590,'응암1동'),(11,120,600,'응암2동'),(11,120,650,'신사1동'),(11,120,660,'신사2동'),(11,120,670,'증산동'),(11,120,680,'수색동'),(11,120,710,'진관동'),(11,120,720,'불광2동'),(11,120,730,'응암3동'),(11,120,740,'역촌동'),(11,130,520,'천연동'),(11,130,620,'홍제1동'),(11,130,640,'홍제3동'),(11,130,650,'홍제2동'),(11,130,660,'홍은1동'),(11,130,680,'홍은2동'),(11,130,690,'남가좌1동'),(11,130,700,'남가좌2동'),(11,130,710,'북가좌1동'),(11,130,720,'북가좌2동'),(11,130,730,'충현동'),(11,130,740,'북아현동'),(11,130,750,'신촌동'),(11,130,760,'연희동'),(11,140,590,'용강동'),(11,140,600,'대흥동'),(11,140,610,'염리동'),(11,140,630,'신수동'),(11,140,660,'서교동'),(11,140,680,'합정동'),(11,140,690,'망원1동'),(11,140,700,'망원2동'),(11,140,710,'연남동'),(11,140,720,'성산1동'),(11,140,730,'성산2동'),(11,140,740,'상암동'),(11,140,750,'도화동'),(11,140,760,'서강동'),(11,140,770,'공덕동'),(11,140,780,'아현동'),(11,150,510,'목1동'),(11,150,520,'목2동'),(11,150,530,'목3동'),(11,150,540,'목4동'),(11,150,570,'신월1동'),(11,150,580,'신월2동'),(11,150,590,'신월3동'),(11,150,600,'신월4동'),(11,150,610,'신월5동'),(11,150,620,'신월6동'),(11,150,630,'신월7동'),(11,150,640,'신정1동'),(11,150,650,'신정2동'),(11,150,660,'신정3동'),(11,150,690,'신정6동'),(11,150,700,'신정7동'),(11,150,710,'목5동'),(11,150,720,'신정4동'),(11,160,510,'염창동'),(11,160,520,'등촌1동'),(11,160,530,'등촌2동'),(11,160,540,'등촌3동'),(11,160,550,'화곡본동'),(11,160,570,'화곡2동'),(11,160,580,'화곡3동'),(11,160,590,'화곡4동'),(11,160,610,'화곡6동'),(11,160,630,'화곡8동'),(11,160,640,'가양1동'),(11,160,650,'가양2동'),(11,160,660,'가양3동'),(11,160,670,'발산1동'),(11,160,690,'공항동'),(11,160,700,'방화1동'),(11,160,710,'방화2동'),(11,160,720,'방화3동'),(11,160,730,'화곡1동'),(11,160,740,'우장산동'),(11,170,510,'신도림동'),(11,170,520,'구로1동'),(11,170,540,'구로3동'),(11,170,550,'구로4동'),(11,170,560,'구로5동'),(11,170,610,'고척1동'),(11,170,620,'고척2동'),(11,170,640,'개봉2동'),(11,170,650,'개봉3동'),(11,170,670,'오류1동'),(11,170,690,'수궁동'),(11,170,700,'가리봉동'),(11,170,710,'구로2동'),(11,170,720,'개봉1동'),(11,170,730,'오류2동'),(11,170,740,'항동'),(11,180,510,'가산동'),(11,180,520,'독산1동'),(11,180,530,'독산2동'),(11,180,540,'독산3동'),(11,180,550,'독산4동'),(11,180,570,'시흥1동'),(11,180,580,'시흥2동'),(11,180,590,'시흥3동'),(11,180,600,'시흥4동'),(11,180,610,'시흥5동'),(11,190,540,'여의동'),(11,190,550,'당산1동'),(11,190,560,'당산2동'),(11,190,610,'양평1동'),(11,190,620,'양평2동'),(11,190,630,'신길1동'),(11,190,650,'신길3동'),(11,190,660,'신길4동'),(11,190,670,'신길5동'),(11,190,680,'신길6동'),(11,190,690,'신길7동'),(11,190,700,'대림1동'),(11,190,710,'대림2동'),(11,190,720,'대림3동'),(11,190,730,'영등포본동'),(11,190,740,'영등포동'),(11,190,750,'도림동'),(11,190,760,'문래동'),(11,200,520,'노량진2동'),(11,200,530,'상도1동'),(11,200,540,'상도2동'),(11,200,550,'상도3동'),(11,200,560,'상도4동'),(11,200,630,'사당1동'),(11,200,650,'사당3동'),(11,200,660,'사당4동'),(11,200,670,'사당5동'),(11,200,680,'대방동'),(11,200,690,'신대방1동'),(11,200,700,'신대방2동'),(11,200,710,'흑석동'),(11,200,720,'노량진1동'),(11,200,730,'사당2동'),(11,210,520,'보라매동'),(11,210,540,'청림동'),(11,210,570,'행운동'),(11,210,580,'낙성대동'),(11,210,610,'중앙동'),(11,210,620,'인헌동'),(11,210,630,'남현동'),(11,210,640,'서원동'),(11,210,650,'신원동'),(11,210,660,'서림동'),(11,210,680,'신사동'),(11,210,690,'신림동'),(11,210,710,'난향동'),(11,210,720,'조원동'),(11,210,730,'대학동'),(11,210,780,'은천동'),(11,210,790,'성현동'),(11,210,800,'청룡동'),(11,210,810,'난곡동'),(11,210,820,'삼성동'),(11,210,830,'미성동'),(11,220,510,'서초1동'),(11,220,520,'서초2동'),(11,220,530,'서초3동'),(11,220,540,'서초4동'),(11,220,550,'잠원동'),(11,220,560,'반포본동'),(11,220,570,'반포1동'),(11,220,580,'반포2동'),(11,220,590,'반포3동'),(11,220,600,'반포4동'),(11,220,610,'방배본동'),(11,220,620,'방배1동'),(11,220,630,'방배2동'),(11,220,640,'방배3동'),(11,220,650,'방배4동'),(11,220,660,'양재1동'),(11,220,670,'양재2동'),(11,220,680,'내곡동'),(11,230,510,'신사동'),(11,230,520,'논현1동'),(11,230,530,'논현2동'),(11,230,580,'삼성1동'),(11,230,590,'삼성2동'),(11,230,600,'대치1동'),(11,230,630,'대치4동'),(11,230,640,'역삼1동'),(11,230,650,'역삼2동'),(11,230,660,'도곡1동'),(11,230,670,'도곡2동'),(11,230,680,'개포1동'),(11,230,710,'개포4동'),(11,230,720,'일원본동'),(11,230,730,'일원1동'),(11,230,740,'일원2동'),(11,230,750,'수서동'),(11,230,760,'세곡동'),(11,230,770,'압구정동'),(11,230,780,'청담동'),(11,230,790,'대치2동'),(11,230,800,'개포2동'),(11,240,510,'풍납1동'),(11,240,520,'풍납2동'),(11,240,530,'거여1동'),(11,240,540,'거여2동'),(11,240,550,'마천1동'),(11,240,560,'마천2동'),(11,240,570,'방이1동'),(11,240,580,'방이2동'),(11,240,590,'오륜동'),(11,240,600,'오금동'),(11,240,610,'송파1동'),(11,240,620,'송파2동'),(11,240,630,'석촌동'),(11,240,640,'삼전동'),(11,240,650,'가락본동'),(11,240,660,'가락1동'),(11,240,670,'가락2동'),(11,240,680,'문정1동'),(11,240,690,'문정2동'),(11,240,710,'잠실본동'),(11,240,750,'잠실4동'),(11,240,770,'잠실6동'),(11,240,780,'잠실7동'),(11,240,790,'잠실2동'),(11,240,800,'잠실3동'),(11,240,810,'장지동'),(11,240,820,'위례동'),(11,250,530,'명일1동'),(11,250,540,'명일2동'),(11,250,550,'고덕1동'),(11,250,560,'고덕2동'),(11,250,580,'암사2동'),(11,250,590,'암사3동'),(11,250,610,'천호1동'),(11,250,630,'천호3동'),(11,250,650,'성내1동'),(11,250,660,'성내2동'),(11,250,670,'성내3동'),(11,250,700,'둔촌1동'),(11,250,710,'둔촌2동'),(11,250,720,'암사1동'),(11,250,730,'천호2동'),(11,250,740,'길동'),(11,250,750,'강일동'),(11,250,760,'상일1동'),(11,250,770,'상일2동'),(21,10,510,'중앙동'),(21,10,520,'동광동'),(21,10,530,'대청동'),(21,10,540,'보수동'),(21,10,560,'부평동'),(21,10,570,'광복동'),(21,10,580,'남포동'),(21,10,590,'영주1동'),(21,10,600,'영주2동'),(21,20,510,'동대신1동'),(21,20,520,'동대신2동'),(21,20,530,'동대신3동'),(21,20,540,'서대신1동'),(21,20,560,'서대신3동'),(21,20,570,'서대신4동'),(21,20,590,'부민동'),(21,20,610,'아미동'),(21,20,630,'초장동'),(21,20,640,'충무동'),(21,20,650,'남부민1동'),(21,20,680,'암남동'),(21,20,690,'남부민2동'),(21,30,510,'초량1동'),(21,30,520,'초량2동'),(21,30,530,'초량3동'),(21,30,550,'초량6동'),(21,30,560,'수정1동'),(21,30,570,'수정2동'),(21,30,590,'수정4동'),(21,30,600,'수정5동'),(21,30,660,'범일2동'),(21,30,680,'범일5동'),(21,30,700,'좌천동'),(21,30,710,'범일1동'),(21,40,530,'남항동'),(21,40,540,'영선1동'),(21,40,550,'영선2동'),(21,40,590,'봉래1동'),(21,40,630,'청학1동'),(21,40,640,'청학2동'),(21,40,650,'동삼1동'),(21,40,660,'동삼2동'),(21,40,670,'동삼3동'),(21,40,680,'신선동'),(21,40,690,'봉래2동'),(21,50,520,'부전2동'),(21,50,540,'연지동'),(21,50,550,'초읍동'),(21,50,560,'양정1동'),(21,50,570,'양정2동'),(21,50,610,'전포2동'),(21,50,640,'부암1동'),(21,50,660,'부암3동'),(21,50,680,'당감2동'),(21,50,700,'당감4동'),(21,50,720,'가야2동'),(21,50,740,'개금1동'),(21,50,750,'개금2동'),(21,50,760,'개금3동'),(21,50,770,'범천1동'),(21,50,800,'당감1동'),(21,50,810,'가야1동'),(21,50,820,'부전1동'),(21,50,830,'전포1동'),(21,50,840,'범천2동'),(21,60,510,'수민동'),(21,60,520,'복산동'),(21,60,550,'온천1동'),(21,60,560,'온천2동'),(21,60,570,'온천3동'),(21,60,580,'사직1동'),(21,60,590,'사직2동'),(21,60,600,'사직3동'),(21,60,610,'안락1동'),(21,60,620,'안락2동'),(21,60,630,'명장1동'),(21,60,640,'명장2동'),(21,60,650,'명륜동'),(21,70,530,'대연3동'),(21,70,540,'대연4동'),(21,70,550,'대연5동'),(21,70,560,'대연6동'),(21,70,570,'용호1동'),(21,70,580,'용호2동'),(21,70,590,'용호3동'),(21,70,600,'용호4동'),(21,70,610,'용당동'),(21,70,620,'감만1동'),(21,70,630,'감만2동'),(21,70,660,'문현1동'),(21,70,670,'문현2동'),(21,70,680,'문현3동'),(21,70,690,'문현4동'),(21,70,700,'대연1동'),(21,70,710,'우암동'),(21,80,510,'구포1동'),(21,80,520,'구포2동'),(21,80,530,'구포3동'),(21,80,540,'금곡동'),(21,80,550,'화명1동'),(21,80,560,'덕천1동'),(21,80,570,'덕천2동'),(21,80,580,'덕천3동'),(21,80,590,'만덕1동'),(21,80,600,'만덕2동'),(21,80,610,'만덕3동'),(21,80,620,'화명2동'),(21,80,630,'화명3동'),(21,90,530,'중1동'),(21,90,540,'중2동'),(21,90,560,'송정동'),(21,90,580,'반여2동'),(21,90,590,'반여3동'),(21,90,620,'반송2동'),(21,90,640,'재송1동'),(21,90,650,'재송2동'),(21,90,660,'좌1동'),(21,90,670,'좌2동'),(21,90,680,'좌3동'),(21,90,690,'좌4동'),(21,90,700,'반여1동'),(21,90,710,'반여4동'),(21,90,720,'반송1동'),(21,90,730,'우1동'),(21,90,740,'우2동'),(21,90,750,'우3동'),(21,100,510,'괴정1동'),(21,100,520,'괴정2동'),(21,100,530,'괴정3동'),(21,100,540,'괴정4동'),(21,100,550,'당리동'),(21,100,560,'하단1동'),(21,100,570,'하단2동'),(21,100,580,'신평1동'),(21,100,590,'신평2동'),(21,100,600,'장림1동'),(21,100,610,'장림2동'),(21,100,620,'다대1동'),(21,100,630,'다대2동'),(21,100,640,'구평동'),(21,100,650,'감천1동'),(21,100,660,'감천2동'),(21,110,510,'서1동'),(21,110,520,'서2동'),(21,110,570,'부곡1동'),(21,110,580,'부곡2동'),(21,110,590,'부곡3동'),(21,110,600,'부곡4동'),(21,110,610,'장전1동'),(21,110,640,'선두구동'),(21,110,670,'청룡노포동'),(21,110,680,'남산동'),(21,110,690,'구서1동'),(21,110,700,'구서2동'),(21,110,710,'금성동'),(21,110,720,'서3동'),(21,110,730,'금사회동동'),(21,110,740,'장전2동'),(21,120,510,'대저1동'),(21,120,520,'대저2동'),(21,120,530,'강동동'),(21,120,550,'가락동'),(21,120,560,'녹산동'),(21,120,580,'가덕도동'),(21,120,590,'명지1동'),(21,120,600,'명지2동'),(21,130,510,'거제1동'),(21,130,520,'거제2동'),(21,130,530,'거제3동'),(21,130,540,'거제4동'),(21,130,550,'연산1동'),(21,130,560,'연산2동'),(21,130,570,'연산3동'),(21,130,580,'연산4동'),(21,130,590,'연산5동'),(21,130,600,'연산6동'),(21,130,620,'연산8동'),(21,130,630,'연산9동'),(21,140,510,'남천1동'),(21,140,520,'남천2동'),(21,140,530,'수영동'),(21,140,540,'망미1동'),(21,140,550,'망미2동'),(21,140,560,'광안1동'),(21,140,570,'광안2동'),(21,140,580,'광안3동'),(21,140,590,'광안4동'),(21,140,600,'민락동'),(21,150,510,'삼락동'),(21,150,520,'모라1동'),(21,150,540,'모라3동'),(21,150,550,'덕포1동'),(21,150,560,'덕포2동'),(21,150,570,'괘법동'),(21,150,600,'주례1동'),(21,150,610,'주례2동'),(21,150,620,'주례3동'),(21,150,630,'학장동'),(21,150,640,'엄궁동'),(21,150,650,'감전동'),(21,510,110,'기장읍'),(21,510,111,'일광읍'),(21,510,120,'장안읍'),(21,510,130,'정관읍'),(21,510,330,'철마면'),(22,10,540,'삼덕동'),(22,10,560,'성내1동'),(22,10,590,'성내2동'),(22,10,610,'성내3동'),(22,10,620,'대신동'),(22,10,640,'남산1동'),(22,10,650,'남산2동'),(22,10,660,'남산3동'),(22,10,670,'남산4동'),(22,10,680,'대봉1동'),(22,10,690,'대봉2동'),(22,10,700,'동인동'),(22,20,510,'신암1동'),(22,20,520,'신암2동'),(22,20,530,'신암3동'),(22,20,540,'신암4동'),(22,20,550,'신암5동'),(22,20,560,'신천1·2동'),(22,20,580,'신천3동'),(22,20,590,'신천4동'),(22,20,600,'효목1동'),(22,20,610,'효목2동'),(22,20,620,'도평동'),(22,20,630,'불로·봉무동'),(22,20,650,'지저동'),(22,20,660,'동촌동'),(22,20,680,'방촌동'),(22,20,690,'해안동'),(22,20,710,'공산동'),(22,20,730,'안심1동'),(22,20,740,'안심2동'),(22,20,760,'안심3동'),(22,20,770,'안심4동'),(22,20,780,'혁신동'),(22,30,510,'내당1동'),(22,30,520,'내당2·3동'),(22,30,530,'내당4동'),(22,30,540,'비산1동'),(22,30,550,'비산2·3동'),(22,30,560,'비산4동'),(22,30,570,'비산5동'),(22,30,580,'비산6동'),(22,30,590,'비산7동'),(22,30,600,'평리1동'),(22,30,610,'평리2동'),(22,30,620,'평리3동'),(22,30,630,'평리4동'),(22,30,640,'평리5동'),(22,30,650,'평리6동'),(22,30,660,'상중이동'),(22,30,680,'원대동'),(22,40,510,'이천동'),(22,40,530,'봉덕1동'),(22,40,540,'봉덕2동'),(22,40,550,'봉덕3동'),(22,40,560,'대명1동'),(22,40,570,'대명2동'),(22,40,580,'대명3동'),(22,40,590,'대명4동'),(22,40,600,'대명5동'),(22,40,610,'대명6동'),(22,40,640,'대명9동'),(22,40,650,'대명10동'),(22,40,660,'대명11동'),(22,50,510,'고성동'),(22,50,520,'칠성동'),(22,50,550,'침산1동'),(22,50,560,'침산2동'),(22,50,570,'침산3동'),(22,50,610,'산격1동'),(22,50,620,'산격2동'),(22,50,630,'산격3동'),(22,50,640,'산격4동'),(22,50,650,'복현1동'),(22,50,660,'복현2동'),(22,50,700,'검단동'),(22,50,710,'무태조야동'),(22,50,740,'태전2동'),(22,50,750,'관문동'),(22,50,760,'읍내동'),(22,50,770,'관음동'),(22,50,780,'태전1동'),(22,50,790,'구암동'),(22,50,810,'노원동'),(22,50,820,'동천동'),(22,50,830,'국우동'),(22,50,840,'대현동'),(22,60,510,'범어1동'),(22,60,520,'범어2동'),(22,60,530,'범어3동'),(22,60,540,'범어4동'),(22,60,550,'만촌1동'),(22,60,560,'만촌2동'),(22,60,570,'만촌3동'),(22,60,580,'수성1가동'),(22,60,590,'수성2·3가동'),(22,60,600,'수성4가동'),(22,60,610,'황금1동'),(22,60,620,'황금2동'),(22,60,630,'중동'),(22,60,640,'상동'),(22,60,650,'파동'),(22,60,660,'두산동'),(22,60,670,'지산1동'),(22,60,680,'지산2동'),(22,60,690,'범물1동'),(22,60,700,'범물2동'),(22,60,710,'고산1동'),(22,60,720,'고산2동'),(22,60,730,'고산3동'),(22,70,550,'두류3동'),(22,70,560,'본리동'),(22,70,570,'감삼동'),(22,70,580,'죽전동'),(22,70,590,'장기동'),(22,70,600,'이곡1동'),(22,70,610,'신당동'),(22,70,630,'월성2동'),(22,70,650,'상인1동'),(22,70,660,'상인2동'),(22,70,670,'상인3동'),(22,70,680,'도원동'),(22,70,690,'송현1동'),(22,70,700,'송현2동'),(22,70,710,'본동'),(22,70,720,'용산1동'),(22,70,730,'용산2동'),(22,70,740,'이곡2동'),(22,70,750,'성당동'),(22,70,760,'두류1·2동'),(22,70,770,'월성1동'),(22,70,780,'진천동'),(22,70,790,'유천동'),(22,510,110,'화원읍'),(22,510,120,'논공읍'),(22,510,130,'다사읍'),(22,510,140,'유가읍'),(22,510,150,'옥포읍'),(22,510,160,'현풍읍'),(22,510,310,'가창면'),(22,510,320,'하빈면'),(22,510,360,'구지면'),(23,10,520,'연안동'),(23,10,530,'신포동'),(23,10,540,'신흥동'),(23,10,560,'도원동'),(23,10,570,'율목동'),(23,10,580,'동인천동'),(23,10,630,'용유동'),(23,10,640,'운서동'),(23,10,650,'영종동'),(23,10,660,'영종1동'),(23,10,670,'개항동'),(23,20,510,'만석동'),(23,20,520,'화수1·화평동'),(23,20,530,'화수2동'),(23,20,550,'송현1·2동'),(23,20,570,'송현3동'),(23,20,580,'송림1동'),(23,20,590,'송림2동'),(23,20,600,'송림3·5동'),(23,20,610,'송림4동'),(23,20,630,'송림6동'),(23,20,640,'금창동'),(23,40,510,'옥련1동'),(23,40,520,'선학동'),(23,40,530,'연수1동'),(23,40,540,'연수2동'),(23,40,550,'연수3동'),(23,40,560,'청학동'),(23,40,570,'동춘1동'),(23,40,580,'동춘2동'),(23,40,590,'동춘3동'),(23,40,600,'옥련2동'),(23,40,640,'송도1동'),(23,40,650,'송도3동'),(23,40,660,'송도2동'),(23,40,680,'송도4동'),(23,40,690,'송도5동'),(23,50,510,'구월1동'),(23,50,520,'구월2동'),(23,50,530,'구월3동'),(23,50,540,'구월4동'),(23,50,550,'간석1동'),(23,50,560,'간석2동'),(23,50,570,'간석3동'),(23,50,580,'간석4동'),(23,50,590,'만수1동'),(23,50,600,'만수2동'),(23,50,610,'만수3동'),(23,50,620,'만수4동'),(23,50,630,'만수5동'),(23,50,640,'만수6동'),(23,50,670,'남촌도림동'),(23,50,720,'논현1동'),(23,50,730,'논현2동'),(23,50,740,'논현고잔동'),(23,50,750,'장수서창동'),(23,50,760,'서창2동'),(23,60,510,'부평1동'),(23,60,520,'부평2동'),(23,60,530,'부평3동'),(23,60,540,'부평4동'),(23,60,550,'부평5동'),(23,60,560,'부평6동'),(23,60,570,'산곡1동'),(23,60,580,'산곡2동'),(23,60,590,'산곡3동'),(23,60,600,'산곡4동'),(23,60,610,'청천1동'),(23,60,620,'청천2동'),(23,60,630,'갈산1동'),(23,60,640,'갈산2동'),(23,60,650,'삼산1동'),(23,60,660,'부개1동'),(23,60,670,'부개2동'),(23,60,680,'부개3동'),(23,60,690,'일신동'),(23,60,700,'십정1동'),(23,60,710,'십정2동'),(23,60,720,'삼산2동'),(23,70,510,'효성1동'),(23,70,520,'효성2동'),(23,70,530,'계산1동'),(23,70,540,'계산2동'),(23,70,550,'계산3동'),(23,70,560,'작전1동'),(23,70,570,'작전2동'),(23,70,580,'작전서운동'),(23,70,610,'계양2동'),(23,70,620,'계산4동'),(23,70,630,'계양1동'),(23,70,640,'계양3동'),(23,80,510,'검암경서동'),(23,80,530,'연희동'),(23,80,540,'가정1동'),(23,80,550,'가정2동'),(23,80,560,'가정3동'),(23,80,580,'석남1동'),(23,80,590,'석남2동'),(23,80,600,'석남3동'),(23,80,620,'가좌1동'),(23,80,630,'가좌2동'),(23,80,640,'가좌3동'),(23,80,650,'가좌4동'),(23,80,730,'신현원창동'),(23,80,740,'청라1동'),(23,80,780,'청라2동'),(23,80,790,'청라3동'),(23,80,800,'검단동'),(23,80,810,'불로대곡동'),(23,80,840,'오류왕길동'),(23,80,850,'당하동'),(23,80,860,'마전동'),(23,80,870,'원당동'),(23,80,880,'아라동'),(23,90,520,'숭의2동'),(23,90,540,'숭의4동'),(23,90,560,'용현2동'),(23,90,570,'용현3동'),(23,90,590,'용현5동'),(23,90,600,'학익1동'),(23,90,610,'학익2동'),(23,90,620,'도화1동'),(23,90,650,'주안1동'),(23,90,660,'주안2동'),(23,90,670,'주안3동'),(23,90,680,'주안4동'),(23,90,690,'주안5동'),(23,90,700,'주안6동'),(23,90,710,'주안7동'),(23,90,720,'주안8동'),(23,90,730,'관교동'),(23,90,740,'문학동'),(23,90,750,'숭의1·3동'),(23,90,760,'용현1·4동'),(23,90,770,'도화2·3동'),(23,510,110,'강화읍'),(23,510,310,'선원면'),(23,510,320,'불은면'),(23,510,330,'길상면'),(23,510,340,'화도면'),(23,510,350,'양도면'),(23,510,360,'내가면'),(23,510,370,'하점면'),(23,510,380,'양사면'),(23,510,390,'송해면'),(23,510,400,'교동면'),(23,510,410,'삼산면'),(23,510,420,'서도면'),(23,520,310,'북도면'),(23,520,320,'연평면'),(23,520,330,'백령면'),(23,520,340,'대청면'),(23,520,350,'덕적면'),(23,520,360,'자월면'),(23,520,370,'영흥면'),(24,10,510,'충장동'),(24,10,540,'동명동'),(24,10,560,'계림1동'),(24,10,580,'계림2동'),(24,10,590,'산수1동'),(24,10,610,'산수2동'),(24,10,620,'지산1동'),(24,10,630,'지산2동'),(24,10,640,'서남동'),(24,10,680,'학동'),(24,10,710,'학운동'),(24,10,720,'지원1동'),(24,10,730,'지원2동'),(24,20,510,'양동'),(24,20,530,'양3동'),(24,20,540,'농성1동'),(24,20,550,'농성2동'),(24,20,560,'광천동'),(24,20,570,'유덕동'),(24,20,580,'상무1동'),(24,20,590,'상무2동'),(24,20,600,'화정1동'),(24,20,610,'화정2동'),(24,20,620,'화정3동'),(24,20,630,'화정4동'),(24,20,640,'서창동'),(24,20,660,'치평동'),(24,20,670,'풍암동'),(24,20,680,'금호1동'),(24,20,690,'금호2동'),(24,20,700,'동천동'),(24,30,510,'양림동'),(24,30,520,'방림1동'),(24,30,530,'방림2동'),(24,30,540,'사직동'),(24,30,570,'월산동'),(24,30,600,'월산4동'),(24,30,610,'월산5동'),(24,30,620,'백운1동'),(24,30,630,'백운2동'),(24,30,640,'주월1동'),(24,30,650,'주월2동'),(24,30,670,'송암동'),(24,30,680,'봉선1동'),(24,30,690,'봉선2동'),(24,30,700,'대촌동'),(24,30,710,'진월동'),(24,30,720,'효덕동'),(24,40,510,'중흥1동'),(24,40,520,'중흥2동'),(24,40,530,'중흥3동'),(24,40,540,'중앙동'),(24,40,550,'임동'),(24,40,560,'신안동'),(24,40,570,'용봉동'),(24,40,580,'운암1동'),(24,40,590,'운암2동'),(24,40,600,'운암3동'),(24,40,610,'동림동'),(24,40,620,'우산동'),(24,40,630,'풍향동'),(24,40,640,'문화동'),(24,40,650,'문흥1동'),(24,40,660,'문흥2동'),(24,40,670,'두암1동'),(24,40,680,'두암2동'),(24,40,690,'두암3동'),(24,40,700,'삼각동'),(24,40,710,'매곡동'),(24,40,720,'오치1동'),(24,40,730,'오치2동'),(24,40,740,'석곡동'),(24,40,780,'일곡동'),(24,40,800,'양산동'),(24,40,810,'건국동'),(24,40,820,'신용동'),(24,50,510,'송정1동'),(24,50,520,'송정2동'),(24,50,540,'도산동'),(24,50,550,'신흥동'),(24,50,560,'어룡동'),(24,50,580,'우산동'),(24,50,590,'월곡1동'),(24,50,600,'월곡2동'),(24,50,610,'비아동'),(24,50,630,'하남동'),(24,50,640,'임곡동'),(24,50,650,'동곡동'),(24,50,660,'평동'),(24,50,670,'삼도동'),(24,50,680,'본량동'),(24,50,690,'첨단1동'),(24,50,700,'첨단2동'),(24,50,710,'운남동'),(24,50,730,'신창동'),(24,50,740,'신가동'),(24,50,750,'수완동'),(25,10,530,'효동'),(25,10,550,'판암1동'),(25,10,560,'판암2동'),(25,10,570,'용운동'),(25,10,600,'자양동'),(25,10,630,'가양1동'),(25,10,640,'가양2동'),(25,10,650,'용전동'),(25,10,680,'홍도동'),(25,10,730,'대청동'),(25,10,750,'산내동'),(25,10,760,'중앙동'),(25,10,770,'신인동'),(25,10,780,'대동'),(25,10,790,'성남동'),(25,10,800,'삼성동'),(25,20,510,'은행선화동'),(25,20,530,'목동'),(25,20,540,'중촌동'),(25,20,550,'대흥동'),(25,20,560,'문창동'),(25,20,570,'석교동'),(25,20,580,'대사동'),(25,20,590,'부사동'),(25,20,600,'용두동'),(25,20,620,'오류동'),(25,20,630,'태평1동'),(25,20,640,'태평2동'),(25,20,650,'유천1동'),(25,20,660,'유천2동'),(25,20,670,'문화1동'),(25,20,680,'문화2동'),(25,20,690,'산성동'),(25,30,510,'복수동'),(25,30,511,'가수원동'),(25,30,512,'도안동'),(25,30,520,'도마1동'),(25,30,530,'도마2동'),(25,30,540,'정림동'),(25,30,550,'변동'),(25,30,560,'용문동'),(25,30,570,'탄방동'),(25,30,590,'둔산1동'),(25,30,600,'둔산2동'),(25,30,610,'괴정동'),(25,30,620,'가장동'),(25,30,630,'내동'),(25,30,640,'갈마1동'),(25,30,650,'갈마2동'),(25,30,660,'월평1동'),(25,30,670,'월평2동'),(25,30,680,'월평3동'),(25,30,690,'만년동'),(25,30,710,'기성동'),(25,30,720,'관저1동'),(25,30,730,'관저2동'),(25,30,740,'둔산3동'),(25,40,540,'온천2동'),(25,40,550,'신성동'),(25,40,570,'전민동'),(25,40,590,'노은1동'),(25,40,610,'구즉동'),(25,40,630,'관평동'),(25,40,640,'온천1동'),(25,40,660,'노은2동'),(25,40,670,'노은3동'),(25,40,680,'진잠동'),(25,40,690,'학하동'),(25,40,700,'상대동'),(25,40,710,'원신흥동'),(25,50,510,'오정동'),(25,50,520,'대화동'),(25,50,530,'회덕동'),(25,50,540,'비래동'),(25,50,550,'중리동'),(25,50,560,'법1동'),(25,50,570,'법2동'),(25,50,580,'신탄진동'),(25,50,590,'석봉동'),(25,50,600,'덕암동'),(25,50,610,'목상동'),(25,50,620,'송촌동'),(26,10,510,'학성동'),(26,10,520,'반구1동'),(26,10,530,'반구2동'),(26,10,540,'복산1동'),(26,10,550,'복산2동'),(26,10,590,'우정동'),(26,10,600,'태화동'),(26,10,610,'다운동'),(26,10,620,'병영1동'),(26,10,630,'병영2동'),(26,10,640,'약사동'),(26,10,660,'성안동'),(26,10,670,'중앙동'),(26,20,510,'신정1동'),(26,20,520,'신정2동'),(26,20,530,'신정3동'),(26,20,540,'신정4동'),(26,20,550,'신정5동'),(26,20,560,'달동'),(26,20,570,'삼산동'),(26,20,580,'삼호동'),(26,20,590,'무거동'),(26,20,600,'옥동'),(26,20,610,'야음장생포동'),(26,20,620,'대현동'),(26,20,630,'수암동'),(26,20,640,'선암동'),(26,30,510,'방어동'),(26,30,520,'일산동'),(26,30,530,'화정동'),(26,30,540,'대송동'),(26,30,550,'전하1동'),(26,30,580,'남목1동'),(26,30,590,'남목2동'),(26,30,600,'남목3동'),(26,30,610,'전하2동'),(26,40,510,'농소1동'),(26,40,520,'농소2동'),(26,40,530,'농소3동'),(26,40,540,'강동동'),(26,40,560,'효문동'),(26,40,570,'송정동'),(26,40,580,'양정동'),(26,40,590,'염포동'),(26,510,110,'온산읍'),(26,510,120,'언양읍'),(26,510,130,'온양읍'),(26,510,140,'범서읍'),(26,510,150,'청량읍'),(26,510,160,'삼남읍'),(26,510,310,'서생면'),(26,510,340,'웅촌면'),(26,510,360,'두동면'),(26,510,370,'두서면'),(26,510,380,'상북면'),(26,510,400,'삼동면'),(29,10,110,'조치원읍'),(29,10,310,'연기면'),(29,10,320,'연동면'),(29,10,330,'부강면'),(29,10,340,'금남면'),(29,10,350,'장군면'),(29,10,360,'연서면'),(29,10,370,'전의면'),(29,10,380,'전동면'),(29,10,390,'소정면'),(29,10,560,'종촌동'),(29,10,590,'아름동'),(29,10,600,'고운동'),(29,10,610,'한솔동'),(29,10,640,'대평동'),(29,10,660,'보람동'),(29,10,670,'새롬동'),(29,10,680,'다정동'),(29,10,690,'도담동'),(29,10,700,'해밀동'),(29,10,710,'소담동'),(29,10,720,'반곡동'),(31,11,540,'파장동'),(31,11,550,'율천동'),(31,11,560,'정자1동'),(31,11,570,'정자2동'),(31,11,580,'영화동'),(31,11,590,'송죽동'),(31,11,600,'조원1동'),(31,11,610,'연무동'),(31,11,620,'정자3동'),(31,11,630,'조원2동'),(31,12,520,'세류1동'),(31,12,530,'세류2동'),(31,12,540,'세류3동'),(31,12,550,'평동'),(31,12,560,'서둔동'),(31,12,570,'구운동'),(31,12,600,'권선1동'),(31,12,610,'곡선동'),(31,12,620,'입북동'),(31,12,640,'권선2동'),(31,12,650,'금곡동'),(31,12,660,'호매실동'),(31,13,530,'지동'),(31,13,540,'우만1동'),(31,13,550,'우만2동'),(31,13,560,'인계동'),(31,13,670,'매교동'),(31,13,680,'매산동'),(31,13,690,'고등동'),(31,13,700,'화서1동'),(31,13,710,'화서2동'),(31,13,720,'행궁동'),(31,14,510,'매탄1동'),(31,14,520,'매탄2동'),(31,14,530,'매탄3동'),(31,14,540,'매탄4동'),(31,14,600,'원천동'),(31,14,620,'광교1동'),(31,14,630,'광교2동'),(31,14,640,'영통1동'),(31,14,650,'영통2동'),(31,14,660,'영통3동'),(31,14,670,'망포1동'),(31,14,680,'망포2동'),(31,21,510,'신흥1동'),(31,21,520,'신흥2동'),(31,21,530,'신흥3동'),(31,21,540,'태평1동'),(31,21,550,'태평2동'),(31,21,560,'태평3동'),(31,21,570,'태평4동'),(31,21,580,'수진1동'),(31,21,590,'수진2동'),(31,21,600,'단대동'),(31,21,610,'산성동'),(31,21,620,'양지동'),(31,21,640,'신촌동'),(31,21,650,'고등동'),(31,21,660,'시흥동'),(31,21,670,'복정동'),(31,21,680,'위례동'),(31,22,510,'성남동'),(31,22,530,'금광1동'),(31,22,540,'금광2동'),(31,22,550,'은행1동'),(31,22,560,'은행2동'),(31,22,570,'상대원1동'),(31,22,580,'상대원2동'),(31,22,590,'상대원3동'),(31,22,600,'하대원동'),(31,22,610,'도촌동'),(31,22,620,'중앙동'),(31,23,510,'분당동'),(31,23,520,'수내3동'),(31,23,530,'수내1동'),(31,23,540,'수내2동'),(31,23,550,'정자2동'),(31,23,560,'정자3동'),(31,23,580,'서현1동'),(31,23,590,'서현2동'),(31,23,600,'이매1동'),(31,23,610,'이매2동'),(31,23,620,'야탑1동'),(31,23,630,'야탑3동'),(31,23,640,'야탑2동'),(31,23,670,'구미동'),(31,23,680,'운중동'),(31,23,710,'금곡동'),(31,23,720,'구미1동'),(31,23,740,'삼평동'),(31,23,750,'판교동'),(31,23,760,'백현동'),(31,23,770,'정자1동'),(31,23,780,'정자동'),(31,30,520,'의정부2동'),(31,30,550,'호원1동'),(31,30,560,'장암동'),(31,30,570,'신곡1동'),(31,30,580,'신곡2동'),(31,30,590,'송산1동'),(31,30,600,'자금동'),(31,30,640,'녹양동'),(31,30,650,'호원2동'),(31,30,670,'흥선동'),(31,30,680,'가능동'),(31,30,690,'의정부1동'),(31,30,700,'송산2동'),(31,30,710,'송산3동'),(31,41,510,'안양1동'),(31,41,520,'안양2동'),(31,41,530,'안양3동'),(31,41,540,'안양4동'),(31,41,550,'안양5동'),(31,41,560,'안양6동'),(31,41,570,'안양7동'),(31,41,580,'안양8동'),(31,41,590,'안양9동'),(31,41,600,'석수1동'),(31,41,610,'석수2동'),(31,41,620,'석수3동'),(31,41,630,'박달1동'),(31,41,640,'박달2동'),(31,42,510,'비산1동'),(31,42,520,'비산2동'),(31,42,530,'비산3동'),(31,42,540,'부흥동'),(31,42,550,'달안동'),(31,42,560,'관양1동'),(31,42,570,'관양2동'),(31,42,580,'부림동'),(31,42,590,'평촌동'),(31,42,600,'평안동'),(31,42,610,'귀인동'),(31,42,620,'호계1동'),(31,42,630,'호계2동'),(31,42,640,'호계3동'),(31,42,650,'범계동'),(31,42,660,'신촌동'),(31,42,670,'갈산동'),(31,50,870,'심곡동'),(31,50,880,'부천동'),(31,50,890,'중동'),(31,50,900,'신중동'),(31,50,910,'상동'),(31,50,920,'대산동'),(31,50,930,'소사본동'),(31,50,940,'범안동'),(31,50,950,'성곡동'),(31,50,960,'오정동'),(31,60,510,'광명1동'),(31,60,520,'광명2동'),(31,60,530,'광명3동'),(31,60,540,'광명4동'),(31,60,550,'광명5동'),(31,60,560,'광명6동'),(31,60,570,'광명7동'),(31,60,580,'철산1동'),(31,60,590,'철산2동'),(31,60,600,'철산3동'),(31,60,610,'철산4동'),(31,60,620,'하안1동'),(31,60,630,'하안2동'),(31,60,640,'하안3동'),(31,60,650,'하안4동'),(31,60,660,'소하1동'),(31,60,680,'학온동'),(31,60,690,'소하2동'),(31,60,700,'일직동'),(31,70,110,'팽성읍'),(31,70,120,'안중읍'),(31,70,130,'포승읍'),(31,70,140,'청북읍'),(31,70,310,'진위면'),(31,70,320,'서탄면'),(31,70,330,'고덕면'),(31,70,340,'오성면'),(31,70,370,'현덕면'),(31,70,510,'중앙동'),(31,70,520,'서정동'),(31,70,530,'송탄동'),(31,70,550,'지산동'),(31,70,560,'송북동'),(31,70,570,'신장1동'),(31,70,580,'신장2동'),(31,70,590,'신평동'),(31,70,600,'원평동'),(31,70,610,'통복동'),(31,70,640,'세교동'),(31,70,650,'비전2동'),(31,70,660,'용이동'),(31,70,670,'비전1동'),(31,70,680,'동삭동'),(31,70,690,'고덕동'),(31,80,510,'생연1동'),(31,80,520,'생연2동'),(31,80,530,'중앙동'),(31,80,550,'보산동'),(31,80,560,'불현동'),(31,80,580,'소요동'),(31,80,600,'상패동'),(31,80,610,'송내동'),(31,91,510,'일동'),(31,91,540,'본오1동'),(31,91,550,'본오2동'),(31,91,560,'본오3동'),(31,91,570,'부곡동'),(31,91,580,'월피동'),(31,91,620,'성포동'),(31,91,710,'반월동'),(31,91,730,'안산동'),(31,91,740,'이동'),(31,91,760,'사동'),(31,91,770,'사이동'),(31,91,780,'해양동'),(31,92,590,'와동'),(31,92,660,'초지동'),(31,92,680,'선부1동'),(31,92,690,'선부2동'),(31,92,700,'선부3동'),(31,92,720,'대부동'),(31,92,730,'호수동'),(31,92,740,'고잔동'),(31,92,750,'중앙동'),(31,92,760,'원곡동'),(31,92,770,'신길동'),(31,92,780,'백운동'),(31,101,510,'주교동'),(31,101,511,'흥도동'),(31,101,512,'삼송1동'),(31,101,513,'삼송2동'),(31,101,514,'행신3동'),(31,101,515,'행신4동'),(31,101,520,'원신동'),(31,101,540,'성사1동'),(31,101,550,'성사2동'),(31,101,560,'효자동'),(31,101,580,'창릉동'),(31,101,590,'고양동'),(31,101,600,'관산동'),(31,101,610,'능곡동'),(31,101,620,'화정1동'),(31,101,630,'화정2동'),(31,101,640,'행주동'),(31,101,650,'행신1동'),(31,101,660,'행신2동'),(31,101,670,'화전동'),(31,101,680,'대덕동'),(31,103,510,'식사동'),(31,103,511,'중산1동'),(31,103,512,'중산2동'),(31,103,530,'정발산동'),(31,103,540,'풍산동'),(31,103,550,'백석1동'),(31,103,560,'마두1동'),(31,103,570,'마두2동'),(31,103,580,'장항1동'),(31,103,590,'장항2동'),(31,103,600,'고봉동'),(31,103,610,'백석2동'),(31,104,510,'일산1동'),(31,104,511,'탄현1동'),(31,104,512,'탄현2동'),(31,104,513,'덕이동'),(31,104,514,'가좌동'),(31,104,520,'일산2동'),(31,104,530,'일산3동'),(31,104,550,'주엽1동'),(31,104,560,'주엽2동'),(31,104,570,'대화동'),(31,104,580,'송포동'),(31,110,510,'중앙동'),(31,110,520,'갈현동'),(31,110,530,'별양동'),(31,110,540,'부림동'),(31,110,550,'과천동'),(31,110,560,'문원동'),(31,120,510,'갈매동'),(31,120,520,'동구동'),(31,120,530,'인창동'),(31,120,540,'교문1동'),(31,120,550,'교문2동'),(31,120,560,'수택1동'),(31,120,570,'수택2동'),(31,120,580,'수택3동'),(31,130,110,'와부읍'),(31,130,111,'화도읍'),(31,130,120,'진접읍'),(31,130,140,'진건읍'),(31,130,150,'오남읍'),(31,130,160,'퇴계원읍'),(31,130,310,'별내면'),(31,130,340,'수동면'),(31,130,350,'조안면'),(31,130,510,'호평동'),(31,130,520,'평내동'),(31,130,530,'금곡동'),(31,130,540,'양정동'),(31,130,570,'별내동'),(31,130,580,'다산1동'),(31,130,590,'다산2동'),(31,140,510,'중앙동'),(31,140,520,'대원동'),(31,140,530,'남촌동'),(31,140,540,'신장동'),(31,140,550,'세마동'),(31,140,560,'초평동'),(31,150,510,'대야동'),(31,150,520,'신천동'),(31,150,530,'신현동'),(31,150,540,'은행동'),(31,150,550,'매화동'),(31,150,560,'목감동'),(31,150,590,'과림동'),(31,150,610,'정왕2동'),(31,150,620,'정왕3동'),(31,150,640,'정왕본동'),(31,150,650,'정왕1동'),(31,150,670,'능곡동'),(31,150,680,'군자동'),(31,150,690,'월곶동'),(31,150,700,'연성동'),(31,150,710,'장곡동'),(31,150,720,'정왕4동'),(31,150,740,'배곧1동'),(31,150,750,'배곧2동'),(31,160,510,'군포1동'),(31,160,520,'군포2동'),(31,160,540,'산본1동'),(31,160,550,'산본2동'),(31,160,560,'금정동'),(31,160,570,'재궁동'),(31,160,580,'오금동'),(31,160,590,'수리동'),(31,160,600,'궁내동'),(31,160,610,'광정동'),(31,160,630,'대야동'),(31,160,640,'송부동'),(31,170,510,'고천동'),(31,170,520,'부곡동'),(31,170,530,'오전동'),(31,170,540,'내손1동'),(31,170,550,'내손2동'),(31,170,560,'청계동'),(31,180,510,'천현동'),(31,180,520,'신장1동'),(31,180,530,'신장2동'),(31,180,540,'덕풍1동'),(31,180,550,'덕풍2동'),(31,180,560,'덕풍3동'),(31,180,590,'춘궁동'),(31,180,600,'초이동'),(31,180,610,'풍산동'),(31,180,620,'미사1동'),(31,180,630,'미사2동'),(31,180,650,'위례동'),(31,180,660,'감북동'),(31,180,670,'감일동'),(31,191,110,'포곡읍'),(31,191,120,'모현읍'),(31,191,130,'이동읍'),(31,191,140,'남사읍'),(31,191,340,'원삼면'),(31,191,350,'백암면'),(31,191,360,'양지면'),(31,191,510,'중앙동'),(31,191,530,'유림동'),(31,191,540,'동부동'),(31,191,550,'역북동'),(31,191,560,'삼가동'),(31,192,520,'구갈동'),(31,192,540,'기흥동'),(31,192,550,'서농동'),(31,192,560,'구성동'),(31,192,570,'마북동'),(31,192,590,'보정동'),(31,192,600,'상하동'),(31,192,610,'신갈동'),(31,192,630,'영덕1동'),(31,192,640,'영덕2동'),(31,192,650,'상갈동'),(31,192,660,'보라동'),(31,192,670,'동백1동'),(31,192,680,'동백2동'),(31,192,690,'동백3동'),(31,193,510,'풍덕천1동'),(31,193,520,'풍덕천2동'),(31,193,530,'신봉동'),(31,193,550,'죽전2동'),(31,193,560,'동천동'),(31,193,580,'상현2동'),(31,193,590,'성복동'),(31,193,600,'죽전1동'),(31,193,610,'죽전3동'),(31,193,620,'상현1동'),(31,193,630,'상현3동'),(31,200,110,'문산읍'),(31,200,120,'파주읍'),(31,200,130,'법원읍'),(31,200,150,'조리읍'),(31,200,310,'월롱면'),(31,200,320,'탄현면'),(31,200,350,'광탄면'),(31,200,360,'파평면'),(31,200,370,'적성면'),(31,200,390,'장단면'),(31,200,520,'금촌2동'),(31,200,530,'금촌1동'),(31,200,540,'금촌3동'),(31,200,550,'교하동'),(31,200,560,'운정1동'),(31,200,570,'운정2동'),(31,200,580,'운정3동'),(31,210,110,'장호원읍'),(31,210,120,'부발읍'),(31,210,310,'신둔면'),(31,210,320,'백사면'),(31,210,330,'호법면'),(31,210,340,'마장면'),(31,210,350,'대월면'),(31,210,360,'모가면'),(31,210,370,'설성면'),(31,210,380,'율면'),(31,210,510,'창전동'),(31,210,520,'중리동'),(31,210,530,'관고동'),(31,210,540,'증포동'),(31,220,110,'공도읍'),(31,220,310,'보개면'),(31,220,320,'금광면'),(31,220,330,'서운면'),(31,220,340,'미양면'),(31,220,350,'대덕면'),(31,220,360,'양성면'),(31,220,380,'원곡면'),(31,220,390,'일죽면'),(31,220,400,'죽산면'),(31,220,410,'삼죽면'),(31,220,420,'고삼면'),(31,220,510,'안성1동'),(31,220,520,'안성2동'),(31,220,530,'안성3동'),(31,230,110,'통진읍'),(31,230,120,'고촌읍'),(31,230,130,'양촌읍'),(31,230,340,'대곶면'),(31,230,350,'월곶면'),(31,230,360,'하성면'),(31,230,530,'사우동'),(31,230,540,'풍무동'),(31,230,560,'장기동'),(31,230,600,'운양동'),(31,230,610,'김포본동'),(31,230,620,'장기본동'),(31,230,630,'구래동'),(31,230,640,'마산동'),(31,240,120,'봉담읍'),(31,240,130,'우정읍'),(31,240,140,'향남읍'),(31,240,150,'남양읍'),(31,240,310,'매송면'),(31,240,330,'비봉면'),(31,240,350,'마도면'),(31,240,360,'송산면'),(31,240,370,'서신면'),(31,240,380,'팔탄면'),(31,240,390,'장안면'),(31,240,420,'양감면'),(31,240,430,'정남면'),(31,240,520,'진안동'),(31,240,530,'병점1동'),(31,240,540,'병점2동'),(31,240,550,'반월동'),(31,240,560,'기배동'),(31,240,570,'화산동'),(31,240,600,'동탄2동'),(31,240,610,'동탄1동'),(31,240,620,'동탄3동'),(31,240,640,'동탄4동'),(31,240,650,'동탄5동'),(31,240,670,'새솔동'),(31,240,690,'동탄7동'),(31,240,700,'동탄6동'),(31,240,710,'동탄8동'),(31,250,110,'오포읍'),(31,250,120,'초월읍'),(31,250,140,'곤지암읍'),(31,250,340,'도척면'),(31,250,350,'퇴촌면'),(31,250,360,'남종면'),(31,250,380,'남한산성면'),(31,250,540,'경안동'),(31,250,550,'송정동'),(31,250,560,'쌍령동'),(31,250,570,'탄벌동'),(31,250,580,'광남1동'),(31,250,590,'광남2동'),(31,260,110,'백석읍'),(31,260,310,'은현면'),(31,260,320,'남면'),(31,260,330,'광적면'),(31,260,340,'장흥면'),(31,260,510,'양주1동'),(31,260,520,'양주2동'),(31,260,530,'회천1동'),(31,260,540,'회천2동'),(31,260,550,'회천3동'),(31,260,560,'회천4동'),(31,270,110,'소흘읍'),(31,270,310,'군내면'),(31,270,320,'내촌면'),(31,270,330,'가산면'),(31,270,340,'신북면'),(31,270,350,'창수면'),(31,270,360,'영중면'),(31,270,370,'일동면'),(31,270,380,'이동면'),(31,270,390,'영북면'),(31,270,400,'관인면'),(31,270,410,'화현면'),(31,270,510,'포천동'),(31,270,520,'선단동'),(31,280,110,'가남읍'),(31,280,310,'점동면'),(31,280,330,'흥천면'),(31,280,340,'금사면'),(31,280,350,'산북면'),(31,280,360,'대신면'),(31,280,370,'북내면'),(31,280,380,'강천면'),(31,280,390,'세종대왕면'),(31,280,510,'여흥동'),(31,280,520,'중앙동'),(31,280,530,'오학동'),(31,550,110,'연천읍'),(31,550,120,'전곡읍'),(31,550,310,'군남면'),(31,550,320,'청산면'),(31,550,330,'백학면'),(31,550,340,'미산면'),(31,550,350,'왕징면'),(31,550,360,'신서면'),(31,550,370,'중면'),(31,550,380,'장남면'),(31,570,110,'가평읍'),(31,570,310,'설악면'),(31,570,320,'청평면'),(31,570,330,'상면'),(31,570,350,'북면'),(31,570,360,'조종면'),(31,580,110,'양평읍'),(31,580,310,'강상면'),(31,580,320,'강하면'),(31,580,330,'양서면'),(31,580,340,'옥천면'),(31,580,350,'서종면'),(31,580,360,'단월면'),(31,580,370,'청운면'),(31,580,380,'양동면'),(31,580,390,'지평면'),(31,580,400,'용문면'),(31,580,410,'개군면'),(32,10,110,'신북읍'),(32,10,310,'동면'),(32,10,320,'동산면'),(32,10,330,'신동면'),(32,10,340,'동내면'),(32,10,350,'남면'),(32,10,360,'남산면'),(32,10,370,'서면'),(32,10,380,'사북면'),(32,10,390,'북산면'),(32,10,520,'교동'),(32,10,530,'조운동'),(32,10,540,'약사명동'),(32,10,570,'근화동'),(32,10,580,'소양동'),(32,10,600,'후평1동'),(32,10,610,'후평2동'),(32,10,620,'후평3동'),(32,10,630,'효자1동'),(32,10,640,'효자2동'),(32,10,650,'효자3동'),(32,10,660,'석사동'),(32,10,670,'퇴계동'),(32,10,680,'강남동'),(32,10,710,'신사우동'),(32,20,110,'문막읍'),(32,20,310,'소초면'),(32,20,320,'호저면'),(32,20,330,'지정면'),(32,20,340,'부론면'),(32,20,350,'귀래면'),(32,20,360,'흥업면'),(32,20,370,'판부면'),(32,20,380,'신림면'),(32,20,510,'중앙동'),(32,20,520,'원인동'),(32,20,530,'개운동'),(32,20,540,'명륜1동'),(32,20,550,'명륜2동'),(32,20,560,'단구동'),(32,20,570,'일산동'),(32,20,580,'학성동'),(32,20,600,'단계동'),(32,20,610,'우산동'),(32,20,620,'태장1동'),(32,20,630,'태장2동'),(32,20,640,'봉산동'),(32,20,660,'행구동'),(32,20,670,'무실동'),(32,20,680,'반곡관설동'),(32,30,110,'주문진읍'),(32,30,310,'성산면'),(32,30,320,'왕산면'),(32,30,330,'구정면'),(32,30,340,'강동면'),(32,30,350,'옥계면'),(32,30,360,'사천면'),(32,30,370,'연곡면'),(32,30,510,'홍제동'),(32,30,520,'중앙동'),(32,30,540,'옥천동'),(32,30,550,'교1동'),(32,30,560,'교2동'),(32,30,570,'포남1동'),(32,30,580,'포남2동'),(32,30,590,'초당동'),(32,30,600,'송정동'),(32,30,610,'내곡동'),(32,30,620,'강남동'),(32,30,650,'성덕동'),(32,30,670,'경포동'),(32,40,510,'천곡동'),(32,40,520,'송정동'),(32,40,530,'북삼동'),(32,40,540,'부곡동'),(32,40,550,'동호동'),(32,40,570,'발한동'),(32,40,590,'묵호동'),(32,40,600,'북평동'),(32,40,630,'망상동'),(32,40,650,'삼화동'),(32,50,510,'황지동'),(32,50,520,'황연동'),(32,50,530,'삼수동'),(32,50,540,'상장동'),(32,50,550,'구문소동'),(32,50,560,'장성동'),(32,50,580,'철암동'),(32,50,620,'문곡소도동'),(32,60,510,'영랑동'),(32,60,520,'동명동'),(32,60,540,'금호동'),(32,60,560,'교동'),(32,60,570,'노학동'),(32,60,580,'조양동'),(32,60,590,'청호동'),(32,60,600,'대포동'),(32,70,110,'도계읍'),(32,70,120,'원덕읍'),(32,70,310,'근덕면'),(32,70,320,'하장면'),(32,70,330,'노곡면'),(32,70,340,'미로면'),(32,70,350,'가곡면'),(32,70,360,'신기면'),(32,70,510,'남양동'),(32,70,520,'성내동'),(32,70,530,'교동'),(32,70,540,'정라동'),(32,510,110,'홍천읍'),(32,510,310,'화촌면'),(32,510,320,'두촌면'),(32,510,330,'내촌면'),(32,510,340,'서석면'),(32,510,360,'남면'),(32,510,370,'서면'),(32,510,380,'북방면'),(32,510,390,'내면'),(32,510,400,'영귀미면'),(32,520,110,'횡성읍'),(32,520,310,'우천면'),(32,520,320,'안흥면'),(32,520,330,'둔내면'),(32,520,340,'갑천면'),(32,520,350,'청일면'),(32,520,360,'공근면'),(32,520,370,'서원면'),(32,520,380,'강림면'),(32,530,110,'영월읍'),(32,530,120,'상동읍'),(32,530,330,'북면'),(32,530,340,'남면'),(32,530,360,'주천면'),(32,530,380,'김삿갓면'),(32,530,390,'한반도면'),(32,530,400,'무릉도원면'),(32,530,410,'산솔면'),(32,540,110,'평창읍'),(32,540,310,'미탄면'),(32,540,320,'방림면'),(32,540,330,'대화면'),(32,540,340,'봉평면'),(32,540,350,'용평면'),(32,540,360,'진부면'),(32,540,370,'대관령면'),(32,550,110,'정선읍'),(32,550,120,'고한읍'),(32,550,130,'사북읍'),(32,550,140,'신동읍'),(32,550,320,'남면'),(32,550,340,'북평면'),(32,550,350,'임계면'),(32,550,360,'화암면'),(32,550,370,'여량면'),(32,560,110,'철원읍'),(32,560,120,'김화읍'),(32,560,130,'갈말읍'),(32,560,140,'동송읍'),(32,560,210,'근북면'),(32,560,260,'근동면'),(32,560,270,'원동면'),(32,560,280,'원남면'),(32,560,290,'임남면'),(32,560,310,'서면'),(32,560,320,'근남면'),(32,570,110,'화천읍'),(32,570,310,'간동면'),(32,570,320,'하남면'),(32,570,330,'상서면'),(32,570,340,'사내면'),(32,580,110,'양구읍'),(32,580,320,'동면'),(32,580,330,'방산면'),(32,580,340,'해안면'),(32,580,350,'국토정중앙면'),(32,590,110,'인제읍'),(32,590,310,'남면'),(32,590,320,'북면'),(32,590,330,'기린면'),(32,590,340,'서화면'),(32,590,350,'상남면'),(32,600,110,'간성읍'),(32,600,120,'거진읍'),(32,600,260,'수동면'),(32,600,310,'현내면'),(32,600,320,'죽왕면'),(32,600,330,'토성면'),(32,610,110,'양양읍'),(32,610,310,'서면'),(32,610,320,'손양면'),(32,610,330,'현북면'),(32,610,340,'현남면'),(32,610,350,'강현면'),(33,20,110,'주덕읍'),(33,20,310,'살미면'),(33,20,320,'수안보면'),(33,20,340,'신니면'),(33,20,350,'노은면'),(33,20,360,'앙성면'),(33,20,380,'금가면'),(33,20,390,'동량면'),(33,20,400,'산척면'),(33,20,410,'엄정면'),(33,20,420,'소태면'),(33,20,430,'대소원면'),(33,20,440,'중앙탑면'),(33,20,510,'성내·충인동'),(33,20,530,'교현·안림동'),(33,20,540,'교현2동'),(33,20,550,'용산동'),(33,20,560,'지현동'),(33,20,570,'문화동'),(33,20,580,'호암·직동'),(33,20,600,'달천동'),(33,20,610,'봉방동'),(33,20,620,'칠금·금릉동'),(33,20,630,'연수동'),(33,20,640,'목행·용탄동'),(33,30,110,'봉양읍'),(33,30,310,'금성면'),(33,30,320,'청풍면'),(33,30,330,'수산면'),(33,30,340,'덕산면'),(33,30,350,'한수면'),(33,30,360,'백운면'),(33,30,370,'송학면'),(33,30,510,'교동'),(33,30,560,'용두동'),(33,30,590,'청전동'),(33,30,600,'화산동'),(33,30,700,'남현동'),(33,30,710,'영서동'),(33,30,720,'신백동'),(33,30,730,'의림지동'),(33,30,740,'중앙동'),(33,41,310,'낭성면'),(33,41,320,'미원면'),(33,41,330,'가덕면'),(33,41,340,'남일면'),(33,41,350,'문의면'),(33,41,510,'중앙동'),(33,41,520,'성안동'),(33,41,530,'탑·대성동'),(33,41,540,'영운동'),(33,41,550,'금천동'),(33,41,560,'용담·명암·산성동'),(33,41,570,'용암1동'),(33,41,580,'용암2동'),(33,42,310,'남이면'),(33,42,320,'현도면'),(33,42,510,'사직1동'),(33,42,520,'사직2동'),(33,42,530,'사창동'),(33,42,540,'모충동'),(33,42,550,'산남동'),(33,42,560,'분평동'),(33,42,570,'수곡1동'),(33,42,580,'수곡2동'),(33,42,590,'성화·개신·죽림동'),(33,43,110,'오송읍'),(33,43,310,'강내면'),(33,43,320,'옥산면'),(33,43,510,'운천·신봉동'),(33,43,520,'복대1동'),(33,43,530,'복대2동'),(33,43,540,'가경동'),(33,43,550,'봉명1동'),(33,43,560,'봉명2·송정동'),(33,43,570,'강서1동'),(33,43,580,'강서2동'),(33,44,110,'내수읍'),(33,44,120,'오창읍'),(33,44,310,'북이면'),(33,44,510,'오근장동'),(33,44,520,'우암동'),(33,44,530,'내덕1동'),(33,44,540,'내덕2동'),(33,44,550,'율량·사천동'),(33,520,110,'보은읍'),(33,520,310,'속리산면'),(33,520,320,'장안면'),(33,520,330,'마로면'),(33,520,340,'탄부면'),(33,520,350,'삼승면'),(33,520,360,'수한면'),(33,520,370,'회남면'),(33,520,380,'회인면'),(33,520,390,'내북면'),(33,520,400,'산외면'),(33,530,110,'옥천읍'),(33,530,310,'동이면'),(33,530,320,'안남면'),(33,530,330,'안내면'),(33,530,340,'청성면'),(33,530,350,'청산면'),(33,530,360,'이원면'),(33,530,370,'군서면'),(33,530,380,'군북면'),(33,540,110,'영동읍'),(33,540,310,'용산면'),(33,540,320,'황간면'),(33,540,330,'추풍령면'),(33,540,340,'매곡면'),(33,540,350,'상촌면'),(33,540,360,'양강면'),(33,540,370,'용화면'),(33,540,380,'학산면'),(33,540,390,'양산면'),(33,540,400,'심천면'),(33,550,110,'진천읍'),(33,550,120,'덕산읍'),(33,550,320,'초평면'),(33,550,330,'문백면'),(33,550,340,'백곡면'),(33,550,350,'이월면'),(33,550,360,'광혜원면'),(33,560,110,'괴산읍'),(33,560,310,'감물면'),(33,560,320,'장연면'),(33,560,330,'연풍면'),(33,560,340,'칠성면'),(33,560,350,'문광면'),(33,560,360,'청천면'),(33,560,370,'청안면'),(33,560,380,'사리면'),(33,560,390,'소수면'),(33,560,400,'불정면'),(33,570,110,'음성읍'),(33,570,120,'금왕읍'),(33,570,310,'소이면'),(33,570,320,'원남면'),(33,570,330,'맹동면'),(33,570,340,'대소면'),(33,570,350,'삼성면'),(33,570,360,'생극면'),(33,570,370,'감곡면'),(33,580,110,'단양읍'),(33,580,120,'매포읍'),(33,580,310,'단성면'),(33,580,320,'대강면'),(33,580,330,'가곡면'),(33,580,340,'영춘면'),(33,580,350,'어상천면'),(33,580,360,'적성면'),(33,590,140,'증평읍'),(33,590,310,'도안면'),(34,11,110,'목천읍'),(34,11,310,'풍세면'),(34,11,320,'광덕면'),(34,11,330,'북면'),(34,11,340,'성남면'),(34,11,350,'수신면'),(34,11,360,'병천면'),(34,11,370,'동면'),(34,11,510,'중앙동'),(34,11,520,'문성동'),(34,11,530,'원성1동'),(34,11,540,'원성2동'),(34,11,550,'봉명동'),(34,11,560,'일봉동'),(34,11,570,'신방동'),(34,11,580,'청룡동'),(34,11,590,'신안동'),(34,12,110,'성환읍'),(34,12,120,'성거읍'),(34,12,130,'직산읍'),(34,12,310,'입장면'),(34,12,510,'성정1동'),(34,12,520,'성정2동'),(34,12,530,'쌍용1동'),(34,12,540,'쌍용2동'),(34,12,550,'쌍용3동'),(34,12,580,'백석동'),(34,12,600,'부성1동'),(34,12,610,'부성2동'),(34,12,620,'불당1동'),(34,12,630,'불당2동'),(34,20,110,'유구읍'),(34,20,310,'이인면'),(34,20,320,'탄천면'),(34,20,330,'계룡면'),(34,20,340,'반포면'),(34,20,360,'의당면'),(34,20,370,'정안면'),(34,20,380,'우성면'),(34,20,390,'사곡면'),(34,20,400,'신풍면'),(34,20,510,'중학동'),(34,20,550,'금학동'),(34,20,560,'옥룡동'),(34,20,570,'신관동'),(34,20,580,'웅진동'),(34,20,590,'월송동'),(34,30,110,'웅천읍'),(34,30,310,'주포면'),(34,30,320,'주교면'),(34,30,330,'오천면'),(34,30,340,'천북면'),(34,30,350,'청소면'),(34,30,360,'청라면'),(34,30,370,'남포면'),(34,30,380,'주산면'),(34,30,390,'미산면'),(34,30,400,'성주면'),(34,30,510,'대천1동'),(34,30,520,'대천2동'),(34,30,530,'대천3동'),(34,30,540,'대천4동'),(34,30,560,'대천5동'),(34,40,110,'염치읍'),(34,40,120,'배방읍'),(34,40,310,'송악면'),(34,40,330,'탕정면'),(34,40,340,'음봉면'),(34,40,350,'둔포면'),(34,40,360,'영인면'),(34,40,370,'인주면'),(34,40,380,'선장면'),(34,40,390,'도고면'),(34,40,400,'신창면'),(34,40,510,'온양1동'),(34,40,520,'온양2동'),(34,40,530,'온양3동'),(34,40,540,'온양4동'),(34,40,550,'온양5동'),(34,40,560,'온양6동'),(34,50,110,'대산읍'),(34,50,310,'인지면'),(34,50,320,'부석면'),(34,50,330,'팔봉면'),(34,50,340,'지곡면'),(34,50,350,'성연면'),(34,50,360,'음암면'),(34,50,370,'운산면'),(34,50,380,'해미면'),(34,50,390,'고북면'),(34,50,510,'부춘동'),(34,50,520,'동문1동'),(34,50,530,'동문2동'),(34,50,540,'수석동'),(34,50,550,'석남동'),(34,60,110,'강경읍'),(34,60,120,'연무읍'),(34,60,310,'성동면'),(34,60,320,'광석면'),(34,60,330,'노성면'),(34,60,340,'상월면'),(34,60,350,'부적면'),(34,60,360,'연산면'),(34,60,370,'벌곡면'),(34,60,380,'양촌면'),(34,60,390,'가야곡면'),(34,60,400,'은진면'),(34,60,410,'채운면'),(34,60,510,'취암동'),(34,60,520,'부창동'),(34,70,310,'두마면'),(34,70,330,'엄사면'),(34,70,340,'신도안면'),(34,70,510,'금암동'),(34,80,110,'합덕읍'),(34,80,120,'송악읍'),(34,80,310,'고대면'),(34,80,320,'석문면'),(34,80,330,'대호지면'),(34,80,340,'정미면'),(34,80,350,'면천면'),(34,80,360,'순성면'),(34,80,370,'우강면'),(34,80,380,'신평면'),(34,80,390,'송산면'),(34,80,510,'당진1동'),(34,80,520,'당진2동'),(34,80,530,'당진3동'),(34,510,110,'금산읍'),(34,510,310,'금성면'),(34,510,320,'제원면'),(34,510,330,'부리면'),(34,510,340,'군북면'),(34,510,350,'남일면'),(34,510,360,'남이면'),(34,510,370,'진산면'),(34,510,380,'복수면'),(34,510,390,'추부면'),(34,530,110,'부여읍'),(34,530,310,'규암면'),(34,530,320,'은산면'),(34,530,330,'외산면'),(34,530,340,'내산면'),(34,530,350,'구룡면'),(34,530,360,'홍산면'),(34,530,370,'옥산면'),(34,530,380,'남면'),(34,530,390,'충화면'),(34,530,400,'양화면'),(34,530,410,'임천면'),(34,530,420,'장암면'),(34,530,430,'세도면'),(34,530,440,'석성면'),(34,530,450,'초촌면'),(34,540,110,'장항읍'),(34,540,120,'서천읍'),(34,540,310,'마서면'),(34,540,320,'화양면'),(34,540,330,'기산면'),(34,540,340,'한산면'),(34,540,350,'마산면'),(34,540,360,'시초면'),(34,540,370,'문산면'),(34,540,380,'판교면'),(34,540,390,'종천면'),(34,540,400,'비인면'),(34,540,410,'서면'),(34,550,110,'청양읍'),(34,550,310,'운곡면'),(34,550,320,'대치면'),(34,550,330,'정산면'),(34,550,340,'목면'),(34,550,350,'청남면'),(34,550,360,'장평면'),(34,550,370,'남양면'),(34,550,380,'화성면'),(34,550,390,'비봉면'),(34,560,110,'홍성읍'),(34,560,120,'광천읍'),(34,560,130,'홍북읍'),(34,560,320,'금마면'),(34,560,330,'홍동면'),(34,560,340,'장곡면'),(34,560,350,'은하면'),(34,560,360,'결성면'),(34,560,370,'서부면'),(34,560,380,'갈산면'),(34,560,390,'구항면'),(34,570,110,'예산읍'),(34,570,120,'삽교읍'),(34,570,310,'대술면'),(34,570,320,'신양면'),(34,570,330,'광시면'),(34,570,340,'대흥면'),(34,570,350,'응봉면'),(34,570,360,'덕산면'),(34,570,370,'봉산면'),(34,570,380,'고덕면'),(34,570,390,'신암면'),(34,570,400,'오가면'),(34,580,110,'태안읍'),(34,580,120,'안면읍'),(34,580,310,'고남면'),(34,580,320,'남면'),(34,580,330,'근흥면'),(34,580,340,'소원면'),(34,580,350,'원북면'),(34,580,360,'이원면'),(35,11,600,'동서학동'),(35,11,610,'서서학동'),(35,11,620,'중화산1동'),(35,11,630,'중화산2동'),(35,11,640,'평화1동'),(35,11,650,'평화2동'),(35,11,660,'서신동'),(35,11,670,'삼천1동'),(35,11,680,'삼천2동'),(35,11,690,'삼천3동'),(35,11,700,'효자1동'),(35,11,710,'효자2동'),(35,11,720,'효자3동'),(35,11,740,'중앙동'),(35,11,750,'풍남동'),(35,11,760,'노송동'),(35,11,770,'완산동'),(35,11,780,'효자4동'),(35,11,790,'효자5동'),(35,12,540,'인후1동'),(35,12,550,'인후2동'),(35,12,560,'인후3동'),(35,12,570,'덕진동'),(35,12,580,'금암1동'),(35,12,590,'금암2동'),(35,12,600,'팔복동'),(35,12,610,'우아1동'),(35,12,620,'우아2동'),(35,12,630,'호성동'),(35,12,650,'송천1동'),(35,12,660,'송천2동'),(35,12,670,'조촌동'),(35,12,690,'진북동'),(35,12,700,'혁신동'),(35,12,710,'여의동'),(35,20,110,'옥구읍'),(35,20,310,'옥산면'),(35,20,320,'회현면'),(35,20,330,'임피면'),(35,20,340,'서수면'),(35,20,350,'대야면'),(35,20,360,'개정면'),(35,20,370,'성산면'),(35,20,380,'나포면'),(35,20,390,'옥도면'),(35,20,400,'옥서면'),(35,20,510,'해신동'),(35,20,530,'월명동'),(35,20,550,'신풍동'),(35,20,560,'삼학동'),(35,20,600,'중앙동'),(35,20,640,'흥남동'),(35,20,650,'조촌동'),(35,20,660,'경암동'),(35,20,670,'구암동'),(35,20,680,'개정동'),(35,20,690,'수송동'),(35,20,700,'나운1동'),(35,20,710,'나운2동'),(35,20,720,'소룡동'),(35,20,730,'미성동'),(35,20,740,'나운3동'),(35,30,110,'함열읍'),(35,30,310,'오산면'),(35,30,320,'황등면'),(35,30,330,'함라면'),(35,30,340,'웅포면'),(35,30,350,'성당면'),(35,30,360,'용안면'),(35,30,370,'낭산면'),(35,30,380,'망성면'),(35,30,390,'여산면'),(35,30,400,'금마면'),(35,30,410,'왕궁면'),(35,30,420,'춘포면'),(35,30,430,'삼기면'),(35,30,440,'용동면'),(35,30,510,'중앙동'),(35,30,530,'평화동'),(35,30,550,'인화동'),(35,30,570,'동산동'),(35,30,580,'마동'),(35,30,590,'남중동'),(35,30,610,'모현동'),(35,30,620,'송학동'),(35,30,650,'신동'),(35,30,660,'영등1동'),(35,30,690,'팔봉동'),(35,30,700,'삼성동'),(35,30,710,'영등2동'),(35,30,720,'어양동'),(35,40,110,'신태인읍'),(35,40,310,'북면'),(35,40,320,'입암면'),(35,40,330,'소성면'),(35,40,340,'고부면'),(35,40,350,'영원면'),(35,40,360,'덕천면'),(35,40,370,'이평면'),(35,40,380,'정우면'),(35,40,390,'태인면'),(35,40,400,'감곡면'),(35,40,410,'옹동면'),(35,40,420,'칠보면'),(35,40,430,'산내면'),(35,40,440,'산외면'),(35,40,510,'수성동'),(35,40,520,'장명동'),(35,40,530,'내장상동'),(35,40,540,'시기동'),(35,40,570,'연지동'),(35,40,580,'농소동'),(35,40,590,'상교동'),(35,40,600,'초산동'),(35,50,110,'운봉읍'),(35,50,310,'주천면'),(35,50,320,'수지면'),(35,50,330,'송동면'),(35,50,340,'주생면'),(35,50,350,'금지면'),(35,50,360,'대강면'),(35,50,370,'대산면'),(35,50,380,'사매면'),(35,50,390,'덕과면'),(35,50,400,'보절면'),(35,50,410,'산동면'),(35,50,420,'이백면'),(35,50,430,'인월면'),(35,50,440,'아영면'),(35,50,450,'산내면'),(35,50,510,'동충동'),(35,50,520,'죽항동'),(35,50,540,'노암동'),(35,50,550,'금동'),(35,50,560,'왕정동'),(35,50,570,'향교동'),(35,50,590,'도통동'),(35,60,110,'만경읍'),(35,60,310,'죽산면'),(35,60,320,'백산면'),(35,60,330,'용지면'),(35,60,340,'백구면'),(35,60,350,'부량면'),(35,60,360,'공덕면'),(35,60,370,'청하면'),(35,60,380,'성덕면'),(35,60,390,'진봉면'),(35,60,400,'금구면'),(35,60,410,'봉남면'),(35,60,420,'황산면'),(35,60,430,'금산면'),(35,60,440,'광활면'),(35,60,510,'요촌동'),(35,60,520,'신풍동'),(35,60,540,'검산동'),(35,60,580,'교월동'),(35,510,110,'삼례읍'),(35,510,120,'봉동읍'),(35,510,130,'용진읍'),(35,510,320,'상관면'),(35,510,330,'이서면'),(35,510,340,'소양면'),(35,510,350,'구이면'),(35,510,360,'고산면'),(35,510,370,'비봉면'),(35,510,380,'운주면'),(35,510,390,'화산면'),(35,510,400,'동상면'),(35,510,410,'경천면'),(35,520,110,'진안읍'),(35,520,310,'용담면'),(35,520,320,'안천면'),(35,520,330,'동향면'),(35,520,340,'상전면'),(35,520,350,'백운면'),(35,520,360,'성수면'),(35,520,370,'마령면'),(35,520,380,'부귀면'),(35,520,390,'정천면'),(35,520,400,'주천면'),(35,530,110,'무주읍'),(35,530,310,'무풍면'),(35,530,320,'설천면'),(35,530,330,'적상면'),(35,530,340,'안성면'),(35,530,350,'부남면'),(35,540,110,'장수읍'),(35,540,310,'산서면'),(35,540,320,'번암면'),(35,540,330,'장계면'),(35,540,340,'천천면'),(35,540,350,'계남면'),(35,540,360,'계북면'),(35,550,110,'임실읍'),(35,550,310,'청웅면'),(35,550,320,'운암면'),(35,550,330,'신평면'),(35,550,340,'성수면'),(35,550,350,'오수면'),(35,550,360,'신덕면'),(35,550,370,'삼계면'),(35,550,380,'관촌면'),(35,550,390,'강진면'),(35,550,400,'덕치면'),(35,550,410,'지사면'),(35,560,110,'순창읍'),(35,560,310,'인계면'),(35,560,320,'동계면'),(35,560,330,'적성면'),(35,560,340,'유등면'),(35,560,350,'풍산면'),(35,560,360,'금과면'),(35,560,370,'팔덕면'),(35,560,380,'복흥면'),(35,560,390,'쌍치면'),(35,560,400,'구림면'),(35,570,110,'고창읍'),(35,570,310,'고수면'),(35,570,320,'아산면'),(35,570,330,'무장면'),(35,570,340,'공음면'),(35,570,350,'상하면'),(35,570,360,'해리면'),(35,570,370,'성송면'),(35,570,380,'대산면'),(35,570,390,'심원면'),(35,570,400,'흥덕면'),(35,570,410,'성내면'),(35,570,420,'신림면'),(35,570,430,'부안면'),(35,580,110,'부안읍'),(35,580,310,'주산면'),(35,580,320,'동진면'),(35,580,330,'행안면'),(35,580,340,'계화면'),(35,580,350,'보안면'),(35,580,360,'변산면'),(35,580,370,'진서면'),(35,580,380,'백산면'),(35,580,390,'상서면'),(35,580,400,'하서면'),(35,580,410,'줄포면'),(35,580,420,'위도면'),(36,10,510,'용당1동'),(36,10,520,'용당2동'),(36,10,530,'연동'),(36,10,550,'산정동'),(36,10,560,'연산동'),(36,10,570,'원산동'),(36,10,580,'대성동'),(36,10,600,'목원동'),(36,10,620,'동명동'),(36,10,630,'삼학동'),(36,10,640,'만호동'),(36,10,650,'유달동'),(36,10,670,'죽교동'),(36,10,680,'북항동'),(36,10,690,'용해동'),(36,10,700,'이로동'),(36,10,710,'상동'),(36,10,720,'하당동'),(36,10,730,'신흥동'),(36,10,740,'삼향동'),(36,10,750,'옥암동'),(36,10,760,'부흥동'),(36,10,770,'부주동'),(36,20,110,'돌산읍'),(36,20,310,'소라면'),(36,20,320,'율촌면'),(36,20,330,'화양면'),(36,20,340,'남면'),(36,20,350,'화정면'),(36,20,360,'삼산면'),(36,20,510,'동문동'),(36,20,520,'한려동'),(36,20,530,'중앙동'),(36,20,540,'충무동'),(36,20,550,'광림동'),(36,20,560,'서강동'),(36,20,570,'대교동'),(36,20,580,'국동'),(36,20,590,'월호동'),(36,20,600,'여서동'),(36,20,610,'문수동'),(36,20,620,'미평동'),(36,20,630,'둔덕동'),(36,20,640,'만덕동'),(36,20,650,'쌍봉동'),(36,20,660,'시전동'),(36,20,670,'여천동'),(36,20,680,'주삼동'),(36,20,690,'삼일동'),(36,20,700,'묘도동'),(36,30,110,'승주읍'),(36,30,310,'주암면'),(36,30,320,'송광면'),(36,30,330,'외서면'),(36,30,340,'낙안면'),(36,30,350,'별량면'),(36,30,360,'상사면'),(36,30,370,'해룡면'),(36,30,380,'서면'),(36,30,390,'황전면'),(36,30,400,'월등면'),(36,30,510,'향동'),(36,30,540,'매곡동'),(36,30,550,'삼산동'),(36,30,560,'조곡동'),(36,30,570,'덕연동'),(36,30,580,'풍덕동'),(36,30,590,'남제동'),(36,30,600,'저전동'),(36,30,610,'장천동'),(36,30,620,'중앙동'),(36,30,630,'도사동'),(36,30,660,'왕조1동'),(36,30,670,'왕조2동'),(36,40,110,'남평읍'),(36,40,310,'세지면'),(36,40,320,'왕곡면'),(36,40,330,'반남면'),(36,40,340,'공산면'),(36,40,350,'동강면'),(36,40,360,'다시면'),(36,40,370,'문평면'),(36,40,380,'노안면'),(36,40,390,'금천면'),(36,40,400,'산포면'),(36,40,410,'다도면'),(36,40,420,'봉황면'),(36,40,510,'송월동'),(36,40,520,'영강동'),(36,40,540,'금남동'),(36,40,550,'성북동'),(36,40,580,'영산동'),(36,40,600,'이창동'),(36,40,610,'빛가람동'),(36,60,110,'광양읍'),(36,60,310,'봉강면'),(36,60,320,'옥룡면'),(36,60,330,'옥곡면'),(36,60,340,'진상면'),(36,60,350,'진월면'),(36,60,360,'다압면'),(36,60,510,'골약동'),(36,60,530,'중마동'),(36,60,540,'광영동'),(36,60,550,'태인동'),(36,60,560,'금호동'),(36,510,110,'담양읍'),(36,510,310,'봉산면'),(36,510,320,'고서면'),(36,510,340,'창평면'),(36,510,350,'대덕면'),(36,510,360,'무정면'),(36,510,370,'금성면'),(36,510,380,'용면'),(36,510,390,'월산면'),(36,510,400,'수북면'),(36,510,410,'대전면'),(36,510,420,'가사문학면'),(36,520,110,'곡성읍'),(36,520,310,'오곡면'),(36,520,320,'삼기면'),(36,520,330,'석곡면'),(36,520,340,'목사동면'),(36,520,350,'죽곡면'),(36,520,360,'고달면'),(36,520,370,'옥과면'),(36,520,380,'입면'),(36,520,390,'겸면'),(36,520,400,'오산면'),(36,530,110,'구례읍'),(36,530,310,'문척면'),(36,530,320,'간전면'),(36,530,330,'토지면'),(36,530,340,'마산면'),(36,530,350,'광의면'),(36,530,360,'용방면'),(36,530,370,'산동면'),(36,550,110,'고흥읍'),(36,550,120,'도양읍'),(36,550,310,'풍양면'),(36,550,320,'도덕면'),(36,550,330,'금산면'),(36,550,340,'도화면'),(36,550,350,'포두면'),(36,550,360,'봉래면'),(36,550,370,'동일면'),(36,550,380,'점암면'),(36,550,390,'영남면'),(36,550,400,'과역면'),(36,550,410,'남양면'),(36,550,420,'동강면'),(36,550,430,'대서면'),(36,550,440,'두원면'),(36,560,110,'보성읍'),(36,560,120,'벌교읍'),(36,560,310,'노동면'),(36,560,320,'미력면'),(36,560,330,'겸백면'),(36,560,340,'율어면'),(36,560,350,'복내면'),(36,560,360,'문덕면'),(36,560,370,'조성면'),(36,560,380,'득량면'),(36,560,390,'회천면'),(36,560,400,'웅치면'),(36,570,110,'화순읍'),(36,570,310,'한천면'),(36,570,320,'춘양면'),(36,570,330,'청풍면'),(36,570,340,'이양면'),(36,570,350,'능주면'),(36,570,360,'도곡면'),(36,570,370,'도암면'),(36,570,380,'이서면'),(36,570,400,'동복면'),(36,570,420,'동면'),(36,570,430,'백아면'),(36,570,440,'사평면'),(36,580,110,'장흥읍'),(36,580,120,'관산읍'),(36,580,130,'대덕읍'),(36,580,310,'용산면'),(36,580,320,'안양면'),(36,580,330,'장동면'),(36,580,340,'장평면'),(36,580,350,'유치면'),(36,580,360,'부산면'),(36,580,370,'회진면'),(36,590,110,'강진읍'),(36,590,310,'군동면'),(36,590,320,'칠량면'),(36,590,330,'대구면'),(36,590,340,'마량면'),(36,590,350,'도암면'),(36,590,360,'신전면'),(36,590,370,'성전면'),(36,590,380,'작천면'),(36,590,390,'병영면'),(36,590,400,'옴천면'),(36,600,110,'해남읍'),(36,600,310,'삼산면'),(36,600,320,'화산면'),(36,600,330,'현산면'),(36,600,340,'송지면'),(36,600,350,'북평면'),(36,600,360,'북일면'),(36,600,370,'옥천면'),(36,600,380,'계곡면'),(36,600,390,'마산면'),(36,600,400,'황산면'),(36,600,410,'산이면'),(36,600,420,'문내면'),(36,600,430,'화원면'),(36,610,110,'영암읍'),(36,610,120,'삼호읍'),(36,610,310,'덕진면'),(36,610,320,'금정면'),(36,610,330,'신북면'),(36,610,340,'시종면'),(36,610,350,'도포면'),(36,610,360,'군서면'),(36,610,370,'서호면'),(36,610,380,'학산면'),(36,610,390,'미암면'),(36,620,110,'무안읍'),(36,620,120,'일로읍'),(36,620,130,'삼향읍'),(36,620,320,'몽탄면'),(36,620,330,'청계면'),(36,620,340,'현경면'),(36,620,350,'망운면'),(36,620,360,'해제면'),(36,620,370,'운남면'),(36,630,110,'함평읍'),(36,630,310,'손불면'),(36,630,320,'신광면'),(36,630,330,'학교면'),(36,630,340,'엄다면'),(36,630,350,'대동면'),(36,630,360,'나산면'),(36,630,370,'해보면'),(36,630,380,'월야면'),(36,640,110,'영광읍'),(36,640,120,'백수읍'),(36,640,130,'홍농읍'),(36,640,310,'대마면'),(36,640,320,'묘량면'),(36,640,330,'불갑면'),(36,640,340,'군서면'),(36,640,350,'군남면'),(36,640,360,'염산면'),(36,640,370,'법성면'),(36,640,380,'낙월면'),(36,650,110,'장성읍'),(36,650,310,'진원면'),(36,650,320,'남면'),(36,650,330,'동화면'),(36,650,340,'삼서면'),(36,650,350,'삼계면'),(36,650,360,'황룡면'),(36,650,370,'서삼면'),(36,650,380,'북일면'),(36,650,390,'북이면'),(36,650,400,'북하면'),(36,660,110,'완도읍'),(36,660,120,'금일읍'),(36,660,130,'노화읍'),(36,660,310,'군외면'),(36,660,320,'신지면'),(36,660,330,'고금면'),(36,660,340,'약산면'),(36,660,350,'청산면'),(36,660,360,'소안면'),(36,660,370,'금당면'),(36,660,380,'보길면'),(36,660,390,'생일면'),(36,670,110,'진도읍'),(36,670,310,'군내면'),(36,670,320,'고군면'),(36,670,330,'의신면'),(36,670,340,'임회면'),(36,670,350,'지산면'),(36,670,360,'조도면'),(36,680,110,'지도읍'),(36,680,120,'압해읍'),(36,680,310,'증도면'),(36,680,320,'임자면'),(36,680,330,'자은면'),(36,680,340,'비금면'),(36,680,350,'도초면'),(36,680,360,'흑산면'),(36,680,370,'하의면'),(36,680,380,'신의면'),(36,680,390,'장산면'),(36,680,400,'안좌면'),(36,680,410,'팔금면'),(36,680,420,'암태면'),(37,11,110,'구룡포읍'),(37,11,120,'연일읍'),(37,11,130,'오천읍'),(37,11,310,'대송면'),(37,11,320,'동해면'),(37,11,330,'장기면'),(37,11,350,'호미곶면'),(37,11,550,'송도동'),(37,11,560,'청림동'),(37,11,570,'제철동'),(37,11,580,'효곡동'),(37,11,590,'대이동'),(37,11,600,'상대동'),(37,11,610,'해도동'),(37,12,110,'흥해읍'),(37,12,310,'신광면'),(37,12,320,'청하면'),(37,12,330,'송라면'),(37,12,340,'기계면'),(37,12,350,'죽장면'),(37,12,360,'기북면'),(37,12,580,'양학동'),(37,12,610,'용흥동'),(37,12,630,'우창동'),(37,12,640,'두호동'),(37,12,650,'장량동'),(37,12,660,'환여동'),(37,12,670,'중앙동'),(37,12,680,'죽도동'),(37,20,110,'감포읍'),(37,20,120,'안강읍'),(37,20,130,'건천읍'),(37,20,140,'외동읍'),(37,20,320,'양남면'),(37,20,330,'내남면'),(37,20,340,'산내면'),(37,20,350,'서면'),(37,20,360,'현곡면'),(37,20,370,'강동면'),(37,20,380,'천북면'),(37,20,390,'문무대왕면'),(37,20,510,'중부동'),(37,20,550,'성건동'),(37,20,580,'월성동'),(37,20,590,'선도동'),(37,20,620,'용강동'),(37,20,630,'황성동'),(37,20,640,'동천동'),(37,20,660,'불국동'),(37,20,670,'보덕동'),(37,20,680,'황오동'),(37,20,690,'황남동'),(37,30,110,'아포읍'),(37,30,310,'농소면'),(37,30,320,'남면'),(37,30,330,'개령면'),(37,30,340,'감문면'),(37,30,350,'어모면'),(37,30,360,'봉산면'),(37,30,370,'대항면'),(37,30,380,'감천면'),(37,30,390,'조마면'),(37,30,400,'구성면'),(37,30,410,'지례면'),(37,30,420,'부항면'),(37,30,430,'대덕면'),(37,30,440,'증산면'),(37,30,550,'양금동'),(37,30,560,'대신동'),(37,30,580,'대곡동'),(37,30,590,'지좌동'),(37,30,600,'자산동'),(37,30,610,'평화남산동'),(37,30,620,'율곡동'),(37,40,110,'풍산읍'),(37,40,310,'와룡면'),(37,40,320,'북후면'),(37,40,330,'서후면'),(37,40,340,'풍천면'),(37,40,350,'일직면'),(37,40,360,'남후면'),(37,40,370,'남선면'),(37,40,380,'임하면'),(37,40,390,'길안면'),(37,40,400,'임동면'),(37,40,410,'예안면'),(37,40,420,'도산면'),(37,40,430,'녹전면'),(37,40,510,'중구동'),(37,40,520,'명륜동'),(37,40,540,'용상동'),(37,40,560,'태화동'),(37,40,580,'평화동'),(37,40,590,'안기동'),(37,40,600,'옥동'),(37,40,610,'송하동'),(37,40,620,'서구동'),(37,40,630,'강남동'),(37,50,110,'선산읍'),(37,50,120,'고아읍'),(37,50,130,'산동읍'),(37,50,310,'무을면'),(37,50,320,'옥성면'),(37,50,330,'도개면'),(37,50,340,'해평면'),(37,50,360,'장천면'),(37,50,510,'송정동'),(37,50,550,'도량동'),(37,50,560,'지산동'),(37,50,570,'선주원남동'),(37,50,590,'형곡1동'),(37,50,600,'형곡2동'),(37,50,610,'신평1동'),(37,50,620,'신평2동'),(37,50,660,'광평동'),(37,50,670,'상모사곡동'),(37,50,690,'임오동'),(37,50,700,'인동동'),(37,50,710,'진미동'),(37,50,720,'양포동'),(37,50,730,'비산동'),(37,50,740,'공단동'),(37,50,750,'원평동'),(37,60,110,'풍기읍'),(37,60,310,'이산면'),(37,60,320,'평은면'),(37,60,330,'문수면'),(37,60,340,'장수면'),(37,60,350,'안정면'),(37,60,360,'봉현면'),(37,60,370,'순흥면'),(37,60,380,'단산면'),(37,60,390,'부석면'),(37,60,510,'상망동'),(37,60,530,'하망동'),(37,60,550,'영주1동'),(37,60,580,'영주2동'),(37,60,590,'휴천1동'),(37,60,600,'휴천2동'),(37,60,610,'휴천3동'),(37,60,620,'가흥1동'),(37,60,630,'가흥2동'),(37,70,110,'금호읍'),(37,70,310,'청통면'),(37,70,320,'신녕면'),(37,70,330,'화산면'),(37,70,340,'화북면'),(37,70,350,'화남면'),(37,70,360,'자양면'),(37,70,370,'임고면'),(37,70,380,'고경면'),(37,70,390,'북안면'),(37,70,400,'대창면'),(37,70,510,'동부동'),(37,70,520,'중앙동'),(37,70,530,'서부동'),(37,70,540,'완산동'),(37,70,550,'남부동'),(37,80,110,'함창읍'),(37,80,320,'중동면'),(37,80,330,'낙동면'),(37,80,340,'청리면'),(37,80,350,'공성면'),(37,80,360,'외남면'),(37,80,370,'내서면'),(37,80,380,'모동면'),(37,80,390,'모서면'),(37,80,400,'화동면'),(37,80,410,'화서면'),(37,80,420,'화북면'),(37,80,430,'외서면'),(37,80,440,'은척면'),(37,80,450,'공검면'),(37,80,460,'이안면'),(37,80,470,'화남면'),(37,80,480,'사벌국면'),(37,80,520,'남원동'),(37,80,530,'북문동'),(37,80,540,'계림동'),(37,80,550,'동문동'),(37,80,560,'동성동'),(37,80,570,'신흥동'),(37,90,110,'문경읍'),(37,90,120,'가은읍'),(37,90,310,'영순면'),(37,90,320,'산양면'),(37,90,330,'호계면'),(37,90,340,'산북면'),(37,90,350,'동로면'),(37,90,360,'마성면'),(37,90,370,'농암면'),(37,90,570,'점촌1동'),(37,90,580,'점촌2동'),(37,90,590,'점촌3동'),(37,90,600,'점촌4동'),(37,90,610,'점촌5동'),(37,100,110,'하양읍'),(37,100,120,'진량읍'),(37,100,130,'압량읍'),(37,100,310,'와촌면'),(37,100,320,'자인면'),(37,100,330,'용성면'),(37,100,340,'남산면'),(37,100,360,'남천면'),(37,100,510,'중앙동'),(37,100,520,'동부동'),(37,100,530,'서부1동'),(37,100,540,'남부동'),(37,100,550,'북부동'),(37,100,560,'중방동'),(37,100,570,'서부2동'),(37,510,110,'군위읍'),(37,510,310,'소보면'),(37,510,320,'효령면'),(37,510,330,'부계면'),(37,510,340,'우보면'),(37,510,350,'의흥면'),(37,510,360,'산성면'),(37,510,380,'삼국유사면'),(37,520,110,'의성읍'),(37,520,310,'단촌면'),(37,520,320,'점곡면'),(37,520,330,'옥산면'),(37,520,340,'사곡면'),(37,520,350,'춘산면'),(37,520,360,'가음면'),(37,520,370,'금성면'),(37,520,380,'봉양면'),(37,520,390,'비안면'),(37,520,400,'구천면'),(37,520,410,'단밀면'),(37,520,420,'단북면'),(37,520,430,'안계면'),(37,520,440,'다인면'),(37,520,450,'신평면'),(37,520,460,'안평면'),(37,520,470,'안사면'),(37,530,110,'청송읍'),(37,530,320,'부남면'),(37,530,330,'현동면'),(37,530,340,'현서면'),(37,530,350,'안덕면'),(37,530,360,'파천면'),(37,530,370,'진보면'),(37,530,380,'주왕산면'),(37,540,110,'영양읍'),(37,540,310,'입암면'),(37,540,320,'청기면'),(37,540,330,'일월면'),(37,540,340,'수비면'),(37,540,350,'석보면'),(37,550,110,'영덕읍'),(37,550,310,'강구면'),(37,550,320,'남정면'),(37,550,330,'달산면'),(37,550,340,'지품면'),(37,550,350,'축산면'),(37,550,360,'영해면'),(37,550,370,'병곡면'),(37,550,380,'창수면'),(37,560,110,'화양읍'),(37,560,120,'청도읍'),(37,560,310,'각남면'),(37,560,320,'풍각면'),(37,560,330,'각북면'),(37,560,340,'이서면'),(37,560,350,'운문면'),(37,560,360,'금천면'),(37,560,370,'매전면'),(37,570,120,'대가야읍'),(37,570,310,'덕곡면'),(37,570,320,'운수면'),(37,570,330,'성산면'),(37,570,340,'다산면'),(37,570,350,'개진면'),(37,570,360,'우곡면'),(37,570,370,'쌍림면'),(37,580,110,'성주읍'),(37,580,310,'선남면'),(37,580,320,'용암면'),(37,580,330,'수륜면'),(37,580,340,'가천면'),(37,580,350,'금수면'),(37,580,360,'대가면'),(37,580,370,'벽진면'),(37,580,380,'초전면'),(37,580,390,'월항면'),(37,590,110,'왜관읍'),(37,590,120,'북삼읍'),(37,590,130,'석적읍'),(37,590,310,'지천면'),(37,590,320,'동명면'),(37,590,330,'가산면'),(37,590,360,'약목면'),(37,590,370,'기산면'),(37,600,110,'예천읍'),(37,600,310,'용문면'),(37,600,340,'감천면'),(37,600,350,'보문면'),(37,600,360,'호명면'),(37,600,370,'유천면'),(37,600,380,'용궁면'),(37,600,390,'개포면'),(37,600,400,'지보면'),(37,600,410,'풍양면'),(37,600,420,'효자면'),(37,600,430,'은풍면'),(37,610,110,'봉화읍'),(37,610,310,'물야면'),(37,610,320,'봉성면'),(37,610,330,'법전면'),(37,610,340,'춘양면'),(37,610,350,'소천면'),(37,610,360,'석포면'),(37,610,370,'재산면'),(37,610,380,'명호면'),(37,610,390,'상운면'),(37,620,110,'울진읍'),(37,620,120,'평해읍'),(37,620,310,'북면'),(37,620,330,'근남면'),(37,620,350,'기성면'),(37,620,360,'온정면'),(37,620,370,'죽변면'),(37,620,380,'후포면'),(37,620,390,'금강송면'),(37,620,400,'매화면'),(37,630,110,'울릉읍'),(37,630,310,'서면'),(37,630,320,'북면'),(38,30,110,'문산읍'),(38,30,310,'내동면'),(38,30,320,'정촌면'),(38,30,330,'금곡면'),(38,30,340,'진성면'),(38,30,350,'일반성면'),(38,30,360,'이반성면'),(38,30,370,'사봉면'),(38,30,380,'지수면'),(38,30,390,'대곡면'),(38,30,400,'금산면'),(38,30,410,'집현면'),(38,30,420,'미천면'),(38,30,430,'명석면'),(38,30,440,'대평면'),(38,30,450,'수곡면'),(38,30,650,'상평동'),(38,30,660,'초장동'),(38,30,670,'평거동'),(38,30,680,'신안동'),(38,30,690,'이현동'),(38,30,700,'판문동'),(38,30,710,'가호동'),(38,30,720,'천전동'),(38,30,730,'성북동'),(38,30,740,'중앙동'),(38,30,750,'상봉동'),(38,30,760,'충무공동'),(38,30,770,'상대동'),(38,30,780,'하대동'),(38,50,110,'산양읍'),(38,50,310,'용남면'),(38,50,320,'도산면'),(38,50,330,'광도면'),(38,50,340,'욕지면'),(38,50,350,'한산면'),(38,50,360,'사량면'),(38,50,520,'명정동'),(38,50,530,'중앙동'),(38,50,540,'정량동'),(38,50,550,'북신동'),(38,50,560,'무전동'),(38,50,620,'도천동'),(38,50,630,'미수동'),(38,50,640,'봉평동'),(38,60,110,'사천읍'),(38,60,310,'정동면'),(38,60,320,'사남면'),(38,60,330,'용현면'),(38,60,340,'축동면'),(38,60,350,'곤양면'),(38,60,360,'곤명면'),(38,60,370,'서포면'),(38,60,510,'동서동'),(38,60,520,'선구동'),(38,60,530,'동서금동'),(38,60,550,'벌용동'),(38,60,570,'향촌동'),(38,60,590,'남양동'),(38,70,110,'진영읍'),(38,70,320,'주촌면'),(38,70,330,'진례면'),(38,70,340,'한림면'),(38,70,350,'생림면'),(38,70,360,'상동면'),(38,70,370,'대동면'),(38,70,510,'동상동'),(38,70,520,'회현동'),(38,70,530,'부원동'),(38,70,540,'내외동'),(38,70,550,'북부동'),(38,70,560,'칠산서부동'),(38,70,580,'활천동'),(38,70,590,'삼안동'),(38,70,600,'불암동'),(38,70,610,'장유1동'),(38,70,620,'장유2동'),(38,70,630,'장유3동'),(38,80,110,'삼랑진읍'),(38,80,120,'하남읍'),(38,80,310,'부북면'),(38,80,320,'상동면'),(38,80,330,'산외면'),(38,80,340,'산내면'),(38,80,350,'단장면'),(38,80,360,'상남면'),(38,80,370,'초동면'),(38,80,380,'무안면'),(38,80,390,'청도면'),(38,80,510,'내일동'),(38,80,520,'내이동'),(38,80,530,'교동'),(38,80,540,'삼문동'),(38,80,550,'가곡동'),(38,90,310,'일운면'),(38,90,320,'동부면'),(38,90,330,'남부면'),(38,90,340,'거제면'),(38,90,350,'둔덕면'),(38,90,360,'사등면'),(38,90,370,'연초면'),(38,90,380,'하청면'),(38,90,390,'장목면'),(38,90,530,'능포동'),(38,90,540,'아주동'),(38,90,550,'옥포1동'),(38,90,560,'옥포2동'),(38,90,570,'장평동'),(38,90,580,'고현동'),(38,90,590,'상문동'),(38,90,600,'수양동'),(38,90,610,'장승포동'),(38,100,120,'물금읍'),(38,100,310,'동면'),(38,100,320,'원동면'),(38,100,330,'상북면'),(38,100,340,'하북면'),(38,100,520,'삼성동'),(38,100,530,'강서동'),(38,100,540,'서창동'),(38,100,550,'소주동'),(38,100,560,'평산동'),(38,100,570,'덕계동'),(38,100,580,'중앙동'),(38,100,590,'양주동'),(38,111,110,'동읍'),(38,111,310,'북면'),(38,111,320,'대산면'),(38,111,510,'의창동'),(38,111,520,'팔룡동'),(38,111,530,'명곡동'),(38,111,540,'봉림동'),(38,112,510,'반송동'),(38,112,520,'중앙동'),(38,112,530,'상남동'),(38,112,540,'사파동'),(38,112,550,'가음정동'),(38,112,560,'성주동'),(38,112,570,'웅남동'),(38,112,580,'용지동'),(38,113,310,'구산면'),(38,113,320,'진동면'),(38,113,330,'진북면'),(38,113,340,'진전면'),(38,113,510,'현동'),(38,113,520,'가포동'),(38,113,530,'월영동'),(38,113,540,'문화동'),(38,113,570,'완월동'),(38,113,580,'자산동'),(38,113,640,'합포동'),(38,113,650,'산호동'),(38,113,660,'반월중앙동'),(38,113,670,'오동동'),(38,113,680,'교방동'),(38,114,110,'내서읍'),(38,114,510,'회원1동'),(38,114,520,'회원2동'),(38,114,550,'회성동'),(38,114,560,'양덕1동'),(38,114,570,'양덕2동'),(38,114,580,'합성1동'),(38,114,590,'합성2동'),(38,114,600,'구암1동'),(38,114,610,'구암2동'),(38,114,620,'봉암동'),(38,114,630,'석전동'),(38,115,540,'여좌동'),(38,115,550,'태백동'),(38,115,560,'경화동'),(38,115,570,'병암동'),(38,115,580,'석동'),(38,115,590,'이동'),(38,115,600,'자은동'),(38,115,610,'덕산동'),(38,115,620,'풍호동'),(38,115,630,'웅천동'),(38,115,640,'웅동1동'),(38,115,650,'웅동2동'),(38,115,660,'충무동'),(38,510,110,'의령읍'),(38,510,310,'가례면'),(38,510,320,'칠곡면'),(38,510,330,'대의면'),(38,510,340,'화정면'),(38,510,350,'용덕면'),(38,510,360,'정곡면'),(38,510,370,'지정면'),(38,510,380,'낙서면'),(38,510,390,'부림면'),(38,510,400,'봉수면'),(38,510,410,'궁류면'),(38,510,420,'유곡면'),(38,520,110,'가야읍'),(38,520,120,'칠원읍'),(38,520,310,'함안면'),(38,520,320,'군북면'),(38,520,330,'법수면'),(38,520,340,'대산면'),(38,520,350,'칠서면'),(38,520,360,'칠북면'),(38,520,380,'산인면'),(38,520,390,'여항면'),(38,530,110,'창녕읍'),(38,530,120,'남지읍'),(38,530,310,'고암면'),(38,530,320,'성산면'),(38,530,330,'대합면'),(38,530,340,'이방면'),(38,530,350,'유어면'),(38,530,360,'대지면'),(38,530,370,'계성면'),(38,530,380,'영산면'),(38,530,390,'장마면'),(38,530,400,'도천면'),(38,530,410,'길곡면'),(38,530,420,'부곡면'),(38,540,110,'고성읍'),(38,540,310,'삼산면'),(38,540,320,'하일면'),(38,540,330,'하이면'),(38,540,340,'상리면'),(38,540,350,'대가면'),(38,540,360,'영현면'),(38,540,370,'영오면'),(38,540,380,'개천면'),(38,540,390,'구만면'),(38,540,400,'회화면'),(38,540,410,'마암면'),(38,540,420,'동해면'),(38,540,430,'거류면'),(38,550,110,'남해읍'),(38,550,310,'이동면'),(38,550,320,'상주면'),(38,550,330,'삼동면'),(38,550,340,'미조면'),(38,550,350,'남면'),(38,550,360,'서면'),(38,550,370,'고현면'),(38,550,380,'설천면'),(38,550,390,'창선면'),(38,560,110,'하동읍'),(38,560,310,'화개면'),(38,560,320,'악양면'),(38,560,330,'적량면'),(38,560,340,'횡천면'),(38,560,350,'고전면'),(38,560,360,'금남면'),(38,560,370,'금성면'),(38,560,380,'진교면'),(38,560,390,'양보면'),(38,560,400,'북천면'),(38,560,410,'청암면'),(38,560,420,'옥종면'),(38,570,110,'산청읍'),(38,570,310,'차황면'),(38,570,320,'오부면'),(38,570,330,'생초면'),(38,570,340,'금서면'),(38,570,350,'삼장면'),(38,570,360,'시천면'),(38,570,370,'단성면'),(38,570,380,'신안면'),(38,570,390,'생비량면'),(38,570,400,'신등면'),(38,580,110,'함양읍'),(38,580,310,'마천면'),(38,580,320,'휴천면'),(38,580,330,'유림면'),(38,580,340,'수동면'),(38,580,350,'지곡면'),(38,580,360,'안의면'),(38,580,370,'서하면'),(38,580,380,'서상면'),(38,580,390,'백전면'),(38,580,400,'병곡면'),(38,590,110,'거창읍'),(38,590,310,'주상면'),(38,590,320,'웅양면'),(38,590,330,'고제면'),(38,590,340,'북상면'),(38,590,350,'위천면'),(38,590,360,'마리면'),(38,590,370,'남상면'),(38,590,380,'남하면'),(38,590,390,'신원면'),(38,590,400,'가조면'),(38,590,410,'가북면'),(38,600,110,'합천읍'),(38,600,310,'봉산면'),(38,600,320,'묘산면'),(38,600,330,'가야면'),(38,600,340,'야로면'),(38,600,350,'율곡면'),(38,600,360,'초계면'),(38,600,370,'쌍책면'),(38,600,380,'덕곡면'),(38,600,390,'청덕면'),(38,600,400,'적중면'),(38,600,410,'대양면'),(38,600,420,'쌍백면'),(38,600,430,'삼가면'),(38,600,440,'가회면'),(38,600,450,'대병면'),(38,600,460,'용주면'),(39,10,110,'한림읍'),(39,10,120,'애월읍'),(39,10,130,'구좌읍'),(39,10,140,'조천읍'),(39,10,310,'한경면'),(39,10,320,'추자면'),(39,10,330,'우도면'),(39,10,510,'일도1동'),(39,10,520,'일도2동'),(39,10,530,'이도1동'),(39,10,540,'이도2동'),(39,10,550,'삼도1동'),(39,10,560,'삼도2동'),(39,10,570,'용담1동'),(39,10,580,'용담2동'),(39,10,590,'건입동'),(39,10,600,'화북동'),(39,10,610,'삼양동'),(39,10,620,'봉개동'),(39,10,630,'아라동'),(39,10,640,'오라동'),(39,10,650,'연동'),(39,10,660,'노형동'),(39,10,670,'외도동'),(39,10,680,'이호동'),(39,10,690,'도두동'),(39,20,110,'대정읍'),(39,20,120,'남원읍'),(39,20,130,'성산읍'),(39,20,310,'안덕면'),(39,20,320,'표선면'),(39,20,510,'송산동'),(39,20,520,'정방동'),(39,20,530,'중앙동'),(39,20,540,'천지동'),(39,20,550,'효돈동'),(39,20,560,'영천동'),(39,20,570,'동홍동'),(39,20,580,'서홍동'),(39,20,590,'대륜동'),(39,20,600,'대천동'),(39,20,610,'중문동'),(39,20,620,'예래동');
/*!40000 ALTER TABLE `dong` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gun`
--

DROP TABLE IF EXISTS `gun`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gun` (
  `si_code` int NOT NULL,
  `code` int NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`si_code`,`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gun`
--

LOCK TABLES `gun` WRITE;
/*!40000 ALTER TABLE `gun` DISABLE KEYS */;
INSERT INTO `gun` VALUES (11,10,'종로구'),(11,20,'중구'),(11,30,'용산구'),(11,40,'성동구'),(11,50,'광진구'),(11,60,'동대문구'),(11,70,'중랑구'),(11,80,'성북구'),(11,90,'강북구'),(11,100,'도봉구'),(11,110,'노원구'),(11,120,'은평구'),(11,130,'서대문구'),(11,140,'마포구'),(11,150,'양천구'),(11,160,'강서구'),(11,170,'구로구'),(11,180,'금천구'),(11,190,'영등포구'),(11,200,'동작구'),(11,210,'관악구'),(11,220,'서초구'),(11,230,'강남구'),(11,240,'송파구'),(11,250,'강동구'),(21,10,'중구'),(21,20,'서구'),(21,30,'동구'),(21,40,'영도구'),(21,50,'부산진구'),(21,60,'동래구'),(21,70,'남구'),(21,80,'북구'),(21,90,'해운대구'),(21,100,'사하구'),(21,110,'금정구'),(21,120,'강서구'),(21,130,'연제구'),(21,140,'수영구'),(21,150,'사상구'),(21,510,'기장군'),(22,10,'중구'),(22,20,'동구'),(22,30,'서구'),(22,40,'남구'),(22,50,'북구'),(22,60,'수성구'),(22,70,'달서구'),(22,510,'달성군'),(23,10,'중구'),(23,20,'동구'),(23,40,'연수구'),(23,50,'남동구'),(23,60,'부평구'),(23,70,'계양구'),(23,80,'서구'),(23,90,'미추홀구'),(23,510,'강화군'),(23,520,'옹진군'),(24,10,'동구'),(24,20,'서구'),(24,30,'남구'),(24,40,'북구'),(24,50,'광산구'),(25,10,'동구'),(25,20,'중구'),(25,30,'서구'),(25,40,'유성구'),(25,50,'대덕구'),(26,10,'중구'),(26,20,'남구'),(26,30,'동구'),(26,40,'북구'),(26,510,'울주군'),(29,10,'세종시'),(31,11,'수원시 장안구'),(31,12,'수원시 권선구'),(31,13,'수원시 팔달구'),(31,14,'수원시 영통구'),(31,21,'성남시 수정구'),(31,22,'성남시 중원구'),(31,23,'성남시 분당구'),(31,30,'의정부시'),(31,41,'안양시 만안구'),(31,42,'안양시 동안구'),(31,50,'부천시'),(31,60,'광명시'),(31,70,'평택시'),(31,80,'동두천시'),(31,91,'안산시 상록구'),(31,92,'안산시 단원구'),(31,101,'고양시 덕양구'),(31,103,'고양시 일산동구'),(31,104,'고양시 일산서구'),(31,110,'과천시'),(31,120,'구리시'),(31,130,'남양주시'),(31,140,'오산시'),(31,150,'시흥시'),(31,160,'군포시'),(31,170,'의왕시'),(31,180,'하남시'),(31,191,'용인시 처인구'),(31,192,'용인시 기흥구'),(31,193,'용인시 수지구'),(31,200,'파주시'),(31,210,'이천시'),(31,220,'안성시'),(31,230,'김포시'),(31,240,'화성시'),(31,250,'광주시'),(31,260,'양주시'),(31,270,'포천시'),(31,280,'여주시'),(31,550,'연천군'),(31,570,'가평군'),(31,580,'양평군'),(32,10,'춘천시'),(32,20,'원주시'),(32,30,'강릉시'),(32,40,'동해시'),(32,50,'태백시'),(32,60,'속초시'),(32,70,'삼척시'),(32,510,'홍천군'),(32,520,'횡성군'),(32,530,'영월군'),(32,540,'평창군'),(32,550,'정선군'),(32,560,'철원군'),(32,570,'화천군'),(32,580,'양구군'),(32,590,'인제군'),(32,600,'고성군'),(32,610,'양양군'),(33,20,'충주시'),(33,30,'제천시'),(33,41,'청주시 상당구'),(33,42,'청주시 서원구'),(33,43,'청주시 흥덕구'),(33,44,'청주시 청원구'),(33,520,'보은군'),(33,530,'옥천군'),(33,540,'영동군'),(33,550,'진천군'),(33,560,'괴산군'),(33,570,'음성군'),(33,580,'단양군'),(33,590,'증평군'),(34,11,'천안시 동남구'),(34,12,'천안시 서북구'),(34,20,'공주시'),(34,30,'보령시'),(34,40,'아산시'),(34,50,'서산시'),(34,60,'논산시'),(34,70,'계룡시'),(34,80,'당진시'),(34,510,'금산군'),(34,530,'부여군'),(34,540,'서천군'),(34,550,'청양군'),(34,560,'홍성군'),(34,570,'예산군'),(34,580,'태안군'),(35,11,'전주시 완산구'),(35,12,'전주시 덕진구'),(35,20,'군산시'),(35,30,'익산시'),(35,40,'정읍시'),(35,50,'남원시'),(35,60,'김제시'),(35,510,'완주군'),(35,520,'진안군'),(35,530,'무주군'),(35,540,'장수군'),(35,550,'임실군'),(35,560,'순창군'),(35,570,'고창군'),(35,580,'부안군'),(36,10,'목포시'),(36,20,'여수시'),(36,30,'순천시'),(36,40,'나주시'),(36,60,'광양시'),(36,510,'담양군'),(36,520,'곡성군'),(36,530,'구례군'),(36,550,'고흥군'),(36,560,'보성군'),(36,570,'화순군'),(36,580,'장흥군'),(36,590,'강진군'),(36,600,'해남군'),(36,610,'영암군'),(36,620,'무안군'),(36,630,'함평군'),(36,640,'영광군'),(36,650,'장성군'),(36,660,'완도군'),(36,670,'진도군'),(36,680,'신안군'),(37,11,'포항시 남구'),(37,12,'포항시 북구'),(37,20,'경주시'),(37,30,'김천시'),(37,40,'안동시'),(37,50,'구미시'),(37,60,'영주시'),(37,70,'영천시'),(37,80,'상주시'),(37,90,'문경시'),(37,100,'경산시'),(37,510,'군위군'),(37,520,'의성군'),(37,530,'청송군'),(37,540,'영양군'),(37,550,'영덕군'),(37,560,'청도군'),(37,570,'고령군'),(37,580,'성주군'),(37,590,'칠곡군'),(37,600,'예천군'),(37,610,'봉화군'),(37,620,'울진군'),(37,630,'울릉군'),(38,30,'진주시'),(38,50,'통영시'),(38,60,'사천시'),(38,70,'김해시'),(38,80,'밀양시'),(38,90,'거제시'),(38,100,'양산시'),(38,111,'창원시 의창구'),(38,112,'창원시 성산구'),(38,113,'창원시 마산합포구'),(38,114,'창원시 마산회원구'),(38,115,'창원시 진해구'),(38,510,'의령군'),(38,520,'함안군'),(38,530,'창녕군'),(38,540,'고성군'),(38,550,'남해군'),(38,560,'하동군'),(38,570,'산청군'),(38,580,'함양군'),(38,590,'거창군'),(38,600,'합천군'),(39,10,'제주시'),(39,20,'서귀포시');
/*!40000 ALTER TABLE `gun` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (2);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invite`
--

DROP TABLE IF EXISTS `invite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invite` (
  `community_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  KEY `FK_community_TO_invite_1` (`community_id`),
  KEY `FK_user_TO_invite_1` (`user_id`),
  CONSTRAINT `FK_community_TO_invite_1` FOREIGN KEY (`community_id`) REFERENCES `community` (`community_id`),
  CONSTRAINT `FK_user_TO_invite_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invite`
--

LOCK TABLES `invite` WRITE;
/*!40000 ALTER TABLE `invite` DISABLE KEYS */;
/*!40000 ALTER TABLE `invite` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `my_course`
--

DROP TABLE IF EXISTS `my_course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `my_course` (
  `my_course_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `course_id` bigint NOT NULL,
  PRIMARY KEY (`my_course_id`),
  KEY `FK_user_TO_my_course_1` (`user_id`),
  KEY `FK_custom_course_TO_my_course_1` (`course_id`),
  CONSTRAINT `FK_custom_course_TO_my_course_1` FOREIGN KEY (`course_id`) REFERENCES `custom_course` (`course_id`),
  CONSTRAINT `FK_user_TO_my_course_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `my_course`
--

LOCK TABLES `my_course` WRITE;
/*!40000 ALTER TABLE `my_course` DISABLE KEYS */;
INSERT INTO `my_course` VALUES (34,3,28),(35,3,39),(36,5,28),(37,9,39),(39,9,28),(40,8,40),(42,8,39),(44,8,26),(47,9,40),(48,9,26),(50,3,41),(52,9,41),(55,6,41),(63,9,46),(67,11,41),(70,11,47),(72,10,46),(73,8,41),(74,10,41),(75,6,39),(76,6,48),(77,11,40),(78,13,48),(83,10,50),(84,6,50),(85,11,50),(86,16,51);
/*!40000 ALTER TABLE `my_course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notice`
--

DROP TABLE IF EXISTS `notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notice` (
  `notice_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `title` varchar(255) NOT NULL COMMENT '게시판 제목',
  `content` varchar(255) DEFAULT NULL COMMENT '게시판 내용',
  `visited` bigint NOT NULL DEFAULT '0' COMMENT '게시판 조회수 , 유저당 최대 1회 카운트',
  `time` datetime(6) NOT NULL,
  PRIMARY KEY (`notice_id`),
  KEY `FK_user_TO_notice_1` (`user_id`),
  CONSTRAINT `FK_user_TO_notice_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notice`
--

LOCK TABLES `notice` WRITE;
/*!40000 ALTER TABLE `notice` DISABLE KEYS */;
/*!40000 ALTER TABLE `notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notice_image`
--

DROP TABLE IF EXISTS `notice_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notice_image` (
  `board_image_id` bigint NOT NULL AUTO_INCREMENT,
  `notice_id` bigint NOT NULL,
  `name` varchar(255) NOT NULL COMMENT '실제 이미지 이름',
  `path` varchar(255) NOT NULL COMMENT '파일 경로와 해당 이미지에 부여된 UUID',
  `type` varchar(255) NOT NULL COMMENT '이미지의 확장명',
  PRIMARY KEY (`board_image_id`),
  KEY `FK_notice_TO_notice_image_1` (`notice_id`),
  CONSTRAINT `FK_notice_TO_notice_image_1` FOREIGN KEY (`notice_id`) REFERENCES `notice` (`notice_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notice_image`
--

LOCK TABLES `notice_image` WRITE;
/*!40000 ALTER TABLE `notice_image` DISABLE KEYS */;
/*!40000 ALTER TABLE `notice_image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recode`
--

DROP TABLE IF EXISTS `recode`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recode` (
  `user_id` bigint NOT NULL,
  `total_speed` bigint DEFAULT NULL,
  `total_cal` bigint DEFAULT NULL,
  `total_dist` bigint DEFAULT NULL,
  `total_time` bigint DEFAULT NULL,
  `week_speed` bigint DEFAULT NULL,
  `week_dist` bigint DEFAULT NULL,
  `week_cal` bigint DEFAULT NULL,
  `week_time` bigint DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `FK_user_TO_recode_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recode`
--

LOCK TABLES `recode` WRITE;
/*!40000 ALTER TABLE `recode` DISABLE KEYS */;
INSERT INTO `recode` VALUES (3,0,6,146,153,0,146,6,153),(6,1,183,3797,2418,1,3797,183,2418),(10,3,58,3672,1205,3,3672,58,1205),(11,0,0,0,0,0,0,0,0),(16,3,42,1337,356,3,1337,42,356),(17,0,0,0,0,0,0,0,0),(18,0,0,0,0,0,0,0,0),(19,0,0,0,0,0,0,0,0),(20,0,0,0,0,0,0,0,0),(22,0,0,0,0,0,0,0,0),(23,0,0,0,0,0,0,0,0),(24,0,0,0,0,0,0,0,0),(25,0,0,0,0,0,0,0,0),(26,0,0,0,0,0,0,0,0),(27,0,0,0,0,0,0,0,0),(28,0,0,0,0,0,0,0,0),(29,0,0,0,0,0,0,0,0);
/*!40000 ALTER TABLE `recode` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `record_every_time`
--

DROP TABLE IF EXISTS `record_every_time`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `record_every_time` (
  `record_every_time_id` bigint NOT NULL AUTO_INCREMENT,
  `calories` bigint NOT NULL,
  `distance` bigint NOT NULL,
  `speed` bigint NOT NULL,
  `time` bigint NOT NULL,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`record_every_time_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `record_every_time`
--

LOCK TABLES `record_every_time` WRITE;
/*!40000 ALTER TABLE `record_every_time` DISABLE KEYS */;
INSERT INTO `record_every_time` VALUES (1,24,20,10,20,'2023-02-14 18:09:39',3),(2,30,18,11,17,'2023-02-14 18:10:09',3),(3,100,46,12,44,'2023-02-14 18:15:24',3),(4,20,30,10,23,'2023-02-14 18:17:33',3),(5,100,20,13,24,'2023-02-14 18:18:00',3),(6,30,31,14,33,'2023-02-14 18:18:36',3),(7,30,19,12,23,'2023-02-14 18:19:02',3),(8,24,30,15,30,'2023-02-15 08:22:29',3),(9,64,2152,12,625,'2023-02-15 12:59:54',6),(10,34,476,2,618,'2023-02-15 19:40:56',6),(11,85,1169,3,1175,'2023-02-16 07:28:52',6),(12,0,0,0,1,'2023-02-16 17:12:20',10),(13,24,1209,9,468,'2023-02-16 20:31:14',10),(14,0,0,0,1,'2023-02-16 23:24:42',16),(15,0,0,0,1,'2023-02-16 23:39:55',16),(16,42,1337,13,354,'2023-02-16 23:49:58',16);
/*!40000 ALTER TABLE `record_every_time` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `report`
--

DROP TABLE IF EXISTS `report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `report` (
  `report_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `user_id_reported` bigint NOT NULL,
  `type` varchar(255) NOT NULL COMMENT '신고 종류',
  `content` varchar(255) NOT NULL COMMENT '구체적인 사유',
  `time` datetime(6) NOT NULL,
  `check` bit(1) NOT NULL COMMENT '관리자 신고 처리 여부',
  PRIMARY KEY (`report_id`),
  KEY `FK_user_TO_report_1` (`user_id`),
  KEY `FK_user_TO_report_2` (`user_id_reported`),
  CONSTRAINT `FK_user_TO_report_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FK_user_TO_report_2` FOREIGN KEY (`user_id_reported`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report`
--

LOCK TABLES `report` WRITE;
/*!40000 ALTER TABLE `report` DISABLE KEYS */;
/*!40000 ALTER TABLE `report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sales_record`
--

DROP TABLE IF EXISTS `sales_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sales_record` (
  `sales_record_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `deal_id` bigint NOT NULL,
  `clearly_deal` bit(1) NOT NULL DEFAULT b'1' COMMENT '정상적으로 거래가 완료되었는지 여부',
  PRIMARY KEY (`sales_record_id`),
  KEY `FK_user_TO_sales_record_1` (`user_id`),
  KEY `FK_deal_TO_sales_record_1` (`deal_id`),
  CONSTRAINT `FK_deal_TO_sales_record_1` FOREIGN KEY (`deal_id`) REFERENCES `deal` (`deal_id`),
  CONSTRAINT `FK_user_TO_sales_record_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sales_record`
--

LOCK TABLES `sales_record` WRITE;
/*!40000 ALTER TABLE `sales_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `sales_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `si`
--

DROP TABLE IF EXISTS `si`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `si` (
  `code` int NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `si`
--

LOCK TABLES `si` WRITE;
/*!40000 ALTER TABLE `si` DISABLE KEYS */;
INSERT INTO `si` VALUES (11,'서울특별시'),(21,'부산광역시'),(22,'대구광역시'),(23,'인천광역시'),(24,'광주광역시'),(25,'대전광역시'),(26,'울산광역시'),(29,'세종특별자치시'),(31,'경기도'),(32,'강원도'),(33,'충청북도'),(34,'충청남도'),(35,'전라북도'),(36,'전라남도'),(37,'경상북도'),(38,'경상남도'),(39,'제주특별자치도');
/*!40000 ALTER TABLE `si` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unofficial_point`
--

DROP TABLE IF EXISTS `unofficial_point`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `unofficial_point` (
  `unofficial_point_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `name` varchar(255) NOT NULL COMMENT '장소 이름',
  `image_name` varchar(255) NOT NULL COMMENT '이미지 이름 및 확장명',
  `content` varchar(255) NOT NULL COMMENT '장소 설명',
  `lat` decimal(18,15) NOT NULL COMMENT '위도',
  `lon` decimal(18,15) NOT NULL COMMENT '경도',
  PRIMARY KEY (`unofficial_point_id`),
  KEY `FK_user_TO_unofficial_point_1` (`user_id`),
  CONSTRAINT `FK_user_TO_unofficial_point_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unofficial_point`
--

LOCK TABLES `unofficial_point` WRITE;
/*!40000 ALTER TABLE `unofficial_point` DISABLE KEYS */;
/*!40000 ALTER TABLE `unofficial_point` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `nickname` varchar(50) DEFAULT NULL,
  `gender` varchar(50) DEFAULT NULL,
  `age` varchar(255) DEFAULT NULL,
  `si` varchar(50) DEFAULT NULL,
  `gun` varchar(50) DEFAULT NULL,
  `dong` varchar(50) DEFAULT NULL,
  `email` varchar(50) NOT NULL,
  `weight` int DEFAULT NULL,
  `cycle_weight` int DEFAULT NULL,
  `id` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `permission` int NOT NULL,
  `image_path` varchar(255) NOT NULL,
  `open` bit(1) NOT NULL,
  `refresh_token` varchar(255) DEFAULT NULL,
  `image_uuid_path` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (3,'최형규','최형규','male','1997-03-29','서울특별시','서대문구','신촌동','758db5f86222@drmail.in',65,12,'test1','e1d1d7bcb9190cca86db9a6a24038d43746a3a48d43c007b8e84365159f67aaa',0,'images/profile/3.jpg',_binary '',NULL,NULL),(4,'홍길동','나이키','female','1990-01-02','서울특별시','종로구','부암동','fbbc25cae63e@drmail.in',65,12,'test2','e1d1d7bcb9190cca86db9a6a24038d43746a3a48d43c007b8e84365159f67aaa',0,'images/profile/4.jpg',_binary '',NULL,NULL),(5,'박민주','배달기사','female','2021-02-06','부산광역시','중구','동광동','2d29f2132887@drmail.in',65,12,'test3','e1d1d7bcb9190cca86db9a6a24038d43746a3a48d43c007b8e84365159f67aaa',0,'images/profile/default.png',_binary '',NULL,NULL),(6,'이상찬','이상찬','male','1992-08-25','서울특별시','중구','회현동','dltkdcksqkqh@naver.com',65,12,'dltkdcksqkqh','febd93f04bda1aec0d374f8fd014d062525934feb1f1b81ee7c64d61f66b84b1',0,'images/profile/6.png',_binary '\0',NULL,NULL),(7,'아이폰','응애','male','1999-01-01','서울특별시','종로구','사직동','afe5880fa712@drmail.in',65,12,'test4','e1d1d7bcb9190cca86db9a6a24038d43746a3a48d43c007b8e84365159f67aaa',0,'images/profile/7.jpg',_binary '\0',NULL,NULL),(8,'권용진','권용','male','1996-11-08','부산광역시','강서구','녹산동','yjkwon1996@gmail.com',50,10,'yjkwon1996','f95e7734ff099f7bd28dc7d36f533e23d8287105e1418d6ddf0663e02bb736c7',0,'images/profile/8.jpg',_binary '',NULL,NULL),(9,'도현','우린','male','1996-10-05','부산광역시','연제구','연산3동','michaia0363@naver.com',84,20,'michael0363','0df0569bbba95d96dbad648ad94e416e2214d24b131e4740515458ec7df8ece4',0,'images/profile/9.jpg',_binary '',NULL,NULL),(10,'하상재','레어닉','남','2009-2-1','부산광역시','동래구','사직3동','dlbia2009@naver.com',65,12,'test12','febd93f04bda1aec0d374f8fd014d062525934feb1f1b81ee7c64d61f66b84b1',1,'images/profile/10.jpg',_binary '',NULL,NULL),(11,'서우린','가나다라','남','2013-2-1','서울특별시','종로구','사직동','tjdnfls12345@gmail.com',65,12,'asd123','8c86837c796f2871647133cee065e6978113940e18a23db5f9dbc04f8511b920',1,'images/profile/11.jpg',_binary '',NULL,NULL),(12,'김효은','효코치','female','2023-02-13','부산광역시','강서구','녹산동','nktion@naver.com',0,0,'kimcoach21','7648fc997c14631ba3aaba1434f5c81ce19500076b8fbc1ff17642698dab2c9a',0,'images/profile/12.png',_binary '\0',NULL,NULL),(13,'도현도현','용진','male','1996-10-05','부산광역시','부산진구','가야2동','michaia0363@gmail.com',21,50,'michaia0363','f4d80c6f768040d209f65b345924f61cf7f59d3755f5e2cd85c84eb76579ca7a',0,'images/profile/13.jpg',_binary '\0',NULL,NULL),(14,'박동탁','동팔','male','2022-01-01','서울특별시','중구','회현동','junzzamg9@gmail.com',87,87,'dondon','6e10c8c96863bc1cb324f7c22a5e890a18bb913ff07f9cdd3d3dbb89021a5ef1',0,'images/profile/14.jpg',_binary '',NULL,NULL),(15,'우린우린','애옹','남','2010-2-1','서울특별시','종로구','사직동','prwoorin@gmail.com',65,12,'tjdnfls1234','8c86837c796f2871647133cee065e6978113940e18a23db5f9dbc04f8511b920',1,'images/profile/default.png',_binary '',NULL,NULL),(16,'한채수','회장아들','남','2003-2-1','울산광역시','중구','학성동','acdc1975@naver.com',65,12,'fancy23','febd93f04bda1aec0d374f8fd014d062525934feb1f1b81ee7c64d61f66b84b1',1,'images/profile/16.jpg',_binary '',NULL,NULL),(17,'문석환','kamoo','male','1996-11-21','부산광역시','북구','만덕3동','tjrghks96@naver.com',85,30,'kamoo','cccba56b778dbd184ad1c4fd83c4651f873605e8027d0748c4a1af07d92eb135',0,'images/profile/default.png',_binary '\0',NULL,NULL),(18,'하상재','하상재','male','1996-01-01','경상남도','거제시','남부면','xkhg0611x@naver.com',75,2,'xkhg0611x','73c97c725a23d64834a9cf6a2a153c8abcedfa8e4996e44d8a1c8f3361b5e1c0',0,'images/profile/default.png',_binary '\0',NULL,NULL),(19,'정경훈','모숙휘','male','1997-08-19','부산광역시','기장군','기장읍','rudgns9334@gmail.com',62,10,'rudgns9334','adb2b265835955b6637e50945d7a92ab7bd9160fa2e3705d0384b5eb9d177d40',0,'images/profile/default.png',_binary '\0',NULL,NULL),(20,'나유진','zz1105','female','1999-11-05','부산광역시','강서구','녹산동','nyj3230@naver.com',0,0,'zzz1105','e19880d4df60029f748a57ea0109b27311cdc0bb2bd6807c43fecf23d99f2fed',0,'images/profile/default.png',_binary '\0',NULL,NULL),(22,'알코치','❤코치❤','male','1999-01-23','서울특별시','중구','명동','kkyjj123@gmail.com',61,12,'coach','febd93f04bda1aec0d374f8fd014d062525934feb1f1b81ee7c64d61f66b84b1',0,'images/profile/default.png',_binary '\0',NULL,NULL),(23,'최형규','말포이','male','1997-03-29','서울특별시','광진구','능동','poi5971@naver.com',0,0,'poi5971','e1d1d7bcb9190cca86db9a6a24038d43746a3a48d43c007b8e84365159f67aaa',0,'images/profile/default.png',_binary '\0',NULL,NULL),(24,'컨설턴트','❤컨설턴트❤','male','2000-01-01','서울특별시','강남구','청담동','sangchan0825@gmail.com',0,0,'consultant','febd93f04bda1aec0d374f8fd014d062525934feb1f1b81ee7c64d61f66b84b1',0,'images/profile/default.png',_binary '\0',NULL,NULL),(25,'홍길동','홍길동','male','1950-12-12','서울특별시','종로구','사직동','ba84adfa9680@drmail.in',0,0,'test0','e1d1d7bcb9190cca86db9a6a24038d43746a3a48d43c007b8e84365159f67aaa',0,'images/profile/default.png',_binary '',NULL,NULL),(26,'백지영','총맞은것처럼','female','1980-02-28','서울특별시','서대문구','북아현동','6d137d8ec669@drmail.in',0,0,'test01','e1d1d7bcb9190cca86db9a6a24038d43746a3a48d43c007b8e84365159f67aaa',0,'images/profile/default.png',_binary '',NULL,NULL),(27,'김싸피','탄산수마시면서라이딩','male','1999-01-01','부산광역시','중구','동광동','bf12c10dd1a4@drmail.in',0,0,'test02','e1d1d7bcb9190cca86db9a6a24038d43746a3a48d43c007b8e84365159f67aaa',0,'images/profile/default.png',_binary '',NULL,NULL),(28,'조정석','좋아좋아','male','1980-12-02','서울특별시','용산구','남영동','3e04e4fd0bd0@drmail.in',0,0,'test03','e1d1d7bcb9190cca86db9a6a24038d43746a3a48d43c007b8e84365159f67aaa',0,'images/profile/default.png',_binary '',NULL,NULL),(29,'김광규','반짝반짝','male','1980-12-12','부산광역시','동구','수정1동','7eca00f01eb5@drmail.in',62,12,'test04','e1d1d7bcb9190cca86db9a6a24038d43746a3a48d43c007b8e84365159f67aaa',0,'images/profile/default.png',_binary '',NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_achievement`
--

DROP TABLE IF EXISTS `user_achievement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_achievement` (
  `user_achievement_id` bigint NOT NULL AUTO_INCREMENT,
  `achievement_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`user_achievement_id`),
  KEY `FKcxeo6malce3dsw6tc43bcrwfd` (`achievement_id`),
  KEY `FKqy0s4xpmb52p1yakm4hxrm4yb` (`user_id`),
  CONSTRAINT `FKcxeo6malce3dsw6tc43bcrwfd` FOREIGN KEY (`achievement_id`) REFERENCES `achievement` (`achievement_id`),
  CONSTRAINT `FKqy0s4xpmb52p1yakm4hxrm4yb` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_achievement`
--

LOCK TABLES `user_achievement` WRITE;
/*!40000 ALTER TABLE `user_achievement` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_achievement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vote_category`
--

DROP TABLE IF EXISTS `vote_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vote_category` (
  `vote_category_id` bigint NOT NULL AUTO_INCREMENT,
  `board_vote_id` bigint NOT NULL,
  `category_name` varchar(255) DEFAULT NULL,
  `num` bigint NOT NULL DEFAULT '0' COMMENT '이 항목에 투표한 사람 수',
  PRIMARY KEY (`vote_category_id`),
  KEY `FK_board_vote_TO_vote_category_1` (`board_vote_id`),
  CONSTRAINT `FK_board_vote_TO_vote_category_1` FOREIGN KEY (`board_vote_id`) REFERENCES `board_vote` (`board_vote_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vote_category`
--

LOCK TABLES `vote_category` WRITE;
/*!40000 ALTER TABLE `vote_category` DISABLE KEYS */;
/*!40000 ALTER TABLE `vote_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `zzim_list`
--

DROP TABLE IF EXISTS `zzim_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `zzim_list` (
  `deal_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`deal_id`,`user_id`),
  KEY `FK_user_TO_zzim_list_1` (`user_id`),
  CONSTRAINT `FK_deal_TO_zzim_list_1` FOREIGN KEY (`deal_id`) REFERENCES `deal` (`deal_id`),
  CONSTRAINT `FK_user_TO_zzim_list_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `zzim_list`
--

LOCK TABLES `zzim_list` WRITE;
/*!40000 ALTER TABLE `zzim_list` DISABLE KEYS */;
INSERT INTO `zzim_list` VALUES (64,3),(60,5),(30,6),(40,6),(60,6),(37,8),(53,25);
/*!40000 ALTER TABLE `zzim_list` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-02-17  8:56:09
