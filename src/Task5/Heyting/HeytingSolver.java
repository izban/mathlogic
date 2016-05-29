package Task5.Heyting;

import Task1_3.Util.Tree.Node;
import Task5.Kripke.KripkeModel;
import Task5.Kripke.KripkeModelBuilder;

/**
 * Created by izban on 29.05.2016.
 */
public class HeytingSolver {
    public Heyting solve(String s) {
        Node t = Node.getTree(s);
        KripkeModel model = new KripkeModelBuilder().getCountertest(t);
        if (model == null) return null;
        Interval intervals[] = new Interval[model.worlds];
        for (int i = 0; i < model.worlds; i++) {
            intervals[i] = new Interval(i, false, i + 1, false);
        }
        Heyting ans = new Heyting();
        for (int i = 0; i < model.variables; i++) {
            IntervalSet cur = new IntervalSet();
            for (int j = 0; j < model.worlds; j++) {
                if (model.forced[j][i]) {
                    cur.a.add(intervals[j]);
                }
            }
            cur.upd();
            ans.mp.put(model.variableNames[i], cur);
        }
        if ((new HeytingChecker().checkTrue(t, ans))) {
            throw new AssertionError("incorrect Heyting sertificate");
        }
        return ans;
    }
}
