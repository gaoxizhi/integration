package net.gaox.equipment.controller;

import lombok.RequiredArgsConstructor;
import net.gaox.domain.entity.Equipment;
import net.gaox.domain.util.api.ApiResponse;
import net.gaox.equipment.service.EquipmentService;
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
    public ApiResponse<Equipment> getById(@PathVariable Long id) {
        Equipment equipment = equipmentService.getById(id);
        if (null == equipment) {
            return ApiResponse.fail("未查询到设备");
        } else if (0 == equipment.getAble()) {
            return ApiResponse.fail("设备已禁用");
        }
        return ApiResponse.success(equipment);
    }

    @GetMapping
    public ApiResponse<List<Equipment>> list() {
        return ApiResponse.success(equipmentService.list(null));
    }

    @PostMapping
    public ApiResponse<Boolean> save(@RequestBody Equipment equipment) {
        boolean save = equipmentService.save(equipment);
        if (save) {
            return ApiResponse.success(true);
        }
        return ApiResponse.fail("保存失败");
    }

    @PutMapping
    public ApiResponse<Boolean> update(@RequestBody Equipment equipment) {
        boolean update = equipmentService.updateById(equipment);
        return ApiResponse.success(update);
    }

}
