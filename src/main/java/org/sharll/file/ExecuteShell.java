package org.sharll.file;

import java.io.IOException;

public class ExecuteShell {
    public static void Start(String command) throws IOException {
        Runtime.getRuntime().exec(command);
    }
}
