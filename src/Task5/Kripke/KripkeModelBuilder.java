package Task5.Kripke;

import Task1_3.Util.Tree.Node;
import Task1_3.Util.Tree.NodeAnd;
import Task1_3.Util.Tree.NodeNot;
import Task1_3.Util.Tree.NodeOr;
import Task1_3.Util.Tree.NodeImpl;
import Task1_3.Util.Tree.NodeVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by izban on 29.05.2016.
 */
public class KripkeModelBuilder {
    final private static int MAX_WORLDS = 7;

    public KripkeModelBuilder() {}

    private int worlds;
    private KripkeModel cur;
    private final HashSet<String> variables = new HashSet<>();
    private String[] variables_list;
    private final HashMap<String, Integer> variable_id = new HashMap<>();
    private Node expression;
    private HashMap<Integer, HashMap<Node, Boolean>> mp;

    private boolean getValue(int world, Node expr) {
        if (mp.containsKey(world) && mp.get(world).containsKey(expr)) {
            return mp.get(world).get(expr);
        }
        boolean res;
        if (expr instanceof NodeVariable) {
            res = cur.forced[world][variable_id.get(expr.toString())];
        } else if (expr instanceof NodeOr) {
            res = getValue(world, expr.children[0]) || getValue(world, expr.children[1]);
        } else if (expr instanceof NodeAnd) {
            res = getValue(world, expr.children[0]) && getValue(world, expr.children[1]);
        } else if (expr instanceof NodeNot) {
            res = true;
            res &= !getValue(world, expr.children[0]);
            for (int i = 0; i < cur.e[world].size(); i++) {
                res &= !getValue(cur.e[world].get(i), expr.children[0]);
            }
        } else if (expr instanceof NodeImpl) {
            res = true;
            res &= !getValue(world, expr.children[0]) || getValue(world, expr.children[1]);
            for (int i = 0; i < cur.e[world].size(); i++) {
                res &= !getValue(cur.e[world].get(i), expr.children[0]) || getValue(cur.e[world].get(i), expr.children[1]);
            }
        } else throw new AssertionError();

        if (!mp.containsKey(world)) {
            mp.put(world, new HashMap<>());
        }
        mp.get(world).put(expr, res);
        return mp.get(world).get(expr);
    }

    private KripkeModel buildVariables(int x) {
        if (x == worlds) {
            mp = new HashMap<>();
            if (!getValue(0, expression)) {
                return cur;
            }
            return null;
        } else {
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < cur.e[i].size(); j++) {
                    if (cur.e[i].get(j) == x) {
                        for (int k = 0; k < cur.variables; k++) {
                            cur.forced[x][k] |= cur.forced[i][k];
                        }
                    }
                }
            }
            ArrayList<Integer> a = new ArrayList<>();
            for (int i = 0; i < cur.variables; i++) {
                if (!cur.forced[x][i]) {
                    a.add(i);
                }
            }
            for (int mask = 0; mask < 1 << a.size(); mask++) {
                for (int i = 0; i < a.size(); i++) {
                    if (((mask >> i) & 1) == 1) {
                        cur.forced[x][a.get(i)] = true;
                    }
                }
                KripkeModel res = buildVariables(x + 1);
                if (res != null) return res;
                for (int i = 0; i < a.size(); i++) {
                    if (((mask >> i) & 1) == 1) {
                        cur.forced[x][a.get(i)] = false;
                    }
                }
            }
            return null;
        }
    }

    private KripkeModel buildWorlds(int x) {
        if (x == worlds) {
            return buildVariables(0);
        } else {
            for (int mask = 1; mask < 1 << x; mask++) {
                ArrayList<Integer> a = new ArrayList<>();
                for (int i = 0; i < x; i++) {
                    if (((mask >> i) & 1) == 1) {
                        a.add(i);
                    }
                }
                boolean bad = false;
                for (int i = 0; i < a.size(); i++) {
                    for (int j = 0; j < a.size(); j++) {
                        if (i == j) continue;
                        if (cur.isPath(a.get(i), a.get(j))) {
                            bad = true;
                        }
                    }
                }
                if (bad) continue;
                for (int i = 0; i < a.size(); i++) {
                    cur.e[a.get(i)].add(x);
                }
                KripkeModel ans = buildWorlds(x + 1);
                if (ans != null) {
                    return ans;
                }
                for (int i = 0; i < a.size(); i++) {
                    cur.e[a.get(i)].remove(cur.e[a.get(i)].size() - 1);
                }
            }
            return null;
        }
    }

    private void getVariables(Node t) {
        if (t instanceof Task1_3.Util.Tree.NodeVariable) {
            variables.add(t.toString());
        }
        for (Node x : t.children) {
            getVariables(x);
        }
    }

    public KripkeModel getCountertest(Node n) {
        expression = n;
        getVariables(n);
        variables_list = variables.toArray(new String[variables.size()]);
        for (int i = 0; i < variables_list.length; i++) {
            variable_id.put(variables_list[i], i);
        }
        for (worlds = 1; worlds <= MAX_WORLDS; worlds++) {
            cur = new KripkeModel(worlds, variables.size());
            KripkeModel m = buildWorlds(1);
            if (m != null) {
                cur.variableNames = variables_list;
                return m;
            }
        }
        // probably, there is no countertest
        return null;
    }
}
