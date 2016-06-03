package com.toys.renju.service.domain;

import com.alibaba.fastjson.JSON;

/**
 * Created by lingyao on 16/5/15.
 */
public class Chessman {
    private Position position;
    // 0表示空 1表示白子 2表示黑子
    private Byte color;
    // 落子的顺序
    private Integer order;

    public Byte getColor() {
        return color;
    }

    public void setColor(Byte color) {
        this.color = color;
    }

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


    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
