package net.gaox.home.feign;

import net.gaox.domain.entity.SystemEquipment;
import net.gaox.domain.util.api.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p> system equipment feign client </p>
 *
 * @author gaox·Eric
 * @since 2024-04-11
 */
@Component
@FeignClient(name = "system")
public interface SystemEquipmentFeignClient {
    @GetMapping("/equipment/{id}")
    ApiResponse<SystemEquipment> getById(@PathVariable("id") Long id);

    @GetMapping("/equipment")
    ApiResponse<List<SystemEquipment>> list();

    @PostMapping("/equipment")
    ApiResponse<Boolean> save(@RequestBody SystemEquipment equipment);

    @PutMapping("/equipment")
    ApiResponse<Boolean> update(@RequestBody SystemEquipment equipment);

}
