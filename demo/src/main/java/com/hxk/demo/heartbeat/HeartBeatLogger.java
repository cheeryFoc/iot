package com.hxk.demo.heartbeat;

/**
 * HeartBeatLogger
 *
 * @author cheer
 */
public interface HeartBeatLogger {

    void error(String message);

    void error(String message, Throwable e);

    void info(String message);
}
