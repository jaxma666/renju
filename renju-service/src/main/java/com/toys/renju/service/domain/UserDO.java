package com.toys.renju.service.domain;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.web.socket.WebSocketSession;

/**
 * Created by lingyao on 16/6/2.
 */
public class UserDO {
    String userName;

    @JSONField(serialize = false)
    WebSocketSession socketSession;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public WebSocketSession getSocketSession() {
        return socketSession;
    }

    public void setSocketSession(WebSocketSession socketSession) {
        this.socketSession = socketSession;
    }
}
