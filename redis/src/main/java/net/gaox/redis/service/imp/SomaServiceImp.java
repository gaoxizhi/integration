package net.gaox.redis.service.imp;

import net.gaox.redis.entity.SomaTempLog;
import net.gaox.redis.mapper.SomaMapper;
import net.gaox.redis.service.SomaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: <p>  </p>
 * @ClassName SomaServiceImp
 * @Author: gaox·Eric
 * @Date: 2019/4/14 10:51
 */
//@CacheConfig(cacheManager = "somaCacheManager")
@Service
public class SomaServiceImp implements SomaService {
    @Resource
    SomaMapper somaMapper;

    @Autowired
    RedisTemplate<Object, SomaTempLog> somaRedisTemplate;

    @Cacheable(value = "soma", key = "#id"/*,cacheManager = "gaoxCacheManager"*/)
    @Override
    public SomaTempLog getSomaByid(Long id) {
        System.out.println("查询id：" + id);
        final SomaTempLog soma = somaMapper.getSomaByid(id);
//        homeRedisTemplate.opsForValue().set("soma" + id, soma);
        return soma;
    }

    @CacheEvict(value = "soma")
    @Override
    public List<SomaTempLog> SomaList() {
        return null;
    }
}
