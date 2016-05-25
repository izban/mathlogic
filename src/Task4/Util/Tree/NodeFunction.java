package Task4.Util.Tree;

/**
 * Created by izban on 25.05.2016.
 */
public class NodeFunction extends Node {
    private String name;

    public NodeFunction(String s) {
        this.name = s;
        children = new Node[0];
        makeHash();
    }

    @Override
    public NodeType type() {
        return NodeType.FUNCTION;
    }

    @Override
    protected Integer hashThis() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}