package top.imwonder.stunsuite.attribute.stunattribute;

import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class XORAddressAttribute extends AddressAttribute {

    public static final short TYPE_XOR_MAPPED_ADDRESS = 0x0020;
    
    private final byte fullTranscationId[];

    public XORAddressAttribute(short type) {
        super(type);
        this.fullTranscationId = new byte[16];
    }

    public XORAddressAttribute(short type, InetAddress addr) {
        super(type, addr);
        this.fullTranscationId = new byte[16];
    }

    public XORAddressAttribute(short type, int port) {
        super(type, port);
        this.fullTranscationId = new byte[16];
    }

    public XORAddressAttribute(short type, int port, InetAddress addr) {
        super(type, port, addr);
        this.fullTranscationId = new byte[16];
    }

    public XORAddressAttribute(short type, byte[] packet) {
        super(type, packet);
        ByteBuffer buf = ByteBuffer.wrap(packet);
        buf.position(packet.length - 16);
        int halfMagicCookie = buf.getShort(packet.length - 16) & 0xffff;
        this.fullTranscationId = new byte[16];
        buf.get(this.fullTranscationId, 0, 16);
        this.port = this.port ^ halfMagicCookie;
        int len = this.address.length;
        for(int i=0;i<len;i++){
            this.address[i] = (byte)(this.address[i] ^ this.fullTranscationId[i]);
        }
    }

    public void setFullTranscationId(byte[] fullTranscationId) {
        Arrays.fill(this.fullTranscationId, (byte)0);
        System.arraycopy(fullTranscationId, 0, this.fullTranscationId, 0, Math.min(fullTranscationId.length, this.fullTranscationId.length));
    }

    @Override
    public ByteBuffer getByte() {
        if (addressFamily == ADDRESS_FAMILY_IPV4) {
            length = 8;
        } else if (addressFamily == ADDRESS_FAMILY_IPV6) {
            length = 20;
        } else {
            log.warn("Illegal address family: {}", addressFamily);
            length = 0;
            return ByteBuffer.allocate(0);
        }
        ByteBuffer ftid = ByteBuffer.wrap(this.fullTranscationId);
        short halfMagicCookie = ftid.getShort();
        ByteBuffer buf = ByteBuffer.allocate(length + 4);
        buf.putShort(type.getType());
        buf.putShort((short)length);
        buf.putInt(halfMagicCookie ^ port);
        buf.position(buf.position() - 4);
        buf.putShort(addressFamily);
        buf.position(buf.position() + 2);

        int len = address.length;
        byte xoredAddress[] = new byte[len]; 
        for(int i=0;i<len;i++){
            xoredAddress[i] = (byte)(address[i] ^ fullTranscationId[i]);
        }
        buf.put(xoredAddress);
        return null;
    }

    
}