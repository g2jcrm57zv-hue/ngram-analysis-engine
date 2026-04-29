package main;


import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {

    private NGramMap map;

    public HistoryTextHandler(NGramMap map) {
        this.map = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        StringBuilder stringBuilder = new StringBuilder();

        for (String word : words) {

            TimeSeries timeSeries = map.weightHistory(word, startYear, endYear);

            stringBuilder.append(word).append(": ").append(timeSeries.toString()).append("\n");

        }

        return stringBuilder.toString();
    }
}

