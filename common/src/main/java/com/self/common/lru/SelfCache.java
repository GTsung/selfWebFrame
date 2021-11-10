package com.self.common.lru;

public interface SelfCache {

    int getSize();

    void putObject(Object key, Object value);

    Object getObject(Object key);

    Object removeObject(Object key);

    void clear();
}
