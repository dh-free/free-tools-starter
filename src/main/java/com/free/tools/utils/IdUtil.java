package com.free.tools.utils;

import com.free.tools.algorithm.id.SnowflakeIdWorker;

import java.util.UUID;

/**
 * Id生成工具类
 *
 * @Author: dinghao
 * @Date: 2021-12-20 10:26:28
 */
public class IdUtil {

    /**
     * 构造方法私有化
     */
    private IdUtil() {
    }

    /**
     * 获取雪花id
     *
     * @param workerId
     * @param datacenterId
     * @return
     */
    public static long getSnowflakeId(long workerId, long datacenterId) {
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(workerId, datacenterId);
        return idWorker.nextId();
    }

    /**
     * 获取32位uuid，不包含'-'
     *
     * @return
     */
    public static String get32UUId() {
        return get36UUId().replace("-", "");
    }

    /**
     * 获取36位uuid 包含 '-'
     *
     * @return
     */
    public static String get36UUId() {
        return UUID.randomUUID().toString();
    }

    public static void main(String[] args) {
        System.out.println(IdUtil.get32UUId());
        System.out.println(IdUtil.get36UUId());
    }
}
