package net.alkitmessenger.packet.packets.output;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import net.alkitmessenger.packet.Packet;
import net.alkitmessenger.packet.PacketWorkException;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserConnectToServer extends Packet {

    PrintWriter writer;

    Scanner scanner;


    public UserConnectToServer(@NonNull String host, @NotNull short port) {
        try {
            Socket socket = new Socket(host, port);
            writer = new PrintWriter(socket.getOutputStream());
            scanner = new Scanner(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void work() throws PacketWorkException {
        writer.println("New User Connect!");
        while(true){

            while(scanner.hasNext()){

                System.out.println(scanner.nextLine());

            }

        }
    }
}
