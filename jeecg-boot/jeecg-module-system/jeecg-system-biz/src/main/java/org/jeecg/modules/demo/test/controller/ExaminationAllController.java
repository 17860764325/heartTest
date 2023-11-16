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

import org.jeecg.modules.demo.test.entity.ExaminationPaperAchievementStandard;
import org.jeecg.modules.demo.test.service.IExaminationPaperAchievementStandardService;
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
import org.jeecg.modules.demo.test.entity.ExaminationTitle;
import org.jeecg.modules.demo.test.entity.ExaminationAll;
import org.jeecg.modules.demo.test.vo.ExaminationAllPage;
import org.jeecg.modules.demo.test.service.IExaminationAllService;
import org.jeecg.modules.demo.test.service.IExaminationTitleService;
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
 * @Description: 试卷表
 * @Author: jeecg-boot
 * @Date:   2023-11-07
 * @Version: V1.0
 */
@Api(tags="试卷表")
@RestController
@RequestMapping("/test/examinationAll")
@Slf4j
public class ExaminationAllController {
	@Autowired
	private IExaminationAllService examinationAllService;
	@Autowired
	private IExaminationTitleService examinationTitleService;
	@Autowired
	private IExaminationPaperAchievementStandardService examinationPaperAchievementStandardService;
	
	/**
	 * 分页列表查询
	 *
	 * @param examinationAll
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "试卷表-分页列表查询")
	@ApiOperation(value="试卷表-分页列表查询", notes="试卷表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<ExaminationAll>> queryPageList(ExaminationAll examinationAll,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ExaminationAll> queryWrapper = QueryGenerator.initQueryWrapper(examinationAll, req.getParameterMap());
		Page<ExaminationAll> page = new Page<ExaminationAll>(pageNo, pageSize);
		IPage<ExaminationAll> pageList = examinationAllService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param examinationAllPage
	 * @return
	 */
	@AutoLog(value = "试卷表-添加")
	@ApiOperation(value="试卷表-添加", notes="试卷表-添加")
    @RequiresPermissions("test:examination_all:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody ExaminationAllPage examinationAllPage) {
		ExaminationAll examinationAll = new ExaminationAll();
		BeanUtils.copyProperties(examinationAllPage, examinationAll);
		examinationAllService.saveMain(examinationAll, examinationAllPage.getExaminationTitleList(), examinationAllPage.getExaminationPaperAchievementStandardList());
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param examinationAllPage
	 * @return
	 */
	@AutoLog(value = "试卷表-编辑")
	@ApiOperation(value="试卷表-编辑", notes="试卷表-编辑")
    @RequiresPermissions("test:examination_all:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody ExaminationAllPage examinationAllPage) {
		ExaminationAll examinationAll = new ExaminationAll();
		BeanUtils.copyProperties(examinationAllPage, examinationAll);
		ExaminationAll examinationAllEntity = examinationAllService.getById(examinationAll.getId());
		if(examinationAllEntity==null) {
			return Result.error("未找到对应数据");
		}
		examinationAllService.updateMain(examinationAll, examinationAllPage.getExaminationTitleList(), examinationAllPage.getExaminationPaperAchievementStandardList());
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "试卷表-通过id删除")
	@ApiOperation(value="试卷表-通过id删除", notes="试卷表-通过id删除")
    @RequiresPermissions("test:examination_all:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		examinationAllService.delMain(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "试卷表-批量删除")
	@ApiOperation(value="试卷表-批量删除", notes="试卷表-批量删除")
    @RequiresPermissions("test:examination_all:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.examinationAllService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "试卷表-通过id查询")
	@ApiOperation(value="试卷表-通过id查询", notes="试卷表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<ExaminationAll> queryById(@RequestParam(name="id",required=true) String id) {
		ExaminationAll examinationAll = examinationAllService.getById(id);
		if(examinationAll==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(examinationAll);

	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "examination_title-通过主表ID查询")
	@ApiOperation(value="examination_title-通过主表ID查询", notes="examination_title-通过主表ID查询")
	@GetMapping(value = "/queryExaminationTitleByMainId")
	public Result<IPage<ExaminationTitle>> queryExaminationTitleListByMainId(@RequestParam(name="id",required=true) String id) {
		List<ExaminationTitle> examinationTitleList = examinationTitleService.selectByMainId(id);
		IPage <ExaminationTitle> page = new Page<>();
		page.setRecords(examinationTitleList);
		page.setTotal(examinationTitleList.size());
		return Result.OK(page);
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "examination_paper_achievement_standard-通过主表ID查询")
	@ApiOperation(value="examination_paper_achievement_standard-通过主表ID查询", notes="examination_paper_achievement_standard-通过主表ID查询")
	@GetMapping(value = "/queryExaminationPaperAchievementStandardByMainId")
	public Result<IPage<ExaminationPaperAchievementStandard>> queryExaminationPaperAchievementStandardListByMainId(@RequestParam(name="id",required=true) String id) {
		List<ExaminationPaperAchievementStandard> examinationPaperAchievementStandardList = examinationPaperAchievementStandardService.selectByMainId(id);
		IPage <ExaminationPaperAchievementStandard> page = new Page<>();
		page.setRecords(examinationPaperAchievementStandardList);
		page.setTotal(examinationPaperAchievementStandardList.size());
		return Result.OK(page);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param examinationAll
    */
    @RequiresPermissions("test:examination_all:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ExaminationAll examinationAll) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<ExaminationAll> queryWrapper = QueryGenerator.initQueryWrapper(examinationAll, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

     //配置选中数据查询条件
      String selections = request.getParameter("selections");
      if(oConvertUtils.isNotEmpty(selections)) {
           List<String> selectionList = Arrays.asList(selections.split(","));
           queryWrapper.in("id",selectionList);
      }
      //Step.2 获取导出数据
      List<ExaminationAll>  examinationAllList = examinationAllService.list(queryWrapper);

      // Step.3 组装pageList
      List<ExaminationAllPage> pageList = new ArrayList<ExaminationAllPage>();
      for (ExaminationAll main : examinationAllList) {
          ExaminationAllPage vo = new ExaminationAllPage();
          BeanUtils.copyProperties(main, vo);
          List<ExaminationTitle> examinationTitleList = examinationTitleService.selectByMainId(main.getId());
          vo.setExaminationTitleList(examinationTitleList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "试卷表列表");
      mv.addObject(NormalExcelConstants.CLASS, ExaminationAllPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("试卷表数据", "导出人:"+sysUser.getRealname(), "试卷表"));
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
    @RequiresPermissions("test:examination_all:importExcel")
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
              List<ExaminationAllPage> list = ExcelImportUtil.importExcel(file.getInputStream(), ExaminationAllPage.class, params);
              for (ExaminationAllPage page : list) {
                  ExaminationAll po = new ExaminationAll();
                  BeanUtils.copyProperties(page, po);
                  examinationAllService.saveMain(po, page.getExaminationTitleList(),page.getExaminationPaperAchievementStandardList());
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
