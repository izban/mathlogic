package Task4.Util.Tree;

/**
 * Created by izban on 25.05.2016.
 */
public class NodeInc extends Node {
    public NodeInc(Node ch) {
        children = new Node[1];
        children[0] = ch;
        makeHash();
    }

    @Override
    public NodeType type() {
        return NodeType.INC;
    }

    @Override
    public String toString() {
        return children[0].toString() + type().toString();
    }
}
