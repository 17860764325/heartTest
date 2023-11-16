package org.jeecg.modules.demo.test.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.test.entity.AchievementStandard;
import org.jeecg.modules.demo.test.entity.AnswerQuestionLog;
import org.jeecg.modules.demo.test.service.IAchievementStandardService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

 /**
 * @Description: achievement_standard
 * @Author: jeecg-boot
 * @Date:   2023-11-09
 * @Version: V1.0
 */
@Api(tags="achievement_standard")
@RestController
@RequestMapping("/test/achievementStandard")
@Slf4j
public class AchievementStandardController extends JeecgController<AchievementStandard, IAchievementStandardService> {
	@Autowired
	private IAchievementStandardService achievementStandardService;
	
	/**
	 * 分页列表查询
	 *
	 * @param achievementStandard
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "achievement_standard-分页列表查询")
	@ApiOperation(value="achievement_standard-分页列表查询", notes="achievement_standard-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<AchievementStandard>> queryPageList(AchievementStandard achievementStandard,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<AchievementStandard> queryWrapper = QueryGenerator.initQueryWrapper(achievementStandard, req.getParameterMap());
		Page<AchievementStandard> page = new Page<AchievementStandard>(pageNo, pageSize);
		IPage<AchievementStandard> pageList = achievementStandardService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param achievementStandard
	 * @return
	 */
	@AutoLog(value = "achievement_standard-添加")
	@ApiOperation(value="achievement_standard-添加", notes="achievement_standard-添加")
	@RequiresPermissions("test:achievement_standard:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody AchievementStandard achievementStandard) {
		achievementStandardService.save(achievementStandard);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param achievementStandard
	 * @return
	 */
	@AutoLog(value = "achievement_standard-编辑")
	@ApiOperation(value="achievement_standard-编辑", notes="achievement_standard-编辑")
	@RequiresPermissions("test:achievement_standard:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody AchievementStandard achievementStandard) {
		achievementStandardService.updateById(achievementStandard);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "achievement_standard-通过id删除")
	@ApiOperation(value="achievement_standard-通过id删除", notes="achievement_standard-通过id删除")
	@RequiresPermissions("test:achievement_standard:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		achievementStandardService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "achievement_standard-批量删除")
	@ApiOperation(value="achievement_standard-批量删除", notes="achievement_standard-批量删除")
	@RequiresPermissions("test:achievement_standard:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.achievementStandardService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "achievement_standard-通过id查询")
	@ApiOperation(value="achievement_standard-通过id查询", notes="achievement_standard-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<AchievementStandard> queryById(@RequestParam(name="id",required=true) String id) {
		AchievementStandard achievementStandard = achievementStandardService.getById(id);
		if(achievementStandard==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(achievementStandard);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param achievementStandard
    */
    @RequiresPermissions("test:achievement_standard:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, AchievementStandard achievementStandard) {
        return super.exportXls(request, achievementStandard, AchievementStandard.class, "achievement_standard");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("test:achievement_standard:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, AchievementStandard.class);
    }
	
	/** 
	 * @description: 根据相应的做题结果来获取对应的成绩详情 
	 * @param: achievementStandard 
	 * @return: org.jeecg.common.api.vo.Result<?> 
	 * @author lhr
	 * @date: 2023/11/10 10:15
	 */ 
//	@RequiresPermissions("test:achievement_standard:importExcel")
    @RequestMapping(value = "/getAchievementStand", method = RequestMethod.POST)
    public Result<?> getAchievementStand(@RequestBody AnswerQuestionLog answerQuestionLog) {
        return achievementStandardService.getAchievementStand(answerQuestionLog);
    }

}
