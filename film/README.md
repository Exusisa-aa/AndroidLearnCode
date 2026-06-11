# 🎬 电影院在线购票系统 (Cinema Ticketing System)

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.22-blue.svg)](https://kotlinlang.org)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.4-green.svg)](https://spring.io/projects/spring-boot)
[![MyBatis Plus](https://img.shields.io/badge/MyBatis%20Plus-3.5.7-blue.svg)](https://baomidou.com)
[![Material Design 3](https://img.shields.io/badge/Material%20Design-3-purple.svg)](https://m3.material.io)

这是一个基于 **Android (Kotlin)** 客户端和 **Spring Boot (Java)** 服务端的全栈电影购票系统演示项目。采用现代化的 **MVVM** 架构和 **Material Design 3** 设计风格，实现了从电影浏览、详情查看、选座购票到订单生成的完整业务流程。

---

## ✨ 核心亮点 (Key Highlights)

*   **沉浸式体验**: Android 端采用 `CoordinatorLayout` + `CollapsingToolbarLayout` 实现丝滑的视差滚动效果。
*   **高性能选座**: 自定义 `SeatView` 控件，支持手势缩放、拖拽平移，基于 `Canvas` 绘制，轻松支撑上千座位的渲染与交互。
*   **高并发防超卖**: 后端利用数据库唯一索引 (`UNIQUE KEY`) 结合事务机制，实现高效的乐观锁选座，确保座位不被重复售卖。
*   **现代化架构**: 
    *   Android: Jetpack (ViewModel, LiveData, Navigation), Hilt 依赖注入, Retrofit 网络库。
    *   Backend: Spring Boot 3, MyBatis-Plus, RESTful API 设计, 全局异常处理。

---

## 🛠️ 技术栈 (Tech Stack)

### 📱 Android 客户端
| 组件 | 说明 |
| :--- | :--- |
| **语言** | Kotlin 1.9 |
| **架构模式** | MVVM (Model-View-ViewModel) |
| **UI 框架** | XML Layouts, Material Design 3 |
| **导航** | Jetpack Navigation Component (Single Activity) |
| **网络请求** | Retrofit 2 + OkHttp 3 + Gson |
| **依赖注入** | Dagger Hilt |
| **图片加载** | Glide 4 |
| **异步处理** | Kotlin Coroutines |

### ☕ 后端服务端
| 组件 | 说明 |
| :--- | :--- |
| **语言** | Java 17 |
| **核心框架** | Spring Boot 3.2.4 |
| **ORM 框架** | MyBatis-Plus 3.5.7 |
| **数据库** | MySQL 8.0 |
| **工具库** | Lombok, Hutool, Maven |

---

## ⚙️ 环境要求 (Prerequisites)

在开始之前，请确保您的开发环境满足以下要求：

*   **JDK**: OpenJDK 17 或更高版本
*   **Android Studio**: Iguana (2023.2.1) 或更高版本
*   **MySQL**: 8.0 或更高版本
*   **Maven**: 3.6+ (可选，IDEA 自带)

---

## 🚀 快速开始 (Getting Started)

### 1. 后端部署 (Backend)

1.  **创建数据库**:
    在 MySQL 中执行以下命令创建数据库：
    ```sql
    CREATE DATABASE film_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
    ```

2.  **初始化数据**:
    运行 SQL 脚本 [schema.sql](file:///backend-server/src/main/resources/db/schema.sql)。该脚本将创建所有必要的表（用户、电影、影厅、座位、排片、订单、票据）并插入测试数据。

3.  **配置连接**:
    打开 `backend-server/src/main/resources/application.yml`，修改数据库连接配置：
    ```yaml
    spring:
      datasource:
        username: your_username  # 替换为你的 MySQL 用户名
        password: your_password  # 替换为你的 MySQL 密码
    ```

4.  **启动服务**:
    运行 `BackendApplication.java`。启动成功后，服务将运行在 `http://localhost:8080`。

### 2. 客户端运行 (Android App)

1.  **导入项目**:
    使用 Android Studio 打开 `android-app` 目录。

2.  **配置网络**:
    打开 [NetworkModule.kt](file:///android-app/app/src/main/java/com/film/app/di/NetworkModule.kt)，检查 `baseUrl`：
    *   **Android 官方模拟器**: 使用 `http://10.0.2.2:8080/api/` (默认)。
    *   **真机 / 第三方模拟器 (Genymotion, 夜神等)**: 使用电脑的局域网 IP，例如 `http://192.168.1.100:8080/api/`。确保手机和电脑在同一 Wi-Fi 下。

3.  **运行应用**:
    连接设备或启动模拟器，点击 **Run** 按钮。

---

## 📱 功能模块 (Features)

### 🏠 首页 (Home)
*   **热门/即将上映**:通过 `TabLayout` + `ViewPager2` 切换不同状态的电影列表。
*   **电影卡片**: 展示海报、评分、名称等摘要信息。

### 🎬 电影详情 (Movie Detail)
*   **视差海报**: 向上滑动时海报逐渐折叠，标题栏渐变显示。
*   **详细信息**: 展示导演、演员、剧情简介、评分等。
*   **购票入口**: 点击“购票”按钮跳转至选座页面。

### 💺 选座购票 (Seat Selection)
*   **可视化选座**: 真实还原影厅座位布局。
*   **状态图例**:
    *   ⬜ 白色：可选座位
    *   🟥 红色：已售座位
    *   🟩 绿色：已选座位
    *   💑 情侣座：支持特殊图标或连座处理（逻辑已预留）
*   **手势交互**: 双指缩放查看全貌，单指拖拽移动视角。

### 📦 订单系统 (Order)
*   **下单接口**: 提交选座信息，后端验证座位状态并锁定。
*   **并发控制**: 利用数据库约束防止多用户购买同一座位。

---

## 🗄️ 数据库设计 (Database Schema)

| 表名 | 描述 | 关键字段 |
| :--- | :--- | :--- |
| `t_user` | 用户表 | id, username, phone, points |
| `t_movie` | 电影表 | id, title, poster_url, rating, status |
| `t_hall` | 影厅表 | id, name, total_rows, total_cols |
| `t_seat` | 座位表 | id, hall_id, row_num, col_num, status |
| `t_schedule` | 排片表 | id, movie_id, hall_id, start_time, price |
| `t_order` | 订单表 | id, order_no, user_id, total_amount, status |
| `t_ticket` | 影票表 | id, order_id, seat_id, qr_code |

---

## ❓ 常见问题 (FAQ)

**Q: App 显示 "Network Error" 或无法加载数据？**
A: 请检查以下几点：
1. 后端服务是否已成功启动？在浏览器访问 `http://localhost:8080/api/movie/list` 看看是否有 JSON 返回。
2. Android 代码中的 IP 地址是否配置正确？(见“快速开始”第2步)。
3. AndroidManifest.xml 中是否添加了网络权限 (已默认添加)。
4. 如果是真机调试，确保防火墙未拦截 8080 端口。

**Q: 如何添加新的电影或排片？**
A: 目前可以通过修改数据库中的 `t_movie` 和 `t_schedule` 表来添加数据，或者使用提供的 `schema.sql` 脚本重置测试数据。

---

## 📝 待办事项 (To-Do List)

- [ ] **用户认证**: 集成 JWT Token 实现登录/注册功能。
- [ ] **支付集成**: 模拟支付宝/微信支付流程。
- [ ] **我的订单**: 添加用户个人中心，查看历史订单和电子票二维码。
- [ ] **管理后台**: 开发 Web 端管理后台，用于排片和数据统计。

---

Copyright © 2026 FilmApp Project.
