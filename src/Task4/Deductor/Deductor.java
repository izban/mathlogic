package Task4.Deductor;

import Task4.Checker.*;
import Task4.Util.Constants.Rules;
import Task4.Util.Parser.Parser;
import Task4.Util.Tree.Node;
import Task4.Util.Tree.NodeImpl;
import javafx.util.Pair;

import java.util.HashSet;

import static Task4.Util.Constants.StringConstants.MP;
import static Task4.Util.Constants.StringConstants.AXIOMES;
import static Task4.Util.Simplificator.Simplificator.simplify;

/**
 * Created by izban on 23.05.2016.
 */
public class Deductor {
    public Input deduce(Input input) {
        Output proved = new Checker().check(input);

        Input res = new Input();
        res.header = new Header(input.header);
        Node a = new Parser().parseTree(res.header.assumptions.get(res.header.assumptions.size() - 1));
        res.header.assumptions.remove(res.header.assumptions.size() - 1);
        res.header.toProve = new NodeImpl(a, Node.getTree(res.header.toProve)).toString();
        HashSet<Node> was = new HashSet<>();
        for (int i = 0; i < proved.a.size(); i++) {
            Node n = Node.getTree(proved.a.get(i).expr);
            if (n.equals(a)) {
                Theorems.addAToA(was, res, a);
            } else
            if (proved.a.get(i).annotation.startsWith(Rules.hypothesis()) || proved.a.get(i).annotation.startsWith(AXIOMES)) {
                Theorems.addDeductHypothesis(was, res, a, n);
            } else
            if (proved.a.get(i).annotation.startsWith(MP)) {
                Pair<Integer, Integer> o = Rules.unMP(proved.a.get(i).annotation);
                Node hj = Node.getTree(proved.a.get(o.getKey() - 1).expr);
                Theorems.addDeductImpl(was, res, a, hj, n);
            } else {
                System.err.println(proved.a.get(i).annotation);
                throw new AssertionError();
            }
        }

        return simplify(res);
    }
}
