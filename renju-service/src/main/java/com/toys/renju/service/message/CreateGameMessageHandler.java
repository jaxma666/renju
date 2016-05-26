package com.toys.renju.service.message;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

/**
 * Created by lingyao on 16/5/27.
 */
@Service("createGameMessageHandler")
public class CreateGameMessageHandler implements IMessageHandler {
    @Override
    public void handle(WebSocketSession session, String content) {

    }
}
