package net.gaox.home.controller;

import lombok.RequiredArgsConstructor;
import net.gaox.domain.entity.Equipment;
import net.gaox.domain.entity.SystemEquipment;
import net.gaox.domain.util.api.ApiResponse;
import net.gaox.home.feign.EquipmentFeignClient;
import net.gaox.home.feign.SystemEquipmentFeignClient;
import net.gaox.home.service.HomeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p> 家庭表 前端控制器 </p>
 *
 * @author gaox·Eric
 * @since 2024-04-11
 */
@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {
    private final HomeService homeService;
    private final EquipmentFeignClient equipmentFeignClient;
    private final SystemEquipmentFeignClient systemEquipmentFeignClient;

    @PostMapping("/bind/equipment")
    public ApiResponse<Boolean> bindEquipment(Long homeId, Long equipmentId) {
        return homeService.bindEquipment(homeId, equipmentId);
    }

    @GetMapping("/system/euqipment/list")
    public ApiResponse<List<SystemEquipment>> systemEuqipmentList() {
        ApiResponse<List<SystemEquipment>> list = systemEquipmentFeignClient.list();
        return list;
    }

    @GetMapping("/system/euqipment/get/{equipmentId}")
    public ApiResponse<SystemEquipment> systemEuqipmentById(@PathVariable("equipmentId") Long equipmentId) {
        ApiResponse<SystemEquipment> response = systemEquipmentFeignClient.getById(equipmentId);
        return response;
    }

    @PostMapping("/system/equipment")
    ApiResponse<Boolean> save(@RequestBody SystemEquipment equipment) {
        return systemEquipmentFeignClient.save(equipment);
    }

    @GetMapping("/euqipment/list")
    public ApiResponse<List<Equipment>> euqipmentList() {
        ApiResponse<List<Equipment>> list = equipmentFeignClient.list();
        return list;
    }

}
