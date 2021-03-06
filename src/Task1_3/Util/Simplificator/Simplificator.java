package Task1_3.Util.Simplificator;

import Common.Pair;
import Task1_3.Task1.Checker.Checker;
import Task1_3.Task1.Checker.Input;
import Task1_3.Task1.Checker.Output;

import java.util.ArrayList;
import java.util.HashSet;

import static Task1_3.Util.Constants.Rules.unMP;
import static Task1_3.Util.Constants.StringConstants.MP;

/**
 * Created by izban on 23.05.2016.
 */
public class Simplificator {
    static public Input simplify(Input input) {
        if (input.header != null) {
            while (!input.a.isEmpty() && !input.a.get(input.a.size() - 1).equals(input.header.toProve)) {
                input.a.remove(input.a.size() - 1);
            }
            if (input.a.isEmpty()) {
                throw new AssertionError("not proved");
            }
        }
        Output output = new Checker().check(input);
        boolean need[] = new boolean[output.a.size()];
        need[need.length - 1] = true;
        int cnt = 0;
        for (int i = output.a.size() - 1; i >= 0; i--) {
            if (!need[i]) {
                continue;
            }
            if (output.a.get(i).annotation.startsWith(MP)) {
                Pair<Integer, Integer> o = unMP(output.a.get(i).annotation);
                need[o.getKey() - 1] = true;
                need[o.getValue() - 1] = true;
            }
        }
        ArrayList<String> res = new ArrayList<>();
        HashSet<String> o = new HashSet<>();
        for (int i = 0; i < output.a.size(); i++) {
            if (need[i] && !o.contains(output.a.get(i).expr)) {
                res.add(output.a.get(i).expr);
                o.add(output.a.get(i).expr);
            }
        }

        Input ans = new Input();
        ans.header = input.header;
        ans.a = res;
        return ans;
    }
}
