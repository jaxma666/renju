package com.toys.renju.service.message;

import com.toys.renju.service.IPushCenter;
import com.toys.renju.service.IUserSessionCenter;
import com.toys.renju.service.protocol.SimpleProtocol;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.Resource;

/**
 * Created by lingyao on 16/5/25.
 */

@Service("createUserMessageHandler")
public class CreateUserMessageHandler implements IMessageHandler {
    @Resource
    IUserSessionCenter userSessionCenter;
    @Resource
    IPushCenter pushCenter;

    @Override
    public void handle(WebSocketSession session, String content) {
        //玩家上线
        userSessionCenter.onLine(session, content);
        SimpleProtocol simpleProtocol = new SimpleProtocol();
        simpleProtocol.returnSuccess("welcome_player", "欢迎玩家");
        pushCenter.pushMessage(simpleProtocol, session);
    }
}
