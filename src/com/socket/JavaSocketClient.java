package com.socket;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by skywing on 2016. 5. 11..
 */
public class JavaSocketClient {
    public static void main(String[] args) {
        try {
            int portNumber = 8001;
            Socket sock = new Socket("localhost", portNumber);

            ObjectOutputStream outputStream = new ObjectOutputStream(sock.getOutputStream());
            outputStream.writeObject("Hello Android Town");
            outputStream.flush();

            ObjectInputStream inputStream = new ObjectInputStream(sock.getInputStream());
            System.out.println(inputStream.readObject());
            sock.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
