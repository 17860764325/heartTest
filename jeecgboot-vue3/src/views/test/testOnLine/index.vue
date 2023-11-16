<template>
  <div v-show="pageShow">
    <a-collapse v-model:activeKey="activeKey">
      <a-collapse-panel v-for="item in paperList" :key="item.id" :header="item.examinationPaper">
        <Description title="试卷介绍" :collapseOptions="{ canExpand: true, helpMessage: '试卷详情🔎' }" :column="3" :data="item" :schema="schema" />
        <div class="startTest">
          <a-button  type="primary" @click="startTestButton(item)">开始测试</a-button>
        </div>
      </a-collapse-panel>
      <!--    禁用案例-->
      <!--    <a-collapse-panel key="3" header="This is panel header 3" collapsible="disabled">-->
      <!--      <p>{{ text }}</p>-->
      <!--    </a-collapse-panel>-->
    </a-collapse>
  </div>
  <Papertitle v-if="!pageShow" :paperInfo="currentPaper" @cancel="handleCancel"></Papertitle>
</template>
<script lang="ts" setup>
import { ref, watch } from 'vue';
import {defHttp} from '/@/utils/http/axios';
import {DescItem, Description} from "@/components/Description";
import {useMessage} from "@/hooks/web/useMessage";

import Papertitle from "./components/title.vue"

// 消息
const {createMessage, createErrorModal, createConfirm} = useMessage();

// 当前试卷信息
const currentPaper = ref({})

//页面隐藏
const pageShow = ref<Boolean>(true)

// 下拉默认展示
const activeKey = ref(['1']);

watch(activeKey, val => {
  console.log(val);
});

// 定义一个试卷 list，展示所有的试卷
const paperList =  ref<Array<any>>([]);
// 获取所有的试卷
function getAll(params){
 defHttp.get({url: '/test/examinationAll/list', params}).then(res => {
   console.log(res.records)
   if (res.records.length !== 0){
     activeKey.value.push(res.records[0].id)
   }
   paperList.value = res.records
 });
}
getAll(null);
// 试卷详情字段值
const schema: DescItem[] = [
  {
    field: 'examinationPaper',
    label: '试卷名称',
  },
  {
    field: 'examinationCode',
    label: '试卷编码',
    // render: (curVal, data) => {
    //   return `${data.username}-${curVal}`;
    // },
  },
  {
    field: 'startTime',
    label: '试卷开始时间',
  },
  {
    field: 'endTime',
    label: '诗卷结束时间',
  },
  {
    field: 'limitTime',
    label: '测评时间',
    render: (curVal, data) => {
      return `${data.limitTime}分钟`;
    },
  },
  {
    field: 'score',
    label: '及格分数',
    render: (curVal, data) => {
      return `${data.score}分`;
    },
  },
  {
    field: 'examinationAbout',
    label: '试卷介绍',
  },
];

// 开始答题按钮
function startTestButton(data){
  createConfirm({
    iconType: 'warning',
    title: '提示',
    content: '是否开始测试？',
    okText: '确认',
    onOk: function () {
      // 隐藏页面
      pageShow.value = false
      // 赋值当前的试卷信息
      currentPaper.value = data
    },
  });
}

// 取消答题
function handleCancel(){
  pageShow.value = true
}

</script>
<style>
.startTest{
  text-align: center;
}
</style>

