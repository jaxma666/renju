package com.toys.renju.service.domain;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

/**
 * Created by lingyao on 16/6/3.
 */
public class ChessmanTest {
    @Test
    public void test() {
        Chessman chessman = new Chessman();
        chessman.setColor((byte) 0);
        chessman.setOrder(1);
        Position position = new Position();
        position.setColumn(11);
        position.setRow(15);
        chessman.setPosition(position);
        System.out.println(JSON.toJSONString(chessman));
    }
}