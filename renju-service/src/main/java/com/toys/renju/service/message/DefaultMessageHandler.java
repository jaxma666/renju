package com.toys.renju.service.message;

import com.toys.renju.service.IPushCenter;
import com.toys.renju.service.protocol.SimpleProtocol;
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
        SimpleProtocol simpleProtocol = new SimpleProtocol();
        simpleProtocol.returnError("protocol_parse_error", "协议解析失败");
        pushCenter.pushMessage(simpleProtocol, session);
    }
}
