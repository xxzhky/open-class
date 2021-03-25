package com.gp.vip.ptn.algorithm.channel;

import java.nio.ByteBuffer;

/**
 * @author: Fred
 * @date: 2021/3/25 1:34 下午
 * @description: (类的描述)
 */
public interface Persistable {
    public void persist(ByteBuffer buffer);

    public void recover(ByteBuffer buffer);
}
