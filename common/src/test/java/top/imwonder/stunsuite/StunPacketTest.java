package top.imwonder.stunsuite;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.junit.Test;

import top.imwonder.stunsuite.attribute.Attribute;
import top.imwonder.stunsuite.exception.IllegalPacketException;
import top.imwonder.stunsuite.packet.StunMsgType;
import top.imwonder.stunsuite.packet.StunPacket;

public class StunPacketTest {

    @Test
    public void parseTest() {
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream("raw_data.txt"))) {
            byte rawData[] = new byte[256];
            bis.read(rawData);
            StunPacket sp = new StunPacket(rawData);
            System.out.println(sp.getAttributes()[0].toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalPacketException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void onlineTest() throws Exception {
        String host = "stun.miwifi.com";
        int port = 3478;
        StunPacket sendPacket = new StunPacket(StunMsgType.BINDING_REQUEST);
        byte spb[] = sendPacket.getByte(false);
        InetAddress ia = InetAddress.getByName(host);
        DatagramPacket sdp = new DatagramPacket(spb, spb.length, ia, port);
        DatagramSocket ds = new DatagramSocket();
        System.out.println( String.format("local: \n\thost: %s\n\tport: %d\n", ds.getLocalAddress().getHostAddress(),ds.getLocalPort()));
        System.out.println(String.format("send to: %s:%d\n", host,port));
        ds.disconnect();
        ds.connect(ia, port);
        ds.send(sdp);

        byte rpb[] = new byte[256];
        DatagramPacket rdp = new DatagramPacket(rpb, 256);
        ds.receive(rdp);
        StunPacket receivePacket = new StunPacket(rdp.getData());
        ds.close();

        FileOutputStream fos = new FileOutputStream("stunqq.txt");
        fos.write(rpb);
        fos.close();
        System.out.println(String.format("received Packet From '%s:%d'",host,port));
        Attribute attrs[] = receivePacket.getAttributes();
        System.out.println(String.format("%d attribute in this packet\n", attrs.length));
        int tag = 1;
        for (Attribute attribute : attrs) {
            System.out.print(String.format("%d. ", tag));
            System.out.println(attribute.toString());
            tag++;
        }
    }
}