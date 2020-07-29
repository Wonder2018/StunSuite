package top.imwonder.stunsuite.attribute;

import java.lang.reflect.Constructor;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import top.imwonder.stunsuite.attribute.stunattribute.AddressAttribute;
import top.imwonder.stunsuite.attribute.stunattribute.XORAddressAttribute;

@Slf4j
@Getter
public enum StunAttribute {

    MAPPED_ADDRESS(AddressAttribute.class, AddressAttribute.TYPE_MAPPED_ADDRESS),
    RESPONSE_ADDRESS2(AddressAttribute.class, AddressAttribute.TYPE_RESPONSE_ADDRESS),
    SOURCE_ADDRESS(AddressAttribute.class, AddressAttribute.TYPE_SOURCE_ADDRESS),
    CHANGED_ADDRESS(AddressAttribute.class, AddressAttribute.TYPE_CHANGED_ADDRESS),
    RESPONSE_ORIGIN(AddressAttribute.class, AddressAttribute.TYPE_RESPONSE_ORIGIN),
    OTHER_ADDRESS(AddressAttribute.class, AddressAttribute.TYPE_OTHER_ADDRESS),
    XOR_MAPPED_ADDRESS(XORAddressAttribute.class, XORAddressAttribute.TYPE_XOR_MAPPED_ADDRESS),
    ;
    
    private short type;

    private Class<? extends Attribute> cs;

    private static Map<Short, StunAttribute> stunAttributes;

    static {
        stunAttributes = new HashMap<>();
        for (StunAttribute sa : values()) {
            stunAttributes.put(sa.getType(), sa);
        }
    }

    StunAttribute(Class<? extends Attribute> cs, short type) {
        this.type = type;
        this.cs = cs;
    }

    public static StunAttribute queryAttribute(short type){
        return stunAttributes.get(type);
    }

    public static Attribute parseToAttribute(byte[] packet) {
        ByteBuffer buf = ByteBuffer.wrap(packet);
        short type = buf.getShort();
        Class<? extends Attribute> clzz = queryAttribute(type).getCs();
        try {
            Constructor<? extends Attribute> sct = clzz.getConstructor(short.class, byte[].class);
            return sct.newInstance(type,packet);
        } catch (Exception e) {
            log.warn("can not get an instance of {}", clzz.getName());
            log.debug("something may useful: {}", e.getMessage());
            return null;
        }
    }
}