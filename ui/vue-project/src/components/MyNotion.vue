<template>
    <a-table rowKey="record => record.id" :columns="columns" :data-source="data" :pagination="pagination"
        @change="handleTableChange" :scroll="{ x: 1500, y: 1000 }">

        <a-table-column key="eventName" title="事件类型" data-index="eventName"/>
        <a-table-column key="message" title="消息" data-index="message" />
        <a-table-column key="deviceName" title="来自设备" data-index="deviceName" />
        <a-table-column key="createDate" title="发生时间" data-index="createDate" />
        <a-table-column key="userName" title="处理人" data-index="userName" />
        <a-table-column key="action" title="操作">
            <template #default="{ record }">
                <a-button type="link" v-if="record.eventName === 'error'"
                    :disabled="record.userName != null && record.userName != ''"
                    @click="promotions(record.id)">已处理</a-button>
                <a-button type="link" v-if="record.eventName === 'warn'" @click="deleteEvent(record.id)">已确认</a-button>
            </template>
        </a-table-column>
    </a-table>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue';
import { notionList, findNotionByGroup, operateById, deleteById } from "@/api/notion";
import nothing from '@/components/nothing.vue';
import { message } from 'ant-design-vue';
import { ElMessage } from 'element-plus';


onMounted(() => {
    getList();
});
const getList = () => {
    notionList().then(res => {
        data.value = res;
    }).catch(err => {
        ElMessage({
            type: 'error',
            message: '暂时还没有相关数据嗷'
        })
    });
}

//消息处理
const promotions = value => {
    operateById(value).then(res => {
        message.success('处理成功');
        getList();
    }).catch(err => {
    });


};
const deleteEvent = value => {
    deleteById(value).then(res => {
        data.value = data.value.filter(item => item.id !== value);
    }).catch(err => {
    });
};
//数据源和分页
const data = ref([
]);
const pagination = ref({
    total: 0,
    pageSize: 10,//每页中显示10条数据
    showSizeChanger: true,
    pageSizeOptions: ["5", "10", "15"],//每页中显示的数据
    showTotal: total => `共有 ${total} 条数据`,  //分页中显示总的数据
});
const handleTableChange = (pag) => {
    console.log(pag);
    pagination.value.current = pag.current;
    pagination.value.pageSize = pag.pageSize;
}
</script>

<style></style>