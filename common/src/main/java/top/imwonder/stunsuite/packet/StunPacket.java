package top.imwonder.stunsuite.packet;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import top.imwonder.stunsuite.attribute.Attribute;
import top.imwonder.stunsuite.attribute.StunAttribute;
import top.imwonder.stunsuite.exception.IllegalPacketException;
import top.imwonder.stunsuite.exception.PacketSizeOverflowException;
import top.imwonder.stunsuite.util.IdGen;

@Getter
public class StunPacket {

    private StunMsgType type;

    @Getter(AccessLevel.NONE)
    private List<Attribute> attributes;

    @Setter
    private int magicCookie = 0x2112A442;

    private byte[] transactionId;

    public StunPacket(StunMsgType type) {
        this.type = type;
        this.attributes = new ArrayList<>();
        this.transactionId = new byte[12];
        IdGen.transcationId(this.transactionId);
    }

    public StunPacket(StunMsgType type, byte[] transcationId) {
        this.type = type;
        this.attributes = new ArrayList<>();
        this.transactionId = new byte[12];
        Arrays.fill(this.transactionId, (byte) 0);
        System.arraycopy(transcationId, 0, this.transactionId, 0,
                Math.min(this.transactionId.length, transcationId.length));
    }

    public StunPacket(StunMsgType type, List<Attribute> attributes) {
        this.type = type;
        this.attributes = attributes;
        this.transactionId = new byte[12];
        IdGen.transcationId(this.transactionId);
    }

    public StunPacket(StunMsgType type, byte[] transcationId, List<Attribute> attributes) {
        this.type = type;
        this.attributes = attributes;
        this.transactionId = new byte[12];
        Arrays.fill(this.transactionId, (byte) 0);
        System.arraycopy(transcationId, 0, this.transactionId, 0,
                Math.min(this.transactionId.length, transcationId.length));
    }

    public StunPacket(byte[] packet) throws IllegalPacketException {
        ByteBuffer buf = ByteBuffer.wrap(packet);
        short type = buf.getShort();
        if (type > 0x3fff) {
            throw new IllegalPacketException("It's not a STUN Packet!");
        }
        this.type = StunMsgType.queryByCode(type);
        int length = buf.getShort() &0xffff;
        this.magicCookie = buf.getInt();
        this.transactionId = new byte[12];
        buf.get(this.transactionId, 0, 12);
        this.attributes = new ArrayList<>();
        while (true) {
            if (length < 4) {
                break;
            }
            int len = buf.getShort(buf.position() + 2) & 0xffff;
            length -= (len + 4);
            byte part[] = new byte[len + 20];
            buf.get(part, 0, len + 4);
            ByteBuffer ap = ByteBuffer.wrap(part);
            ap.position(len + 4);
            ap.putInt(this.magicCookie);
            ap.put(this.transactionId);
            Attribute attr = StunAttribute.parseToAttribute(ap.array());
            this.attributes.add(attr);
            // buf.position(buf.position() + part.length);
        }
    }

    public void addAttribute(Attribute attr) {
        attributes.add(attr);
    }

    public void addAttributes(List<Attribute> attrs) {
        attributes.addAll(attrs);
    }

    public Attribute[] getAttributes() {
        return attributes.toArray(new Attribute[attributes.size()]);
    }

    public byte[] getByte(boolean isLimitPacketSize) throws PacketSizeOverflowException {
        int msgLength = 20;
        for (Attribute attr : attributes) {
            msgLength += attr.length();
        }
        if (isLimitPacketSize && msgLength > 548) {
            throw new PacketSizeOverflowException(String.format(
                    "The current package size is %dbyte, but the safe package size is 548byte. Please try to reduce the number of attributes or use the \"false\" parameter to force the stun package to be built.",
                    msgLength));
        }
        ByteBuffer result = ByteBuffer.allocate(msgLength);
        result.putShort(type.getValue());
        result.putShort((short)(msgLength-20));
        result.putInt(magicCookie);
        result.put(transactionId);
        for (Attribute attr : attributes) {
            result.put(attr.getByte());
        }
        return result.array();
    }

}