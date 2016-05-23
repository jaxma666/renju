package com.toys.renju.service.domain;

import org.springframework.web.socket.WebSocketSession;

import java.util.List;

/**
 * Created by lingyao on 16/5/18.
 */
public class Participants {
    WebSocketSession creator;
    WebSocketSession joiner;
    List<WebSocketSession> visitor;

    public List<WebSocketSession> getVisitor() {
        return visitor;
    }

    public void setVisitor(List<WebSocketSession> visitor) {
        this.visitor = visitor;
    }

    public WebSocketSession getCreator() {
        return creator;
    }

    public void setCreator(WebSocketSession creator) {
        this.creator = creator;
    }

    public WebSocketSession getJoiner() {
        return joiner;
    }

    public void setJoiner(WebSocketSession joiner) {
        this.joiner = joiner;
    }
}
