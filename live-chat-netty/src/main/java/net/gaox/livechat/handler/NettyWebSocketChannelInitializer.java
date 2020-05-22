package net.gaox.livechat.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author gaox·Eric
 * @date 2019/7/19 20:24
 */

@Component
@Qualifier("somethingChannelInitializer")
public class NettyWebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {
    //    private RabbitSender rabbitSender;
//    private int port;
//
//    public NettyWebSocketChannelInitializer(RabbitSender rs, int tcpPort) {
//        this.rabbitSender = rs;
//        this.port = tcpPort;
//    }
    private final TextWebSocketFrameHandler textWebSocketFrameHandler;

    public NettyWebSocketChannelInitializer(TextWebSocketFrameHandler textWebSocketFrameHandler) {
        this.textWebSocketFrameHandler = textWebSocketFrameHandler;
    }


    @Override
    public void initChannel(SocketChannel ch) throws Exception {//2
        ChannelPipeline pipeline = ch.pipeline();

        // ====================== 用于支持http协议    ======================

        // websocket 基于http协议，所以要有http编解码器
        pipeline.addLast(new HttpServerCodec());
        // 对httpMessage进行聚合，聚合成FullHttpRequest或FullHttpResponse
        // 几乎在netty中的编程，都会使用到此hanler
        pipeline.addLast(new HttpObjectAggregator(64 * 1024));
        // 对写大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());


        // ====================== 支持httpWebsocket ======================

        /**
         * websocket 服务器处理的协议，用于指定给客户端连接访问的路由 : /ws
         * 本handler会帮你处理一些繁重的复杂的事
         * 会帮你处理握手动作： handshaking（close, ping, pong） ping + pong = 心跳
         * 对于websocket来讲，都是以frames进行传输的，不同的数据类型对应的frames也不同
         */
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        // 自定义handler 文本处理器
        pipeline.addLast(textWebSocketFrameHandler);
        //分割符
        ByteBuf delimiter = Unpooled.copiedBuffer(new byte[]{-25});
        //打印分隔符
        pipeline.addLast("frameDecoder", new DelimiterBasedFrameDecoder(1024, false, delimiter));

    }
}