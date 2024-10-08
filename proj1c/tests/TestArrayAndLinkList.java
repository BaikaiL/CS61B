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
}
