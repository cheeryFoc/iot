<template>
    <div>
        <a-row>
            <a-col :span="12">
                {{ alias }}
            </a-col>
            <a-col :span="12">
                <a-switch v-model:checked="checked1" :checked-children="firstCommand"
                    :un-checked-children="secondCommand" />
            </a-col>
        </a-row>
    </div>
</template>

<script setup>
import { ref, reactive, toRefs, onMounted, onUpdated, watch } from 'vue';
import { sendMsg, getData } from '@/api/myMqtt'

// 接收父组件传递过来的值
const props = defineProps([
    'deviceName',
    'attribute',
    'alias',
    'firstCommand',
    'secondCommand'
]);

const checked1 = ref(false);
const deviceName = props.deviceName;
const attribute = props.attribute;
const alias = props.alias;
const firstCommand = props.firstCommand;
const secondCommand = props.secondCommand;

onMounted(() => {
    window.addEventListener("setItemEvent", function (e) {
        if (e.key == deviceName) {
            // let data = e.newValue;
            let data = JSON.parse(e.newValue)
            // console.log( e.newValue);
            // if (data.attribute == alias) {
            //     value.value = data.value;
            //     console.log('更新了:',data.value);
            // }

            if (data.attribute == attribute) {
                checked1.value = data.command == firstCommand ? false : true
            }
        }
    })
    init();
});
// onUpdated(() => {
//     init();
// });
watch(checked1, (newVal, oldVal) => {
    sendData(newVal);
});

let msg = {
    'data': '',
    'topic': 'server/' + deviceName
}
//初始化事件
const init = () => {
    //从数据库里读
    getData(deviceName + "io2").then(res => {
        if (res != null && res != '') {
            let data = JSON.parse(res[attribute]);

            if (data.attribute == attribute) {
                if (data.command == 'on') {
                    checked1.value = true;
                } else {
                    checked1.value = false;
                }
            }
        }
    }).catch(err => {
        console.log(err);
    });
};
// 发送指令
const sendData = (newVal) => {
    let temp = {
        'event': 'control',
        'attribute': attribute,
        'control': newVal ? secondCommand : firstCommand
    };
    msg.data = JSON.stringify(temp);
    sendMsg(msg).then(res => {
        //待处理
        console.log("ok");
    }).catch(err => {
        console.log(err);
    });
};
</script>