package wordnet;

import java.util.HashMap;
import java.util.HashSet;

public class IdToWord extends HashMap <Integer, HashSet<String>>{

	IdToWord(){
		super();
	}

	public void add(int v, String s){
		if(this.containsKey(v)){
			this.get(v).add(s);
		}
		else{
			HashSet<String> set = new HashSet<>();
			set.add(s);
			this.put(v, set);
		}
	}

	public HashSet<String> getWordSet(int v){
		HashSet<String> set = new HashSet<>();
		if(this.containsKey(v)){
			set.addAll(get(v));
		}
		return set;
	}
}
