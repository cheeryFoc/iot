<script setup>
import { ref, reactive, onMounted } from 'vue';
import { getTokenAUTH } from '@/utils/auth';
import { login } from '@/api/authentication';
import { register } from '@/api/users';
import Device from '@/components/Device.vue';
import MyNotion from '@/components/MyNotion.vue';
import SocketService from '@/utils/websocket';
// ant
import { UserOutlined } from '@ant-design/icons-vue';
import { ElMessage } from 'element-plus';

// websocket
let socketServe = null;
//休眠
const sleep = (time) => {
    return new Promise(resolve => setTimeout(resolve, time))
};
//创建websocket链接，即页面初始化
const init = (id) => {
    socketServe = SocketService.Instance
    socketServe.connect(id)
    sleep(500)
};
// 808080灰色 87d068绿色 头像变色
const loginCol = ref("background-color: #808080");
onMounted(() => {
    if (getTokenAUTH("user") != null) {
        // init(getTokenAUTH('uid'));
        let data = JSON.parse(getTokenAUTH("user"));
        init(data.id);
        loginCol.value = "background-color: #87d068";
    } else {
        loginCol.value = "background-color: #808080";
    }

});

//动态组件
const typeComponentMap = {
    'Device': Device,
    'Notion': MyNotion
}
const currentTabName = ref('Device');
let changeNav = (item) => {
    currentTabName.value = item
}

//选择
const selectedKeys = ref([]);

// 登录 注册 退出
const visible = ref(false);
const innerVisible = ref(false);
let formLogin = reactive({
    userName: '',
    password: '',
    remember: false
});
let formRegister = reactive({
    userName: '',
    nickName: '',
    password: '',
    email: ''
});
const pwd2 = ref('');
let rules = ref({
    userName: [
        { required: true, message: "用户名不能为空", trigger: "blur" },
        { min: 3, max: 6, message: "3-6的字符长度", trigger: "blur" }
    ],
    nickName: [
        { required: true, message: "昵称不能为空", trigger: "blur" },
        { min: 3, max: 6, message: "3-6的字符长度", trigger: "blur" }
    ],
    password: [
        { required: true, message: "密码不能为空", trigger: "blur" },
        { min: 3, max: 6, message: "3-6的字符长度", trigger: "blur" }
    ],
    email: [
        { required: true, message: "邮箱不能为空", trigger: "blur" }
    ],
    phone: [
        { required: true, message: "电话不能为空", trigger: "blur" }
    ]
});

const showModal = () => {
    if (getTokenAUTH("token") != null) {
        open();
    } else {
        visible.value = true;
    }
};
const onSubmit = () => {
    login(formLogin).then(res => {//要注释
        let data = JSON.stringify(res)
        // sessionStorage.setItem('uid', res.id);
        // sessionStorage.setItem('uname', res.userName);
        sessionStorage.setItem('user', data);
        loginCol.value = "background-color: #87d068";
        visible.value = false;
        init(res.id);
        changeNav('Device')
        ElMessage({
                type: 'success',
                message: '登录成功'
            })
    }).catch(err => {
        ElMessage({
                type: 'error',
                message: '您的用户名或密码出错'
            })
    });
};
//注册
const submitR = () => {
    formRegister.userName = formRegister.userName.replace(/\s*/g, "");
    formRegister.nickName = formRegister.nickName.replace(/\s*/g, "");
    formRegister.password = formRegister.password.replace(/\s*/g, "");
    formRegister.email = formRegister.email.replace(/\s*/g, "");
    pwd2.value = pwd2.value.replace(/\s*/g, "");
    if (pwd2.value == formRegister.password) {
        register(formRegister).then(res => {
            innerVisible.value = false;
            resetR();
            ElMessage({
                type: 'success',
                message: '注册成功'
            })
        }).catch(err => {
            ElMessage({
                type: 'error',
                message: '您的注册信息未正确填写'
            })
        });
    } else {
        ElMessage({
            type: 'error',
            message: '两次输入的密码不一致'
        })
    }
};
// 重置
const resetR = () => {
    pwd2.value = "";
    formRegister.userName = "";
    formRegister.nickName = "";
    formRegister.password = "";
    formRegister.email = "";
    formRegister.phone = "";
};
</script>

<template>
    <!-- 主体 -->
    <a-layout>
        <a-layout-header :style="{ position: 'fixed', zIndex: 1, width: '100%' }">
            <div class="logo" />
            <a-menu theme="dark" mode="horizontal" v-model:selectedKeys="selectedKeys" :style="{ lineHeight: '64px' }">
                <a-menu-item key="1" @click="showModal">
                    <a-avatar :style="loginCol">
                        <template #icon>
                            <UserOutlined />
                        </template>
                    </a-avatar>
                </a-menu-item>
                <a-menu-item key="2" @click="changeNav('Device')">设备</a-menu-item>
                <!-- <a-menu-item key="3" @click="changeNav()">任务</a-menu-item> -->
                <a-menu-item key="4" @click="changeNav('Notion')">消息</a-menu-item>
                <!-- <a-menu-item key="10" @click="changeNav('Doc')">文档</a-menu-item> -->
            </a-menu>
        </a-layout-header>

        <a-layout-content :style="{ padding: '0 20px', marginTop: '64px' }">
            <div :style="{ background: '#fff', padding: '24px', minHeight: '380px' }">
                <!-- 在 <script setup> 中要使用动态组件时，需要直接用 :is="Component" 直接绑定到组件本身，而不是字符串的组件名。 -->
                <keep-alive>
                    <component :is="typeComponentMap[currentTabName]"></component>
                </keep-alive>
            </div>
        </a-layout-content>



        <!-- 登录组件 -->
        <el-dialog v-model="visible" title="登录" :show-close="false" width="360" modal="false">



            <el-form label-position="left" label-width="100px" :rules="rules" :model="formLogin" style="max-width: 360px">
                <el-form-item label="用户名">
                    <el-input v-model="formLogin.userName" />
                </el-form-item>
                <el-form-item label="密  码">
                    <el-input v-model="formLogin.password" type="password" />
                </el-form-item>
                <el-form-item label="长期会话">
                    <!-- <div> {{ formLogin }} </div> -->
                    <el-switch v-model="formLogin.remember" />
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="onSubmit">登录</el-button>
                    <el-button type="primary" @click="innerVisible = true">注册</el-button>
                </el-form-item>

            </el-form>

            <el-dialog v-model="innerVisible" width="360" title="注册" append-to-body="true" modal="false">
                <el-form label-position="left" :rules="rules" label-width="100px" :model="formRegister"
                    style="max-width: 360px">
                    <el-form-item label="用户名">
                        <el-input v-model="formRegister.userName" placeholder="用户名长度为2-30"/>
                    </el-form-item>
                    <el-form-item label="昵  称">
                        <el-input v-model="formRegister.nickName" placeholder="昵称长度为2-30"/>
                    </el-form-item>
                    <el-form-item label="密  码">
                        <el-input type="password" v-model="formRegister.password" placeholder="密码长度为6-15"/>
                    </el-form-item>
                    <el-form-item label="确认密码">
                        <el-input type="password" v-model="pwd2" placeholder="密码长度为6-15"/>
                    </el-form-item>
                    <el-form-item label="邮   箱">
                        <el-input v-model="formRegister.email" placeholder="邮箱最大长度为40"/>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="submitR">确定</el-button>
                        <el-button type="primary" @click="resetR">重置</el-button>
                    </el-form-item>
                </el-form>
            </el-dialog>
        </el-dialog>

        <!-- 退出组件 -->


    </a-layout>
</template>

<style scoped>
#components-layout-demo-fixed .logo {
    width: 120px;
    height: 31px;
    background: rgba(255, 255, 255, 0.2);
    margin: 16px 24px 16px 0;
    float: left;
}

.site-layout .site-layout-background {
    background: #fff;
}

[data-theme='dark'] .site-layout .site-layout-background {
    background: #141414;
}
</style>
