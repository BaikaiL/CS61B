import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class ArrayDeque61B<T> implements Deque61B<T> {
	private T[] items;
	private int size;
	private int nextFirst;
	private int nextLast;


	public ArrayDeque61B() {
		items = (T[]) new Object[8];
		size = 0;
		nextFirst = 4;
		nextLast = 5;
	}

	@Override
	public void addFirst(T x) {
		items[nextFirst] = x;
		nextFirst = Math.floorMod(nextFirst-1, items.length);
		size+=1;
	}

	@Override
	public void addLast(T x) {
		items[nextLast] = x;
		nextLast = Math.floorMod(nextLast+1, items.length);
		size+=1;
	}

	@Override
	public List<T> toList() {
		List<T> list = new ArrayList<T>(size);
		int index = Math.floorMod(nextFirst+1, items.length);
		for(int i = 0; i < size; i++){
			list.add(items[index]);
			index = Math.floorMod(index+1, items.length);
		}
		return list;
	}

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
		if(isEmpty()) return null;
		int index = Math.floorMod(nextFirst+1, items.length);
		T x = items[index];
		items[index] = null;
		nextFirst = index;
		size-=1;
		return x;
	}

	@Override
	public T removeLast() {
		if(isEmpty()) return null;
		int index = Math.floorMod(nextLast-1, items.length);
		T x = items[index];
		items[index] = null;
		nextLast = index;
		size-=1;
		return x;
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
