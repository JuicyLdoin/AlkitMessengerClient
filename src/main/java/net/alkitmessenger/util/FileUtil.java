package net.alkitmessenger.util;

import lombok.experimental.UtilityClass;

import java.io.File;

@UtilityClass
public class FileUtil {

    public static final File DATA_FOLDER = new File(System.getProperty("user.home") + "\\AlkitMessenger");

    public static final File USER_DATA = new File(DATA_FOLDER, "user.json");
    public static final File USER_SETTINGS = new File(DATA_FOLDER, "user.json");

    static {

        if (!DATA_FOLDER.exists())
            DATA_FOLDER.mkdirs();

    }
}