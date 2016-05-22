package com.toys.renju.service;


import org.springframework.web.socket.WebSocketSession;

/**
 * Created by lingyao on 16/5/16.
 */
public interface IUserSessionCenter {
    public void onLine(WebSocketSession session, String userName);

    public void offLine(WebSocketSession session);

    public Integer getUserCout();
}
