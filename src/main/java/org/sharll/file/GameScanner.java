package org.sharll.file;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameScanner {
    public static List<String> scanMc(String McPath) {
        List<String> ret = new ArrayList<>();
        String[] ls = new File(Paths.get(McPath, "versions").toString()).list();
        if (ls != null) {
            for (String i : ls) {
                if (new File(Paths.get(McPath, "versions", i, i + ".json").toString()).exists()) {
                    if (new File(Paths.get(McPath, "versions", i, i + ".jar").toString()).exists()) {
                        ret.add(i);
                    }
                }
            }
        }
        return ret;
    }

    public static List<String> scanJava() {
        List<String> JavaList = new ArrayList<>();
        String Check = "";
        String[] possiblePath = {"Java", "BellSoft", "AdoptOpenJDK", "Zulu", "Microsoft", "Eclipse Foundation", "Semeru"};

        for (String i : System.getenv("PATH").split(";")) {
            Check = Paths.get(i, "java.exe").toString();
            if (new File(Check).exists()) {
                JavaList.add(Check);
            }
            Check = Paths.get(i, "bin", "java.exe").toString();
            if (new File(Paths.get(Check).toString()).exists()) {
                JavaList.add(i);
            }
        }

        for (String i : possiblePath) {
            Check = Paths.get(System.getenv("ProgramFiles"), i).toString();
            checkone(JavaList, Check);

            Check = Paths.get(System.getenv("ProgramFiles(x86)"), i).toString();
            checkone(JavaList, Check);

        }

        return JavaList;
    }

    private static void checkone(List<String> javaList, String check) {
        if (new File(check).list() != null) {
            for (String j : Objects.requireNonNull(new File(check).list())) {
                if (new File(Paths.get(check, j, "bin", "java.exe").toString()).exists()) {
                    javaList.add(Paths.get(check, j, "bin", "java.exe").toString());
                }
            }
        }
    }
}
