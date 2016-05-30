package Run.RunSingleTest;

import Task1_3.Task1.Checker.Checker;
import Task1_3.Task1.Checker.Input;
import Task1_3.Task1.Checker.Output;
import Task1_3.Task3.Prover.Prover;

import java.util.ArrayList;
import java.util.Scanner;

import static Task1_3.Util.Constants.StringConstants.EXPRISFALSE;
import static Task1_3.Util.Files.FileWalker.curFile;

/**
 * Created by izban on 29.05.2016.
 */
public class RunTask3SingleTest {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList<String> a = new ArrayList<>();
        while (in.hasNextLine()) {
            a.add(in.nextLine());
        }
        Input input = new Input(a);
        Input output = new Prover().prove(a.get(0));
        if (output.a.size() == 1 && output.a.get(0).startsWith(EXPRISFALSE)) {
            System.out.println(output.toString());
            return;
        }
        if (!output.header.assumptions.isEmpty()) {
            throw new AssertionError();
        }
        output.header = null;
        Output proved = new Checker().check(output);
        if (!proved.ok) {
            throw new AssertionError();
        }
        System.out.println(output.toString());
    }
}
