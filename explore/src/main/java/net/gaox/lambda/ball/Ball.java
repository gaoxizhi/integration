package net.gaox.lambda.ball;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <p> 球类示例 </p>
 * 暴露：无参、全参、get方法
 *
 * @author gaox·Eric
 * @date 2019/9/21 13:07
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Ball {
    private static final String[] COLORS = {"red", "blue", "green", "yellow", "pink", "pepper"};
    private static Random r = new Random();
    private String color;
    private float weight;

    public static List<Ball> randomBall(int number) {
        List<Ball> balls = new ArrayList<>(number);
        for (int i = 0; i < number; i++) {
            Ball ball = new Ball(COLORS[r.nextInt(COLORS.length)], 1f + r.nextFloat() / 10f - 0.05f);
            balls.add(ball);
        }
        return balls;
    }
}