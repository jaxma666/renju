package com.toys.renju.service.domain;

import org.junit.Test;

/**
 * Created by lingyao on 16/6/4.
 */
public class RenjuGameTest {
    @Test
    public void test() {
        RenjuGame renjuGame = new RenjuGame();
        Chessman chessman = new Chessman();
        chessman.setColor((byte) 2);
        Position position = new Position();
        position.setRow(0);

        for (int i = 1; i < 6; i++) {
            position.setColumn(i);
            chessman.setPosition(position);
            System.out.println(renjuGame.doNextStep(chessman));
        }


        renjuGame.showCurrentBoard();
    }

}