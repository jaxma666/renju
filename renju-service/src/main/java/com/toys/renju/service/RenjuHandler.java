package com.toys.renju.service;

import com.alibaba.fastjson.JSON;
import com.toys.renju.service.domain.ApiResult;
import com.toys.renju.service.message.IMessageHandler;
import com.toys.renju.service.message.MessageHandlerFactory;
import com.toys.renju.service.protocol.SimpleProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.Resource;

/**
 * Created by lingyao on 16/5/21.
 */
public class RenjuHandler extends TextWebSocketHandler {
    private static final Logger logger = LoggerFactory.getLogger(RenjuHandler.class);

    @Resource
    IUserSessionCenter userSessionCenter;

    @Resource
    MessageHandlerFactory messageHandlerFactory;

    @Resource
    IPushCenter pushCenter;

    @Resource
    IRenjuCenter renjuCenter;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.error("新用户创建了连接: {}", session.getId());
        TextMessage message = new TextMessage("welcome to the renju world!");
        session.sendMessage(message);
    }

    private SimpleProtocol parseMessage(String message) throws Exception {
        return JSON.parseObject(message, SimpleProtocol.class);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        SimpleProtocol simpleProtocolIn;
        SimpleProtocol simpleProtocolOut = new SimpleProtocol();
        ApiResult<String> apiResult = new ApiResult<>();
        try {
            simpleProtocolIn = parseMessage(message.getPayload());
        } catch (Exception e) {
            logger.error("解析协议失败: message:{}", message, e);
            simpleProtocolOut.returnError("protocol_format_error", "协议解析失败");
            pushCenter.pushMessage(simpleProtocolOut, session);
            return;
        }
        IMessageHandler messageHandler = messageHandlerFactory.getMessageHandler(simpleProtocolIn.getAction());
        if (messageHandler == null) {
            messageHandler = messageHandlerFactory.getMessageHandler("default");
        }
        try {
            messageHandler.handle(session, simpleProtocolIn.getContent());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        logger.error(exception.getMessage(), exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        IMessageHandler messageHandler = messageHandlerFactory.getMessageHandler("left_game");
        messageHandler.handle(session, "");
        renjuCenter.leftGame(session);
        userSessionCenter.offLine(session);
    }
}