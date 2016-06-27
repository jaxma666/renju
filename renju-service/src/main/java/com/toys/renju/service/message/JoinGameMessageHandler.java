package com.toys.renju.service.message;

import com.toys.renju.service.IPushCenter;
import com.toys.renju.service.IRenjuCenter;
import com.toys.renju.service.domain.RenjuGame;
import com.toys.renju.service.protocol.SimpleProtocol;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lingyao on 16/5/27.
 */

@Service("joinGameMessageHandler")
public class JoinGameMessageHandler implements IMessageHandler {
    @Resource
    IRenjuCenter renjuCenter;
    @Resource
    IPushCenter pushCenter;

    @Override
    public void handle(WebSocketSession session, String content) {
        //这里先做的简单一点,如果对手都就位,那么游戏即刻开始,没有准备状态
        List<RenjuGame> renjuGameList = renjuCenter.getGameList();
        RenjuGame renjuGame = renjuGameList.get(Integer.valueOf(content));
        SimpleProtocol simpleProtocol = new SimpleProtocol();
        if (renjuGame == null) {
            simpleProtocol.returnError("join_game_failed", "加入游戏失败");
            pushCenter.pushMessage(simpleProtocol, session);
            return;
        }
        Boolean result = renjuCenter.joinGame(session, renjuGame);
        if (result) {
            simpleProtocol.returnSuccess("join_game_success", "加入游戏成功");
        } else {
            simpleProtocol.returnError("join_game_failed", "加入游戏失败");
        }
        //通知所有的listener
        pushCenter.pushToAllParticipants(simpleProtocol, renjuGame.getParticipants());
    }
}
