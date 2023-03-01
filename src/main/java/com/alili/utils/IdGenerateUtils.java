package com.alili.utils;

import cn.hutool.core.util.IdUtil;

public class IdGenerateUtils {
    public static void main(String[] args) {
        System.out.println(IdUtil.simpleUUID());
        System.out.println(IdUtil.getSnowflake().nextId());
    }
}
