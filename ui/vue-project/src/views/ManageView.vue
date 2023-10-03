<template>
    <a-layout>
        <a-layout-header :style="{ position: 'fixed', zIndex: 1, width: '100%' }">
            
            <a-menu theme="dark" mode="horizontal" v-model:selectedKeys="selectedKeys" :style="{ lineHeight: '64px' }">
                <a-menu-item key="1" @click="changeNav('User')">用户管理</a-menu-item>
                <a-menu-item key="2" @click="changeNav('Log')">日志管理</a-menu-item>
                <!-- <a-menu-item key="2" @click="changeNav('Log')">日志管理</a-menu-item>
                <a-menu-item key="3" @click="changeNav('Setting')">拓展设置</a-menu-item> -->
            </a-menu>
        </a-layout-header>

        <!-- //主体 -->
        <a-layout-content :style="{ marginTop: '16px' }">
            <a-breadcrumb :style="{ margin: '16px 0' }">
                <a-breadcrumb-item>Manage</a-breadcrumb-item>
                <a-breadcrumb-item>List</a-breadcrumb-item>
            </a-breadcrumb>
            <div :style="{ background: '#fff', padding: '24px', minHeight: '380px' }">

                <keep-alive>
                    <component :is="typeComponentMap[currentTabName]"></component>
                </keep-alive>

            </div>
        </a-layout-content>
    </a-layout>

</template>

<script setup>
import { ref } from 'vue';
import User from '@/components/User.vue';
import Log from '@/components/Log.vue';
import nothing from '@/components/nothing.vue';

const typeComponentMap = {
    'User': User,
    'Log': Log
}
const currentTabName = ref('User');
let changeNav = (item) => {
    currentTabName.value = item
}

</script>
<style>
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