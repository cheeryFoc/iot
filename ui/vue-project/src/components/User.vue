<template>
    <a-space style="width: 100%">
        <a-input v-model:value="uName" placeholder="根具姓名搜索" />
        <a-button type="primary" ghost @click="search">搜索</a-button>
        <a-button type="primary" ghost @click="resetQuery">重置搜索</a-button>
    </a-space>
    <!-- :scroll="{ x: 2500, y: 1000 }" :scroll="{ x: '200%', y: 1000 }" size="small" -->
    <a-table rowKey="record => record.id" :data-source="data" :pagination="pagination" @change="handleTableChange"
         size="small" @resizeColumn="handleResizeColumn">

        <a-table-column key="userName" title="用户名" data-index="userName" width='100' minWidth:='100' />
        <a-table-column key="nickName" title="昵称" data-index="nickName" width='100' minWidth:='100' />

        <a-table-column key="roles" title="角色" data-index="roles" >
            <template #default="{ text: roles }">
                <span>
                    <a-tag v-for="role in roles" :key="tag" color="blue">{{ role }}</a-tag>
                </span>
            </template>
        </a-table-column>

        <a-table-column key="email" title="邮箱" data-index="email" width='100' minWidth:='100' />
        <a-table-column key="phone" title="电话" data-index="phone" width='100' minWidth:='100' />
        <a-table-column key="createDate" title="创建时间" data-index="createDate" width='100' minWidth:='100' />
        <!-- maxWidth='200' -->
        <a-table-column key="action" title="操作" width='100' minWidth:='100'>
            <template #default="{ record }">
                <span>
                    <a-popconfirm title="您已经确认删除了吗?" ok-text="Yes" cancel-text="No" @confirm="handleDelete(record)"
                        @cancel="cancel">
                        <!-- <a href="#">删除</a> -->
                        <a-button danger>删除</a-button>
                    </a-popconfirm>
                    <a-divider type="vertical" />
                    <a-button type="dashed" @click="reset(record)">重置密码</a-button>
                    <a-divider type="vertical" />
                    <a-button type="dashed"
                        :disabled="record.roles.some(item => item == 'ROLE_ADMIN') || item == 'ROLE_ROOT'"
                        @click="promotions(record)">升为管理</a-button>
                    <a-divider type="vertical" />
                    <a-button type="dashed" :disabled="record.roles.some(item => item == 'ROLE_USER' || item == '')"
                        @click="demotion(record)">降级</a-button>
                </span>
            </template>
        </a-table-column>

    </a-table>
</template>

<script setup>
import { message } from 'ant-design-vue';
import { ElMessage } from 'element-plus';
import { ref, reactive, onMounted, nextTick } from 'vue';
import { userList, resetPassword, changeIdentity, downgrading } from "@/api/manage";
import { userInfoByName, deleteInfoByName } from "@/api/users";
import nothing from '@/components/nothing.vue';

onMounted(() => {
    getList();
    setTimeout(() => {
    }, 3000);
});
const handleResizeColumn = (w, col) => {
    col.width = w;
};
/** 查询列表 */
const getList = () => {
    userList().then(res => {
        data.value = res;
    }).catch(err => {
    });
};

const handleOk = () => {

}

const data = ref([
]);
const visible = ref(false);
const showModal = (record) => {
    visible.value = true;
}
// 重置密码
const reset = (record) => {
    resetPassword(record.userName).then(res => {
        message.success('密码重置成功');
    }).catch(err => {
        ElMessage({
            type: 'error',
            message: '密码重置失败'
        })
    });
}
// promotions升职
const promotions = (record) => {
    changeIdentity(record.userName).then(res => {
        message.success('设置管理员成功');
        getList();
    }).catch(err => {
        ElMessage({
            type: 'error',
            message: '该用户不能再升级'
        })
    });
}
// demotion降级
const demotion = (record) => {
    downgrading(record.userName).then(res => {
        message.success('降级成功');
        getList();
    }).catch(err => {
        ElMessage({
            type: 'error',
            message: '该用户不能再降级'
        })
    });
}
/** 重置按钮操作 */
const resetQuery = () => {
    uName.value = "";
    getList();
};
/** 删除按钮操作 */
const handleDelete = (record) => {
    deleteInfoByName(record.userName).then(res => {
        // getList();
        data.value = data.value.filter(item => item.id !== record.id);
        message.success('删除成功');
    }).catch(err => {
        console.log(err);
    });
};

const uName = ref('');
const search = () => {
    userInfoByName(uName.value).then(res => {
        // data.value = [];
        // data.value.push(res);
        data.value = res;
    }).catch(err => {
        // message.error('没有匹配的结果');
        ElMessage({
            type: 'error',
            message: '没有匹配的结果'
        })
    });
};
const cancel = e => {
};
const pagination = ref({
    total: 0,
    pageSize: 10,//每页中显示10条数据
    showSizeChanger: true,
    pageSizeOptions: ["3", "5", "10"],//每页中显示的数据
    showTotal: total => `共有 ${total} 条数据`,  //分页中显示总的数据
});
const handleTableChange = (pag) => {
    console.log(pag);
    pagination.value.current = pag.current;
    pagination.value.pageSize = pag.pageSize;

}

</script>
<style>
/* #components-table-demo-size h4 {
  margin-bottom: 16px;
} */
</style>