package Task1_3.Task1.Checker;

import java.util.ArrayList;

/**
 * Created by izban on 21.05.2016.
 */
public class Input {
    public Header header;
    public ArrayList<String> a = new ArrayList<>();

    public Input() {}

    public Input(ArrayList<String> a) {
        if (a.get(0).contains("|-")) {
            header = new Header(a.get(0));
            a.remove(0);
        }
        this.a = a;
    }

    Input(Header header, ArrayList<String> a) {
        this.header = header;
        this.a = a;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        if (header != null) {
            res.append(header.toString()).append("\r\n");
        }
        for (String anA : a) {
            res.append(anA).append("\r\n");
        }
        return res.toString();
    }
}
