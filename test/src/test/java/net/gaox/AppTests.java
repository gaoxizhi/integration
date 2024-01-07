package net.gaox;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * <p> spring test 执行顺序 </p>
 *
 * @author gaox·Eric
 * @date 2023-04-05 20:37
 */
@Slf4j
@SpringBootTest
class AppTests {

    /**
     * 只执行一次，执行时机是在所有测试和 @BeforeEach 注解方法之前
     */
    @BeforeAll
    public static void beforeAll() {
        log.warn("begin All");
    }

    /**
     * 在每个测试执行之前执行
     */
    @BeforeEach
    public void beforeEach() {
        log.info("begin Each");
    }

    /**
     * 在每个测试执行之后执行
     */
    @AfterEach
    public void afterEach() {
        log.info("end Each");
    }

    /**
     * 只执行一次，执行时机是在所有测试和 @AfterEach 注解方法之后
     */
    @AfterAll
    public static void afterAll() {
        log.info("end All");
    }

    @Test
    public void test1() {
        log.info("test1");
    }

    @Test
    public void test2() {
        log.info("test2");
    }

}
