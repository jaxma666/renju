package com.toys.renju.web;

import com.toys.renju.service.IPushCenter;
import com.toys.renju.service.IUserSessionCenter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by lingyao on 16/5/21.
 */

@Controller
public class RenjuConsoleController {
    @Resource
    IUserSessionCenter userSessionCenter;
    @Resource
    IPushCenter pushCenter;

    @RequestMapping("/boradcast")
    @ResponseBody
    public String boradcast() {
        for (Map.Entry<WebSocketSession, String> entry : userSessionCenter.getUserMap().entrySet()) {
            pushCenter.pushMessage("hi", entry.getKey());
        }
        return "done";
    }
}
