package com.toys.renju.service.message;

import com.alibaba.fastjson.JSON;
import com.toys.renju.service.IPushCenter;
import com.toys.renju.service.IRenjuCenter;
import com.toys.renju.service.IUserSessionCenter;
import com.toys.renju.service.code.ErrorCode;
import com.toys.renju.service.domain.ActionResult;
import com.toys.renju.service.domain.Chessman;
import com.toys.renju.service.domain.RenjuGame;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.Resource;

/**
 * Created by lingyao on 16/6/3.
 */
public class DoStepMessageHandler implements IMessageHandler {

    @Resource
    IPushCenter pushCenter;
    @Resource
    IUserSessionCenter userSessionCenter;

    @Resource
    IRenjuCenter renjuCenter;

    @Override
    public void handle(WebSocketSession session, String content) {
        ActionResult<String> actionResult = new ActionResult<>();
        Chessman chessman = JSON.parseObject(content, Chessman.class);
        if (chessman == null) {
            actionResult.setErrorCode(ErrorCode.INVILAD_CHESSMAN_PROTOCOL);
            pushCenter.pushMessage(actionResult, session);
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
            actionResult.setErrorCode(ErrorCode.TURN_ERROR);
            pushCenter.pushMessage(actionResult, session);
            return;
        }
        actionResult.setSuccessResult(result);
        pushCenter.pushToAllParticipants(actionResult, renjuGame.getParticipants());
    }
}
