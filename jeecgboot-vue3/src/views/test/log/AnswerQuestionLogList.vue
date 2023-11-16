<template>
  <div>
    <!--引用表格-->
   <BasicTable @register="registerTable" :rowSelection="rowSelection" :expandedRowKeys="expandedRowKeys"  @expand="handleExpand">
      <!-- 内嵌table区域 begin -->
           <template #expandedRowRender="{record}">
             <a-tabs tabPosition="top">
               <a-tab-pane tab="答题详情" key="answerQuestionLogDetail" forceRender>
                  <answerQuestionLogDetailSubTable :id="expandedRowKeys[0]"/>
               </a-tab-pane>
             </a-tabs>
           </template>
     <!-- 内嵌table区域 end -->
     <!--插槽:table标题-->
      <template #tableTitle>
          <a-button  type="primary" preIcon="ant-design:export-outlined" @click="onExportXls"> 导出</a-button>
          <j-upload-button  type="primary" preIcon="ant-design:import-outlined" @click="onImportXls">导入</j-upload-button>
          <a-dropdown v-if="selectedRowKeys.length > 0">
              <template #overlay>
                <a-menu>
                  <a-menu-item key="1" @click="batchHandleDelete">
                    <Icon icon="ant-design:delete-outlined"></Icon>
                    删除
                  </a-menu-item>
                </a-menu>
              </template>
              <a-button>批量操作
                <Icon icon="mdi:chevron-down"></Icon>
              </a-button>
        </a-dropdown>
      </template>
       <!--操作栏-->
      <template #action="{ record }">
        <TableAction :actions="getTableAction(record)" :dropDownActions="getDropDownAction(record)"/>
      </template>
      <!--字段回显插槽-->
      <template v-slot:bodyCell="{ column, record, index, text }">
      </template>
    </BasicTable>
    <!-- 表单区域 -->
    <AnswerQuestionLogModal @register="registerModal" @success="handleSuccess"></AnswerQuestionLogModal>
    <!--  查看成绩分析页面  -->
    <achievement @register="registeAchievementModal" ></achievement>
  </div>
</template>

<script lang="ts" name="test-answerQuestionLog" setup>
  import {ref, computed, unref} from 'vue';
  import {BasicTable, useTable, TableAction} from '/@/components/Table';
  import { useListPage } from '/@/hooks/system/useListPage'
  import {useModal} from '/@/components/Modal';
  import AnswerQuestionLogModal from './components/AnswerQuestionLogModal.vue'
  import AnswerQuestionLogDetailSubTable from './subTables/AnswerQuestionLogDetailSubTable.vue'
  import {columns, searchFormSchema} from './AnswerQuestionLog.data';
  import {list, deleteOne, batchDelete, getImportUrl,getExportUrl} from './AnswerQuestionLog.api';
  import achievement from "./components/achievement.vue"
  import { useUserStore } from '/@/store/modules/user';
   // 展开key
  const expandedRowKeys = ref<any[]>([]);
  //注册model
  const [registerModal, {openModal}] = useModal();
  // 查看成绩弹窗
  const [registeAchievementModal, {openModal:openAchievementModal,setModalProps:setAchievementModalProps}] = useModal();
  const userStore = useUserStore();
   //注册table数据
  const { prefixCls,tableContext,onExportXls,onImportXls } = useListPage({
      tableProps:{
           title: 'answer_question_log',
           api: list,
           columns,
           canResize:false,
           formConfig: {
                //labelWidth: 120,
                schemas: searchFormSchema,
                autoSubmitOnEnter:true,
                showAdvancedButton:true,
                fieldMapToNumber: [
                ],
                fieldMapToTime: [
                   ['createTime', ['createTime_begin', 'createTime_end'], 'YYYY-MM-DD HH:mm:ss'],
                ],
            },
           actionColumn: {
               width: 120,
               fixed:'right'
           },
        },
        exportConfig: {
            name:"answer_question_log",
            url: getExportUrl,
        },
        importConfig: {
            url: getImportUrl,
            success: handleSuccess
        },
    })

  const [registerTable, {reload},{ rowSelection, selectedRowKeys }] = tableContext

  /**
   * 成绩查看
   */
  function achievementDetail(record: Recordable){
    console.log(record)
    // 设置为全屏展示
    setAchievementModalProps({defaultFullscreen:true})
    // 打开弹窗弹窗展示成绩信息,并将 log 信息传输过去
    openAchievementModal(true,record)

  }
  /**
     * 展开事件
     * */
   function handleExpand(expanded, record){
        expandedRowKeys.value=[];
        if (expanded === true) {
           expandedRowKeys.value.push(record.id)
        }
    }
   /**
    * 新增事件
    */
  function handleAdd() {
     openModal(true, {
       isUpdate: false,
       showFooter: true,
     });
  }
   /**
    * 编辑事件
    */
  function handleEdit(record: Recordable) {
     openModal(true, {
       record,
       isUpdate: true,
       showFooter: true,
     });
   }
   /**
    * 详情
   */
  function handleDetail(record: Recordable) {
     openModal(true, {
       record,
       isUpdate: true,
       showFooter: false,
     });
   }
   /**
    * 删除事件
    */
  async function handleDelete(record) {
     await deleteOne({id: record.id}, handleSuccess);
   }
   /**
    * 批量删除事件
    */
  async function batchHandleDelete() {
     await batchDelete({ids: selectedRowKeys.value},handleSuccess);
   }
   /**
    * 成功回调
    */
  function handleSuccess() {
      (selectedRowKeys.value = []) && reload();
   }
   /**
      * 操作栏
      */
  function getTableAction(record){
       return [
         {
           label: '查看成绩',
           onClick: achievementDetail.bind(null, record),
         },
         {
           label: '详情',
           onClick: handleDetail.bind(null, record),
         }
       ]
   }


  /**
   * 下拉操作栏
   */
  function getDropDownAction(record){
    return [

    ]
  }

</script>

<style scoped>

</style>
