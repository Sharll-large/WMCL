package org.sharll.launcher;
import org.json.*;

import java.nio.file.Paths;
import java.util.Objects;

import org.sharll.file.ReadFile;

public class Launcher {
    final JSONObject MinecraftObject;
    final String JavaPath;
    final String PlayerName;
    final String McPath;
    final String Version;
    public Launcher(String McPath, String Version, String JavaPath, String PlayerName){
        this.MinecraftObject = new JSONObject(ReadFile.read(Paths.get(McPath, "versions", Version, Version + ".json").toString()));
        this.JavaPath = JavaPath;
        this.PlayerName = PlayerName;
        this.McPath = McPath;
        this.Version = Version;
    }
    protected String SolveJava(){
        if(JavaPath == null) return "java";
        if(ReadFile.read(JavaPath).equals("")){
            return "java";
        }else{
            return "\"" + this.JavaPath + "\"";
        }
    }

    protected String SolveJvm(){
        if(MinecraftObject.isNull("arguments")){
            return "-Djava.library.path=${natives_directory} -cp ${classpath} " + MinecraftObject.get("minecraftArguments");
        }else{
            String arguments = "";

            JSONObject temp = new JSONObject(MinecraftObject.get("arguments").toString());
            JSONArray JvmArguments = new JSONArray(temp.get("jvm").toString());
            JSONArray GameArguments = new JSONArray(temp.get("game").toString());

            JvmArguments.forEach(System.out::println);
            GameArguments.forEach(System.out::println);

            return JvmArguments + GameArguments.toString();
        }
    }

    public String SolveArguments(){
        String GameArguments = "";
        if(MinecraftObject.isNull("arguments")){
            GameArguments = MinecraftObject.get("minecraftArguments").toString();
        }else{
            StringBuilder arguments = new StringBuilder();
            JSONObject temp = new JSONObject(MinecraftObject.get("arguments").toString());
            JSONArray $GameArguments = new JSONArray(temp.get("game").toString());
            for(Object i: $GameArguments){
                //System.out.println(i);
                try{
                    new JSONObject(i.toString());
                }catch(JSONException e) {
                    System.out.println(i);
                    System.out.println("omg");
                    arguments.append(i).append(" ");
                }
            }
            GameArguments = arguments.toString();

        }
        return "-Djava.library.path=${natives_directory} -cp ${classpath} ${MainClass} " + GameArguments;
    }
    public String SolveClassPath(){
        StringBuilder classpath = new StringBuilder();
        for(Object i: new JSONArray(MinecraftObject.get("libraries").toString())){
            JSONObject now = new JSONObject(i.toString());

            String[] this_package = now.get("name").toString().split(":");
            String pack = this_package[0].replace(".", "\\");
            String name = this_package[1];
            String ver = this_package[2];
            String thisLib = Paths.get(McPath, "libraries", pack, name, ver, name + '-' + ver + ".jar") + ";";

            if(now.isNull("downloads") || new JSONObject(now.get("downloads").toString()).isNull("classifiers")) {
                if (now.isNull("rules")) {
                    classpath.append(thisLib);
                } else {
                    for (Object j : new JSONArray(now.get("rules").toString())) {
                        JSONObject n = new JSONObject(j.toString());
                        if (Objects.equals(n.get("action").toString(), "allow")) {
                            if (!n.isNull("os")) {
                                if (Objects.equals(new JSONObject(n.get("os").toString()).get("name").toString(), "windows")) {
                                    classpath.append(thisLib);
                                }
                            }
                        } else {
                            if (!n.isNull("os")) {
                                if (!Objects.equals(new JSONObject(n.get("os").toString()).get("name").toString(), "windows")) {
                                    classpath.append(thisLib);
                                }
                            }
                        }
                    }
                }
            }
        }
        classpath.append(Paths.get(McPath, "versions", Version, Version + ".jar"));
        return classpath.toString();
    }
    public void Launch(){
        System.out.println((SolveJava() + " " + SolveArguments()).replace("${MainClass}", MinecraftObject.get("mainClass").toString()).replace("${classpath}", SolveClassPath()).replace("${natives_directory}", Paths.get(McPath, "versions", Version, "natives").toString()));
        //System.out.println(SolveArguments());
        //System.out.println(SolveClassPath());

    }
}
