<template>
  <BasicModal v-bind="$attrs" @register="registerModal" destroyOnClose :title="title" :width="800" @ok="handleSubmit">
    <BasicForm @register="registerForm" ref="formRef" />
    <!-- 子表单区域 -->
    <a-tabs v-model:activeKey="activeKey" animated @change="handleChangeTabs">
      <a-tab-pane tab="题目列表" key="examinationTitle" :forceRender="true">
        <JVxeTable keep-source resizable
                   ref="examinationTitle"
                   :loading="examinationTitleTable.loading"
                    :columns="examinationTitleTable.columns"
                   :dataSource="examinationTitleTable.dataSource"
                   :height="340"
                    :disabled="formDisabled"
                   :rowNumber="true"
                   :rowSelection="true"
                   :toolbar="true">
          <!-- 定义插槽 -->
          <template #titleCodeSlot="props">
            <!-- 点击超链接，出发弹窗 -->
<!--            <a-textare v-if="props.value !== '' || props.value !== undefined || props.value !== null" v-model = "props.value" ></a-textare>-->
            <a-tag>{{"编码："+props.value}}</a-tag>
            <a  @click="chooseTitle(props)">操作：选择题目</a>
          </template>
        </JVxeTable>
      </a-tab-pane>
      <a-tab-pane tab="试卷评判标准" key="examinationPaperAchievementStandard" :forceRender="true">
        <JVxeTable
            keep-source
            resizable
            ref="examinationPaperAchievementStandard"
            :loading="examinationPaperAchievementStandardTable.loading"
            :columns="examinationPaperAchievementStandardTable.columns"
            :dataSource="examinationPaperAchievementStandardTable.dataSource"
            :height="340"
            :disabled="formDisabled"
            :rowNumber="true"
            :rowSelection="true"
            :toolbar="true"
        >
          <!-- 定义插槽 -->
          <template #achievementIdSlot="props">
            <!-- 点击超链接，出发弹窗 -->
            <!--            <a-textare v-if="props.value !== '' || props.value !== undefined || props.value !== null" v-model = "props.value" ></a-textare>-->
            <a-tag>{{"id："+props.value}}</a-tag>
            <a  @click="chooseAchievement(props)">操作：选择评分标准</a>
          </template>
        </JVxeTable>
      </a-tab-pane>
    </a-tabs>
  </BasicModal>
  <div class="px-10">
    <ChooseTitleVueModal v-if="TitleValueDd" @register="register" @success="handleSuccess " @CloseCreateE="closCreate"></ChooseTitleVueModal>
  </div>
  <div class="px-10">
    <ChooseStandVueModal v-if="StandValueDd" @register="registerStand" @success="handleStandSuccess" @CloseCreateE="closCreate"></ChooseStandVueModal>
  </div>
</template>

<script lang="ts" setup>
import { ref, computed, unref, reactive } from 'vue';
import { BasicModal, useModalInner } from '/src/components/Modal';
import { BasicForm, useForm } from '/src/components/Form';
import { JVxeTable } from '/src/components/jeecg/JVxeTable'
import { useJvxeMethod } from '/src/hooks/system/useJvxeMethods.ts'
import { formSchema, examinationTitleJVxeColumns,examinationPaperAchievementStandardJVxeColumns } from '../ExaminationAll.data';
import { saveOrUpdate, queryExaminationTitle,queryExaminationPaperAchievementStandard } from '../ExaminationAll.api';
import { VALIDATE_FAILED } from '/src/utils/common/vxeUtils'
import ChooseTitleVueModal from './ChooseTitle.vue';
import ChooseStandVueModal from './ChooseStand.vue';
import { useModal } from '/@/components/Modal';
import ChooseStand from "@/views/test/defend/components/ChooseStand.vue";
// Emits声明
const emit = defineEmits(['register', 'success']);
const isUpdate = ref(true);
const formDisabled = ref(false);
const refKeys = ref(['examinationTitle','examinationPaperAchievementStandard']);
const activeKey = ref('examinationTitle');
const examinationTitle = ref();
const examinationPaperAchievementStandard = ref();
const tableRefs = { examinationTitle, examinationPaperAchievementStandard};
const examinationTitleTable = reactive({
  loading: false,
  dataSource: [],
  columns: examinationTitleJVxeColumns
})
// 评判标准表格
const examinationPaperAchievementStandardTable = reactive({
  loading: false,
  dataSource: [],
  columns:examinationPaperAchievementStandardJVxeColumns
})
//表单配置
const [registerForm, { setProps, resetFields, setFieldsValue, validate }] = useForm({
  //labelWidth: 150,
  schemas: formSchema,
  showActionButtonGroup: false,
  baseColProps: { span: 24 }
});
const [register, { openModal,setModalProps:setModalChooseProps }] = useModal();
const [registerStand, { openModal:openStandModal,setModalProps:setStandModalChooseProps }] = useModal();
//表单赋值
const [registerModal, { setModalProps, closeModal }] = useModalInner(async (data) => {
  //重置表单
  await reset();
  setModalProps({ confirmLoading: false, showCancelBtn: data?.showFooter, showOkBtn: data?.showFooter });
  isUpdate.value = !!data?.isUpdate;
  formDisabled.value = !data?.showFooter;
  if (unref(isUpdate)) {
    //表单赋值
    await setFieldsValue({
      ...data.record,
    });
    // 子表1赋值
    requestSubTableData(queryExaminationTitle, { id: data?.record?.examinationCode }, examinationTitleTable)
    // 子表2赋值
    requestSubTableData(queryExaminationPaperAchievementStandard, { id: data?.record?.examinationCode }, examinationPaperAchievementStandardTable)
  }
  // 隐藏底部时禁用整个表单
  setProps({ disabled: !data?.showFooter })
});
//方法配置
/**
 * handleChangeTabs 表单 tab 页changge 事件
 * handleSubmit 整个表单提交事件
 * requestSubTableData 编辑时候讲数据渲染到表格上的方法
 * formRef 表单的 ref
 * requestAddOrEdit 整个表单的提交事件
 * classifyIntoFormData 组装编辑和保存是接口要发送到后台的借口的参数的事件
 * tableRefs 有几个子表，就需要将几个子表的 ref 全都放到这里
 * activeKey 当前选中的 tab 页面的 key
 * refKeys 所有子表的 ref 都放到这里面
 */
const [handleChangeTabs, handleSubmit, requestSubTableData, formRef] = useJvxeMethod(requestAddOrEdit, classifyIntoFormData, tableRefs, activeKey, refKeys);

//设置标题
const title = computed(() => (!unref(isUpdate) ? '新增' : '编辑'));

async function reset() {
  await resetFields();
  activeKey.value = 'examinationTitle';
  examinationTitleTable.dataSource = [];
  examinationPaperAchievementStandardTable.dataSource = []
}
function classifyIntoFormData(allValues) {
  console.log(allValues,'allValues')
  let main = Object.assign({}, allValues.formValue)
  return {
    ...main, // 展开
    examinationTitleList: allValues.tablesValue[0].tableData,
    examinationPaperAchievementStandardList: allValues.tablesValue[1].tableData
  }
}
//表单提交事件
async function requestAddOrEdit(values) {
  console.log(values,'要存储的数据')
  try {
    setModalProps({ confirmLoading: true });
    //提交表单
    await saveOrUpdate(values, isUpdate.value);
    //关闭弹窗
    closeModal();
    //刷新列表
    emit('success');
  } finally {
    setModalProps({ confirmLoading: false });
  }
}
const TitleValue = ref({})
const TitleValueDd = ref(false)
// 选择题目弹窗
function chooseTitle(props) {
  TitleValueDd.value = true
  console.log(props)
  TitleValue.value = props
  setModalChooseProps({defaultFullscreen:true})
  // 出发弹窗，选择题目
  openModal(true)
}

// 选择成功后的回调事件
function handleSuccess(data){
  console.log(data)
  // 进行赋值
  console.log(TitleValue.value)
  TitleValue.value.row.remark = data.titleName
  TitleValue.value.row.titleCode = data.titleCode
}

const StandValue = ref({})
const StandValueDd = ref(false)
function chooseAchievement(props) {
  console.log(props)
  StandValueDd.value=true
  StandValue.value = props
  setStandModalChooseProps({defaultFullscreen:true})
  // 出发弹窗，选择题目
  openStandModal(true)
}

// 选择成功后的回调事件
function handleStandSuccess(data){
  console.log(data)
  // 进行赋值
  console.log(StandValue.value)
  StandValue.value.row.achievementId = data.id
  StandValue.value.row.achievementName = data.achievementName

}

function closCreate(){
  StandValueDd.value=false
  TitleValueDd.value = false
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
