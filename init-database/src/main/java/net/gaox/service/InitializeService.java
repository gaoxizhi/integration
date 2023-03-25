package net.gaox.service;

import net.gaox.domain.dto.InitializeDTO;

/**
 * <p> 初始化配置 Service </p>
 *
 * @author gaox·Eric
 * @date 2023-03-25 17:19
 */
public interface InitializeService {

    /**
     * 配置数据库
     *
     * @param initializeDto dto
     * @return 配置成功
     */
    Boolean configurer(InitializeDTO initializeDto);

}
