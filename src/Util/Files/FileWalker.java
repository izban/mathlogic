package Util.Files;

import Task1.Checker.Checker;
import Task1.Checker.Input;
import Task1.Checker.Output;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.function.Function;

/**
 * Created by izban on 23.05.2016.
 */
public class FileWalker {
    public static void fileWalk(String spath, Function<ArrayList<String>, String> action) throws IOException {
        Path path = Paths.get(spath);
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (!file.toString().endsWith(".in")) {
                    return FileVisitResult.CONTINUE;
                }
                String f = file.toString().replaceAll(".in", "");
                BufferedReader br = new BufferedReader(new FileReader(new File(f + ".in")));
                ArrayList<String> a = new ArrayList<>();
                br.lines().forEach(a::add);
                br.close();
                BufferedWriter bw = new BufferedWriter(new FileWriter(new File(f + ".out")));
                System.err.print(f + ".in: ");
                String res = action.apply(a);
                bw.write(res);
                bw.close();
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
