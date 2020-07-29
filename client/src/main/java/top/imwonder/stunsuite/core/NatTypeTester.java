/*
 * @Author: Wonder2019 
 * @Date: 2020-07-26 10:42:57 
 * @Last Modified by:   Wonder2019 
 * @Last Modified time: 2020-07-26 10:42:57 
 */
package top.imwonder.stunsuite.core;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import top.imwonder.stunsuite.exception.IllegalPacketException;
import top.imwonder.stunsuite.exception.PacketSizeOverflowException;
import top.imwonder.stunsuite.packet.StunMsgType;
import top.imwonder.stunsuite.packet.StunPacket;

@Data
@Slf4j
public class NatTypeTester {

    private int timeout = 500;

    public NatType test(String stunHost, int port) throws UnknownHostException {
        return test(InetAddress.getByName(stunHost), port);
    }

    public NatType test(InetAddress address, int port) {
        try {
            StunPacket sendPacket = new StunPacket(StunMsgType.BINDING_REQUEST);
            byte spb[] = sendPacket.getByte(false);
            DatagramPacket sdp = new DatagramPacket(spb, spb.length, address, port);
            DatagramSocket ds = new DatagramSocket();
            ds.disconnect();
            ds.setSoTimeout(timeout);
            ds.connect(address, port);
            ds.send(sdp);
            byte rpb[] = new byte[256];
            DatagramPacket rdp = new DatagramPacket(rpb, 256);
            ds.receive(rdp);
            StunPacket receivePacket = new StunPacket(rdp.getData());
            ds.close();
        } catch (PacketSizeOverflowException e) {
            log.info("This should not happen!");
            log.debug(e.getMessage());
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SocketTimeoutException e) {
            log.debug(e.getMessage());
            return NatType.UDP_BLOCK;
        } catch (IOException e) {

        } catch (IllegalPacketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}