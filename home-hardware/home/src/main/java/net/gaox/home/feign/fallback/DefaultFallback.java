package net.gaox.home.feign.fallback;

import net.gaox.domain.entity.SystemEquipment;
import net.gaox.domain.util.api.ApiResponse;
import net.gaox.home.feign.SystemEquipmentFeignClient;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p> feign 降级 </p>
 *
 * @author gaox·Eric
 * @date 2024-04-12 17:37
 */
@Component
public class DefaultFallback implements SystemEquipmentFeignClient {
    public static final String DEFAULT_FALLBACK_MESSAGE = "系统繁忙，请稍后重试";

    @Override
    public ApiResponse<SystemEquipment> getById(Long id) {
        return ApiResponse.fail(DEFAULT_FALLBACK_MESSAGE);
    }

    @Override
    public ApiResponse<List<SystemEquipment>> list() {
        return ApiResponse.fail(DEFAULT_FALLBACK_MESSAGE);
    }

    @Override
    public ApiResponse<Boolean> save(SystemEquipment equipment) {
        return ApiResponse.fail(DEFAULT_FALLBACK_MESSAGE);
    }

    @Override
    public ApiResponse<Boolean> update(SystemEquipment equipment) {
        return ApiResponse.fail(DEFAULT_FALLBACK_MESSAGE);
    }

}
