-- Database Schema for Cinema Ticketing System

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL COMMENT 'Primary Key (Snowflake ID)',
  `username` varchar(50) NOT NULL COMMENT 'Username',
  `password` varchar(100) NOT NULL COMMENT 'Encrypted Password',
  `phone` varchar(20) NOT NULL COMMENT 'Phone Number',
  `avatar` varchar(255) DEFAULT NULL COMMENT 'Avatar URL',
  `points` int(11) DEFAULT 0 COMMENT 'Membership Points',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT 'Creation Time',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update Time',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='User Table';

-- ----------------------------
-- Table structure for t_movie
-- ----------------------------
DROP TABLE IF EXISTS `t_movie`;
CREATE TABLE `t_movie` (
  `id` bigint(20) NOT NULL COMMENT 'Primary Key',
  `title` varchar(100) NOT NULL COMMENT 'Movie Title',
  `poster_url` varchar(255) DEFAULT NULL COMMENT 'Poster Image URL',
  `description` text COMMENT 'Plot Synopsis',
  `video_url` varchar(255) DEFAULT NULL COMMENT 'Trailer Video URL',
  `release_date` date DEFAULT NULL COMMENT 'Release Date',
  `duration` int(11) DEFAULT 0 COMMENT 'Duration in Minutes',
  `director` varchar(100) DEFAULT NULL COMMENT 'Director',
  `actors` varchar(500) DEFAULT NULL COMMENT 'Cast List',
  `rating` decimal(3,1) DEFAULT 0.0 COMMENT 'Rating (e.g., 9.5)',
  `status` tinyint(4) DEFAULT 1 COMMENT 'Status: 1-Hot Showing, 2-Coming Soon, 0-Offline',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Movie Info Table';

-- ----------------------------
-- Table structure for t_hall
-- ----------------------------
DROP TABLE IF EXISTS `t_hall`;
CREATE TABLE `t_hall` (
  `id` bigint(20) NOT NULL COMMENT 'Primary Key',
  `name` varchar(50) NOT NULL COMMENT 'Hall Name (e.g., IMAX Hall 1)',
  `total_rows` int(11) NOT NULL COMMENT 'Total Rows',
  `total_cols` int(11) NOT NULL COMMENT 'Total Columns',
  `type` tinyint(4) DEFAULT 1 COMMENT 'Type: 1-2D, 2-3D, 3-IMAX',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Cinema Hall Table';

-- ----------------------------
-- Table structure for t_seat
-- ----------------------------
DROP TABLE IF EXISTS `t_seat`;
CREATE TABLE `t_seat` (
  `id` bigint(20) NOT NULL COMMENT 'Primary Key',
  `hall_id` bigint(20) NOT NULL COMMENT 'Hall ID',
  `row_num` int(11) NOT NULL COMMENT 'Row Number (1-based)',
  `col_num` int(11) NOT NULL COMMENT 'Column Number (1-based)',
  `type` tinyint(4) DEFAULT 1 COMMENT 'Type: 1-Standard, 2-Couple, 0-Corridor/Broken',
  `status` tinyint(4) DEFAULT 1 COMMENT 'Status: 1-Available, 0-Unavailable',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_hall_id` (`hall_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Seat Layout Table';

-- ----------------------------
-- Table structure for t_schedule
-- ----------------------------
DROP TABLE IF EXISTS `t_schedule`;
CREATE TABLE `t_schedule` (
  `id` bigint(20) NOT NULL COMMENT 'Primary Key',
  `movie_id` bigint(20) NOT NULL COMMENT 'Movie ID',
  `hall_id` bigint(20) NOT NULL COMMENT 'Hall ID',
  `start_time` datetime NOT NULL COMMENT 'Show Start Time',
  `end_time` datetime NOT NULL COMMENT 'Show End Time',
  `price` decimal(10,2) NOT NULL COMMENT 'Price',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_movie_id` (`movie_id`),
  KEY `idx_hall_id` (`hall_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Movie Schedule Table';

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
  `id` bigint(20) NOT NULL COMMENT 'Primary Key',
  `order_no` varchar(64) NOT NULL COMMENT 'Order Number',
  `user_id` bigint(20) NOT NULL COMMENT 'User ID',
  `schedule_id` bigint(20) NOT NULL COMMENT 'Schedule ID',
  `total_amount` decimal(10,2) NOT NULL COMMENT 'Total Amount',
  `status` tinyint(4) DEFAULT 0 COMMENT 'Status: 0-Pending, 1-Paid, 2-Cancelled, 3-Refunded',
  `pay_time` datetime DEFAULT NULL COMMENT 'Payment Time',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Order Table';

-- ----------------------------
-- Table structure for t_ticket
-- ----------------------------
DROP TABLE IF EXISTS `t_ticket`;
CREATE TABLE `t_ticket` (
  `id` bigint(20) NOT NULL COMMENT 'Primary Key',
  `order_id` bigint(20) NOT NULL COMMENT 'Order ID',
  `schedule_id` bigint(20) NOT NULL COMMENT 'Schedule ID',
  `seat_id` bigint(20) NOT NULL COMMENT 'Seat ID',
  `seat_label` varchar(20) NOT NULL COMMENT 'Seat Label (e.g., 5排6座)',
  `price` decimal(10,2) NOT NULL COMMENT 'Ticket Price',
  `status` tinyint(4) DEFAULT 0 COMMENT 'Status: 0-Unused, 1-Used, 2-Refunded',
  `qr_code` varchar(255) DEFAULT NULL COMMENT 'QR Code Content',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`),
  UNIQUE KEY `uk_schedule_seat` (`schedule_id`, `seat_id`) COMMENT 'Prevent double booking'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Ticket Table';

-- ----------------------------
-- Insert Data: Users
-- ----------------------------
INSERT INTO `t_user` (`id`, `username`, `password`, `phone`, `avatar`, `points`) VALUES
(1001, 'admin', '123456', '13800138001', 'https://api.dicebear.com/7.x/avataaars/svg?seed=admin', 9999),
(1002, 'testuser', '123456', '13800138002', 'https://api.dicebear.com/7.x/avataaars/svg?seed=test', 100),
(1003, 'john_doe', '123456', '13900139000', 'https://api.dicebear.com/7.x/avataaars/svg?seed=john', 50),
(1004, 'jane_doe', '123456', '13900139001', 'https://api.dicebear.com/7.x/avataaars/svg?seed=jane', 200),
(1005, 'movie_fan', '123456', '13900139002', 'https://api.dicebear.com/7.x/avataaars/svg?seed=fan', 500);

-- ----------------------------
-- Insert Data: Movies
-- ----------------------------
INSERT INTO `film_db`.`t_movie` (`id`, `title`, `poster_url`, `description`, `video_url`, `release_date`, `duration`, `director`, `actors`, `rating`, `status`, `create_time`, `update_time`) VALUES (2001, '盗梦空间', 'https://www.themoviedb.org/t/p/w1280/89W962aAnPS3N3BdKgy2BvUhnCh.jpg', '道姆·柯布与同事阿瑟和纳什在一次针对日本能源大亨齐藤（渡边谦 饰）的盗梦行动中失败，反被齐藤利用。齐藤威逼利诱因遭通缉而流亡海外的柯布帮他拆分他竞争对手的公司，采取极端措施在其唯一继承人罗伯特·费希尔的深层潜意识中种下放弃家族公司、自立门户的想法。为了重返美国，柯布偷偷求助于岳父迈尔斯，吸收了年轻的梦境设计师艾里阿德妮、梦境演员艾姆斯和药剂师约瑟夫加入行动。在一层层递进的梦境中，柯布不仅要对付费希尔潜意识的本能反抗，还必须直面已逝妻子梅尔的处处破坏，实际情况远比预想危险得多……', 'http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4', '2010-07-16', 148, '克里斯托弗·诺兰', 'Leonardo DiCaprio, Joseph Gordon-Levitt, Elliot Page', 9.3, 1, '2026-03-11 21:39:04', '2026-03-12 10:56:29');
INSERT INTO `film_db`.`t_movie` (`id`, `title`, `poster_url`, `description`, `video_url`, `release_date`, `duration`, `director`, `actors`, `rating`, `status`, `create_time`, `update_time`) VALUES (2002, '星际穿越', 'https://www.themoviedb.org/t/p/w1280/c35Vwd9rmMQfaEJuUrJRF3LZWJX.jpg', '近未来的地球黄沙遍野，小麦、秋葵等基础农作物相继因枯萎病灭绝，人类不再像从前那样仰望星空，放纵想象力和灵感的迸发，而是每日在沙尘暴的肆虐下倒数着所剩不多的光景。在家务农的前NASA宇航员库珀接连在女儿墨菲的书房发现奇怪的重力场现象，随即得知在某个未知区域内前NASA成员仍秘密进行一个拯救人类的计划。多年以前土星附近出现神秘虫洞，NASA借机将数名宇航员派遣到遥远的星系寻找适合居住的星球。在布兰德教授的劝说下，库珀忍痛告别了女儿，和其他三名专家教授女儿艾米莉亚·布兰德、罗米利、多伊尔搭乘宇宙飞船前往目前已知的最有希望的三颗星球考察。他们穿越遥远的星系银河，感受了一小时七年光阴的沧海桑田，窥见了未知星球和黑洞的壮伟与神秘。在浩瀚宇宙的绝望而孤独角落，总有一份超越了时空的笃定情怀将他们紧紧相连……', 'http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4', '2014-11-07', 169, '克里斯托弗·诺兰', 'Matthew McConaughey, Anne Hathaway, Jessica Chastain', 9.4, 1, '2026-03-11 21:39:04', '2026-03-12 10:56:31');
INSERT INTO `film_db`.`t_movie` (`id`, `title`, `poster_url`, `description`, `video_url`, `release_date`, `duration`, `director`, `actors`, `rating`, `status`, `create_time`, `update_time`) VALUES (2003, '蝙蝠侠:黑暗骑士', 'https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg', '蝙蝠侠在打击犯罪的战争中加大了赌注。在吉姆·戈登警长与地方检察官哈维·丹特的协助下，蝙蝠侠决心清除肆虐哥谭街头的残余犯罪组织。这一联手行动卓有成效，但他们很快发现自己沦为了一场混沌统治的猎物——这场混乱由一位新兴犯罪大师所掀动，惶恐的哥谭市民称他为“小丑”。', 'http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4', '2008-07-18', 152, '克里斯托弗·诺兰', 'Christian Bale, Heath Ledger, Aaron Eckhart', 9.0, 1, '2026-03-11 21:39:04', '2026-03-12 10:56:33');
INSERT INTO `film_db`.`t_movie` (`id`, `title`, `poster_url`, `description`, `video_url`, `release_date`, `duration`, `director`, `actors`, `rating`, `status`, `create_time`, `update_time`) VALUES (2004, '阿凡达:水之道', 'https://image.tmdb.org/t/p/w500/t6HIqrRAclMCA60NsSmeqe9RmNV.jpg', '十多年过去了，曾经截瘫的前海军士兵杰克·萨利（萨姆·沃辛顿饰）永久变身为拥有地球人基因和纳威人基因的“阿凡达”，并与奥马蒂卡亚部落的族长之女奈蒂莉（佐伊·索尔达娜饰）结为夫妇。杰克已经完全适应了他的新身体和族长的身份，并和奈蒂莉有了三个孩子，还收养了格蕾丝留下的女儿琪莉（西格妮·韦弗饰）。四个孩子里，老大纳特亚姆（杰米·福雷特斯饰）最为懂事听话，也很会照顾弟弟妹妹；老二琪莉（西格妮·韦弗 饰）因为不知道自己的父亲是谁，有些敏感；老三洛克（布里坦·道尔顿 饰）酷爱冒险，是家里的闯祸精；最小的妹妹图克（特里尼蒂·布利斯 饰）总是黏着哥哥姐姐们。四人跟人类留下的孤儿“蜘蛛”（杰克·尚皮永饰）是形影不离的好朋友。 原本，将地球人赶走后，杰克一家与族人过上了田园牧歌般的生活。然而，原本死去的迈尔斯·夸里奇上校（史蒂芬·朗饰）化身阿凡达重新归来，一心要找杰克和奈蒂莉复仇。为了族人的安危，也为了保护孩子，杰克决定带着家人离开，前往不易被找到的由罗娜尔（凯特·温斯莱特饰）和特诺瓦里（克利夫·柯蒂斯饰）领导的海洋部落，展开全新的生活。正当一家人经过重重磨合，终于适应以水构筑的新家园时，新的危机也在慢慢靠近。', 'http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4', '2022-12-16', 192, '詹姆斯·卡梅隆', 'Sam Worthington, Zoe Saldana, Sigourney Weaver', 8.9, 1, '2026-03-11 21:39:04', '2026-03-12 10:57:06');
INSERT INTO `film_db`.`t_movie` (`id`, `title`, `poster_url`, `description`, `video_url`, `release_date`, `duration`, `director`, `actors`, `rating`, `status`, `create_time`, `update_time`) VALUES (2005, '沙丘2', 'https://image.tmdb.org/t/p/w500/1pdfLvkbY9ohJlCjQH2CZjjYVvJ.jpg', '《沙丘2》承接第一部剧情，讲述保罗·厄崔迪（提莫西·查拉梅 Timothée Chalamet 饰）被帕迪沙皇帝和哈克南人联手灭族后，在厄拉科斯星球遇到弗雷曼女战士契妮（赞达亚 Zendaya 饰）以及加入弗雷曼人后展开的传奇旅程。保罗与让他家破人亡的阴谋家们开战，同时面临着一生所爱与已知宇宙命运的两难选择。', 'http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4', '2024-03-01', 166, '丹尼斯·维伦纽瓦', 'Timothée Chalamet, Zendaya, Rebecca Ferguson', 8.8, 2, '2026-03-11 21:39:04', '2026-03-12 10:57:35');
INSERT INTO `film_db`.`t_movie` (`id`, `title`, `poster_url`, `description`, `video_url`, `release_date`, `duration`, `director`, `actors`, `rating`, `status`, `create_time`, `update_time`) VALUES (2006, '奥本海默', 'https://image.tmdb.org/t/p/w500/8Gxv8gSFCU0XGDykEGv7zR1n2ua.jpg', '亲临这部震撼全球的现象级巨制。克里斯托弗·诺兰编剧并执导的本片带领观众深入物理学家J·罗伯特·奥本海默（基里安·墨菲 饰）的内心世界，他在曼哈顿计划中的开创性工作催生了世界上第一颗原子弹。这场无与伦比的银幕盛宴汇聚全明星阵容，包括艾米莉·布朗特、奥斯卡奖得主马特·达蒙、奥斯卡奖提名者小罗伯特·唐尼、奥斯卡奖提名者弗洛伦丝·皮尤、乔什·哈奈特，以及奥斯卡奖得主凯西·阿弗莱克、拉米·马雷克与肯尼思·布拉纳。', 'http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4', '2023-07-21', 180, '克里斯托弗·诺兰', 'Cillian Murphy, Emily Blunt, Matt Damon', 8.7, 1, '2026-03-11 21:39:04', '2026-03-12 10:56:36');
INSERT INTO `film_db`.`t_movie` (`id`, `title`, `poster_url`, `description`, `video_url`, `release_date`, `duration`, `director`, `actors`, `rating`, `status`, `create_time`, `update_time`) VALUES (2007, '蜘蛛侠:纵横宇宙', 'https://image.tmdb.org/t/p/w500/8Vt6mWEReuy4Of61Lnj5Xj704m8.jpg', '荣获奥斯卡奖的蜘蛛侠平行宇宙传奇迎来全新篇章。在这段史诗级冒险中，布鲁克林的全职邻家英雄蜘蛛侠迈尔斯·莫拉莱斯将穿越多元宇宙，与格温·史黛西及全新蜘蛛侠团队集结成军，直面前所未见的强大反派。', 'http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4', '2023-06-02', 140, '肯普·鲍尔斯', 'Shameik Moore, Hailee Steinfeld, Brian Tyree Henry', 8.6, 1, '2026-03-11 21:39:04', '2026-03-12 10:58:23');
INSERT INTO `film_db`.`t_movie` (`id`, `title`, `poster_url`, `description`, `video_url`, `release_date`, `duration`, `director`, `actors`, `rating`, `status`, `create_time`, `update_time`) VALUES (2008, '肖申克的救赎', 'https://image.tmdb.org/t/p/w500/q6y0Go1tsGEsmtFryDOJo3dEmqu.jpg', '安迪·杜佛兰被错判谋杀妻子及其情人，在缅因州肖申克服刑，判处两项无期徒刑接连执行。影片背景设定于1940年代，展现安迪如何在残酷的监狱环境中艰难求生，甚至赢得狱友们的敬重——尤其是与老囚徒“瑞德”·雷丁之间深厚的友谊。', 'http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4', '1994-09-23', 142, '弗兰克·德拉邦特', 'Tim Robbins, Morgan Freeman, Bob Gunton', 9.7, 0, '2026-03-11 21:39:04', '2026-03-12 10:58:51');
INSERT INTO `film_db`.`t_movie` (`id`, `title`, `poster_url`, `description`, `video_url`, `release_date`, `duration`, `director`, `actors`, `rating`, `status`, `create_time`, `update_time`) VALUES (2010, '功夫熊猫4', 'https://image.tmdb.org/t/p/w500/kDp1vUBnMpe8ak4rjgl3cLELqjU.jpg', '神龙大侠阿宝再度归来，要被师父强行进阶修行。神秘莫测的魅影妖后可以幻化成每一个阿宝的昔日宿敌。此次阿宝又结识了小真等新伙伴，并将一同开启这场冒险旅程。', 'http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4', '2024-03-08', 94, '迈克·米歇尔', 'Jack Black, Awkwafina, Viola Davis', 7.8, 2, '2026-03-11 21:39:04', '2026-03-12 10:59:24');


-- ----------------------------
-- Insert Data: Halls
-- ----------------------------
INSERT INTO `t_hall` (`id`, `name`, `rows`, `cols`, `type`) VALUES
(3001, 'IMAX Hall 1', 5, 8, 3),
(3002, 'Standard Hall 2', 4, 6, 1),
(3003, 'VIP Hall 3', 3, 4, 2);

-- ----------------------------
-- Insert Data: Seats (Generated for Halls)
-- ----------------------------
-- Hall 1: 5 rows * 8 cols = 40 seats
INSERT INTO `t_seat` (`id`, `hall_id`, `row_num`, `col_num`, `type`, `status`) VALUES
-- Row 1
(4001, 3001, 1, 1, 1, 1), (4002, 3001, 1, 2, 1, 1), (4003, 3001, 1, 3, 1, 1), (4004, 3001, 1, 4, 1, 1),
(4005, 3001, 1, 5, 1, 1), (4006, 3001, 1, 6, 1, 1), (4007, 3001, 1, 7, 1, 1), (4008, 3001, 1, 8, 1, 1),
-- Row 2
(4009, 3001, 2, 1, 1, 1), (4010, 3001, 2, 2, 1, 1), (4011, 3001, 2, 3, 1, 1), (4012, 3001, 2, 4, 1, 1),
(4013, 3001, 2, 5, 1, 1), (4014, 3001, 2, 6, 1, 1), (4015, 3001, 2, 7, 1, 1), (4016, 3001, 2, 8, 1, 1),
-- Row 3
(4017, 3001, 3, 1, 1, 1), (4018, 3001, 3, 2, 1, 1), (4019, 3001, 3, 3, 1, 1), (4020, 3001, 3, 4, 1, 1),
(4021, 3001, 3, 5, 1, 1), (4022, 3001, 3, 6, 1, 1), (4023, 3001, 3, 7, 1, 1), (4024, 3001, 3, 8, 1, 1),
-- Row 4
(4025, 3001, 4, 1, 1, 1), (4026, 3001, 4, 2, 1, 1), (4027, 3001, 4, 3, 1, 1), (4028, 3001, 4, 4, 1, 1),
(4029, 3001, 4, 5, 1, 1), (4030, 3001, 4, 6, 1, 1), (4031, 3001, 4, 7, 1, 1), (4032, 3001, 4, 8, 1, 1),
-- Row 5 (Couple Seats)
(4033, 3001, 5, 1, 2, 1), (4034, 3001, 5, 2, 2, 1), (4035, 3001, 5, 3, 2, 1), (4036, 3001, 5, 4, 2, 1),
(4037, 3001, 5, 5, 2, 1), (4038, 3001, 5, 6, 2, 1), (4039, 3001, 5, 7, 2, 1), (4040, 3001, 5, 8, 2, 1);

-- Hall 2: 4 rows * 6 cols = 24 seats
INSERT INTO `t_seat` (`id`, `hall_id`, `row_num`, `col_num`, `type`, `status`) VALUES
-- Row 1
(5001, 3002, 1, 1, 1, 1), (5002, 3002, 1, 2, 1, 1), (5003, 3002, 1, 3, 1, 1),
(5004, 3002, 1, 4, 1, 1), (5005, 3002, 1, 5, 1, 1), (5006, 3002, 1, 6, 1, 1),
-- Row 2
(5007, 3002, 2, 1, 1, 1), (5008, 3002, 2, 2, 1, 1), (5009, 3002, 2, 3, 1, 1),
(5010, 3002, 2, 4, 1, 1), (5011, 3002, 2, 5, 1, 1), (5012, 3002, 2, 6, 1, 1),
-- Row 3
(5013, 3002, 3, 1, 1, 1), (5014, 3002, 3, 2, 1, 1), (5015, 3002, 3, 3, 1, 1),
(5016, 3002, 3, 4, 1, 1), (5017, 3002, 3, 5, 1, 1), (5018, 3002, 3, 6, 1, 1),
-- Row 4
(5019, 3002, 4, 1, 1, 1), (5020, 3002, 4, 2, 1, 1), (5021, 3002, 4, 3, 1, 1),
(5022, 3002, 4, 4, 1, 1), (5023, 3002, 4, 5, 1, 1), (5024, 3002, 4, 6, 1, 1);

-- Hall 3: 3 rows * 4 cols = 12 seats (VIP)
INSERT INTO `t_seat` (`id`, `hall_id`, `row_num`, `col_num`, `type`, `status`) VALUES
-- Row 1
(6001, 3003, 1, 1, 1, 1), (6002, 3003, 1, 2, 1, 1), (6003, 3003, 1, 3, 1, 1), (6004, 3003, 1, 4, 1, 1),
-- Row 2
(6005, 3003, 2, 1, 1, 1), (6006, 3003, 2, 2, 1, 1), (6007, 3003, 2, 3, 1, 1), (6008, 3003, 2, 4, 1, 1),
-- Row 3
(6009, 3003, 3, 1, 1, 1), (6010, 3003, 3, 2, 1, 1), (6011, 3003, 3, 3, 1, 1), (6012, 3003, 3, 4, 1, 1);

-- ----------------------------
-- Insert Data: Schedules
-- ----------------------------
INSERT INTO `t_schedule` (`id`, `movie_id`, `hall_id`, `start_time`, `end_time`, `price`) VALUES
-- 2001: Inception (148 min = 2h 28m)
(7001, 2001, 3001, DATE_ADD(DATE(NOW()), INTERVAL '10:00' HOUR_MINUTE), DATE_ADD(DATE(NOW()), INTERVAL '12:28' HOUR_MINUTE), 80.00),
(7002, 2001, 3002, DATE_ADD(DATE(NOW()), INTERVAL '13:00' HOUR_MINUTE), DATE_ADD(DATE(NOW()), INTERVAL '15:28' HOUR_MINUTE), 50.00),
(7003, 2001, 3003, DATE_ADD(DATE(NOW()), INTERVAL '16:00' HOUR_MINUTE), DATE_ADD(DATE(NOW()), INTERVAL '18:28' HOUR_MINUTE), 100.00),
(7004, 2001, 3001, DATE_ADD(DATE(NOW()), INTERVAL '19:00' HOUR_MINUTE), DATE_ADD(DATE(NOW()), INTERVAL '21:28' HOUR_MINUTE), 85.00),

-- 2002: Interstellar (169 min = 2h 49m)
(7005, 2002, 3001, DATE_ADD(DATE(NOW()), INTERVAL '09:00' HOUR_MINUTE), DATE_ADD(DATE(NOW()), INTERVAL '11:49' HOUR_MINUTE), 90.00),
(7006, 2002, 3002, DATE_ADD(DATE(NOW()), INTERVAL '12:30' HOUR_MINUTE), DATE_ADD(DATE(NOW()), INTERVAL '15:19' HOUR_MINUTE), 60.00),
(7007, 2002, 3001, DATE_ADD(DATE(NOW()), INTERVAL '16:00' HOUR_MINUTE), DATE_ADD(DATE(NOW()), INTERVAL '18:49' HOUR_MINUTE), 95.00),

-- 2003: The Dark Knight (152 min = 2h 32m)
(7008, 2003, 3002, DATE_ADD(DATE(NOW()), INTERVAL '10:30' HOUR_MINUTE), DATE_ADD(DATE(NOW()), INTERVAL '13:02' HOUR_MINUTE), 55.00),
(7009, 2003, 3001, DATE_ADD(DATE(NOW()), INTERVAL '14:00' HOUR_MINUTE), DATE_ADD(DATE(NOW()), INTERVAL '16:32' HOUR_MINUTE), 85.00),
(7010, 2003, 3003, DATE_ADD(DATE(NOW()), INTERVAL '19:30' HOUR_MINUTE), DATE_ADD(DATE(NOW()), INTERVAL '22:02' HOUR_MINUTE), 110.00),

-- 2004: Avatar 2 (192 min = 3h 12m)
(7011, 2004, 3001, DATE_ADD(DATE(NOW()), INTERVAL '13:00' HOUR_MINUTE), DATE_ADD(DATE(NOW()), INTERVAL '16:12' HOUR_MINUTE), 120.00),
(7012, 2004, 3001, DATE_ADD(DATE(NOW()), INTERVAL '17:00' HOUR_MINUTE), DATE_ADD(DATE(NOW()), INTERVAL '20:12' HOUR_MINUTE), 130.00),

-- 2005: Dune 2 (166 min = 2h 46m) (Status 2 - Coming Soon, but scheduled for future dates)
(7013, 2005, 3001, DATE_ADD(DATE(NOW()), INTERVAL '1 10:00' DAY_MINUTE), DATE_ADD(DATE(NOW()), INTERVAL '1 12:46' DAY_MINUTE), 90.00),
(7014, 2005, 3002, DATE_ADD(DATE(NOW()), INTERVAL '1 14:00' DAY_MINUTE), DATE_ADD(DATE(NOW()), INTERVAL '1 16:46' DAY_MINUTE), 60.00),

-- 2006: Oppenheimer (180 min = 3h)
(7015, 2006, 3001, DATE_ADD(DATE(NOW()), INTERVAL '11:00' HOUR_MINUTE), DATE_ADD(DATE(NOW()), INTERVAL '14:00' HOUR_MINUTE), 85.00),
(7016, 2006, 3002, DATE_ADD(DATE(NOW()), INTERVAL '15:00' HOUR_MINUTE), DATE_ADD(DATE(NOW()), INTERVAL '18:00' HOUR_MINUTE), 55.00),
(7017, 2006, 3003, DATE_ADD(DATE(NOW()), INTERVAL '20:00' HOUR_MINUTE), DATE_ADD(DATE(NOW()), INTERVAL '23:00' HOUR_MINUTE), 115.00),

-- 2007: Spider-Man (140 min = 2h 20m)
(7018, 2007, 3002, DATE_ADD(DATE(NOW()), INTERVAL '09:30' HOUR_MINUTE), DATE_ADD(DATE(NOW()), INTERVAL '11:50' HOUR_MINUTE), 50.00),
(7019, 2007, 3002, DATE_ADD(DATE(NOW()), INTERVAL '13:00' HOUR_MINUTE), DATE_ADD(DATE(NOW()), INTERVAL '15:20' HOUR_MINUTE), 50.00),
(7020, 2007, 3002, DATE_ADD(DATE(NOW()), INTERVAL '16:30' HOUR_MINUTE), DATE_ADD(DATE(NOW()), INTERVAL '18:50' HOUR_MINUTE), 55.00),

-- 2008: Shawshank (Offline - No schedules)

-- 2009: Pulp Fiction (Offline - No schedules)

-- 2010: Kung Fu Panda 4 (94 min = 1h 34m) (Status 2 - Coming Soon)
(7021, 2010, 3002, DATE_ADD(DATE(NOW()), INTERVAL '2 10:00' DAY_MINUTE), DATE_ADD(DATE(NOW()), INTERVAL '2 11:34' DAY_MINUTE), 45.00),
(7022, 2010, 3003, DATE_ADD(DATE(NOW()), INTERVAL '2 14:00' DAY_MINUTE), DATE_ADD(DATE(NOW()), INTERVAL '2 15:34' DAY_MINUTE), 80.00);

SET FOREIGN_KEY_CHECKS = 1;
