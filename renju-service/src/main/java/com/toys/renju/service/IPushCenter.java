package com.toys.renju.service;

import com.toys.renju.service.domain.ActionResult;
import com.toys.renju.service.domain.Participants;
import org.springframework.web.socket.WebSocketSession;

/**
 * Created by lingyao on 16/5/25.
 */
public interface IPushCenter {
    public void pushMessage(ActionResult actionResult, WebSocketSession... receiver);

    public void pushToAllParticipants(ActionResult actionResult,Participants participants);

}


