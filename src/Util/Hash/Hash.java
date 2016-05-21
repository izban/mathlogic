package Util.Hash;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by izban on 21.05.2016.
 */
public class Hash {
    Hash() {}

    private static HashMap<ArrayList<Integer>, Integer> map = new HashMap<>();
    private static ArrayList<ArrayList<Integer>> arr = new ArrayList<>();

    public static int getHash(ArrayList<Integer> a) {
        if (map.containsKey(a)) {
            return map.get(a);
        }
        map.put(a, arr.size());
        arr.add(a);
        return arr.size() - 1;
    }
}
