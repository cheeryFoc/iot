<template>
    <a-table rowKey="record => record.id" :data-source="data" :pagination="pagination" @change="handleTableChange">

        <a-table-column key="log" title="日志" data-index="log" />

        <a-table-column key="action" title="操作">
            <template #default="{ record }">
                <span>
                    <a-popconfirm title="您已经确认删除了吗?" ok-text="Yes" cancel-text="No" @confirm="handleDelete(record)"
                        @cancel="cancel">
                        <a-button danger>删除</a-button>
                    </a-popconfirm>
                    <a-divider type="vertical" />
                    <a-button type="dashed" @click="openFile(record.log)">查看</a-button>
                    <a-divider type="vertical" />
                    <a-button type="dashed" @click="downLoad(record.log)">下载</a-button>
                </span>
            </template>
        </a-table-column>

    </a-table>
</template>

<script setup>
import { ElMessage } from 'element-plus';
import { ref, reactive, onMounted, nextTick } from 'vue';
import nothing from '@/components/nothing.vue';
import { getFiles, deleteFile, fileDownLoad } from "@/api/log";

onMounted(() => {
    getList();
    setTimeout(() => {
    }, 3000);
});
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
/** 查询列表 */
const getList = () => {
    getFiles().then(res => {
        for (let i = 0; i < res.length; ++i) {
            let obj = {id: i,log: res[i]}
            data.value.push(obj)
        }
    }).catch(err => {
        console.log(err);
    });
};

const handleDelete = record => {
    deleteFile(record.log).then(res => {
        data.value = data.value.filter(item => item.id !== record.id);
    }).catch(err => {
        ElMessage({
                type: 'error',
                message: '删除失败了'
        })
    });
};
const openFile = fileName => {
    window.open('http://43.139.8.56:8080/logs/'+ fileName, '_blank', 'width=800, height=600','charset=UTF-8');
};
const downLoad = fileName => {
    fileDownLoad(fileName).then(res => {
        console.log("res",res);
        const content = res;
        const blob = new Blob([content]);
        console.log("blob",blob);
        if ("download" in document.createElement("a")) {
            const elink = document.createElement("a");
            elink.download = fileName;
            elink.style.display = "none";
            elink.target = "_blank";
            elink.href = URL.createObjectURL(blob);
            console.log("下载地址",URL.createObjectURL(blob));
            document.body.appendChild(elink);
            elink.click();
            URL.revokeObjectURL(elink.href);//释放
            document.body.removeChild(elink);
        } else {
            navigator.msSaveBlob(blob, fileName);//向下兼容IE ，可能会翻车
        }
    }).catch(err => {
        ElMessage({
                type: 'error',
                message: '文件下载失败'
        })
    });
};
const data = ref([])
</script>