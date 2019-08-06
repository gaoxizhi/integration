package net.gaox.livechat.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import net.gaox.livechat.entity.User;
import net.gaox.livechat.service.UserService;
import net.gaox.livechat.util.RandomName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
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

    @Autowired
    private UserService userService;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx,
                                TextWebSocketFrame msg) throws Exception {
        Channel incoming = ctx.channel();
        String uName = map.get(incoming.id().toString());
        for (Channel channel : channels) {
            if (channel != incoming) {
                channel.writeAndFlush(new TextWebSocketFrame("[" + uName + "]" + msg.text()));
            } else {
                System.out.println("66666666");
                channel.writeAndFlush(new TextWebSocketFrame("[you]" + msg.text()));
                System.out.println(String.format("%s  User[%s]: %s", LocalDateTime.now().toString(), uName, msg.text()));
            }
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress());
        String uName = new RandomName().getRandomName();
        BigDecimal decimal = new BigDecimal("0");
        Channel incoming = ctx.channel();
        System.out.println("333333333333333333333");
        List<User> twoName = userService.findTwoName("小红", "笑话");
        twoName.forEach(s -> System.out.println(s.toString()+"1111111111111111111111111111111111111"));
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