package com.toys.renju.service.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lingyao on 16/5/15.
 */
public class RenjuGame {
    //标准棋盘大小
    private final Integer boardSize = 15;
    //游戏状态:0代表尚未开始,1代表游戏开始
    public AtomicInteger gameState = new AtomicInteger(0);
    //观战人数列表是否初始化
    public AtomicInteger visitorInit = new AtomicInteger(0);
    //判断当前是该谁走了,默认0是黑色走
    public Boolean blacksTurn = true;
    //参与者
    private Participants participants = new Participants();
    //棋盘
    private List<Chessman> chessBoard = new ArrayList<>();
    //落子的顺序
    private Integer stepOrder = 0;


    public RenjuGame() {
        for (int i = 0; i < boardSize * boardSize; i++) {
            chessBoard.add(null);
        }
    }

    public Participants getParticipants() {
        return participants;
    }

    public void setParticipants(Participants participants) {
        this.participants = participants;
    }

    //核心判断部分
    public String doNextStep(Chessman chessman) {
        return checkAllDirection(chessman);
    }


    public void showCurrentBoard() {
        for (int i = 0; i < boardSize * boardSize; i++) {
            if (i % boardSize == 0) {
                System.out.println();
            }
            if (chessBoard.get(i) == null) {
                System.out.print(0);
            } else {
                System.out.print(chessBoard.get(i).getColor());
            }
        }
    }

    private String checkAllDirection(Chessman chessman) {
        Integer index = chessman.getPosition().getRow() * boardSize + chessman.getPosition().getColumn();
        chessman.setOrder(stepOrder++);
        chessBoard.set(index, chessman);
        if (checkLeft(index, chessman)
                || checkRight(index, chessman)
                || checkUp(index, chessman)
                || checkDown(index, chessman)
                || checkUpLeft(index, chessman)
                || checkUpRight(index, chessman)
                || checkDownLeft(index, chessman)
                || checkDownRight(index, chessman)) {
            return chessman.getColor().toString();
        }
        return "go";
    }


    private Boolean checkLeft(Integer index, Chessman chessman) {
        for (int i = 1; i < 5; i++) {
            if (chessman.getPosition().getColumn() - i < 0) return false;
            if (chessBoard.get(index - i) == null || !chessBoard.get(index - i).getColor().equals(chessman.getColor()))
                return false;
        }
        return true;
    }

    private Boolean checkRight(Integer index, Chessman chessman) {
        for (int i = 1; i < 5; i++) {
            if (chessman.getPosition().getColumn() + i >= boardSize) return false;
            if (chessBoard.get(index + i) == null || !chessBoard.get(index + i).getColor().equals(chessman.getColor()))
                return false;
        }
        return true;
    }

    private Boolean checkUp(Integer index, Chessman chessman) {
        for (int i = 1; i < 5; i++) {
            if (chessman.getPosition().getRow() - i < 0) return false;
            if (chessBoard.get(index - boardSize * i) == null || !chessBoard.get(index - boardSize * i).getColor().equals(chessman.getColor()))
                return false;
        }
        return true;
    }

    private Boolean checkDown(Integer index, Chessman chessman) {
        for (int i = 1; i < 5; i++) {
            if (chessman.getPosition().getRow() + i >= boardSize) return false;
            if (chessBoard.get(index + boardSize * i) == null || !chessBoard.get(index + boardSize * i).getColor().equals(chessman.getColor()))
                return false;
        }
        return true;
    }

    private Boolean checkUpLeft(Integer index, Chessman chessman) {
        for (int i = 1; i < 5; i++) {
            if (chessman.getPosition().getRow() - i < 0 || chessman.getPosition().getColumn() - i < 0)
                return false;
            if (chessBoard.get(index - (boardSize + 1) * i) == null || !chessBoard.get(index - (boardSize + 1) * i).getColor().equals(chessman.getColor()))
                return false;
        }
        return true;
    }

    private Boolean checkUpRight(Integer index, Chessman chessman) {
        for (int i = 1; i < 5; i++) {
            if (chessman.getPosition().getRow() - i < 0 || chessman.getPosition().getColumn() + i >= boardSize)
                return false;
            if (chessBoard.get(index - (boardSize - 1) * i) == null || !chessBoard.get(index - (boardSize - 1) * i).getColor().equals(chessman.getColor()))
                return false;
        }
        return true;
    }

    private Boolean checkDownLeft(Integer index, Chessman chessman) {
        for (int i = 1; i < 5; i++) {
            if (chessman.getPosition().getRow() + i >= boardSize || chessman.getPosition().getColumn() - i < 0)
                return false;
            if (chessBoard.get(index + (boardSize - 1) * i) == null || !chessBoard.get(index + (boardSize - 1) * i).getColor().equals(chessman.getColor()))
                return false;
        }
        return true;
    }

    private Boolean checkDownRight(Integer index, Chessman chessman) {
        for (int i = 1; i < 5; i++) {
            if (chessman.getPosition().getRow() + i >= boardSize || chessman.getPosition().getColumn() + i >= boardSize)
                return false;
            if (chessBoard.get(index + (boardSize + 1) * i) == null || !chessBoard.get(index + (boardSize + 1) * i).getColor().equals(chessman.getColor()))
                return false;
        }
        return true;
    }
}
