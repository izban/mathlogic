package Task4.Util.Tree;

/**
 * Created by izban on 25.05.2016.
 */
public class NodeEqual extends Node {
    public NodeEqual(Node left, Node right) {
        children = new Node[2];
        children[0] = left;
        children[1] = right;
        makeHash();
    }

    @Override
    public NodeType type() {
        return NodeType.PREDICAT_EQUAL;
    }

    @Override
    public String toString() {
        return children[0].toString() + "=" + children[1].toString();
    }
}
