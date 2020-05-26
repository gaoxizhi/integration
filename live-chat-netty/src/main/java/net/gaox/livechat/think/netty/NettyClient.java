package net.gaox.livechat.think.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.time.LocalDateTime;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @date 2020/5/23 23:39
 */
public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) {
                        ch.pipeline().addLast(new StringEncoder());
                    }
                });

        Channel channel = bootstrap.connect("local.gaox.site", 8005).channel();

        while (true) {
            channel.writeAndFlush((LocalDateTime.now().toString() + ": hello from client!").getBytes());
            Thread.sleep(2000);
        }
    }
}
