package Task4.Util.Constants;

import javafx.util.Pair;

import static Task4.Util.Constants.StringConstants.*;

/**
 * Created by izban on 23.05.2016.
 */
public class Rules {
    public static String axiom(String id) {
        return AXIOMES + id;
    }

    public static String hypothesis() {
        return HYPOTHESIS;
    }

    public static String notProved() {
        return NOTPROVED;
    }

    public static Pair<Integer, Integer> unMP(String s) {
        int a = Integer.parseInt(s.substring(5, s.indexOf(',')));
        int b = Integer.parseInt(s.substring(s.indexOf(',') + 2));
        return new Pair<>(a, b);
    }

    public static String MP(int i, int j) {
        return MP + i + ", " + j;
    }

    public static String ruleForall(int i) {
        return FORALL + " " + i;
    }

    public static String ruleExist(int i) {
        return EXIST + " " + i;
    }
}
