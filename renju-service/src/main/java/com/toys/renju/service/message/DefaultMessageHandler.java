package com.toys.renju.service.message;

import com.toys.renju.service.IPushCenter;
import com.toys.renju.service.code.ErrorCode;
import com.toys.renju.service.domain.ActionResult;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.Resource;

/**
 * Created by lingyao on 16/5/17.
 */
@Service("defaultMessageHandler")
public class DefaultMessageHandler implements IMessageHandler {


    @Resource
    IPushCenter pushCenter;

    @Override
    public void handle(WebSocketSession session, String content) {
        ActionResult<String> actionResult = new ActionResult<>();
        actionResult.setErrorCode(ErrorCode.INVILAD_PROTOCOL);
        pushCenter.pushMessage(actionResult, session);
    }
}
