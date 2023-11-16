import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '文章编码',
    align:"center",
    dataIndex: 'pageCode'
   },
   {
    title: '文章名称',
    align:"center",
    dataIndex: 'pageName'
   },
   {
    title: '文章类型',
    align:"center",
    dataIndex: 'pageType',
     customRender: ({value}) => {
       return render.renderDict(value, 'article_classify');
     }
   },
   {
    title: '文章标题',
    align:"center",
    dataIndex: 'title'
   },
   {
    title: '作者',
    align:"center",
    dataIndex: 'createPeople'
   },
   {
    title: ' 内容',
    align:"center",
    dataIndex: 'about'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
	{
      label: "文章名称",
      field: 'pageName',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "文章类型",
      field: 'pageType',
      component: 'JDictSelectTag',
    componentProps:{
      dictCode:"article_classify"
    },
      //colProps: {span: 6},
 	},
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '文章编码',
    field: 'pageCode',
    component: 'Input',
  },
  {
    label: '文章名称',
    field: 'pageName',
    component: 'Input',
  },
  {
    label: '文章类型',
    field: 'pageType',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"article_classify"
     },
  },
  {
    label: '文章标题',
    field: 'title',
    component: 'Input',
  },
  {
    label: '作者',
    field: 'createPeople',
    component: 'Input',
  },
  {
    label: ' 内容',
    field: 'about',
    component: 'JEditor',//注意string转换问题
  },
	// TODO 主键隐藏字段，目前写死为ID
	{
	  label: '',
	  field: 'id',
	  component: 'Input',
	  show: false
	},
];



/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}
