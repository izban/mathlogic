package Task4.Util.Tree;

/**
 * Created by izban on 21.05.2016.
 */
public class NodeOr extends NodeBinary {
    public NodeOr(Node l, Node r) {
        super(l, r);
    }

    @Override
    public NodeType type() {
        return NodeType.OR;
    }
}
