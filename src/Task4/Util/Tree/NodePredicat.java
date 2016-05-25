package Task4.Util.Tree;

/**
 * Created by izban on 25.05.2016.
 */
public class NodePredicat extends Node {
    public NodePredicat(String s, Node... other) {
        children = new Node[other.length + 1];
        children[0] = new NodeFunction(s);
        for (int i = 0; i < other.length; i++) {
            children[i + 1] = other[i];
        }
        makeHash();
    }

    @Override
    public NodeType type() {
        return NodeType.PREDICAT_NAMED;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(children[0]);
        if (children.length > 1) {
            res.append("(");
            for (int i = 1; i < children.length; i++) {
                res.append(children[i].toString());
                if (i + 1 < children.length) {
                    res.append(",");
                }
            }
            res.append(")");
        }
        return res.toString();
    }
}
