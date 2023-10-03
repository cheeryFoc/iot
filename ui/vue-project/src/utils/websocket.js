import { notification } from 'ant-design-vue';
import { logout } from '@/api/authentication';

export default class SocketService {
    //实例化
    static instance = null;
    static get Instance() {
        if (!this.instance) {
            this.instance = new SocketService();
        }
        return this.instance;
    }
    // 和服务端连接的socket对象
    ws = null;
    wid = 0;
    // 存储回调函数
    callBackMapping = {};
    // 标识是否连接成功
    connected = false;
    // 记录重试的次数
    sendRetryCount = 0;
    // 重新连接尝试的次数
    connectRetryCount = 0;
    //  定义连接服务器的方法
    connect(id) {
        // 连接服务器
        if (!window.WebSocket) {
            return console.log('您的浏览器不支持WebSocket');
        }
        this.wid = id;
        // let token = $.cookie('123');
        // let token = '4E6EF539AAF119D82AC4C2BC84FBA21F';
        // let url = 'ws://192.168.31.170:8080/ws/asset/' + id;
        let url = 'ws://43.139.8.56:8080/ws/asset/' + id;
        this.ws = new WebSocket(url);
        // 连接成功的事件
        this.ws.onopen = () => {
            console.log('连接服务端成功了');
            this.connected = true;
            // 重置重新连接的次数
            this.connectRetryCount = 0;
        };
        // 1.连接服务端失败
        // 2.当连接成功之后, 服务器关闭的情况
        this.ws.onclose = () => {
            console.log('断开连接服务端');
            this.connected = false;
            this.connectRetryCount++;
            setTimeout(() => {
                console.log("尝试重新连接第" + (this.connectRetryCount + 1) + "次");
                if (this.connectRetryCount == 5) {
                    console.log('尝试连接5次，停止链接');
                    this.connectRetryCount = 0
                    return;
                }
                this.connect(this.wid);
            }, 500 * this.connectRetryCount);
        };
        // 得到服务端发送过来的数据
        this.ws.onmessage = msg => {
            console.log('从服务端获取到了数据:', msg.data);
            let data = JSON.parse(msg.data)
            if ('eventName' in data) {
                if (data.eventName == 'error') {
                    notification.open({
                        message: 'ERROR',
                        description:
                            data.message,
                        duration: 0
                    });
                } else if (data.eventName == 'warn') {
                    notification.open({
                        message: 'WARN',
                        description:
                            data.message,
                        duration: 0
                    });
                } else if (data.eventName == 'info') {
                    notification.open({
                        message: 'INFO',
                        description:
                            data.message,
                        duration: 0
                    });
                } else if (data.eventName == 'message') {
                    notification.open({
                        message: 'MESSAGE',
                        description:
                            data.message,
                        duration: 0
                    });
                } else if (data.eventName == 'exit') {
                    logout().then(res => {
                        sessionStorage.removeItem("user")
                    }).catch(err => {
                        console.log(err)
                    });
                }
            } else {
                // console.log(msg.data);
                localStorage.setItem(data.id, msg.data)
            }


            //保存
            // if (data.code == 200) {
            //     if (data.data && data.data.CuTemperature) {
            //         // sessionStorage.setItem("deviceData", JSON.stringify(data.data))
            //         // console.log('deviceData', sessionStorage.getItem("deviceData"));
            //         this.deviceData = data.data
            //     }
            // }
            return true;
        };
    }
    // 回调函数的注册
    registerCallBack(socketType, callBack) {
        this.callBackMapping[socketType] = callBack;
    }
    // 取消某一个回调函数
    unRegisterCallBack(socketType) {
        this.callBackMapping[socketType] = null;
    }
    // 发送数据的方法
    send(data) {
        // 判断此时此刻有没有连接成功
        if (this.connected) {
            this.sendRetryCount = 0;
            try {
                // this.ws.send(JSON.stringify(data));
                this.ws.send(data);
            } catch (e) {
                this.ws.send(data);
            }
        } else {
            console.log("链接异常，发送失败");
            this.sendRetryCount++;
            setTimeout(() => {
                console.log("尝试重新发送第" + (this.sendRetryCount + 1) + "次");
                if (this.sendRetryCount == 5) {
                    console.log('尝试发送5次，停止发送');
                    this.sendRetryCount = 0
                    return;
                }
                this.send(data);
            }, this.sendRetryCount * 500);
        }
    }
}
