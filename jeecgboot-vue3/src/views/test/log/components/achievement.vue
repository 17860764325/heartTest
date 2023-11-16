<template>
  <BasicModal v-bind="$attrs" @register="registerModal" destroyOnClose title="成绩查看" :width="800"
              @ok="handleOk">
    <Description title="成绩详情查看"
                 :collapseOptions="{ canExpand: true, helpMessage: '成绩详情查看🔎' }" :column="3"
                 :data="item" :schema="schema"/>
  </BasicModal>
</template>

<script lang="ts" setup>
import {ref, computed, unref, reactive} from 'vue';
import {BasicModal, useModalInner} from '/@/components/Modal';
import {getAchievementStandApi} from "../../achievement/AchievementStandard.api"
import {DescItem, Description} from "@/components/Description";
import {render} from "@/utils/common/renderUtils";
// Emits声明
const emit = defineEmits([]);
// 展示数据
const item = ref({})
//表单赋值
const [registerModal, {setModalProps, closeModal}] = useModalInner(async (data) => {
  // 后端书写方法，根据成绩 log 将对应的试卷的评判标准，返回来，并且渲染到页面上
  await getAchievementStandApi(data).then((res) => {
    console.log(res)
    const dataParamReturn = {
      createBy: data.createBy,
      totalScore: data.totalScore,
      paperType: data.paperType,
      createTime: data.createTime,
      achievementName: res.achievementName,
      achievementCode: res.achievementCode,
      psychologicalState: res.psychologicalState,
      high: res.high,
      low: res.low,
      comment: res.comment,
    }
    item.value = dataParamReturn
  })
});

// 成绩详情字段值
const schema: DescItem[] = [
  {
    field: 'createBy',
    label: '用户',
  },
  {
    field: 'totalScore',
    label: '总得分',
  },
  {
    field: 'paperType',
    label: '试卷类型',
    render: (curVal, data) => {
      return render.renderDict(data.paperType, 'test_paper_type');
    },
  },
  {
    field: 'createTime',
    label: '答题时间',
  },
  {
    field: 'achievementName',
    label: '成绩评判标准名称',
  },
  {
    field: 'achievementCode',
    label: '成绩评判标准编码',
    // render: (curVal, data) => {
    //   return `${data.username}-${curVal}`;
    // },
  },
  {
    field: 'psychologicalState',
    label: '心理状态',
    render: (curVal, data) => {
      return render.renderDict(data.psychologicalState, 'heart_type');
    },
  },
  {
    field: 'high',
    label: '区间最高值',
  },
  {
    field: 'low',
    label: '区间低值',

  },
  {
    field: 'comment',
    label: '指导：',
  },
];

function handleOk(){
  closeModal();
}
</script>

<style lang="less" scoped>
/** 时间和数字输入框样式 */
:deep(.ant-input-number) {
  width: 100%
}

:deep(.ant-calendar-picker) {
  width: 100%
}
</style>
