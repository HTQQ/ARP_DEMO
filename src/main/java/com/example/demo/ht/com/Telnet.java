package com.example.demo.ht.com;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import org.apache.commons.net.telnet.TelnetClient;


public class Telnet {

    private TelnetClient telnet = new TelnetClient("VT100");

    private InputStream in;

    private PrintStream out;

    private static final String DEFAULT_AIX_PROMPT = "#";
    private static final String ENTER_COMMAND_ARROW = ">";
    private static final String ENTER_COMMAND_BRACKETS = "]";
    private static final String ENTER="\n";


    /**
     * telnet 端口
     */
    private String port;

    /**
     * 用户名
     */
    private String user;

    /**
     * 密码
     */
    private String password;

    /**
     * IP 地址
     */
    private String ip;

    public Telnet(String ip, String user, String password) {
        this.ip = ip;
        this.port = String.valueOf(23);
        this.user = user;
        this.password = password;
    }

    public Telnet(String ip, String port, String user, String password) {
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.password = password;
    }

    /**
     * @return boolean 连接成功返回true，否则返回false
     */
    private boolean connect() {

        boolean isConnect = true;

        try {

            telnet.connect(ip, Integer.parseInt(port));
            in = telnet.getInputStream();
            out = new PrintStream(telnet.getOutputStream());
            telnet.setKeepAlive(true);
            write(user);
            write(password);
            String msg=readUntil(ENTER_COMMAND_ARROW);
        } catch (Exception e) {
            isConnect = false;
            e.printStackTrace();
            return isConnect;
        }
        return isConnect;
    }

    public void su(String user, String password) {
        try {
            write("su" + " - " + user);
            readUntil("Password:");
            write(password);
            readUntil(DEFAULT_AIX_PROMPT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String readUntil(String pattern) {
        try {
            char lastChar = pattern.charAt(pattern.length() - 1);
            StringBuffer sb = new StringBuffer();
            char ch = (char) in.read();
            while (true) {
                sb.append(ch);
                if (ch == lastChar) {
                    if (sb.toString().endsWith(pattern)) {
                        return sb.toString();
                    }
                }
                ch = (char) in.read();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void write(String value) {
        try {
            out.println(value);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String sendCommand(String command) {
        try {
            write(command);
            return readUntil(DEFAULT_AIX_PROMPT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private void disconnect() {
        try {
            telnet.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getNowDate() {
        this.connect();
        String nowDate = this.sendCommand("date|awk '{print $2,$3,$4}'");
        String[] temp = nowDate.split("\r\n");
        // 去除命令字符串
        if (temp.length > 1) {
            nowDate = temp[0];
        } else {
            nowDate = "";
        }
        this.disconnect();
        return nowDate;
    }

    public static void main(String[] args) {
        try {
            Telnet telnet = new Telnet("192.168.0.99", "juji", "jujijuji");

            telnet.connect();
            String enable = telnet.sendCommand("enable");
            String show_history = telnet.sendCommand("show mac address-table dynamic");
            telnet.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}