package net.gaox.livechat.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import net.gaox.livechat.util.RandomName;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * <p> 处理[文本消息]的handler </p>
 * {@link TextWebSocketFrame} 在netty中，是用于为websocket专门处理文本的对象，frame是消息的载体
 *
 * @author gaox·Eric
 * @date 2019/7/19 20:25
 */
@Slf4j
@Component
@Qualifier("textWebSocketFrameHandler")
@ChannelHandler.Sharable
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static Map<String, String> map = new HashMap<>(128);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx,
                                TextWebSocketFrame msg) throws Exception {
        Channel incoming = ctx.channel();
        String uName = map.get(incoming.id().asLongText());
        for (Channel channel : channels) {
            if (channel != incoming) {
                channel.writeAndFlush(new TextWebSocketFrame("[" + uName + "]" + msg.text()));
            } else {
                channel.writeAndFlush(new TextWebSocketFrame("[you]" + msg.text()));
                log.info("收到来自[{}]的消息：[{}]", uName, msg.text());
            }
        }
    }

    /**
     * 当客户端连接服务端之后（打开连接）
     * 获取客户端的channle，并且放到ChannelGroup中去进行管理
     *
     * @param ctx ctx
     * @throws Exception e
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

        String uName = new RandomName().getRandomName();
        Channel incoming = ctx.channel();
        map.put(incoming.id().asLongText(), uName);
        log.info("新用户[{}]加入，来自[{}]", map.get(incoming.id().asLongText()), ctx.channel().remoteAddress().toString());

        for (Channel channel : channels) {
            channel.writeAndFlush(new TextWebSocketFrame("[新用户] - " + uName + " 加入"));
        }
        channels.add(ctx.channel());
    }

    /**
     * 当触发handlerRemoved，ChannelGroup会自动移除对应客户端的channel
     *
     * @param ctx ctx
     * @throws Exception e
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        String uName = map.get(incoming.id().asLongText());
        log.info("用户[{}]离开", uName);

        for (Channel channel : channels) {
            channel.writeAndFlush(new TextWebSocketFrame("[用户] - " + uName + " 离开"));
        }
        map.remove(incoming.id().asLongText());
//        redisDao.saveString("cacheName",redisDao.getString("cacheName").replaceAll(uName,""));

        channels.remove(ctx.channel());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        log.info("用户[{}]上线", map.get(incoming.id().asLongText()));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        log.info("用户[{}]离线", map.get(incoming.id().asLongText()));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel incoming = ctx.channel();
        log.info("用户[{}]状态异常", map.get(incoming.id().asLongText()));
        cause.printStackTrace();
        ctx.close();
    }
}