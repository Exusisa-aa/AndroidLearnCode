## ADDED Requirements

### Requirement: Markdown 基础语法渲染
系统 SHALL 解析并渲染 AI 回复中的 Markdown 语法，转换为对应的富文本样式。

#### Scenario: 渲染标题
- **WHEN** AI 回复包含 `# 一级标题`、`## 二级标题`、`### 三级标题`
- **THEN** 系统以对应大小的加粗字体渲染标题文本

#### Scenario: 渲染粗体和斜体
- **WHEN** AI 回复包含 `**粗体**` 和 `*斜体*`
- **THEN** 系统以粗体和斜体样式分别渲染

#### Scenario: 渲染行内代码
- **WHEN** AI 回复包含 `` `val x = 1` ``
- **THEN** 系统以等宽字体 + 浅色背景渲染行内代码

#### Scenario: 渲染代码块
- **WHEN** AI 回复包含 ` ```kotlin ... ``` `
- **THEN** 系统以等宽字体 + 深色背景渲染代码块，代码块内有独立的内边距和圆角

#### Scenario: 渲染无序列表
- **WHEN** AI 回复包含 `- 项目1` 和 `* 项目2`
- **THEN** 系统渲染为带缩进和符号的列表项

#### Scenario: 渲染有序列表
- **WHEN** AI 回复包含 `1. 第一项`
- **THEN** 系统渲染为带数字编号的列表项

### Requirement: 普通文本兜底
系统 SHALL 对不匹配任何 Markdown 语法的行以普通文本样式渲染。

#### Scenario: 纯文本行
- **WHEN** AI 回复包含无 Markdown 标记的普通文本
- **THEN** 系统以正文样式（常规字号、行高）渲染

### Requirement: Markdown 中的链接渲染
系统 SHALL 将 Markdown 链接语法 `[text](url)` 渲染为可点击的链接样式文本。

#### Scenario: 渲染并点击链接
- **WHEN** AI 回复包含 `[查看文档](https://example.com)`
- **THEN** 系统渲染为蓝色可点击链接，点击后通过系统浏览器打开 URL
