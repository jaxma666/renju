package com.toys.renju.service;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by lingyao on 16/5/16.
 */
@Service
public class UserSessionCenterImpl implements IUserSessionCenter {

    private ConcurrentHashMap<WebSocketSession, String> userMap;

    @Override
    public ConcurrentHashMap<WebSocketSession, String> getUserMap() {
        return this.userMap;
    }

    public void setUserMap(ConcurrentHashMap<WebSocketSession, String> userMap) {
        this.userMap = userMap;
    }

    @PostConstruct
    void init() {
        userMap = new ConcurrentHashMap<>();
    }

    @Override
    public void onLine(WebSocketSession session, String userName) {
        userMap.put(session, userName);
    }

    @Override
    public void offLine(WebSocketSession session) {
        userMap.remove(session);
    }

    @Override
    public Integer getUserCout() {
        return userMap.size();
    }
}
