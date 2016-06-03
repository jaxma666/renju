package com.toys.renju.service;

import com.toys.renju.service.domain.Chessman;
import com.toys.renju.service.domain.RenjuGame;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;


/**
 * Created by lingyao on 16/5/17.
 */
public interface IRenjuCenter {
    //全局的棋局信息
    public List<RenjuGame> getGameList();

    //创建一局棋,并把session关联到棋局id
    public void createGame(WebSocketSession creator);

    //加入一个棋局,把session关联到棋局id
    public Boolean joinGame(WebSocketSession joiner, RenjuGame renjuGame);

    public Boolean watchGame(WebSocketSession visitor, RenjuGame renjuGame);

    public void leftGame(WebSocketSession oneUser);

    public String doStep(RenjuGame renjuGame, Chessman chessman);

}
