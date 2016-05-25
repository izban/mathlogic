package Task4.Run;

import Task4.Checker.Checker;
import Task4.Checker.Input;
import Task4.Checker.Output;
import Task4.Deductor.Deductor;
import Task4.Util.Files.FileWalker;
import Task4.Util.Tree.Node;

import java.io.IOException;

import static Task4.Util.Files.FileWalker.curFile;
import static Task4.Util.Files.FileWalker.fileWalk;

/**
 * Created by izban on 25.05.2016.
 */
public class RunTask4 {
    public static void main(String[] args) throws IOException {
        FileWalker.fileWalk("tests/Task4", a -> {
            if (curFile.equals("tests\\Task4\\correct11.in")) return "";
            Input input = new Input(a);
            Output proved = new Checker().check(input, true);
            if (!proved.ok) {
                System.err.println(curFile + ": " + "wrong proof");
                return proved.firstError();
            }
            Input output = new Deductor().deduce(input);
            Output nproved = new Checker().check(output, false);
            System.err.println(curFile + ": " + nproved.ok);
            return nproved.toString();
        });
    }
}
