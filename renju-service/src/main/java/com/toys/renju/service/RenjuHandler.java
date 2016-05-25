package com.toys.renju.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * Created by lingyao on 16/5/21.
 */
public class RenjuHandler extends TextWebSocketHandler {
    private static final Logger logger = LoggerFactory.getLogger(RenjuHandler.class);


    @Resource
    IUserSessionCenter userSessionCenter;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        userSessionCenter.onLine(session, "hehe");
        TextMessage message = new TextMessage("欢迎");
        logger.error(session.getId());
        session.getAttributes();
        session.sendMessage(message);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        TextMessage msg = new TextMessage("Hello, " + message.getPayload() + "!");
        try {
            session.sendMessage(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    }
}