package net.gaox;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * <p> spring test 执行顺序 </p>
 *
 * @author gaox·Eric
 * @date 2023-04-05 20:37
 */
@Slf4j
@SpringBootTest
public class LettuceRedisConnectApplicationTests {

    /**
     * 只执行一次，执行时机是在所有测试和 @BeforeEach 注解方法之前
     */
    @BeforeAll
    public static void beforeAll() {
        log.warn("begin All");
    }

}
