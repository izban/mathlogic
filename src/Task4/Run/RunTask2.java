package Task4.Run;

import Task4.Checker.Checker;
import Task4.Checker.Input;
import Task4.Checker.Output;
import Task4.Deductor.Deductor;
import Task4.Util.Files.FileWalker;

import java.io.IOException;

import static Task4.Util.Files.FileWalker.curFile;

/**
 * Created by izban on 23.05.2016.
 */
public class RunTask2 {
    public static void main(String[] args) throws IOException {
        FileWalker.fileWalk("tests/Task2", a -> {
            Input input = new Input(a);
            Input output = new Deductor().deduce(input);
            Output proved = new Checker().check(output, true);
            System.err.println(curFile + ": " + proved.ok);
            StringBuilder res = new StringBuilder();
            res.append(output.toString());
            return res.toString();
        });
    }
}
