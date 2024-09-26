import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

	@Test
	@DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
	void noNonTrivialFields() {
		List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
				.filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
				.toList();

		assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
	}

	@Test
	void testAddFirstOne() {
		Deque61B<String> d = new ArrayDeque61B<String>();
		d.addFirst("a");
		assertThat(d.toList()).containsExactly("a").inOrder();
	}

	@Test
	void testAddFirstTwo() {
		Deque61B<String> d = new ArrayDeque61B<String>();
		d.addFirst("a");
		d.addFirst("b");
		assertThat(d.toList()).containsExactly("b", "a").inOrder();
	}

	@Test
	void testAddLastOne() {
		Deque61B<String> d = new ArrayDeque61B<String>();
		d.addLast("a");
		assertThat(d.toList()).containsExactly("a").inOrder();
	}

	@Test
	void testAddLastTwo() {
		Deque61B<String> d = new ArrayDeque61B<String>();
		d.addLast("a");
		d.addLast("b");
		assertThat(d.toList()).containsExactly("a", "b").inOrder();
	}

	@Test
	void testAddFirstAndLast() {
		Deque61B<String> d = new ArrayDeque61B<>();
		d.addLast("a");
		d.addLast("b");
		d.addFirst("c");
		d.addLast("d");
		d.addLast("e");
		assertThat(d.toList()).containsExactly("c", "a", "b", "d", "e").inOrder();
	}

	@Test
	void testRemoveFirst() {
		Deque61B<String> d = new ArrayDeque61B<>();
		d.addLast("a");
		d.addLast("b");
		d.addFirst("c");
		d.addLast("d");
		d.addLast("e");

		d.removeFirst();
		assertThat(d.toList()).containsExactly("a", "b", "d", "e").inOrder();
	}

	@Test
	void testRemoveFirstTwo() {
		Deque61B<String> d = new ArrayDeque61B<>();
		d.addLast("a");
		d.addLast("b");
		d.addFirst("c");
		d.addLast("d");
		d.addLast("e");

		d.removeFirst();
		d.removeFirst();
		assertThat(d.toList()).containsExactly("b", "d", "e").inOrder();
	}

	@Test
	void testRemoveLast() {
		Deque61B<String> d = new ArrayDeque61B<>();
		d.addLast("a");
		d.addLast("b");
		d.addFirst("c");
		d.addLast("d");
		d.addLast("e");

		d.removeLast();
		assertThat(d.toList()).containsExactly("c", "a", "b", "d").inOrder();
	}

	@Test
	void testRemoveLastTwo() {
		Deque61B<String> d = new ArrayDeque61B<>();
		d.addLast("a");
		d.addLast("b");
		d.addFirst("c");
		d.addLast("d");
		d.addLast("e");

		d.removeLast();
		d.removeLast();
		assertThat(d.toList()).containsExactly("c", "a", "b").inOrder();
	}

	@Test
	void testGet() {
		Deque61B<String> d = new ArrayDeque61B<>();
		d.addLast("a");
		d.addLast("b");
		d.addFirst("c");
		d.addLast("d");
		d.addLast("e");

		//"c", "a", "b", "d", "e"
		assertThat(d.get(0)).isEqualTo("c");
		assertThat(d.get(1)).isEqualTo("a");
		assertThat(d.get(4)).isEqualTo("e");
	}

	@Test
	void testResize(){
		Deque61B<String> d = new ArrayDeque61B<>();
		d.addLast("a");
		d.addLast("b");
		d.addFirst("c");
		d.addLast("d");
		d.addLast("e");
		d.addFirst("f");
		d.addLast("g");
		d.addLast("h");
		d.addLast("Z");

		assertThat(d.toList()).containsExactly("f", "c", "a", "b", "d","e","g","h","Z").inOrder();

		d.addFirst("A");
		assertThat(d.toList()).containsExactly("A","f", "c", "a", "b", "d","e","g","h","Z").inOrder();

	}

	@Test
	void testResizeTwo(){
		Deque61B<String> d = new ArrayDeque61B<>();
		d.addLast("a");
		d.addLast("b");
		d.addFirst("c");
		d.addLast("d");
		d.addLast("e");
		d.addFirst("f");
		d.addLast("g");
		d.addLast("h");
		d.addLast("Z");
		d.addFirst("A");
		assertThat(d.toList()).containsExactly("A","f", "c", "a", "b", "d","e","g","h","Z").inOrder();

		d.removeFirst();
		d.removeFirst();
		d.removeFirst();
		d.removeLast();
		d.removeLast();
		d.removeLast();
		d.removeLast();
		d.removeLast();
		assertThat(d.toList()).containsExactly("a", "b").inOrder();

	}

}
