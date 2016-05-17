package com.toys.renju.service;

import javax.websocket.Session;

/**
 * Created by lingyao on 16/5/16.
 */
public interface IUserSessionCenter {
    public void onLine(Session session, String userName);

    public void offLine(Session session);

    public Integer getUserCout();
}
