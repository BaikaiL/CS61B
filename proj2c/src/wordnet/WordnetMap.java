package wordnet;

import edu.princeton.cs.algs4.In;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;

public class WordnetMap {
	Graph children;
	IdToWord idToWord;
	WordToId wordToId;
	Graph parents;
	public WordnetMap(String synsetsFileName, String hyponymsFileName) {
		children = new Graph();
		idToWord = new IdToWord();
		wordToId = new WordToId();
		parents = new Graph();

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
				children.AddEdge(parent,child);
				parents.AddEdge(child,parent);
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
		TreeSet<String> hyponyms = helper(word,"children");
		return hyponyms;
	}

	public TreeSet<String> getHyponysm(List<String> words){
		TreeSet<String> hyponyms = new TreeSet<>();
		String firstWord = words.getFirst();
		hyponyms = getHyponysm(firstWord);
		for (String word : words) {
			hyponyms.retainAll(getHyponysm(word));
		}
		return hyponyms;
	}

	public TreeSet<String> getCommonAncestors(String word){
		// 树集，有顺序且不会重复，用来存放所有的ancestors
		TreeSet<String> ancestors = helper(word,"parents");
		return ancestors;
	}

	public TreeSet<String> getCommonAncestors(List<String> words){
		TreeSet<String> ancestors = new TreeSet<>();
		String firstWord = words.getFirst();
		ancestors = getCommonAncestors(firstWord);
		for (String word : words) {
			ancestors.retainAll(getCommonAncestors(word));
		}
		return ancestors;
	}

	private TreeSet<String> helper(String word,String choice){
		// 树集，有顺序且不会重复，用来存放所有的hyponysm
		TreeSet<String> treeSet = new TreeSet<>();

		// 获取到该单词所有的id，一个单词的id可能有多个
		HashSet<Integer> idSet = wordToId.getIdSet(word);

		// 添加同id的单词，在synset中同id的可能有多个单词，这些单词都是ancestors
		for(Integer id : idSet){
			treeSet.addAll(idToWord.getWordSet(id));
		}

		// 用来存放子树id的hashset
		HashSet<Integer> hashSet = new HashSet<>();
		// 遍历graph，找到Graph中idSet所有的子树
		if(Objects.equals(choice, "parents")){
			for (int id : idSet) {
				hashSet.addAll(parents.GetALLChildren(id));
			}
		}
		else if(Objects.equals(choice, "children")){
			for (int id : idSet) {
				hashSet.addAll(children.GetALLChildren(id));
			}
		}
		// 将子树中对应的单词加入到hyponyms
		for(Integer id : hashSet){
			treeSet.addAll(idToWord.getWordSet(id));
		}
		return treeSet;
	}
}
