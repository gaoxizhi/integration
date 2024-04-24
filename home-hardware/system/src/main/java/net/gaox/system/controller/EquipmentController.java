package net.gaox.system.controller;

import lombok.RequiredArgsConstructor;
import net.gaox.domain.entity.SystemEquipment;
import net.gaox.domain.util.api.ApiResponse;
import net.gaox.system.service.EquipmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p> 设备总表 前端控制器 </p>
 *
 * @author gaox·Eric
 * @since 2024-04-11
 */
@RestController
@RequestMapping("/equipment")
@RequiredArgsConstructor
public class EquipmentController {
    private final EquipmentService equipmentService;

    @GetMapping("/{id}")
    public ApiResponse<SystemEquipment> getById(@PathVariable Long id) {
        SystemEquipment equipment = equipmentService.getById(id);
        if (null == equipment) {
            return ApiResponse.fail("未查询到设备");
            // } else if (0 == equipment.getAble()) {
            //     return ApiResponse.fail("设备已禁用");
        }
        return ApiResponse.success(equipment);
    }

    @GetMapping
    public ApiResponse<List<SystemEquipment>> list() {
        return ApiResponse.success(equipmentService.list(null));
    }

    @PostMapping
    public ApiResponse<Boolean> save(@RequestBody SystemEquipment equipment) {
        boolean save = equipmentService.save(equipment);
        if (save) {
            return ApiResponse.success(true);
        }
        return ApiResponse.fail("保存失败");
    }

    @PutMapping
    public ApiResponse<Boolean> update(@RequestBody SystemEquipment equipment) {
        SystemEquipment one = equipmentService.getById(equipment.getId());
        // 测试，模拟异步分不可用场景
        if (null == one || 0 == one.getAble()) {
            throw new RuntimeException("设备已禁用");
        }
        boolean update = equipmentService.updateById(equipment);
        return ApiResponse.success(update);
    }

}
