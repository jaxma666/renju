package com.toys.renju.service;


import org.springframework.web.socket.WebSocketSession;

import java.util.Set;

/**
 * Created by lingyao on 16/5/16.
 */
public interface IUserSessionCenter {
    void onLine(WebSocketSession session, String userName);

    void offLine(WebSocketSession session);

    Set<WebSocketSession> getUserSessionSet();

    Integer getUserCout();
}
