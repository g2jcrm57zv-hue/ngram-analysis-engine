# NGram Analysis Engine

## 项目简介
本项目是一个基于 Google Books NGrams 数据集的历史词频分析引擎。通过处理海量的历史文本数据，本项目能够分析并可视化特定单词或词组在数百年间的出现频率变化趋势。

## 核心功能
1. **高效数据处理**：实现了对大规模词频文件（words file）和年度统计文件（counts file）的解析与读取，支持海量数据的快速检索。
2. **时间序列建模**：设计并实现了 `TimeSeries` 数据结构，支持时间跨度内数据的精准截取、逐年求和（plus）以及相对频率计算（dividedBy）等数学运算。
3. **词频趋势分析**：`NGramMap` 模块提供底层支持，可查询特定单词的历史记录，并计算其在所有出版物中的相对权重。
4. **可视化交互**：集成 Spark Java 微框架搭建后端服务器，通过 Web 界面将复杂的后台计算结果转化为直观的动态折线图。

## 技术栈
- **语言**：Java 17
- **框架**：Spark Java (Web Server)
- **数据结构**：TreeMap, HashMap, TimeSeries
- **绘图库**：XChart

## 数据说明
项目运行依赖于 Google NGrams 提供的 CSV 数据集。由于数据集体积较大，未包含在代码仓库中。请确保项目根目录下存在 `data/` 文件夹，并包含以下结构的子文件夹：
- `data/ngrams/`：包含各类词频统计 CSV 文件。

## 项目文档与资源
有关本项目的更多详细说明或相关资源，请参阅以下链接：
[https://drive.google.com/file/d/1xGTZqCo5maiZjA307OPocmKDOTYlJXnz/view](https://drive.google.com/file/d/1xGTZqCo5maiZjA307OPocmKDOTYlJXnz/view)

## 运行方式
1. 确保已正确配置 `data` 文件夹及其子文件。
2. 运行 `main.Main` 类启动后端服务器。
3. 服务器启动后，通过浏览器访问日志中提示的本地地址（通常为 http://localhost:4567/ngordnet_2a.html）进行交互。
