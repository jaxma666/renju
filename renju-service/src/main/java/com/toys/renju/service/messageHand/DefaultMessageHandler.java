package com.toys.renju.service.messageHand;

import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.io.IOException;

/**
 * Created by lingyao on 16/5/17.
 */
@Service("defaultMessageHandler")
public class DefaultMessageHandler implements IMessageHandler {
    @Override
    public void handle(Session session) {
        try {
            session.getBasicRemote().sendText("hehe");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
