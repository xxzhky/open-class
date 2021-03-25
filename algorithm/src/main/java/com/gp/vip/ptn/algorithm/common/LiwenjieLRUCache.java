package com.gp.vip.ptn.algorithm.common;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author: Fred
 * @date: 2020/8/2 1:11 上午
 * @description: (类的描述)
 */
public class LiwenjieLRUCache<K, V> {

        //利用linkedhashmap 自身的lru实现
        private LinkedHashMap<K,V> map;
        //默认缓存大小 1024
        private int capacity = 1024;
        //加载因子
        private static final float hashLoadFactory = 0.75f;

        public LiwenjieLRUCache() {
            map = new LinkedHashMap<K,V>(capacity, hashLoadFactory, true){
                private static final long serialVersionUID = 1;

                @Override
                protected boolean removeEldestEntry(Map.Entry eldest) {
                    return size() > capacity;
                }
            };
        }

        public synchronized V get(K key) {
            return map.get(key);
        }

        public synchronized void put(K key, V value) {
            map.put(key, value);
        }


}
