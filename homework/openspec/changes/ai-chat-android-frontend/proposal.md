## Why

Spring AI 后端已实现流式输出接口 `http://localhost:8080/ai/chat?prompt=xxx`，但缺乏一个可交互的 Android 客户端来消费并展示 SSE（Server-Sent Events）流式响应。用户需要在手机上选择不同 AI 模型进行对话，并以 Markdown 格式获得可读性强的回复。本项目为 Android 课程作业（API 24+），需要快速交付一个功能完整的前端。

## What Changes

- 新增 AI 聊天主界面，支持输入 Prompt 并展示流式回复内容
- 新增模型切换组件：支持 DeepSeek、豆包（Doubao）、千问（Qwen）三种模型的切换
- 集成 SSE 流式网络请求能力，从后端接口实时接收并逐字渲染 AI 回复
- 集成 Markdown 渲染能力，将 AI 返回的 Markdown 格式文本正确渲染（标题、列表、代码块、粗体、斜体等）
- 将现有 Hello World 模板代码替换为聊天功能

## Capabilities

### New Capabilities

- `chat-streaming`: 基于 SSE 的流式聊天功能，负责网络请求、流式数据解析、消息列表展示与管理
- `model-switching`: AI 模型切换功能，提供模型选择器 UI，支持 DeepSeek / 豆包 / 千问 三种模型
- `markdown-rendering`: Markdown 文档渲染能力，将 AI 回复中的 Markdown 语法转为富文本样式展示

### Modified Capabilities

<!-- 无现有能力需要修改 -->

## Impact

- **依赖**: 新增 OkHttp 用于 SSE 网络请求，新增 Markdown 渲染库（如 compose-markdown 或类似 Compose Multiplatform 方案）
- **代码**: 替换 `MainActivity.kt` 中的模板代码为聊天界面；新增 `ui/chat/` 目录存放聊天相关 Composable；新增 `network/` 目录存放流式请求客户端
- **配置**: `build.gradle.kts` 新增网络和 Markdown 相关依赖；`AndroidManifest.xml` 新增 INTERNET 权限
