<template>
  <div>
    <BasicForm @register="registerForm" ref="formRef"/>
  <!-- 子表单区域 -->
    <a-tabs v-model:activeKey="activeKey" animated @change="handleChangeTabs">
      <a-tab-pane tab="examination_title" key="examinationTitle" :forceRender="true">
        <JVxeTable
          v-if="examinationTitleTable.show"
          keep-source
          resizable
          ref="examinationTitle"
          :loading="examinationTitleTable.loading"
          :columns="examinationTitleTable.columns"
          :dataSource="examinationTitleTable.dataSource"
          :height="340"
          :disabled="formDisabled"
          :rowNumber="true"
          :rowSelection="true"
          :toolbar="true"
          />
      </a-tab-pane>
    </a-tabs>
    <div style="width: 100%;text-align: center;margin-top: 10px;" v-if="showFlowSubmitButton">
      <a-button preIcon="ant-design:check-outlined" style="width: 126px" type="primary" @click="handleSubmit">提 交</a-button>
    </div>
  </div>
</template>

<script lang="ts" setup>
    import { defHttp } from '/src/utils/http/axios';
    import {ref, computed, unref,reactive, onMounted, defineProps } from 'vue';
    import {BasicForm, useForm} from '/src/components/Form';
    import { JVxeTable } from '/src/components/jeecg/JVxeTable'
    import { useJvxeMethod } from '/src/hooks/system/useJvxeMethods.ts'
    import {formSchema,examinationTitleJVxeColumns} from '../ExaminationAll.data';
    import {saveOrUpdate,queryExaminationTitle} from '../ExaminationAll.api';
    import { VALIDATE_FAILED } from '/src/utils/common/vxeUtils'
    const isUpdate = ref(true);

    const refKeys = ref(['examinationTitle', ]);
    const activeKey = ref('examinationTitle');
    const examinationTitle = ref();
    const tableRefs = {examinationTitle, };
    const examinationTitleTable = reactive({
          loading: false,
          dataSource: [],
          columns:examinationTitleJVxeColumns,
          show: false
    })

    const props = defineProps({
      formData: { type: Object, default: ()=>{} },
      formBpm: { type: Boolean, default: true }
    });
    const formDisabled = computed(()=>{
      if(props.formBpm === true){
        if(props.formData.disabled === false){
          return false;
        }
      }
      return true;
    });
    // 是否显示提交按钮
    const showFlowSubmitButton = computed(()=>{
      if(props.formBpm === true){
        if(props.formData.disabled === false){
          return true
        }
      }
      return false
    });

    //表单配置
    const [registerForm, {setProps,resetFields, setFieldsValue, validate}] = useForm({
        labelWidth: 150,
        schemas: formSchema,
        showActionButtonGroup: false,
        baseColProps: {span: 24}
    });

    onMounted(()=>{
      initFormData();
    });
    //渲染流程表单数据
    const queryByIdUrl = '/test/examinationAll/queryById';
    async function initFormData(){
      if(props.formBpm === true){
        await reset();
        let params = {id: props.formData.dataId};
        const data = await defHttp.get({url: queryByIdUrl, params});
        //表单赋值
        await setFieldsValue({
          ...data
        });
        requestSubTableData(queryExaminationTitle, {id: data.id}, examinationTitleTable, ()=>{
          examinationTitleTable.show = true;
        });
        // 隐藏底部时禁用整个表单
        setProps({ disabled: formDisabled.value })
      }
    }

    //方法配置
    const [handleChangeTabs,handleSubmit,requestSubTableData,formRef] = useJvxeMethod(requestAddOrEdit,classifyIntoFormData,tableRefs,activeKey,refKeys);

    async function reset(){
      await resetFields();
      activeKey.value = 'examinationTitle';
      examinationTitleTable.dataSource = [];
    }
    function classifyIntoFormData(allValues) {
         let main = Object.assign({}, allValues.formValue)
         return {
           ...main, // 展开
           examinationTitleList: allValues.tablesValue[0].tableData,
         }
       }
    //表单提交事件
    async function requestAddOrEdit(values) {
      //提交表单
      await saveOrUpdate(values, true);
    }
</script>

<style lang="less" scoped>
	/** 时间和数字输入框样式 */
    :deep(.ant-input-number){
		width: 100%
	}

	:deep(.ant-calendar-picker){
		width: 100%
	}
</style>
