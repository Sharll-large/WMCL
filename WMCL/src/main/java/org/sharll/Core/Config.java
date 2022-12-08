package org.sharll.Core;

import com.alibaba.fastjson.*;
import org.sharll.Tools.OS;
import org.sharll.Tools.ReadFile;
import org.sharll.Tools.WriteFile;

import java.io.IOException;
import java.nio.file.Paths;

public class Config {
    static OS os = new OS();
    public static JSONObject read() throws IOException {
        System.out.println(ReadFile.read(Paths.get(os.rootDir, "WmclConfig.json").toString()));

        if(JSONObject.parseObject(ReadFile.read(Paths.get(os.rootDir, "WmclConfig.json").toString())) == null){
            System.out.println("n");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("McPath", Paths.get(os.rootDir, ".minecraft").toString());
            jsonObject.put("JavaPath", "java.exe");
            jsonObject.put("PlayerName", "player");
            jsonObject.put("JvmMaxRam", "1024");
            write(jsonObject);
        }
        return JSONObject.parseObject(ReadFile.read(Paths.get(os.rootDir, "WmclConfig.json").toString()));
    }
    public static void write(JSONObject config) throws IOException {
        WriteFile.write(Paths.get(os.rootDir, "WmclConfig.json").toString(), config.toString());
    }
}
