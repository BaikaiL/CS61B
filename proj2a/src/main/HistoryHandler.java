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
	NGramMap ngramMap;
	public HistoryHandler(NGramMap ngramMap) {
		this.ngramMap = ngramMap;
	}

	@Override
	public String handle(NgordnetQuery q) {
		List<String> words = q.words();
		int startYear = q.startYear();
		int endYear = q.endYear();

		ArrayList<TimeSeries> lts = new ArrayList<>();
		ArrayList<String> labels = new ArrayList<>();
		TimeSeries ts;
		for (String word : words) {
			ts = ngramMap.countHistory(word, startYear, endYear);
			if(!ts.isEmpty()) {
				lts.add(ts);
				labels.add(word);
			}
		}

		XYChart chart = Plotter.generateTimeSeriesChart(labels, lts);
		String encodedImage = Plotter.encodeChartAsString(chart);

		return encodedImage;
	}
}
