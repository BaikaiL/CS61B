package wordnet;

import java.util.HashMap;
import java.util.HashSet;

// 储存同义词的关系，只提供功能，读取数据在WordnetMap中进行
public class Graph extends HashMap<Integer, HashSet<Integer>> {

	Graph() {
		super();
	}

	// 给定父节点和子节点的id，将子节点的id添加到父节点对应的HashSet中
	public void AddEdge(int parent, int child) {
		if (!containsKey(parent)) {
			HashSet<Integer> set = new HashSet<>();
			set.add(child);
			put(parent, set);
		}
		else {
			HashSet<Integer> set = get(parent);
			set.add(child);
		}
	}

	public HashSet<Integer> GetALLChildren(int parent) {
		HashSet<Integer> set = new HashSet<>();
		if(get(parent) != null) {
			for(Integer child : get(parent)) {
				set.addAll(GetAllChildrenHelper(child,set));
			}
		}
		return set;

	}
	private HashSet<Integer> GetAllChildrenHelper(int parent, HashSet<Integer> set) {
		set.add(parent);
		if(get(parent) != null) {
			for(Integer child : get(parent)) {
				set.addAll(GetAllChildrenHelper(child,set));
			}
		}

		return set;
	}
}
