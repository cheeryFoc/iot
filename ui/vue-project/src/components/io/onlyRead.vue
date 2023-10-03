<template>
    <div>

        <a-row>
            <a-col :span="12">
                <a-statistic :title="alias" :value="value" style="margin-right: 50px" />
            </a-col>
            <a-col :span="12">
                <a-statistic title="单位" :precision="2" :value="scale" />
            </a-col>
        </a-row>

    </div>
</template>

<script setup>
import { ref, onMounted, watch, initCustomFormatter } from 'vue'
import { useLocalStorage } from '@vueuse/core'
import { sendMsg, getData } from '@/api/myMqtt'

// 接收父组件传递过来的值
const props = defineProps([
    'deviceName',
    'attribute',
    'alias',
    'scale'
])

const value = ref(0);
const deviceName = props.deviceName;
const attribute = props.attribute;
const alias = props.alias;
const scale = props.scale;
// const uls = useLocalStorage(deviceName, {
//   "id": deviceName,
//   "attribute": alias,
//   "value": "22"
// });
// const uls = useLocalStorage(deviceName,{});
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
                value.value = data.value;
            }
        }
    });
    init();
});
// watch(
//     ()=>uls,
//     val => {
//         console.log('val:' ,val)
//         console.log('uls:' ,uls)
//         if (val.id == deviceName) {
//             if (val.attribute == alias) {
//                 value.value = val.value;
//             }
//         }
//     },
//     { deep: true, immediate: true }
// )

//初始化事件
const init = () => {
    //从数据库里读
    getData(deviceName).then(res => {
        if (res != null && res != '') {
            let data = JSON.parse(res[attribute]);

            if (data.attribute == attribute) {
                value.value = data.value;
            }
        }
    }).catch(err => {
        console.log(err);
    });
};
</script>
