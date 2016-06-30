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
    List<RenjuGame> getGameList();

    //创建一局棋,并把session关联到棋局id
    Integer createGame(WebSocketSession creator);

    //加入一个棋局,把session关联到棋局id
    Boolean joinGame(WebSocketSession joiner, RenjuGame renjuGame);

    Boolean watchGame(WebSocketSession visitor, RenjuGame renjuGame);

    void leftGame(WebSocketSession oneUser);

    String doStep(RenjuGame renjuGame, Chessman chessman);

    void endGame(String index);
}
