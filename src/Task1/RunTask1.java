package Task1;

import Task1.Checker.Checker;
import Task1.Checker.Header;
import Task1.Checker.Input;
import Task1.Checker.Output;
import Util.Files.FileWalker;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

import static Util.Files.FileWalker.curFile;

/**
 * Created by izban on 21.05.2016.
 */
public class RunTask1 {
    public static void main(String[] args) throws IOException {
        FileWalker.fileWalk("tests/Task1", a -> {
            Input input = new Input(a);
            Output output = new Checker().check(input);
            System.err.println(curFile + ": " + output.ok);
            return output.toString();
        });
    }
}
