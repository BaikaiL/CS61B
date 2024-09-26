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

	private T[] extendItems() {
		T[] new_items = (T[]) new Object[2*size];
		int new_index = (new_items.length-items.length)/2;
		int old_index = nextFirst+1;
		for(int i = 0; i <size; i++) {
			new_items[new_index+i] = items[old_index];
			old_index = Math.floorMod(old_index+1, items.length);
		}
		nextFirst = (new_items.length-items.length)/2 - 1;
		nextLast = nextFirst + size + 1;
		return new_items;
	}

	private T[] reduceItems() {
		T[] new_items = (T[]) new Object[2*size];
		int new_index = (items.length-new_items.length)/4;
		int old_index = nextFirst+1;
		for(int i = 0; i <size; i++) {
			new_items[new_index+i] = items[old_index];
			old_index = Math.floorMod(old_index+1, items.length);
		}
		nextFirst = (items.length-new_items.length)/4 - 1;
		nextLast = nextFirst + size + 1;
		return new_items;
	}

	@Override
	public void addFirst(T x) {
		//假如容量满了，则需要扩容
		if (size == items.length) {
			items = extendItems();
		}
		items[nextFirst] = x;
		nextFirst = Math.floorMod(nextFirst-1, items.length);
		size+=1;
	}

	@Override
	public void addLast(T x) {
		if (size == items.length) {
			items = extendItems();
		}
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
		if(items.length > 8 && (float)size / items.length <= 0.25) {
			items = reduceItems();

		}
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
		if(items.length > 8 && (float)size / items.length <= 0.25) {
			items = reduceItems();
		}
		int index = Math.floorMod(nextLast-1, items.length);
		T x = items[index];
		items[index] = null;
		nextLast = index;
		size-=1;
		return x;
	}

	@Override
	public T get(int index) {
		int i = Math.floorMod(index+nextFirst+1, items.length);
		return items[i];
	}

	@Override
	public T getRecursive(int index) {
		throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
	}
}
