package Task4.Util.Parser;

/**
 * Created by izban on 21.05.2016.
 */
enum Token {
    IMPLICATION,
    OR,
    AND,
    NOT,
    LEFT_BRACKET,
    RIGHT_BRACKET,
    VARIABLE,
    END,
    COMMA,
    TOURNIQUET,
    FORALL,
    EXIST,
    PREDICATE,
    EQUAL,
    ADD,
    MULTIPLY,
    ZERO,
    APOSTROPHE;

    public static Token type(String s) {
        switch (s) {
            case "->":
                return IMPLICATION;
            case "|":
                return OR;
            case "&":
                return AND;
            case "!":
                return NOT;
            case "(":
                return LEFT_BRACKET;
            case ")":
                return RIGHT_BRACKET;
            case "":
                return END;
            case ",":
                return COMMA;
            case "|-":
                return TOURNIQUET;
            case "@":
                return FORALL;
            case "?":
                return EXIST;
            case "=":
                return EQUAL;
            case "+":
                return ADD;
            case "*":
                return MULTIPLY;
            case "0":
                return ZERO;
            case "'":
                return APOSTROPHE;
            default:
                if (!s.isEmpty()) {
                    if (Character.isLetter(s.charAt(0))) {
                        boolean ok = true;
                        for (int i = 1; i < s.length(); i++) {
                            ok &= Character.isDigit(s.charAt(i));
                        }
                        if (ok) {
                            if (Character.isLowerCase(s.charAt(0))) {
                                return VARIABLE;
                            } else {
                                return PREDICATE;
                            }
                        }
                    }
                }
                throw new AssertionError("Unexpected token");
        }
    }
}
