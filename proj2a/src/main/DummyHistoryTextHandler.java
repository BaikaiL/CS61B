package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;

import java.util.List;

public class DummyHistoryTextHandler extends NgordnetQueryHandler {
    NGramMap nGramMap;
    public DummyHistoryTextHandler(NGramMap nGramMap) {
        this.nGramMap = nGramMap;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        String response = "You entered the following info into the browser:\n";
        response += "Words: " + q.words() + "\n";
        response += "Start Year: " + q.startYear() + "\n";
        response += "End Year: " + q.endYear() + "\n";
        return response;
    }
}
