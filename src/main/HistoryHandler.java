package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import org.knowm.xchart.XYChart;
import plotting.Plotter;

import java.util.ArrayList;
import java.util.List;

public class HistoryHandler extends NgordnetQueryHandler {
    private NGramMap map;

    public HistoryHandler(NGramMap map) {
        this.map = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        // 你的代码写在这里...
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        List<String> labels = new ArrayList<>();
        List<TimeSeries> data = new ArrayList<>();

        for (String word : words) {
            TimeSeries timeSeries = map.weightHistory(word, startYear, endYear);
            labels.add(word);
            data.add(timeSeries);
        }

        XYChart chart = Plotter.generateTimeSeriesChart(labels, data);
        String encodedImage = Plotter.encodeChartAsString(chart);

        return encodedImage;
    }
}