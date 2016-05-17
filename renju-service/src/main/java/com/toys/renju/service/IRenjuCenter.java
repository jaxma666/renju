package com.toys.renju.service;

import com.toys.renju.service.domain.RenjuGame;

import javax.websocket.Session;
import java.util.List;

/**
 * Created by lingyao on 16/5/17.
 */
public interface IRenjuCenter {
    public List<RenjuGame> getGameList();

    public void createGame(Session creator);

    public void joinGame(Session creator, Session joiner);

    public void watchGame(Session visitor ,RenjuGame renjuGame);

    public void endGame(Session oneUser);

    public void doStep(Session oneUser);

}
