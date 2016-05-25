package Task4.Util.Formatter;

import Task4.Util.Tree.Node;

/**
 * Created by izban on 23.05.2016.
 */
public class ExpressionFormatter {
    public static String format(String s, Node... nodes) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                res.append("(").append(nodes[c - 'A'].toString()).append(")");
            } else {
                res.append(c);
            }
        }
        return Node.getTree(res.toString()).toString();
    }

    public static String format(String s, String... nodes) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                res.append("(").append(nodes[c - 'A']).append(")");
            } else {
                res.append(c);
            }
        }
        return Node.getTree(res.toString()).toString();
    }
}
