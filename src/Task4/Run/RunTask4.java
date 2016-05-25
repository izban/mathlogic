package Task4.Run;

import Task4.Checker.Checker;
import Task4.Checker.Input;
import Task4.Checker.Output;
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
            //if (!curFile.equals("tests\\Task4\\incorrect2.in")) return "";
            Input input = new Input(a);
            Output output = new Checker().check(input);
            System.err.println(curFile + ": " + output.ok);
            return output.toString();
        });
    }
}
