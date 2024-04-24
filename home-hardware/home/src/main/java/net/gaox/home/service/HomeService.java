package net.gaox.home.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.gaox.domain.entity.Home;
import net.gaox.domain.util.api.ApiResponse;

/**
 * <p> 家庭表 服务类 </p>
 *
 * @author gaox·Eric
 * @since 2024-04-11
 */
public interface HomeService extends IService<Home> {

    /**
     * 绑定设备到家庭
     *
     * @param homeId      家庭id
     * @param equipmentId 设备id
     * @return 绑定结果
     */
    ApiResponse<Boolean> bindEquipment(Long homeId, Long equipmentId);

}
