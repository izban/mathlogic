package Util.Constants;

import javafx.util.Pair;

import static Util.Constants.StringConstants.*;

/**
 * Created by izban on 23.05.2016.
 */
public class Rules {
    public static String axiom(int id) {
        return axiomes + id;
    }

    public static String hypothesis() {
        return hypothesis;
    }

    public static String notProved() {
        return notProved;
    }

    public static Pair<Integer, Integer> unMP(String s) {
        int a = Integer.parseInt(s.substring(5, s.indexOf(',')));
        int b = Integer.parseInt(s.substring(s.indexOf(',') + 2));
        return new Pair<>(a, b);
    }

    public static String MP(int i, int j) {
        return MP + i + ", " + j;
    }
}
