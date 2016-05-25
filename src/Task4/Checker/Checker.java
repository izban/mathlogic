package Task4.Checker;

import Task4.Util.Constants.Rules;
import Task4.Util.Parser.Parser;
import Task4.Util.Tree.*;
import com.sun.deploy.security.ruleset.Rule;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static Task4.Checker.Axioms.trees;

/**
 * Created by izban on 21.05.2016.
 */
public class Checker {
    private HashMap<String, ArrayList<Node>> matched = new HashMap<>();
    private HashSet<Node> assumptions = new HashSet<>();
    private HashMap<Node, String> proven = new HashMap<>();
    private HashMap<Node, Integer> exprID = new HashMap<>();
    private HashMap<Node, ArrayList<Pair<Node, Integer>>> lazyProve = new HashMap<>();

    private boolean matchTrees(Node a, Node b) {
        if (a instanceof NodeFunction) {
            return a.type() == b.type() && a.toString().equals(b.toString());
        }
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

    HashSet<Node> matched2 = new HashSet<>();
    HashSet<String> willBinded = new HashSet<>();
    // <if matched ok, was x or not>
    private Pair<Boolean, Boolean> matchTrees2(Node a, Node b, String x) {
        if (a instanceof NodeFunction) {
            return new Pair<>(a.type() == b.type() && a.toString().equals(b.toString()), false);
        }
        if (a instanceof NodeVariable) {
            if (a.toString().equals(x)) {
                matched2.add(b);
                return new Pair<>(true, true);
            } else {
                return new Pair<>(a.equals(b), false);
            }
        }
        boolean ok = true;
        ok &= a.type() == b.type();
        ok &= a.children.length == b.children.length;
        if (!ok) {
            return new Pair<>(false, false);
        }
        boolean was = false;
        String nx = x;
        if (a instanceof NodeQuantor && a.children[0].toString().equals(x)) {
            nx = "";
        }
        for (int i = 0; i < a.children.length; i++) {
            Pair<Boolean, Boolean> o = matchTrees2(a.children[i], b.children[i], nx);
            ok &= o.getKey();
            was |= o.getValue();
        }
        if (a instanceof NodeQuantor && was) {
            willBinded.add(a.children[0].toString());
        }
        return new Pair<>(ok, was);
    }

    HashMap<String, Integer> binded = new HashMap<>();
    HashSet<String> free = new HashSet<>();
    private void findFree(Node v) {
        if (v instanceof NodeVariable) {
            String name = v.toString();
            if ((!binded.containsKey(name) || binded.get(name) == 0) && !name.isEmpty() && Character.isLowerCase(name.charAt(0))) {
                free.add(name);
            }
            return;
        }
        if (v instanceof NodeQuantor) {
            if (!binded.containsKey(v.children[0].toString())) {
                binded.put(v.children[0].toString(), 0);
            }
            binded.put(v.children[0].toString(), binded.get(v.children[0].toString()) + 1);
        }
        for (int i = 0; i < v.children.length; i++) {
            findFree(v.children[i]);
        }
        if (v instanceof NodeQuantor) {
            if (!binded.containsKey(v.children[0].toString())) {
                binded.put(v.children[0].toString(), 0);
            }
            binded.put(v.children[0].toString(), binded.get(v.children[0].toString()) - 1);
        }
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

    boolean free(Node x, String s) {
        if (x instanceof NodeVariable) {
            return s.equals(x.toString()) && (!s.isEmpty() && Character.isLowerCase(s.charAt(0)));
        }
        if (x instanceof NodeQuantor) {
            if (x.children[0].toString().equals(s)) {
                return false;
            }
            return free(x.children[1], s);
        }
        boolean res = false;
        for (int i = 0; !res && i < x.children.length; i++) {
            res |= free(x.children[i], s);
        }
        return res;
    }

    // f[x := t] == g
    boolean equal(Node f, Node g, String x, Node t) {
        matched2.clear();
        willBinded.clear();
        if (!matchTrees2(f, g, x).getKey()) return false;
        if (matched2.size() != 1) return false;
        return matched2.iterator().next().equals(t);
    }

    public Output check(Input input) {
        Output res = new Output();

        free.clear();
        HashSet<String> freeAssumption = new HashSet<>();
        if (input.header != null) {
            for (String p : input.header.assumptions) {
                assumptions.add(new Parser().parseTree(p));
            }
            if (!input.header.assumptions.isEmpty()) {
                findFree(Node.getTree(input.header.assumptions.get(input.header.assumptions.size() - 1)));
            }
            freeAssumption = free;
        }

        for (int i = 0; i < input.a.size(); i++) {
            //System.err.println(input.a.get(i));
            Node node;
            try {
                node = new Parser().parseTree(input.a.get(i));
            } catch (AssertionError e) {
                Output bad = new Output();
                bad.ok = false;
                bad.a.add(new Comment(0, input.a.get(i), "fail to parse"));
                return bad;
            }
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
            for (int k = 0; k < Axioms.trees.size(); k++) {
                Node axiom = Axioms.trees.get(k);
                matched = new HashMap<>();
                boolean ok = matchTrees(axiom, node);
                for (ArrayList<Node> v : matched.values()) {
                    for (int j = 1; j < v.size(); j++) {
                        ok &= v.get(0).equals(v.get(j));
                    }
                }
                if (ok) {
                    proved = true;
                    Comment c = new Comment(i + 1, input.a.get(i), Rules.axiom(Integer.toString(k + 1)));
                    res.a.add(c);
                    break;
                }
            }
            if (!proved) {
                for (int j = 0; j < AxiomsMath.trees.size(); j++) {
                    Node axiom = AxiomsMath.trees.get(j);
                    if (axiom.equals(node)) {
                        proved = true;
                        Comment c = new Comment(i + 1, input.a.get(i), Rules.axiom("A" + (j + 1)));
                        res.a.add(c);
                    }
                }
            }
            // check (f -> g) |- (f -> @x g) where x is not free in f
            rule1: if (!proved) {
                if (!(node instanceof NodeImpl)) break rule1;
                if (!(node.children[1] instanceof NodeForall)) break rule1;
                if (freeAssumption.contains(node.children[1].children[0].toString())) break rule1;
                if (free(node.children[0], node.children[1].children[0].toString())) break rule1;
                Node test = new NodeImpl(node.children[0], node.children[1].children[1]);
                if (exprID.containsKey(test)) {
                    proved = true;
                    Comment c = new Comment(i + 1, input.a.get(i), Rules.ruleForall(exprID.get(test)));
                    res.a.add(c);
                }
            }
            // check (f -> g) |- (?x f -> g) where x is not free in g
            rule2: if (!proved) {
                if (!(node instanceof NodeImpl)) break rule2;
                if (!(node.children[0] instanceof NodeExist)) break rule2;
                if (freeAssumption.contains(node.children[0].children[0].toString())) break rule2;
                if (free(node.children[1], node.children[0].children[0].toString())) break rule2;
                Node test = new NodeImpl(node.children[0].children[1], node.children[1]);
                if (exprID.containsKey(test)) {
                    proved = true;
                    Comment c = new Comment(i + 1, input.a.get(i), Rules.ruleExist(exprID.get(test)));
                    res.a.add(c);
                }
            }
            // check axiom @x f -> f(x := t)
            axiom11: if (!proved) {
                if (!(node instanceof NodeImpl)) break axiom11;
                if (!(node.children[0] instanceof NodeForall)) break axiom11;
                matched2.clear();
                willBinded.clear();
                if (!matchTrees2(node.children[0].children[1], node.children[1], node.children[0].children[0].toString()).getKey()) break axiom11;
                if (matched2.size() > 1) break axiom11;
                free = new HashSet<>();
                if (!matched2.isEmpty()) findFree(matched2.iterator().next());
                for (String x : willBinded) {
                    if (free.contains(x)) {
                        break axiom11;
                    }
                }
                proved = true;
                Comment c = new Comment(i + 1, input.a.get(i), Rules.axiom("11"));
                res.a.add(c);
            }
            // check axiom f(x := t) -> ?x f
            axiom12: if (!proved) {
                if (!(node instanceof NodeImpl)) break axiom12;
                if (!(node.children[1] instanceof NodeExist)) break axiom12;
                matched2.clear();
                willBinded.clear();
                if (!matchTrees2(node.children[1].children[1], node.children[0], node.children[1].children[0].toString()).getKey()) break axiom12;
                if (matched2.size() > 1) break axiom12;
                free = new HashSet<>();
                if (!matched2.isEmpty()) findFree(matched2.iterator().next());
                for (String x : willBinded) {
                    if (free.contains(x)) {
                        break axiom12;
                    }
                }
                proved = true;
                Comment c = new Comment(i + 1, input.a.get(i), Rules.axiom("12"));
                res.a.add(c);
            }
            // check axiom (f[x := 0]) & @x (f -> f[x := x']) -> f
            axiomA9: if (!proved) {
                if (!(node instanceof NodeImpl)) break axiomA9;
                if (!(node.children[0] instanceof NodeAnd)) break axiomA9;
                if (!(node.children[0].children[1] instanceof NodeForall)) break axiomA9;
                if (!(node.children[0].children[1].children[1] instanceof NodeImpl)) break axiomA9;
                Node f1 = node.children[0].children[0];
                Node f2 = node.children[0].children[1].children[1].children[0];
                Node f3 = node.children[0].children[1].children[1].children[1];
                Node f4 = node.children[1];
                String x = node.children[0].children[1].children[0].toString();
                if (!f2.equals(f4)) break axiomA9;
                Node f = f2;
                if (!equal(f1, f, x, new NodeZero()));
                if (!equal(f3, f, x, new NodeInc(new NodeVariable(x))));

                proved = true;
                Comment c = new Comment(i + 1, input.a.get(i), Rules.axiom("A9"));
                res.a.add(c);
            }

            if (!proved) {
                Comment c = new Comment(i + 1, input.a.get(i), Rules.notProved());
                res.a.add(c);
                res.ok = false;
            } else {
                addProven(node, i + 1);
            }
        }

        return res;
    }
}
