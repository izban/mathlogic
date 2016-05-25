package Task1_3.Util.Tree;

/**
 * Created by izban on 21.05.2016.
 */
public abstract class NodeBinary extends Node {
    public NodeBinary() {}

    NodeBinary(Node l, Node r) {
        children = new Node[2];
        children[0] = l;
        children[1] = r;
        makeHash();
    }

    @Override
    public String toString() {
        return "(" + children[0].toString() + ")" + type().toString() + "(" + children[1].toString() + ")";
    }

    protected abstract boolean f(boolean a, boolean b);

    public boolean calcValue(boolean... a) {
        return f(a[0], a[1]);
    }
}
