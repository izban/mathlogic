package Task5.Heyting;

import java.util.HashMap;

/**
 * Created by izban on 29.05.2016.
 */
public class Heyting {
    final HashMap<String, IntervalSet> mp = new HashMap<>();

    @Override
    public String toString() {
        StringBuilder ans = new StringBuilder();
        mp.forEach((s, a) -> {
            ans.append(s).append(" = ").append(a).append("\r\n");
        });
        return ans.toString();
    }
}
