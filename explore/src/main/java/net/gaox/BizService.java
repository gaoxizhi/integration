package net.gaox;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * <p> 某个业务服务类，多个简单分支 </p>
 *
 * @author gaox·Eric
 * @date 2020/9/25 10:45
 */
public class BizService {

    /**
     * 传统的 if else 解决方法
     * 当每个业务逻辑有3、4行时，用传统的策略模式不值得，直接的if else又显得不易读
     */
    public static String getCheckResult(String order) {
        if ("校验1".equals(order)) {
            return "执行业务逻辑1";
        } else if ("校验2".equals(order)) {
            return "执行业务逻辑2";
        } else if ("校验3".equals(order)) {
            return "执行业务逻辑3";
        } else if ("校验4".equals(order)) {
            return "执行业务逻辑4";
        } else if ("校验5".equals(order)) {
            return "执行业务逻辑5";
        } else if ("校验6".equals(order)) {
            return "执行业务逻辑6";
        } else if ("校验7".equals(order)) {
            return "执行业务逻辑7";
        } else if ("校验8".equals(order)) {
            return "执行业务逻辑8";
        } else if ("校验9".equals(order)) {
            return "执行业务逻辑9";
        }
        return "不在处理的逻辑中返回业务错误";
    }

    /**
     * 业务逻辑分派Map
     * Function为函数式接口
     * Function<String, String> 的含义是接收一个Stirng类型的变量，返回一个String类型的结果
     */
    private static Map<String, Function<String, String>> checkResultDispatcher = new HashMap<>();

    /**
     * 初始化 业务逻辑分派Map 其中value 存放的是 lambda表达式
     * PostConstruct : 用来修饰一个非静态的void（）方法。Java注解，非Spring提供
     * 会在服务器加载Servlet的时候运行，并且只会被服务器执行一次（在构造函数之后执行，init（）方法之前执行）。
     */
    @PostConstruct
    public static void checkResultDispatcherInit() {
        checkResultDispatcher.put("校验1", order -> String.format("对%s执行业务逻辑1", order));
        checkResultDispatcher.put("校验2", order -> String.format("对%s执行业务逻辑2", order));
        checkResultDispatcher.put("校验3", order -> String.format("对%s执行业务逻辑3", order));
        checkResultDispatcher.put("校验4", order -> String.format("对%s执行业务逻辑4", order));
        checkResultDispatcher.put("校验5", order -> String.format("对%s执行业务逻辑5", order));
        checkResultDispatcher.put("校验6", order -> String.format("对%s执行业务逻辑6", order));
        checkResultDispatcher.put("校验7", order -> String.format("对%s执行业务逻辑7", order));
        checkResultDispatcher.put("校验8", order -> String.format("对%s执行业务逻辑8", order));
        checkResultDispatcher.put("校验9", order -> String.format("对%s执行业务逻辑9", order));
    }

    public static String getCheckResultSuper(String order) {
        //从逻辑分派Dispatcher中获得业务逻辑代码，result变量是一段lambda表达式
        Function<String, String> result = checkResultDispatcher.get(order);
        if (result != null) {
            //执行这段表达式获得String类型的结果
            return result.apply(order);
        }
        return "不在处理的逻辑中返回业务错误";
    }

    public static void main(String[] args) {
        // 在Servlet中会自动执行
        checkResultDispatcherInit();
        System.out.println(getCheckResult("校验7"));
        System.out.println(getCheckResult("校验3"));
        System.out.println(getCheckResult("校验6"));
        System.out.println("------------------------------");
        System.out.println(getCheckResultSuper("校验7"));
        System.out.println(getCheckResultSuper("校验3"));
        System.out.println(getCheckResultSuper("校验6"));
    }
}

