package net.gaox.redis.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.gaox.redis.entity.SomaTempLog;
import net.gaox.redis.mapper.SomaMapper;
import net.gaox.redis.service.SomaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gaox·Eric
 * @date 2019/4/14 10:51
 * 类全局配置cacheConfig
 */
//@CacheConfig(cacheManager = "somaCacheManager")
@Service
public class SomaServiceImpl implements SomaService {
    @Resource
    SomaMapper somaMapper;

    @Autowired
    RedisTemplate<Object, SomaTempLog> somaRedisTemplate;

    @Cacheable(value = "soma", key = "#id"/*,cacheManager = "gaoxCacheManager"*/)
    /**
     * 通过id查询
     * @param id
     * @return
     */
    @Override
    public SomaTempLog getSomaByid(Long id) {
        System.out.println("查询id：" + id);
        final SomaTempLog soma = somaMapper.getSomaByid(id);
        somaRedisTemplate.opsForValue().set("soma" + id, soma);
        return soma;
    }

    @CacheEvict(value = "soma")
    @Override
    public List<SomaTempLog> somaList() {
        QueryWrapper<SomaTempLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNotNull("id");

        /**lambda表达式*/
        /***java8特性，01*/
        List<SomaTempLog> list = new ArrayList<SomaTempLog>();
        //在forEach中填写逻辑
        somaMapper.somaList().stream().forEach(s -> list.add((SomaTempLog) s));

        /***java8 新特性，02*/
        //将使用list的流属性，得到map，转换到集合，再转换成list
        List<SomaTempLog> list2 = somaMapper.somaList().stream().map(object -> {
            return ((SomaTempLog) object);
        }).collect(Collectors.toList());

        return list;
    }
}