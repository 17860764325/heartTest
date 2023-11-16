package org.jeecg.modules.demo.test.controller;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.common.aspect.annotation.PermissionData;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.vo.LoginUser;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.test.entity.AnswerQuestionLogDetail;
import org.jeecg.modules.demo.test.entity.AnswerQuestionLog;
import org.jeecg.modules.demo.test.vo.AnswerQuestionLogPage;
import org.jeecg.modules.demo.test.service.IAnswerQuestionLogService;
import org.jeecg.modules.demo.test.service.IAnswerQuestionLogDetailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

 /**
 * @Description: answer_question_log
 * @Author: jeecg-boot
 * @Date:   2023-11-08
 * @Version: V1.0
 */
@Api(tags="answer_question_log")
@RestController
@RequestMapping("/test/answerQuestionLog")
@Slf4j
public class AnswerQuestionLogController {
	@Autowired
	private IAnswerQuestionLogService answerQuestionLogService;
	@Autowired
	private IAnswerQuestionLogDetailService answerQuestionLogDetailService;
	
	/**
	 * 分页列表查询
	 *
	 * @param answerQuestionLog
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "answer_question_log-分页列表查询")
	@ApiOperation(value="answer_question_log-分页列表查询", notes="answer_question_log-分页列表查询")
	@GetMapping(value = "/list")
	@PermissionData(pageComponent="test/log/AnswerQuestionLogList")
	public Result<IPage<AnswerQuestionLog>> queryPageList(AnswerQuestionLog answerQuestionLog,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<AnswerQuestionLog> queryWrapper = QueryGenerator.initQueryWrapper(answerQuestionLog, req.getParameterMap());
		Page<AnswerQuestionLog> page = new Page<AnswerQuestionLog>(pageNo, pageSize);
		IPage<AnswerQuestionLog> pageList = answerQuestionLogService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param answerQuestionLogPage
	 * @return
	 */
	@AutoLog(value = "answer_question_log-添加")
	@ApiOperation(value="answer_question_log-添加", notes="answer_question_log-添加")
    @RequiresPermissions("test:answer_question_log:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody AnswerQuestionLogPage answerQuestionLogPage) {
		AnswerQuestionLog answerQuestionLog = new AnswerQuestionLog();
		BeanUtils.copyProperties(answerQuestionLogPage, answerQuestionLog);
		answerQuestionLogService.saveMain(answerQuestionLog, answerQuestionLogPage.getAnswerQuestionLogDetailList());
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param answerQuestionLogPage
	 * @return
	 */
	@AutoLog(value = "answer_question_log-编辑")
	@ApiOperation(value="answer_question_log-编辑", notes="answer_question_log-编辑")
    @RequiresPermissions("test:answer_question_log:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody AnswerQuestionLogPage answerQuestionLogPage) {
		AnswerQuestionLog answerQuestionLog = new AnswerQuestionLog();
		BeanUtils.copyProperties(answerQuestionLogPage, answerQuestionLog);
		AnswerQuestionLog answerQuestionLogEntity = answerQuestionLogService.getById(answerQuestionLog.getId());
		if(answerQuestionLogEntity==null) {
			return Result.error("未找到对应数据");
		}
		answerQuestionLogService.updateMain(answerQuestionLog, answerQuestionLogPage.getAnswerQuestionLogDetailList());
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "answer_question_log-通过id删除")
	@ApiOperation(value="answer_question_log-通过id删除", notes="answer_question_log-通过id删除")
    @RequiresPermissions("test:answer_question_log:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		answerQuestionLogService.delMain(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "answer_question_log-批量删除")
	@ApiOperation(value="answer_question_log-批量删除", notes="answer_question_log-批量删除")
    @RequiresPermissions("test:answer_question_log:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.answerQuestionLogService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "answer_question_log-通过id查询")
	@ApiOperation(value="answer_question_log-通过id查询", notes="answer_question_log-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<AnswerQuestionLog> queryById(@RequestParam(name="id",required=true) String id) {
		AnswerQuestionLog answerQuestionLog = answerQuestionLogService.getById(id);
		if(answerQuestionLog==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(answerQuestionLog);

	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "answer_question_log_detail-通过主表ID查询")
	@ApiOperation(value="answer_question_log_detail-通过主表ID查询", notes="answer_question_log_detail-通过主表ID查询")
	@GetMapping(value = "/queryAnswerQuestionLogDetailByMainId")
	public Result<IPage<AnswerQuestionLogDetail>> queryAnswerQuestionLogDetailListByMainId(@RequestParam(name="id",required=true) String id) {
		List<AnswerQuestionLogDetail> answerQuestionLogDetailList = answerQuestionLogDetailService.selectByMainId(id);
		IPage <AnswerQuestionLogDetail> page = new Page<>();
		page.setRecords(answerQuestionLogDetailList);
		page.setTotal(answerQuestionLogDetailList.size());
		return Result.OK(page);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param answerQuestionLog
    */
    @RequiresPermissions("test:answer_question_log:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, AnswerQuestionLog answerQuestionLog) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<AnswerQuestionLog> queryWrapper = QueryGenerator.initQueryWrapper(answerQuestionLog, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

     //配置选中数据查询条件
      String selections = request.getParameter("selections");
      if(oConvertUtils.isNotEmpty(selections)) {
           List<String> selectionList = Arrays.asList(selections.split(","));
           queryWrapper.in("id",selectionList);
      }
      //Step.2 获取导出数据
      List<AnswerQuestionLog>  answerQuestionLogList = answerQuestionLogService.list(queryWrapper);

      // Step.3 组装pageList
      List<AnswerQuestionLogPage> pageList = new ArrayList<AnswerQuestionLogPage>();
      for (AnswerQuestionLog main : answerQuestionLogList) {
          AnswerQuestionLogPage vo = new AnswerQuestionLogPage();
          BeanUtils.copyProperties(main, vo);
          List<AnswerQuestionLogDetail> answerQuestionLogDetailList = answerQuestionLogDetailService.selectByMainId(main.getId());
          vo.setAnswerQuestionLogDetailList(answerQuestionLogDetailList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "answer_question_log列表");
      mv.addObject(NormalExcelConstants.CLASS, AnswerQuestionLogPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("answer_question_log数据", "导出人:"+sysUser.getRealname(), "answer_question_log"));
      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      return mv;
    }

    /**
    * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("test:answer_question_log:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
          // 获取上传文件对象
          MultipartFile file = entity.getValue();
          ImportParams params = new ImportParams();
          params.setTitleRows(2);
          params.setHeadRows(1);
          params.setNeedSave(true);
          try {
              List<AnswerQuestionLogPage> list = ExcelImportUtil.importExcel(file.getInputStream(), AnswerQuestionLogPage.class, params);
              for (AnswerQuestionLogPage page : list) {
                  AnswerQuestionLog po = new AnswerQuestionLog();
                  BeanUtils.copyProperties(page, po);
                  answerQuestionLogService.saveMain(po, page.getAnswerQuestionLogDetailList());
              }
              return Result.OK("文件导入成功！数据行数:" + list.size());
          } catch (Exception e) {
              log.error(e.getMessage(),e);
              return Result.error("文件导入失败:"+e.getMessage());
          } finally {
              try {
                  file.getInputStream().close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      return Result.OK("文件导入失败！");
    }

}
