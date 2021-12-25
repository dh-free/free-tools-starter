package com.free.tools.utils;

import java.util.Collection;

/**
 * 集合工具类
 *
 * @Author: dinghao
 * @Date: 2021-12-25 17:55:18
 */
public class CollUtils {

    /**
     * 构造方法私有化
     */
    private CollUtils() {
    }

    /**
     * 判断集合是否为空
     *
     * @param coll
     * @return
     */
    public static boolean isEmpty(Collection coll) {
        return null == coll || coll.isEmpty();
    }

    /**
     * 判断集合是否不为空
     *
     * @param coll
     * @return
     */
    public static boolean isNotEmpty(Collection coll) {
        return !CollUtils.isEmpty(coll);
    }
}
