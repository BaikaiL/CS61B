package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {
	NGramMap nGramMap;
	public HistoryTextHandler(NGramMap nGramMap) {
		this.nGramMap = nGramMap;
	}
	@Override
	public String handle(NgordnetQuery q) {
		String response = "";
		List<String> words = q.words();
		int startYear = q.startYear();
		int endYear = q.endYear();
		for (String word : words) {
			response += word + ":" + " ";
			TimeSeries ts = nGramMap.countHistory(word,startYear,endYear);
			response += ts.toString();
			response += "\n";
		}

		return response;
	}
}
