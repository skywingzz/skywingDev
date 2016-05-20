package com.socket;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by skywing on 2016. 5. 11..
 */
public class JavaSocketServer {
    ArrayList<ObjectOutputStream> clients = new ArrayList<ObjectOutputStream>();

    public void broadcast(Object msg) {
        for(ObjectOutputStream out : clients) {
            try {
                out.writeObject(msg);
                out.flush();
            } catch(IOException e) {
                if(out != null) {
                    try {
                        out.close();
                    } catch (IOException e1){}
                    clients.remove(out);
                }
            }

        }
    }

    public JavaSocketServer() {

        try {
            ServerSocket server = new ServerSocket(8001);
            System.out.println("Starting Java Socket Server...");

            while(true) {
                Socket aSocket = server.accept();
                System.out.println(aSocket.getInetAddress().getHostName() + "님이 접속 하셨습니다.");

                ObjectInputStream instream = new ObjectInputStream(aSocket.getInputStream());
                ObjectOutputStream outstream = new ObjectOutputStream(aSocket.getOutputStream());

                clients.add(outstream);

                ServerThread thread = new ServerThread(aSocket, instream);
                thread.start();

                outstream.flush();
                aSocket.close();
                System.out.println("Close.");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {



    }

    class ServerThread extends Thread {
        Socket aSocket;
        ObjectInputStream instream;
        public ServerThread(Socket s, ObjectInputStream in) {
            this.aSocket = s;
            this.instream = in;
        }
        @Override
        public void run() {
            Object obj = null;
            try {
                while(true) {
                    obj = instream.readObject();
                    broadcast(obj);
                }

            } catch(Exception e) {
                System.out.println(aSocket.getInetAddress().getHostName() + "님이 나가셨습니다");
                if(instream != null) {
                    try {
                        instream.close();
                    } catch(IOException ie) {}

                }
                if(aSocket != null) {
                    try {
                        aSocket.close();
                    } catch(IOException ie) {}

                }
            }
            System.out.println("서버로 부터 온 메세지 : " + obj);

        }
    }

}
