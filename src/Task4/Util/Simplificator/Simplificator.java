package Task4.Util.Simplificator;

import Task4.Checker.Checker;
import Task4.Checker.Input;
import Task4.Checker.Output;
import Task4.Util.Tree.Node;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashSet;

import static Task4.Util.Constants.Rules.unExist;
import static Task4.Util.Constants.Rules.unForall;
import static Task4.Util.Constants.Rules.unMP;
import static Task4.Util.Constants.StringConstants.EXIST;
import static Task4.Util.Constants.StringConstants.FORALL;
import static Task4.Util.Constants.StringConstants.MP;

/**
 * Created by izban on 23.05.2016.
 */
public class Simplificator {
    static public Input simplify(Input input) {
        return input; /*
        if (input.header != null) {
            while (!input.a.isEmpty() && !Tree.getTree(input.a.get(input.a.size() - 1)).equals(Tree.getTree(input.header.toProve))) {
                input.a.remove(input.a.size() - 1);
            }
            if (input.a.isEmpty()) {
                throw new AssertionError("not proved");
            }
        }
        Output output = new Checker().check(input, false);
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
            if (output.a.get(i).annotation.startsWith(FORALL)) {
                need[unForall(output.a.get(i).annotation) - 1] = true;
            }
            if (output.a.get(i).annotation.startsWith(EXIST)) {
                need[unExist(output.a.get(i).annotation) - 1] = true;
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
        return ans;*/
    }
}
