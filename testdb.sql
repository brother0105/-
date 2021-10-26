-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- 생성 시간: 21-10-26 12:05
-- 서버 버전: 5.7.31
-- PHP 버전: 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 데이터베이스: `testdb`
--

-- --------------------------------------------------------

--
-- 테이블 구조 `favtable`
--

DROP TABLE IF EXISTS `favtable`;
CREATE TABLE IF NOT EXISTS `favtable` (
  `id` varchar(63) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `memo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 테이블의 덤프 데이터 `favtable`
--

INSERT INTO `favtable` (`id`, `name`, `memo`) VALUES
('7', '흑임자죽', '검은깨를 갈아서 만든 고소함이 가득한 흑임자죽. 남녀노소 모두 사랑하는 맛!'),
('3', '잡채밥', '잡채밥 한 그릇이면 오늘 저녁 끝! 입 맛 없을 때 먹으면 그만이지요~'),
('4', '콩나물밥', '다이어트에 으뜸인 콩나물밥. 밥 물 넣을때 평소보다 적게 넣는거 잊지마세요!');

-- --------------------------------------------------------

--
-- 테이블 구조 `ingretable`
--

DROP TABLE IF EXISTS `ingretable`;
CREATE TABLE IF NOT EXISTS `ingretable` (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `ingre` varchar(255) NOT NULL,
  `num` varchar(255) NOT NULL,
  `date` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;

--
-- 테이블의 덤프 데이터 `ingretable`
--

INSERT INTO `ingretable` (`id`, `ingre`, `num`, `date`) VALUES
(34, '쌀', '2kg', '2021/9/17'),
(33, '콩나물', '30g', '2021/9/16'),
(37, 's', 'e', 'x'),
(38, 'ㅁㄴㅇㄹ', 'ㄹㅇㄴ', '123'),
(32, '김치', '3포기', '2021/09/15');

-- --------------------------------------------------------

--
-- 테이블 구조 `page_view`
--

DROP TABLE IF EXISTS `page_view`;
CREATE TABLE IF NOT EXISTS `page_view` (
  `recipe_num` int(10) UNSIGNED NOT NULL,
  `view_day` datetime DEFAULT NULL,
  PRIMARY KEY (`recipe_num`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 테이블의 덤프 데이터 `page_view`
--

INSERT INTO `page_view` (`recipe_num`, `view_day`) VALUES
(4, '2021-10-26 20:41:11'),
(1, '2021-10-26 20:37:16'),
(3, '2021-10-16 20:53:31'),
(2, '2021-10-15 11:40:51'),
(8, '2021-09-25 11:59:19'),
(14, '2021-09-18 00:00:00'),
(6, '2021-09-25 11:59:10'),
(19, '2021-09-18 00:00:00'),
(17, '2021-09-18 00:00:00'),
(100, '2021-09-18 23:01:24'),
(9, '2021-09-25 11:59:37'),
(7, '2021-09-25 12:06:41'),
(195454, '2021-09-25 11:37:18'),
(5, '2021-10-09 21:00:24'),
(195455, '2021-10-19 20:31:53'),
(195456, '2021-10-19 20:32:06'),
(197676, '2021-10-22 16:21:19'),
(200001, '2021-10-22 16:21:26'),
(200002, '2021-10-26 15:18:10'),
(200003, '2021-10-26 20:54:16'),
(200007, '2021-10-26 20:54:07'),
(21, NULL);

-- --------------------------------------------------------

--
-- 테이블 구조 `person`
--

DROP TABLE IF EXISTS `person`;
CREATE TABLE IF NOT EXISTS `person` (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `country` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

--
-- 테이블의 덤프 데이터 `person`
--

INSERT INTO `person` (`id`, `name`, `country`) VALUES
(1, 'what', 'the'),
(2, 'asdf', 'jap'),
(3, 'holy', 'shit'),
(4, 'what the hell', 'is this thing'),
(5, 'asdf', 'qwer'),
(6, 'asdf', '1234'),
(7, 'fdsa', '4321'),
(8, 'asdf', 'fdsa'),
(9, '123', '321'),
(10, 'sex', 'sex');

-- --------------------------------------------------------

--
-- 테이블 구조 `recipe`
--

DROP TABLE IF EXISTS `recipe`;
CREATE TABLE IF NOT EXISTS `recipe` (
  `photo` varchar(255) DEFAULT NULL,
  `recipe_num` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `recipe_name` varchar(30) NOT NULL,
  `recipe_summary` varchar(255) DEFAULT NULL,
  `recipe_category` varchar(15) DEFAULT NULL,
  `recipe_difficulty` varchar(15) DEFAULT NULL,
  `recipe_time` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`recipe_num`)
) ENGINE=MyISAM AUTO_INCREMENT=200008 DEFAULT CHARSET=utf8;

--
-- 테이블의 덤프 데이터 `recipe`
--

INSERT INTO `recipe` (`photo`, `recipe_num`, `recipe_name`, `recipe_summary`, `recipe_category`, `recipe_difficulty`, `recipe_time`) VALUES
(NULL, 197676, 'test_197676', 'test_197676', NULL, NULL, NULL),
(NULL, 200001, 'test_197676', 'test197676', NULL, NULL, NULL),
('200002.jpg', 200002, 'ㅁㄴㅇㄹ', 'ㅂㅈㄷ', 'ㄷㅈㅂ', '초보환영', '123'),
('200003.jpg', 200003, '테슽', '테', '스', '초보환영', '트'),
('200004.jpg', 200004, 'ㅌ', 'ㅅ', 'ㅌ', '초보환영', '1'),
('200005.jpg', 200005, 'ㅁ', 'ㄴ', 'ㅇ', '초보환영', 'ㄹ'),
('200006.jpg', 200006, 'ㄹ', 'ㅇ', 'ㄴ', '초보환영', 'ㅁ'),
('200007.jpg', 200007, 'ㅁㄴㅇㄹㄴㅇ', 'ㄹㅇ', 'ㄴㅇ', '초보환영', '12');

-- --------------------------------------------------------

--
-- 테이블 구조 `recipe_context`
--

DROP TABLE IF EXISTS `recipe_context`;
CREATE TABLE IF NOT EXISTS `recipe_context` (
  `recipe_context` varchar(512) DEFAULT NULL,
  `recipe_num` int(10) UNSIGNED NOT NULL,
  `recipe_sort` int(10) UNSIGNED NOT NULL,
  `recipe_photo` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`recipe_num`,`recipe_sort`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 테이블의 덤프 데이터 `recipe_context`
--

INSERT INTO `recipe_context` (`recipe_context`, `recipe_num`, `recipe_sort`, `recipe_photo`) VALUES
('ㄹㅇㄹㅇ', 200007, 1, '200007_1.jpg');

-- --------------------------------------------------------

--
-- 테이블 구조 `recipe_favorite`
--

DROP TABLE IF EXISTS `recipe_favorite`;
CREATE TABLE IF NOT EXISTS `recipe_favorite` (
  `recipe_num` int(10) UNSIGNED NOT NULL,
  `f_memo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`recipe_num`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 테이블의 덤프 데이터 `recipe_favorite`
--

INSERT INTO `recipe_favorite` (`recipe_num`, `f_memo`) VALUES
(6, '호박죽 한 그릇이면 하루가 든든하답니다.'),
(5, '집에서도 쉽게 만들어 맛있게 먹을 수 있답니다. 어려워 마시고 만들어 보세요~!'),
(4, '다이어트에 으뜸인 콩나물밥. 밥 물 넣을때 평소보다 적게 넣는거 잊지마세요!'),
(2, '정월대보름에 먹던 오곡밥! 영양을 한그릇에 담았습니다.'),
(195454, 'sex'),
(3, '잡채밥 한 그릇이면 오늘 저녁 끝! 입 맛 없을 때 먹으면 그만이지요~'),
(1, '육수로 지은 밥에 야채를 듬뿍 넣은 영양만점 나물비빔밥!'),
(8, '향긋한 카레향이 너무 좋지요. 누구나 좋아하는 만들기도 간편한 음식입니다.'),
(200007, 'ㄹㅇ');

-- --------------------------------------------------------

--
-- 테이블 구조 `recipe_ingredient`
--

DROP TABLE IF EXISTS `recipe_ingredient`;
CREATE TABLE IF NOT EXISTS `recipe_ingredient` (
  `recipe_num` int(10) UNSIGNED NOT NULL,
  `category` varchar(30) NOT NULL DEFAULT '재료',
  `i_name` varchar(30) NOT NULL,
  `quantity` varchar(20) NOT NULL,
  `typecode` bigint(30) NOT NULL,
  `i_memo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`recipe_num`,`category`,`i_name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 테이블의 덤프 데이터 `recipe_ingredient`
--

INSERT INTO `recipe_ingredient` (`recipe_num`, `category`, `i_name`, `quantity`, `typecode`, `i_memo`) VALUES
(200007, '주재료', 'ㅋㅌㅊ', 'ㅌㅋㅊㅋ', 3060001, '');

-- --------------------------------------------------------

--
-- 테이블 구조 `testdescription`
--

DROP TABLE IF EXISTS `testdescription`;
CREATE TABLE IF NOT EXISTS `testdescription` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `row` bigint(20) NOT NULL,
  `description` varchar(255) NOT NULL,
  `image` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 테이블의 덤프 데이터 `testdescription`
--

INSERT INTO `testdescription` (`id`, `row`, `description`, `image`) VALUES
(195455, 1, 'ㅁㅁㅁㅁㅁㅁㅇㄹㅇㄴㄹㄴㅇㄹㅇㄴㄹㅇㄴㄹ', '195455_1.jpg'),
(195455, 1, 'ㅁㅁㅁㅁㅁㅁㅇㄹㅇㄴㄹㄴㅇㄹㅇㄴㄹㅇㄴㄹ', '195455_1.jpg'),
(195455, 4, 'ㅁㅁㅁㅁㅁㅁㅇㄹㅇㄴㄹㄴㅇㄹㅇㄴㄹㅇㄴㄹ', '195455_4.jpg'),
(195455, 1, '화이팅', '195455_1.jpg'),
(195455, 2, '하고싶다', '195455_2.jpg'),
(195455, 3, '정말', '195455_3.jpg'),
(195457, 1, 'ssdfsdffsdfsf', '195457_1.jpg'),
(195457, 1, 'ssdfsdffsdfsf', '195457_1.jpg'),
(195457, 1, 'asdfasdf', '195457_1.jpg'),
(195457, 1, 'ㅊㅌㅋㅌㅊ', '195457_1.jpg');

-- --------------------------------------------------------

--
-- 테이블 구조 `testingredient`
--

DROP TABLE IF EXISTS `testingredient`;
CREATE TABLE IF NOT EXISTS `testingredient` (
  `id` varchar(255) NOT NULL,
  `ing_order` bigint(20) NOT NULL,
  `ing_name` varchar(255) NOT NULL,
  `ing_volume` varchar(255) NOT NULL,
  `type_code` bigint(20) UNSIGNED NOT NULL,
  `type` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 테이블의 덤프 데이터 `testingredient`
--

INSERT INTO `testingredient` (`id`, `ing_order`, `ing_name`, `ing_volume`, `type_code`, `type`) VALUES
('1', 1, 'ㄹㄴㅇㄹ1', 'ㄹㅇㄴㄹ1', 3060001, '주재료'),
('1', 0, '1', '1', 3060001, '주재료'),
('1', 1, '1', '1', 3060001, '주재료'),
('1', 2, '1', '1', 3060001, '주재료'),
('1', 0, '1', '233', 3060001, '주재료'),
('1', 1, '1', '233', 3060001, '주재료'),
('1', 2, '1', '233', 3060001, '주재료'),
('195455', 0, '1', '1', 3060001, '주재료'),
('195455', 1, '12', '12', 3060001, '주재료'),
('195455', 0, '감자', '1개', 3060001, '주재료'),
('195455', 8, '당근', '1개', 3060002, '부재료'),
('195455', 9, '당근', '1개', 3060003, '양념'),
('195455', 31, '당근', '1개', 3060001, '주재료'),
('195455', 0, '감자', '1개', 3060001, '주재료'),
('195455', 8, '당근', '1개', 3060002, '부재료'),
('195455', 9, '당근', '1개', 3060003, '양념'),
('195455', 31, '당근', '1개', 3060001, '주재료'),
('195455', 0, 'ㄹㄴㅇㄹㄴㅇ', 'ㄹㅇㄴㄹㅇㄴ', 3060001, '주재료'),
('195455', 1, 'ㄹㄴㅇㄹㄴㅇ', 'ㄹㅇㄴㄹㅇㄴ', 3060001, '주재료'),
('195455', 2, 'ㄹㄴㅇㄹㄴㅇ', 'ㄹㅇㄴㄹㅇㄴ', 3060001, '주재료'),
('195455', 0, '딸기', '2개', 3060001, '주재료'),
('195455', 1, '생크림', '500ml', 3060001, '주재료'),
('195455', 2, '사과', '2개', 3060002, '부재료'),
('195457', 0, 'qwe', '1', 3060001, '주재료'),
('195457', 0, 'ㅂㅈㄷ', '3', 3060001, '주재료');

-- --------------------------------------------------------

--
-- 테이블 구조 `tobuyingre`
--

DROP TABLE IF EXISTS `tobuyingre`;
CREATE TABLE IF NOT EXISTS `tobuyingre` (
  `recipe_num` int(10) UNSIGNED NOT NULL,
  `category` varchar(30) NOT NULL DEFAULT '',
  `i_name` varchar(30) NOT NULL,
  `quantity` char(30) NOT NULL,
  PRIMARY KEY (`recipe_num`,`category`,`i_name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 테이블의 덤프 데이터 `tobuyingre`
--

INSERT INTO `tobuyingre` (`recipe_num`, `category`, `i_name`, `quantity`) VALUES
(1, 'main', '쌀', '4컵'),
(1, 'main', '청포묵', '1/2모'),
(1, 'main', '콩나물', '20g'),
(1, 'sub', '국간장', '약간'),
(1, 'main', '계란', '1개'),
(1, 'main', '양지머리', '100g'),
(1, 'main', '미나리', '20g'),
(1, 'sub', '다진마늘', '약간'),
(2, '주재료', '멥쌀', '1컵'),
(0, '개인입력', 'asdf', 'fds');

-- --------------------------------------------------------

--
-- 테이블 구조 `userrecipe`
--

DROP TABLE IF EXISTS `userrecipe`;
CREATE TABLE IF NOT EXISTS `userrecipe` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `summary` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `time` varchar(255) NOT NULL,
  `difficulty` varchar(255) NOT NULL,
  `cooking_url` varchar(255) NOT NULL,
  UNIQUE KEY `name` (`name`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

--
-- 테이블의 덤프 데이터 `userrecipe`
--

INSERT INTO `userrecipe` (`id`, `name`, `summary`, `type`, `time`, `difficulty`, `cooking_url`) VALUES
('195455', '123', '123', '123', '123', '초보환영', '195455.jpg'),
('195456', 'asdf', 'qwe', 'ewq', '123', '초보환영', '195456.jpg');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
