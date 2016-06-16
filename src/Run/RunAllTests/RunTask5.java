package Run.RunAllTests;

import Task1_3.Task1.Checker.Input;
import Task1_3.Util.Files.FileWalker;
import Task1_3.Util.Tree.Node;
import Task5.Heyting.Heyting;
import Task5.Heyting.HeytingSolver;
import Task5.Kripke.KripkeModel;
import Task5.Kripke.KripkeModelBuilder;

import java.io.IOException;
import java.io.ObjectInput;

import static Task1_3.Util.Files.FileWalker.curFile;

/**
 * Created by izban on 29.05.2016.
 */
public class RunTask5 {
    public static void main(String[] args) throws IOException {
        FileWalker.fileWalk("tests/Task5", a -> {
            Input input = new Input(a);
            //KripkeModel output = new KripkeModelBuilder().getCountertest(Node.getTree(input.a.get(0)));
            Object output = new HeytingSolver().solve(input.a.get(0));
            if (output == null) {
                output = new KripkeModelBuilder().getCountertest(Node.getTree(input.a.get(0)));
            }
            System.err.println(curFile + ": " + input.a.get(0));
            String res = (output == null ? "Формула общезначима (наверное)" : output.toString());
            System.err.println(res);
            return res;
        });
    }
}
