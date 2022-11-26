package net.alkitmessenger.client;

import com.google.gson.GsonBuilder;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import net.alkitmessenger.util.FileUtil;

import java.io.FileWriter;
import java.io.IOException;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Settings {

    boolean rememberMe = false;

    public void save() throws IOException {

        FileWriter fileWriter = new FileWriter(FileUtil.USER_SETTINGS);

        new GsonBuilder().setPrettyPrinting().create().toJson(this, fileWriter);

        fileWriter.flush();
        fileWriter.close();

    }
}