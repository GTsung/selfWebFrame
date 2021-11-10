package com.self.common.lru;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author GTsung
 * @date 2021/11/10
 */
public class SelfLruCache implements SelfCache {

    private SelfCache selfCache;

    private Map<Object, Object> keyMap;

    private Object eldestKey;

    public SelfLruCache(SelfCache selfCache) {
        this.selfCache = selfCache;
        setSize(1024);
    }

    private void setSize(final int size) {
        keyMap = new LinkedHashMap<Object, Object>(size, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                boolean res = size() > size;
                if (res) {
                    eldestKey = eldest.getKey();
                }
                return res;
            }
        };
    }

    @Override
    public int getSize() {
        return selfCache.getSize();
    }

    @Override
    public void putObject(Object key, Object value) {
        selfCache.putObject(key, value);
        keyMap.put(key, key);
        if (eldestKey != null) {
            selfCache.removeObject(eldestKey);
            eldestKey = null;
        }
    }

    @Override
    public Object getObject(Object key) {
        keyMap.get(key); // 重新排列双向链表
        return selfCache.getObject(key);
    }

    @Override
    public Object removeObject(Object key) {
        return selfCache.removeObject(key);
    }

    @Override
    public void clear() {
        selfCache.clear();
        keyMap.clear();
    }
}
