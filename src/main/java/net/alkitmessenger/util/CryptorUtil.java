package net.alkitmessenger.util;

import lombok.experimental.UtilityClass;

import java.util.stream.IntStream;
@UtilityClass
public class CryptorUtil {
    public StringBuffer[] byteCryptor(byte[] buffer){
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

    public String textCryptor(String text){
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


// переработать


//    public byte[] byteDecryptor(StringBuffer[] cryptByte){
//        byte[] result = new byte[cryptByte.length];
//        for (int i = 0; i < cryptByte.length / 2; i++) {
//            StringBuffer tempInt = cryptByte[i];
//            cryptByte[i] = cryptByte[cryptByte.length - 1 - i];
//            cryptByte[cryptByte.length - 1 - i] = tempInt;
//        }
//        for (int i = 0; i < cryptByte.length; i++) {
//            if (cryptByte[i] != null){
//                result[i] = Byte.parseByte(cryptByte[i].toString().replaceAll("\\D+","") + alfabetToRemains(cryptByte[i].toString().replaceAll("\\d+","").toCharArray()[0]));
//            }
//            else
//                result[i] = 0;
//        }
//        return result;
//    }
//    public int alfabetToRemains(char r){
//        char[] used = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
//
//        if (Character.isUpperCase(r)){
//            for (int i = 0; i < used.length; i++) {
//                if (used[i] == r) return i;
//            }
//        }
//        else{
//            for (int i = 0; i < used.length; i++) {
//                if (used[i] == r) return -i;
//            }
//        }
//
//        return -1;
//    }
}
