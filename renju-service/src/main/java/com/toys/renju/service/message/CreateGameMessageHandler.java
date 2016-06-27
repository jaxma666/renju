package com.toys.renju.service.message;

import com.toys.renju.service.IPushCenter;
import com.toys.renju.service.IRenjuCenter;
import com.toys.renju.service.protocol.SimpleProtocol;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.Resource;

/**
 * Created by lingyao on 16/5/27.
 */
@Service("createGameMessageHandler")
public class CreateGameMessageHandler implements IMessageHandler {
    @Resource
    IRenjuCenter renjuCenter;
    @Resource
    IPushCenter pushCenter;

    @Override
    public void handle(WebSocketSession session, String content) {
        renjuCenter.createGame(session);
        SimpleProtocol simpleProtocol = new SimpleProtocol();
        simpleProtocol.returnSuccess("create_game_success", "创建游戏成功");
        pushCenter.pushMessage(simpleProtocol, session);
    }
}
