package Task2;

import Task1.Checker.Checker;
import Task1.Checker.Input;
import Task1.Checker.Output;
import Task2.Deductor.Deductor;
import Util.Files.FileWalker;

import java.io.IOException;

/**
 * Created by izban on 23.05.2016.
 */
public class RunTask2 {
    public static void main(String[] args) throws IOException {
        FileWalker.fileWalk("tests/Task2", a -> {
            Input input = new Input(a);
            Input output = new Deductor().deduce(input);
            Output proved = new Checker().check(output);
            System.err.println(proved.ok);
            StringBuilder res = new StringBuilder();
            res.append(output.toString());
            return res.toString();
        });
    }
}
