<script setup>
import { ref, reactive, onMounted, nextTick } from 'vue';
import { removeTokenAUTH, getTokenAUTH } from '@/utils/auth';
import { logout } from '@/api/authentication';
import { getUser, updatePwd, updateInfo, deleteUser } from "@/api/users";
import { useRouter } from 'vue-router';
import nothing from '@/components/nothing.vue';
import { ElMessage } from 'element-plus';

onMounted(() => {
    if (sessionStorage.getItem("user") == null) {
    } else {
        getInfo();
    }
});

const router = useRouter();

/** 查询列表 */
const getInfo = () => {
    let data = JSON.parse(getTokenAUTH("user"));
    arr.value = data.roles;
    getUser(data.userName).then(res => {
        user.value = res;
    }).catch(err => {
    });
};

const user = ref({
});
const arr = ref([]);
const visible2 = ref(false);
const edit = () => {
    visible2.value = true;
};
// api 修改信息
const handleOk2 = () => {
    delete user.value.createDate;
    user.value.userName = user.value.userName.replace(/\s*/g, "");
    user.value.nickName = user.value.nickName.replace(/\s*/g, "");
    user.value.phone = user.value.phone.replace(/\s*/g, "");
    user.value.email = user.value.email.replace(/\s*/g, "");
    updateInfo(user.value).then(res => {
        getInfo();
        visible2.value = false;
    }).catch(err => {
        getInfo();
        visible2.value = false;
        console.log(err);
    });
};

let accountDTO = reactive({
    id: 0,
    userName: '',
    password: '',
    newPassword: ''
});
const visible = ref(false);
const alterPwd = () => {
    accountDTO.id = user.value.id;
    accountDTO.userName = user.value.userName;
    visible.value = true;
};
// api 修改密码
const handleOk = () => {
    accountDTO.password = accountDTO.password.replace(/\s*/g, "");
    accountDTO.newPassword = accountDTO.newPassword.replace(/\s*/g, "");
    updatePwd(accountDTO).then(res => {
        visible.value = false;
        exit();
    }).catch(err => {
        console.log(err)
    });
};
// api logout
const exit = () => {
    logout().then(res => {
        removeTokenAUTH("token")
        removeTokenAUTH("user")
        router.push({
            path: '/'
        })
    }).catch(err => {
        ElMessage({
            type: 'error',
            message: '退出失败'
        })
    });
};
// 注销帐号
const destroy = () => {
    deleteUser(user.value.userName).then(res => {
        removeTokenAUTH("token")
        removeTokenAUTH("user")
        router.push({
            path: '/'
        })
    }).catch(err => {
        ElMessage({
            type: 'error',
            message: '注销帐号失败'
        })
    });
};
const cancel = e => {
};
</script>

<template>
    <a-layout>
        <a-layout-header :style="{ position: 'fixed', zIndex: 1, width: '100%' }">
            <div class="logo" />
            <a-menu theme="dark" mode="horizontal" v-model:selectedKeys="selectedKeys" :style="{ lineHeight: '64px' }">
                <a-menu-item key="1">用户信息</a-menu-item>
            </a-menu>
        </a-layout-header>


        <a-layout-content :style="{ padding: '0 20px', marginTop: '16px' }">
            <a-breadcrumb :style="{ margin: '16px 0' }">
                <a-breadcrumb-item>about</a-breadcrumb-item>
                <a-breadcrumb-item>me</a-breadcrumb-item>
            </a-breadcrumb>
            <div :style="{ background: '#fff', padding: '24px', minHeight: '380px' }">
                <a-card title="关于我">
                    <p style="font-size: 14px; color: rgba(0, 0, 0, 0.85); margin-bottom: 16px; font-weight: 500">
                        个人信息
                    </p>
                    <a-card title="基本信息">
                        <p>用户名: {{ user.userName }}</p>
                        <p>昵 称: {{ user.nickName }}</p>
                        <p>邮 箱: {{ user.email }}</p>
                        <p>电 话: {{ user.phone }}</p>
                        <p>权 限: {{ user.roles }}</p>
                    </a-card>

                    <a-card title="个人操作" :style="{ marginTop: '16px' }">
                            <a-button type="primary" size="smail" @click="edit">编辑信息</a-button>
                            &nbsp;
                            <a-button type="primary" size="smail" @click="alterPwd">修改密码</a-button>
                            &nbsp;
                            <a-button size="smail" danger @click="exit">退出登录</a-button>
                            &nbsp;
                            <a-popconfirm title="您已经确认删除了吗?" ok-text="Yes" cancel-text="No" @confirm="destroy()"
                                @cancel="cancel">
                                <a-button size="smail" danger v-if="!arr.some(item => item == 'ROLE_ROOT')">注销帐号</a-button>
                            </a-popconfirm>
                    </a-card>
                </a-card>

            </div>
        </a-layout-content>
    </a-layout>

    <div>
        <a-modal v-model:visible="visible" title="修改密码" @ok="handleOk">
            <a-input v-model:value="accountDTO.password" placeholder="请输入原密码" />
            <a-input v-model:value="accountDTO.newPassword" placeholder="请输入新密码" />
        </a-modal>
    </div>

    <div>
        <a-modal v-model:visible="visible2" title="编辑信息" @ok="handleOk2">
            用户名:<a-input disabled="true" v-model:value="user.userName" />
            昵 称:<a-input v-model:value="user.nickName" />
            邮 箱:<a-input v-model:value="user.email" />
            电 话:<a-input v-model:value="user.phone" />
        </a-modal>
    </div>
</template>

<style scoped></style>
