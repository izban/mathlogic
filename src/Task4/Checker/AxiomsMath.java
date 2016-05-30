package Task4.Checker;

import Task4.Util.Parser.Parser;
import Task4.Util.Tree.Node;

import java.util.ArrayList;

/**
 * Created by izban on 25.05.2016.
 */
class AxiomsMath {
    private static final ArrayList<String> axioms = new ArrayList<>();
    static final ArrayList<Node> trees = new ArrayList<>();

    static {
        axioms.add("a=b->a'=b'");
        axioms.add("a=b->a=c->b=c");
        axioms.add("a'=b'->a=b");
        axioms.add("!a'=0");
        axioms.add("a+b'=(a+b)'");
        axioms.add("a+0=a");
        axioms.add("a*0=0");
        axioms.add("a*b'=a*b+a");

        for (String axiom : axioms) {
            trees.add(new Parser().parseTree(axiom));
        }
    }

}
