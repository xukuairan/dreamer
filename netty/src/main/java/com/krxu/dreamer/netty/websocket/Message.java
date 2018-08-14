package com.krxu.dreamer.netty.websocket;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author maywin
 * @version [版本号]
 * @date 2018/8/14
 * @description [添加描述]
 */
@Data
public class Message {
    public static final String JOIN = "join";
    public static final String LEAVE = "leave";

    private String type;
    private String content;
    private String date;
    private User user;

    public Message() {
    }

    public Message(String type, String content, String date, User user) {
        this.type = type;
        this.content = content;
        this.date = date;
        this.user = user;
    }

    public static Message getJoinMessage(User user) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = formatter.format(new Date());
        String content = user.getUserName() + " join in";
        return new Message(JOIN, content, now, user);
    }

    public static Message getLeaveMessage(User user) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = formatter.format(new Date());
        String content = user.getUserName() + " join in";
        return new Message(LEAVE, content, now, user);
    }
}
