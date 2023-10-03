#include "Helper.h"
#include "Helper.h"

#define RGB_R  4  //GPIO4对应ESP8266板子的D2
#define RGB_G  5  //GPIO5对应ESP8266板子的D1 
#define RGB_B  0  //GPIO5对应ESP8266板子的D3
//RGB 低灭高亮
WiFiClient wifiClient;
PubSubClient mqttClient;
// #define AP_ssid   "Xiaomi_363D_5G" //这里改成你的设备当前环境下要连接的接入点名字
// #define wfpwd  "2013yyq1215"         //这里改成你的设备当前环境下要连接的接入点密码 5G频段不可用
#define AP_ssid   "mylime" //这里改成你的设备当前环境下要连接的接入点名字
#define wfpwd  "hxk123456"         //这里改成你的设备当前环境下要连接的接入点密码
int timeout = 0;

//==================================== 这是需要配置的项 ===============================


// 设备信息配置
String userId = "2083450322973444"; //暂时不用
String productId = "2083450322973444"; //改

// Mqtt配置
char *mqttHost = "43.139.8.56"; //改
int mqttPort = 1883;  //可改
//MQTT帐号密码
char *mqttUserName = "admin"; //改
char *mqttPwd = "tx22emqx"; //改
//char *mqttPwd = "public"; //改

//====================================================================================

// 订阅的主题
String prefix = "server/" + productId;
// 发布的主题 
String pPropertyTopic = "2083450322973444"; //改
// 记录主题
String record = "record";
String warn = "warn";
String error = "error";
String inf = "info";
//初始化事件tag
// String initial="init";
//控制事件tag
String ctrl="control";

void rgbOff() {
    digitalWrite(RGB_R, LOW);
    digitalWrite(RGB_G, LOW);
    digitalWrite(RGB_B, LOW);
    delay(1000);
}
void ctrlRgb(int res) {
  switch(res) {
    case 0: 
        rgbOff();
        digitalWrite(RGB_R, HIGH);
        break;
    case 1: 
        rgbOff();
        digitalWrite(RGB_G, HIGH);
        break;
    case 2: 
        rgbOff();
        digitalWrite(RGB_B, HIGH);
        break;
    case 3: 
    rgbOff();
        digitalWrite(RGB_R, HIGH);
        digitalWrite(RGB_B, HIGH);
        break;
    case 4: 
    rgbOff();
        digitalWrite(RGB_B, HIGH);
        digitalWrite(RGB_G, HIGH);
        break;
    case 5:
    rgbOff(); 
        digitalWrite(RGB_R, HIGH);
        digitalWrite(RGB_G, HIGH);
        break;
    case 6: 
    rgbOff();
        digitalWrite(RGB_R, HIGH);
        digitalWrite(RGB_G, HIGH);
        digitalWrite(RGB_B, HIGH);
        break;
    default:
           rgbOff();
  }
}

//事件处理
void processFunction(String payload)
{
  
  StaticJsonDocument<1024> doc;
  DeserializationError error = deserializeJson(doc, payload);
  if (error) {
    Serial.print(F("deserializeJson() failed: "));
    Serial.println(error.f_str());
    return;
  }
  
  const char* event = doc["event"];
  const char* attribute = doc["attribute"];
 

  if (strcmp(event, "control") == 0)
  {
      
    if (strcmp(attribute, "rgb") == 0) 
    {
      const char* control = doc["control"];
      int value = doc["value"];
      if (strcmp(control, "off") == 0) 
      {
       rgbOff();
        printMsg("rgb is off");
      } else if (strcmp(control, "on") == 0) {
        ctrlRgb(value);
      }
      char str[10]; 
      sprintf(str,"%d",value);
      String msg = "{\"id\":\"" + productId + "io3"  +"\",\"attribute\":\"rgb\",\"command\":\"" + control +"\",\"value\":" + str + "}";
      mqttClient.publish(record.c_str(), msg.c_str());
    }

  }
 

}

// Mqtt回调
void callback(char *topic, byte *payload, unsigned int length)
{
  //blink();
  printMsg("receive data: ");
  String data = "";
  for (int i = 0; i < length; i++)
  {
    Serial.print((char)payload[i]);
    data += (char)payload[i];
  }
  processFunction(data);

}

// 连接wifi
void connectWifi()
{
  
  Serial.println("Start connecting");
  
  //调用 WiFi.begin()函数，开始连接接入点
  WiFi.begin(AP_ssid, wfpwd);
  WiFi.mode(WIFI_STA);
  Serial.print("Connecting to");
  Serial.print(AP_ssid);
  
  //这里的循环用来判断是否连接成功的。连接过程中每隔500毫秒会检查一次是否连接成功，，并打一个点表示正在连接中
  //连接成功后会给出提示，但是若60秒后还是没有连接上，则会提示超时
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("net ok");
  pinMode(RGB_R,OUTPUT);
  pinMode(RGB_G,OUTPUT);
  pinMode(RGB_B,OUTPUT);
}

// 连接mqtt
void connectMqtt()
{
  printMsg("connectMqtt...");
  String password = "tx22emqx"; //改
  printMsg("password: " + password);
  mqttClient.setClient(wifiClient);
  mqttClient.setServer(mqttHost, mqttPort);
  mqttClient.setCallback(callback);
  mqttClient.setBufferSize(1024);
  mqttClient.setKeepAlive(10);
  String clientId = productId;
  bool connectResult = mqttClient.connect(clientId.c_str(), mqttUserName, password.c_str());
  if (connectResult)
  {
    printMsg("connection successful");
    // 订阅(OTA、NTP、属性、功能、实时监测、信息) 1的参数表示QOS
    mqttClient.subscribe(prefix.c_str(), 1);

    printMsg("Subscribe to a topic: " + prefix);
    // 发布事件
    publishEvent(inf, "{\"eventName\":\"info\", \"message\":\"全功能设备,上线咯,芜湖\"}");
  }
  else
  {
    printMsg("Connection failed, rc=");
    Serial.print(mqttClient.state());
  }
}

// 发布实时监测数据
void publishMonitor(float temp)
{
  int flag = 0;
  if (temp > 40.0 || temp < 10.0) {
    flag = 1;
  }
  char str[10]; 
  sprintf(str,"%.2f",temp);
  String msg = "{\"id\":" + productId +",\"attribute\":\"temperature\",\"value\":" + str + "}" ;
  mqttClient.publish(pPropertyTopic.c_str(), msg.c_str()); //主题，信息
  if (flag) {
    publishEvent(warn,"{\"eventName\":\"warn\",\"deviceName\":\"" +productId+"\",\"message\":\"演示分组:全类型设备:异常温度, "+ str +"℃\"}");
  }
  
}

// 发布事件
void publishEvent(String topic,String msg)
{
 
  mqttClient.publish(topic.c_str(), msg.c_str());
}

void dataInit() {

}

//打印提示信息
void printMsg(String msg)
{
  Serial.print("\r\n[");
  Serial.print(millis());
  Serial.print("ms]");
  Serial.print(msg);
}
