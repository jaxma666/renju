package com.toys.renju.service;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by lingyao on 16/5/16.
 */
@Service
public class UserSessionCenterImpl implements IUserSessionCenter {

    private Set<WebSocketSession> userSessionSet;

    @Override
    public Set<WebSocketSession> getUserSessionSet() {
        return userSessionSet;
    }

    public void setUserSessionSet(Set<WebSocketSession> userSessionSet) {
        this.userSessionSet = userSessionSet;
    }

    @PostConstruct
    void init() {
        userSessionSet = Collections.newSetFromMap(new ConcurrentHashMap<>());
    }

    @Override
    public void onLine(WebSocketSession session, String userName) {
        session.getAttributes().put("userName", userName);
        userSessionSet.add(session);
    }

    @Override
    public void offLine(WebSocketSession session) {
        userSessionSet.remove(session);
    }

    @Override
    public Integer getUserCout() {
        return userSessionSet.size();
    }
}
