package com.toys.renju.web;

import com.google.common.collect.Lists;
import com.toys.renju.service.IRenjuCenter;
import com.toys.renju.service.IUserSessionCenter;
import com.toys.renju.service.domain.ActionResult;
import com.toys.renju.service.domain.Participants;
import com.toys.renju.service.domain.RenjuGame;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lingyao on 16/5/31.
 */
@Controller
public class RenjuController {
    @Resource
    IRenjuCenter renjuCenter;

    @Resource
    IUserSessionCenter userSessionCenter;

    @RequestMapping("/getAllGames")
    @ResponseBody
    public ActionResult<List> getAllGames() {
        ActionResult<List> actionResult = new ActionResult<>();
        List<RenjuGame> renjuGameList = renjuCenter.getGameList();
        List<RenjuGameVO> renjuGameVOList = Lists.newArrayList();
        for (RenjuGame each : renjuGameList) {
            renjuGameVOList.add(transDO2VO(each));
        }
        actionResult.setSuccessResult(renjuGameVOList);
        return actionResult;
    }

    @RequestMapping("/getAllUsers")
    @ResponseBody
    public ActionResult<List> getAllUsers() {
        ActionResult<List> actionResult = new ActionResult<>();
        List<String> userNameList = Lists.newArrayList();
        Set<WebSocketSession> webSocketSessionSet = userSessionCenter.getUserSessionSet();
        for (WebSocketSession each : webSocketSessionSet) {
            userNameList.add((String) each.getAttributes().get("userName"));
        }
        actionResult.setSuccessResult(userNameList);
        return actionResult;
    }

    private RenjuGameVO transDO2VO(RenjuGame renjuGame) {
        RenjuGameVO renjuGameVO = new RenjuGameVO();
        BeanUtils.copyProperties(renjuGame, renjuGameVO);
        Participants participants = renjuGame.getParticipants();
        renjuGameVO.setCreator((String) participants.getCreator().getAttributes().get("userName"));
        if (participants.getJoiner() != null) {
            renjuGameVO.setJoiner((String) participants.getJoiner().getAttributes().get("userName"));
        } else {
            renjuGameVO.setJoiner("");
        }
        List<WebSocketSession> visitor = participants.getVisitor();
        List<String> visitorNameList = Lists.newArrayList();
        if (visitor != null) {
            for (WebSocketSession each : visitor) {
                visitorNameList.add((String) each.getAttributes().get("userName"));
            }
        }
        renjuGameVO.setVisitors(visitorNameList);
        return renjuGameVO;
    }

    private static class RenjuGameVO {
        AtomicInteger gameState;
        String creator;
        String joiner;
        List<String> visitors;

        public List<String> getVisitors() {
            return visitors;
        }

        public void setVisitors(List<String> visitors) {
            this.visitors = visitors;
        }

        public AtomicInteger getGameState() {
            return gameState;
        }

        public void setGameState(AtomicInteger gameState) {
            this.gameState = gameState;
        }

        public String getCreator() {
            return creator;
        }

        public void setCreator(String creator) {
            this.creator = creator;
        }

        public String getJoiner() {
            return joiner;
        }

        public void setJoiner(String joiner) {
            this.joiner = joiner;
        }
    }
}
