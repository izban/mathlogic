package Task5.Heyting;

import Task1_3.Util.Tree.*;

/**
 * Created by izban on 29.05.2016.
 */
public class HeytingChecker {
    IntervalSet dfs(Node t, Heyting m) {
        if (t instanceof NodeVariable) {
            return m.mp.get(t.toString());
        } else if (t instanceof NodeOr) {
            return dfs(t.children[0], m).or(dfs(t.children[1], m));
        } else if (t instanceof NodeAnd) {
            return dfs(t.children[0], m).and(dfs(t.children[1], m));
        } else if (t instanceof NodeNot) {
            return dfs(t.children[0], m).inv();
        } else if (t instanceof NodeImpl) {
            return dfs(t.children[0], m).inv().or(dfs(t.children[1], m));
        } else throw new AssertionError();
    }

    boolean checkTrue(Node t, Heyting m) {
        IntervalSet a = dfs(t, m);
        return a.a.size() == 1 && a.a.get(0).equals(new Interval(Integer.MIN_VALUE, false, Integer.MAX_VALUE, false));
    }
}
