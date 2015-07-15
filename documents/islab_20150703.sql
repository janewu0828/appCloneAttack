-- phpMyAdmin SQL Dump
-- version 4.0.4
-- http://www.phpmyadmin.net
--
-- 主機: localhost
-- 產生日期: 2015 年 07 月 04 日 05:10
-- 伺服器版本: 5.6.12-log
-- PHP 版本: 5.4.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- 資料庫: `islab`
--
CREATE DATABASE IF NOT EXISTS `islab` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `islab`;

-- --------------------------------------------------------

--
-- 表的結構 `app`
--

CREATE TABLE IF NOT EXISTS `app` (
  `app_restrict` int(255) DEFAULT NULL,
  `app_id` varchar(255) NOT NULL,
  `app_id2` varchar(255) NOT NULL,
  `note` text NOT NULL,
  PRIMARY KEY (`app_id`,`app_id2`),
  KEY `app_id2` (`app_id2`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- 轉存資料表中的資料 `app`
--

INSERT INTO `app` (`app_restrict`, `app_id`, `app_id2`, `note`) VALUES
(5, '090303012E1CCBF9B78E24775421257B65D83D55622EB20024D8779C1E0194A9', '133d2ce1b18ad7ace533a1a194d8bfe23251b0ae', 'Mind reading'),
(5, '1D4F48188E233EFBF0DEE7491098CD7BAB76BC76C258A26D482236F3D9FE3774', '7d070d811e4fe1167622cbcc03e3cebc73347242', 'Guess A of hearts'),
(5, '5c804575dcbadefc2fe5010b6e191710401c7e9864bcb67883db6c59dfcac623', '', ''),
(5, '721c9ab444bd2d84396a0c40ea7bb9396290c253cfebc4ae8fe30c60cd3071aa', '', ''),
(5, '87DB5C140ED3FADE9079DC90B6D1A0E15DBB35510784B53ECBFFA8398278EC3D', '827ad617dc3bb9e96f2f251550545cbcf3601568', 'Turn Left, Turn Right'),
(5, '982BEF2ED36180A2969B0179DFB8CDE674D2F1E79E7D311360B0D8455B6A89B1', 'd976b367ace10ce1960f06f3f0baa164501595c3', 'SD Card Info'),
(5, 'A472273F1FEF44AD7D642AA5409983231616FE6A523406F4A3A451831DA76DF9', 'efac62a612ce10800608f721fc3a02c417e53699', 'email'),
(5, 'ECA6AD143F034B03B3F6400CB73203177E2370B7740CC8FAA2AB895839207C05', 'ce33917a2639757472da33abf9a999e462addb04', 'Battery Info');

-- --------------------------------------------------------

--
-- 表的結構 `apps`
--

CREATE TABLE IF NOT EXISTS `apps` (
  `idapps` int(11) NOT NULL AUTO_INCREMENT,
  `idcat2` int(11) NOT NULL,
  `userid` varchar(255) NOT NULL,
  `name` varchar(45) CHARACTER SET big5 DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `times` int(11) DEFAULT NULL,
  `ver` varchar(20) DEFAULT NULL,
  `vercode` int(11) DEFAULT NULL,
  `apkid` varchar(100) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `sha1sum` varchar(255) DEFAULT NULL,
  `dwn` int(11) DEFAULT NULL,
  `sz` int(11) DEFAULT NULL,
  `minSdk` int(11) DEFAULT NULL,
  `minScreen` varchar(45) DEFAULT NULL,
  `cmt` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `jar` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idapps`),
  KEY `FK_UPLOADED_BY` (`userid`),
  KEY `FK_BELONGS_TO` (`idcat2`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=37 ;

--
-- 轉存資料表中的資料 `apps`
--

INSERT INTO `apps` (`idapps`, `idcat2`, `userid`, `name`, `path`, `times`, `ver`, `vercode`, `apkid`, `icon`, `date`, `sha1sum`, `dwn`, `sz`, `minSdk`, `minScreen`, `cmt`, `jar`, `created_at`, `updated_at`) VALUES
(24, 35, 'aaa', 'Mind reading', 'apks/Mind_reading2_1400039766.apk', 2, '1.0', 1, 'irdc.ex04_25', 'icons/57d12019b31b56322c12a2c5391b4a9c2c6c919a_1400039766.png', '2014-05-14', '266683f295fbc870d5a99e9dbe62d7590744e1cc', 0, 125, 8, 'small', '心電感應', 'output041122.jar', '2014-05-14 03:56:06', '2014-09-02 09:02:13'),
(25, 15, 'aaa', 'SD Card Info', 'apks/SD_Card_Info2_1400039937.apk', 2, '1.0.0', 1, 'irdc.ex06_08', 'icons/57d12019b31b56322c12a2c5391b4a9c2c6c919a_1400039937.png', '2014-05-14', '2188f2db03e90d826be9c122d524a2d3d1acd7ea', 0, 93, 10, 'small', '記憶卡容量', 'output0414.jar', '2014-05-14 03:58:57', '2014-09-02 09:04:13'),
(26, 35, 'aaa', 'Turn Left, Turn Right', 'apks/Turn_Left_Turn_Right2_1400040006.apk', 2, '1.0', 1, 'irdc.ex04_24', 'icons/57d12019b31b56322c12a2c5391b4a9c2c6c919a_1400040006.png', '2014-05-14', '30c8157f479721e4c709a8738130548ff0ba3f75', 0, 109, 10, 'small', '向左轉，向右轉', 'output0420.jar', '2014-05-14 04:00:06', '2014-09-02 09:02:02'),
(27, 15, 'aaa', 'Battery Info', 'apks/Battery_Info2_1402549871.apk', 2, '1.0.0', 1, 'irdc.ex06_02', 'icons/57d12019b31b56322c12a2c5391b4a9c2c6c919a_1402549871.png', '2014-06-12', '278fc0cbf8acb5dfdbc7b76b3888059d6e2e6ed9', 0, 108, 10, 'small', '電池計量', 'output0510.jar', '2014-06-12 05:11:12', '2014-09-02 09:03:29'),
(29, 2, 'aaa', 'Send Email', 'apks/Email_1405062861.apk', 2, '1.0.0', 1, 'irdc.EX05_04', 'icons/57d12019b31b56322c12a2c5391b4a9c2c6c919a_1405062861.png', '2014-07-11', 'f413785c8bbf769c575a419cdc660db130da7694', 0, 52, 10, 'small', '電子郵件小幫手', 'outputEmail20140701.jar', '2014-07-11 07:14:21', '2014-08-15 06:18:27'),
(36, 36, 'aaa', 'Guess A of hearts', 'apks/Guess_A_of_hearts2_1409665468.apk', 3, '1.0.0', 1, 'irdc.ex04_16', 'icons/57d12019b31b56322c12a2c5391b4a9c2c6c919a_1409665468.png', '2014-09-02', '2e869b60d754ed9f8210e32be83b1c614a2c331c', 0, 104, 8, 'small', '猜猜紅心A', 'output0408_2.jar', '2014-09-02 13:44:28', '2014-09-02 05:44:28');

-- --------------------------------------------------------

--
-- 表的結構 `first_categories`
--

CREATE TABLE IF NOT EXISTS `first_categories` (
  `IDCAT` int(11) NOT NULL AUTO_INCREMENT,
  `CATEGORIES` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`IDCAT`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- 轉存資料表中的資料 `first_categories`
--

INSERT INTO `first_categories` (`IDCAT`, `CATEGORIES`) VALUES
(1, 'App'),
(2, 'Games');

-- --------------------------------------------------------

--
-- 表的結構 `member`
--

CREATE TABLE IF NOT EXISTS `member` (
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `androidid` varchar(255) DEFAULT NULL,
  `deviceid` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`username`),
  UNIQUE KEY `username` (`username`),
  KEY `username_2` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- 轉存資料表中的資料 `member`
--

INSERT INTO `member` (`username`, `password`, `androidid`, `deviceid`, `email`, `name`) VALUES
('123', '13955235245b2497399d7a93245b2497399d7a93', '354435050331560\r\n354435050331560\r\n', NULL, '123@gmail.com', '123'),
('aaa', '5cb138284d431abd6a053a56625ec088bfb88912', '356619053248081', '40a81d22-76b7-3f53-9444-77cc72a7c97d', 'example@railstutorial.org', 'AAA BBBB'),
('Alice', 'c6922b6ba9e0939583f973bc1682493351ad4fe8', '357869050150827', NULL, 'ggg@gmail.com', 'Alice chen'),
('bbb', '5cb138284d431abd6a053a56625ec088bfb88912', '355921042314748', '', NULL, NULL),
('ccc', 'f36b4825e5db2cf7dd2d2593b3f5c24c0311d8b2', '355921042314748', '', NULL, NULL),
('eee', '5cb138284d431abd6a053a56625ec088bfb88912', '000000000000000', '', NULL, NULL),
('gablooge', 'b7584429f8faa541397e86336d882064677117a5', NULL, NULL, 'gablooge@gmail.com', 'Samsul Hadi'),
('projecttwo', 'projecttwo', '353528060638999', '6e0b599d-7e09-4eb0-8491-b8943192e105', NULL, NULL),
('projecttwotwo', 'projecttwotwo', '000000000000000', 'f18c924d-e59f-3a37-a049-6a032762415d', NULL, NULL),
('sam', '123', '359616042536177', '5cf8d63d-600d-3de8-8655-308d3ea34962', NULL, NULL),
('sub1', '48BA2D64D76ED2B86AEC79263F599AA3484C4789', '353538055226213', NULL, NULL, NULL),
('test', 'A94A8FE5CCB19BA61C4C0873D391E987982FBBD3', '356619053257538', 'e5751cf1-4b33-3b85-947c-00c03b662d83', NULL, NULL),
('tnt', '098f6bcd4621d373cade4e832627b4f6', '358094051965284', NULL, 'abc@gmail.com', 'Togu'),
('zzz', '984ff6ee7c78078d4cb1ca08255303fb8741d986', NULL, NULL, 'zzz@gmail.com', 'zzz');

-- --------------------------------------------------------

--
-- 表的結構 `purchase`
--

CREATE TABLE IF NOT EXISTS `purchase` (
  `p_id` int(8) NOT NULL AUTO_INCREMENT,
  `app_id` varchar(255) DEFAULT NULL,
  `app_id2` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `note` text NOT NULL,
  `times` int(255) DEFAULT NULL,
  `total_transfer_times` int(255) DEFAULT NULL,
  PRIMARY KEY (`p_id`),
  KEY `fk_purchase_username` (`username`),
  KEY `app_id` (`app_id`),
  KEY `app_id_2` (`app_id`),
  KEY `app_id2` (`app_id2`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=23 ;

--
-- 轉存資料表中的資料 `purchase`
--

INSERT INTO `purchase` (`p_id`, `app_id`, `app_id2`, `username`, `note`, `times`, `total_transfer_times`) VALUES
(1, '721c9ab444bd2d84396a0c40ea7bb9396290c253cfebc4ae8fe30c60cd3071aa', '7d070d811e4fe1167622cbcc03e3cebc73347242', 'sam', '', NULL, NULL),
(5, '1D4F48188E233EFBF0DEE7491098CD7BAB76BC76C258A26D482236F3D9FE3774', '7d070d811e4fe1167622cbcc03e3cebc73347242', 'test', 'Guess A of hearts', 19, 3),
(6, '982BEF2ED36180A2969B0179DFB8CDE674D2F1E79E7D311360B0D8455B6A89B1', '827ad617dc3bb9e96f2f251550545cbcf3601568', 'aaa', 'Turn Left, Turn Right', NULL, NULL),
(7, 'ECA6AD143F034B03B3F6400CB73203177E2370B7740CC8FAA2AB895839207C05', 'ce33917a2639757472da33abf9a999e462addb04', 'aaa', 'Battery Info', NULL, NULL),
(8, '982BEF2ED36180A2969B0179DFB8CDE674D2F1E79E7D311360B0D8455B6A89B1', 'd976b367ace10ce1960f06f3f0baa164501595c3', 'aaa', 'SD Card Info', NULL, NULL),
(9, '090303012E1CCBF9B78E24775421257B65D83D55622EB20024D8779C1E0194A9', '133d2ce1b18ad7ace533a1a194d8bfe23251b0ae', 'test', 'Mind reading', NULL, NULL),
(10, '1D4F48188E233EFBF0DEE7491098CD7BAB76BC76C258A26D482236F3D9FE3774', '7d070d811e4fe1167622cbcc03e3cebc73347242', 'sam', 'Guess A of hearts', 0, 2),
(11, '982BEF2ED36180A2969B0179DFB8CDE674D2F1E79E7D311360B0D8455B6A89B1', 'd976b367ace10ce1960f06f3f0baa164501595c3', 'sam', 'SD Card', NULL, NULL),
(12, '1D4F48188E233EFBF0DEE7491098CD7BAB76BC76C258A26D482236F3D9FE3774', '7d070d811e4fe1167622cbcc03e3cebc73347242', 'sam', 'Guess A of hearts', NULL, NULL),
(13, '721c9ab444bd2d84396a0c40ea7bb9396290c253cfebc4ae8fe30c60cd3071aa', NULL, 'sam', '', NULL, NULL),
(15, '1D4F48188E233EFBF0DEE7491098CD7BAB76BC76C258A26D482236F3D9FE3774', '7d070d811e4fe1167622cbcc03e3cebc73347242', 'sam', 'Guess A of hearts', NULL, NULL),
(17, '982BEF2ED36180A2969B0179DFB8CDE674D2F1E79E7D311360B0D8455B6A89B1', '827ad617dc3bb9e96f2f251550545cbcf3601568', 'sam', 'Turn Left, Turn Right', NULL, NULL),
(18, '090303012E1CCBF9B78E24775421257B65D83D55622EB20024D8779C1E0194A9', '133d2ce1b18ad7ace533a1a194d8bfe23251b0ae', 'sam', 'Mind reading', NULL, NULL),
(19, '1D4F48188E233EFBF0DEE7491098CD7BAB76BC76C258A26D482236F3D9FE3774', NULL, 'sam', '', NULL, NULL),
(20, '1D4F48188E233EFBF0DEE7491098CD7BAB76BC76C258A26D482236F3D9FE3774', NULL, 'sam', '', 1, 2),
(21, 'A472273F1FEF44AD7D642AA5409983231616FE6A523406F4A3A451831DA76DF9', 'efac62a612ce10800608f721fc3a02c417e53699', 'aaa', 'email', NULL, NULL),
(22, '1D4F48188E233EFBF0DEE7491098CD7BAB76BC76C258A26D482236F3D9FE3774', '7d070d811e4fe1167622cbcc03e3cebc73347242', 'sub1', 'new add', 0, 2);

-- --------------------------------------------------------

--
-- 表的結構 `schema_migrations`
--

CREATE TABLE IF NOT EXISTS `schema_migrations` (
  `version` varchar(255) NOT NULL,
  UNIQUE KEY `unique_schema_migrations` (`version`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- 轉存資料表中的資料 `schema_migrations`
--

INSERT INTO `schema_migrations` (`version`) VALUES
('20130924135907'),
('20130924141840'),
('20130924155847');

-- --------------------------------------------------------

--
-- 表的結構 `second_categories`
--

CREATE TABLE IF NOT EXISTS `second_categories` (
  `IDCAT2` int(11) NOT NULL AUTO_INCREMENT,
  `IDCAT` int(11) DEFAULT NULL,
  `CATEGORIES2` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`IDCAT2`),
  KEY `FK_DIVIDED_INTO` (`IDCAT`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=40 ;

--
-- 轉存資料表中的資料 `second_categories`
--

INSERT INTO `second_categories` (`IDCAT2`, `IDCAT`, `CATEGORIES2`) VALUES
(1, 1, 'Comics'),
(2, 1, 'Communication'),
(3, 1, 'Entertainment'),
(4, 1, 'Finance'),
(5, 1, 'Health'),
(6, 1, 'Lifestyle'),
(7, 1, 'Multimedia'),
(8, 1, 'News & Weather'),
(9, 1, 'Productivity'),
(10, 1, 'Reference'),
(11, 1, 'Shopping'),
(12, 1, 'Social'),
(13, 1, 'Sports'),
(14, 1, 'Themes'),
(15, 1, 'Tools'),
(16, 1, 'Travel'),
(17, 1, 'Demo'),
(18, 1, 'Software Libraries'),
(19, 1, 'Weather'),
(20, 1, 'Travel & Local'),
(21, 1, 'Transportation'),
(22, 1, 'Medical'),
(23, 1, 'Libraries & Demo'),
(24, 1, 'Transport'),
(25, 1, 'News & Magazines'),
(26, 1, 'Music & Audio'),
(27, 1, 'Photography'),
(28, 1, 'Personalization'),
(29, 1, 'Books & Reference'),
(30, 1, 'Health & Fitness'),
(31, 1, 'Media & Video'),
(32, 1, 'Education'),
(33, 1, 'Business'),
(34, 2, 'Arcade & Action'),
(35, 2, 'Brain & Puzzle'),
(36, 2, 'Cards & Casino'),
(37, 2, '益智'),
(38, 2, 'Sports Games'),
(39, 2, 'Racing');

-- --------------------------------------------------------

--
-- 表的結構 `time_restrict`
--

CREATE TABLE IF NOT EXISTS `time_restrict` (
  `time_restrict_id` int(8) NOT NULL,
  `time_type_id` int(8) NOT NULL,
  `start_days` int(8) DEFAULT NULL,
  `end_days` int(8) DEFAULT NULL,
  PRIMARY KEY (`time_restrict_id`),
  KEY `time_type_id` (`time_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- 轉存資料表中的資料 `time_restrict`
--

INSERT INTO `time_restrict` (`time_restrict_id`, `time_type_id`, `start_days`, `end_days`) VALUES
(1, 0, 10, NULL);

-- --------------------------------------------------------

--
-- 表的結構 `time_type`
--

CREATE TABLE IF NOT EXISTS `time_type` (
  `time_type_id` int(8) NOT NULL,
  `time_type_name` varchar(254) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`time_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- 轉存資料表中的資料 `time_type`
--

INSERT INTO `time_type` (`time_type_id`, `time_type_name`) VALUES
(0, 'time_after'),
(1, 'time_before'),
(2, 'time_between');

-- --------------------------------------------------------

--
-- 表的結構 `transfer`
--

CREATE TABLE IF NOT EXISTS `transfer` (
  `p_id` int(8) NOT NULL,
  `app_id` varchar(255) NOT NULL,
  `deviceid` varchar(255) NOT NULL,
  `pre_username_id` varchar(255) NOT NULL,
  `username_id` varchar(255) NOT NULL,
  `times_left` int(11) DEFAULT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `time_restrict_id` int(8) DEFAULT NULL,
  KEY `app_id` (`app_id`),
  KEY `pre_username_id` (`pre_username_id`),
  KEY `p_id` (`p_id`),
  KEY `username_id` (`username_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

--
-- 轉存資料表中的資料 `transfer`
--

INSERT INTO `transfer` (`p_id`, `app_id`, `deviceid`, `pre_username_id`, `username_id`, `times_left`, `time`, `time_restrict_id`) VALUES
(1, '721c9ab444bd2d84396a0c40ea7bb9396290c253cfebc4ae8fe30c60cd3071aa', '5cf8d63d-600d-3de8-8655-308d3ea34962', 'aaa', 'sam', 2, '2014-08-15 06:17:08', NULL),
(1, '721c9ab444bd2d84396a0c40ea7bb9396290c253cfebc4ae8fe30c60cd3071aa', '5cf8d63d-600d-3de8-8655-308d3ea34962', 'test', 'sam', 2, '2014-08-15 06:17:08', NULL),
(1, '721c9ab444bd2d84396a0c40ea7bb9396290c253cfebc4ae8fe30c60cd3071aa', '5cf8d63d-600d-3de8-8655-308d3ea34962', 'aaa', 'sam', 2, '2014-08-15 06:17:09', NULL),
(5, '1D4F48188E233EFBF0DEE7491098CD7BAB76BC76C258A26D482236F3D9FE3774', '5cf8d63d-600d-3de8-8655-308d3ea34962', 'test', 'sam', 2, '2014-08-15 06:17:09', NULL),
(5, '1D4F48188E233EFBF0DEE7491098CD7BAB76BC76C258A26D482236F3D9FE3774', '5cf8d63d-600d-3de8-8655-308d3ea34962', 'test', 'sam', 2, '2014-08-15 06:17:11', NULL),
(5, '1D4F48188E233EFBF0DEE7491098CD7BAB76BC76C258A26D482236F3D9FE3774', '5cf8d63d-600d-3de8-8655-308d3ea34962', 'test', 'sam', 2, '2014-08-15 06:17:13', NULL),
(1, '721c9ab444bd2d84396a0c40ea7bb9396290c253cfebc4ae8fe30c60cd3071aa', '5cf8d63d-600d-3de8-8655-308d3ea34962', 'test', 'sam', 2, '2014-08-15 06:17:13', NULL),
(12, '1D4F48188E233EFBF0DEE7491098CD7BAB76BC76C258A26D482236F3D9FE3774', '5cf8d63d-600d-3de8-8655-308d3ea34962', 'test', 'sam', 2, '2014-08-15 06:17:14', NULL),
(9, '090303012E1CCBF9B78E24775421257B65D83D55622EB20024D8779C1E0194A9', '5cf8d63d-600d-3de8-8655-308d3ea34962', 'test', 'sam', 2, '2014-08-15 06:17:14', NULL),
(12, '1D4F48188E233EFBF0DEE7491098CD7BAB76BC76C258A26D482236F3D9FE3774', '5cf8d63d-600d-3de8-8655-308d3ea34962', 'test', 'sam', 2, '2014-08-15 06:17:14', NULL),
(12, '1D4F48188E233EFBF0DEE7491098CD7BAB76BC76C258A26D482236F3D9FE3774', '5cf8d63d-600d-3de8-8655-308d3ea34962', 'aaa', 'sam', 2, '2014-08-15 06:17:15', NULL),
(12, '1D4F48188E233EFBF0DEE7491098CD7BAB76BC76C258A26D482236F3D9FE3774', '5cf8d63d-600d-3de8-8655-308d3ea34962', 'aaa', 'sam', 2, '2014-08-15 06:17:15', NULL),
(12, '1D4F48188E233EFBF0DEE7491098CD7BAB76BC76C258A26D482236F3D9FE3774', '5cf8d63d-600d-3de8-8655-308d3ea34962', 'test', 'sam', 2, '2014-08-15 06:17:16', NULL),
(12, '1D4F48188E233EFBF0DEE7491098CD7BAB76BC76C258A26D482236F3D9FE3774', '5cf8d63d-600d-3de8-8655-308d3ea34962', 'aaa', 'sam', 2, '2014-08-15 06:17:16', NULL),
(12, '1D4F48188E233EFBF0DEE7491098CD7BAB76BC76C258A26D482236F3D9FE3774', '5cf8d63d-600d-3de8-8655-308d3ea34962', 'test', 'sam', 2, '2014-08-15 06:17:17', NULL),
(12, '1D4F48188E233EFBF0DEE7491098CD7BAB76BC76C258A26D482236F3D9FE3774', '5cf8d63d-600d-3de8-8655-308d3ea34962', 'aaa', 'sam', 2, '2014-08-15 06:17:23', NULL),
(5, '1D4F48188E233EFBF0DEE7491098CD7BAB76BC76C258A26D482236F3D9FE3774', '5cf8d63d-600d-3de8-8655-308d3ea34962', 'aaa', 'sam', 2, '2014-08-15 06:17:25', NULL),
(5, '1D4F48188E233EFBF0DEE7491098CD7BAB76BC76C258A26D482236F3D9FE3774', 'e5751cf1-4b33-3b85-947c-00c03b662d83', 'test', 'sam', 2, '2014-08-15 06:17:26', NULL),
(5, '1D4F48188E233EFBF0DEE7491098CD7BAB76BC76C258A26D482236F3D9FE3774', 'e5751cf1-4b33-3b85-947c-00c03b662d83', 'aaa', 'sam', 2, '2014-08-15 06:17:26', NULL),
(5, '1D4F48188E233EFBF0DEE7491098CD7BAB76BC76C258A26D482236F3D9FE3774', 'e5751cf1-4b33-3b85-947c-00c03b662d83', 'test', 'sam', 2, '2014-08-15 06:17:26', NULL),
(5, '1D4F48188E233EFBF0DEE7491098CD7BAB76BC76C258A26D482236F3D9FE3774', 'e5751cf1-4b33-3b85-947c-00c03b662d83', 'aaa', 'sam', 2, '2014-08-15 06:17:26', NULL),
(5, '1D4F48188E233EFBF0DEE7491098CD7BAB76BC76C258A26D482236F3D9FE3774', 'e5751cf1-4b33-3b85-947c-00c03b662d83', 'test', 'sam', 2, '2014-08-15 06:17:30', NULL),
(5, '1D4F48188E233EFBF0DEE7491098CD7BAB76BC76C258A26D482236F3D9FE3774', 'e5751cf1-4b33-3b85-947c-00c03b662d83', 'aaa', 'sam', 2, '2014-08-15 06:17:34', NULL),
(5, '1D4F48188E233EFBF0DEE7491098CD7BAB76BC76C258A26D482236F3D9FE3774', 'e5751cf1-4b33-3b85-947c-00c03b662d83', 'test', 'sam', 2, '2014-08-15 06:17:35', NULL),
(5, '1D4F48188E233EFBF0DEE7491098CD7BAB76BC76C258A26D482236F3D9FE3774', 'e5751cf1-4b33-3b85-947c-00c03b662d83', 'aaa', 'sam', 2, '2014-08-15 06:17:35', NULL),
(5, '1D4F48188E233EFBF0DEE7491098CD7BAB76BC76C258A26D482236F3D9FE3774', 'e5751cf1-4b33-3b85-947c-00c03b662d83', 'aaa', 'sam', 2, '2014-08-15 06:17:35', NULL),
(5, '1D4F48188E233EFBF0DEE7491098CD7BAB76BC76C258A26D482236F3D9FE3774', 'e5751cf1-4b33-3b85-947c-00c03b662d83', 'test', 'sam', 2, '2014-08-15 06:17:35', NULL),
(5, '1D4F48188E233EFBF0DEE7491098CD7BAB76BC76C258A26D482236F3D9FE3774', 'e5751cf1-4b33-3b85-947c-00c03b662d83', 'aaa', 'sam', 2, '2014-08-15 06:17:36', NULL),
(5, '1D4F48188E233EFBF0DEE7491098CD7BAB76BC76C258A26D482236F3D9FE3774', 'e5751cf1-4b33-3b85-947c-00c03b662d83', 'test', 'sam', 2, '2014-08-15 06:17:36', NULL),
(5, '1D4F48188E233EFBF0DEE7491098CD7BAB76BC76C258A26D482236F3D9FE3774', 'e5751cf1-4b33-3b85-947c-00c03b662d83', 'aaa', 'sam', 2, '2014-08-15 06:17:36', NULL),
(5, '1D4F48188E233EFBF0DEE7491098CD7BAB76BC76C258A26D482236F3D9FE3774', 'e5751cf1-4b33-3b85-947c-00c03b662d83', 'aaa', 'sam', 2, '2014-08-15 06:17:36', NULL),
(5, '1D4F48188E233EFBF0DEE7491098CD7BAB76BC76C258A26D482236F3D9FE3774', 'e5751cf1-4b33-3b85-947c-00c03b662d83', 'aaa', 'sam', 2, '2014-08-15 06:17:37', NULL),
(5, '1D4F48188E233EFBF0DEE7491098CD7BAB76BC76C258A26D482236F3D9FE3774', 'e5751cf1-4b33-3b85-947c-00c03b662d83', 'test', 'sam', 2, '2014-08-15 06:17:37', NULL),
(5, '1D4F48188E233EFBF0DEE7491098CD7BAB76BC76C258A26D482236F3D9FE3774', 'e5751cf1-4b33-3b85-947c-00c03b662d83', 'aaa', 'sam', 2, '2014-08-15 06:17:37', NULL),
(5, '1D4F48188E233EFBF0DEE7491098CD7BAB76BC76C258A26D482236F3D9FE3774', 'e5751cf1-4b33-3b85-947c-00c03b662d83', 'aaa', 'test', 2, '2015-06-29 05:44:24', 1);

-- --------------------------------------------------------

--
-- 表的結構 `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password_digest` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `remember_token` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_users_on_remember_token` (`remember_token`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=105 ;

--
-- 轉存資料表中的資料 `users`
--

INSERT INTO `users` (`id`, `name`, `email`, `password_digest`, `created_at`, `updated_at`, `remember_token`) VALUES
(1, 'Samsul Hadi', 'samsulhadikediri@gmail.com', '$2a$10$QIYMGqfVL3HCMFwBb3t19ufIZK2CKvP7W/..XX6F6.Uf8OGssg8gq', '2013-09-24 14:54:47', '2014-04-13 13:11:48', 'rFnBC_WLFHFqKjFVhqdkgQ'),
(4, 'Example User', 'example@railstutorial.org', '$2a$10$F76SJnUKb3KhbvH9z.6A8uWXDCevR4QY3x4Xdl527behCO1V8302u', '2014-04-13 12:43:53', '2014-04-13 17:03:14', 'Kmt-k0uxEtcyD4uJuSZ2EQ'),
(5, 'Example 1', 'example-1@railstutorial.org', '$2a$10$oLD22fHU05AA.aLateKev.8eAXjDxsggWc81H35wAKDH8MtRLFBvO', '2014-04-13 16:34:08', '2014-04-13 16:34:08', '1sxm_KJbXyamC2t8nECvWQ'),
(6, 'Example 2', 'example-2@railstutorial.org', '$2a$10$95GQU.h9tblwBWuDH1mLz.W1OY91HsXp14f/3WbNS2BJ.nEcOeXEa', '2014-04-13 16:34:08', '2014-04-13 16:34:08', '1y0HmojiB93oHmh9tadALA'),
(7, 'Example 3', 'example-3@railstutorial.org', '$2a$10$j5x5QtApRfzA0ZVXYEsHs.8T59ispcnN0IqP7ehdf2D6.qxt1JtN.', '2014-04-13 16:34:08', '2014-04-13 16:34:08', 'dL6rx2aJBHJiQJ3grWwNvA'),
(8, 'Example 4', 'example-4@railstutorial.org', '$2a$10$vGn.eQ4utL3dUsHMDCj7rO2jrkj0qBN6UR84IWPUAYNhqKKqw1sFW', '2014-04-13 16:34:08', '2014-04-13 16:34:08', 'aURdc8hWK7XmgrFXX3YZMg'),
(9, 'Example 5', 'example-5@railstutorial.org', '$2a$10$zaY4YtQsAiMfsl.PR8ugWOkQApuO0eGxAFGKyKF0xNeuMTLNxfcIe', '2014-04-13 16:34:08', '2014-04-13 16:34:08', 'Cna-Pg9iR2GNzHTD8B6I2Q'),
(10, 'Example 6', 'example-6@railstutorial.org', '$2a$10$JJ16I3qVubWlGKjw7Ealruzy6NsniY.ZkY0eifuOvgIbDvDc/y9He', '2014-04-13 16:34:08', '2014-04-13 16:34:08', 'Q9S26BMf5znS5FbYYysVog'),
(11, 'Example 7', 'example-7@railstutorial.org', '$2a$10$DAB91NEAyzwZ4/aIZ4Kne.xYLH0GgJnQDYmiKQA9D1D/u4p/j1WAO', '2014-04-13 16:34:08', '2014-04-13 16:34:08', 'a1MIMfZQe3ArNoteJ2TqZQ'),
(12, 'Example 8', 'example-8@railstutorial.org', '$2a$10$4Je28kRiuEO6lAC/eoeZwe6p..AktIxeve9aZQCvTGEr8I8DOi7ze', '2014-04-13 16:34:08', '2014-04-13 16:34:08', 'nd-o-CHctZTo-UJfSkDg9Q'),
(13, 'Example 9', 'example-9@railstutorial.org', '$2a$10$cOe1O5WVvlYjLM/6eEzj3.Z01.D8cZrqe8J6j09FYsui9oxn2/iqy', '2014-04-13 16:34:09', '2014-04-13 16:34:09', '54xSaO1MMsrwsY8R4cHNRQ'),
(14, 'Example 10', 'example-10@railstutorial.org', '$2a$10$6wG27Tr0Y1IVbeLzTdQ/2OjZLgrlz6iR2OJta4Tjdbr6XDii8dYu.', '2014-04-13 16:34:09', '2014-04-13 16:34:09', 'Bd8v0BrnBuUN01BKbizP2A'),
(15, 'Example 11', 'example-11@railstutorial.org', '$2a$10$D.U9kbZyrDhrkAcTYMrDD.ZNFRjjRYkVNU6Kaxlo4YSWM.THYdu32', '2014-04-13 16:34:09', '2014-04-13 16:34:09', 'ndjbBp8HhpkTzkxh5v5yDg'),
(16, 'Example 12', 'example-12@railstutorial.org', '$2a$10$uMpBBYgnBllwpFTqWf3Wpukm148H7joWkxqoCw77LuwFQd88NZT/G', '2014-04-13 16:34:09', '2014-04-13 16:34:09', 'yFdA1NfgCAc10sqqapZiRw'),
(17, 'Example 13', 'example-13@railstutorial.org', '$2a$10$xCFGuQ7cfsejYb6NehcS1.lt0GWglOtUtJ5nL3dc4manqzqI8VR4W', '2014-04-13 16:34:09', '2014-04-13 16:34:09', 'OAyPuoAiMEAcfF2Lqh57QA'),
(18, 'Example 14', 'example-14@railstutorial.org', '$2a$10$dtrpJyK/j5QZ0c0l.jqkNeGfi8e2A.QzYjWgREerDU/VXcJZg1qVC', '2014-04-13 16:34:09', '2014-04-13 16:34:09', 'wTxIsQ9as2eCDW-q4ocHng'),
(19, 'Example 15', 'example-15@railstutorial.org', '$2a$10$ZHPEaGvL53uJYvgfGEM4Aei991HWOvJzkt/351MK3jRsgNuHUJ.ja', '2014-04-13 16:34:09', '2014-04-13 16:34:09', 'xMwOaMMVnKzRLt1UTJWZAw'),
(20, 'Example 16', 'example-16@railstutorial.org', '$2a$10$jR3lXB//RBDvS3XEUWUyFeZMub4tCnCoA04MqFKGEa1hbS/Er6LM2', '2014-04-13 16:34:09', '2014-04-13 16:34:09', 'yi9xxq8hYfNfKZdlIWuZHQ'),
(21, 'Example 17', 'example-17@railstutorial.org', '$2a$10$NZPiSEQvnyIVr3hmsmDUwOSshjYN/zx2bAWNTBMQOQ8C1K4mUAUn6', '2014-04-13 16:34:10', '2014-04-13 16:34:10', '9McIJOof0DGKlC-O-j6Ing'),
(22, 'Example 18', 'example-18@railstutorial.org', '$2a$10$oFfJUWePb/A6vKIDNnjWOOYTzTon7pm5uSz0aqPB.8S.KruRbjPXC', '2014-04-13 16:34:10', '2014-04-13 16:34:10', 'u1ebubvqIqE4rpn4IYN5iA'),
(23, 'Example 19', 'example-19@railstutorial.org', '$2a$10$JIdvNfDHBeWUpMVnv9Rz7.TB4HlLrP12i3vqcPa4mSNryatNNjb26', '2014-04-13 16:34:10', '2014-04-13 16:34:10', '0EQcrdkjOU3yj_1lJKrP_w'),
(24, 'Example 20', 'example-20@railstutorial.org', '$2a$10$HY0NLnub8usV2NZht7Gj1.VYzUnDfFGvCfNBDHh/X2MPAdm6fzc2K', '2014-04-13 16:34:10', '2014-04-13 16:34:10', 'xgjT70sqxcCOfYeP7OJ55Q'),
(25, 'Example 21', 'example-21@railstutorial.org', '$2a$10$ybwZc98N47YQr8l2gY.jWuSHr0I3kEes6rzKfUunlmfGyBAZj4Tx.', '2014-04-13 16:34:10', '2014-04-13 16:34:10', 'nADrC1PDtYUHkAx-eWChCA'),
(26, 'Example 22', 'example-22@railstutorial.org', '$2a$10$Q4Nq5EGFP3AtT.ZAF7MgX.hz5BJIbb3yLfjR6DL8vpBTE.wmsiPg6', '2014-04-13 16:34:10', '2014-04-13 16:34:10', '32WIzHMT7DkT4x5pAEOfkg'),
(27, 'Example 23', 'example-23@railstutorial.org', '$2a$10$PFzJs1JpGCPZq/5zMl8hW.Vyw4B4RyEmaQDuhaTGXrSTU0bJ5FqqS', '2014-04-13 16:34:10', '2014-04-13 16:34:10', 'VF58TVpTFCJSPh_Sbmn0qA'),
(28, 'Example 24', 'example-24@railstutorial.org', '$2a$10$AVaSJd8xuwV47a5YawpJk.2UJgWi.5lGMrQxEPE3S4ZypRi7kXQyq', '2014-04-13 16:34:10', '2014-04-13 16:34:10', 'EscyM9kN8uYKKGM3uPVJoQ'),
(29, 'Example 25', 'example-25@railstutorial.org', '$2a$10$wtYIFB1UWEXsPleSCze2oOwj8qP0FjEZqocdhophRQN5UIacs5FUG', '2014-04-13 16:34:10', '2014-04-13 16:34:10', 'kAM_wkLT2PeW3_fEeQMC0Q'),
(30, 'Example 26', 'example-26@railstutorial.org', '$2a$10$uzuViQh3.7l4/rUj3.Dat.sTOuV1BbmIsvHLQTOyaAmvw7pQDegA2', '2014-04-13 16:34:11', '2014-04-13 16:34:11', '6mdSJgDD0Z_w_5jh9zVilQ'),
(31, 'Example 27', 'example-27@railstutorial.org', '$2a$10$/n.cs6G.z/SOhhLXN1l5muFJYEyXjSLnAUsFf96oUf1JXAtWe4.xq', '2014-04-13 16:34:11', '2014-04-13 16:34:11', 'XjADnxfs9eJIiPY7tEcDww'),
(32, 'Example 28', 'example-28@railstutorial.org', '$2a$10$SJB0vLQ.2MHntBnPXeuCOuib5BZxCq4cLNal4.eQNC8f6DrXoJyoW', '2014-04-13 16:34:11', '2014-04-13 16:34:11', 'w9lZYAWrcMwKaGo3VKZYdQ'),
(33, 'Example 29', 'example-29@railstutorial.org', '$2a$10$BoYsQnAzFIrc2RPDTll8F.0LKqXblgV1a3yJP.MSc2L2CbWtuNgQK', '2014-04-13 16:34:11', '2014-04-13 16:34:11', '5-RyLilpbLMaGLV8TRcnew'),
(34, 'Example 30', 'example-30@railstutorial.org', '$2a$10$yNGsGygKB2mdhf0t0GHnVewIkPG.9yooeMPqnSQiGT2teYmIq/QV6', '2014-04-13 16:34:11', '2014-04-13 16:34:11', 'JSEJ3-Ok0kcM_VoQY7ERNA'),
(35, 'Example 31', 'example-31@railstutorial.org', '$2a$10$nZidFBnFaBbd/NwI3rXU/evEv0gkMthLoqfiuXDzWh0yuZa33r4C2', '2014-04-13 16:34:11', '2014-04-13 16:34:11', 'lNy6DMlH7h_YjD9NfHm8kA'),
(36, 'Example 32', 'example-32@railstutorial.org', '$2a$10$e7qKKB4.HBiOXKRYc/.4nur/Q9DjnMKgAY1drHNaHFJ549kXdvyw2', '2014-04-13 16:34:11', '2014-04-13 16:34:11', '4jdGvmJ_rBHoKjOcekKErA'),
(37, 'Example 33', 'example-33@railstutorial.org', '$2a$10$Yz9JOQ2NvgqY/u524s4ASeaIBCYKAJHtuFwWeAVyPtaFifsi.zrIC', '2014-04-13 16:34:11', '2014-04-13 16:34:11', 'F65HERlFNN4XUyqXO8jyYg'),
(38, 'Example 34', 'example-34@railstutorial.org', '$2a$10$BppggZmLOId.lLEVkv3Lo.M26dCPk.9OqM/BTi4PBFcN.GNkWKhpm', '2014-04-13 16:34:12', '2014-04-13 16:34:12', 'wOqr_QSc15zSQpRHjS9uoQ'),
(39, 'Example 35', 'example-35@railstutorial.org', '$2a$10$zZorY2wmKZ7HJ2Vm/8fcWOSBYQZWQBITglSvbUUK7bVyabumjQGOm', '2014-04-13 16:34:12', '2014-04-13 16:34:12', '5qUNOudF3llfNyZJm8rISg'),
(40, 'Example 36', 'example-36@railstutorial.org', '$2a$10$xthNfqk83XZJ5fwsPRLxVuCKTId7nNo7CFJfx59fIzbbgxtR.N5Wy', '2014-04-13 16:34:12', '2014-04-13 16:34:12', '1PTwUuHoex9Wo0OtY9vcjA'),
(41, 'Example 37', 'example-37@railstutorial.org', '$2a$10$pm5CiAosFcYvWalkjtYyFuR66zFpHwkvHE/USn0Wd07WNxy1MP7ai', '2014-04-13 16:34:12', '2014-04-13 16:34:12', 'SxEAGoSVS6HGBLBEbn6fPQ'),
(42, 'Example 38', 'example-38@railstutorial.org', '$2a$10$5EE.9uk93TV0TTA9vw.Fye1w2qsHG70AgOOHruQ760cY72XPec.x2', '2014-04-13 16:34:12', '2014-04-13 16:34:12', 'azTf4XUhlaSj6jjZ8vgLuA'),
(43, 'Example 39', 'example-39@railstutorial.org', '$2a$10$WUnANpJpb.q7S7EwYNfnXeWa7j/Ua4/72T3/SEXMQtTplIhwvdrxe', '2014-04-13 16:34:12', '2014-04-13 16:34:12', 'YxTSQB339Vt0rM0g720lmw'),
(44, 'Example 40', 'example-40@railstutorial.org', '$2a$10$a7S9nFxeEvXRcr2YaDJDq.Wl10NfIy55k4NrUjMGXCUlXuIn6BKZu', '2014-04-13 16:34:12', '2014-04-13 16:34:12', '_6cZvXSYJTQmvERVRPXyLg'),
(45, 'Example 41', 'example-41@railstutorial.org', '$2a$10$RZzdx/LC2e0xvmsxUhE32Oz2C4DsiNX/I/tl/jXQ.u1ySpp0x5VM6', '2014-04-13 16:34:12', '2014-04-13 16:34:12', 'XZ7JlHtIxY49gvHJ-M3MYA'),
(46, 'Example 42', 'example-42@railstutorial.org', '$2a$10$zG9HyqlFocSOmnEgBuwiiOP.aWM7F/Hj24nE/oPLHB4xZlRYMmhuS', '2014-04-13 16:34:12', '2014-04-13 16:34:12', 'KSmonewIkh6Bz5r4t_6lGw'),
(47, 'Example 43', 'example-43@railstutorial.org', '$2a$10$uBHB7PbPj98b0GIP.VNtr.6LrhDJerYk5cqNIkgaMzqk94cnjLifK', '2014-04-13 16:34:13', '2014-04-13 16:34:13', 'Q15_sSzMXdEU8KDYkwwFkQ'),
(48, 'Example 44', 'example-44@railstutorial.org', '$2a$10$NWTuJx/mUIcPQqGilew3K.hfe.suw1Ry1gIYghl76Tlm37FmbNy8K', '2014-04-13 16:34:13', '2014-04-13 16:34:13', '4eFONwF5m3Z5glVjGZdpRA'),
(49, 'Example 45', 'example-45@railstutorial.org', '$2a$10$hH2Pr2wvOZCrh6IRl3IuEOuCv2691hl1Cg1auVnOJqh7pUNF30MIe', '2014-04-13 16:34:13', '2014-04-13 16:34:13', 'RYGBXdLOuMEytW9m3GzV7w'),
(50, 'Example 46', 'example-46@railstutorial.org', '$2a$10$K8DUC6iyKzQwPXQrmwe4Teortny2/lbmwcgvAf4qBERFudMNBGPj.', '2014-04-13 16:34:13', '2014-04-13 16:34:13', '03-TIz65AZMn7LxZxfXdYg'),
(51, 'Example 47', 'example-47@railstutorial.org', '$2a$10$f3hvfuE3MwqCXtCrMlXKgOm/doEmD0SO7ypQ3kOmQYHQP2kake9jy', '2014-04-13 16:34:13', '2014-04-13 16:34:13', '3LvAFZQ3URPi5gHxQmaodA'),
(52, 'Example 48', 'example-48@railstutorial.org', '$2a$10$OPyDxpbL9HIbgqNfg4FC3O/Fkf/xtJgCwav7CiB/hP2FM6rHgZ3H.', '2014-04-13 16:34:13', '2014-04-13 16:34:13', 'rDSh1FsCLHlk3JAo4nf-rA'),
(53, 'Example 49', 'example-49@railstutorial.org', '$2a$10$tyR3n2OcxIC3brbYK0bfC.O8Hww/ENtExOV3/ZhKwWoRw47Yjy7Ya', '2014-04-13 16:34:13', '2014-04-13 16:34:13', 'PxxSA3SzklVGNQB6GBW5nA'),
(54, 'Example 50', 'example-50@railstutorial.org', '$2a$10$JAoQC/qbffjahXaawn91uuKNrIoJOzJKvlm8B7eCF8bxm3YbqI/Tq', '2014-04-13 16:34:13', '2014-04-13 16:34:13', 'CSg_RfP5fCRRSjXvrNdsNw'),
(55, 'Example 51', 'example-51@railstutorial.org', '$2a$10$tanBhBDI8PgcFpCOCxHqDeQfcSdbh4warIWMuIfs8zEBAxuuIsyMO', '2014-04-13 16:34:14', '2014-04-13 16:34:14', '1VYSKlcHTlP5ZAc6lKNBfQ'),
(56, 'Example 52', 'example-52@railstutorial.org', '$2a$10$p22oTT8ZFyVBnyyN.u.ne.PlcenwjODoxB7n9zTqum0ugHD67T9bi', '2014-04-13 16:34:14', '2014-04-13 16:34:14', 'Lo8cXvdMRXx2Em9iBrDe4w'),
(57, 'Example 53', 'example-53@railstutorial.org', '$2a$10$aUXAhf0py/jH6Y2Z3Q8aJudID7eRhdGbwFpqZKL.kpRM2z9h5Akem', '2014-04-13 16:34:14', '2014-04-13 16:34:14', '9iQi4ZEPLC8saMwL3o00cQ'),
(58, 'Example 54', 'example-54@railstutorial.org', '$2a$10$9Gdq0ITgOb0mReTIqTLdruc/9rZLEwGKX35Rtk2UftYnayQNVUwT6', '2014-04-13 16:34:14', '2014-04-13 16:34:14', 'R2OKMNxe6lNkMcwLe_UTjw'),
(59, 'Example 55', 'example-55@railstutorial.org', '$2a$10$XA5ZcpEyl6hVAvKeIIUD.utk0/b73w4Ttu/N4r.J7FycLK1X5Ibla', '2014-04-13 16:34:14', '2014-04-13 16:34:14', 'aEeeE179xu9VRxBS6x1AgQ'),
(60, 'Example 56', 'example-56@railstutorial.org', '$2a$10$b2byvpqi6Ynw1Q98mbt9MObth3FLJawLCqYX2tJnOBQZMVzLrIcuG', '2014-04-13 16:34:14', '2014-04-13 16:34:14', 'XwspkLuVbGQSN4T9-2KvMw'),
(61, 'Example 57', 'example-57@railstutorial.org', '$2a$10$B7/U9rgKizsRJ9RVoV4THuMA8GRHLLpI0S37OkHdWggALteEyqQqO', '2014-04-13 16:34:14', '2014-04-13 16:34:14', 'i1qShHSHwcnV5GbJqgEC1A'),
(62, 'Example 58', 'example-58@railstutorial.org', '$2a$10$/GfnRCK9/C95Z/Ze78IjB.ItILzV0IWiO2vTodn7ISITeXWYrQn1q', '2014-04-13 16:34:14', '2014-04-13 16:34:14', 'hh4UJ0Walp-H8Qh0FuwOSg'),
(63, 'Example 59', 'example-59@railstutorial.org', '$2a$10$99oPn5wSpWzu/RRCKHXSdO36DecuLV/FaRTrd62hfZ.A.FBymSWqe', '2014-04-13 16:34:14', '2014-04-13 16:34:14', 'BnukTEO-vc3hYRs-nAxNgw'),
(64, 'Example 60', 'example-60@railstutorial.org', '$2a$10$7yuEDdO3dxjJtjgQx26age2fTQ5ipgjLmlaGhXg/2S1Nc/p4Eknqm', '2014-04-13 16:34:15', '2014-04-13 16:34:15', '8yZkJSVfo3Vt0nCOulNTZQ'),
(65, 'Example 61', 'example-61@railstutorial.org', '$2a$10$AZunJ5fJnMkSv8SQFMCVDOGO2qyi99dCN7awcxWFrXSIAf05cbfjK', '2014-04-13 16:34:15', '2014-04-13 16:34:15', 'fH26ZO9dl-hpKInxJIwJ2Q'),
(66, 'Example 62', 'example-62@railstutorial.org', '$2a$10$2.qFbtEvTtUfycMCiNK8meya62r4d/O.WPXMtwn9SaCYRUYvKM0Jm', '2014-04-13 16:34:15', '2014-04-13 16:34:15', 'T5UMdWauiH6j5x2dbwAaxA'),
(67, 'Example 63', 'example-63@railstutorial.org', '$2a$10$iYUjK14YZnxX6dT7htkuduRMh1yictXC.CpFHqykcFaG2pQX2l57.', '2014-04-13 16:34:15', '2014-04-13 16:34:15', 'lZVHOp_uAeUw2hN2QC_6Tw'),
(68, 'Example 64', 'example-64@railstutorial.org', '$2a$10$mtyg4EYkaq681dhsZzZqWOiThZ.X2Ab9iCjab3HVBTgNUVTKj3Yk2', '2014-04-13 16:34:15', '2014-04-13 16:34:15', 'PEhBwgTG48S6K898LK2jOA'),
(69, 'Example 65', 'example-65@railstutorial.org', '$2a$10$I.U2qA5YoYq.9Le55LfyMeQwegaIKUQADFYM46ec3u9c3hXTKsxCu', '2014-04-13 16:34:15', '2014-04-13 16:34:15', '5RRx9aIOHQzaKw1ZfhgZqw'),
(70, 'Example 66', 'example-66@railstutorial.org', '$2a$10$sA7DrHn.4Pvsb8i2RVdgl.slMTa9ySieswdBff1e72LnysiaTCJr2', '2014-04-13 16:34:15', '2014-04-13 16:34:15', '65UOAu6J0eovlXkTJeJj4A'),
(71, 'Example 67', 'example-67@railstutorial.org', '$2a$10$e9dThxs8/FfE35doHcS0iO23Hw5Q3pQ3Dv594DDRracuEhv0Uht8m', '2014-04-13 16:34:15', '2014-04-13 16:34:15', '5V0RI93QIFZRe4trHpgNFw'),
(72, 'Example 68', 'example-68@railstutorial.org', '$2a$10$e/Tz2YX8k2QQwkpPNX5KneAEk8CC58nFT/EjxNl6sJUbQNB685W0C', '2014-04-13 16:34:15', '2014-04-13 16:34:15', 'EqpFMm-R4F_1J8xQZHooNw'),
(73, 'Example 69', 'example-69@railstutorial.org', '$2a$10$ppFDA1T/twTBstczzv0ZO.PhDxOfQ..m6CsvqOIu7RNk31C30LV2O', '2014-04-13 16:34:16', '2014-04-13 16:34:16', 'FdF4weFzxbdfIo3wb6kbsA'),
(74, 'Example 70', 'example-70@railstutorial.org', '$2a$10$F8Cerw68pKR./uwV3bkXiuZIwIqCspoHQN7vaYrbfcyHk9wMWMZCW', '2014-04-13 16:34:16', '2014-04-13 16:34:16', '3CuSIisXdk3zqT9AhQZ8oQ'),
(75, 'Example 71', 'example-71@railstutorial.org', '$2a$10$tbS8iRhbOEH6O7qCzh4WT.O68z5g0FV.pJhC1eMTx4BcBWFBn/.7a', '2014-04-13 16:34:16', '2014-04-13 16:34:16', 'OcFsFdbLc5VrjWnhSkdkaA'),
(76, 'Example 72', 'example-72@railstutorial.org', '$2a$10$QltkkLparxTJdUrAsA24LOyjVIYs8doR7BdUxUR4m2HTSbHueEB1e', '2014-04-13 16:34:16', '2014-04-13 16:34:16', 'vemRDdP8W3Rujr2jMTsFgQ'),
(77, 'Example 73', 'example-73@railstutorial.org', '$2a$10$pVL.aMXiuJ7cW0awDbPkdu3XBLDxiSANusv8M.bqvSow1kgAso5Za', '2014-04-13 16:34:16', '2014-04-13 16:34:16', 'awMeV-wXCuAfeOpfRfrMzg'),
(78, 'Example 74', 'example-74@railstutorial.org', '$2a$10$/vXdq4pBGiMcg26S9GVyn.yfmfv3eQaT8yrYhn0vmSSAH6rftZwra', '2014-04-13 16:34:16', '2014-04-13 16:34:16', 'q6Y10fjl32Ar4qhfhFVIIA'),
(79, 'Example 75', 'example-75@railstutorial.org', '$2a$10$w2Swj.LA7fJOI454RNfjIexV17VMUmx/bheMLEk6O.BT4sALmFwsy', '2014-04-13 16:34:16', '2014-04-13 16:34:16', 'GAHKhOGju45jonKIZgKPCg'),
(80, 'Example 76', 'example-76@railstutorial.org', '$2a$10$zK6NKcuAFHqjjsdx1wtyVOJwRfjhktrsuojOMxTV82kAgUso43vhy', '2014-04-13 16:34:16', '2014-04-13 16:34:16', 'aBSafONLMtf-57sB0dNRXQ'),
(81, 'Example 77', 'example-77@railstutorial.org', '$2a$10$NYzO3HfIZ3z2RqaUSgbVb.SrDNAyy.cCZTiuM8bKShipad27dKpsa', '2014-04-13 16:34:17', '2014-04-13 16:34:17', 'yaKYFEWO_K0AvD5CwghD4g'),
(82, 'Example 78', 'example-78@railstutorial.org', '$2a$10$uT4StsphIDxX5zaUQr.FgesI945JH9GdSneLhpZ7T.kWRu4woWXlu', '2014-04-13 16:34:17', '2014-04-13 16:34:17', 'dfi588w-oHYCnqeikJHTWg'),
(83, 'Example 79', 'example-79@railstutorial.org', '$2a$10$HomBZKEjx0rR0/ry9CBQN.Kuhliuck3lCo2bSgx.eNyeKBgdKttoe', '2014-04-13 16:34:17', '2014-04-13 16:34:17', 'Adzec6Rnj9md78_aN7TFqQ'),
(84, 'Example 80', 'example-80@railstutorial.org', '$2a$10$EkvbVbkk04Ln6Dayb.eOTOoY1t7XxR.Bb9NU9lnI/EW.he5UzsqxK', '2014-04-13 16:34:17', '2014-04-13 16:34:17', 'P5KQEfzg8mQoQ0u7NZFzQw'),
(85, 'Example 81', 'example-81@railstutorial.org', '$2a$10$.RIyUXvVS0bylNBPI2EUAO/qb2ACDI7BlwzX0kctJEapm7QJRrhYy', '2014-04-13 16:34:17', '2014-04-13 16:34:17', '7zrr-fHTk8DOY86t0WIDeA'),
(86, 'Example 82', 'example-82@railstutorial.org', '$2a$10$mtu6g3I2JowDhmTBoN6UVOTU3q3KFMkFSho8sz5Vyjm0iYKsUIUdG', '2014-04-13 16:34:17', '2014-04-13 16:34:17', 'JHLDIBdx-blGeBUlIbqtAQ'),
(87, 'Example 83', 'example-83@railstutorial.org', '$2a$10$dFSqmAmxVRIGvbv0swZDDuLmVcRnHUzGCSeqLBsVJgBisAxMbc6yO', '2014-04-13 16:34:17', '2014-04-13 16:34:17', 'zpE0F-gsiZjXJDgOv6XkAQ'),
(88, 'Example 84', 'example-84@railstutorial.org', '$2a$10$EWV.WHR/jN8dNifErFqg3O0PofFmEeuwMiwnt5FInNFNvfJD4DkC2', '2014-04-13 16:34:17', '2014-04-13 16:34:17', 'ln6aEYO2DE6Vbxc7osZBQw'),
(89, 'Example 85', 'example-85@railstutorial.org', '$2a$10$/fkaerszs8x3zFU0oJ8n5ehdj5H6Xk4C/3vpoHszt9Z7CFkDvriQW', '2014-04-13 16:34:17', '2014-04-13 16:34:17', 'rzPbH8erlNFXXk4SCHCrqA'),
(90, 'Example 86', 'example-86@railstutorial.org', '$2a$10$XK4.eam5c6k3H8M/VgCmRuy1qxPBDGYBO9PsfRgBso/apNaHXnYY.', '2014-04-13 16:34:18', '2014-04-13 16:34:18', '3iEFoCM1gdsPe2HVhhoACQ'),
(91, 'Example 87', 'example-87@railstutorial.org', '$2a$10$hQv1HsRrO9Snzj4XKcTs5uSKA.XOPEcUFIFoKPK6JGlUt5tP1tyru', '2014-04-13 16:34:18', '2014-04-13 16:34:18', 'G05XgLMpVymSNnWf3-OhXA'),
(92, 'Example 88', 'example-88@railstutorial.org', '$2a$10$zl5Y/a5zZZan6GLGClqcQ.FiiTz/1ITOu8sE.oKFUuK9z3ca0j61G', '2014-04-13 16:34:18', '2014-04-13 16:34:18', 'jovhSgVZW9meYSBZyHME9w'),
(93, 'Example 89', 'example-89@railstutorial.org', '$2a$10$OGYGEWtetJqFCy9aFf26PuTPHFN3L01vZo.epG0lFf8DCyh0eJv5K', '2014-04-13 16:34:18', '2014-04-13 16:34:18', 'j51Vo8iPwNtQyLZUT9nY0w'),
(94, 'Example 90', 'example-90@railstutorial.org', '$2a$10$VorFRrNZpq3qMFILTMTceOyxz/dhGcZc.bMyTFAFrvhXt.PG6Z3Ie', '2014-04-13 16:34:18', '2014-04-13 16:34:18', 'yBw29_0UYqZGkDMarZWu5w'),
(95, 'Example 91', 'example-91@railstutorial.org', '$2a$10$eYE2E4ZZJ5AuUsuibycdcuzTs1Egexx15ImPr7.IItmKUwroMvNCS', '2014-04-13 16:34:18', '2014-04-13 16:34:18', '96g0hWS_2NrLtd7G84hIAw'),
(96, 'Example 92', 'example-92@railstutorial.org', '$2a$10$rxx7uH3QjEncd.pLkvDElO9OuL2fgtCYkEvkUOsieZICtGtpNVKda', '2014-04-13 16:34:18', '2014-04-13 16:34:18', 'bBxXBiWlvWAq0BX9znxa9g'),
(97, 'Example 93', 'example-93@railstutorial.org', '$2a$10$sPHVK1C43isJS0x6omO5uOIJu.Z.Qf2d0aSLpCC.uHNyLAGdBmQ02', '2014-04-13 16:34:18', '2014-04-13 16:34:18', 'OD_r0qtJ0eT9sSu10f5InA'),
(98, 'Example 94', 'example-94@railstutorial.org', '$2a$10$MGj7J8oo3ZK/MWS.D9JXhuamOSRqKnreVd.Q8DtI8ggKeh3tN1a6u', '2014-04-13 16:34:19', '2014-04-13 16:34:19', 'Uan0nXd8jMY9KEAegZgtww'),
(99, 'Example 95', 'example-95@railstutorial.org', '$2a$10$zbk/Oe1RnXlfNbTdy6WQWO4Wquga1V51lHLdNf3EorgusoSZ5hcrW', '2014-04-13 16:34:19', '2014-04-13 16:34:19', 'gvK7WkV6pYyVvAdbEdUocw'),
(100, 'Example 96', 'example-96@railstutorial.org', '$2a$10$EemsYmS5cEHgxRhUiOUnEepAJzUMTOljxSYyPGzS.fE6D.SH14KK6', '2014-04-13 16:34:19', '2014-04-13 16:34:19', 'dmSPcf-pUTtrysESAakQaA'),
(101, 'Example 97', 'example-97@railstutorial.org', '$2a$10$2d5n5sA.2v4.gnxYHLEvxOOOWxvm1c5VrjreFWz4J.l/ENFvzMeti', '2014-04-13 16:34:19', '2014-04-13 16:34:19', 'OeXURbn-ayTzZjDZuJLagA'),
(102, 'Example 98', 'example-98@railstutorial.org', '$2a$10$1OOtolQwSrdOnE.vfZtI8e6VDk7Wldmq64KyIIbnOdPo3BR3TNqpu', '2014-04-13 16:34:19', '2014-04-13 16:34:19', 'ugj2jHc4Pv42gEJNBeiToQ'),
(103, 'Example 99', 'example-99@railstutorial.org', '$2a$10$f1Aej/Cj4mI7IVSCdPHXC.jN2.525Bleh7WXNmVMceAWaZkpdLNWK', '2014-04-13 16:34:19', '2014-04-13 16:34:19', 'yZr_ZZAB56PnDv3q4hinDQ'),
(104, '123', '123', '123', '2014-07-09 11:07:22', '2014-07-09 11:07:18', '123');

--
-- 匯出資料表的 Constraints
--

--
-- 資料表的 Constraints `apps`
--
ALTER TABLE `apps`
  ADD CONSTRAINT `FK_BELONGS_TO` FOREIGN KEY (`idcat2`) REFERENCES `second_categories` (`IDCAT2`),
  ADD CONSTRAINT `FK_UPLOADED_BY` FOREIGN KEY (`userid`) REFERENCES `member` (`username`);

--
-- 資料表的 Constraints `purchase`
--
ALTER TABLE `purchase`
  ADD CONSTRAINT `fk_purchase_app_id` FOREIGN KEY (`app_id`) REFERENCES `app` (`app_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_purchase_app_id2` FOREIGN KEY (`app_id2`) REFERENCES `app` (`app_id2`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_purchase_username` FOREIGN KEY (`username`) REFERENCES `member` (`username`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 資料表的 Constraints `second_categories`
--
ALTER TABLE `second_categories`
  ADD CONSTRAINT `FK_DIVIDED_INTO` FOREIGN KEY (`IDCAT`) REFERENCES `first_categories` (`IDCAT`);

--
-- 資料表的 Constraints `time_restrict`
--
ALTER TABLE `time_restrict`
  ADD CONSTRAINT `time_type_id` FOREIGN KEY (`time_type_id`) REFERENCES `time_type` (`time_type_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 資料表的 Constraints `transfer`
--
ALTER TABLE `transfer`
  ADD CONSTRAINT `transfer_ibfk_1` FOREIGN KEY (`app_id`) REFERENCES `app` (`app_id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `transfer_ibfk_2` FOREIGN KEY (`pre_username_id`) REFERENCES `member` (`username`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `transfer_ibfk_3` FOREIGN KEY (`p_id`) REFERENCES `purchase` (`p_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `transfer_ibfk_4` FOREIGN KEY (`username_id`) REFERENCES `purchase` (`username`) ON DELETE NO ACTION ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
