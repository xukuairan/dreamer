package com.krxu.dreamer.netty.websocket;

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author maywin
 * @version [版本号]
 * @date 2018/8/14
 * @description [添加描述]
 */
public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {

    private static final String WEB_SOCKET_UPGRADE = "websocket";
    private static final String WEB_SOCKET_CONNECTION = "Upgrade";
    private WebSocketServerHandshaker shaker;

    private Integer groupId;
    private User user;
    private Channel channel;

    public WebSocketServerHandler() {
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.channel = ctx.channel();
        System.out.println(ctx.channel().id().toString() + " 客户端与服务端连接开启");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Message message = Message.getLeaveMessage(user);
        TextWebSocketFrame tws = new TextWebSocketFrame(JSONObject.toJSONString(message));
        Global.groupSend(groupId, tws);
        Global.removeChannel(groupId, ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            handleHttpRequest(ctx, ((FullHttpRequest) msg));
        } else if (msg instanceof WebSocketFrame) {
            handlerWebSocketFrame(ctx, (WebSocketFrame) msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    private void handlerWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        // 判断是否关闭链路的指令
        if (frame instanceof CloseWebSocketFrame) {
            if (shaker == null) {
                return;
            }
            shaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            return;
        }
        // 判断是否ping消息
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        // 仅支持文本消息，不支持二进制消息
        if (!(frame instanceof TextWebSocketFrame)) {
            System.out.println("本例程仅支持文本消息，不支持二进制消息");
            return;
        }
        // 返回应答消息
        String msg = ((TextWebSocketFrame) frame).text();
        System.out.println("服务端收到：" + msg);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = formatter.format(new Date());
        Message message = new Message(Message.LEAVE, msg, now, user);
        TextWebSocketFrame tws = new TextWebSocketFrame(JSONObject.toJSONString(message));

        // 群发
        Global.groupSend(groupId, tws);
        //ChannelMatcher channelMatcher = ChannelMatchers.is()
        // 返回【谁发的发给谁】
        // ctx.channel().writeAndFlush(tws);
    }

    /**
     * ws的http连接请求
     *
     * @param ctx
     * @param req
     */
    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
        //uri= /ctr/groupId/userId
        String uri = req.getUri();
        if (null == uri || uri.trim().length() == 0) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
        }

        try {
            groupId = Integer.valueOf(uri.substring(1).split("/")[1]);
            Integer userId = Integer.valueOf(uri.substring(1).split("/")[2]);
            user = new User();
            user.setUserId(userId);
            user.setUserName(userId.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }

        if (!Global.isExists(groupId)) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }

        if (isWebSocketUpgrade(req)) {
            String subProtocols = req.headers().get(HttpHeaderNames.SEC_WEBSOCKET_PROTOCOL);
            WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(null, subProtocols, false);
            shaker = wsFactory.newHandshaker(req);

            if (shaker == null) {
                WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
            } else {
                shaker.handshake(ctx.channel(), req);
            }
            Global.addChannel(groupId, channel);
            Message message = Message.getJoinMessage(user);
            TextWebSocketFrame tws = new TextWebSocketFrame(JSONObject.toJSONString(message));
            Global.groupSend(groupId, tws);
        } else {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
        }
    }

    private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, DefaultFullHttpResponse res) {
        if (res.getStatus().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.getStatus().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();

        }
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        f.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private boolean isWebSocketUpgrade(FullHttpRequest req) {
        HttpHeaders headers = req.headers();
        return req.method().equals(HttpMethod.GET)
                && headers.get(HttpHeaderNames.UPGRADE).contains(WEB_SOCKET_UPGRADE)
                && headers.get(HttpHeaderNames.CONNECTION).contains(WEB_SOCKET_CONNECTION);
    }

}