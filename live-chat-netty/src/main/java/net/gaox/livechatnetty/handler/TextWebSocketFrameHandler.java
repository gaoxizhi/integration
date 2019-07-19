package net.gaox.livechatnetty.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import net.gaox.livechatnetty.util.RandomName;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: <p>  </p>
 * @ClassName: TextWebSocketFrameHandler
 * @Author: gaox·Eric
 * @Date: 2019/7/19 20:25
 */
@Component
@Qualifier("textWebSocketFrameHandler")
@ChannelHandler.Sharable
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static Map<String, String> map = new HashMap<>(100);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx,
                                TextWebSocketFrame msg) throws Exception {
        Channel incoming = ctx.channel();
        String uName = map.get(incoming.id().toString());
        for (Channel channel : channels) {
            if (channel != incoming) {
                channel.writeAndFlush(new TextWebSocketFrame("[" + uName + "]" + msg.text()));
            } else {
                channel.writeAndFlush(new TextWebSocketFrame("[you]" + msg.text()));
            }
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress());
        String uName = new RandomName().getRandomName();

        Channel incoming = ctx.channel();
        for (Channel channel : channels) {
            channel.writeAndFlush(new TextWebSocketFrame("[新用户] - " + uName + " 加入"));
        }
        map.put(incoming.id() + "", uName);
        channels.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        String uName = map.get(incoming.id().toString());
        for (Channel channel : channels) {
            channel.writeAndFlush(new TextWebSocketFrame("[用户] - " + uName + " 离开"));
        }
        map.remove(incoming.id().toString());
//        redisDao.saveString("cacheName",redisDao.getString("cacheName").replaceAll(uName,""));

        channels.remove(ctx.channel());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("用户:" + map.get(incoming.id().toString()) + "在线");
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("用户:" + map.get(incoming.id().toString()) + "掉线");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("用户:" + map.get(incoming.id().toString()) + "异常");
        cause.printStackTrace();
        ctx.close();
    }

}