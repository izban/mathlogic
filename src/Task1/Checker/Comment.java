package Task1.Checker;

/**
 * Created by izban on 21.05.2016.
 */
public class Comment {
    public int stringID;
    public String expr;
    public String annotation;

    Comment() {}
    public Comment(int stringID, String expr, String annotation) {
        this.stringID = stringID;
        this.expr = expr;
        this.annotation = annotation;
    }

    @Override
    public String toString() {
        return "(" + stringID + ")" +
                " " + expr + " " +
                "(" + annotation + ")";
    }
}
