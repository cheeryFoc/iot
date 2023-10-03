<template>
    <div>
        <a-row>
            <a-col :span="12">
                {{ alias }}
            </a-col>
            <a-col :span="12">
                <a-switch v-model:checked="checked1" :checked-children="firstCommand" :un-checked-children="secondCommand"
                    v-if="firstCommand !== null && firstCommand !== ''" />
            </a-col>
        </a-row>
        <a-slider v-model:value="value" :max="max" :min="min" />
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
    'min',
    'max',
    'firstCommand',
    'secondCommand'
]);
const checked1 = ref(false);

const deviceName = props.deviceName;
const attribute = props.attribute;
const alias = props.alias;
const min = props.min;
const max = props.max;
const firstCommand = props.firstCommand;
const secondCommand = props.secondCommand;
const value = ref(min);

onMounted(() => {
    window.addEventListener("setItemEvent", function (e) {
        if (e.key == deviceName) {
            let data = JSON.parse(e.newValue)

            if (data.attribute == attribute) {
                checked1.value = data.command == firstCommand ? false : true;
                if (checked1.value) {
                    value.value = data.value;
                } else {
                    value.value = min;
                }
            }
        }
    })
    init();
});
// onUpdated(() => {
//     init();
// });
watch([checked1, value], ([newChk, newVal], [oldChk, oldVal]) => {
    sendData(newChk, newVal);
});

let msg = {
    'data': '',
    'topic': 'server/' + deviceName
}
//初始化事件
const init = () => {
    //从数据库里读
    getData(deviceName + "io3").then(res => {
        if (res != null && res != '') {
            let data = JSON.parse(res[attribute]);

            if (data.attribute == attribute) {
                if (data.command == 'on') {
                    checked1.value = true;
                    value.value = data.value;
                } else {
                    checked1.value = false;
                    value.value = min;
                }
            }
        }
    }).catch(err => {
        console.log(err);
    });
};
// 发送指令
const sendData = (newChk, newVal) => {
    let temp = {
        'event': 'control',
        'attribute': attribute,
        'control': newChk ? secondCommand : firstCommand,
        'value': newVal
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