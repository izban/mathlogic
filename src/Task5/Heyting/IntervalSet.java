package Task5.Heyting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by izban on 29.05.2016.
 */
public class IntervalSet {
    ArrayList<Interval> a = new ArrayList<>();

    IntervalSet() {}
    IntervalSet(ArrayList<Interval> a) {
        this.a = new ArrayList<>(a);
    }
    IntervalSet(IntervalSet o) {
        this.a = new ArrayList<>(o.a);
    }

    void upd() {
        Collections.sort(a);
        ArrayList<Interval> ans = new ArrayList<>();

        int curLeft = 0, curRight = 0;
        boolean curIncL = false, curIncR = false;
        for (int i = 0; i < a.size(); i++) {
            if (i == 0 || a.get(i).left > curRight || a.get(i).left == curRight && !a.get(i).incL && !curIncR) {
                curLeft = a.get(i).left;
                curRight = a.get(i).right;
                curIncL = a.get(i).incL;
                curIncR = a.get(i).incR;
            }
            if (curRight < a.get(i).right || curRight == a.get(i).right && a.get(i).incR) {
                curRight = a.get(i).right;
                curIncR = a.get(i).incR;
            }
            if (i + 1 == a.size() || curRight < a.get(i + 1).left || curRight == a.get(i + 1).left && !curIncR && !a.get(i + 1).incL) {
                ans.add(new Interval(curLeft, curIncL, curRight, curIncR));
            }
        }
        a = ans;
    }

    IntervalSet inv() {
        ArrayList<Interval> ans = new ArrayList<>();
        if (a.isEmpty()) {
            ans.add(new Interval(Integer.MIN_VALUE, false, Integer.MAX_VALUE, false));
            return new IntervalSet(ans);
        }
        if (a.get(0).left != Integer.MIN_VALUE) {
            ans.add(new Interval(Integer.MIN_VALUE, false, a.get(0).left, !a.get(0).incL));
        }
        for (int i = 0; i < a.size() - 1; i++) {
            ans.add(new Interval(a.get(i).right, !a.get(i).incR, a.get(i + 1).left, !a.get(i + 1).incL));
        }
        if (a.get(a.size() - 1).right != Integer.MAX_VALUE) {
            ans.add(new Interval(a.get(a.size() - 1).right, !a.get(a.size() - 1).incR, Integer.MAX_VALUE, false));
        }
        return new IntervalSet(ans);
    }

    @Override
    public String toString() {
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < a.size(); i++) {
            ans.append(a.get(i).toString());
            if (i + 1 < a.size()) {
                ans.append("|");
            }
        }
        if (a.isEmpty()) {
            ans.append("(0;0)");
        }
        return ans.toString();
    }

    IntervalSet or(IntervalSet o) {
        IntervalSet ans = new IntervalSet();
        for (int i = 0; i < a.size(); i++) {
            ans.a.add(a.get(i));
        }
        for (int i = 0; i < o.a.size(); i++) {
            ans.a.add(o.a.get(i));
        }
        ans.upd();
        return ans;
    }

    IntervalSet and(IntervalSet o) {
        return new IntervalSet(this).inv().or(o.inv()).inv();
    }

    public IntervalSet interior() {
        IntervalSet ans = new IntervalSet();
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i).left != a.get(i).right) {
                ans.a.add(new Interval(a.get(i).left, false, a.get(i).right, false));
            }
        }
        return ans;
    }
}
