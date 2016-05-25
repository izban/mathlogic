package Task4.Util.Tree;

/**
 * Created by izban on 25.05.2016.
 */
public class NodeZero extends Node {
    public NodeZero() {
        children = new Node[0];
        makeHash();
    }

    @Override
    public NodeType type() {
        return NodeType.ZERO;
    }

    @Override
    public String toString() {
        return type().toString();
    }
}
