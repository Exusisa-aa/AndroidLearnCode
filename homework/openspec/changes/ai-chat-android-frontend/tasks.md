## 1. 项目配置

- [x] 1.1 在 `build.gradle.kts` 中添加 OkHttp 依赖
- [x] 1.2 在 `AndroidManifest.xml` 中添加 INTERNET 权限和 `usesCleartextTraffic=true`
- [x] 1.3 在 `AndroidManifest.xml` 中修改 `networkSecurityConfig` 允许明文流量（或全局 `usesCleartextTraffic`）

## 2. 网络层 — SSE 流式客户端

- [x] 2.1 创建 `network/SseClient.kt`，封装 OkHttp GET 请求，支持 `prompt` 和 `model` 参数
- [x] 2.2 实现 SSE 响应逐行解析：通过 `BufferedSource.source()` 读取 ResponseBody，按行匹配 `data:` 前缀提取 chunk
- [x] 2.3 使用 `callbackFlow` 将 SSE 事件包装为 Kotlin Flow，供 ViewModel 消费
- [x] 2.4 处理错误场景：连接失败、超时、服务端非 200 响应，通过 Flow 的 `catch` 向下游发送错误

## 3. 聊天核心 — ViewModel 与状态管理

- [x] 3.1 创建 `ui/chat/ChatViewModel.kt`，定义 `ChatUiState` data class（输入文本、消息列表、流式状态、当前模型）
- [x] 3.2 实现 `sendMessage()` 方法：校验空输入 → 添加用户消息 → 发起 SSE 请求 → 逐 chunk 追加 AI 消息
- [x] 3.3 在 `onCleared()` 中取消正在进行的 SSE 请求协程，防止泄漏
- [x] 3.4 流式传输过程中锁定输入框（`isStreaming=true`），结束后解锁

## 4. 聊天 UI — 消息列表与输入

- [x] 4.1 创建 `ui/chat/ChatScreen.kt`，实现对话界面的脚手架布局（消息列表 + 底部输入栏）
- [x] 4.2 创建 `ui/chat/MessageBubble.kt`，实现用户/AI 两种气泡样式（颜色、对齐方向区分）
- [x] 4.3 实现消息列表的 `LazyColumn`，并绑定 ViewModel 的 `messages` StateFlow
- [x] 4.4 实现消息列表自动滚动到底部（`LazyListState.animateScrollToItem`）
- [x] 4.5 实现输入框 + 发送按钮，对接 `sendMessage()`

## 5. 模型切换

- [x] 5.1 创建 `ui/chat/ModelSelector.kt`，实现模型选择器 Composable（DropdownMenu 或 SegmentedButton）
- [x] 5.2 模型选项定义为枚举常量：DeepSeek（`deepseek`）、豆包（`doubao`）、千问（`qwen`），含中文显示名和请求参数值
- [x] 5.3 模型切换后更新 ViewModel 中的 `currentModel` 并传递给 SSE 请求的 model 参数
- [x] 5.4 AI 消息气泡底部显示当前使用的模型名称

## 6. Markdown 渲染

- [x] 6.1 创建 `ui/chat/MarkdownText.kt`，实现 Markdown 行级解析器（按行分类：标题、代码块、列表、普通文本等）
- [x] 6.2 实现标题渲染（`#` ~ `####`），映射到 Material3 `TextStyle` 的 `headlineSmall` 等
- [x] 6.3 实现行内样式（粗体 `**`、斜体 `*`、行内代码 `` ` ``）的解析与富文本组合
- [x] 6.4 实现代码块渲染（` ``` `），使用等宽字体 + 深色背景 + 内边距
- [x] 6.5 实现有序/无序列表渲染（`- `, `* `, `1. ` 等前缀）
- [x] 6.6 实现 Markdown 链接 `[text](url)` 解析与可点击跳转
- [x] 6.7 纯文本行以默认样式兜底渲染

## 7. 入口整合与清理

- [x] 7.1 重构 `MainActivity.kt`，将 `setContent` 内容替换为 `ChatScreen`
- [x] 7.2 删除旧的 `Greeting` 和 `GreetingPreview` Composable
- [x] 7.3 运行应用并验证整体流程：切换模型 → 输入 prompt → 发送 → 看到流式 Markdown 回复
