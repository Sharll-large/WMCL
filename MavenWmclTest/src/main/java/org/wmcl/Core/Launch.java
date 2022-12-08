package org.wmcl.Core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.wmcl.Tools.OS;
import org.wmcl.Tools.ReadFile;

import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public class Launch {
    static OS os = new OS();
    protected static boolean CheckRule(JSONObject obj){

        if(obj.get("rules") != null) {
            List<JSONObject> rules = JSON.parseArray(obj.getJSONArray("rules").toJSONString(), JSONObject.class);
            for (JSONObject j : rules) {
                if (j.get("os") != null && (obj.get("downloads") == null || JSONObject.parseObject(obj.get("downloads").toString()).get("classifiers") != null)) {
                    String ActionOsName = JSONObject.parseObject(j.get("os").toString()).get("name").toString();
                    String ActionType = j.get("action").toString();
                    System.out.println(ActionOsName);
                    if(os.OsName.lastIndexOf(ActionOsName) >= 0){
                        if(Objects.equals(ActionType, "allow")) return true;
                        if(Objects.equals(ActionType, "disallow")) return false;
                    }else{
                        if(Objects.equals(ActionType, "allow")) return false;
                        if(Objects.equals(ActionType, "disallow")) return true;
                    }
                }

            }
        }else{
            return true;
        }
        return false;
    }
    public static void launch(String Minecraft_Path, String version) {
        StringBuilder command = new StringBuilder("java -cp ");
        JSONObject version_json = JSONObject.parseObject(ReadFile.read(Paths.get(Minecraft_Path, "versions", version, version+".json").toAbsolutePath().toString()));
        String string;

        if(version_json.get("arguments") == null) {
            string = version_json.get("minecraftArguments").toString();
        }else{
            string = version_json.get("arguments").toString();
        }

        System.out.println(string);

        List<JSONObject> libraries = JSON.parseArray(version_json.getJSONArray("libraries").toJSONString(),JSONObject.class);
        for(JSONObject i: libraries){
            if(CheckRule(i)){
                String[] this_package = i.get("name").toString().split(":");
                String pack = this_package[0].replace(".", os.split_dir);
                String name = this_package[1];
                String ver = this_package[2];
                String thisLib = Paths.get(Minecraft_Path, "libraries", pack, name, ver, name + '-' + ver + ".jar").toString();
                if(command.lastIndexOf(thisLib) < 0) command.append(thisLib);
            }
        }
        command.append(Paths.get(Minecraft_Path, "versions", version, version+".jar").toAbsolutePath());
        command.append(" ").append(version_json.get("mainClass")).append(" ");
        System.out.println(command);
    }
}
