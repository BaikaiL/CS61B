package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import wordnet.WordnetMap;

import java.util.List;
import java.util.TreeSet;

public class HyponymsHandler extends NgordnetQueryHandler {
	private WordnetMap wordnet;
	HyponymsHandler(String syssetFile, String hyponymsFile) {
		this.wordnet = new WordnetMap(syssetFile, hyponymsFile);
	}

	@Override
	public String handle(NgordnetQuery q) {
		List<String> word = q.words();
		int startYear = q.startYear();
		int endYear = q.endYear();
		int k = q.k();
		StringBuilder response = new StringBuilder();
		response.append("[");
		TreeSet<String> hyponyms = wordnet.getHyponysm(word);
		for(String s : hyponyms) {
			response.append(s);
			response.append(", ");
		}
		response.delete(response.length()-2, response.length());
		response.append("]");
		return response.toString();
	}
}
