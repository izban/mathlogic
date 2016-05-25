package Task4.Checker;

import java.util.ArrayList;

import static Task4.Util.Constants.StringConstants.NOTPROVED;

/**
 * Created by izban on 21.05.2016.
 */
public class Output {
    public Header header = null;
    public ArrayList<Comment> a = new ArrayList<>();
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

    public String firstError() {
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i).annotation.startsWith(NOTPROVED)) {
                return a.get(i).toString();
            }
        }
        throw new AssertionError();
    }
}
