package Task5;

import Task1_3.Task1.Checker.Input;
import Task1_3.Util.Files.FileWalker;
import Task5.Heyting.Heyting;
import Task5.Heyting.HeytingSolver;
import Task5.Kripke.KripkeModel;

import java.io.IOException;

import static Task1_3.Util.Files.FileWalker.curFile;

/**
 * Created by izban on 29.05.2016.
 */
public class RunTask5 {
    public static void main(String[] args) throws IOException {
        FileWalker.fileWalk("tests/Task5", a -> {
            Input input = new Input(a);
            Heyting output = new HeytingSolver().solve(input.a.get(0));
            System.err.println(curFile + ": ");
            String res = (output == null ? "Формула общезначима (наверное)" : output.toString());
            System.err.println(res);
            return res;
        });
    }
}
