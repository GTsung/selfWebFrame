package com.self.backend.service;

import com.self.common.constant.RedisNameSpace;
import com.self.backend.domain.CommonConfigDO;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface CommonConfigService {

    int save(CommonConfigDO commonConfigDO);

    @Cacheable(cacheNames = {RedisNameSpace.COMMON_CONFIG}, unless = "#result == null", keyGenerator = "springCacheKeyGenerator")
    List<CommonConfigDO> selectByGroup(String group);

    @Cacheable(cacheNames = {RedisNameSpace.COMMON_CONFIG}, unless = "#result == null", keyGenerator = "springCacheKeyGenerator")
    CommonConfigDO selectByGroupAndKey(String group, String key);

    @Cacheable(cacheNames = {RedisNameSpace.COMMON_CONFIG}, unless = "#result == null", keyGenerator = "springCacheKeyGenerator")
    CommonConfigDO selectByKey(String key);

    int update(CommonConfigDO commonConfigDO);
}
