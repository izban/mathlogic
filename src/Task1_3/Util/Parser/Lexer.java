package Task1_3.Util.Parser;

/**
 * Created by izban on 21.05.2016.
 */
class Lexer {
    private String s;
    private int it;

    Lexer(String s) {
        this.s = s.replace(" ", "");
        it = 0;
        nextToken();
    }

    private String cur;
    String curToken() {
        return cur;
    }

    void nextToken() {
        cur = next();
    }

    private String next() {
        if (it == s.length()) {
            it++;
            return "";
        }
        if (it > s.length()) {
            throw new AssertionError();
        }
        switch (s.charAt(it)) {
            case '-':
                assert s.charAt(it + 1) == '>';
                it += 2;
                return "->";
            case '|':
                if (it + 1 < s.length() && s.charAt(it + 1) == '-') {
                    it += 2;
                    return "|-";
                }
                it++;
                return "|";
            case '&':
                it++;
                return "&";
            case '!':
                it++;
                return "!";
            case ',':
                it++;
                return ",";
            case '(':
                it++;
                return "(";
            case ')':
                it++;
                return ")";
            default:
                if (s.charAt(it) >= 'A' && s.charAt(it) <= 'Z') {
                    StringBuilder res = new StringBuilder();
                    res.append(s.charAt(it++));
                    while (it < s.length() && Character.isDigit(s.charAt(it))) {
                        res.append(s.charAt(it++));
                    }
                    return res.toString();
                }
                throw new AssertionError();
        }
    }
}
