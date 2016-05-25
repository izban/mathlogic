package Task4.Util.Tree;

/**
 * Created by izban on 25.05.2016.
 */
public class NodeMultiply extends NodeBinary {
    public NodeMultiply(Node res, Node node) {
        super(res, node);
    }

    @Override
    public NodeType type() {
        return NodeType.MULTIPLY;
    }
}
