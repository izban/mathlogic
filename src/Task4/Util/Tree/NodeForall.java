package Task4.Util.Tree;

/**
 * Created by izban on 25.05.2016.
 */
public class NodeForall extends NodeQuantor {
    public NodeForall(Node var, Node ch) {
        super(var, ch);
    }

    @Override
    public NodeType type() {
        return NodeType.FORALL;
    }
}
