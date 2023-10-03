package com.hxk.demo.utils.id;

import java.util.Random;
import java.util.UUID;

/**
 * @projectName: demo
 * @package: com.hxk.demo.utils.id
 * @className: UUIDTool
 * @author: cheer
 * @description: TODO
 * @version: 1.0
 */
public class UUIDTool {

    public static String getOrderSeq() {
        String orderSeq = Math.abs(UUID.randomUUID().toString().hashCode()) + "";
        while (orderSeq.length() < 16) {
            orderSeq = orderSeq + (int) (Math.random() * 10);
        }
        return orderSeq;
    }


}
