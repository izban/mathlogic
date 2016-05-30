package Task8;

import Task1_3.Util.Files.FileWalker;
import Task8.Ordinal.OrdinalChecker;

import java.io.IOException;

import static Task1_3.Util.Files.FileWalker.curFile;

/**
 * Created by izban on 30.05.2016.
 */
public class RunTask8 {
    public static void main(String[] args) throws IOException {
        FileWalker.fileWalk("tests/Task8", a -> {
            String res = new OrdinalChecker().check(a.get(0));
            System.err.println(curFile + ": " + res);
            return res;
        });
    }
}
