package net.gaox.redis.service;


import net.gaox.redis.entity.SomaTempLog;

import java.util.List;

/**
 * @Description: <p>  </p>
 * @ClassName SomaService
 * @Author: gaox·Eric
 * @Date: 2019/4/14 10:49
 */
public interface SomaService {

    SomaTempLog getSomaByid(Long id);

    List<SomaTempLog> SomaList();

}
