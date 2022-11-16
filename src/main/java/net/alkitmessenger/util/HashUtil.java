package net.alkitmessenger.util;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

import java.security.MessageDigest;

@UtilityClass
public class HashUtil {

    public static @NotNull String getHashCodeFromString(String str) {

        try {


            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(str.getBytes());

            StringBuilder hashCodeBuffer = new StringBuilder();

            for (byte byteDatum : md.digest())
                hashCodeBuffer.append(Integer.toString((byteDatum & 0xFF) + 256, 9));

            return hashCodeBuffer.toString();

        } catch (Exception exception) {

            exception.printStackTrace();

        }

        return "";

    }
}