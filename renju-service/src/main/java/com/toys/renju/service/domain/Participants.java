package com.toys.renju.service.domain;

import javax.websocket.Session;
import java.util.List;

/**
 * Created by lingyao on 16/5/18.
 */
public class Participants {
    Session creator;
    Session joiner;
    List<Session> visitor;

    public List<Session> getVisitor() {
        return visitor;
    }

    public void setVisitor(List<Session> visitor) {
        this.visitor = visitor;
    }

    public Session getCreator() {
        return creator;
    }

    public void setCreator(Session creator) {
        this.creator = creator;
    }

    public Session getJoiner() {
        return joiner;
    }

    public void setJoiner(Session joiner) {
        this.joiner = joiner;
    }
}
