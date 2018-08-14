package com.krxu.dreamer.netty.websocket;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author maywin
 * @version [版本号]
 * @date 2018/8/14
 * @description [添加描述]
 */
public class Global {

    public static void printGroupMap(){
        for(Integer groupId : groupMap.keySet()){
            ChannelGroup channels = getChannelGroup(groupId);
            System.out.println("GROUP[" + groupId + "]" + channels);
        }

    }

    private volatile static Map<Integer, ChannelGroup> groupMap = new ConcurrentHashMap<>();

    static {
        createGroup(10000);
        createGroup(20000);
    }

    public static boolean createGroup(Integer id) {
        if (isExists(id)) {
            return false;
        }
        ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        groupMap.put(id, group);
        return true;
    }

    public static void addChannel(Integer groupId, Channel channel){
        getChannelGroup(groupId).add(channel);
    }

    public static void removeChannel(Integer groupId, Channel channel){
        getChannelGroup(groupId).remove(channel);

    }

    public static ChannelGroup getChannelGroup(Integer groupId) {
        return groupMap.get(groupId);
    }

    public static boolean isExists(Integer groupId) {
        return groupMap.containsKey(groupId);
    }

    public static void groupSend(Integer id, TextWebSocketFrame tws) {
        getChannelGroup(id).writeAndFlush(tws);
    }
}