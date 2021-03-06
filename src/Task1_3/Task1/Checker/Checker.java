package Task1_3.Task1.Checker;

import Common.Pair;
import Task1_3.Util.Constants.Rules;
import Task1_3.Util.Parser.Parser;
import Task1_3.Util.Tree.Node;
import Task1_3.Util.Tree.NodeImpl;
import Task1_3.Util.Tree.NodeVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static Task1_3.Task1.Checker.Axioms.trees;

/**
 * Created by izban on 21.05.2016.
 */
public class Checker {
    private HashMap<String, ArrayList<Node>> matched = new HashMap<>();
    private final HashSet<Node> assumptions = new HashSet<>();
    private final HashMap<Node, String> proven = new HashMap<>();
    private final HashMap<Node, Integer> exprID = new HashMap<>();
    private final HashMap<Node, ArrayList<Pair<Node, Integer>>> lazyProve = new HashMap<>();

    private boolean matchTrees(Node a, Node b) {
        if (a instanceof NodeVariable) {
            String s = a.toString();
            if (!matched.containsKey(s)) {
                matched.put(s, new ArrayList<>());
            }
            matched.get(s).add(b);
            return true;
        }
        boolean ok = true;
        ok &= a.type() == b.type();
        ok &= a.children.length == b.children.length;
        if (!ok) {
            return false;
        }
        for (int i = 0; i < a.children.length; i++) {
            ok &= matchTrees(a.children[i], b.children[i]);
        }
        return ok;
    }

    private void addProven(Node node, int id) {
        exprID.put(node, id);
        if (node instanceof NodeImpl) {
            if (exprID.containsKey(node.children[0])) {
                proven.put(node.children[1], Rules.MP(exprID.get(node.children[0]), exprID.get(node)));
            }
            if (!proven.containsKey(node.children[0])) {
                if (!lazyProve.containsKey(node.children[0])) {
                    lazyProve.put(node.children[0], new ArrayList<>());
                }
                lazyProve.get(node.children[0]).add(new Pair<>(node.children[1], id));
            }
        }
        if (lazyProve.containsKey(node)) {
            for (Pair<Node, Integer> o : lazyProve.get(node)) {
                proven.put(o.getKey(), Rules.MP(id, o.getValue()));
            }
            lazyProve.remove(node);
        }
    }

    public Output check(Input input) {
        Output res = new Output();

        if (input.header != null) {
            for (String p : input.header.assumptions) {
                if (!p.isEmpty()) {
                    assumptions.add(new Parser().parseTree(p));
                }
            }
        }

        for (int i = 0; i < input.a.size(); i++) {
            Node node = new Parser().parseTree(input.a.get(i));

            if (assumptions.contains(node)) {
                Comment c = new Comment(i + 1, input.a.get(i), Rules.hypothesis());
                res.a.add(c);
                addProven(node, i + 1);
                continue;
            }
            if (proven.containsKey(node)) {
                Comment c = new Comment(i + 1, input.a.get(i), proven.get(node));
                res.a.add(c);
                addProven(node, i + 1);
                continue;
            }
            boolean proved = false;
            for (int k = 0; k < trees.size(); k++) {
                Node axiom = trees.get(k);
                matched = new HashMap<>();
                boolean ok = matchTrees(axiom, node);
                for (ArrayList<Node> v : matched.values()) {
                    for (int j = 1; j < v.size(); j++) {
                        ok &= v.get(0).equals(v.get(j));
                    }
                }
                if (ok) {
                    proved = true;
                    Comment c = new Comment(i + 1, input.a.get(i), Rules.axiom(k + 1));
                    res.a.add(c);
                    addProven(node, i + 1);
                    break;
                }
            }
            if (!proved) {
                Comment c = new Comment(i + 1, input.a.get(i), Rules.notProved());
                res.a.add(c);
                res.ok = false;
            }
        }

        return res;
    }
}
