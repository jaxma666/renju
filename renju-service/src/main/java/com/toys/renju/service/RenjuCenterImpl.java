package com.toys.renju.service;

import com.toys.renju.service.domain.Participants;
import com.toys.renju.service.domain.RenjuGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lingyao on 16/5/17.
 */
public class RenjuCenterImpl implements IRenjuCenter {
    private static final Logger logger = LoggerFactory.getLogger(RenjuCenterImpl.class);


    private List<RenjuGame> renjuGameList = new ArrayList<>();

    @Override
    public List<RenjuGame> getGameList() {
        return renjuGameList;
    }

    @Override
    public void createGame(Session creator) {
        RenjuGame renjuGame = new RenjuGame();
        renjuGame.getParticipants().setCreator(creator);
        creator.getUserProperties().put(creator.getId(), renjuGame);
        renjuGameList.add(renjuGame);
    }

    @Override
    public void joinGame(Session creator, Session joiner) {
        RenjuGame renjuGame = (RenjuGame) creator.getUserProperties().get(creator.getId());
        if (renjuGame.gameState.compareAndSet(0, 1)) {
            joiner.getUserProperties().put(joiner.getId(), renjuGame);
            renjuGame.getParticipants().setJoiner(joiner);
        } else {
            sendMessage(joiner, "加入游戏失败,请再选一个对手");
        }
    }

    @Override
    public void watchGame(Session visitor, RenjuGame renjuGame) {
        if (renjuGame.visitorInit.compareAndSet(0, 1)) {
            List<Session> visitorList = new ArrayList<>();
            visitorList.add(visitor);
            renjuGame.getParticipants().setVisitor(visitorList);
        } else {
            List<Session> visitorList = renjuGame.getParticipants().getVisitor();
            visitorList.add(visitor);
            renjuGame.getParticipants().setVisitor(visitorList);
        }
    }

    @Override
    public void endGame(Session oneUser) {
        RenjuGame renjuGame = (RenjuGame) oneUser.getUserProperties().get(oneUser.getId());
        Participants participants = renjuGame.getParticipants();
        sendMessage(participants.getCreator(), "游戏结束了!");
        sendMessage(participants.getJoiner(), "游戏结束了!");
        List<Session> visitorList = participants.getVisitor();
        for (Session each : visitorList) {
            sendMessage(each, "游戏结束了!");
        }
        renjuGameList.remove(renjuGame);
    }

    @Override
    public void doStep(Session oneUser) {
        //游戏业务逻辑
    }

    private void sendMessage(Session receiver, String message) {
        try {
            receiver.getBasicRemote().sendText(message);
        } catch (IOException e) {
            logger.error("io exception while send text to " + receiver.getId());
        }
    }
}
