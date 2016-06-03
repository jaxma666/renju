package com.toys.renju.service.code;

/**
 * Created by lingyao on 16/5/26.
 */
public enum ErrorCode {

    ERROR_PROTOCOL_FORMAT(101, "protocol format error"),
    INVILAD_PROTOCOL(102, "protocol error"),
    JOIN_GAME_FAILED(201, "join game failed"),
    WATCH_GAME_FAILED(202, "watch game failed"),
    LEFT_GAME_FAILED(203, "left game failed"),
    INVILAD_CHESSMAN_PROTOCOL(301, "chess man parse error"),
    TURN_ERROR(302, "not your true");
    private int code;
    private String defaultMsg;


    ErrorCode(int code, String defaultMsg) {
        this.code = code;
        this.defaultMsg = defaultMsg;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDefaultMsg() {
        return defaultMsg;
    }

    public void setDefaultMsg(String defaultMsg) {
        this.defaultMsg = defaultMsg;
    }
}
