package org.jeecg.modules.demo.chars.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.demo.chars.service.ICharsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: chat_log
 * @Author: jeecg-boot
 * @Date:   2023-11-10
 * @Version: V1.0
 */
@Api(tags="表格数据统计-数据可视化")
@RestController
@RequestMapping("/chars")
@Slf4j
public class CharsController  {
	@Autowired
	private ICharsService chatLogService;


	@AutoLog(value = "个人历史数据查询")
	@ApiOperation(value="个人历史数据查询", notes="个人历史数据查询")
	@GetMapping("/getHistory/{pageType}")
	public String getHistory(@PathVariable("pageType") String pageType){
		return chatLogService.getHistory(pageType);
	}


	@AutoLog(value = "性格缺点倾向")
	@ApiOperation(value="性格缺点倾向", notes="性格缺点倾向")
	@GetMapping("/personalityShortcomingsTendencies")
	public String personalityShortcomingsTendencies(){
		return chatLogService.personalityShortcomingsTendencies();
	}

	@AutoLog(value = "活跃统计")
	@ApiOperation(value="活跃统计", notes="活跃统计")
	@GetMapping("/activeStatistics")
	public String activeStatistics(){
		return chatLogService.activeStatistics();
	}


	@AutoLog(value = "各学生部门，专业，")
	@ApiOperation(value="各学生部门，专业，班级人员的数据", notes="各学生部门，专业，班级人员的数据")
	@GetMapping("/getPeopleFromAll/{type}")
	public String getPeopleFromAll(@PathVariable("type") String type){
		return chatLogService.getPeopleFromAll(type);
	}








}
