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
@Service("watchGameMessageHandler")

public class WatchGameMessageHandler implements IMessageHandler {
    @Resource
    IRenjuCenter renjuCenter;
    @Resource
    IPushCenter pushCenter;

    @Override
    public void handle(WebSocketSession session, String content) {
        ActionResult<String> actionResult = new ActionResult<>();
        List<RenjuGame> renjuGameList = renjuCenter.getGameList();
        RenjuGame renjuGame = renjuGameList.get(Integer.valueOf(content));
        Boolean result = renjuCenter.watchGame(session, renjuGame);
        if (result) {
            actionResult.setSuccessResult(session.getId() + "watch_game_success");
        } else {
            actionResult.setErrorCode(ErrorCode.WATCH_GAME_FAILED);
        }
        pushCenter.pushToAllParticipants(actionResult, renjuGame.getParticipants());
    }
}
