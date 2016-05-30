package Task5.Kripke;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by izban on 29.05.2016.
 */
public class KripkeModel {
    public final int worlds;
    public final int variables;
    public final ArrayList<Integer>[] e;        // <= relation between worlds, worlds are int
    public final boolean[][] forced;            // set of true variables in the world
    public String variableNames[];

    KripkeModel(int worlds, int variables) {
        this.worlds = worlds;
        this.variables = variables;
        e = new ArrayList[worlds];
        forced = new boolean[worlds][variables];
        for (int i = 0; i < worlds; i++) {
            e[i] = new ArrayList<>();
        }
    }

    public boolean isPath(int u, int v) {
        int n = worlds;
        boolean was[] = new boolean[n];
        int q[] = new int[n];
        int l = 0, r = 0;
        q[r++] = u;
        was[u] = true;
        while (l < r) {
            int x = q[l++];
            for (int i = 0; i < e[x].size(); i++) {
                int to = e[x].get(i);
                if (!was[to]) {
                    was[to] = true;
                    q[r++] = to;
                }
            }
        }
        return was[v];
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(worlds + " worlds\r\n");
        for (int i = 0; i < worlds; i++) {
            for (int j = 0; j < e[i].size(); j++) {
                res.append(i + "<=" + e[i].get(j) + "\r\n");
            }
        }
        for (int i = 0; i < worlds; i++) {
            res.append("forced variables in " + i + ":");
            for (int j = 0; j < variables; j++) {
                if (forced[i][j]) {
                    res.append(" " + variableNames[j]);
                }
            }
            res.append("\n");
        }
        return res.toString();
    }
}
