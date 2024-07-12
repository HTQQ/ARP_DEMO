package com.example.demo.ht.com;

import com.jcraft.jsch.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class SshArpDemo {

    private static final int SESSION_TIMEOUT = 100;
    //    private static final String IP = "172.16.79.128";
    private static final String IP = "192.168.0.99";
    private static final String USERNAME = "juji";
    private static final String PASSWORD = "jujijuji";

    private void sshArpDemo() throws JSchException, IOException, InterruptedException {
        JSch jSch = new JSch();
        List<String> res = new ArrayList<>();
//        jSch.addIdentity("D:\\192.168.0.99\\192.168.0.99.pem");
        Session session = jSch.getSession(USERNAME, IP, 22);
        session.setPassword(PASSWORD);
        Properties sshConfig = new Properties();
        sshConfig.put("StrictHostKeyChecking", "no");
        sshConfig.put("PreferredAuthentications", "password");
//        sshConfig.put("PreferredAuthentications", "publickey");
        session.setConfig(sshConfig);
        session.connect(600000);
        ChannelShell channel = (ChannelShell) session.openChannel("shell");
        InputStream inputStream = channel.getInputStream();
//        channel.setCommand("enable");
//        channel.setCommand("enable,show mac address-table dynamic");
//        channel.setCommand("show history");
//channel.setCommand(commands);

        channel.connect();
//        Thread.sleep(1000);
//        // 写入该流的数据都将发送到远程端
        OutputStream outputStream = channel.getOutputStream();

        // 使用PrintWriter 就是为了使用println 这个方法好处就是不需要每次手动给字符加\n
        PrintWriter printWriter = new PrintWriter(outputStream);

        printWriter.println("enable");
        printWriter.println("show mac address-table dynamic");
        printWriter.flush();
        String result = null;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        while ((result = in.readLine()) != null) {
            res.add(result);
            System.out.println(result);
        }
        System.out.println("结束");
    }

    public static void main(String[] args) {
        SshArpDemo sshArpDemo = new SshArpDemo();
        try {
            sshArpDemo.sshArpDemo();
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
