# NGram Analysis Engine

## Project Overview
This project is a historical word frequency analysis engine based on the Google Books NGrams dataset. By processing vast amounts of historical text data, the system analyzes and visualizes the frequency trends of specific words or phrases over several centuries.

## Key Features
1.  **Efficient Data Processing**: Implements parsing and loading for large-scale word frequency files and annual statistical count files, supporting rapid retrieval of massive datasets.
2.  **Time Series Modeling**: Includes a custom `TimeSeries` data structure that supports precise data slicing within specific time frames and mathematical operations such as year-wise summation and relative frequency calculation.
3.  **Frequency Trend Analysis**: The `NGramMap` module provides a high-level API to query historical records and calculate the relative weight of words compared to the total volume of published works.
4.  **Interactive Visualization**: Integrates the Spark Java micro-framework to power a backend server, transforming complex backend computations into intuitive, dynamic line charts via a web interface.

## Tech Stack
* **Language**: Java 17
* **Framework**: Spark Java (Web Server)
* **Data Structures**: TreeMap, HashMap, TimeSeries
* **Charting Library**: XChart

## Data Configuration
Due to the large file sizes, the raw Google NGrams datasets are not included in this repository. To run the project, ensure that a `data/` directory exists in the project root with the following structure:
* `data/ngrams/`: Contains various word frequency CSV files.

## Project Documentation and Resources
For further details regarding the project specifications or related resources, please refer to the following link:
[https://drive.google.com/file/d/1xGTZqCo5maiZjA307OPocmKDOTYlJXnz/view](https://drive.google.com/file/d/1xGTZqCo5maiZjA307OPocmKDOTYlJXnz/view)

## Getting Started
1.  Verify that the `data` folder and its subdirectories are correctly placed in the project root.
2.  Run the `main.Main` class to start the backend server.
3.  Once the server is ignited, access the local address provided in the console (typically http://localhost:4567/ngordnet_2a.html) using a web browser to interact with the engine.
