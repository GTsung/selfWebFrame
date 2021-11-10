package com.self.common.lru;

import java.util.HashMap;
import java.util.Map;

/**
 * @author GTsung
 * @date 2021/11/10
 */
public class SelfCacheImpl implements SelfCache {

    private final Map<Object, Object> cache = new HashMap<>();

    @Override
    public int getSize() {
        return cache.size();
    }

    @Override
    public void putObject(Object key, Object value) {
        cache.put(key, value);
    }

    @Override
    public Object getObject(Object key) {
        return cache.get(key);
    }

    @Override
    public Object removeObject(Object key) {
        return cache.remove(key);
    }

    @Override
    public void clear() {
        cache.clear();
    }
}
