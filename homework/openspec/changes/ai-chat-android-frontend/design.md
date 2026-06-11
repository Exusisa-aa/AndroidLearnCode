## Context

当前项目为 Android 课程作业，使用 Kotlin + Jetpack Compose 构建，minSdk 24（Android 7.0），targetSdk 36。后端 Spring AI 服务运行在 `localhost:8080`，暴露出流式 SSE 接口。需要在此空白模板项目基础上搭建完整的 AI 聊天前端。

**约束**：
- API 24+，需考虑低版本 Android 对 OkHttp/SSL 的兼容性
- 模拟器访问宿主机 `localhost` 需使用 `10.0.2.2`
- 后端接口格式：`GET http://<host>:8080/ai/chat?prompt=xxx&model=<model>`，假设 model 参数通过 query string 传递
- 流式返回格式假设为 SSE 行式 (`data: <chunk>\n\n`)

## Goals / Non-Goals

**Goals:**
- 实现流式聊天消息的完整生命周期：输入 Prompt → 发送请求 → 逐字接收并渲染 → 展示完整回答
- 提供模型切换 UI，支持 DeepSeek / 豆包 / 千问 三个模型
- 支持 Markdown 基本语法渲染（标题、粗体、斜体、代码块、行内代码、列表、分隔线）
- 使用标准 Android 架构（ViewModel + StateFlow + Compose）

**Non-Goals:**
- 多轮对话/对话历史持久化（仅单次提问）
- 图片/文件上传
- 用户认证与授权
- 消息本地存储
- 后端 api-key 配置界面（api-key 由后端管理）

## Decisions

### 1. 网络层：OkHttp + 手动 SSE 解析

**选择**: 使用 OkHttp 发起异步 GET 请求，通过 `ResponseBody.source()` 逐行读取 SSE 流。

**备选方案考虑**:
- Ktor Client：功能强大但依赖较重，API 24 上需额外配置
- Retrofit + RxJava/Flow：对 SSE 无原生支持，需要大量适配代码
- OkHttp 原生方式：轻量、广泛兼容、解析逻辑完全可控

**理由**: OkHttp 已内置于 Android，兼容性最好，手动解析 SSE 的复杂度很低（逐行读、过滤空行、匹配 `data:` 前缀）。

### 2. Markdown 渲染：自建简化解析器

**选择**: 编写一个轻量级 Composable 渲染器，按行解析 Markdown 语法并映射到 Compose 样式。

**备选方案考虑**:
- `com.github.jeziellago:compose-markdown`: 第三方库但维护不稳定，可能不兼容 Compose BOM 最新版
- WebView + marked.js: 性能差、样式难以统一、需要在 JS 和 Kotlin 间桥接

**理由**: 课程作业范围只需覆盖常见语法，自建解析器代码量可控（约 200-300 行），避免第三方依赖兼容性风险，也体现了技术实现能力。

### 3. 架构模式：单 ViewModel + StateFlow

**选择**: 使用 `ChatViewModel` 统一管理聊天状态，通过 `StateFlow<ChatUiState>` 驱动 UI。

**ChatUiState 结构**:
```
- currentModel: String (deepseek / doubao / qwen)
- messages: List<ChatMessage> (每条消息含 role + content)
- isStreaming: Boolean
- inputText: String
```

**理由**: 功能规模小，单 ViewModel 即可覆盖。不用 Room/DataStore 等持久化因为明确是非目标。StateFlow 天然适合流式更新。

### 4. 模拟器 localhost 访问

**选择**: 在代码中硬编码 `http://10.0.2.2:8080/ai/chat` 作为 Base URL，同时在 AndroidManifest 中声明 `usesCleartextTraffic=true`。

**理由**: `10.0.2.2` 是 Android 模拟器访问宿主机 localhost 的标准映射。`usesCleartextTraffic` 允许 HTTP 明文流量（本地调试场景必需）。

### 5. 项目包结构

```
com.example.homework/
├── MainActivity.kt          # Activity 入口
├── ui/
│   ├── chat/
│   │   ├── ChatScreen.kt         # 聊天主界面
│   │   ├── ChatViewModel.kt      # 聊天状态管理
│   │   ├── MessageBubble.kt      # 消息气泡组件
│   │   ├── ModelSelector.kt      # 模型选择器
│   │   └── MarkdownText.kt       # Markdown 渲染组件
│   └── theme/
│       ├── Color.kt, Theme.kt, Type.kt  # 现有主题文件
└── network/
    └── SseClient.kt          # SSE 流式请求客户端
```

## Risks / Trade-offs

- [风险] 自建 Markdown 解析器可能遗漏某些语法或边界情况 → 先覆盖 80% 常用语法，后续可替换为成熟库
- [风险] OkHttp 在 UI 线程上的回调处理 → 用 `Dispatchers.IO` 做网络请求，`withContext(Dispatchers.Main)` 切回主线程
- [风险] SSE 流在 Android 生命周期管理中可能泄漏 → ViewModel `onCleared()` 中取消协程，配合 `viewModelScope` 自动管理
- [权衡] 不做多轮对话意味着每次提问是独立请求，简化了后端与前端的状态管理，但限制了用户体验

## Open Questions

- 后端 SSE 的具体响应格式？（假设为标准 `data:` 行格式）
- 模型参数是通过 query string (`&model=xxx`) 还是 header 传递？（设计假设为 query string，实现时根据实际接口调整）
- 是否需要消息发送前的前端校验（空 prompt 拦截）？（设计包含此项）
