package net.alkitmessenger.packet;

import com.google.gson.Gson;
import lombok.Data;

@Data
public class PacketData<T> {

    String classPath;
    String jsonString;

    public PacketData(T data) {

        classPath = data.getClass().getName();
        jsonString = new Gson().toJson(data);

    }

    public T deserialize() throws ClassNotFoundException {

        return (T) new Gson().fromJson(jsonString, Class.forName(classPath));

    }
}