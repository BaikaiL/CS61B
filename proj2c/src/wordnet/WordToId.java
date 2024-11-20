package wordnet;

import java.util.HashMap;
import java.util.HashSet;

public class WordToId extends HashMap<String, HashSet<Integer>> {
	WordToId(){
		super();
	}

	public void add(String word, int id){
		if(this.containsKey(word)){
			this.get(word).add(id);
		}
		else{
			HashSet<Integer> set = new HashSet<>();
			set.add(id);
			this.put(word, set);
		}
	}

	public HashSet<Integer> getIdSet(String word){
		HashSet<Integer> set = new HashSet<>();
		if(this.containsKey(word)){
			set.addAll(this.get(word));
		}
		return set;
	}
}
