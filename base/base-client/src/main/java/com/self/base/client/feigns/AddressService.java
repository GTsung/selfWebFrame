package com.self.base.client.feigns;

import com.self.base.client.domain.AddressDO;
import com.self.common.constant.RedisNameSpace;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author GTsung
 * @date 2021/9/8
 */
@FeignClient(name = "SELF-BASE")
public interface AddressService {

    @GetMapping(value = "/address/all")
    @Cacheable(cacheNames = {RedisNameSpace.BASE_ADDRESS}, unless = "#result == null", keyGenerator = "springCacheKeyGenerator")
    List<AddressDO> getAll();
}
