package com.toys.renju.service.message;

import org.springframework.web.socket.WebSocketSession;


/**
 * Created by lingyao on 16/5/17.
 */
public interface IMessageHandler {
    void handle(WebSocketSession session, String content);
}
