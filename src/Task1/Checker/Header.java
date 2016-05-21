package Task1.Checker;

import java.util.ArrayList;

/**
 * Created by izban on 21.05.2016.
 */
class Header {
    ArrayList<String> assumptions;
    private String toProve;

    Header() {}
    Header(String s) {
        s = s + ",";
        String cur = "";
        assumptions = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isWhitespace(c)) {
                continue;
            }
            if (c == ',' || c == '|' && s.charAt(i + 1) == '-') {
                if (i + 1 == s.length()) {
                    toProve = s;
                } else {
                    assumptions.add(s);
                }
            }
        }
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
