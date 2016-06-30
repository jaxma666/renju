package com.toys.renju.service.message;

import com.toys.renju.service.IPushCenter;
import com.toys.renju.service.IRenjuCenter;
import com.toys.renju.service.IUserSessionCenter;
import com.toys.renju.service.domain.Participants;
import com.toys.renju.service.domain.RenjuGame;
import com.toys.renju.service.protocol.SimpleProtocol;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.Resource;

/**
 * Created by lingyao on 16/6/1.
 */

@Service("leftGameMessageHandler")
public class LeftGameMessageHandler implements IMessageHandler {
    @Resource
    IRenjuCenter renjuCenter;
    @Resource
    IPushCenter pushCenter;
    @Resource
    IUserSessionCenter userSessionCenter;

    @Override
    public void handle(WebSocketSession session, String content) {
        RenjuGame renjuGame = (RenjuGame) session.getAttributes().get(session.getId());
        Participants participants = renjuGame.getParticipants();
        //清空session和游戏棋局的绑定
        renjuCenter.leftGame(session);
        SimpleProtocol simpleProtocol = new SimpleProtocol();
        simpleProtocol.returnSuccess("one_player_left", "玩家" + userSessionCenter.getUserName(session) + "离开了游戏,游戏结束");
        //通知所有的listener
        pushCenter.pushToAllParticipants(simpleProtocol, participants);
    }
}
