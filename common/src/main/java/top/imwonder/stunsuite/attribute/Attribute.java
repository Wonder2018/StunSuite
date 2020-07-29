package top.imwonder.stunsuite.attribute;

import java.nio.ByteBuffer;
import java.util.Arrays;

import lombok.Getter;

@Getter
public abstract class Attribute {
    protected final StunAttribute type;
    protected int length;
    protected byte[] value;

    public Attribute(short type){
        this.type = StunAttribute.queryAttribute(type);
    }

    public int length(){
        return length + 4;
    }

    public void setValue(byte[] value) {
        this.length = (short) ((value.length + (4 - 1)) & ~(4 - 1));
        if (this.length == value.length) {
            this.value = value;
        }else{
            this.value = new byte[this.length];
            Arrays.fill(this.value, (byte)0);
            System.arraycopy(value, 0, this.value, this.length-value.length, value.length);
        }
    }

    public abstract ByteBuffer getByte();
    // {
    //     ByteBuffer result = ByteBuffer.allocate(8 + length);
    //     result.putShort(type);
    //     result.putShort(length);
    //     result.put(value);
    //     return null;
    // }
    
}