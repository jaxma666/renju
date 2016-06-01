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
@Service("watchGameMessageHandler")

public class WatchGameMessageHandler implements IMessageHandler {
    @Resource
    IRenjuCenter renjuCenter;
    @Resource
    IPushCenter pushCenter;

    @Override
    public void handle(WebSocketSession session, String content) {
        ActionResult<String> actionResult = new ActionResult<>();
        Boolean result = renjuCenter.watchGame(session, Integer.valueOf(content));
        if (result) {
            actionResult.setSuccessResult("watch game successfully");
        } else {
            actionResult.setErrorCode(ErrorCode.WATCH_GAME_FAILED);
        }
        pushCenter.pushMessage(actionResult, session);
    }
}
