-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 2018-01-13 16:28:35
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
-- 表的结构 `course`
--

CREATE TABLE `course` (
  `cour_id` int(11) NOT NULL,
  `course_name` varchar(50) NOT NULL COMMENT '课程名称',
  `introduction` text COMMENT '课程简介'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
(1, '正常'),
(2, '交换生'),
(3, '休学'),
(4, '出国');

-- --------------------------------------------------------

--
-- 表的结构 `stu_course`
--

CREATE TABLE `stu_course` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL,
  `type` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `submit_task`
--

CREATE TABLE `submit_task` (
  `id` int(11) NOT NULL,
  `task_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `filename` varchar(80) DEFAULT NULL,
  `grade` double(30,0) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `task`
--

CREATE TABLE `task` (
  `task_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL,
  `creat_time` datetime(6) NOT NULL COMMENT '创建作业时间',
  `content` varchar(80) NOT NULL COMMENT '作业内容',
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `user_info`
--

CREATE TABLE `user_info` (
  `id` int(11) NOT NULL,
  `username` varchar(40) DEFAULT NULL COMMENT '用户名',
  `password` varchar(35) NOT NULL,
  `user_id` int(30) NOT NULL,
  `type` int(11) NOT NULL,
  `status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `course`
--
ALTER TABLE `course`
  ADD PRIMARY KEY (`cour_id`);

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
  ADD KEY `fk_course_id` (`course_id`),
  ADD KEY `fk_user_id` (`user_id`),
  ADD KEY `fk_type` (`type`);

--
-- Indexes for table `submit_task`
--
ALTER TABLE `submit_task`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_task_id` (`task_id`),
  ADD KEY `fk_stu_id` (`user_id`);

--
-- Indexes for table `task`
--
ALTER TABLE `task`
  ADD PRIMARY KEY (`task_id`);

--
-- Indexes for table `user_info`
--
ALTER TABLE `user_info`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `user_id` (`user_id`),
  ADD KEY `type-id` (`type`),
  ADD KEY `fk_status` (`status`);

--
-- 在导出的表使用AUTO_INCREMENT
--

--
-- 使用表AUTO_INCREMENT `course`
--
ALTER TABLE `course`
  MODIFY `cour_id` int(11) NOT NULL AUTO_INCREMENT;
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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- 使用表AUTO_INCREMENT `task`
--
ALTER TABLE `task`
  MODIFY `task_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- 使用表AUTO_INCREMENT `user_info`
--
ALTER TABLE `user_info`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- 限制导出的表
--

--
-- 限制表 `stu_course`
--
ALTER TABLE `stu_course`
  ADD CONSTRAINT `fk_course_id` FOREIGN KEY (`course_id`) REFERENCES `course` (`cour_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_type` FOREIGN KEY (`type`) REFERENCES `role_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- 限制表 `submit_task`
--
ALTER TABLE `submit_task`
  ADD CONSTRAINT `fk_stu_id` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_task_id` FOREIGN KEY (`task_id`) REFERENCES `task` (`task_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- 限制表 `user_info`
--
ALTER TABLE `user_info`
  ADD CONSTRAINT `fk_status` FOREIGN KEY (`status`) REFERENCES `status_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `type-id` FOREIGN KEY (`type`) REFERENCES `role_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
