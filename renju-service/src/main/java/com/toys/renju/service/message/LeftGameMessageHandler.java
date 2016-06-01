package com.toys.renju.service.message;

import com.toys.renju.service.IPushCenter;
import com.toys.renju.service.IRenjuCenter;
import com.toys.renju.service.domain.ActionResult;
import com.toys.renju.service.domain.Participants;
import com.toys.renju.service.domain.RenjuGame;
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

    @Override
    public void handle(WebSocketSession session, String content) {
        ActionResult<String> actionResult = new ActionResult<>();
        RenjuGame renjuGame = (RenjuGame) session.getAttributes().get(session.getId());
        Participants participants = renjuGame.getParticipants();
        //清空session和游戏棋局的绑定
        renjuCenter.leftGame(session);
        actionResult.setSuccessResult("one_player_left");
        //通知所有的listener
        pushCenter.pushToAllParticipants(actionResult, participants);
    }
}
