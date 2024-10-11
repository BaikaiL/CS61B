package deque;

import java.util.Comparator;

public class MaxArrayDeque61B <T> extends ArrayDeque61B<T> {
	private Comparator<T> comparator;

	public MaxArrayDeque61B(Comparator<T> c){
		//creates a MaxArrayDeque61B with the given Comparator
		super();
		this.comparator = c;
	}

	public MaxArrayDeque61B(){
		super();
	}

	public T max(){
		//returns the maximum element in the deque as governed by the previously given Comparator.
		// If the MaxArrayDeque61B is empty, simply return null
		if(isEmpty()) return null;
		T max = get(0);
		for(T x: this){
			if(comparator.compare(x,max) > 0) max = x;
		}

		return max;
	}

	public T max(Comparator<T> c){
//		returns the maximum element in the deque as governed by the parameter Comparator c.
//		If the MaxArrayDeque61B is empty, simply return null
		if(isEmpty()) return null;
		T max = get(0);
		for(T x: this){
			if(c.compare(x,max) > 0) max = x;
		}

		return max;

	}
}
