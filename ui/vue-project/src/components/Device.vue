<script setup>
import { ref, reactive, onMounted, computed } from 'vue';
import { addDevice, deviceList, getModel, findDeviceByGroup, groupList, deleteInfoByName, offline, edit } from "@/api/device";
import nothing from '@/components/nothing.vue';
import onlyRead from './io/onlyRead.vue';
import onlyWriete from './io/onlyWriete.vue';
import rw from './io/rw.vue';
import { message } from 'ant-design-vue';
// <!-- 页面相关 -->
onMounted(() => {
  getList();
  getGroupList();
});
const getList = () => {
  deviceList().then(res => {
    data.value = res;
  }).catch(err => {
  });
}
// <!-- 设备新增 -->
const visualize = ref(false);
const add = () => {
  visualize.value = true;
};
const commit = () => {
  addDevice(newDev).then(res => {
    newDev.alias = '';
    newDev.groupName = '';
    newDev.modelList = [
      {
        attribute: '',
        alias: '',
        io: '',
        maxValue: '',
        minValue: '',
        scale: '',
        firstCommand: '',
        secondCommand: '',
      }
    ];
    getList();
    visualize.value = false;
    message.info('您的设备编号与系统订阅是 ' + res.tag);
  }).catch(err => {
  });

};
let newDev = reactive({
  alias: '',
  groupName: '',
  modelList: [
    {
      attribute: '',
      alias: '',
      io: '',
      maxValue: '',
      minValue: '',
      scale: '',
      firstCommand: '',
      secondCommand: '',
    }
  ]
});
const addList = () => {
  newDev.modelList.push({
    attribute: '',
    alias: '',
    io: '',
    maxValue: '',
    minValue: '',
    scale: '',
    firstCommand: '',
    secondCommand: '',
  });
};
const subList = (index) => {
  newDev.modelList.splice(index, 1);
};
// <!-- 分组 -->
const options1 = reactive([
]);
// 查组
const getGroupList = () => {
  groupList().then(res => {
    for (let i = 0; i < res.length; ++i) {
      options1.push(
        {
          value: res[i].groupName,
          label: res[i].groupName
        }
      )
    }

  }).catch(err => {
  });
};
// 按组查
const handleChange = value => {
  findDeviceByGroup(value).then(res => {
    data.value = res;
  }).catch(err => {
  });
};

const data = ref([
]);
// <!-- 设备详情 -->
const visible = ref(false);
const delDevice = (record) => {
  deleteInfoByName(record.deviceName).then(res => {
    // getList();
    data.value = data.value.filter(item => item.id !== record.id);
  }).catch(err => {
  });
};
const showModal = (record) => {
  visible.value = true;
  info.id = record.id;
  info.deviceName = record.deviceName;
  info.groupName = record.groupName;
  info.alias = record.alias;
  info.status = record.status;
  info.createDate = record.createDate;
  info.lastModifiedDate = record.lastModifiedDate;

  getModel(info.id).then(res => {
    dataSource = res;
    for (let i = 0; i < res.length; ++i) {
      if (res[i].io == 2) {
        iot2.value.push(
          {
            deviceName: info.deviceName,
            firstCommand: res[i].firstCommand,
            secondCommand: res[i].secondCommand,
            attribute: res[i].attribute,
            alias: res[i].alias
          }
        )
      };
      if (res[i].io == 1) {
        iot1.value.push(
          {
            deviceName: info.deviceName,
            scale: res[i].scale,
            attribute: res[i].attribute,
            alias: res[i].alias
          }
        )
      };
      if (res[i].io == 0) {
        iot0.value.push(
          {
            deviceName: info.deviceName,
            maxValue: res[i].maxValue,
            minValue: res[i].minValue,
            attribute: res[i].attribute,
            alias: res[i].alias,
            firstCommand: res[i].firstCommand,
            secondCommand: res[i].secondCommand
          }
        )
      };
    }
  }).catch(err => {
  });
  //一个api 向后端传递数据，并让java从websocket发送数据，然后知道是需要什么设备的数据，传用户的id，设备的uid等
};
const handleOk = e => {
  visible.value = false;
  handleCancel();
};
const handleCancel = e => {
  iot0.value = [];
  iot1.value = [];
  iot2.value = [];
};
const activeKey = ref('1');
let info = reactive({
  id: null,
  deviceName: null,
  alias: null,
  groupName: null,
  status: null,
  createDate: null,
  lastModifiedDate: null
});

// 编辑信息
const isShow = ref(false)
const confirmLoading = ref(false);
const editableData = ref({});
const editInfo = record => {
  editableData.value.id = record.id;
  editableData.value.deviceName = record.deviceName;
  editableData.value.alias = record.alias;
  editableData.value.groupName = record.groupName;
  isShow.value = true;
};
const saveE = () => {
  console.log(data.value.length)
  // confirmLoading.value = true;
  edit(editableData.value).then(res => {
    getList();
    isShow.value = false;
  }).catch(err => {
  });
  // setTimeout(() => {
  //   isShow.value = false;
  //   confirmLoading.value = false;
  // }, 2000);
};
const cancelE = () => {
  editableData.value = {}
  isShow.value = false;
};

// <!-- 设备模型 -->
let dataSource = reactive([
]);

let mdCls = reactive([
  {
    title: '名称',
    dataIndex: 'alias',
    key: 'alias',
  },
  {
    title: '标记',
    dataIndex: 'attribute',
    key: 'attribute',
  },
  {
    title: '数据单位',
    dataIndex: 'scale',
    key: 'scale',
  },
]);


// <!-- 设备状态 -->
// <!-- 设备数据 -->
let iot0 = ref([
]);
let iot1 = ref([
]);
let iot2 = ref([
]);

const lostConnet = (record) => {
  offline(record.deviceName).then(res => {
    getList();
  }).catch(err => {
    console.log(err)
  });

};


// 后期修补 
/** 重置按钮操作 */
const resetQuery = () => {
  getList();
};
const cancelTip = e => {
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
 
<template>
  <!-- <a-space style="width: 100%"> -->
    <a-button type="primary" ghost @click="add()">设备新增</a-button>
    按组查询：
    <a-select ref="select" v-model:value="value1" style="width: 120px" :options="options1" @focus="focus"
      @change="handleChange">
    </a-select>
    &nbsp;
    <a-button type="primary" ghost @click="resetQuery">重置搜索</a-button>
    &nbsp;
    <a-button type="primary" ghost @click="getList">重置</a-button>
  <!-- </a-space> -->

  <!-- 设备新增 -->
  <a-modal v-model:visible="visualize" title="新增设备" @ok="commit">
    基本信息<br />
    <a-input v-model:value="newDev.alias" placeholder="设备名" />
    <a-input v-model:value="newDev.groupName" placeholder="所属组" />
    连接协议：<a-radio checked="true">MQTT</a-radio>
    <div v-for="(item, i) in newDev.modelList">
      <a-form-item label="属性:" prop="attribute">
        <a-input v-model:value="newDev.modelList[i].attribute" placeholder="属性标识" />
        <a-input v-model:value="newDev.modelList[i].alias" placeholder="属性名" />
        设备类型：<a-radio-group v-model:value="newDev.modelList[i].io">
          <a-radio :value="0">滑动写入</a-radio>
          <a-radio :value="1">可读</a-radio>
          <a-radio :value="2">开关指令</a-radio>
        </a-radio-group>
        <!-- <a-input v-model:value="newDev.modelList[i].io" placeholder="0读写,1可读,2可写" /> -->
        <a-input v-model:value="newDev.modelList[i].minValue" placeholder="最小值" />
        <a-input v-model:value="newDev.modelList[i].maxValue" placeholder="最大值" />
        <a-input v-model:value="newDev.modelList[i].scale" placeholder="数据单位" />
        <a-input v-model:value="newDev.modelList[i].firstCommand" placeholder="默认指令" />
        <a-input v-model:value="newDev.modelList[i].secondCommand" placeholder="第二指令" />
      </a-form-item>
      <a-button @click="addList">+</a-button>
      <a-button @click="subList(i)" v-if="i > 0">-</a-button>
    </div>
  </a-modal>
  <!-- 新增系统订阅 -->

  <!-- 设备列表 -->
  <a-table :data-source="data" :pagination="pagination" @change="handleTableChange">

    <a-table-column key="alias" title="设备名称" data-index="alias" />
    <a-table-column key="groupName" title="所属组" data-index="groupName" />

    <a-table-column key="status" title="状态" data-index="status">
      <template #default="{ text: status }">
        <span>
          <a-tag v-if="status == 'online'" :key="tag" color="blue">{{ status }}</a-tag>
          <a-tag v-if="status == 'offline'" :key="tag" color="red">{{ status }}</a-tag>
        </span>
      </template>
    </a-table-column>

    <a-table-column key="action" title="操作">
      <template #default="{ record }">
        <span>
          <a-button type="primary" @click="lostConnet(record)">断连</a-button>
          <a-divider type="vertical" />
          <a-button type="dashed" @click="editInfo(record)">编辑</a-button>
          <a-divider type="vertical" />
          <a-button type="dashed" @click="showModal(record)">详情</a-button>
          <a-divider type="vertical" />
          <a-popconfirm title="您已经确认删除了吗?" ok-text="Yes" cancel-text="No" @confirm="delDevice(record)"
            @cancel="cancelTip">
            <!-- <a href="#">删除</a> -->
            <a-button type="primary" danger>删除</a-button>
          </a-popconfirm>
        </span>
      </template>
    </a-table-column>

  </a-table>


  <!-- 设备详情 -->
  <a-modal v-model:visible="visible" :title="info.alias" @ok="handleOk" @cancel="handleCancel">
    <a-tabs v-model:activeKey="activeKey">

      <a-tab-pane key="1" tab="设备详情">
        <table class="mailTable" cellspacing="0" cellpadding="0">
          <tr>
            <td class="column">设备别名</td>
            <td>{{ info.alias }}</td>
          </tr>
          <tr>
            <td class="column">设备UUID</td>
            <td>{{ info.deviceName }}</td>
          </tr>
          <tr>
            <td class="column">所属组</td>
            <td>{{ info.groupName }}</td>
          </tr>
          <tr>
            <td class="column">创建时间</td>
            <td>{{ info.createDate }}</td>
          </tr>
          <tr>
            <td class="column">最近更新时间</td>
            <td>{{ info.lastModifiedDate }}</td>
          </tr>
        </table>
      </a-tab-pane>


      <!-- 设备模型 -->
      <a-tab-pane key="2" tab="设备模型">
        <a-table :dataSource="dataSource" :columns="mdCls" />
      </a-tab-pane>


      <!-- 设备状态 -->
      <a-tab-pane key="3" tab="设备状态">
        <!-- 设备状态：{{ info.status }} -->
        <a-col :span="14">
          <a-statistic title="设备状态：" :value="info.status" style="margin-right: 50px" />
        </a-col>
      </a-tab-pane>


      <!-- 设备数据 -->
      <a-tab-pane key="4" tab="设备数据">
        <!-- <nothing v-if="info.status === 'off-line'"/> -->
        <div>

          <div v-for="(item, i) in iot2">
              <a-card size="small" :title="item.alias">
                <onlyWriete :deviceName="item.deviceName" :attribute="item.attribute" :alias="item.alias"
                  :firstCommand="item.firstCommand" :secondCommand="item.secondCommand" />
              </a-card>
            <br />
          </div>

          
          <div v-for="(item, i) in iot1">
              <a-card size="small" :title="item.alias">
                <onlyRead :deviceName="item.deviceName" :attribute="item.attribute" :alias="item.alias"
                  :scale="item.scale" />
              </a-card>
              <br />
          </div>

          <div v-for="(item, i) in iot0">
              <a-card size="small" :title="item.alias" >
                <rw :deviceName="item.deviceName" :attribute="item.attribute" :alias="item.alias" :min="item.minValue"
                  :max="item.maxValue" :firstCommand="item.firstCommand" :secondCommand="item.secondCommand" />
              </a-card>
              <br />
          </div>

        </div>
      </a-tab-pane>

    </a-tabs>
  </a-modal>

  <a-modal v-model:visible="isShow" title="编辑信息" :confirm-loading="confirmLoading" @ok="saveE" @cancel="cancelE">
    <a-form :model="editableData" name="basic" autocomplete="off">

      <a-form-item label="设备别名" name="alias" :rules="[{ required: true, message: '请填入设备别名' }]">
        <a-input v-model:value="editableData.alias" />
      </a-form-item>

      <a-form-item label="所属组名" name="groupName" :rules="[{ required: true, message: '请填入所属组名' }]">
        <a-input v-model:value="editableData.groupName" />
      </a-form-item>

    </a-form>
  </a-modal>
</template>

<style>
/* <!-- 新增设备 --> */
/* <!-- 设备显示 --> */
/* <!-- 设备详情 --> */
.mailTable {
  width: 100%;
  border-top: 1px solid #E6EAEE;
  border-left: 1px solid #E6EAEE;
}

.mailTable tr td {
  width: 200px;
  height: 35px;
  line-height: 35px;
  box-sizing: border-box;
  padding: 0 10px;
  border-bottom: 1px solid #E6EAEE;
  border-right: 1px solid #E6EAEE;
}

.mailTable tr td.column {
  background-color: #EFF3F6;
  color: #393C3E;
  width: 30%;
}

</style>