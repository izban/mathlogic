package Task4.Util.Parser;

import java.util.ArrayList;

/**
 * Created by izban on 21.05.2016.
 */
class Lexer {
    private final String s;
    private int it;
    private final ArrayList<Character> pair = new ArrayList<>();

    Lexer(String s) {
        this.s = s.replace(" ", "");
        it = 0;
        ArrayList<Integer> stack = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            pair.add((char)(0));
            if (s.charAt(i) == '(') {
                stack.add(i);
            }
            if (s.charAt(i) == ')') {
                if (stack.isEmpty()) {
                    //System.err.println(s);
                    throw new AssertionError();
                }
                int j = i + 1;
                if (j < s.length()) {
                    pair.set(stack.get(stack.size() - 1), s.charAt(j));
                }
                stack.remove(stack.size() - 1);
            }
        }
        nextToken();
    }

    private String cur;
    String curToken() {
        return cur;
    }

    Token curType() {
        return Token.type(cur);
    }

    void nextToken() {
        cur = next();
    }

    char afterPairCurBracket() {
        return pair.get(it - 1);
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
            case '@':
                it++;
                return "@";
            case '?':
                it++;
                return "?";
            case '=':
                it++;
                return "=";
            case '+':
                it++;
                return "+";
            case '*':
                it++;
                return "*";
            case '0':
                it++;
                return "0";
            case '\'':
                it++;
                return "'";
            default:
                if (Character.isLetter(s.charAt(it))) {
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
