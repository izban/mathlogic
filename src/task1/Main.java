package Task1;

import Task1.Checker.Checker;
import Task1.Checker.Header;
import Task1.Checker.Input;
import Task1.Checker.Output;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

/**
 * Created by Asus-dns on 21.05.2016.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("tests");
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (!file.toString().endsWith(".in")) {
                    return FileVisitResult.CONTINUE;
                }
                String f = file.toString().replaceAll(".in", "");
                BufferedReader br = new BufferedReader(new FileReader(new File(f + ".in")));
                Input input = new Input();
                ArrayList<String> a = new ArrayList<>();
                br.lines().forEach(input.a::add);
                br.close();
                if (input.a.get(0).contains("|-")) {
                    input.header = new Header(input.a.get(0));
                    input.a.remove(0);
                }
                Output output = new Checker().check(input);
                BufferedWriter bw = new BufferedWriter(new FileWriter(new File(f + ".out")));
                bw.write(output.toString());
                bw.close();
                System.err.println(f + ".in: " + output.ok);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
