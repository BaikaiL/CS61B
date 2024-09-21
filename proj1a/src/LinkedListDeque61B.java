import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T> {

	private Node<T> sentinel;

	private class Node<T> {
		T data;
		Node<T> next;
		Node<T> prev;

		public Node(T data) {
			this.data = data;
			next = this;
			prev = this;
		}
	};

	public LinkedListDeque61B() {
		sentinel = new Node<>(null);
	}

	@Override
	public void addFirst(T x) {
		Node<T> newNode = new Node<>(x);
		newNode.prev = sentinel;
		newNode.next = sentinel.next;
		sentinel.next.prev = newNode;
		sentinel.next = newNode;
	}

	@Override
	public void addLast(T x) {
		Node<T> newNode = new Node<>(x);
		newNode.next = sentinel;
		newNode.prev = sentinel.prev;
		sentinel.prev.next = newNode;
		sentinel.prev = newNode;
	}

	@Override
	public List<T> toList() {
		List<T> returnList = new ArrayList<>();
		for (Node<T> node = sentinel.next; node != sentinel; node = node.next) {
			returnList.add(node.data);
		}
		return returnList;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public T removeFirst() {
		return null;
	}

	@Override
	public T removeLast() {
		return null;
	}

	@Override
	public T get(int index) {
		return null;
	}

	@Override
	public T getRecursive(int index) {
		return null;
	}
}
