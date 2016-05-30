package Run.RunSingleTest;

import Task1_3.Task1.Checker.Checker;
import Task1_3.Task1.Checker.Input;
import Task1_3.Task1.Checker.Output;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by izban on 29.05.2016.
 */
public class RunTask1SingleTest {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList<String> a = new ArrayList<>();
        while (in.hasNextLine()) {
            a.add(in.nextLine());
        }
        Input input = new Input(a);
        Output output = new Checker().check(input);
        System.out.println(output.toString());
    }
}
