package com.toys.renju.service;

import com.toys.renju.service.domain.RenjuGame;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;


/**
 * Created by lingyao on 16/5/17.
 */
public interface IRenjuCenter {
    public List<RenjuGame> getGameList();

    public void createGame(WebSocketSession creator);

    public void joinGame(WebSocketSession creator, WebSocketSession joiner);

    public void watchGame(WebSocketSession visitor, RenjuGame renjuGame);

    public void endGame(WebSocketSession oneUser);

    public void doStep(WebSocketSession oneUser);

}
