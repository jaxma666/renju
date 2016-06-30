package com.toys.renju.service.message;

import com.alibaba.fastjson.JSON;
import com.toys.renju.service.IPushCenter;
import com.toys.renju.service.IRenjuCenter;
import com.toys.renju.service.IUserSessionCenter;
import com.toys.renju.service.domain.Chessman;
import com.toys.renju.service.domain.RenjuGame;
import com.toys.renju.service.protocol.SimpleProtocol;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.Resource;

/**
 * Created by lingyao on 16/6/3.
 */

@Service("doStepMessageHandler")
public class DoStepMessageHandler implements IMessageHandler {

    @Resource
    IPushCenter pushCenter;
    @Resource
    IUserSessionCenter userSessionCenter;
    @Resource
    IRenjuCenter renjuCenter;

    @Override
    public void handle(WebSocketSession session, String content) {
        Chessman chessman = JSON.parseObject(content, Chessman.class);
        SimpleProtocol simpleProtocol = new SimpleProtocol();
        if (chessman == null) {
            simpleProtocol.returnError("chess_man_parse_error", "五子棋协议解析失败");
            pushCenter.pushMessage(simpleProtocol, session);
            return;
        }
        RenjuGame renjuGame = (RenjuGame) session.getAttributes().get(session.getId());
        if (renjuGame.gameState.intValue() == 0) {
            simpleProtocol.returnSuccess("turn_error", "游戏尚未开始,请稍等");
            pushCenter.pushMessage(simpleProtocol, session);
            return;
        }
        String result;
        if (renjuGame.blacksTurn && renjuGame.getParticipants().getCreator().getId().equals(session.getId())) {
            //do biz
            result = renjuCenter.doStep(renjuGame, chessman);
            renjuGame.blacksTurn = false;

        } else if (!renjuGame.blacksTurn && renjuGame.getParticipants().getJoiner().getId().equals(session.getId())) {
            //do biz
            result = renjuCenter.doStep(renjuGame, chessman);
            renjuGame.blacksTurn = true;
        } else {
            simpleProtocol.returnSuccess("turn_error", "还未轮到你喔");
            pushCenter.pushMessage(simpleProtocol, session);
            return;
        }
        simpleProtocol.returnSuccess(result, JSON.toJSONString(chessman));
        pushCenter.pushToAllParticipants(simpleProtocol, renjuGame.getParticipants());
        if (!result.equals("game_go_on")) {
            renjuCenter.endGame(session.getId());
        }
    }
}
