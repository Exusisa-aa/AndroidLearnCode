## ADDED Requirements

### Requirement: 模型选择器 UI
系统 SHALL 提供一个模型切换选择器，允许用户在 DeepSeek、豆包、千问之间切换当前使用的 AI 模型。

#### Scenario: 默认模型
- **WHEN** 应用首次启动
- **THEN** 模型选择器显示默认模型（DeepSeek）

#### Scenario: 用户切换模型
- **WHEN** 用户点击模型选择器并选择「豆包」
- **THEN** 当前模型更新为「豆包」，后续所有聊天请求携带 `model=doubao` 参数

#### Scenario: 切换模型不影响已有消息
- **WHEN** 用户切换模型时聊天列表已有消息
- **THEN** 已有消息不受影响，仅后续新请求使用新模型

### Requirement: 模型标识显示
系统 SHALL 在每个 AI 回复气泡中标识使用的模型名称。

#### Scenario: 模型名称显示
- **WHEN** AI 回复一条消息
- **THEN** 消息气泡底部显示对应模型名称（如 "DeepSeek"、"豆包"、"千问"）

### Requirement: 模型参数传递
系统 SHALL 在每次 SSE 请求中携带当前选中的模型参数。

#### Scenario: 请求包含模型参数
- **WHEN** 当前选中模型为「千问」且用户发送 Prompt
- **THEN** 请求 URL 包含 `model=qwen` 查询参数
