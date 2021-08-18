package net.gaox.lambda;

import net.gaox.entity.Ball;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p> 统计100个球中各种颜色球的总质量为多少？三种实现方式 </p>
 * 注意：map并不支持stream的方法
 *
 * @author gaox·Eric
 * @date 2019/9/21 13:10
 */
public class BallTest {
    /**
     * 最糟糕
     *
     * @param balls list
     * @return 结果
     */
    private static Map<String, Float> legendWay(List<Ball> balls) {
        Map<String, Float> result = new HashMap<>();
        for (Ball ball : balls) {
            if (!result.containsKey(ball.getColor())) {
                result.put(ball.getColor(), 0f);
            }
            result.put(ball.getColor(), result.get(ball.getColor()) + ball.getWeight());
        }
        return result;
    }

    /**
     * 利用Java8中map增加的接口方法的一种写法，相对更加简洁
     *
     * @param balls list
     * @return 结果
     */
    private static Map<String, Float> java8Way(List<Ball> balls) {
        Map<String, Float> result = new HashMap<>();
        for (Ball ball : balls) {
            result.putIfAbsent(ball.getColor(), 0f);
            result.merge(ball.getColor(), ball.getWeight(), (acc, element) -> acc + element);
        }
        return result;
    }

    /**
     * 首先利用了Collectors提供的分组方法
     * 再对分组后的各种颜色的球执行map(),reduce(),统计出每种颜色球的总质量。
     *
     * @param balls list
     * @return 结果
     */
    private static Map<String, Float> streamWay(List<Ball> balls) {
        Map<String, Float> result = new HashMap<>();
        balls.stream().collect(Collectors.groupingBy(Ball::getColor))
                .forEach((k, v) -> result.put(k, v.stream()
                        .map(Ball::getWeight)
                        //优化：(acc, element) -> acc + element ----> Float::sum
                        .reduce(0f, Float::sum)));
        return result;
    }

    public static void main(String[] args) {
        List<Ball> balls = Ball.randomBall(100);
        System.out.println(legendWay(balls));
        System.out.println(java8Way(balls));
        System.out.println(streamWay(balls));
    }
}