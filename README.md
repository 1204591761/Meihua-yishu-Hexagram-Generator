# 梅花易数 🌸

一款基于传统《梅花易数》的 Android 占卜应用，支持时间起卦、数字起卦、汉字起卦，并提供 AI 智能解卦功能。

![Platform](https://img.shields.io/badge/Platform-Android-green.svg)
![License](https://img.shields.io/badge/License-MIT-blue.svg)
![Language](https://img.shields.io/badge/Language-Java%2FHTML%2FCSS%2FJS-orange.svg)

## ✨ 功能特点

- 🔮 **多种起卦方式**
  - 时间起卦：根据当前时间自动起卦
  - 数字起卦：输入任意数字组合
  - 汉字起卦：输入汉字自动转换为卦象

- 🤖 **AI 智能解卦**
  - 集成 DeepSeek AI 大模型
  - 提供专业、详细的卦象解读
  - 支持一键复制解卦模板

- 📜 **历史记录**
  - 自动保存所有起卦记录
  - 支持查看历史解卦结果

- 📚 **起卦法说明**
  - 内置详细的起卦方法教程
  - 帮助初学者快速入门

## 📱 截图预览

> 应用界面简洁优雅，采用传统中国风设计

## 🚀 快速开始

### 环境要求

- Android Studio Arctic Fox 或更高版本
- Android SDK 21+
- Java 8+

### 构建步骤

1. 克隆仓库
```bash
git clone https://github.com/yourusername/meihua-yishu.git
cd meihua-yishu
```

2. 在 Android Studio 中打开项目

3. 同步 Gradle 并构建
```bash
./gradlew build
```

4. 运行到设备或模拟器
```bash
./gradlew installDebug
```

## 🔧 配置 AI 解卦

本应用使用 DeepSeek API 进行智能解卦，需要用户自行配置 API Key：

1. 访问 [DeepSeek 开放平台](https://platform.deepseek.com/) 注册账号
2. 获取 API Key
3. 在应用设置中输入 API Key

> ⚠️ 注意：API Key 仅存储在本地，不会上传到任何服务器

## 📖 使用说明

### 起卦流程

1. **选择起卦方式**：时间 / 数字 / 汉字
2. **输入信息**：根据提示输入相应内容
3. **点击起卦**：系统自动生成卦象
4. **查看解卦**：点击"AI解卦"获取详细解读

### 卦象说明

- **本卦**：当前事物的主要状态
- **互卦**：事物发展的中间过程
- **变卦**：事物的最终结果或变化趋势

## 🏗️ 项目结构

```
MeihuaAndroid/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── assets/
│   │   │   │   └── index.html      # 前端界面（WebView）
│   │   │   ├── java/
│   │   │   │   └── com/example/meihua/
│   │   │   │       └── MainActivity.java  # Android 主活动
│   │   │   └── res/
│   │   └── test/
│   └── build.gradle
├── build.gradle
├── settings.gradle
└── gradle.properties
```

## 🤝 贡献指南

欢迎提交 Issue 和 Pull Request！

1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 打开 Pull Request

## 📄 开源协议

本项目采用 [MIT](LICENSE) 协议开源。

## 🙏 致谢

- [DeepSeek](https://deepseek.com/) - 提供 AI 解卦能力
- 《梅花易数》- 邵雍（北宋）

## 📮 联系我们

如有问题或建议，欢迎通过以下方式联系：

- 提交 [Issue](https://github.com/yourusername/meihua-yishu/issues)
- 发送邮件至：hytx2003@gmail.com

---

> ⚠️ **免责声明**：本应用仅供娱乐和学习传统文化使用，占卜结果仅供参考，请勿用于重大决策。
