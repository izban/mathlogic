package Task5.Heyting;

import Task1_3.Util.Tree.Node;
import Task5.Kripke.KripkeModel;
import Task5.Kripke.KripkeModelBuilder;
import Task5.Kripke.KripkeModelTreeBuilder;

import java.nio.channels.InterruptedByTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by izban on 29.05.2016.
 */
public class HeytingSolver {
    IntervalSet intervalsWorld[];

    void dfs(KripkeModel model, int v, int l, int r) {
        if (model.e[v].size() == 0) {
            intervalsWorld[v].a.add(new Interval(l, false, r, false));
            return;
        }
        int a[] = new int[Math.max(1, model.e[v].size() - 1)];
        long len = (long) r - (long) l + 1;
        long av = len / (a.length + 1);
        for (int i = 0; i < a.length; i++) {
            a[i] = (int)(l + av * (i + 1));
        }
        int last = l;
        for (int i = 0; i < a.length; i++) {
            dfs(model, model.e[v].get(i), last, a[i]);
            intervalsWorld[v].a.add(new Interval(a[i], true, a[i], true));
            last = a[i];
        }
        dfs(model, model.e[v].get(model.e[v].size() - 1), last, r);
    }

    public Heyting solve(String s) {
        Node t = Node.getTree(s);
        KripkeModel model = new KripkeModelTreeBuilder().getCountertest(t);
        if (model == null) return null;
        intervalsWorld = new IntervalSet[model.worlds];
        for (int i = 0; i < model.worlds; i++) intervalsWorld[i] = new IntervalSet();
        dfs(model, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);

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

        Heyting ans = new Heyting();
        for (int i = 0; i < model.variables; i++) {
            IntervalSet cur = new IntervalSet();
            int cmask = 0;
            for (int j = 0; j < model.worlds; j++) {
                if (model.forced[j][i]) {
                    cmask |= 1 << j;
                    cur = cur.or(intervalsWorld[j]);
                }
            }

            /*for (int x : points[mp.get(cmask)]) {
                cur.a.add(new Interval(x * 2, true, x * 2 + 1, true));
            }*/
            //cur = cur.inv();
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
            System.err.println("incorrect sertificate for " + s);
            return null;
            //throw new AssertionError("incorrect Heyting sertificate");
        }
        return ans;
    }
}
