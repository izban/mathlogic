package Task5;

import Task1_3.Task1.Checker.Checker;
import Task1_3.Task1.Checker.Input;
import Task1_3.Task1.Checker.Output;
import Task5.Heyting.Heyting;
import Task5.Heyting.HeytingSolver;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by izban on 29.05.2016.
 */
public class RunTask5SingleTest {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList<String> a = new ArrayList<>();
        while (in.hasNextLine()) {
            a.add(in.nextLine());
        }
        Heyting output = new HeytingSolver().solve(a.get(0));
        System.out.println(output.toString());
    }
}
