package Task3.Prover;

import Task1.Checker.Checker;
import Task1.Checker.Header;
import Task1.Checker.Input;
import Task2.Deductor.Deductor;
import Util.Tree.Node;
import Util.Tree.NodeBinary;
import Util.Tree.NodeType;
import Util.Tree.NodeUnary;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static Task3.Prover.Theorems.assumptionRemoval;
import static Util.Constants.StringConstants.EXPRISFALSE;
import static Util.Constants.StringConstants.VAL;
import static Util.Simplificator.Simplificator.simplify;

/**
 * Created by izban on 23.05.2016.
 */
public class Prover {
    HashSet<String> set = new HashSet<>();
    HashMap<String, Boolean> values = new HashMap<>();
    String variables[];
    Node t;

    void dfs1(Node v) {
        for (int i = 0; i < v.children.length; i++) {
            dfs1(v.children[i]);
        }
        if (v.type() == NodeType.VARIABLE) {
            set.add(v.toString());
        }
    }

    boolean dfs2(Node v) {
        if (v.type() == NodeType.VARIABLE) {
            return values.get(v.toString());
        }
        boolean a[] = new boolean[v.children.length];
        for (int i = 0; i < v.children.length; i++) {
            a[i] = dfs2(v.children[i]);
        }
        return v.calcValue(a);
    }

    Pair<ArrayList<String>, Boolean> makeStructuralProof(Node t) {
        if (t.type().equals(NodeType.VARIABLE)) {
            ArrayList<String> a = new ArrayList<>();
            a.add((values.get(t.toString()) ? "" : "!") + t.toString());
            return new Pair<>(a, values.get(t.toString()));
        }
        boolean v[] = new boolean[t.children.length];
        ArrayList<String> res1 = new ArrayList<>();
        String g[] = new String[t.children.length];
        for (int i = 0; i < t.children.length; i++) {
            Pair<ArrayList<String>, Boolean> o = makeStructuralProof(t.children[i]);
            v[i] = o.getValue();
            res1.addAll(o.getKey());
            g[i] = t.children[i].toString();
        }
        boolean res2 = t.calcValue(v);

        String name = "";
        for (int i = 0; i < t.children.length; i++) {
            if (!v[i]) {
                name += "!";
            }
            name += (char)('A' + i);
            if (i + 1 < t.children.length) {
                name += ",";
            }
        }
        name += "|-";
        if (!res2) {
            name += "!(";
        }
        if (t instanceof NodeBinary) {
            name += "A" + t.type().toString() + "B";
        }
        if (t instanceof NodeUnary) {
            name += t.type().toString() + "A";
        }
        if (!res2) {
            name += ")";
        }
        res1.addAll(GathererProofs.getProof(name, g));
        return new Pair<>(res1, res2);
    }

    Input getProof(int cur) {
        if (cur == variables.length) {
            Input res = new Input();
            res.header = new Header();
            res.header.toProve = t.toString();
            for (String variable : variables) {
                res.header.assumptions.add((values.get(variable) ? "" : "!") + variable);
            }
            res.a = makeStructuralProof(t).getKey();
            return simplify(res);
        }
        values.put(variables[cur], false);
        Input proof1 = getProof(cur + 1);
        values.put(variables[cur], true);
        Input proof2 = getProof(cur + 1);

        proof1 = new Deductor().deduce(proof1);
        proof2 = new Deductor().deduce(proof2);

        return assumptionRemoval(proof1, proof2);
    }

    public Input prove(String expr) {
        t = Node.getTree(expr);
        dfs1(t);
        variables = new String[set.size()];
        variables = set.toArray(variables);
        boolean ok = true;
        for (int mask = 0; mask < 1 << variables.length; mask++) {
            for (int i = 0; i < variables.length; i++) {
                values.put(variables[i], (mask & (1 << i)) != 0);
            }
            if (!dfs2(t)) {
                StringBuilder res = new StringBuilder();
                res.append(EXPRISFALSE);
                for (int i = 0; i < variables.length; i++) {
                    res.append(variables[i]).append("=").append(VAL[(mask >> i) & 1]);
                    if (i + 1 < variables.length) {
                        res.append(",");
                    }
                }
                ArrayList<String> o = new ArrayList<>();
                o.add(res.toString());
                return new Input(o);
            }
        }

        Input res = getProof(0);

        return res;
    }
}
