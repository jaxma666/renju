package com.toys.renju.service.domain;

/**
 * Created by lingyao on 16/5/15.
 */
public class Position {
    private Integer row;
    private Integer column;

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    @Override
    public String toString() {
        return "pos: x:" + row + " y:" + column;
    }
}
