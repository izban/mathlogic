package Task3;

import Task1.Checker.Checker;
import Task1.Checker.Input;
import Task1.Checker.Output;
import Task2.Deductor.Deductor;
import Task3.Prover.Prover;
import Util.Files.FileWalker;
import Util.Tree.Node;

import java.io.IOException;

import static Util.Constants.StringConstants.EXPRISFALSE;
import static Util.Files.FileWalker.curFile;

/**
 * Created by izban on 23.05.2016.
 */
public class RunTask3 {
    public static void main(String[] args) throws IOException {
        FileWalker.fileWalk("tests/Task3", a -> {
            Input output = new Prover().prove(a.get(0));
            if (output.a.size() == 1 && output.a.get(0).startsWith(EXPRISFALSE)) {
                System.err.println(curFile + ": " + true);
                return output.toString();
            }
            assert output.header.assumptions.isEmpty();
            output.header = null;
            Output proved = new Checker().check(output);
            System.err.println(curFile + ": " + proved.ok);
            StringBuilder res = new StringBuilder();
            res.append(output.toString());
            return res.toString();
        });
    }
}
