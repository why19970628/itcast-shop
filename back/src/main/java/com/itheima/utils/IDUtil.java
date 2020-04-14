package com.itheima.utils;

import java.util.UUID;

public class IDUtil {

    public static String randId(){
        UUID uuid = UUID.randomUUID();
        String s = uuid.toString();
        s=s.replace("-","");
        return s;
    }
}
