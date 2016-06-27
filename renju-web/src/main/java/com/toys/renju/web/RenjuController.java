package com.toys.renju.web;

import com.google.common.collect.Lists;
import com.toys.renju.service.IRenjuCenter;
import com.toys.renju.service.IUserSessionCenter;
import com.toys.renju.service.domain.ApiResult;
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
    public ApiResult<List> getAllGames() {
        ApiResult<List> apiResult = new ApiResult<>();
        List<RenjuGame> renjuGameList = renjuCenter.getGameList();
        List<RenjuGameVO> renjuGameVOList = Lists.newArrayList();
        for (RenjuGame each : renjuGameList) {
            renjuGameVOList.add(transDO2VO(each));
        }
        apiResult.setSuccessResult(renjuGameVOList);
        return apiResult;
    }

    @RequestMapping("/getGameInfo")
    @ResponseBody
    public ApiResult<RenjuGame> getGameInfo(int index) {
        ApiResult<RenjuGame> apiResult = new ApiResult<>();
        List<RenjuGame> renjuGameList = renjuCenter.getGameList();
        apiResult.setSuccessResult(renjuGameList.get(index));
        return apiResult;
    }

    @RequestMapping("/getAllUsers")
    @ResponseBody
    public ApiResult<List> getAllUsers() {
        ApiResult<List> apiResult = new ApiResult<>();
        List<String> userNameList = Lists.newArrayList();
        Set<WebSocketSession> webSocketSessionSet = userSessionCenter.getUserSessionSet();
        for (WebSocketSession each : webSocketSessionSet) {
            userNameList.add((String) each.getAttributes().get("userName"));
        }
        apiResult.setSuccessResult(userNameList);
        return apiResult;
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
        renjuGameVO.setGameState(GameStateVO.detect(renjuGame.gameState).getDesc());
        return renjuGameVO;
    }

    public enum GameStateVO {
        WAITING(0, "等待对手"), PLAYING(1, "游戏中");

        private int code;
        private String desc;

        GameStateVO(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static GameStateVO detect(AtomicInteger atomicInteger) {
            if (atomicInteger.intValue() == 0) {
                return WAITING;
            } else {
                return PLAYING;
            }
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    private static class RenjuGameVO {
        String gameState;
        String creator;
        String joiner;
        List<String> visitors;

        public List<String> getVisitors() {
            return visitors;
        }

        public void setVisitors(List<String> visitors) {
            this.visitors = visitors;
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

        public String getGameState() {
            return gameState;
        }

        public void setGameState(String gameState) {
            this.gameState = gameState;
        }
    }
}
