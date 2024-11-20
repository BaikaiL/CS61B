package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import browser.NgordnetQueryType;
import ngrams.NGramMap;
import wordnet.WordnetMap;

import java.util.*;
import java.util.stream.Collectors;

public class HyponymsHandler extends NgordnetQueryHandler {
	private WordnetMap wordnet;
	private NGramMap nGramMap;
	HyponymsHandler(String syssetFile,String hyponymsFile, NGramMap nGramMap) {
		this.wordnet = new WordnetMap(syssetFile, hyponymsFile);
		this.nGramMap = nGramMap;
	}

	@Override
	public String handle(NgordnetQuery q) {
		List<String> word = q.words();
		int startYear = q.startYear();
		int endYear = q.endYear();
		int k = q.k();

		TreeSet<String> result = new TreeSet();
		if(q.ngordnetQueryType() == NgordnetQueryType.HYPONYMS){
			result = HyponmsBottom(word, startYear, endYear, k);
		}
		else if(q.ngordnetQueryType() == NgordnetQueryType.ANCESTORS){
			result = AncestorsBottom(word, startYear, endYear, k);
		}
		StringBuilder response = new StringBuilder();
		response.append("[");

		for(String s : result) {
			response.append(s);
			response.append(", ");
		}
		if(!result.isEmpty()){
			response.delete(response.length()-2, response.length());
		}
		response.append("]");
		return response.toString();
	}

	private TreeSet<String> HyponmsBottom(List<String>word,int startYear,int endYear,int k){
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
		return result;
	}

	private TreeSet<String> AncestorsBottom(List<String> word,int startYear,int endYear,int k){
		TreeSet<String> ancestors = wordnet.getCommonAncestors(word);

		// 筛选在start和end出现过的单词, 出现过的单词要统计出现次数
		TreeMap<String,Double> Numbers = new TreeMap<>();
		for(String s : ancestors){
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
			result = ancestors;
		}
		return result;
	}
}
