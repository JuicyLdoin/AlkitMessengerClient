package net.alkitmessenger.util;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

import java.util.stream.IntStream;

import static java.lang.Byte.parseByte;

@UtilityClass
public class CryptorUtil {
    public static StringBuffer @NotNull [] byteCryptor(byte @NotNull [] buffer){
        StringBuffer[] result = new StringBuffer[buffer.length];
        for (int i = 0; i < buffer.length / 2; i++) {
            int temp = buffer[i];
            buffer[i] = buffer[buffer.length - 1 - i];
            buffer[buffer.length - 1 - i] = (byte) temp;
        }

        IntStream.range(0, buffer.length).forEach(i -> {
            buffer[i] = (byte) -buffer[i];
            if (buffer[i] != 0) {
                result[i] = new StringBuffer().append(buffer[i] / 26).append(remainsToAlfabet(buffer[i] % 26));
            }
        });

        return result;
    }
    public char remainsToAlfabet(int i){
        char[] result;

        if (i < 0){
            result = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
            i = -i;
        }
        else
            result = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

        return result[i];
    }

    public static @NotNull String textCryptor(@NotNull String text){
        char[] tempArr = text.toCharArray();

        for (int i = 0; i < tempArr.length; i++) {
            tempArr[i] = (char) ((char) (tempArr[i] / 26) + remainsToAlfabet(tempArr[i] % 26));
        }
        for (int i = 0; i < tempArr.length / 2; i++) {
            char temp = tempArr[i];
            tempArr[i] = tempArr[tempArr.length - 1 - i];
            tempArr[tempArr.length - 1 - i] = temp;
        }
        return String.valueOf(tempArr);
    }

    public static byte @NotNull [] byteDecryptor(StringBuffer @NotNull [] cryptByte){
        byte[] result = new byte[cryptByte.length];
        for (int i = 0; i < cryptByte.length / 2; i++) {
            StringBuffer tempInt = cryptByte[i];
            cryptByte[i] = cryptByte[cryptByte.length - 1 - i];
            cryptByte[cryptByte.length - 1 - i] = tempInt;
        }
        for (int i = 0; i < cryptByte.length; i++) {
            if (cryptByte[i] != null){
                result[i] = parseByte(cryptByte[i].toString().replaceAll("\\D+",""));

                //System.out.println(cryptByte[i].toString().replaceAll("\\d+","").replaceAll("[-]", "").toCharArray()[0]);
                if (result[i] > 0)
                    result[i] = (byte) (result[i] * 26 + alfabetToRemains(cryptByte[i].toString().replaceAll("\\d+","").replaceAll("[-]", "").toCharArray()[0]));

                if (alfabetToRemains(cryptByte[i].toString().replaceAll("\\d+","").toCharArray()[0]) < 26 && alfabetToRemains(cryptByte[i].toString().replaceAll("\\d+","").toCharArray()[0]) < 0)
                    result[i] += alfabetToRemains(cryptByte[i].toString().replaceAll("\\d+","").toCharArray()[0]) + 1;

                else if (alfabetToRemains(cryptByte[i].toString().replaceAll("\\d+","").toCharArray()[0]) < 26 && alfabetToRemains(cryptByte[i].toString().replaceAll("\\d+","").toCharArray()[0]) > 0){
                    result[i] += alfabetToRemains(cryptByte[i].toString().replaceAll("\\d+","").toCharArray()[0]) -1;
                }
            }
            else
                result[i] = 0;
        }
        return result;
    }
    public int alfabetToRemains(char r){
        char[] usedm = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        char[] usedM = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

        if (Character.isUpperCase(r)){
            for (int i = 0; i < usedM.length; i++) {
                int comp = Character.compare(usedM[i], r);
                if (comp > 0)
                    return i;
            }
        }
        else{
            for (int i = 0; i < usedm.length; i++) {
                int comp = Character.compare(usedm[i], r);
                if (comp > 0)
                    return -i;
            }
        }

        return -1;
    }
}
