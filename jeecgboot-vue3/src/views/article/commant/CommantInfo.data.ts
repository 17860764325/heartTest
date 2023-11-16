import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '文章 id',
    align:"center",
    dataIndex: 'pageId'
   },
   {
    title: '评论内容',
    align:"center",
    dataIndex: 'about'
   },
   {
    title: '父id',
    align:"center",
    dataIndex: 'parentId'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
	{
      label: "评论内容",
      field: 'about',
      component: 'Input',
      //colProps: {span: 6},
 	},
	{
      label: "父id",
      field: 'parentId',
      component: 'Input',
      //colProps: {span: 6},
 	},
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '文章   id',
    field: 'pageId',
    component: 'Input',
  },
  {
    label: '评论内容',
    field: 'about',
    component: 'Input',
  },
  {
    label: '父id',
    field: 'parentId',
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



/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}
