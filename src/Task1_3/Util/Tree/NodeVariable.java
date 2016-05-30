package Task1_3.Util.Tree;

/**
 * Created by izban on 21.05.2016.
 */
public class NodeVariable extends Node {
    private final String name;

    public NodeVariable(String s) {
        this.name = s;
        children = new Node[0];
        makeHash();
    }

    @Override
    public NodeType type() {
        return NodeType.VARIABLE;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    protected Integer hashThis() {
        return name.hashCode();
    }

    public boolean calcValue(boolean... a) {
        throw new IllegalStateException();
    }
}
