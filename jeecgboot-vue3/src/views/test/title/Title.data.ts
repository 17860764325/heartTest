import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '题目名称',
    align:"center",
    dataIndex: 'titleName'
   },
   {
    title: '题目编码',
    align:"center",
    dataIndex: 'titleCode'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
	{
      label: "题目名称",
      field: "titleName",
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "题目编码",
      field: "titleCode",
      component: 'Input',
      //colProps: {span: 6},
 	},
];

//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '题目名称',
    field: 'titleName',
    component: 'InputTextArea',//TODO 注意string转换问题
  },
  {
    label: '题目编码',
    field: 'titleCode',
    component: 'Input',
  },
	// TODO 主键隐藏字段，目前写死为ID
	{
	  label: '',
	  field: 'id',
	  component: 'Input',
	  show: false
	},
];

//子表列表数据
export const answerColumns: BasicColumn[] = [
   {
    title: '答案名称',
    align:"center",
    dataIndex: 'answerName'
   },
   {
    title: '答案编码',
    align:"center",
    dataIndex: 'answerCode'
   },
   {
    title: '分值',
    align:"center",
    dataIndex: 'answerScore'
   },
];
//子表表单数据
export const answerFormSchema: FormSchema[] = [
  // TODO 子表隐藏字段，目前写死为ID
  {
    label: '',
    field: 'id',
    component: 'Input',
    show: false
  },
  {
    label: '答案名称',
    field: 'answerName',
    component: 'Input',
  },
  {
    label: '答案编码',
    field: 'answerCode',
    component: 'Input',
  },
  {
    label: '分值',
    field: 'answerScore',
    component: 'Input',
  },
];
