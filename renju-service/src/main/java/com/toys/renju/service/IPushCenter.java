package com.toys.renju.service;

import org.springframework.web.socket.WebSocketSession;

/**
 * Created by lingyao on 16/5/25.
 */
public interface IPushCenter {
    public void pushMessage(String message, WebSocketSession... receiver);
}


