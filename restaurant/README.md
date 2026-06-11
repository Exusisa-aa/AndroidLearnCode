# 餐厅点餐系统 (Restaurant Ordering System)

## 项目简介
这是一个基于 Android 前端和 Spring Boot 后端的餐厅点餐系统。用户可以通过 Android App 浏览菜单、查看菜品详情（含评论）、加入购物车、管理收货地址、提交订单以及查看历史订单。

## 技术栈

### 后端 (Server)
*   **语言**: Java 17+
*   **框架**: Spring Boot 3.x
*   **ORM**: MyBatis-Plus
*   **数据库**: MySQL 8.0
*   **构建工具**: Maven
*   **其他**: Lombok, FastJson

### 前端 (Android App)
*   **语言**: Kotlin
*   **架构**: MVVM (ViewModel + LiveData)
*   **网络**: Retrofit + OkHttp
*   **图片加载**: Coil
*   **UI组件**: Material Design 3, ConstraintLayout, RecyclerView, DrawerLayout
*   **构建工具**: Gradle

## 功能模块

1.  **用户系统**
    *   手机号验证码登录（模拟）。
    *   用户信息管理。

2.  **菜单浏览**
    *   左侧分类导航，右侧菜品列表联动。
    *   菜品图片、价格、描述展示。

3.  **菜品详情**
    *   查看大图。
    *   **用户评论**：根据菜品动态生成的用户评价，支持显示头像（Robohash）。
    *   加入购物车。

4.  **购物车**
    *   底部弹窗展示已选商品。
    *   调整数量、清空购物车。
    *   计算总价。

5.  **订单系统**
    *   **提交订单**：选择默认地址进行结算。
    *   **历史订单**：侧边栏入口，查看过往订单记录及详情。

6.  **地址管理**
    *   地址列表展示。
    *   新增/编辑收货地址（支持自动解析地区）。
    *   设置默认地址。

## 目录结构

```
restaurant/
├── restaurant-app/       # Android 客户端项目
│   ├── app/src/main/java/com/restaurant/app/
│   │   ├── data/         # 数据层 (API, Model, Repository)
│   │   ├── ui/           # 界面层 (Activity, Fragment, Adapter, ViewModel)
│   │   └── utils/        # 工具类
│   └── ...
├── restaurant-server/    # Spring Boot 服务端项目
│   ├── src/main/java/com/restaurant/
│   │   ├── controller/   # 控制器
│   │   ├── entity/       # 实体类
│   │   ├── service/      # 业务逻辑
│   │   └── mapper/       # 数据访问
│   ├── images/           # 图片资源目录
│   └── ...
├── db_schema.sql         # 数据库表结构脚本
├── seed_data.sql         # 数据库初始数据脚本
└── README.md             # 项目说明文档
```

## 运行指南

### 1. 数据库准备
1.  安装 MySQL 数据库。
2.  创建数据库 `restaurant`。
3.  执行 `db_schema.sql` 建表。
4.  执行 `seed_data.sql` 导入初始数据。

### 2. 后端启动
1.  使用 IntelliJ IDEA 打开 `restaurant-server` 目录。
2.  修改 `application.yml` 中的数据库连接配置（用户名/密码）。
3.  运行 `RestaurantApplication.java`。
4.  服务默认运行在 `http://localhost:8080`。

### 3. 图片资源
*   在 `restaurant-server` 根目录下创建 `images` 文件夹。
*   放入菜品图片（文件名需与数据库 `dish.image` 字段一致，如 `kung_pao_chicken.jpg`）。

### 4. 前端运行
1.  使用 Android Studio 打开 `restaurant-app` 目录。
2.  修改 `NetworkModule.kt` 中的 `BASE_URL`：
    *   模拟器运行：使用 `http://10.0.2.2:8080/`
    *   真机运行：使用电脑局域网 IP，如 `http://192.168.1.100:8080/`
3.  Sync Gradle 并运行 App。

## 注意事项
*   **登录**：任意手机号输入验证码即可登录（测试模式）。
*   **图片显示**：确保后端 `images` 目录下有对应的图片文件，否则 App 端显示占位图。
*   **网络**：Android 9+ 默认禁止明文 HTTP 请求，本项目已在 `AndroidManifest.xml` 中配置 `android:usesCleartextTraffic="true"` 允许 HTTP。

## 贡献
欢迎提交 Issue 和 Pull Request。
