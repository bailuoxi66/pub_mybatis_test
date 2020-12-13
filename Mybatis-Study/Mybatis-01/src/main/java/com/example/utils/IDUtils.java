package com.example.utils;


import org.junit.jupiter.api.Test;

import java.util.UUID;

/**
 * @author <a href="mailto:liangzhengtao.lzt@lazada.com">liangzhengtao.lzt</a>
 * @version 1.0
 * @date 2020/12/13 11:23 上午
 * @desc
 */
public class IDUtils {

    public static String getId(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    @Test
    public void test(){
        System.out.println(IDUtils.getId());
    }
}
