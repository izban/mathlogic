package Run.RunSingleTest;

import Task4.Checker.Checker;
import Task4.Checker.Input;
import Task4.Checker.Output;
import Task4.Deductor.Deductor;

import java.util.ArrayList;
import java.util.Scanner;

import static Task4.Util.Files.FileWalker.curFile;

/**
 * Created by izban on 29.05.2016.
 */
public class RunTask4SingleTest {
    public static void main(String[] args) {
        ArrayList<String> a = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            a.add(in.nextLine());
        }

        Input input = new Input(a);
        Output proved = new Checker().check(input, true);
        if (!proved.ok) {
            System.out.println("Ошибка: " + proved.firstError());
            return;
        }
        Input output = new Deductor().deduce(input);
        Output nproved = new Checker().check(output, false);
        if (!nproved.ok) {
            throw new AssertionError();
        }
        System.out.println(output.toString());
    }
}
