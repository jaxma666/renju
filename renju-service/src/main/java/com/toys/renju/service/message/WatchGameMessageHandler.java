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
@Service("watchGameMessageHandler")

public class WatchGameMessageHandler implements IMessageHandler {
    @Resource
    IRenjuCenter renjuCenter;
    @Resource
    IPushCenter pushCenter;

    @Override
    public void handle(WebSocketSession session, String content) {
        List<RenjuGame> renjuGameList = renjuCenter.getGameList();
        RenjuGame renjuGame = renjuGameList.get(Integer.valueOf(content));
        Boolean result = renjuCenter.watchGame(session, renjuGame);
        SimpleProtocol simpleProtocol = new SimpleProtocol();
        if (result) {
            simpleProtocol.returnSuccess("watch_game_success", "观看游戏成功");
        } else {
            simpleProtocol.returnError("watch_game_failed", "观看游戏失败");
        }
        pushCenter.pushToAllParticipants(simpleProtocol, renjuGame.getParticipants());
    }
}
