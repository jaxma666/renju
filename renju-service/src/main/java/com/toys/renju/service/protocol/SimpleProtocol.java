package com.toys.renju.service.protocol;

/**
 * Created by lingyao on 16/5/25.
 */
public class SimpleProtocol {
    private String action;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
