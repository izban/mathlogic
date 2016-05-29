package Task1_3.Task2;

import Task1_3.Task1.Checker.Checker;
import Task1_3.Task1.Checker.Input;
import Task1_3.Task1.Checker.Output;
import Task1_3.Task2.Deductor.Deductor;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by izban on 29.05.2016.
 */
public class RunTask2SingleTest {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList<String> a = new ArrayList<>();
        while (in.hasNextLine()) {
            a.add(in.nextLine());
        }
        Input input = new Input(a);
        Input output = new Deductor().deduce(input);
        Output proved = new Checker().check(output);
        if (!proved.ok) {
            throw new AssertionError();
        }
        System.out.println(output.toString());
    }
}
