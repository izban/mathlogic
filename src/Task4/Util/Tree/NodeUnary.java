package Task4.Util.Tree;

/**
 * Created by izban on 21.05.2016.
 */
public abstract class NodeUnary extends Node {
    NodeUnary() {}

    NodeUnary(Node l) {
        children = new Node[1];
        children[0] = l;
        makeHash();
    }

    @Override
    public String toString() {
        return type().toString() + "(" + children[0].toString() + ")";
    }
}
