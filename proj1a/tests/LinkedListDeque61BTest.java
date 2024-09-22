import org.eclipse.jetty.xml.XmlParser;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

/** Performs some basic linked list tests. */
public class LinkedListDeque61BTest {

     @Test
     /** In this test, we have three different assert statements that verify that addFirst works correctly. */
     public void addFirstTestBasic() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();

         lld1.addFirst("back"); // after this call we expect: ["back"]
         assertThat(lld1.toList()).containsExactly("back").inOrder();

         lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
         assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

         lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

         /* Note: The first two assertThat statements aren't really necessary. For example, it's hard
            to imagine a bug in your code that would lead to ["front"] and ["front", "middle"] failing,
            but not ["front", "middle", "back"].
          */
     }

     @Test
     /** In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
      *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
     public void addLastTestBasic() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();

         lld1.addLast("front"); // after this call we expect: ["front"]
         lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
         lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
     }

     @Test
     /** This test performs interspersed addFirst and addLast calls. */
     public void addFirstAndAddLastTest() {
         Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
         lld1.addLast(0);   // [0]
         lld1.addLast(1);   // [0, 1]
         lld1.addFirst(-1); // [-1, 0, 1]
         lld1.addLast(2);   // [-1, 0, 1, 2]
         lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

         assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
     }

    // Below, you'll write your own tests for LinkedListDeque61B.
    @Test
    public void addFirstAndAddLastTest2(){
         Deque61B<String> lld1 = new LinkedListDeque61B<>();
         lld1.addFirst("first");  //["first"]
         lld1.addLast("second");  //["first","second"]
         lld1.addFirst("third");  //["third","first","second"]
         lld1.addLast("fourth");  //["third","first","second","fourth"]
         lld1.addFirst("fifth");  //["fifth","third","first","second","fourth"]

        assertThat(lld1.toList()).containsExactly("fifth","third","first","second","fourth").inOrder();
    }

    //testIsEmpty, testSizeZero, testSizeOne to very coarse grained, e.g. testSizeAndIsEmpty
    /**this tests isEmpty() call */
    @Test
    public void testIsEmpty() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();
         assertThat(lld1.isEmpty()).isTrue();

         lld1.addFirst("first");
         assertThat(lld1.isEmpty()).isFalse();
    }

    /** this*/
    @Test
    public void testSizeZero(){
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        assertThat(lld1.size()).isEqualTo(0);
    }

    @Test
    public void testSizeOne(){
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        lld1.addFirst("first");
        assertThat(lld1.size()).isEqualTo(1);

        lld1.addLast("second");
        assertThat(lld1.size()).isEqualTo(2);
    }

    @Test
    public void testSizeAndIsEmpty(){
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        assertThat(lld1.size()).isEqualTo(0);
        assertThat(lld1.isEmpty()).isTrue();

        lld1.addLast("first");
        assertThat(lld1.isEmpty()).isFalse();
        assertThat(lld1.size()).isEqualTo(1);

    }

    @Test
    public void testGetFirst() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        lld1.addLast("first");
        lld1.addLast("second");
        lld1.addLast("third");

        assertThat(lld1.get(0)).isEqualTo("first");
    }

    @Test
    public void testGetLast() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        lld1.addLast("first");
        lld1.addLast("second");
        lld1.addLast("third");
        assertThat(lld1.get(2)).isEqualTo("third");
    }

    @Test
    public void testGetIndex() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        lld1.addLast("first");
        lld1.addLast("second");
        lld1.addLast("third");
        assertThat(lld1.get(1)).isEqualTo("second");
        assertThat(lld1.get(16546)).isEqualTo(null);
    }

    @Test
    public void testGetEmpty() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        assertThat(lld1.getRecursive(23164)).isEqualTo(null);
    }

    @Test
    public void testGetRecursiveFirst() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        lld1.addLast("first");
        lld1.addLast("second");
        lld1.addLast("third");

        assertThat(lld1.getRecursive(0)).isEqualTo("first");
    }

    @Test
    public void testGetRecursiveLast() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        lld1.addLast("first");
        lld1.addLast("second");
        lld1.addLast("third");
        assertThat(lld1.getRecursive(2)).isEqualTo("third");
    }

    @Test
    public void testGetRecursiveIndex() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        lld1.addLast("first");
        lld1.addLast("second");
        lld1.addLast("third");
        assertThat(lld1.getRecursive(1)).isEqualTo("second");
        assertThat(lld1.getRecursive(16546)).isEqualTo(null);
    }

    @Test
    public void testGetRecursiveEmpty() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        assertThat(lld1.getRecursive(23164)).isEqualTo(null);
    }

    @Test
    public void remoteFirstTestBasic(){
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        lld1.addLast("front"); // after this call we expect: ["front"]
        lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
        lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
        String s = lld1.removeFirst();
        assertThat(s).isEqualTo("front");
        assertThat(lld1.toList()).containsExactly("middle","back").inOrder();
    }



    @Test
    public void remoteLastTestBasic() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addLast("front"); // after this call we expect: ["front"]
        lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
        lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
        String s =  lld1.removeLast();
        assertThat(s).isEqualTo("back");
        assertThat(lld1.toList()).containsExactly("front", "middle").inOrder();
    }

    @Test
    /** This test performs interspersed addFirst and addLast calls. */
    public void remoteFirstAndRemoteLastTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
        lld1.addLast(0);   // [0]
        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]
        lld1.addLast(2);   // [-1, 0, 1, 2]
        lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

        lld1.removeFirst();
        assertThat(lld1.toList()).containsExactly(-1, 0, 1, 2).inOrder();
        lld1.removeLast();
        assertThat(lld1.toList()).containsExactly(-1, 0, 1).inOrder();
        lld1.removeLast();
        assertThat(lld1.toList()).containsExactly(-1, 0).inOrder();
        lld1.removeLast();
        assertThat(lld1.toList()).containsExactly(-1).inOrder();

    }

    // Below, you'll write your own tests for LinkedListDeque61B.
    @Test
    public void remoteFirstAndRemoteLastTest2(){
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        lld1.addFirst("first");  //["first"]
        lld1.addLast("second");  //["first","second"]
        lld1.addFirst("third");  //["third","first","second"]

        lld1.removeFirst();
        assertThat(lld1.toList()).containsExactly("first","second").inOrder();
        lld1.removeLast();
        assertThat(lld1.toList()).containsExactly("first").inOrder();
        lld1.removeLast();
        assertThat(lld1.toList()).containsExactly().inOrder();
        String s = lld1.removeLast();
        assertThat(s).isEqualTo(null);

    }
}