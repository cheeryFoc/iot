#include "Helper.h"
// #include <Windows.h>
#include <OneWire.h>
#include <DallasTemperature.h>
#define ONE_WIRE_BUS 13 //GPIO13 --- D6(DQ)

OneWire oneWire(ONE_WIRE_BUS);
DallasTemperature sensors(&oneWire);
DeviceAddress insideThermometer, outsideThermometer;


long lastMqttConn;          // 上次mqtt连接时间
long lastPublishMonitor;    // 上次发布监测数据时间
long lastTimerMonitor;      // 上次定时发布监测数据
int monitorCount = 100;
long monitorInterval = 10000;
/**
 * 启动
 */
void setup()
{
  //打开串行端口：
  Serial.begin(9600);
  sensors.begin();
  if (!sensors.getAddress(insideThermometer, 0)) Serial.println("Unable to find address for Device 0"); 
  if (!sensors.getAddress(outsideThermometer, 1)) Serial.println("Unable to find address for Device 1");
  
  printMsg("device starting...");
  connectWifi();
  printMsg("connectMqtt starting...");
  connectMqtt();
}

float tempC;
//温度相关函数
void printTemperature(DeviceAddress deviceAddress)
{
  tempC = sensors.getTempC(deviceAddress);
  Serial.print("Temp C: ");
  Serial.println(tempC);
  Serial.print("\r\n");
 
}

/**
 * 循环执行
 */
void loop()
{
  //Wifi掉线重连
  if (WiFi.status() != WL_CONNECTED)
  {
    connectWifi();
  }

  // 非阻塞Mqtt重连，间隔30秒
  if (WiFi.status() == WL_CONNECTED)
  {
    //Serial.println("reconnect mqtt 1");
    long now = millis();
    if (!mqttClient.connected())
    {
      if (now - lastMqttConn > 30000)
      {
        lastMqttConn = now;
        connectMqtt();
        Serial.println("connect mqtt 2");
      }
    }
    else
    {
      mqttClient.loop();
    }
  }

  // 非阻塞发布实时监测数据,间隔默认1秒
  if(WiFi.status() == WL_CONNECTED && monitorCount>0){
    //Serial.printf("\n发布实时监测数据: %d\n");
    long now = millis();
    if (now - lastPublishMonitor > monitorInterval)
      {
        lastPublishMonitor = now;
        monitorCount--;
        if (monitorCount == 0) {
          monitorCount=100;
        }
        sensors.requestTemperatures();
        printTemperature(outsideThermometer);
        publishMonitor(tempC);
      }
  }

  // 非阻塞定时上报，测试用，60秒发布一次
  // if(WiFi.status() == WL_CONNECTED){
  //   long now = millis();
  //   if (now - lastTimerMonitor > 60000)
  //     {
  //       publishEvent("{\"msg\": \"test\"}");
  //     }
  // }



}
