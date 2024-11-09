package wordnet;

import edu.princeton.cs.algs4.In;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

public class WordnetMap {
	Graph graph;
	IdToWord idToWord;
	WordToId wordToId;
	public WordnetMap(String synsetsFileName, String hyponymsFileName) {
		graph = new Graph();
		idToWord = new IdToWord();
		wordToId = new WordToId();

		setGraph(hyponymsFileName);
		setIdToWord(synsetsFileName);
		setWordToId(synsetsFileName);
	}

	private void setGraph(String hyponymsFileName) {
		In in = new In(hyponymsFileName);
		String line;
		int parent;
		int child;
		while (in.hasNextLine()) {
			line = in.readLine();
			String[] words = line.split(",");
			parent = Integer.parseInt(words[0]);
			for (int i = 1; i < words.length; i++) {
				child = Integer.parseInt(words[i]);
				graph.AddEdge(parent,child);
			}
		}
		in.close();
	}

	private void setIdToWord(String synsetsFileName) {
		In in = new In(synsetsFileName);
		HashSet<String> wordSet = new HashSet<>();
		String line;
		int id;
		while (in.hasNextLine()) {
			line = in.readLine();
			String[] word = line.split(",");
			id = Integer.parseInt(word[0]);
			String[] wordInSet = word[1].split(" ");
			for (String w : wordInSet) {
				idToWord.add(id,w);
			}
		}
		in.close();
	}

	private void setWordToId(String synsetsFileName) {
		In in = new In(synsetsFileName);
		HashSet<Integer> idSet = new HashSet<>();
		String line;
		int id;
		while (in.hasNextLine()) {
			line = in.readLine();
			String[] word = line.split(",");
			id = Integer.parseInt(word[0]);
			String[] wordInSet = word[1].split(" ");
			for (String w : wordInSet) {
				wordToId.add(w,id);
			}
		}
		in.close();
	}

	public TreeSet<String> getHyponysm(String word){
		// 树集，有顺序且不会重复，用来存放所有的hyponysm
		TreeSet<String> hyponyms = new TreeSet<>();

		// 获取到该单词所有的id，一个单词的id可能有多个
		HashSet<Integer> idSet = wordToId.getIdSet(word);

		// 添加同id的单词，在synset中同id的可能有多个单词，这些单词都是hyponysm
		for(Integer id : idSet){
			hyponyms.addAll(idToWord.getWordSet(id));
		}

		// 用来存放子树id的hashset
		HashSet<Integer> hyponymSet = new HashSet<>();
		// 遍历graph，找到Graph中idSet所有的子树
		for (int id : idSet) {
			hyponymSet.addAll(graph.GetALLChildren(id));
		}

		// 将子树中对应的单词加入到hyponyms
		for(Integer id : hyponymSet){
			hyponyms.addAll(idToWord.getWordSet(id));
		}
		return hyponyms;
	}

	public TreeSet<String> getHyponysm(List<String> words){
		TreeSet<String> synonyms = new TreeSet<>();
		for(String word : words){
			synonyms.addAll(getHyponysm(word));
		}
		return synonyms;
	}
}
