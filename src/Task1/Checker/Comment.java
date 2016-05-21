package Task1.Checker;

/**
 * Created by izban on 21.05.2016.
 */
class Comment {
    private int stringID;
    private String expr;
    private String annotation;

    Comment() {}
    Comment(int stringID, String expr, String annotation) {
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
