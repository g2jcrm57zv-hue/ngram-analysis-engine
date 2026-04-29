package ngrams;

import java.sql.Time;
import java.util.Collection;
import java.util.HashMap;
import edu.princeton.cs.algs4.In;
import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;

/**
 * 一个提供实用方法的对象，用于查询 Google NGrams 数据集（或其子集）。
 *
 * NGramMap 存储了来自“单词文件（words file）”和“计数文件（counts file）”的相关数据。
 * 严格意义上它并不是一个 map（映射），但它确实提供了额外的功能。
 *
 * @author Josh Hug
 */
public class NGramMap {

    // 用于存储每个单词
    private HashMap<String, TimeSeries> wordMap;

    // 用于存储每年出版总字数
    private TimeSeries totalCounts;

    /**
     * 根据 WORDSFILENAME（单词文件名）和 COUNTSFILENAME（计数文件名）构造一个 NGramMap。
     */
    public NGramMap(String wordsFilename, String countsFilename) {

        // =========================================================
        // 准备阶段：初始化大柜子
        // =========================================================
        // wordMap 是柜子A：用来存每个单词对应的历史账本 (String -> TimeSeries)
        wordMap = new HashMap<>();
        // totalCounts 是柜子B：一本单独的账本，存人类每年出版图书的总单词数
        totalCounts = new TimeSeries();

        // =========================================================
        // 第一部分：读取并处理“单词文件” (例如 words.csv)
        // 文件格式示例： "apple\t2000\t1500\t42"
        // =========================================================
        // 使用 CS61B 提供的 In 类打开单词文件
        In wordsIn = new In(wordsFilename);

        // 只要文件里还有内容没读完，就一直循环读取
        while (!wordsIn.isEmpty()) {
            // 读出完整的一行数据
            String line = wordsIn.readLine();
            // 单词文件是用“制表符 (Tab)”隔开的，所以用 "\t" 进行切割成数组
            String[] parts = line.split("\t");

            // 提取切开后的各个部分，并把字符串转换成数学数字
            String word = parts[0];                      // 第1列：单词，例如 "apple"
            int year = Integer.parseInt(parts[1]);       // 第2列：年份，把 "2000" 转成 2000
            double count = Double.parseDouble(parts[2]); // 第3列：出现次数，把 "1500" 转成 1500.0
            // (注：第4列代表包含该词的书本数量，本次作业用不到，所以不用管)

            // 核心逻辑：把数据放进柜子A (wordMap)
            // 1. 如果柜子里还没有这个单词（说明是第一次遇到它）
            if (!wordMap.containsKey(word)) {
                // 就先给这个新单词建一本全新的、空白的“记账本” (TimeSeries) 放到柜子里
                wordMap.put(word, new TimeSeries());
            }

            // 2. 此时柜子里肯定有这个单词的记账本了。
            // 我们把它拿出来 (.get)，并把今年的年份(year)和次数(count)记录进去 (.put)
            wordMap.get(word).put(year, count);
        }

        // =========================================================
        // 第二部分：读取并处理“总字数文件” (例如 counts.csv)
        // 文件格式示例： "2000,1000000,50000,12000"
        // =========================================================
        // 打开总字数文件
        In countsIn = new In(countsFilename);

        while (!countsIn.isEmpty()) {
            // 读出完整的一行数据
            String line = countsIn.readLine();
            // 注意这里：总字数文件是用“逗号”隔开的！所以用 "," 进行切割
            String[] parts = line.split(",");

            // 提取年份和当年的总词数
            int year = Integer.parseInt(parts[0]);            // 第1列：年份
            double totalWords = Double.parseDouble(parts[1]); // 第2列：当年的总单词数

            // 直接把年份和总字数记录到柜子B (totalCounts) 的账本里
            totalCounts.put(year, totalWords);
        }
    }

    /**
     * 提供 WORD（单词）在 STARTYEAR（起始年份）和 ENDYEAR（结束年份）之间（包含首尾两年）的历史记录。
     * 返回的 TimeSeries 应该是一个副本，而不是指向此 NGramMap 中 TimeSeries 的引用。
     * 换句话说，对该函数返回的对象所做的更改不应影响到 NGramMap。
     * 这也被称为“防御性拷贝（defensive copy）”。如果该单词不在数据文件中，则返回一个空的 TimeSeries。
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        if (!wordMap.containsKey(word)) {
            return new TimeSeries();
        }

        TimeSeries wordTimeSeries = wordMap.get(word);
        TimeSeries timeSeries = new TimeSeries(wordTimeSeries, startYear, endYear);

        return timeSeries;
    }

    /**
     * 提供 WORD（单词）的历史记录。返回的 TimeSeries 应该是一个副本，而不是指向此 NGramMap 中
     * TimeSeries 的引用。换句话说，对该函数返回的对象所做的更改不应影响到 NGramMap。
     * 这也被称为“防御性拷贝”。如果该单词不在数据文件中，则返回一个空的 TimeSeries。
     */
    public TimeSeries countHistory(String word) {
        if (!wordMap.containsKey(word)) {
            return new TimeSeries();
        }

        TimeSeries wordTimeSeries = wordMap.get(word);
        TimeSeries timeSeries = new TimeSeries(wordTimeSeries, MIN_YEAR, MAX_YEAR);

        return timeSeries;
    }

    /**
     * 返回所有卷中每年记录的单词总数的防御性拷贝。
     */
    public TimeSeries totalCountHistory() {
        return new TimeSeries(totalCounts, MIN_YEAR, MAX_YEAR);
    }

    /**
     * 提供一个 TimeSeries，包含 WORD（单词）在 STARTYEAR（起始年份）和 ENDYEAR（结束年份）之间
     * （包含首尾两年）每年的相对频率。如果该单词不在数据文件中，则返回一个空的 TimeSeries。
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries wordCounts = countHistory(word, startYear, endYear);
        TimeSeries timeSeries = new TimeSeries(totalCounts, startYear, endYear);

        return wordCounts.dividedBy(timeSeries);
    }

    /**
     * 提供一个 TimeSeries，包含 WORD（单词）与当年记录的所有单词相比的每年相对频率。
     * 如果该单词不在数据文件中，则返回一个空的 TimeSeries。
     */
    public TimeSeries weightHistory(String word) {
        TimeSeries wordCounts = countHistory(word);
        TimeSeries totalWords = totalCountHistory();

        return wordCounts.dividedBy(totalWords);
    }

    /**
     * 提供 WORDS（单词集合）中所有单词在 STARTYEAR（起始年份）和 ENDYEAR（结束年份）之间
     * （包含首尾两年）每年的相对频率总和。如果某个单词在该时间段内不存在，请忽略它，而不是抛出异常。
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        TimeSeries timeSeries = new TimeSeries();
        for (String s : words) {
            TimeSeries wordHistory = weightHistory(s, startYear, endYear);
            timeSeries = timeSeries.plus(wordHistory);
        }

        return timeSeries;
    }

    /**
     * 返回 WORDS（单词集合）中所有单词每年的相对频率总和。如果某个单词在该时间段内不存在，
     * 请忽略它，而不是抛出异常。
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        // TODO: 填写此方法。
        TimeSeries timeSeries = new TimeSeries();
        for (String s : words) {
            TimeSeries wordHistory = weightHistory(s);
            timeSeries = timeSeries.plus(wordHistory);
        }

        return timeSeries;

    }

}