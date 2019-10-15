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
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private TextWebSocketFrameHandler textWebSocketFrameHandler;


    @Override
    public void initChannel(SocketChannel ch) throws Exception {//2
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(64 * 1024));
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        pipeline.addLast(textWebSocketFrameHandler);
        //分割符
        ByteBuf delimiter = Unpooled.copiedBuffer(new byte[]{-25});
        //打印分隔符
        pipeline.addLast("frameDecoder", new DelimiterBasedFrameDecoder(1024, false, delimiter));

    }
}