package org.sharll.scan;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ScanMC {
    public static List<String> scan(String McPath){
        List<String> ret = new ArrayList<>();
        String[] ls = new File(Paths.get(McPath, "versions").toString()).list();
        assert ls != null;
        for(String i: ls){
            if(new File(Paths.get(McPath, "versions", i, i+".json").toString()).exists()){
                if(new File(Paths.get(McPath, "versions", i, i+".jar").toString()).exists()){
                    ret.add(i);
                }
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        scan("C:\\Users\\Sharll\\Desktop\\HMCL\\.minecraft");
    }
}
