<template>
  <BasicModal v-bind="$attrs" @register="registerModal" title="题目选择"
              helpMessage="请选择试卷的相关题目" @ok="handleSubmit" @cancel="cancelThe">
    <!-- 书写一个 table，用来选择相应题目 -->
    <!--定义表格-->
    <BasicTable @register="registerTable" :rowSelection="rowSelection">
    </BasicTable>
  </BasicModal>
</template>
<script lang="ts" setup>
import {BasicModal, useModalInner} from '/@/components/Modal';
import {ActionItem, BasicColumn, BasicTable, TableAction} from '/@/components/Table';
import {useListPage} from '/@/hooks/system/useListPage';
import {defHttp} from '/@/utils/http/axios';
// Emits声明
const emit = defineEmits(['success', 'register','CloseCreateE']);
//定义表格列
const columns: BasicColumn[] = [
  {
    title: '题目',
    dataIndex: 'titleName',
    width: 170,
    align: 'left',
    resizable: true,
    sorter: {
      multiple: 1,
    },
  },
  {
    title: '题目编码',
    dataIndex: 'titleCode',
    width: 170,
    align: 'left',
    resizable: true,
    sorter: {
      multiple: 1,
    },
  },
]

//ajax请求api接口
const demoListApi = (params) => {
  return defHttp.get({url: '/test/title/list', params});
};

// 列表页面公共参数、方法
const {tableContext} = useListPage({
  designScope: 'basic-table-demo-ajax',
  tableProps: {
    title: '用户列表',
    api: demoListApi,
    columns: columns,
    //定义rowSelection的类型，默认是checkbox多选，可以设置成radio单选
    rowSelection: {type: 'radio'},
  },
});

//BasicTable绑定注册
const [registerTable, {reload}, {rowSelection, selectedRows, selectedRowKeys}] = tableContext;

const [registerModal, {
  setModalProps,
  closeModal,
  redoModalHeight
}] = useModalInner(async (data) => {
  // 可以直接通过openModal传递参数
  console.log(data)
});

function handleSubmit() {
  closeModal()
  emit("success",selectedRows.value[0])
}

function cancelThe(){
  closeModal()
  emit("CloseCreateE")
}
</script>
