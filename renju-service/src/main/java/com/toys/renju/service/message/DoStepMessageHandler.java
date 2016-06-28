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
            simpleProtocol.returnError("turn_error", "还未轮到你喔");
            pushCenter.pushMessage(simpleProtocol, session);
            return;
        }
        switch (result) {
            case "game_go_on":
                simpleProtocol.returnSuccess(result, JSON.toJSONString(chessman));
                break;
            case "creator_win":
                simpleProtocol.returnSuccess(result, "黑方胜!");
                break;
            default:
                simpleProtocol.returnSuccess(result, "白方胜!");
                break;
        }
        pushCenter.pushToAllParticipants(simpleProtocol, renjuGame.getParticipants());
    }
}
