package Run.RunSingleTest;

import Task8.Ordinal.OrdinalChecker;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by izban on 29.05.2016.
 */
public class RunTask8SingleTest {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList<String> a = new ArrayList<>();
        while (in.hasNextLine()) {
            a.add(in.nextLine());
        }
        String res = new OrdinalChecker().check(a.get(0));
        System.out.println(res);
    }
}
