import java.util.ArrayList;
import java.util.List;

public class ListExercises {

    /** Returns the total sum in a list of integers */
	public static int sum(List<Integer> L) {
        // TODO: Fill in this function.
        if (L.isEmpty()) return 0;
        int sum = 0;
        for(int i : L){
            sum += i;
        }
        return sum;
    }

    /** Returns a list containing the even numbers of the given list */
    public static List<Integer> evens(List<Integer> L) {
        // TODO: Fill in this function.
        List<Integer> evens = new ArrayList<>();
        for(int i : L){
            if(i % 2 == 0) evens.add(i);
        }
        return evens;
    }

    /** Returns a list containing the common item of the two given lists */
    public static List<Integer> common(List<Integer> L1, List<Integer> L2) {
        // TODO: Fill in this function.
        List<Integer> common = new ArrayList<>();
        for(int i : L1){
            if(L2.contains(i)) common.add(i);
        }
        return common;
    }


    /** Returns the number of occurrences of the given character in a list of strings. */
    public static int countOccurrencesOfC(List<String> words, char c) {
        // TODO: Fill in this function.
        String new_c = String.valueOf(c);
        int count = 0;
        for (int i = 0; i < words.size(); i++) {
            if (words.get(i).contains(new_c)) {
                count++;
            }
        }
        return count;
    }
}
