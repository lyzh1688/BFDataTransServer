package com.beifeng.dtuProtocal;

import java.lang.reflect.Type;
import java.util.EnumMap;

/**
 * Created by 胡志洁 on 2016/8/19.
 */
public class DType {
    public static EnumMap<TypeEnum,Byte> TypeMap;

    static {
        TypeMap = new EnumMap<TypeEnum, Byte>(TypeEnum.class);
        TypeMap.put(TypeEnum.ACK, (byte) 0x81);
        TypeMap.put(TypeEnum.FAILED, (byte) 0x84);
        TypeMap.put(TypeEnum.REG,(byte)0x01);
        TypeMap.put(TypeEnum.MSG,(byte)0x09);
    }
}

