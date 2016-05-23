package Task3.Prover;

import Task1.Checker.Header;
import Task1.Checker.Input;
import Util.Formatter.ExpressionFormatter;
import Util.Tree.Node;
import Util.Tree.NodeVariable;

import java.util.ArrayList;

import static Task3.Prover.GathererProofs.getProof;
import static Util.Simplificator.Simplificator.simplify;

/**
 * Created by izban on 23.05.2016.
 */
public class Theorems {
    static private String f(String s, String... a) {
        return ExpressionFormatter.format(s, a);
    }
    static private String f(String s, Node... a) {
        return ExpressionFormatter.format(s, a);
    }

    public static ArrayList<String> aOrNotA(Node t) {
        return getProof("A|!A", t.toString());
    }

    public static Input assumptionRemoval(Input input1, Input input2) {
        Input res = new Input();
        String p = Node.getTree(input1.a.get(input1.a.size() - 1)).children[0].toString();
        String a = Node.getTree(input1.a.get(input1.a.size() - 1)).children[1].toString();
        res.header = new Header(input1.header);
        for (String x : input1.a) {
            res.a.add(x);
        }
        for (String x : input2.a) {
            res.a.add(x);
        }
        ArrayList<String> zz = aOrNotA(new NodeVariable(p));
        for (String x : zz) {
            res.a.add(x);
        }
        res.a.add(f("(A->B)->(!A->B)->(A|!A)->B", p, a));
        res.a.add(f("(!A->B)->(A|!A)->B", p, a));
        res.a.add(f("(A|!A)->B", p, a));
        res.a.add(f("B", p, a));

        return simplify(res);
    }
}
