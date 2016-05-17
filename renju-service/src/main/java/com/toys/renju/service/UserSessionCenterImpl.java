package com.toys.renju.service;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.websocket.Session;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by lingyao on 16/5/16.
 */
@Service
public class UserSessionCenterImpl implements IUserSessionCenter {

    ConcurrentHashMap<Session, String> userMap;

    @PostConstruct
    void init() {
        userMap = new ConcurrentHashMap<>();
    }

    @Override
    public void onLine(Session session, String userName) {
        userMap.put(session, userName);
    }

    @Override
    public void offLine(Session session) {
        userMap.remove(session);
    }

    @Override
    public Integer getUserCout() {
        return userMap.size();
    }
}
