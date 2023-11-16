import {BasicColumn} from '/src/components/Table';
import {FormSchema} from '/src/components/Table';
import {rules} from '/src/utils/helper/validator';
import {render} from '/src/utils/common/renderUtils';
import {JVxeTypes, JVxeColumn} from '/src/components/jeecg/JVxeTable/types'
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '试卷名称',
    align: "center",
    dataIndex: 'examinationPaper'
  },
  {
    title: '试卷介绍',
    align: "center",
    dataIndex: 'examinationAbout'
  },
  {
    title: '开始时间',
    align: "center",
    dataIndex: 'startTime',
    customRender: ({text}) => {
      return !text ? "" : (text.length > 10 ? text.substr(0, 10) : text)
    },
  },
  {
    title: '结束时间',
    align: "center",
    dataIndex: 'endTime',
    customRender: ({text}) => {
      return !text ? "" : (text.length > 10 ? text.substr(0, 10) : text)
    },
  },
  {
    title: '试卷类型',
    align: "center",
    dataIndex: 'examinationType',
    customRender: ({value}) => {
      return render.renderDict(value, 'test_paper_type');
    }
  },
  {
    title: '限制时间',
    align: "center",
    dataIndex: 'limitTime'
  },
  {
    title: '及格分数',
    align: "center",
    dataIndex: 'score'
  },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
  {
    label: "试卷名称",
    field: 'examinationPaper',
    component: 'Input',
    //colProps: {span: 6},
  },
  {
    label: "试卷类型",
    field: 'examinationType',
    component: 'JDictSelectTag',
    componentProps: {},
    //colProps: {span: 6},
  },
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '试卷名称',
    field: 'examinationPaper',
    component: 'Input',
  },
  {
    label: '试卷编码',
    field: 'examinationCode',
    component: 'Input',
    show:false
  },
  {
    label: '试卷介绍',
    field: 'examinationAbout',
    component: 'InputTextArea',
  },
  {
    label: '开始时间',
    field: 'startTime',
    component: 'DatePicker',
  },
  {
    label: '结束时间',
    field: 'endTime',
    component: 'DatePicker',
  },
  {
    label: '试卷类型',
    field: 'examinationType',
    component: 'JDictSelectTag',
    componentProps: {
      dictCode: 'test_paper_type',
    },
  },
  {
    label: '限制时间',
    field: 'limitTime',
    component: 'Input',
  },
  {
    label: '及格分数',
    field: 'score',
    component: 'InputNumber',
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
export const examinationTitleColumns: BasicColumn[] = [
  {
    title: '试卷编码',
    align: "center",
    dataIndex: 'examinationCode'
  },
  {
    title: '题目编码',
    align: "center",
    dataIndex: 'titleCode'
  },
  {
    title: '预留字段',
    align: "center",
    dataIndex: 'remark'
  },
];
//子表表格配置
export const examinationTitleJVxeColumns: JVxeColumn[] = [
  // 不需要展示默认可以设置
  // {
  //   title: '试卷编码',
  //   key: 'examinationCode',
  //   type: JVxeTypes.input,
  //   width:"200px",
  //   placeholder: '请输入${title}',
  //   defaultValue:'',
  // },
  {
    title: '题目编码',
    key: 'titleCode',
    type: JVxeTypes.slot,
    placeholder: '请输入${title}',
    defaultValue: '',
    // 配置插槽
    slotName: 'titleCodeSlot',
  },
  {
    title: '题目',
    key: 'remark',
    type: JVxeTypes.input,
    placeholder: '请输入${title}',
    defaultValue: '',
    disabled:true
  }
]

//子表表格配置
export const examinationPaperAchievementStandardJVxeColumns: JVxeColumn[] = [
  {
    title: '评判标准',
    key: 'achievementId',
    type: JVxeTypes.slot,
    placeholder: '请输入${title}',
    defaultValue:'',
    // 配置插槽
    slotName: 'achievementIdSlot',
  },
  {
    title: '评判标准名称',
    key: 'achievementName',
    type: JVxeTypes.input,
    placeholder: '请输入${title}',
    defaultValue:'',
  },
]


//子表单数据
//子表列表数据
export const examinationPaperAchievementStandardColumns: BasicColumn[] = [
  {
    title: '试卷编码',
    align:"center",
    dataIndex: 'paperCode'
  },
  {
    title: '评判标准名称',
    align:"center",
    dataIndex: 'achievementName'
  },
];

/**
 * 流程表单调用这个方法获取formSchema
 * @param param
 */
export function getBpmFormSchema(_formData): FormSchema[] {
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}
