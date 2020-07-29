package top.imwonder.stunsuite.attribute.stunattribute;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import top.imwonder.stunsuite.attribute.Attribute;

@Data
@Slf4j
@EqualsAndHashCode(callSuper = false)
public class AddressAttribute extends Attribute {

    public static final short ADDRESS_FAMILY_IPV4 = 0x0001;
    public static final short ADDRESS_FAMILY_IPV6 = 0x0002;

    public static final short TYPE_MAPPED_ADDRESS = 0x0001;
    public static final short TYPE_RESPONSE_ADDRESS = 0x0002;
    public static final short TYPE_SOURCE_ADDRESS = 0x0004;
    public static final short TYPE_CHANGED_ADDRESS = 0x0005;
    public static final short TYPE_RESPONSE_ORIGIN = (short)0x802b;
    public static final short TYPE_OTHER_ADDRESS = (short)0x802c;



    protected short addressFamily;
    protected int port;
    protected byte[] address;

    public AddressAttribute(short type) {
        super(type);
    }

    public AddressAttribute(short type, InetAddress addr) {
        super(type);
        this.address = addr.getAddress();
        if (this.address.length == 4) {
            this.addressFamily = ADDRESS_FAMILY_IPV4;
        } else {
            this.addressFamily = ADDRESS_FAMILY_IPV6;
        }
    }

    public AddressAttribute(short type, int port) {
        super(type);
        this.port = port;
    }

    public AddressAttribute(short type, int port, InetAddress addr) {
        super(type);
        this.port = port;
        this.address = addr.getAddress();
        if (this.address.length == 4) {
            this.addressFamily = ADDRESS_FAMILY_IPV4;
        } else {
            this.addressFamily = ADDRESS_FAMILY_IPV6;
        }
    }

    public AddressAttribute(short type, byte[] packet) {
        super(type);
        ByteBuffer buf = ByteBuffer.wrap(packet);
        buf.position(2);
        length = buf.getShort() & 0xffff;
        addressFamily = buf.getShort();
        port = buf.getShort() & 0xffff;
        address = new byte[length - 4];
        buf.get(address, 0, address.length);
    }

    public void setAddress(byte[] address) {
        this.address = address;
        if (this.address.length == 4) {
            this.addressFamily = ADDRESS_FAMILY_IPV4;
        } else {
            this.addressFamily = ADDRESS_FAMILY_IPV6;
        }
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
        ByteBuffer buf = ByteBuffer.allocate(length + 4);
        buf.putShort(type.getType());
        buf.putShort((short)length);
        buf.putInt(port);
        buf.position(buf.position() - 4);
        buf.putShort(addressFamily);
        buf.position(buf.position() + 2);
        buf.put(address);
        return buf;
    }

    public String getHostAddress() {
        InetAddress inetAddress;
        try {
            inetAddress = InetAddress.getByAddress(address);
        } catch (UnknownHostException e) {
            log.warn("unknow host! \nraw data: {}:{}:{}:{} - {}:{}:{}:{} - {}:{}:{}:{} - {}:{}:{}:{}",address);
            return "UnknowHost";
        }
        return inetAddress.getHostAddress();
    }

    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append(type.name());

        sb.append("\n\t");
        sb.append("AddressFamily: ");
        if(addressFamily == ADDRESS_FAMILY_IPV4){
            sb.append("IPV4");
        }else{
            sb.append("IPV6");
        }

        sb.append("\n\t");
        sb.append("host: ");
        sb.append(getHostAddress());

        sb.append("\n\t");
        sb.append("port: ");
        sb.append(port);
        
        sb.append("\n");
        return sb.toString();
    }

}