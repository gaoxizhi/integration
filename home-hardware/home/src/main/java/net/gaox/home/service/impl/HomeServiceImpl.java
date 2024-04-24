package net.gaox.home.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gaox.domain.entity.Equipment;
import net.gaox.domain.entity.Home;
import net.gaox.domain.entity.RHomeEq;
import net.gaox.domain.entity.SystemEquipment;
import net.gaox.domain.util.api.ApiResponse;
import net.gaox.home.feign.EquipmentFeignClient;
import net.gaox.home.feign.SystemEquipmentFeignClient;
import net.gaox.home.mapper.HomeMapper;
import net.gaox.home.service.HomeService;
import net.gaox.home.service.RHomeEqService;
import org.springframework.stereotype.Service;

/**
 * <p> 家庭表 服务实现类 </p>
 *
 * @author gaox·Eric
 * @since 2024-04-11
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HomeServiceImpl extends ServiceImpl<HomeMapper, Home> implements HomeService {
    private final RHomeEqService homeEqService;
    private final SystemEquipmentFeignClient systemEquipmentFeignClient;
    private final EquipmentFeignClient equipmentFeignClient;

    @Override
    @GlobalTransactional
    public ApiResponse<Boolean> bindEquipment(Long homeId, Long equipmentId) {
        Home home = getById(homeId);
        if (null == home) {
            log.error("家庭不存在");
            return ApiResponse.fail("家庭不存在");
        }
        // 查询总表
        ApiResponse<SystemEquipment> systemEquipmentResp = systemEquipmentFeignClient.getById(equipmentId);
        SystemEquipment systemEquipment = systemEquipmentResp.getData();
        if (null == systemEquipment) {
            log.error("设备不存在: {}", systemEquipmentResp);
            return ApiResponse.fail("设备不存在");
        }
        // 保存设备
        Equipment equipment = new Equipment().setHomeId(homeId)
                .setAble(systemEquipment.getAble())
                .setTypeId(systemEquipment.getTypeId())
                .setMac(systemEquipment.getMac());
        equipmentFeignClient.save(equipment);
        log.info("保存设备到equipment...");
        // 更新总表
        systemEquipment.setAble(1);
        systemEquipmentFeignClient.update(systemEquipment);
        log.info("更新设备到systemEquipment...");
        // 绑定家庭设备
        LambdaQueryWrapper<RHomeEq> queryWrapper = new LambdaQueryWrapper<RHomeEq>()
                .eq(RHomeEq::getHomeId, homeId)
                .eq(RHomeEq::getEqId, equipmentId);
        RHomeEq r = homeEqService.getOne(queryWrapper);
        if (r == null) {
            RHomeEq homeEq = new RHomeEq().setHomeId(homeId)
                    .setEqId(equipmentId).setTypeId(systemEquipment.getTypeId());
            homeEqService.save(homeEq);
        }
        log.info("绑定成功");

        return ApiResponse.success(true);
    }

}
