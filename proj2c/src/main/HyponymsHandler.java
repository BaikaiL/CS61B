package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import wordnet.WordnetMap;

import java.util.*;
import java.util.stream.Collectors;

public class HyponymsHandler extends NgordnetQueryHandler {
	private WordnetMap wordnet;
	private NGramMap nGramMap;
	HyponymsHandler(String syssetFile, String hyponymsFile) {
		this.wordnet = new WordnetMap(syssetFile, hyponymsFile);
	}

	HyponymsHandler(String syssetFile,String hyponymsFile, NGramMap ngordnet) {
		this.wordnet = new WordnetMap(syssetFile, hyponymsFile);
		this.nGramMap = ngordnet;
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

		// 筛选在start和end出现过的单词, 出现过的单词要统计出现次数
		TreeMap<String,Double> Numbers = new TreeMap<>();
		for(String s : hyponyms){
			Double sum = nGramMap.countToTalBetweenSAndE(s, startYear, endYear);
			if(sum != 0){
				Numbers.put(s, sum);
			}
		}

		TreeSet<String> result = new TreeSet<>();
		// 如果k大于0，则按照出现次数进行排序，取前k个
		if(k > 0){
			List<Map.Entry<String, Double>> entryList = new ArrayList<>(Numbers.entrySet());

			// 使用Lambda表达式进行排序（从大到小）
			entryList.sort((o1, o2) -> Double.compare(o2.getValue(), o1.getValue()));

			// 提取前k个键
			List<String> topKKeys = entryList.stream()
					.limit(k)
					.map(Map.Entry::getKey)
					.collect(Collectors.toList());

			result.addAll(topKKeys);
		}
		else{
			result = hyponyms;
		}


		for(String s : result) {
			response.append(s);
			response.append(", ");
		}
		response.delete(response.length()-2, response.length());
		response.append("]");
		return response.toString();
	}
}
