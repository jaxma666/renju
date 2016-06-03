package com.toys.renju.service;

import com.toys.renju.service.domain.Chessman;
import com.toys.renju.service.domain.Participants;
import com.toys.renju.service.domain.RenjuGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by lingyao on 16/5/17.
 */
@Service
public class RenjuCenterImpl implements IRenjuCenter {
    private static final Logger logger = LoggerFactory.getLogger(RenjuCenterImpl.class);
    @Resource
    IPushCenter pushCenter;

    private List<RenjuGame> renjuGameList = Collections.synchronizedList(new ArrayList<>());

    @Override
    public List<RenjuGame> getGameList() {
        return renjuGameList;
    }

    @Override
    public void createGame(WebSocketSession creator) {
        RenjuGame renjuGame = new RenjuGame();
        renjuGame.getParticipants().setCreator(creator);
        creator.getAttributes().put(creator.getId(), renjuGame);
        renjuGameList.add(renjuGame);
    }

    @Override
    public Boolean joinGame(WebSocketSession joiner, RenjuGame renjuGame) {
        if (renjuGame.gameState.compareAndSet(0, 1)) {
            joiner.getAttributes().put(joiner.getId(), renjuGame);
            renjuGame.getParticipants().setJoiner(joiner);
            return true;
        }
        return false;
    }

    @Override
    public Boolean watchGame(WebSocketSession visitor, RenjuGame renjuGame) {
        if (renjuGame.visitorInit.compareAndSet(0, 1)) {
            List<WebSocketSession> visitorList = new ArrayList<>();
            visitorList.add(visitor);
            renjuGame.getParticipants().setVisitor(visitorList);
        } else {
            List<WebSocketSession> visitorList = renjuGame.getParticipants().getVisitor();
            visitorList.add(visitor);
            renjuGame.getParticipants().setVisitor(visitorList);
        }
        return true;
    }

    @Override
    public void leftGame(WebSocketSession oneUser) {
        RenjuGame renjuGame = (RenjuGame) oneUser.getAttributes().get(oneUser.getId());
        Participants participants = renjuGame.getParticipants();
        participants.getCreator().getAttributes().clear();
        participants.getJoiner().getAttributes().clear();
        List<WebSocketSession> visitorList = participants.getVisitor();
        for (WebSocketSession each : visitorList) {
            each.getAttributes().clear();
        }
        renjuGameList.remove(renjuGame);
    }

    @Override
    public String doStep(RenjuGame renjuGame, Chessman chessman) {
        return renjuGame.doNextStep(chessman);
    }
}

