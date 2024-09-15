import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class test {
    public static void main(String[] args) {
        Set<Integer> set = new HashSet<>();
        set.add(1);
        set.add(1);
        set.add(2);
        set.remove(2);
        System.out.println(set.size());
        if (set.contains(1)) {
            System.out.println("1 in set");
        }
        for (int elem : set) {
            System.out.println(elem);
        }
    }

}
