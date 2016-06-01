package com.toys.renju.service;

import com.toys.renju.service.domain.UserDO;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by lingyao on 16/5/16.
 */
@Service
public class UserSessionCenterImpl implements IUserSessionCenter {

    private ConcurrentHashMap<String, UserDO> userMap;

    @Override
    public ConcurrentHashMap<String, UserDO> getUserMap() {
        return userMap;
    }

    public void setUserMap(ConcurrentHashMap<String, UserDO> userMap) {
        this.userMap = userMap;
    }

    @PostConstruct
    void init() {
        userMap = new ConcurrentHashMap<>();
    }

    @Override
    public void onLine(WebSocketSession session, String userName) {
        UserDO userDO = new UserDO();
        userDO.setUserName(userName);
        userDO.setSocketSession(session);
        userMap.put(session.getId(), userDO);
    }

    @Override
    public void offLine(WebSocketSession session) {
        userMap.remove(session.getId());
    }

    @Override
    public Integer getUserCout() {
        return userMap.size();
    }
}
