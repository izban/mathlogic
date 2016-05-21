package Util.Tree;

/**
 * Created by izban on 21.05.2016.
 */
public class NodeImpl extends NodeBinary {
    public NodeImpl(Node l, Node r) {
        super(l, r);
    }

    @Override
    public NodeType type() {
        return NodeType.IMPL;
    }
}
