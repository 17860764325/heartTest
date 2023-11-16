import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import {JVxeTypes,JVxeColumn} from '/@/components/jeecg/JVxeTable/types'
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '答题人',
    align:"center",
    dataIndex: 'createBy'
   },
   {
    title: '答题结束时间',
    align:"center",
    dataIndex: 'createTime'
   },
   {
    title: '试卷编码',
    align:"center",
    dataIndex: 'paperCode'
   },
   {
    title: '试卷名称',
    align:"center",
    dataIndex: 'paperName'
   },
   {
    title: '总分',
    align:"center",
    dataIndex: 'totalScore'
   },
   {
    title: '试卷分类',
    align:"center",
    dataIndex: 'paperType',
     customRender: ({value}) => {
       return render.renderDict(value, 'test_paper_type');
     }
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
	{
      label: "创建人",
      field: 'createBy',
      component: 'Input',
      //colProps: {span: 6},
 	},
     {
      label: "创建时间",
      field: "createTime",
      component: 'RangePicker',
      componentProps: {
          valueType: 'Date',
          showTime:true
      },
      //colProps: {span: 6},
	},
	{
      label: "试卷名称",
      field: 'paperName',
      component: 'Input',
      //colProps: {span: 6},
 	},
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '试卷编码',
    field: 'paperCode',
    component: 'Input',
  },
  {
    label: '试卷名称',
    field: 'paperName',
    component: 'Input',
  },
  {
    label: '总分',
    field: 'totalScore',
    component: 'Input',
  },
  {
    label: '试卷分类',
    field: 'paperType',
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
//子表单数据
//子表列表数据
export const answerQuestionLogDetailColumns: BasicColumn[] = [
   {
    title: '问题名称',
    align:"center",
    dataIndex: 'titleName'
   },
   {
    title: '答案',
    align:"center",
    dataIndex: 'remark'
   },
   {
    title: '得分',
    align:"center",
    dataIndex: 'socre'
   },
   {
    title: '答题时间',
    align:"center",
    dataIndex: 'makeTime'
   },
];
//子表表格配置
export const answerQuestionLogDetailJVxeColumns: JVxeColumn[] = [
    {
      title: '问题名称',
      key: 'titleName',
      type: JVxeTypes.input,
      width:"200px",
      placeholder: '请输入${title}',
      defaultValue:'',
    },
    {
      title: '题目编码',
      key: 'titleCode',
      type: JVxeTypes.input,
      width:"200px",
      placeholder: '请输入${title}',
      defaultValue:'',
    },
    {
      title: '答案编码',
      key: 'answerCode',
      type: JVxeTypes.input,
      width:"200px",
      placeholder: '请输入${title}',
      defaultValue:'',
    },
    {
      title: '得分',
      key: 'socre',
      type: JVxeTypes.input,
      width:"200px",
      placeholder: '请输入${title}',
      defaultValue:'',
    },
    {
      title: '答题时间',
      key: 'makeTime',
      type: JVxeTypes.datetime,
      width:"200px",
      placeholder: '请输入${title}',
      defaultValue:'',
    },
  ]

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}
