package com.toys.renju.service.message;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

/**
 * Created by lingyao on 16/5/17.
 */
@Service("defaultMessageHandler")
public class DefaultMessageHandler implements IMessageHandler {
    @Override
    public void handle(WebSocketSession session) {
//        try {
//            session.getBasicRemote().sendText("hehe");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
