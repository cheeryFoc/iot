/***********************************************************
 * author: kerwincui [物美智能 wumei-smart]
 * create: 2022-02-20
 * email：164770707@qq.com
 * source:https://github.com/kerwincui/wumei-smart
 * board:esp8266 core for arduino v3.0.2
 ***********************************************************/
 
#ifndef _HELPER_H
#define _HELPER_H

#include <ESP8266WiFi.h> 
#include <ESP8266HTTPClient.h>
#include "PubSubClient.h"  
#include <string>
#include <sstream>
#include <ArduinoJson.h>    


extern WiFiClient wifiClient;
extern PubSubClient mqttClient;

extern String deviceNum ;      // 设备编号（重要，同时是Mqtt的clientId）
extern String userId;          // 用户ID
extern String productId;       // 产品ID
extern float rssi;             // 信号强度（信号极好4格[-55— 0]，信号好3格[-70— -55]，信号一般2格[-85— -70]，信号差1格[-100— -85]）
extern float firmwareVersion;  // 固件版本
extern char *wifiSsid;         // WIFI的SSID
extern char *wifiPwd;          // WIFI的密码
extern char *mqttHost;         // Mqtt消息服务器地址
extern int mqttPort;           // Mqtt消息服务器端口
extern char *mqttUserName;     // Mqtt消息服务器账号
extern char *mqttPwd;          // Mqtt消息服务器密码
extern char mqttSecret[17];    // Mqtt秘钥,16位

void rgbOff();
void ctrlRgb(int res);
void processFunction(String payload);
// 连接wifi
void connectWifi();
// 连接mqtt
void connectMqtt();
// Mqtt回调
void callback(char *topic, byte *payload, unsigned int length);

// 发布实时监测数据
void publishMonitor(float temp);
// 发布事件
void publishEvent(String topic,String msg);

void dataInit();
void printMsg(String msg);

#endif 
