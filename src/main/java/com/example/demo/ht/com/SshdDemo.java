package com.example.demo.ht.com;

import org.apache.sshd.client.ClientBuilder;
import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.channel.ChannelExec;
import org.apache.sshd.client.channel.ClientChannel;
import org.apache.sshd.client.future.ConnectFuture;
import org.apache.sshd.client.keyverifier.AcceptAllServerKeyVerifier;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.common.NamedFactory;
import org.apache.sshd.common.kex.BuiltinDHFactories;
import org.apache.sshd.common.signature.BuiltinSignatures;

import java.util.ArrayList;

public class SshdDemo {

    public static void main(String[] args)throws Exception {

        SshClient client = SshClient.setUpDefaultClient();
        client.start();
        ClientSession session = client.connect("juji", "192.168.0.99", 22).verify(10000).getSession();
        //session.addPublicKeyIdentity(SecurityUtils.loadKeyPairIdentity("keyname", new FileInputStream("priKey.pem"), null));
        session.addPasswordIdentity("jujijuji");
        client.setKeyExchangeFactories(NamedFactory.setUpTransformedFactories(
                false,
                BuiltinDHFactories.VALUES,
                ClientBuilder.DH2KEX
        ));

        client.setSignatureFactories(new ArrayList<>(BuiltinSignatures.VALUES));
        if (!session.auth().verify(10 * 1000).isSuccess()) {
            throw new Exception("auth faild");
        }

        session.createChannel("enable");
        ChannelExec ec = session.createExecChannel("show history");
        ec.setOut(System.out);
        ec.open();
        ec.close();

        client.stop();
    }
}
