import {defHttp} from '/@/utils/http/axios';
import { useMessage } from "/@/hooks/web/useMessage";

const { createConfirm } = useMessage();

enum Api {
  list = '/test/title/list',
  save='/test/title/add',
  edit='/test/title/edit',
  deleteOne = '/test/title/delete',
  deleteBatch = '/test/title/deleteBatch',
  importExcel = '/test/title/importExcel',
  exportXls = '/test/title/exportXls',
  answerList = '/test/title/listAnswerByMainId',
  answerSave='/test/title/addAnswer',
  answerEdit='/test/title/editAnswer',
  answerDelete = '/test/title/deleteAnswer',
  answerDeleteBatch = '/test/title/deleteBatchAnswer',
}
/**
 * 导出api
 * @param params
 */
export const getExportUrl = Api.exportXls;

/**
 * 导入api
 */
export const getImportUrl = Api.importExcel;
/**
 * 列表接口
 * @param params
 */
export const list = (params) =>
  defHttp.get({url: Api.list, params});

/**
 * 删除单个
 */
export const deleteOne = (params,handleSuccess) => {
  return defHttp.delete({url: Api.deleteOne, params}, {joinParamsToUrl: true}).then(() => {
    handleSuccess();
  });
}
/**
 * 批量删除
 * @param params
 */
export const batchDelete = (params, handleSuccess) => {
  createConfirm({
    iconType: 'warning',
    title: '确认删除',
    content: '是否删除选中数据',
    okText: '确认',
    cancelText: '取消',
    onOk: () => {
      return defHttp.delete({url: Api.deleteBatch, data: params}, {joinParamsToUrl: true}).then(() => {
        handleSuccess();
      });
    }
  });
}
/**
 * 保存或者更新
 * @param params
 */
export const saveOrUpdate = (params, isUpdate) => {
  let url = isUpdate ? Api.edit : Api.save;
  return defHttp.post({url: url, params});
}
/**
 * 列表接口
 * @param params
 */
export const answerList = (params) => {
  if(params['questionId']){
    return defHttp.get({url: Api.answerList, params});
  }
  return Promise.resolve({});
}


/**
 * 删除单个
 */
export const answerDelete = (params,handleSuccess) => {
  return defHttp.delete({url: Api.answerDelete, params}, {joinParamsToUrl: true}).then(() => {
    handleSuccess();
  });
}
/**
 * 批量删除
 * @param params
 */
export const answerDeleteBatch = (params, handleSuccess) => {
  createConfirm({
    iconType: 'warning',
    title: '确认删除',
    content: '是否删除选中数据',
    okText: '确认',
    cancelText: '取消',
    onOk: () => {
      return defHttp.delete({url: Api.answerDeleteBatch, data: params}, {joinParamsToUrl: true}).then(() => {
        handleSuccess();
      });
    }
  });
}
/**
 * 保存或者更新
 * @param params
 */
export const  answerSaveOrUpdate = (params, isUpdate) => {
  let url = isUpdate ? Api.answerEdit : Api.answerSave;
  return defHttp.post({url: url, params});
}
/**
 * 导入
 */
export const answerImportUrl = '/test/title/importAnswer'

/**
 * 导出
 */
export const answerExportXlsUrl = '/test/title/exportAnswer'
