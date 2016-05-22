package com.toys.renju.service;

import com.toys.renju.service.domain.Participants;
import com.toys.renju.service.domain.RenjuGame;
import com.toys.renju.service.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketSession;

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
    public void createGame(WebSocketSession creator) {
        RenjuGame renjuGame = new RenjuGame();
        renjuGame.getParticipants().setCreator(creator);

        creator.getAttributes().put(creator.getId(), renjuGame);
        renjuGameList.add(renjuGame);
    }

    @Override
    public void joinGame(WebSocketSession creator, WebSocketSession joiner) {
        RenjuGame renjuGame = (RenjuGame) creator.getAttributes().get(creator.getId());
        if (renjuGame.gameState.compareAndSet(0, 1)) {
            joiner.getAttributes().put(joiner.getId(), renjuGame);
            renjuGame.getParticipants().setJoiner(joiner);
        } else {
            MessageUtil.sendMessage("加入游戏失败,请再选一个对手", joiner);
        }
    }

    @Override
    public void watchGame(WebSocketSession visitor, RenjuGame renjuGame) {
        if (renjuGame.visitorInit.compareAndSet(0, 1)) {
            List<WebSocketSession> visitorList = new ArrayList<>();
            visitorList.add(visitor);
            renjuGame.getParticipants().setVisitor(visitorList);
        } else {
            List<WebSocketSession> visitorList = renjuGame.getParticipants().getVisitor();
            visitorList.add(visitor);
            renjuGame.getParticipants().setVisitor(visitorList);
        }
    }

    @Override
    public void endGame(WebSocketSession oneUser) {
        RenjuGame renjuGame = (RenjuGame) oneUser.getAttributes().get(oneUser.getId());
        Participants participants = renjuGame.getParticipants();
        MessageUtil.sendMessage("游戏结束了!", participants.getJoiner());
        MessageUtil.sendMessage("游戏结束了!", participants.getCreator());
        List<WebSocketSession> visitorList = participants.getVisitor();
        for (WebSocketSession each : visitorList) {
            MessageUtil.sendMessage("游戏结束了!", each);
        }
        renjuGameList.remove(renjuGame);
    }

    @Override
    public void doStep(WebSocketSession oneUser) {
        //游戏业务逻辑
    }

}
