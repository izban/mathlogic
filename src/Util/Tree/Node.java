package Util.Tree;

import Util.Hash.Hash;
import Util.Parser.Parser;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.ArrayList;

/**
 * Created by izban on 21.05.2016.
 */
public abstract class Node {
    public Node children[];
    public abstract NodeType type();
    private int h;

    static public Node getTree(String s) {
        return new Parser().parseTree(s);
    }

    @Override
    public int hashCode() {
        return hash();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Node && ((Node)obj).hash() == hash();
    }


    @Override
    public abstract String toString();

    protected Integer hashThis() {
        return type().ordinal();
    }

    void makeHash() {
        ArrayList<Integer> res = new ArrayList<>();
        res.add(hashThis());
        for (Node aChildren : children) {
            res.add(aChildren.hash());
        }
        h = Hash.getHash(res);
    }

    private int hash() {
        return h;
    }

    public abstract boolean calcValue(boolean... a);
}
