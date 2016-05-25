package Task4.Util.Tree;

/**
 * Created by izban on 21.05.2016.
 */
public class NodeNot extends NodeUnary {
    public NodeNot(Node l) {
        super(l);
    }

    @Override
    public NodeType type() {
        return NodeType.NOT;
    }
}
