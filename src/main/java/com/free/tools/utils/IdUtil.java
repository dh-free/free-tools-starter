package com.free.tools.utils;

import com.free.tools.algorithm.id.SnowflakeIdWorker;
import com.free.tools.algorithm.id.UUID;

/**
 * Id生成工具类
 *
 * @Author: dinghao
 * @Date: 2021-12-20 10:26:28
 */
public class IdUtil {

    public static long getSnowflakeId(long workerId, long datacenterId) {
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(workerId, datacenterId);
        return idWorker.nextId();
    }

    public static String getUUId() {
        return UUID.randomUUID().toString();
    }

    public static void main(String[] args) {
        System.out.println(IdUtil.getUUId());
    }
}
