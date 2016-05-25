package Task4.Util.Tree;

/**
 * Created by izban on 25.05.2016.
 */
public class NodeAdd extends NodeBinary {
    public NodeAdd(Node res, Node node) {
        super(res, node);
    }

    @Override
    public NodeType type() {
        return NodeType.ADD;
    }
}
