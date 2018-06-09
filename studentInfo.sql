/*
SQLyog Ultimate v8.32 
MySQL - 5.5.40 : Database - db_studentinfo
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `t_grade` */

CREATE TABLE `t_grade` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gradeName` varchar(20) DEFAULT NULL,
  `gradeDesc` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

/*Data for the table `t_grade` */

insert  into `t_grade`(`id`,`gradeName`,`gradeDesc`) values (1,'15软设3班','软件设计03'),(2,'15云计算2班','云计算02'),(4,'15大数据2班','大数据02'),(5,'15大数据3班','大数据03'),(6,'15软设2班','软件设计02'),(7,'15软件设计1班','软设01'),(17,'15云计算3班','云计算'),(18,'15级测试班','sdsd'),(20,'16大数据2班','大数据方向');

/*Table structure for table `t_student` */

CREATE TABLE `t_student` (
  `stuId` int(11) NOT NULL AUTO_INCREMENT,
  `stuNo` varchar(20) DEFAULT NULL,
  `stuName` varchar(10) DEFAULT NULL,
  `sex` varchar(5) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `gradeId` int(11) DEFAULT NULL,
  `email` varchar(20) DEFAULT NULL,
  `stuDesc` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`stuId`),
  KEY `FK_t_student` (`gradeId`),
  CONSTRAINT `FK_t_student` FOREIGN KEY (`gradeId`) REFERENCES `t_grade` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

/*Data for the table `t_student` */

insert  into `t_student`(`stuId`,`stuNo`,`stuName`,`sex`,`birthday`,`gradeId`,`email`,`stuDesc`) values (1,'080606110','张三','男','1989-11-03',1,'31321@qq.com','让双方都'),(2,'080606110','发送','男','1989-11-03',1,'7661@qq.com','Good人情味'),(3,'080606123','张三','男','1989-11-03',1,'1254@qq.com','Good'),(7,'080606110','热风','男','1999-11-03',1,'31321@qq.com','Good'),(8,'080606110','sn','女','1989-11-20',5,'31321@qq.com','Goodboy'),(9,'080606110','张三','男','1959-11-03',1,'31321@qq.com','Good'),(10,'080606110','张三','男','1939-01-03',1,'31321@qq.com','GoodEAD '),(11,'080606110','发萨尔图','男','1929-04-03',1,'31321@qq.com','Good'),(12,'080606110','张三','男','1989-11-03',1,'31321@qq.com','Good'),(13,'080606110','张三','男','1989-11-03',1,'31321@qq.com','Good'),(14,'080606110','阿二手房','男','1989-11-03',1,'31321@qq.com','Good'),(15,'080606110','张三','男','1989-01-03',1,'31321@qq.com','Good'),(16,'080606110','张三','男','1989-11-03',1,'31321@qq.com','Good'),(17,'080606110','张三','男','1989-11-03',1,'31321@qq.com','Good'),(18,'090606119','二维','女','1992-11-03',1,'3112121@qq.com','Good Girls'),(19,'090606119','热法','女','1993-11-03',1,'3112121@qq.com','Good Girls'),(20,'090606119','二分','女','1997-11-06',1,'3112121@qq.com','Good Girls'),(21,'090606119','认为他','女','1997-02-04',1,'3112121@qq.com','Good Girls'),(22,'090606119','sd ','女','1990-11-03',1,'3112121@qq.com','Good Girls'),(23,'090606119','re','女','1990-11-03',1,'3112121@qq.com','Good Girls'),(24,'090606119','erw','女','1990-11-03',1,'3112121@qq.com','Good Girls'),(25,'124','fgsd','男','2018-06-12',2,'123467@qq.com','asd');

/*Table structure for table `t_user` */

CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `t_user` */

insert  into `t_user`(`id`,`userName`,`password`) values (1,'admin','123456');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
