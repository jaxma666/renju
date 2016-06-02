package com.toys.renju.service.domain;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lingyao on 16/5/15.
 */
public class RenjuGame {
    //游戏状态:0代表尚未开始,1代表游戏开始
    public AtomicInteger gameState = new AtomicInteger(0);
    public AtomicInteger visitorInit = new AtomicInteger(0);
    private Participants participants = new Participants();
    //棋局基本属性
    private List<Chessman> chessBoard = new ArrayList<>();
    private Integer boardHeight = 10;
    private Integer boardWeight = 10;

    public RenjuGame() {
        initBoard();
    }

    public Participants getParticipants() {
        return participants;
    }

    public void setParticipants(Participants participants) {
        this.participants = participants;
    }

    public void doChessStep() {

    }

    //初始化棋盘
    private void initBoard() {
        for (int i = 0; i < boardHeight * boardWeight; i++) {
            Chessman chessman = new Chessman();
            Position position = new Position();
            position.setRow(i / boardHeight);
            position.setColumn(i % boardWeight);
            chessman.setPosition(position);
            chessman.setState((byte) 0);
            chessBoard.add(chessman);
        }
    }

    public void showCurrentBoard() {
        for (int i = 0; i < boardHeight * boardWeight; i++) {
            if (i % boardHeight == 0) {
                System.out.println();
            }
            System.out.print(chessBoard.get(i).getState());
        }
    }

    Boolean checkWhosTurn() {
        return false;
    }
}
