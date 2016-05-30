package Task5.Heyting;

import Task1_3.Util.Tree.Node;
import Task5.Kripke.KripkeModel;
import Task5.Kripke.KripkeModelBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by izban on 29.05.2016.
 */
public class HeytingSolver {
    public Heyting solve(String s) {
        Node t = Node.getTree(s);
        KripkeModel model = new KripkeModelBuilder().getCountertest(t);
        if (model == null) return null;

        ArrayList<Integer> submasks = new ArrayList<>();
        HashMap<Integer, Integer> mp = new HashMap<>();
        for (int mask = 0; mask < 1 << model.worlds; mask++) {
            boolean ok = true;
            for (int i = 0; i < model.worlds; i++) if (((mask >> i) & 1) == 1) {
                for (int j = 0; j < model.worlds; j++) if (model.isPath(i, j)) {
                    ok &= 1 == (1 & (mask >> j));
                }
            }
            if (ok) {
                mp.put(mask, submasks.size());
                submasks.add(mask);
            }
        }
        ArrayList<Integer> points[] = new ArrayList[submasks.size()];
        for (int i = 0; i < submasks.size(); i++) {
            points[i] = new ArrayList<>();
            points[i].add(i);
            for (int j = 0; j < submasks.size(); j++) {
                boolean ok = false;
                for (int k = 0; k < model.worlds; k++) {
                    if (((submasks.get(j) >> k) & 1) == 1 && (((submasks.get(i) >> k) & 1) == 0)) {
                        ok = true;
                    }
                }
                if (ok) {
                    points[i].add(j);
                }
            }
        }

        /*IntervalSet intervals[] = new IntervalSet[model.worlds];
        HashSet<Integer> points[] = new HashSet[model.worlds];
        for (int i = 0; i < model.worlds; i++) points[i] = new HashSet<>();
        for (int i = 0; i < model.worlds; i++) {
            if (i > 0) {
                points[i].add(i * 2);
            }
            Integer p[] = points[i].toArray(new Integer[points[i].size()]);
            Arrays.sort(p);
            if (p.length != 0) {
                intervals[i] = new IntervalSet();
                intervals[i].a.add(new Interval(Integer.MIN_VALUE, false, p[0], false));
                for (int j = 0; j < p.length - 1; j++) {
                    intervals[i].a.add(new Interval(p[j], false, p[j + 1], false));
                }
                intervals[i].a.add(new Interval(p[p.length - 1], false, Integer.MAX_VALUE, false));
            }
            points[i].add(i * 2 + 1);
            for (int j = 0; j < model.e[i].size(); j++) {
                points[model.e[i].get(j)].addAll(points[i]);
            }
            intervals[i] = new IntervalSet();
            for (int j = 0; j < i; j++) {
                if (model.isPath(j, i)) {
                    for (Interval x : intervals[j].a) {
                        intervals[i].a.add(x);
                    }
                }
            }
            //intervals[i] = new IntervalSet();
            //intervals[i].a.add(new Interval(i, false, i + 1, false));
            //intervals[i].a.add(new Interval(2 * i + 1, false, 2 * i + 2, false));
        }
        */

        Heyting ans = new Heyting();
        for (int i = 0; i < model.variables; i++) {
            IntervalSet cur = new IntervalSet();
            int cmask = 0;
            for (int j = 0; j < model.worlds; j++) {
                if (model.forced[j][i]) {
                    cmask |= 1 << j;
                }
            }
            for (int x : points[mp.get(cmask)]) {
                cur.a.add(new Interval(x * 2, true, x * 2 + 1, true));
            }
            cur = cur.inv();
            //cur.a.add(new Interval(0, false, 1, false));
            //for (int j = 0; j < model.worlds; j++) {
            //    if (!model.forced[j][i]) {
                    //cur.a.add(intervals[j]);
                    /*for (Interval x : intervals[j].a) {
                        cur.a.add(x);
                    }*/
            //        cur.a.add(new Interval(j, true, j, true));
            //    }
            //}
            //if (!cur.a.isEmpty()) {
            //cur = cur.inv();
            //}
            cur.upd();
            ans.mp.put(model.variableNames[i], cur);
        }
        if ((new HeytingChecker().checkTrue(t, ans))) {
            new HeytingChecker().checkTrue(t, ans);
            throw new AssertionError("incorrect Heyting sertificate");
        }
        return ans;
    }
}
