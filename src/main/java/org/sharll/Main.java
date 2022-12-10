package org.sharll;
import org.sharll.launcher.Launcher;
public class Main {
    public static void main(String[] args) {
        Launcher launcher = new Launcher("C:\\Users\\Sharll\\Desktop\\HMCL\\.minecraft", "1.16.5", null, "player");
        launcher.Launch();
    }
}