package com.gp.vip.ptn.algorithm.channel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;

/**
 * @author: Fred
 * @date: 2021/3/25 1:35 下午
 * @description: (类的描述)
 */
@Getter
@Setter
@AllArgsConstructor
class Tablet implements Persistable {

    private String brand;
    private boolean isCellular;
    // in US Dollars
    private long cost;

    public Tablet() {
        brand = "";
    }

    @Override
    public void persist(ByteBuffer buffer) {
        byte[] strBytes = brand.getBytes();
        buffer.putInt(strBytes.length);
        buffer.put(strBytes, 0, strBytes.length);
        buffer.put(isCellular == true ? (byte) 1 : (byte) 0);
        buffer.putLong(cost);
    }

    @Override
    public void recover(ByteBuffer buffer) {
        int size = buffer.getInt();
        byte[] rawBytes = new byte[size];
        buffer.get(rawBytes, 0, size);
        this.brand = new String(rawBytes);
        this.isCellular = buffer.get() == 1 ? true : false;
        this.cost = buffer.getLong();
    }

    @Override
    public String toString() {
        return "Tablet [brand=" + brand + ", isCellular=" + isCellular + ", cost=" + cost + "]";
    }

}
