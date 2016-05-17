package com.toys.renju.service.message;

import javax.websocket.Session;

/**
 * Created by lingyao on 16/5/17.
 */
public interface IMessageHandler {
    void handle(Session session);
}
