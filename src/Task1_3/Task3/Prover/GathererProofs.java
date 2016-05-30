package Task1_3.Task3.Prover;

import Task1_3.Task1.Checker.Checker;
import Task1_3.Task1.Checker.Header;
import Task1_3.Task1.Checker.Input;
import Task1_3.Util.Files.FileWalker;
import Task1_3.Util.Tree.Node;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by izban on 23.05.2016.
 */
class GathererProofs {
    private static final HashMap<String, ArrayList<String>> proofs = new HashMap<>();

    static public ArrayList<String> getProof(String theorem, String... formulas) {
        ArrayList<String> res = new ArrayList<>();
        assert proofs.containsKey(theorem);
        for (String s : proofs.get(theorem)) {
            StringBuilder cur = new StringBuilder();
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c >= 'A' && c <= 'Z') {
                    cur.append("(").append(formulas[c - 'A']).append(")");
                } else {
                    cur.append(c);
                }
            }
            res.add(Node.getTree(cur.toString()).toString());
        }
        return res;
    }

    static {
        try {
            FileWalker.fileWalk("res", a -> {
                for (int i = 0; i < a.size(); i++) {
                    String name = a.get(i++);
                    proofs.put(name, new ArrayList<>());
                    while (i < a.size()) {
                        if (a.get(i).equals("---")) {
                            break;
                        }
                        proofs.get(name).add(a.get(i++));
                    }
                    Input input = new Input();
                    if (name.contains("|-")) {
                        input.header = new Header(name);
                    }
                    input.a = proofs.get(name);
                    if (!new Checker().check(input).ok) throw new AssertionError();
                }
                return null;
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
