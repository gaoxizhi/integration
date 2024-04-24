package net.gaox.home.feign;

import net.gaox.domain.entity.Equipment;
import net.gaox.domain.util.api.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p> kv feign client </p>
 *
 * @author gaox·Eric
 * @since 2024-04-11
 */
@Component
@FeignClient(name = "equipment", path = "/equipment")
public interface EquipmentFeignClient {

    @GetMapping("/{id}")
    ApiResponse<Equipment> getById(@PathVariable("id") Long id);

    @GetMapping
    ApiResponse<List<Equipment>> list();

    @PostMapping
    ApiResponse<Boolean> save(@RequestBody Equipment equipment);

    @PutMapping
    ApiResponse<Boolean> update(@RequestBody Equipment equipment);

}
