package com.toys.renju.service.util;

import com.toys.renju.service.RenjuCenterImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;


/**
 * Created by lingyao on 16/5/19.
 */
public class MessageUtil {
    private static final Logger logger = LoggerFactory.getLogger(RenjuCenterImpl.class);

    public static void sendMessage(String message, WebSocketSession... receiver) {
        TextMessage textMessage = new TextMessage(message);
        try {
            for (WebSocketSession each : receiver) {
                each.sendMessage(textMessage);
            }
        } catch (Exception e) {
            logger.error("发消息异常:" + e.getMessage(), e);
        }
    }

}
