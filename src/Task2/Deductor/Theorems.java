package Task2.Deductor;

import Task1.Checker.Comment;
import Task1.Checker.Input;
import Util.Constants.Rules;
import Util.Formatter.ExpressionFormatter;
import Util.Hash.Hash;
import Util.Tree.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Consumer;

/**
 * Created by izban on 23.05.2016.
 */
public class Theorems {
    static private String f(String s, Node... a) {
        return ExpressionFormatter.format(s, a);
    }

    static Consumer<String> makeAdd(HashSet<Node> was, Input _res) {
        return s -> {
            if (!_res.a.isEmpty() && _res.header.toProve.equals(_res.a.get(_res.a.size() - 1))) {
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
}
