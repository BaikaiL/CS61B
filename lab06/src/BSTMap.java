import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

	private class Node {
		K key;
		V value;
		Node left, right;
		public Node(K key, V value) {
			this.key = key;
			this.value = value;
			left = right = null;
		}
	}

	private Node root;
	private int size;

	private Node Insert(Node root, K key, V value) {

		if (root == null) {
			size++;
			return new Node(key, value);
		}
		else if (key.compareTo(root.key) < 0) {
			root.left = Insert(root.left, key, value);
		}
		else if (key.compareTo(root.key) > 0) {
			root.right = Insert(root.right, key, value);
		}
		else {
			root.value = value;
		}
		return root;
	}

	@Override
	public void put(K key, V value) {
		root = Insert(root, key, value);
	}

	private Node getHelp(Node tmp, K key){
		if(tmp == null){
			return null;
		}
		if(key.compareTo(tmp.key) < 0){
			return getHelp(tmp.left, key);
		}
		else if(key.compareTo(tmp.key) > 0){
			return getHelp(tmp.right, key);
		}
		else{
			return tmp;
		}
	}

	@Override
	public V get(K key) {
		Node help = getHelp(root, key);
		if(help == null){
			return null;
		}
		return help.value;
	}

	// 参考了大佬的思路，不直接在get方法里面找是否有这个key
	// 转而使用getHelp方法，如果有这个key，就将这个节点返回、
	// 这就可以判断value==null的情况
	@Override
	public boolean containsKey(K key) {
		return getHelp(root, key) != null;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void clear() {
		root = null;
		size = 0;
	}

	private void addHelp(Set<K> set,Node help){
		if(help == null){
			return;
		}
		set.add(help.key);
		addHelp(set, help.left);
		addHelp(set, help.right);
	}

	@Override
	public Set<K> keySet() {
		if (root == null) {
			return null;
		}
		Set<K> set = new TreeSet<>();
		addHelp(set, root);
		return set;
	}

	private Node FindMin(Node curr){
		if(curr.left == null){
			return curr;
		}
		return FindMin(curr.left);
	}

	private Node removeHelp(Node tmp, K key){

		if(tmp == null){
			return null;
		}
		else if(key.compareTo(tmp.key) == 0){
			//1.左右节点都为空
			size--;
			if(tmp.left == null && tmp.right == null){
				return null;
			}
			else if(tmp.left == null){
				return tmp.right;
			}
			else if(tmp.right == null){
				return tmp.left;
			}
			else{
				Node current = tmp.right;
				current = FindMin(current);
				current.left = tmp.left;
				return tmp.right;
			}
		}
		else if(key.compareTo(tmp.key) < 0){
			tmp.left = removeHelp(tmp.left, key);
			return tmp;
		}
		else{
			tmp.right = removeHelp(tmp.right, key);
			return tmp;
		}
	}

	@Override
	public V remove(K key) {
		if(root == null){
			return null;
		}
		if(!containsKey(key)){
			return null;
		}
		V value = get(key);
		root = removeHelp(root, key);
		return value;
	}

	@Override
	public Iterator<K> iterator() {
		return keySet().iterator();
	}
}
