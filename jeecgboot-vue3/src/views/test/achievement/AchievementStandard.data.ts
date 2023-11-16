import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import {rules} from '/@/utils/helper/validator';
import {render} from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
    {
        title: '区间最高值',
        align: "center",
        dataIndex: 'high'
    },
    {
        title: '区间低值',
        align: "center",
        dataIndex: 'low'
    },
    {
        title: '该区间段评价结果',
        align: "center",
        dataIndex: 'comment'
    },
    {
        title: '心理状态',
        align: "center",
        dataIndex: 'psychologicalState',
        customRender: ({value}) => {
            return render.renderDict(value, 'heart_type');
        }
    },
    {
        title: '成绩评判标准名称',
        align: "center",
        dataIndex: 'achievementName'
    },
    {
        title: '成绩评判标准编码',
        align: "center",
        dataIndex: 'achievementCode'
    },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
    {
        label: "心理状态",
        field: 'psychologicalState',
        component: 'JDictSelectTag',
        componentProps: {
            dictCode: "heart_type"
        },
        //colProps: {span: 6},
    },
    {
        label: "成绩评判标准名称",
        field: 'achievementName',
        component: 'Input',
        //colProps: {span: 6},
    },
];
//表单数据
export const formSchema: FormSchema[] = [
    {
        label: '区间最高值',
        field: 'high',
        component: 'InputNumber',
    },
    {
        label: '区间低值',
        field: 'low',
        component: 'InputNumber',
    },
    {
        label: '该区间段评价结果',
        field: 'comment',
        component: 'InputTextArea',
    },
    {
        label: '心理状态',
        field: 'psychologicalState',
        component: 'JDictSelectTag',
        componentProps: {
            dictCode: "heart_type"
        },
    },
    {
        label: '成绩评判标准名称',
        field: 'achievementName',
        component: 'Input',
    },
    {
        label: '成绩评判标准编码',
        field: 'achievementCode',
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
export function getBpmFormSchema(_formData): FormSchema[] {
    // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
    return formSchema;
}
