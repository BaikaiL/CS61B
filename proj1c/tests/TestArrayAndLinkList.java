import deque.ArrayDeque61B;
import deque.Deque61B;
import deque.LinkedListDeque61B;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class TestArrayAndLinkList {

	@Test
	public void addLastTestBasicWithoutToList01() {
		Deque61B<String> lld1 = new ArrayDeque61B<String>();

		lld1.addLast("front"); // after this call we expect: ["front"]
		lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
		lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
		assertThat(lld1).containsExactly("front", "middle", "back");


	}

	@Test
	public void addLastTestBasicWithoutToList02() {
		Deque61B<String> lld1 = new LinkedListDeque61B<>();

		lld1.addLast("front"); // after this call we expect: ["front"]
		lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
		lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
		assertThat(lld1).containsExactly("front", "middle", "back");

	}

	@Test
	public void testEqualDeques61B() {
		Deque61B<String> lld1 = new LinkedListDeque61B<>();
		Deque61B<String> lld2 = new LinkedListDeque61B<>();

		lld1.addLast("front");
		lld1.addLast("middle");
		lld1.addLast("back");

		lld2.addLast("front");
		lld2.addLast("middle");
		lld2.addLast("back");
		assertThat(lld1).isEqualTo(lld2);


		Deque61B<String> lld3 = new ArrayDeque61B<>();
		Deque61B<String> lld4 = new ArrayDeque61B<>();

		lld3.addLast("front");
		lld3.addLast("middle");
		lld3.addLast("back");

		lld4.addLast("front");
		lld4.addLast("middle");
		lld4.addLast("back");
		assertThat(lld3).isEqualTo(lld4);
	}
}
