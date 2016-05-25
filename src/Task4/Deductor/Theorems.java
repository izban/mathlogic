package Task4.Deductor;

import Task4.Checker.Header;
import Task4.Checker.Input;
import Task4.Util.Formatter.ExpressionFormatter;
import Task4.Util.Tree.Node;
import Task4.Util.Tree.NodeImpl;

import java.util.HashSet;
import java.util.function.Consumer;

import static Task4.Deductor.GathererProofs.getProof;

/**
 * Created by izban on 23.05.2016.
 */
public class Theorems {
    static private String f(String s, Node... a) {
        return ExpressionFormatter.format(s, a);
    }

    static Consumer<String> makeAdd(HashSet<Node> was, Input _res) {
        return s -> {
            if (!_res.a.isEmpty() && Node.getTree(_res.header.toProve).equals(Node.getTree(_res.a.get(_res.a.size() - 1)))) {
                return;
            }
            Node n = Node.getTree(s);
            if (was.contains(n)) return;
            was.add(n);
            _res.a.add(s);
        };
    }

    static public void addAToA(HashSet<Node> was, Input res, Node a) {
        Consumer<String> add = makeAdd(was, res);
        add.accept(f("A->A->A", a));
        add.accept(f("(A->A->A)->(A->(A->A)->A)->(A->A)", a));
        add.accept(f("(A->(A->A)->A)->(A->A)", a));
        add.accept(f("(A->(A->A)->A)", a));
        add.accept(f("A->A", a));
    }

    static public void addDeductHypothesis(HashSet<Node> was, Input res, Node a, Node h) {
        Consumer<String> add = makeAdd(was, res);
        add.accept(f("A", h));
        add.accept(f("A->B->A", h, a));
        add.accept(f("A->B", a, h));
    }

    static public void addDeductImpl(HashSet<Node> was, Input res, Node a, Node hj, Node hi) {
        Consumer<String> add = makeAdd(was, res);
        add.accept(f("(A->B)->(A->B->C)->(A->C)", a, hj, hi));
        add.accept(f("(A->B->C)->(A->C)", a, hj, hi));
        add.accept(f("A->B", a, hi));
    }

    // ?x f -> g
    static public void addDeductExist(HashSet<Node> was, Input res, Node a, Node f, Node g, String x) {
        Consumer<String> add = makeAdd(was, res);
        for (String s : getProof("|-(A->B->C)->(B->A->C)", a.toString(), f.toString(), g.toString())) add.accept(s);
        for (String s : getProof("|-(A->B->C)->(B->A->C)", "?" + x + "(" + f.toString() + ")", a.toString(), g.toString())) add.accept(s);
        String aa = "(" + a + ")";
        String ff = "(" + f + ")";
        String gg = "(" + g + ")";
        add.accept(ff + "->" + aa + "->" + gg);
        add.accept("?" + x + ff + "->" + aa + "->" + gg);
        add.accept(aa + "->" + "?" + x + ff + "->" + gg);
    }

    // f -> @x g
    static public void addDeductForall(HashSet<Node> was, Input res, Node a, Node f, Node g, String x) {
        Consumer<String> add = makeAdd(was, res);
        for (String s : getProof("|-(A&B->C)->(A->B->C)", a.toString(), f.toString(), "@" + x + "(" + g.toString() + ")")) add.accept(s);
        for (String s : getProof("|-(A->B->C)->(A&B->C)", a.toString(), f.toString(), g.toString())) add.accept(s);
        String aa = "(" + a + ")";
        String ff = "(" + f + ")";
        String gg = "(" + g + ")";
        add.accept(aa + "&" + ff + "->" + gg);
        add.accept(aa + "&" + ff + "->" + "@" + x + gg);
        add.accept(aa + "->" + ff + "->" + "@" + x + gg);
    }
}
