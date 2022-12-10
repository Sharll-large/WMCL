package org.sharll.file;

import java.util.Properties;

public class OS {
    Properties props = System.getProperties();
    public final String split_dir = props.getProperty("file.separator");
    public final String split_obj = props.getProperty("path.separator");
    public final String OsName = props.getProperty("os.name");
    public final String OsArch = props.getProperty("os.arch");
    public final String OsVersion = props.getProperty("os.version");
    public final String rootDir = props.getProperty("user.dir");
}
