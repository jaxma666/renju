package com.toys.renju.service;

import com.alibaba.fastjson.JSON;
import com.toys.renju.service.domain.Participants;
import com.toys.renju.service.protocol.SimpleProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * Created by lingyao on 16/5/25.
 */

@Service
public class PushCenterImpl implements IPushCenter {
    private static final Logger logger = LoggerFactory.getLogger(PushCenterImpl.class);

    @Override
    public void pushMessage(SimpleProtocol simpleProtocol, WebSocketSession... receiver) {
        TextMessage textMessage = new TextMessage(JSON.toJSONString(simpleProtocol));
        try {
            for (WebSocketSession each : receiver) {
                each.sendMessage(textMessage);
            }
        } catch (Exception e) {
            logger.error("发消息异常:" + e.getMessage(), e);
        }
    }


    @Override
    public void pushToAllParticipants(SimpleProtocol simpleProtocol, Participants participants) {
        TextMessage textMessage = new TextMessage(JSON.toJSONString(simpleProtocol));
        try {
            participants.getCreator().sendMessage(textMessage);
            participants.getJoiner().sendMessage(textMessage);
            for (WebSocketSession each : participants.getVisitor()) {
                each.sendMessage(textMessage);
            }
        } catch (Exception e) {
            logger.error("发消息异常:" + e.getMessage(), e);
        }
    }
}
