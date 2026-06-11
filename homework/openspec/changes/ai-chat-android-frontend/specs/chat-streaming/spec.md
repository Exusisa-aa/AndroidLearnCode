## ADDED Requirements

### Requirement: 发起流式聊天请求
系统 SHALL 允许用户输入 Prompt 文本并发送到后端 `/ai/chat` 接口，通过 SSE 协议逐字接收并展示 AI 回复。

#### Scenario: 用户发送 Prompt 成功接收流式回复
- **WHEN** 用户在输入框中输入 "介绍一下 Android Jetpack" 并点击发送按钮
- **THEN** 系统向后端 `http://10.0.2.2:8080/ai/chat?prompt=xxx&model=xxx` 发起 GET 请求，并在消息列表中逐字追加 AI 回复内容，直到收到 SSE 流结束信号

#### Scenario: 发送空 Prompt
- **WHEN** 用户点击发送按钮且输入框内容为空或仅含空白字符
- **THEN** 系统不发起任何网络请求，提示用户输入内容

#### Scenario: 网络请求失败
- **WHEN** 网络请求因超时、连接拒绝或服务端错误而失败
- **THEN** 系统在消息列表中显示错误提示，停止 loading 状态

#### Scenario: 流式接收过程中取消
- **WHEN** AI 正在回复过程中，用户关闭了 Activity
- **THEN** 系统取消正在进行的 SSE 请求，释放相关网络资源

### Requirement: 消息列表展示
系统 SHALL 以聊天气泡形式展示全部消息，区分用户消息和 AI 消息。

#### Scenario: 显示用户消息
- **WHEN** 用户发送了一条 Prompt
- **THEN** 消息列表末尾出现右对齐的用户消息气泡，显示用户输入的文本

#### Scenario: 显示 AI 流式消息
- **WHEN** AI 正在回复
- **THEN** 消息列表末尾出现左对齐的 AI 消息气泡，内容逐字更新

#### Scenario: 消息列表自动滚动
- **WHEN** 新消息到达或流式内容更新时
- **THEN** 列表自动滚动到底部，确保最新内容可见

### Requirement: 消息状态管理
系统 SHALL 维护和管理聊天消息的状态，并通过 ViewModel 驱动 UI 更新。

#### Scenario: 流式状态切换
- **WHEN** AI 开始回复
- **THEN** `isStreaming` 状态为 true，输入区域不可编辑
- **WHEN** AI 回复结束
- **THEN** `isStreaming` 状态为 false，输入区域恢复可编辑
