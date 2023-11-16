package org.jeecg.modules.demo.test.controller;

import org.jeecg.common.system.query.QueryGenerator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.api.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import java.util.Arrays;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.test.entity.Answer;
import org.jeecg.modules.demo.test.entity.Title;
import org.jeecg.modules.demo.test.service.ITitleService;
import org.jeecg.modules.demo.test.service.IAnswerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.shiro.authz.annotation.RequiresPermissions;

 /**
 * @Description: 题目表
 * @Author: jeecg-boot
 * @Date:   2023-11-08
 * @Version: V1.0
 */
@Api(tags="题目表")
@RestController
@RequestMapping("/test/title")
@Slf4j
public class TitleController extends JeecgController<Title, ITitleService> {

	@Autowired
	private ITitleService titleService;

	@Autowired
	private IAnswerService answerService;


	/*---------------------------------主表处理-begin-------------------------------------*/

	/**
	 * 分页列表查询
	 * @param title
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "题目表-分页列表查询")
	@ApiOperation(value="题目表-分页列表查询", notes="题目表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<Title>> queryPageList(Title title,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<Title> queryWrapper = QueryGenerator.initQueryWrapper(title, req.getParameterMap());
		Page<Title> page = new Page<Title>(pageNo, pageSize);
		IPage<Title> pageList = titleService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
     *   添加
     * @param title
     * @return
     */
    @AutoLog(value = "题目表-添加")
    @ApiOperation(value="题目表-添加", notes="题目表-添加")
    @RequiresPermissions("test:title:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody Title title) {
        titleService.save(title);
        return Result.OK("添加成功！");
    }

    /**
     *  编辑
     * @param title
     * @return
     */
    @AutoLog(value = "题目表-编辑")
    @ApiOperation(value="题目表-编辑", notes="题目表-编辑")
    @RequiresPermissions("test:title:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
    public Result<String> edit(@RequestBody Title title) {
        titleService.updateById(title);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     * @param id
     * @return
     */
    @AutoLog(value = "题目表-通过id删除")
    @ApiOperation(value="题目表-通过id删除", notes="题目表-通过id删除")
    @RequiresPermissions("test:title:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name="id",required=true) String id) {
        titleService.delMain(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @AutoLog(value = "题目表-批量删除")
    @ApiOperation(value="题目表-批量删除", notes="题目表-批量删除")
    @RequiresPermissions("test:title:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
        this.titleService.delBatchMain(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 导出
     * @return
     */
    @RequiresPermissions("test:title:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Title title) {
        return super.exportXls(request, title, Title.class, "题目表");
    }

    /**
     * 导入
     * @return
     */
    @RequiresPermissions("test:title:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, Title.class);
    }
	/*---------------------------------主表处理-end-------------------------------------*/
	

    /*--------------------------------子表处理-答案表-begin----------------------------------------------*/
	/**
	 * 通过主表ID查询
	 * @return
	 */
	//@AutoLog(value = "答案表-通过主表ID查询")
	@ApiOperation(value="答案表-通过主表ID查询", notes="答案表-通过主表ID查询")
	@GetMapping(value = "/listAnswerByMainId")
    public Result<IPage<Answer>> listAnswerByMainId(Answer answer,
                                                    @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                    HttpServletRequest req) {
        QueryWrapper<Answer> queryWrapper = QueryGenerator.initQueryWrapper(answer, req.getParameterMap());
        Page<Answer> page = new Page<Answer>(pageNo, pageSize);
        IPage<Answer> pageList = answerService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

	/**
	 * 添加
	 * @param answer
	 * @return
	 */
	@AutoLog(value = "答案表-添加")
	@ApiOperation(value="答案表-添加", notes="答案表-添加")
	@PostMapping(value = "/addAnswer")
	public Result<String> addAnswer(@RequestBody Answer answer) {
		answerService.save(answer);
		return Result.OK("添加成功！");
	}

    /**
	 * 编辑
	 * @param answer
	 * @return
	 */
	@AutoLog(value = "答案表-编辑")
	@ApiOperation(value="答案表-编辑", notes="答案表-编辑")
	@RequestMapping(value = "/editAnswer", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> editAnswer(@RequestBody Answer answer) {
		answerService.updateById(answer);
		return Result.OK("编辑成功!");
	}

	/**
	 * 通过id删除
	 * @param id
	 * @return
	 */
	@AutoLog(value = "答案表-通过id删除")
	@ApiOperation(value="答案表-通过id删除", notes="答案表-通过id删除")
	@DeleteMapping(value = "/deleteAnswer")
	public Result<String> deleteAnswer(@RequestParam(name="id",required=true) String id) {
		answerService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "答案表-批量删除")
	@ApiOperation(value="答案表-批量删除", notes="答案表-批量删除")
	@DeleteMapping(value = "/deleteBatchAnswer")
	public Result<String> deleteBatchAnswer(@RequestParam(name="ids",required=true) String ids) {
	    this.answerService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

    /**
     * 导出
     * @return
     */
    @RequestMapping(value = "/exportAnswer")
    public ModelAndView exportAnswer(HttpServletRequest request, Answer answer) {
		 // Step.1 组装查询条件
		 QueryWrapper<Answer> queryWrapper = QueryGenerator.initQueryWrapper(answer, request.getParameterMap());
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

		 // Step.2 获取导出数据
		 List<Answer> pageList = answerService.list(queryWrapper);
		 List<Answer> exportList = null;

		 // 过滤选中数据
		 String selections = request.getParameter("selections");
		 if (oConvertUtils.isNotEmpty(selections)) {
			 List<String> selectionList = Arrays.asList(selections.split(","));
			 exportList = pageList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
		 } else {
			 exportList = pageList;
		 }

		 // Step.3 AutoPoi 导出Excel
		 ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		 //此处设置的filename无效,前端会重更新设置一下
		 mv.addObject(NormalExcelConstants.FILE_NAME, "答案表");
		 mv.addObject(NormalExcelConstants.CLASS, Answer.class);
		 mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("答案表报表", "导出人:" + sysUser.getRealname(), "答案表"));
		 mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
		 return mv;
    }

    /**
     * 导入
     * @return
     */
    @RequestMapping(value = "/importAnswer/{mainId}")
    public Result<?> importAnswer(HttpServletRequest request, HttpServletResponse response, @PathVariable("mainId") String mainId) {
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
				 List<Answer> list = ExcelImportUtil.importExcel(file.getInputStream(), Answer.class, params);
				 for (Answer temp : list) {
                    temp.setQuestionId(mainId);
				 }
				 long start = System.currentTimeMillis();
				 answerService.saveBatch(list);
				 log.info("消耗时间" + (System.currentTimeMillis() - start) + "毫秒");
				 return Result.OK("文件导入成功！数据行数：" + list.size());
			 } catch (Exception e) {
				 log.error(e.getMessage(), e);
				 return Result.error("文件导入失败:" + e.getMessage());
			 } finally {
				 try {
					 file.getInputStream().close();
				 } catch (IOException e) {
					 e.printStackTrace();
				 }
			 }
		 }
		 return Result.error("文件导入失败！");
    }

    /*--------------------------------子表处理-答案表-end----------------------------------------------*/




}
