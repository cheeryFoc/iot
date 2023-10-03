<script setup>
import { onMounted, onUnmounted, nextTick } from 'vue';
import { RouterLink, RouterView, useRouter } from 'vue-router'
import { removeTokenAUTH,getTokenAUTH } from './utils/auth';
import { logout } from './api/authentication';

onMounted(() => {
  // window.addEventListener('beforeunload', e => beforeunloadHandler(e));
  // window.addEventListener('unload', e => unloadHandler(e));
  localStorage.clear();
});

// onUnmounted(() => {
//   window.removeEventListener('beforeunload', e => beforeunloadHandler(e));
//   window.removeEventListener('unload', e => unloadHandler(e));
// });

// const beforeunloadHandler = (e) => {
//   exit();
//   window.close();
// };

// const unloadHandler = (e) => {
//   // 暂时不用
// }

// 
const router = useRouter();

const home = () => {
  router.push({
    path: '/'
  })
};

const manage = () => {
  router.push({
    path: '/manage'
  })
};

const about = () => {
  router.push({
    path: '/about'
  })
};

const exit = () => {
  // api logout
  logout().then(res => {
    console.log(res)
    removeTokenAUTH("token")
  }).catch(err => {
    console.log(err)
  });
};
</script>

<template>
  <a-layout>


    <a-layout-content>
      <RouterView />
    </a-layout-content>


    <a-layout-footer :style="{ textAlign: 'center' }">
      <el-button type="success" @click="home" plain>首页</el-button>
      <el-button type="info" @click="manage" plain>管理</el-button>
      <el-button type="danger" @click="about" plain>我的</el-button>
    </a-layout-footer>


  </a-layout>
</template>

<style scoped>

</style>
