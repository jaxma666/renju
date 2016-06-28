package com.toys.renju.service;

import com.toys.renju.service.domain.Participants;
import com.toys.renju.service.protocol.SimpleProtocol;
import org.springframework.web.socket.WebSocketSession;

/**
 * Created by lingyao on 16/5/25.
 */
public interface IPushCenter {
    public void pushMessage(SimpleProtocol simpleProtocol, WebSocketSession... receiver);

    public void pushToAllParticipants(SimpleProtocol simpleProtocol, Participants participants);

}


