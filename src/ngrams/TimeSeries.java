package ngrams;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

/**
 * 一个用于将年份（例如 1996）映射到数值数据的对象。
 * 提供了对数据分析有用的实用方法。
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

    /**
     * 如果有助于加速你的代码，你可以假设传给 NGramMap 的年份参数
     * 在 1400 到 2100 之间。我们将这些值存储在常量 MIN_YEAR 和 MAX_YEAR 中。
     */
    public static final int MIN_YEAR = 1400;
    public static final int MAX_YEAR = 2100;

    /**
     * 构造一个新的空 TimeSeries。
     */
    public TimeSeries() {
        super();
    }

    /**
     * 创建 TS 的一个副本，但仅包含从 STARTYEAR 到 ENDYEAR 之间的部分，
     * 包括这两个端点年份。
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        for (Integer year : ts.keySet()) {
            if (year >= startYear && year <= endYear) {
                this.put(year, ts.get(year));
            }
        }
    }

    /**
     * 返回此 TimeSeries 的所有年份（以任意顺序）。
     */
    public List<Integer> years() {
        return new ArrayList<>(this.keySet());
    }

    /**
     * 返回此 TimeSeries 的所有数据（以任意顺序）。
     * 必须与 years() 返回的顺序保持一致。
     */
    public List<Double> data() {
        return new ArrayList<>(this.values());
    }

    /**
     * 返回此 TimeSeries 与给定 TS 的“逐年和”（year-wise sum）。
     * 换句话说，对于每一年，将此 TimeSeries 中的数据与 TS 中的数据相加。
     * 应返回一个新的 TimeSeries（不修改此 TimeSeries）。
     *
     * 如果两个 TimeSeries 都不包含任何年份，则返回一个空的 TimeSeries。
     * 如果一个 TimeSeries 包含另一个 TimeSeries 没有的年份，
     * 则返回的 TimeSeries 应存储包含该年份的 TimeSeries 中的那个值。
     */
    public TimeSeries plus(TimeSeries ts) {
        TimeSeries res = new TimeSeries();
        Set<Integer> thisSet = this.keySet();
        Set<Integer> tsSet = ts.keySet();

        for (Integer i : thisSet) {
            res.put(i, this.get(i));
        }

        for (Integer i : tsSet) {
            if (thisSet.contains(i)) {
                res.put(i, this.get(i) + ts.get(i));
            }else {
                res.put(i, ts.get(i));
            }
        }

        return res;
    }

    /**
     * 返回此 TimeSeries 中每年的值除以 TS 中同一年份的值的商。
     * 应返回一个新的 TimeSeries（不修改此 TimeSeries）。
     *
     * 如果 TS 缺少此 TimeSeries 中存在的年份，抛出 IllegalArgumentException。
     * 如果 TS 拥有此 TimeSeries 中没有的年份，则忽略该年份。
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        TimeSeries timeSeries = new TimeSeries();
        Set<Integer> thisSet = this.keySet();

        for (Integer i : thisSet) {
            if (!ts.containsKey(i)) {
                throw new IllegalArgumentException();
            }
            Double dividedValue = this.get(i) / ts.get(i);
            timeSeries.put(i, dividedValue);
        }

        return timeSeries;
    }

}