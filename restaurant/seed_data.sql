-- SQL 测试数据
-- 数据库: restaurant_db

USE restaurant_db;

-- 1. 插入分类 (Type: 1 菜品, 2 套餐)
INSERT INTO `category` (`id`, `type`, `name`, `sort`, `create_time`, `update_time`, `create_user`, `update_user`) VALUES
(1, 1, '店长推荐', 1, NOW(), NOW(), 1, 1),
(2, 1, '热销主菜', 2, NOW(), NOW(), 1, 1),
(3, 1, '开胃凉菜', 3, NOW(), NOW(), 1, 1),
(4, 1, '精美甜点', 4, NOW(), NOW(), 1, 1),
(5, 1, '饮料酒水', 5, NOW(), NOW(), 1, 1);

-- 2. 插入菜品
INSERT INTO `dish` (`id`, `name`, `category_id`, `price`, `image`, `description`, `status`, `sort`, `create_time`, `update_time`, `create_user`, `update_user`) VALUES
-- 分类 1: 店长推荐
(1, '宫保鸡丁', 1, 38.00, 'kung_pao_chicken.jpg', '精选鸡腿肉，配以花生米、辣椒爆炒，酸甜适口，辣而不燥。', 1, 1, NOW(), NOW(), 1, 1),
(2, '麻婆豆腐', 1, 22.00, 'mapo_tofu.jpg', '传统川菜，麻辣鲜香，豆腐嫩滑，下饭神器。', 1, 2, NOW(), NOW(), 1, 1),
(3, '红烧牛肉面', 1, 28.00, 'beef_noodle.jpg', '私房秘制汤底，大块牛肉，劲道面条。', 1, 3, NOW(), NOW(), 1, 1),
(4, '手工水饺', 1, 18.00, 'dumplings.jpg', '皮薄馅大，猪肉白菜馅，现包现煮（10个）。', 1, 4, NOW(), NOW(), 1, 1),

-- 分类 2: 热销主菜
(5, '菠萝咕咾肉', 2, 42.00, 'sweet_sour_pork.jpg', '外酥里嫩的里脊肉，搭配新鲜菠萝，酸甜开胃。', 1, 1, NOW(), NOW(), 1, 1),
(6, '北京烤鸭', 2, 88.00, 'peking_duck.jpg', '果木炭火烤制，皮酥肉嫩，配荷叶饼、葱丝、甜面酱。', 1, 2, NOW(), NOW(), 1, 1),
(7, '红烧肉', 2, 45.00, 'braised_pork.jpg', '精选五花肉，肥而不腻，入口即化。', 1, 3, NOW(), NOW(), 1, 1),
(8, '麻辣小龙虾', 2, 98.00, 'crayfish.jpg', '鲜活小龙虾，秘制香料爆炒，麻辣过瘾。', 1, 4, NOW(), NOW(), 1, 1),
(9, '扬州炒饭', 2, 25.00, 'fried_rice.jpg', '粒粒分明，配料丰富，包含火腿、虾仁、鸡蛋。', 1, 5, NOW(), NOW(), 1, 1),
(10, '左宗棠鸡', 2, 40.00, 'general_tso.jpg', '外皮酥脆，酱汁浓郁，微辣带甜。', 1, 6, NOW(), NOW(), 1, 1),

-- 分类 3: 开胃凉菜
(11, '春卷', 3, 12.00, 'spring_rolls.jpg', '外皮金黄酥脆，内馅鲜美素菜。', 1, 1, NOW(), NOW(), 1, 1),
(12, '盐水毛豆', 3, 8.00, 'edamame.jpg', '清爽解腻，下酒好菜。', 1, 2, NOW(), NOW(), 1, 1),
(13, '芝麻海草', 3, 15.00, 'seaweed_salad.jpg', '日式风味，口感爽脆。', 1, 3, NOW(), NOW(), 1, 1),
(14, '酸辣汤', 3, 10.00, 'hot_sour_soup.jpg', '酸辣开胃，料足味美。', 1, 4, NOW(), NOW(), 1, 1),

-- 分类 4: 精美甜点
(15, '芒果布丁', 4, 15.00, 'mango_pudding.jpg', '浓郁芒果香，口感顺滑。', 1, 1, NOW(), NOW(), 1, 1),
(16, '葡式蛋挞', 4, 10.00, 'egg_tart.jpg', '外皮酥松，蛋香浓郁（2个）。', 1, 2, NOW(), NOW(), 1, 1),
(17, '豆沙芝麻球', 4, 12.00, 'sesame_balls.jpg', '外酥里糯，香甜红豆沙馅。', 1, 3, NOW(), NOW(), 1, 1),
(18, '脆皮炸香蕉', 4, 14.00, 'fried_bananas.jpg', '外脆里软，淋上蜂蜜。', 1, 4, NOW(), NOW(), 1, 1),

-- 分类 5: 饮料酒水
(19, '港式冻柠茶', 5, 12.00, 'lemon_tea.jpg', '红茶底，新鲜柠檬，解渴消暑。', 1, 1, NOW(), NOW(), 1, 1),
(20, '珍珠奶茶', 5, 18.00, 'bubble_tea.jpg', '香浓奶茶，Q弹珍珠。', 1, 2, NOW(), NOW(), 1, 1),
(21, '可口可乐', 5, 5.00, 'cola.jpg', '冰镇罐装。', 1, 3, NOW(), NOW(), 1, 1),
(22, '鲜榨橙汁', 5, 15.00, 'orange_juice.jpg', '100% 鲜榨，补充维生素。', 1, 4, NOW(), NOW(), 1, 1),
(23, '茉莉花茶', 5, 8.00, 'green_tea.jpg', '热饮，清香怡人。', 1, 5, NOW(), NOW(), 1, 1);

