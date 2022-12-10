package org.sharll.file;

import java.io.FileWriter;
import java.io.IOException;

public class WriteFile {
    public static void write(String filepath, String content) throws IOException {
        try (FileWriter fileWriter = new FileWriter(filepath)) {
            fileWriter.append(content);
        }
    }
}
