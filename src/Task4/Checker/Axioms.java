package Task4.Checker;

import Task4.Util.Parser.Parser;
import Task4.Util.Tree.Node;

import java.util.ArrayList;

/**
 * Created by izban on 21.05.2016.
 */
class Axioms {
    private static ArrayList<String> axioms = new ArrayList<>();
    static ArrayList<Node> trees = new ArrayList<>();

    static {
        axioms.add("A->B->A");
        axioms.add("(A->B)->(A->B->C)->(A->C)");
        axioms.add("A->B->A&B");
        axioms.add("A&B->A");
        axioms.add("A&B->B");
        axioms.add("A->A|B");
        axioms.add("B->A|B");
        axioms.add("(A->C)->(B->C)->(A|B->C)");
        axioms.add("(A->B)->(A->!B)->!A");
        axioms.add("!!A->A");

        for (String axiom : axioms) {
            trees.add(new Parser().parseTree(axiom));
        }
    }
}
