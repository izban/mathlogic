package Task5.Kripke;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by izban on 29.05.2016.
 */
public class KripkeModel {
    public int worlds, variables;
    public ArrayList<Integer> e[];        // <= relation between worlds, worlds are int
    public boolean forced[][];            // set of true variables in the world
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

    boolean isPath(int u, int v) {
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
}
