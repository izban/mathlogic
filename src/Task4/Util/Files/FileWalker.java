package Task4.Util.Files;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.function.Function;

/**
 * Created by izban on 23.05.2016.
 */
public class FileWalker {
    public static String curFile;

    public static void fileWalk(String spath, Function<ArrayList<String>, String> action) throws IOException {
        Path path = Paths.get(spath);
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (!file.toString().endsWith(".in")) {
                    return FileVisitResult.CONTINUE;
                }
                String f = file.toString();
                f = f.substring(0, f.length() - 3);
                BufferedReader br = new BufferedReader(new FileReader(new File(f + ".in")));
                ArrayList<String> a = new ArrayList<>();
                br.lines().forEach(a::add);
                br.close();
                String was = curFile;
                curFile = f + ".in";
                String res = action.apply(a);
                curFile = was;
                if (res == null) {
                    return FileVisitResult.CONTINUE;
                }
                BufferedWriter bw = new BufferedWriter(new FileWriter(new File(f + ".out")));
                bw.write(res);
                bw.close();
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
