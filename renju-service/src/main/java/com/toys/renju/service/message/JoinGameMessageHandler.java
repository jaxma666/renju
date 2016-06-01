package com.toys.renju.service.message;

import com.toys.renju.service.IPushCenter;
import com.toys.renju.service.IRenjuCenter;
import com.toys.renju.service.code.ErrorCode;
import com.toys.renju.service.domain.ActionResult;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.Resource;

/**
 * Created by lingyao on 16/5/27.
 */

@Service("joinGameMessageHandler")
public class JoinGameMessageHandler implements IMessageHandler {
    @Resource
    IRenjuCenter renjuCenter;
    @Resource
    IPushCenter pushCenter;

    @Override
    public void handle(WebSocketSession session, String content) {
        //这里先做的简单一点,如果对手都就位,那么游戏即刻开始,没有准备状态
        ActionResult<String> actionResult = new ActionResult<>();
        Boolean result = renjuCenter.joinGame(session, Integer.valueOf(content));
        if (result) {
            actionResult.setSuccessResult("join game successfully");
        } else {
            actionResult.setErrorCode(ErrorCode.JOIN_GAME_FAILED);
        }
        pushCenter.pushMessage(actionResult, session);
    }
}
