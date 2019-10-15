package net.gaox.redis.service;


import net.gaox.redis.entity.SomaTempLog;

import java.util.List;

/**
 * @Description: <p>  </p>
 * @ClassName SomaService
 * @author gaox·Eric
 * @date 2019/4/14 10:49
 */
public interface SomaService {
    /**
     * 通过id查询信息
     *
     * @param id
     * @return
     */
    SomaTempLog getSomaByid(Long id);

    /**
     * 查询历史信息列表
     *
     * @return
     */
    List<SomaTempLog> somaList();

}
