package com.krxu.dreamer.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.AttributeKey;
import lombok.extern.log4j.Log4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author maywin
 * @version [版本号]
 * @date 2018/8/14
 * @description [添加描述]
 */
@Log4j
public class ChatRoomServer {

    public static void main(String[] args) {
        new ChatRoomServer(9999).start();
    }

    private static final String HN_HTTP_CODEC = "HN_HTTP_CODEC";
    private static final String HN_HTTP_AGGREGATOR = "HN_HTTP_AGGREGATOR";
    private static final String HN_HTTP_CHUNK = "HN_HTTP_CHUNK";
    private static final String HN_SERVER = "HN_LOGIC";
    private static final AttributeKey<WebSocketServerHandshaker> ATTR_HAND_SHAKER = AttributeKey.newInstance("ATTR_KEY_CHANNELID");
    private static final int MAX_CONTENT_LENGTH = 65536;
    private static final String WEB_SOCKET_UPGRADE = "websocket";
    private static final String WEB_SOCKET_CONNECTION = "Upgrade";
    private static final String WEB_SOCKET_URI_ROOT_PATTERN = "ws://%s:%d";


    private String host; // 绑定的地址
    private int port; // 绑定的端口

    /**
     * 保存所有WebSocket连接
     */
    private Map<ChannelId, Channel> channelMap = new ConcurrentHashMap<ChannelId, Channel>();

    private final String WEBSOCKET_URI_ROOT;

    public ChatRoomServer(int port) {
        this("localhost", port);
    }

    public ChatRoomServer(String host, int port) {
        this.host = host;
        this.port = port;
        WEBSOCKET_URI_ROOT = String.format(WEB_SOCKET_URI_ROOT_PATTERN, host, port);
    }

    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup);
        b.channel(NioServerSocketChannel.class);
        b.childHandler(new ChannelInitializer<Channel>() {

            @Override
            protected void initChannel(Channel ch) throws Exception {
                ChannelPipeline pl = ch.pipeline();
                // 保存该Channel的引用
                channelMap.put(ch.id(), ch);
                log.info("new channel " + ch.toString());
                ch.closeFuture().addListener(new ChannelFutureListener() {
                    public void operationComplete(ChannelFuture future) throws Exception {
                        channelMap.remove(future.channel().id());
                    }
                });

                pl.addLast(HN_HTTP_CODEC, new HttpServerCodec());
                pl.addLast(HN_HTTP_AGGREGATOR, new HttpObjectAggregator(MAX_CONTENT_LENGTH));
                pl.addLast(HN_HTTP_CHUNK, new ChunkedWriteHandler());
               // pl.addLast(HN_SERVER, new WebSocketServerHandler(WebSocketServer.this, WebSocketServer.this));
            }

        });

        try {
            // 绑定端口
            ChannelFuture future = b.bind(host, port).addListener(new ChannelFutureListener() {

                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        log.info("websocket started.");
                    }
                }
            }).sync();

            future.channel().closeFuture().addListener(new ChannelFutureListener() {

                public void operationComplete(ChannelFuture future) throws Exception {
                    log.info("server channel closed. " + future.channel());
                }

            }).sync();
        } catch (InterruptedException e) {
            log.error(e.toString());
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
        log.info("websocket server shutdown");
    }

    public void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
        // 该请求是不是websocket upgrade请求
        if (isWebSocketUpgrade(req)) {
            log.info("upgrade to websocket protocol");

            String subProtocols = req.headers().get(HttpHeaderNames.SEC_WEBSOCKET_PROTOCOL);

            WebSocketServerHandshakerFactory factory = new WebSocketServerHandshakerFactory(WEBSOCKET_URI_ROOT, subProtocols, false);
            WebSocketServerHandshaker handshaker = factory.newHandshaker(req);

            // 请求头不合法, 导致handshaker没创建成功
            if (handshaker == null) {
                WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
            } else {
                // 响应该请求
                handshaker.handshake(ctx.channel(), req);
                // 把handshaker 绑定给Channel, 以便后面关闭连接用
                ctx.channel().attr(ATTR_HAND_SHAKER).set(handshaker);// attach handshaker to this channel
            }
            return;
        }
        log.info("ignoring normal http request");
    }

    public void handleFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        // text frame
        if (frame instanceof TextWebSocketFrame) {
            String text = ((TextWebSocketFrame) frame).text();
            TextWebSocketFrame rspFrame = new TextWebSocketFrame(text);
            log.info("recieve TextWebSocketFrame from channel :" + ctx.channel());
            // 发给其他所有channel
            for (Channel ch : channelMap.values()) {
                if (ctx.channel().equals(ch)) {
                    continue;
                }
                ch.writeAndFlush(rspFrame);
            }
            return;
        }

        // ping frame, 回复pong frame即可
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().writeAndFlush(new PongWebSocketFrame(frame.content().retain()));
            return;
        }

        if (frame instanceof PongWebSocketFrame) {
            return;
        }
        // close frame,
        if (frame instanceof CloseWebSocketFrame) {
            WebSocketServerHandshaker handshaker = ctx.channel().attr(ATTR_HAND_SHAKER).get();
            if (handshaker == null) {
                return;
            }
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            return;
        }
        // 剩下的是binary frame, 忽略
        log.warn("unHandle binary frame from channel :" + ctx.channel());
    }

    /**
     * 三者与：1.GET? 2.Upgrade头 包含websocket字符串?  3.Connection头 包含 Upgrade字符串?
     *
     * @param req
     * @return
     */
    private boolean isWebSocketUpgrade(FullHttpRequest req) {
        HttpHeaders headers = req.headers();
        return req.method().equals(HttpMethod.GET)
                && headers.get(HttpHeaderNames.UPGRADE).contains(WEB_SOCKET_UPGRADE)
                && headers.get(HttpHeaderNames.CONNECTION).contains(WEB_SOCKET_CONNECTION);
    }
}
