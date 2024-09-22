import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T> {

	private Node<T> sentinel;
	private int size;

	private class Node<T> {
		T data;
		Node<T> prev;
		Node<T> next;
		//Node(T data)-> Node(T data, Node<T> prev, Node<T> next)
	/*	public Node(T data) {
	//  this.data = data;
	//  next = this;
	//  prev = this;
	//  }
    */
		Node(T data, Node<T> prev, Node<T> next) {
			this.data = data;
			this.prev = prev;
			this.next = next;
		}
	};

	public LinkedListDeque61B() {
		sentinel = new Node<>(null, null,null);
		sentinel.next = sentinel;
		sentinel.prev = sentinel;
		size = 0;
	}

	@Override
	public void addFirst(T x) {
		Node<T> newNode = new Node<>(x,sentinel,sentinel.next);
		sentinel.next.prev = newNode;
		sentinel.next = newNode;
		size++;
	}

	@Override
	public void addLast(T x) {
		Node<T> newNode = new Node<>(x,sentinel.prev,sentinel);
		sentinel.prev.next = newNode;
		sentinel.prev = newNode;
		size++;
	}

	@Override
	public List<T> toList() {
		List<T> returnList = new ArrayList<>();
		for (Node<T> node = sentinel.next; node != sentinel; node = node.next) {
			returnList.add(node.data);
		}
		return returnList;
	}

	// isEmpty() and size() must take constant time
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public T removeFirst() {
		if (isEmpty()) {return null;}
		Node<T> first = sentinel.next;
		T data = first.data;
		sentinel.next = first.next;
		first.next.prev = sentinel;
		size-=1;
		return data;
	}

	@Override
	public T removeLast() {
		if(isEmpty()){return null;}
		Node<T> last = sentinel.prev;
		T data = last.data;
		sentinel.prev = last.prev;
		last.prev.next = sentinel;
		size-=1;
		return data;
	}

	@Override
	public T get(int index) {
		if(index < 0 || index >= size) {return null;};
		Node<T> node = sentinel.next;
		for(int i = 0; i < index; i++) {
			node = node.next;
		}
		return node.data;
	}

	@Override
	public T getRecursive(int index) {
		if(index < 0 || index >= size) {return null;};
		Node<T> node = sentinel.next;
		return getRecursiveHelper(node, index);
	}

	private T getRecursiveHelper(Node<T> node, int index) {
		if(index == 0){
			return node.data;
		}
		else {
			return getRecursiveHelper(node.next, index-1);
		}

	}
}
