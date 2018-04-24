-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 2018-04-24 18:11:11
-- 服务器版本： 10.1.19-MariaDB
-- PHP Version: 5.6.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `teaching_platform`
--

-- --------------------------------------------------------

--
-- 表的结构 `class_info`
--

CREATE TABLE `class_info` (
  `class_id` int(11) UNSIGNED NOT NULL,
  `course_name` varchar(40) NOT NULL,
  `tea_id` bigint(20) NOT NULL,
  `tea_name` varchar(40) NOT NULL,
  `course_id` int(11) NOT NULL,
  `class_name` varchar(40) NOT NULL COMMENT '课程名字',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间 ',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `class_info`
--

INSERT INTO `class_info` (`class_id`, `course_name`, `tea_id`, `tea_name`, `course_id`, `class_name`, `create_time`, `update_time`) VALUES
(21, '数据3', 20141002426, 'tDiang', 21, '数据1', '2018-04-09 08:31:18', NULL),
(22, '数据3', 20141002426, 'tDiang', 21, '数据2班', '2018-04-09 08:31:43', NULL),
(23, 'JS', 20141002426, 'tDiang', 27, '1班', '2018-04-21 03:50:59', NULL),
(24, 'JS', 20141002426, 'tDiang', 27, ' 2班', '2018-04-21 03:50:59', NULL),
(25, 'JS', 20141002426, 'tDiang', 27, '3班', '2018-04-21 03:50:59', NULL),
(26, 'JS2', 20141002426, 'tDiang', 28, '', '2018-04-24 07:24:29', NULL),
(27, 'JS2', 20141002426, 'tDiang', 28, 'js基础班', '2018-04-24 07:43:13', NULL),
(28, 'JS', 20141002426, 'tDiang', 27, '444', '2018-04-24 07:44:05', NULL),
(29, 'JS2', 20141002426, 'tDiang', 28, 'js进阶班', '2018-04-24 07:53:31', NULL),
(30, 'JS2', 20141002426, 'tDiang', 28, 'js高级班', '2018-04-24 07:56:15', NULL),
(31, 'JS2', 20141002426, 'tDiang', 28, 'js巨无霸', '2018-04-24 07:58:41', NULL),
(32, 'JS2', 20141002426, 'tDiang', 28, 'node基础班', '2018-04-24 08:00:58', NULL),
(33, 'JS2', 20141002426, 'tDiang', 28, 'node进阶班', '2018-04-24 08:01:14', NULL),
(34, 'DB3', 20141002426, 'tDiang', 26, '数据库1班', '2018-04-24 14:15:49', NULL),
(35, 'JS2', 20141002426, 'tDiang', 28, 'node高级班', '2018-04-24 14:16:32', NULL),
(36, 'JS', 20141002426, 'tDiang', 27, 'js44444', '2018-04-24 14:16:47', NULL);

-- --------------------------------------------------------

--
-- 表的结构 `comment`
--

CREATE TABLE `comment` (
  `id` int(11) NOT NULL,
  `task_id` int(11) NOT NULL,
  `stu_id` bigint(20) NOT NULL DEFAULT '0',
  `tea_id` bigint(20) NOT NULL DEFAULT '0',
  `reply_to` int(11) NOT NULL DEFAULT '0' COMMENT '对id的回复',
  `content` varchar(255) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `course`
--

CREATE TABLE `course` (
  `course_id` int(11) NOT NULL,
  `course_name` varchar(50) NOT NULL COMMENT '课程名称',
  `introduction` text COMMENT '课程简介',
  `tea_id` bigint(20) NOT NULL COMMENT '该老师创建的课程',
  `tea_name` varchar(40) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `course`
--

INSERT INTO `course` (`course_id`, `course_name`, `introduction`, `tea_id`, `tea_name`, `create_time`, `update_time`) VALUES
(16, 'DB', '56322544', 20141002426, 'tDiang', '2018-04-09 02:09:38', NULL),
(17, 'Magic', '56322544', 20141002426, 'tDiang', '2018-04-09 02:12:33', NULL),
(18, 'English', '56322544', 20141002426, 'tDiang', '2018-04-09 02:19:18', NULL),
(20, '数据2', '56322544', 20141002426, 'tDiang', '2018-04-09 02:21:42', NULL),
(21, '数据3', '这是数据3', 20141002426, 'tDiang', '2018-04-09 02:36:25', '2018-04-09 02:36:25'),
(22, '算法', '发散思维', 20141002426, 'tDiang', '2018-04-12 13:08:51', '2018-04-12 13:08:51'),
(23, '22', 'DB2', 20141002426, 'tDiang', '2018-04-12 13:46:10', NULL),
(24, 'DB2', '11255', 20141002426, 'tDiang', '2018-04-12 13:54:28', NULL),
(25, 'Chinese', '154654', 20141002426, 'tDiang', '2018-04-17 03:09:40', NULL),
(26, 'DB3', '12552', 20141002426, 'tDiang', '2018-04-19 12:10:52', NULL),
(27, 'JS', '125638', 20141002426, 'tDiang', '2018-04-21 03:50:59', NULL),
(28, 'JS2', '125638', 20141002426, 'tDiang', '2018-04-21 03:51:36', NULL);

-- --------------------------------------------------------

--
-- 表的结构 `course_file_info`
--

CREATE TABLE `course_file_info` (
  `id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL,
  `title` varchar(50) NOT NULL,
  `introduction` text,
  `filename` varchar(255) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `url` varchar(255) NOT NULL,
  `file_id` varchar(32) NOT NULL,
  `tea_id` bigint(20) NOT NULL,
  `tea_name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `course_file_info`
--

INSERT INTO `course_file_info` (`id`, `course_id`, `title`, `introduction`, `filename`, `create_time`, `url`, `file_id`, `tea_id`, `tea_name`) VALUES
(6, 11, '', NULL, 'Jessy_HSBC.doc', '2018-01-01 03:08:48', '/fileDir/u/2018-03-13/dc0b7b03e6ce4e89866bb61892209492Jessy_HSBC.doc', '56db8e9f08ba43d8b2ed15c13ecef41c', 0, ''),
(7, 11, '', NULL, '4c116a3360cc4d5585633a3e195a487d.jpg', '2018-03-13 03:10:07', '/img/u/2018-03-13/4c116a3360cc4d5585633a3e195a487d.jpg', 'a52855f12b9446309e2fcd456f8ec5b2', 0, ''),
(8, 21, '', NULL, 'Jessy_HSBC.doc', '2018-04-10 06:39:21', '/fileDir/u/2018-03-13/dc0b7b03e6ce4e89866bb61892209492Jessy_HSBC.doc', '56db8e9f08ba43d8b2ed15c13ecef41c', 20141002426, 'tDiang'),
(9, 11, '', NULL, 'Jessy_HSBC.doc', '2018-04-15 05:13:03', '/fileDir/u/2018-03-13/dc0b7b03e6ce4e89866bb61892209492Jessy_HSBC.doc', '56db8e9f08ba43d8b2ed15c13ecef41c', 20141002426, 'tDiang'),
(10, 21, '第一课课件', 'daskhflksadjflkksjd6788', 'Jessy_HSBC.doc', '2018-04-24 13:12:10', '/fileDir/u/2018-03-13/dc0b7b03e6ce4e89866bb61892209492Jessy_HSBC.doc', '56db8e9f08ba43d8b2ed15c13ecef41c', 20141002426, 'tDiang'),
(11, 21, '第二课课件', NULL, 'Jessy_HSBC.doc', '2018-04-24 13:12:54', '/fileDir/u/2018-03-13/dc0b7b03e6ce4e89866bb61892209492Jessy_HSBC.doc', '56db8e9f08ba43d8b2ed15c13ecef41c', 20141002426, 'tDiang');

-- --------------------------------------------------------

--
-- 表的结构 `grade_info`
--

CREATE TABLE `grade_info` (
  `id` int(11) NOT NULL,
  `grade` smallint(1) NOT NULL,
  `detail` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `grade_info`
--

INSERT INTO `grade_info` (`id`, `grade`, `detail`) VALUES
(1, 0, 'null'),
(2, 10, 'D'),
(3, 20, 'C-'),
(4, 30, 'C+'),
(5, 40, 'B-'),
(6, 50, 'B+'),
(7, 60, 'A-'),
(8, 70, 'A+');

-- --------------------------------------------------------

--
-- 表的结构 `img_file_store`
--

CREATE TABLE `img_file_store` (
  `id` varchar(32) NOT NULL,
  `url` varchar(255) NOT NULL,
  `filename` varchar(255) NOT NULL,
  `create_time` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `img_file_store`
--

INSERT INTO `img_file_store` (`id`, `url`, `filename`, `create_time`) VALUES
('41c0c3f09ca04cb99ea38fbbdf44ef03', '/fileDir/u/2018-03-13/6ea3f38746674fabb4e7271693b4d4100703计算机E模拟.docx', '0703计算机E模拟.docx', '2018-03-13 14:24:37'),
('4242541de6bf4f1cafb38569c3ba78ad', '/fileDir/u/2018-03-13/14a8ec60100946e28fa4e2b414cb74ccCareerFrog_简历模板_中文(1).docx', 'CareerFrog_简历模板_中文(1).docx', '2018-03-13 10:59:40'),
('43831b86269c4bd58f673eae0b378634', '/img/u/2018-04-15/e2b1af426014442e99e199d9d3d21b61.jpg', 'e2b1af426014442e99e199d9d3d21b61.jpg', '2018-04-15 11:36:29'),
('4e535ad07ad84e76b7baf254a814ef2a', '/img/u/2018-04-22/0b77233c560f4ba0aa4b88bc94be5ec5.png', '0b77233c560f4ba0aa4b88bc94be5ec5.png', '2018-04-22 11:08:27'),
('56db8e9f08ba43d8b2ed15c13ecef41c', '/fileDir/u/2018-03-13/dc0b7b03e6ce4e89866bb61892209492Jessy_HSBC.doc', 'Jessy_HSBC.doc', '2018-03-13 10:30:08'),
('787eb075f462452abe6f816487372238', '/fileDir/u/2018-04-22/34c8d904ffce4331b1016c3946774ea7目录结构.docx', '目录结构.docx', '2018-04-22 11:07:57'),
('a1e35383ab5d4ccfa6ca9f3de5d073dd', '/fileDir/u/2018-04-22/dfcacb5d77574f828c5698b241fc32b4蔡康永的说话之道.docx', '蔡康永的说话之道.docx', '2018-04-22 11:44:35'),
('a4873393803e42cb9d298b7fe192c64c', '/fileDir/u/2018-04-15/66934001304d44a9916ff46354d6454f注意事项4.docx', '注意事项4.docx', '2018-04-15 16:51:58'),
('a52855f12b9446309e2fcd456f8ec5b2', '/img/u/2018-03-13/4c116a3360cc4d5585633a3e195a487d.jpg', '4c116a3360cc4d5585633a3e195a487d.jpg', '2018-03-13 10:59:13'),
('ac73f9d263d7484fa8dad2816570fce7', '/fileDir/u/2018-04-22/38fba0ed801a4fbbb65092a649ed41d7蔡康永的说话之道.docx', '蔡康永的说话之道.docx', '2018-04-22 11:14:04'),
('b306ace50bd0481eb1be474438a4b00c', '/fileDir/u/2018-04-24/86891db011ef4cd5af33cc36ee0d8d9f接口.docx', '接口.docx', '2018-04-24 13:44:57'),
('b5d309c6586f4778bfa819887a56751f', '/fileDir/u/2018-04-24/690c1cc9bac84dc4a4f09088c2b8bda0test1.docx', 'test1.docx', '2018-04-24 13:43:38'),
('c3e1ddb8c68b4b3db7491bc200019ede', '/fileDir/u/2018-04-15/15bd7dadaccc470fba98b994c895cd6d注意事项.docx', '注意事项.docx', '2018-04-15 11:34:35');

-- --------------------------------------------------------

--
-- 表的结构 `role_type`
--

CREATE TABLE `role_type` (
  `id` int(11) NOT NULL,
  `role` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `role_type`
--

INSERT INTO `role_type` (`id`, `role`) VALUES
(1, '学生'),
(2, '老师'),
(3, '管理员');

-- --------------------------------------------------------

--
-- 表的结构 `status_type`
--

CREATE TABLE `status_type` (
  `id` int(11) NOT NULL,
  `status` varchar(35) NOT NULL COMMENT '1表示在读生，2表示交换生，3表示休学'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `status_type`
--

INSERT INTO `status_type` (`id`, `status`) VALUES
(1, '在校'),
(2, '交换生'),
(3, '休学'),
(4, '出国');

-- --------------------------------------------------------

--
-- 表的结构 `stu_course`
--

CREATE TABLE `stu_course` (
  `id` int(11) UNSIGNED ZEROFILL NOT NULL,
  `stu_id` bigint(20) NOT NULL,
  `stu_name` varchar(40) NOT NULL,
  `class_id` int(11) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `class_name` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `stu_course`
--

INSERT INTO `stu_course` (`id`, `stu_id`, `stu_name`, `class_id`, `create_time`, `class_name`) VALUES
(00000000021, 20141002426, 'diang', 21, '2018-04-09 08:42:07', '数据1'),
(00000000024, 20141002418, 'biyue', 21, '2018-04-09 08:44:27', '数据1'),
(00000000025, 20141002426, 'diang', 22, '0000-00-00 00:00:00', '数据2班'),
(00000000026, 20141002426, 'diang', 23, '0000-00-00 00:00:00', '1班');

-- --------------------------------------------------------

--
-- 表的结构 `stu_info`
--

CREATE TABLE `stu_info` (
  `id` bigint(20) NOT NULL,
  `username` varchar(40) NOT NULL COMMENT '用户名',
  `password` varchar(35) NOT NULL,
  `status` int(11) NOT NULL DEFAULT '1',
  `imgUrl` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `email` varchar(45) DEFAULT NULL,
  `phone` bigint(13) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `stu_info`
--

INSERT INTO `stu_info` (`id`, `username`, `password`, `status`, `imgUrl`, `email`, `phone`) VALUES
(20141002412, 'Tracy', '5DEDAF99D256C1CFD97CE2975F4B8624', 1, NULL, NULL, NULL),
(20141002418, 'biyue', '5DEDAF99D256C1CFD97CE2975F4B8624', 1, NULL, NULL, NULL),
(20141002426, 'diang', '5DEDAF99D256C1CFD97CE2975F4B8624', 1, '/fileDir/u/2018-03-13/6ea3f38746674fabb4e7271693b4d4100703计算机E模拟.docx', '4253@qq.com', NULL),
(201410024989, 'llllll', '123123qwe', 1, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- 表的结构 `submit_task`
--

CREATE TABLE `submit_task` (
  `submit_tid` int(11) NOT NULL,
  `task_id` int(11) NOT NULL,
  `stu_name` varchar(40) NOT NULL,
  `stu_id` bigint(20) NOT NULL,
  `filename` varchar(80) NOT NULL,
  `grade` smallint(2) NOT NULL DEFAULT '0',
  `remark` text,
  `file_id` varchar(32) NOT NULL,
  `url` varchar(255) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `class_id` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `submit_task`
--

INSERT INTO `submit_task` (`submit_tid`, `task_id`, `stu_name`, `stu_id`, `filename`, `grade`, `remark`, `file_id`, `url`, `create_time`, `class_id`) VALUES
(9, 8, 'diang', 20141002426, 'CareerFrog_简历模板_中文(1).docx', 6, '请更认真对待作业', '4242541de6bf4f1cafb38569c3ba78ad', '/fileDir/u/2018-03-13/14a8ec60100946e28fa4e2b414cb74ccCareerFrog_简历模板_中文(1).docx', '2018-04-24 12:47:51', 21),
(11, 10, 'diang', 20141002426, 'Jessy_HSBC.doc', 0, NULL, '56db8e9f08ba43d8b2ed15c13ecef41c', '/fileDir/u/2018-03-13/dc0b7b03e6ce4e89866bb61892209492Jessy_HSBC.doc', '2018-04-21 11:18:58', 21);

-- --------------------------------------------------------

--
-- 表的结构 `task`
--

CREATE TABLE `task` (
  `task_id` int(11) NOT NULL,
  `tea_id` bigint(20) NOT NULL,
  `tea_name` varchar(40) NOT NULL,
  `class_id` int(11) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建作业时间',
  `end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '上传文件的截止时间',
  `content` text COMMENT '作业内容',
  `title` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `task`
--

INSERT INTO `task` (`task_id`, `tea_id`, `tea_name`, `class_id`, `create_time`, `end_time`, `content`, `title`) VALUES
(8, 20141002426, 'tDiang', 21, '2018-04-10 03:06:04', '2018-04-10 16:00:00', '请完成1.5', '数据3第一章作业'),
(10, 20141002426, 'tDiang', 21, '2018-04-10 03:51:53', '2018-04-10 03:54:00', '请完成3.1', '数据3第三章作业');

-- --------------------------------------------------------

--
-- 表的结构 `tea_info`
--

CREATE TABLE `tea_info` (
  `id` bigint(20) NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(40) NOT NULL,
  `status` int(11) NOT NULL DEFAULT '1',
  `imgUrl` varchar(255) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `phone` bigint(13) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `tea_info`
--

INSERT INTO `tea_info` (`id`, `username`, `password`, `status`, `imgUrl`, `email`, `phone`) VALUES
(20141000000, '罗半仙', '123123qwe', 1, NULL, NULL, NULL),
(20141002401, '罗显现', '123123qwe', 1, NULL, NULL, NULL),
(20141002402, '罗库库', '123123qwe', 1, NULL, NULL, NULL),
(20141002412, 'tTracy', '5DEDAF99D256C1CFD97CE2975F4B8624', 1, NULL, NULL, NULL),
(20141002418, 'tbiyue', '5DEDAF99D256C1CFD97CE2975F4B8624', 1, NULL, NULL, NULL),
(20141002426, 'tDiang', '5DEDAF99D256C1CFD97CE2975F4B8624', 1, '/img/u/2018-04-15/e2b1af426014442e99e199d9d3d21b61.jpg', '12345@qq.com', 1356789277882),
(2014100242642, 't0409', '5DEDAF99D256C1CFD97CE2975F4B8624', 1, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- 表的结构 `tp_advertise`
--

CREATE TABLE `tp_advertise` (
  `notice_id` int(11) NOT NULL,
  `class_id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `content` text NOT NULL,
  `tea_id` bigint(20) NOT NULL,
  `tea_name` varchar(255) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `tp_advertise`
--

INSERT INTO `tp_advertise` (`notice_id`, `class_id`, `title`, `content`, `tea_id`, `tea_name`, `create_time`) VALUES
(1, 21, '第一次', '2445', 20141002426, 'tDiang', '0000-00-00 00:00:00'),
(2, 21, '第二次', '的开发决定了', 20141002426, 'tDiang', '2018-04-24 01:43:05'),
(3, 21, '第三次', '的开始放假', 20141002426, 'tDiang', '2018-04-24 01:43:39'),
(4, 21, '第四次', '看大送积分', 20141002426, 'tDiang', '2018-04-24 01:44:04'),
(7, 22, '第一', '佳斯柯达', 20141002426, 'tDiang', '2018-04-24 02:08:40'),
(8, 21, '数据3第二次作业', '请完成2.11.1', 20141002426, 'tDiang', '2018-04-24 04:56:13'),
(9, 33, '基于React.js的教学平台设计与开发', '本宝准备飞天', 20141002426, 'tDiang', '2018-04-24 11:21:31'),
(10, 27, 'js基础班的同学看过来', '有本教材需要大家买一下，叫做《javascript程序设计开发》，人手一本哈哈哈', 20141002426, 'tDiang', '2018-04-24 11:31:35');

-- --------------------------------------------------------

--
-- 表的结构 `tp_class`
--

CREATE TABLE `tp_class` (
  `class_id` int(11) NOT NULL,
  `class_name` varchar(255) NOT NULL,
  `tea_id` bigint(20) NOT NULL,
  `course_id` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `tp_class`
--

INSERT INTO `tp_class` (`class_id`, `class_name`, `tea_id`, `course_id`, `created_at`) VALUES
(1, '饥客2班', 1, 1, '0000-00-00 00:00:00');

-- --------------------------------------------------------

--
-- 表的结构 `tp_course`
--

CREATE TABLE `tp_course` (
  `course_id` int(11) NOT NULL,
  `course_name` varchar(50) NOT NULL COMMENT '课程名称',
  `introduction` text COMMENT '课程简介',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `tea_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建者'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `tp_course`
--

INSERT INTO `tp_course` (`course_id`, `course_name`, `introduction`, `create_time`, `update_time`, `tea_id`) VALUES
(1, '数据结构', NULL, '2018-04-08 10:25:46', '0000-00-00 00:00:00', 1);

-- --------------------------------------------------------

--
-- 表的结构 `tp_stu`
--

CREATE TABLE `tp_stu` (
  `stu_id` bigint(20) NOT NULL,
  `username` varchar(50) NOT NULL,
  `pwd` varchar(255) NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '1',
  `email` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `tp_stu`
--

INSERT INTO `tp_stu` (`stu_id`, `username`, `pwd`, `status`, `email`, `avatar`) VALUES
(1, '1班的用户', 'ddd', 1, NULL, NULL);

-- --------------------------------------------------------

--
-- 表的结构 `tp_stu_class`
--

CREATE TABLE `tp_stu_class` (
  `id` int(11) UNSIGNED ZEROFILL NOT NULL,
  `stu_id` bigint(20) NOT NULL,
  `class_id` int(11) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `class_name` varchar(40) NOT NULL,
  `stu_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `tp_stu_class`
--

INSERT INTO `tp_stu_class` (`id`, `stu_id`, `class_id`, `create_time`, `class_name`, `stu_name`) VALUES
(00000000001, 1, 1, '0000-00-00 00:00:00', '饥客2班', ''),
(00000000002, 2, 1, '0000-00-00 00:00:00', '', '');

-- --------------------------------------------------------

--
-- 表的结构 `tp_task`
--

CREATE TABLE `tp_task` (
  `task_id` int(11) NOT NULL,
  `class_id` int(11) NOT NULL,
  `tea_id` bigint(20) NOT NULL,
  `tea_name` varchar(255) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建作业时间',
  `content` text NOT NULL COMMENT '作业内容',
  `title` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `tp_task`
--

INSERT INTO `tp_task` (`task_id`, `class_id`, `tea_id`, `tea_name`, `create_time`, `content`, `title`) VALUES
(1, 1, 1, 'laoshi', '2018-04-08 10:28:11', '1.1', ''),
(2, 1, 1, 'laoshi', '2018-04-08 10:28:24', '1.2', ''),
(3, 2, 1, 'laoshi', '2018-04-08 10:36:06', '2.1', '');

-- --------------------------------------------------------

--
-- 表的结构 `tp_task_submit`
--

CREATE TABLE `tp_task_submit` (
  `id` int(11) NOT NULL,
  `task_id` int(11) NOT NULL,
  `stu_id` bigint(20) NOT NULL,
  `class_id` int(11) NOT NULL DEFAULT '0',
  `filename` varchar(80) NOT NULL,
  `grade` double(30,2) DEFAULT NULL,
  `file_id` varchar(32) NOT NULL,
  `url` varchar(255) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `tp_task_submit`
--

INSERT INTO `tp_task_submit` (`id`, `task_id`, `stu_id`, `class_id`, `filename`, `grade`, `file_id`, `url`, `create_time`) VALUES
(1, 1, 1, 1, '', NULL, '', '', '2018-04-08 10:28:46'),
(2, 3, 1, 2, '', NULL, '', '', '2018-04-08 10:36:21');

-- --------------------------------------------------------

--
-- 表的结构 `tp_tea`
--

CREATE TABLE `tp_tea` (
  `tea_id` bigint(20) NOT NULL,
  `username` varchar(50) NOT NULL,
  `pwd` varchar(255) NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '1',
  `email` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `tp_tea`
--

INSERT INTO `tp_tea` (`tea_id`, `username`, `pwd`, `status`, `email`, `avatar`) VALUES
(1, 'laoshi', 'ddd', 1, NULL, NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `class_info`
--
ALTER TABLE `class_info`
  ADD PRIMARY KEY (`class_id`),
  ADD KEY `index_class_name` (`class_name`) USING BTREE,
  ADD KEY `index_course_id` (`course_id`) USING BTREE,
  ADD KEY `index_tea_id` (`tea_id`) USING BTREE;

--
-- Indexes for table `comment`
--
ALTER TABLE `comment`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `course`
--
ALTER TABLE `course`
  ADD PRIMARY KEY (`course_id`),
  ADD KEY `index_course_name` (`course_name`) USING BTREE,
  ADD KEY `fk_tea_id` (`tea_id`);

--
-- Indexes for table `course_file_info`
--
ALTER TABLE `course_file_info`
  ADD PRIMARY KEY (`id`),
  ADD KEY `index_course_id` (`course_id`) USING BTREE,
  ADD KEY `index_file_id` (`file_id`) USING BTREE;

--
-- Indexes for table `grade_info`
--
ALTER TABLE `grade_info`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_grade` (`grade`) USING BTREE;

--
-- Indexes for table `img_file_store`
--
ALTER TABLE `img_file_store`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `role_type`
--
ALTER TABLE `role_type`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `status_type`
--
ALTER TABLE `status_type`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `stu_course`
--
ALTER TABLE `stu_course`
  ADD PRIMARY KEY (`id`),
  ADD KEY `index_stu_id` (`stu_id`) USING BTREE,
  ADD KEY `index_class_id` (`class_id`) USING BTREE,
  ADD KEY `index_class_name` (`class_name`) USING BTREE;

--
-- Indexes for table `stu_info`
--
ALTER TABLE `stu_info`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `submit_task`
--
ALTER TABLE `submit_task`
  ADD PRIMARY KEY (`submit_tid`),
  ADD KEY `fk_task_id` (`task_id`),
  ADD KEY `fk_file_id` (`file_id`) USING BTREE;

--
-- Indexes for table `task`
--
ALTER TABLE `task`
  ADD PRIMARY KEY (`task_id`),
  ADD KEY `index_class_id` (`class_id`) USING BTREE;

--
-- Indexes for table `tea_info`
--
ALTER TABLE `tea_info`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tp_advertise`
--
ALTER TABLE `tp_advertise`
  ADD PRIMARY KEY (`notice_id`);

--
-- Indexes for table `tp_class`
--
ALTER TABLE `tp_class`
  ADD PRIMARY KEY (`class_id`);

--
-- Indexes for table `tp_course`
--
ALTER TABLE `tp_course`
  ADD PRIMARY KEY (`course_id`);

--
-- Indexes for table `tp_stu`
--
ALTER TABLE `tp_stu`
  ADD PRIMARY KEY (`stu_id`);

--
-- Indexes for table `tp_stu_class`
--
ALTER TABLE `tp_stu_class`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tp_task`
--
ALTER TABLE `tp_task`
  ADD PRIMARY KEY (`task_id`);

--
-- Indexes for table `tp_task_submit`
--
ALTER TABLE `tp_task_submit`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tp_tea`
--
ALTER TABLE `tp_tea`
  ADD PRIMARY KEY (`tea_id`);

--
-- 在导出的表使用AUTO_INCREMENT
--

--
-- 使用表AUTO_INCREMENT `class_info`
--
ALTER TABLE `class_info`
  MODIFY `class_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=37;
--
-- 使用表AUTO_INCREMENT `comment`
--
ALTER TABLE `comment`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- 使用表AUTO_INCREMENT `course`
--
ALTER TABLE `course`
  MODIFY `course_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;
--
-- 使用表AUTO_INCREMENT `course_file_info`
--
ALTER TABLE `course_file_info`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
--
-- 使用表AUTO_INCREMENT `grade_info`
--
ALTER TABLE `grade_info`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- 使用表AUTO_INCREMENT `role_type`
--
ALTER TABLE `role_type`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- 使用表AUTO_INCREMENT `status_type`
--
ALTER TABLE `status_type`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- 使用表AUTO_INCREMENT `stu_course`
--
ALTER TABLE `stu_course`
  MODIFY `id` int(11) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;
--
-- 使用表AUTO_INCREMENT `submit_task`
--
ALTER TABLE `submit_task`
  MODIFY `submit_tid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
--
-- 使用表AUTO_INCREMENT `task`
--
ALTER TABLE `task`
  MODIFY `task_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- 使用表AUTO_INCREMENT `tp_advertise`
--
ALTER TABLE `tp_advertise`
  MODIFY `notice_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- 使用表AUTO_INCREMENT `tp_class`
--
ALTER TABLE `tp_class`
  MODIFY `class_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- 使用表AUTO_INCREMENT `tp_course`
--
ALTER TABLE `tp_course`
  MODIFY `course_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- 使用表AUTO_INCREMENT `tp_stu_class`
--
ALTER TABLE `tp_stu_class`
  MODIFY `id` int(11) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- 使用表AUTO_INCREMENT `tp_task`
--
ALTER TABLE `tp_task`
  MODIFY `task_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- 使用表AUTO_INCREMENT `tp_task_submit`
--
ALTER TABLE `tp_task_submit`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
