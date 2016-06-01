package com.toys.renju.service.message;

import com.toys.renju.service.IPushCenter;
import com.toys.renju.service.IRenjuCenter;
import com.toys.renju.service.code.ErrorCode;
import com.toys.renju.service.domain.ActionResult;
import com.toys.renju.service.domain.RenjuGame;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.Resource;
import java.util.List;

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
        List<RenjuGame> renjuGameList = renjuCenter.getGameList();
        RenjuGame renjuGame = renjuGameList.get(Integer.valueOf(content));
        if (renjuGame == null) {
            actionResult.setErrorCode(ErrorCode.JOIN_GAME_FAILED);
            pushCenter.pushMessage(actionResult, session);
            return;
        }
        Boolean result = renjuCenter.joinGame(session, renjuGame);
        if (result) {
            actionResult.setSuccessResult("join_game_success");
        } else {
            actionResult.setErrorCode(ErrorCode.JOIN_GAME_FAILED);
        }
        //通知所有的listener
        pushCenter.pushToAllParticipants(actionResult, renjuGame.getParticipants());
    }
}
