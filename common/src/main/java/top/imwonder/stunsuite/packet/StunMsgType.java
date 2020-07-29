package top.imwonder.stunsuite.packet;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public enum StunMsgType {

    BINDING_REQUEST((short) 0x0001, "绑定请求"), 
    BINDING_SUCCESS_RESPONSE((short) 0x0101, "绑定成功响应"),
    BINDING_ERROR_RESPONSE((short) 0x0111, "绑定错误响应"), 
    SHARED_SECRET_REQUEST((short) 0x0002, "私密请求"),
    SHARED_SECRET_SUCCESS_RESPONSE((short) 0x0102, "私密成功响应"), 
    SHARED_SECRET_ERROR_RESPONSE((short) 0x0112, "私密错误响应");

    private static final Map<Short,StunMsgType> list;

    static{
        list = new HashMap<>();
        for(StunMsgType smt: values()){
            list.put(smt.getValue(), smt);
        }
    }

    private final short value;

    private final String description;

    StunMsgType(short value, String description) {
        this.value = value;
        this.description = description;
    }

    public static StunMsgType queryByCode(short value){
        return list.get(value);
    }
}