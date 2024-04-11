package net.gaox.equipment.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.gaox.domain.entity.Equipment;
import net.gaox.equipment.service.EquipmentService;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @date 2024-04-11 23:27
 */
@Slf4j
@SpringBootTest
public class EquipmentServiceTest {
    @Resource
    private EquipmentService equipmentService;

    @Test
    public void check() {
        Equipment equipment = new Equipment();

        boolean save = equipmentService.save(equipment);
        if (save) {
            log.info("保存成功, equipment = {}", equipment);
            boolean remove = equipmentService.removeById(equipment.getId());
            if (remove) {
                log.info("删除成功");
            } else {
                log.info("删除失败");
            }
        } else {
            log.info("保存失败");
        }
    }

}