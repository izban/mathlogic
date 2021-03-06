package Task1_3.Task1.Checker;

import java.util.ArrayList;

/**
 * Created by izban on 21.05.2016.
 */
public class Header {
    public ArrayList<String> assumptions = new ArrayList<>();
    public String toProve = "";

    public Header() {}
    public Header(String s) {
        s = s + ",";
        StringBuilder cur = new StringBuilder();
        assumptions = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isWhitespace(c)) {
                continue;
            }
            if (c == ',' || c == '|' && s.charAt(i + 1) == '-') {
                if (i + 1 == s.length()) {
                    toProve = cur.toString();
                } else {
                    assumptions.add(cur.toString());
                }
                cur = new StringBuilder();
                if (c == '|') i++;
            } else {
                cur.append(c);
            }
        }
    }

    public Header(Header header) {
        assumptions = new ArrayList<>(header.assumptions);
        toProve = header.toProve;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < assumptions.size(); i++) {
            str.append(assumptions.get(i));
            if (i + 1 < assumptions.size()) {
                str.append(",");
            }
        }
        str.append("|-");
        str.append(toProve);
        return str.toString();
    }
}
