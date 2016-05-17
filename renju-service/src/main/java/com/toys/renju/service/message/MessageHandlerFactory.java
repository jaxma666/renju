package com.toys.renju.service.message;

import java.util.Map;

/**
 * Created by lingyao on 16/5/17.
 */
public class MessageHandlerFactory {
    private Map<String, IMessageHandler> messageHandlerMap;

    public Map<String, IMessageHandler> getMessageHandlerMap() {
        return messageHandlerMap;
    }

    public void setMessageHandlerMap(Map<String, IMessageHandler> messageHandlerMap) {
        this.messageHandlerMap = messageHandlerMap;
    }

    public IMessageHandler getMessageHandler(String message) {
        if (message == null || messageHandlerMap == null) {
            return null;
        } else {
            return messageHandlerMap.get(message);
        }
    }
}
