package com.example.demo.ht.com;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TelnetDemo {

    public static void main(String[] args) throws Exception {
        Socket client = new Socket("192.168.0.99", 22);

        OutputStream outToServer = client.getOutputStream();
        //拼接字符串
//        String namePwd = "juji" + "," + "jujijuji";
//        //传入服务端
//        outToServer.write(namePwd.getBytes());
        DataOutputStream out = new DataOutputStream(outToServer);
        out.writeUTF("juji");
        out.writeUTF("\n");
        out.writeUTF("jujijuji");
        out.writeUTF("\n");
        int data = client.getInputStream().read();
        InputStream inFromServer = client.getInputStream();
        out.writeUTF("\n");
        String cmdStr = "show ip arp inspection interface";
        out.writeUTF(cmdStr);
        client.shutdownOutput();

        DataInputStream in = new DataInputStream(inFromServer);
        System.out.println(" 读取服务器返回" + in.readUTF());
        client.close();

    }
}
