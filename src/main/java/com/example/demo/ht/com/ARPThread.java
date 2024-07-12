package com.example.demo.ht.com;

import jpcap.packet.ARPPacket;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ARPThread extends Thread {

    private String netIP;
    private Integer n = 1;
    private boolean flag = true;

    ARPThread(String netIP) {
        this.netIP = netIP;
    }

    @Override
    public void run() {
        try {
            ARPPacket arpP = ARPSearch.getTargetMAC(InetAddress.getByName(this.netIP));
            if (arpP == null) {
            } else {
                System.out.println("硬件类型：" + arpP.hardtype);
                System.out.println("操作类型：" + arpP.operation);
                System.out.println("源 MAC 地址：" + arpP.getSenderHardwareAddress());
                System.out.println("源 IP 地址 ：" + arpP.getSenderProtocolAddress());
                System.out.println("目标 MAC 地址    " + arpP.getTargetHardwareAddress());
                System.out.println("目标 IP 地址     " + arpP.getTargetProtocolAddress());
                System.out.println("===================================");
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}