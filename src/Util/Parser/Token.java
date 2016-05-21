package Util.Parser;

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
    TOURNIQUET;

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
            default:
                return VARIABLE; // not best choice, but why not
        }
    }
}
