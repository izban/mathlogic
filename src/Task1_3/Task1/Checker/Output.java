package Task1_3.Task1.Checker;

import java.util.ArrayList;

/**
 * Created by izban on 21.05.2016.
 */
public class Output {
    private final Header header = null;
    public final ArrayList<Comment> a = new ArrayList<>();
    public boolean ok = true;

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        if (header != null) {
            str.append(header.toString());
        }
        for (Comment anA : a) {
            str.append(anA.toString()).append("\r\n");
        }
        return str.toString();
    }
}
