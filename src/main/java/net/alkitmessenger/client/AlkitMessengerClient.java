package net.alkitmessenger.client;

import net.alkitmessenger.util.CryptorUtil;

public class AlkitMessengerClient {
    public static void main(String[] args) {
        byte[] test = new byte[] {0, 5, -4, 2, 127, -85, 25};
        StringBuffer[] test1 = CryptorUtil.byteCryptor(test);
        byte[] test2 = CryptorUtil.byteDecryptor(test1);
        for (int i = 0; i < test2.length; i++) {
            System.out.println(test2[i]);
        }
    }
}