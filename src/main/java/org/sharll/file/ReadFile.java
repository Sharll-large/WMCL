package org.sharll.file;
import java.io.*;

public class ReadFile {
    public static String read(String filename)  {
        StringBuilder stuff = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader(filename));
            String str;
            while ((str = in.readLine()) != null) {
                stuff.append(str);
            }
        } catch (IOException ignored) {
        }
        return stuff.toString();
    }
}