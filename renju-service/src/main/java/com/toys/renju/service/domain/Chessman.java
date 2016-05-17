package com.toys.renju.service.domain;

/**
 * Created by lingyao on 16/5/15.
 */
public class Chessman {
    private Position position;
    // 0表示空 1表示白子 2表示黑子
    private Byte state;
    // 落子的顺序
    private Integer order;

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }


    @Override
    public String toString() {
        return position.toString() + " state: " + state + " order: " + order;
    }
}
