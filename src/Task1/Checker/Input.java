package Task1.Checker;

import java.util.ArrayList;

/**
 * Created by izban on 21.05.2016.
 */
public class Input {
    Header header;
    public ArrayList<String> a = new ArrayList<>();

    public Input() {}

    Input(Header header, ArrayList<String> a) {
        this.header = header;
        this.a = a;
    }
}
