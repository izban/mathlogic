package Task4.Util.Tree;

/**
 * Created by izban on 25.05.2016.
 */
public abstract class NodeQuantor extends Node {
    public NodeQuantor(Node var, Node ch) {
        children = new Node[2];
        children[0] = var;
        children[1] = ch;
        makeHash();
    }

    @Override
    public String toString() {
        return type().toString() + children[0].toString() + children[1].toString();
    }
}
