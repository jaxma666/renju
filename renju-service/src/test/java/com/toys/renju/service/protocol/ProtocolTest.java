package com.toys.renju.service.protocol;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

/**
 * Created by lingyao on 16/5/25.
 */
public class ProtocolTest {
    @Test
    public void test() {
        String a = "{\"action\":\"b\",\"hehe\":\"dd\"}";
        Protocol protocol = JSON.parseObject(a, Protocol.class);
        System.out.println(a);
    }
}