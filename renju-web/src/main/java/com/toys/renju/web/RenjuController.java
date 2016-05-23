//package com.toys.renju.web;
//
//import com.toys.renju.service.IUserSessionCenter;
//import com.toys.renju.service.message.MessageHandlerFactory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.annotation.Resource;
//import javax.websocket.*;
//import javax.websocket.server.ServerEndpoint;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by lingyao on 16/5/16.
// */
//@ServerEndpoint(value = "/websocket")
//public class RenjuController {
//    private static final Logger logger = LoggerFactory.getLogger(RenjuController.class);
//
//    @Resource
//    IUserSessionCenter userSessionCenter;
//
//    @Resource
//    MessageHandlerFactory messageHandlerFactory;
//
//    @OnOpen
//    public void onOpen(Session session) {
//        Map<String, List<String>> params = session.getRequestParameterMap();
//        String userName = params.get("userName").get(0);
//        userSessionCenter.onLine(session, userName);
//    }
//
//    @OnMessage
//    public void onMessage(Session session, String message) {
//        messageHandlerFactory.getMessageHandler(message).handle(session);
//    }
//
//    @OnClose
//    public void onClose(Session session) {
//        userSessionCenter.offLine(session);
//    }
//
//    @OnError
//    public void onError(Session session) {
//        logger.error("error:" + session.getId());
//    }
//}
