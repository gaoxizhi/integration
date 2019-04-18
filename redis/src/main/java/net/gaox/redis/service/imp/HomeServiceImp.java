package net.gaox.redis.service.imp;

import net.gaox.redis.entity.Home;
import net.gaox.redis.mapper.HomeMapper;
import net.gaox.redis.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description: <p>  </p>
 * @ClassName HomeServiceImp
 * @Author: gaox·Eric
 * @Date: 2019/4/13 14:41
 */
//对类进行配置缓存组件名
//@CacheConfig(cacheNames = "home")
@Service
public class HomeServiceImp implements HomeService {
    @Resource
    HomeMapper homeMapper;

    /**
     * 默认情况下redis使用的时JDK的序列化器，存储到jredis的数据时人无法识别的
     * 自己定制
     * 配置MyRedisConfig
     */
//    @Autowired
//    RedisTemplate redisTemplate;
    @Autowired
    RedisTemplate<Object, Home> homeRedisTemplate;


    /*****************************************************************************************************
     * cacheNames/ value：指定缓存组件的名字；将方法的返回值放在那个缓存中，是数组的形式，可以指定多个缓存
     * ***************************************************************************************************
     * key：缓存数据使用的key；可以用它来指定。默认是使用方法参数的值 1-方法的返回值
     *      编写SpEL：#id是参数id的值，等同于#a0、#p0，#{root.args[0]（所有参数第一个值）
     *      keyGenerator：key的生成器；可以自己指定key的生成器的组件id
     *-------------------------------------------------------------------------------------
     * 指定格式：
     *      spELl：          key = "#root.methodName+'['+#id+']'"（方法名加中括号id值）
     *      keyGenerator：   自定义KeyGenerator @{LINK}MyKeyGenerator
     * 注意：keyGenerator/key只能使用一个
     * *********************************************************************************
     * condition：指定符合条件的情况下才缓存；
     *      id值大于1的：condition = "#id>0"
     * **********************************************************************************
     * unless：否定缓存，当unless指定条件为true，方法的返回值就不回被缓存；可以获取到结果进行判断
     * unless = "#result == null"
     * ***********************************************************
     * sync： 是否使用异步模式
     *
     * **************************************************************************
     * //对类进行配置缓存组件名
     * //@CacheConfig(cacheNames = "home")
     * **************************************************************************
     * @Cacheable
     * @param id
     * @return
     */
//    @Cacheable(cacheNames = {"home"})
//    @Cacheable(cacheNames = "home", key = "#id")
//    @Cacheable(value = {"home"},keyGenerator = "myKeyGenerator"}
//    @Cacheable(value = {"home"}, keyGenerator = "myKeyGenerator", condition = "#id>=2")
    @Override
    public Home getHomeByid(Long id) {
        System.out.println("查询开始："+LocalDateTime.now());
        final Home home1 = homeRedisTemplate.opsForValue().get("home" + id);
        if (null != home1) {
            System.out.println("查询redis："+ LocalDateTime.now());
            System.out.println(home1);
            return home1;
        }
        System.out.println("查询home id：" + id);
        final Home home = homeMapper.getHome(id);
        System.out.println("查询数据库："+ LocalDateTime.now());
        homeRedisTemplate.opsForValue().set("home" + id, home);
        System.out.println("存入redis："+LocalDateTime.now());
        return home;
    }

    /**
     * @param home
     * @return
     * @CachePut: 既能调用方法还能同步更新缓存数据
     * 修改数据库的某个数据，同时更新缓存：value和key必须是相同值
     * 运行时机：
     * 1. 先调用目标方法
     * 2. 将目标方法的结果缓存起来
     * <p>
     * 测试步骤：《更新后，查询方法数据没有更新》
     * 1. 查询home15，查到结果会被缓存起来
     * <p>
     * 2.以后查询还是之前结果
     * <p>
     * 3.更新home15
     * <p>
     * 4.查询home15，不是更新后的最新数据
     * @Cacheable的key是不能使用#result.id result是方法执行后返回体中的数据
     */
    @CachePut(value = "home", key = "#result.id")
    @Override
    public Home updata(Home home) {
        System.out.println("更新：" + home.toString());
        homeMapper.updates(home);
        return homeMapper.getHome((home.getId()));
    }

    /**
     * @param id
     * @return
     * @CacheEvict 删除缓存
     * key 指定要清除的数据
     * allEntry = true 指定清除这个缓存中所有的数据
     * beforeIvocation = false 缓存的清除在方法之前调用
     * 默认代表缓存清除操作在方法执行之后操作，如果出现异常缓存就不会清除
     * beforeIvocation = true
     * 缓存在方法执行之前调用，无论方法是否执行，无论方法中出现什么异常，缓存都清除
     */
    @CacheEvict(value = "home", key = "#id", beforeInvocation = true)
    @Override
    public Home delete(Long id) {
        Home home = homeMapper.getHome(id);
        try {
            int i = 1 / 0;
        } catch (Exception e) {
            return null;
        }
        System.out.println("删除：" + home.toString());
        homeMapper.deleteByid(id);
        return home;
    }

    //    @CacheEvict(allEntries = true, value = "home")
    @Caching(
            cacheable = {
                    @Cacheable(value = "home"/*,key = "#id"*/)
            },
            put = {
                    //可以配置多个缓存组件
                    @CachePut(value = "home"),
                    @CachePut(value = "uu-key")
            }
    )
    @Override
    public List<Home> homeList() {
        List<Home> list = homeMapper.listHome();
        System.out.println("查询列表，并清空缓存！");
        return list;
    }
}
