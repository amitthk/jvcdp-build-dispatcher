package com.jvcdp.utility;

import java.io.File;

public class OsHelper {

    private static String OS = System.getProperty("os.name").toLowerCase();

    public static boolean isWindows() {

        return (OS.indexOf("win") >= 0);

    }

    public static boolean isMac() {

        return (OS.indexOf("mac") >= 0);

    }

    public static boolean isUnix() {

        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );

    }

    public static boolean isSolaris() {

        return (OS.indexOf("sunos") >= 0);

    }

    public static File getResource(String fileName){
        ClassLoader classLoader = OsHelper.class.getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        return(file);
    }

}
