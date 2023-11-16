package org.jeecg.modules.demo.chat.controller;

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
import org.jeecg.modules.demo.chat.entity.ChatLog;
import org.jeecg.modules.demo.chat.service.IChatLogService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.demo.chat.vo.ChatLogVo;
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
 * @Description: chat_log
 * @Author: jeecg-boot
 * @Date:   2023-11-10
 * @Version: V1.0
 */
@Api(tags="chat_log")
@RestController
@RequestMapping("/chat/chatLog")
@Slf4j
public class ChatLogController extends JeecgController<ChatLog, IChatLogService> {
	@Autowired
	private IChatLogService chatLogService;
	
	/**
	 * 分页列表查询
	 *
	 * @param chatLog
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "chat_log-分页列表查询")
	@ApiOperation(value="chat_log-分页列表查询", notes="chat_log-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<ChatLog>> queryPageList(ChatLog chatLog,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ChatLog> queryWrapper = QueryGenerator.initQueryWrapper(chatLog, req.getParameterMap());
		Page<ChatLog> page = new Page<ChatLog>(pageNo, pageSize);
		IPage<ChatLog> pageList = chatLogService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param chatLog
	 * @return
	 */
	@AutoLog(value = "chat_log-添加")
	@ApiOperation(value="chat_log-添加", notes="chat_log-添加")
	@RequiresPermissions("chat:chat_log:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody ChatLog chatLog) {
		chatLogService.save(chatLog);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param chatLog
	 * @return
	 */
	@AutoLog(value = "chat_log-编辑")
	@ApiOperation(value="chat_log-编辑", notes="chat_log-编辑")
	@RequiresPermissions("chat:chat_log:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody ChatLog chatLog) {
		chatLogService.updateById(chatLog);
		return Result.OK("编辑成功!");
	}

	/**
	 *  查询聊天记录
	 *
	 * @param chatLog
	 * @return
	 */
	@AutoLog(value = "查询聊天记录")
	@ApiOperation(value="查询聊天记录", notes="查询聊天记录")
//	@RequiresPermissions("chat:chat_log:edit")
	@PostMapping("/getChatLog")
	public Result<List<ChatLog>> getChatLog(@RequestBody ChatLogVo chatLog) {
		return chatLogService.getChatLog(chatLog);
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "chat_log-通过id删除")
	@ApiOperation(value="chat_log-通过id删除", notes="chat_log-通过id删除")
	@RequiresPermissions("chat:chat_log:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		chatLogService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "chat_log-批量删除")
	@ApiOperation(value="chat_log-批量删除", notes="chat_log-批量删除")
	@RequiresPermissions("chat:chat_log:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.chatLogService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "chat_log-通过id查询")
	@ApiOperation(value="chat_log-通过id查询", notes="chat_log-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<ChatLog> queryById(@RequestParam(name="id",required=true) String id) {
		ChatLog chatLog = chatLogService.getById(id);
		if(chatLog==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(chatLog);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param chatLog
    */
    @RequiresPermissions("chat:chat_log:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ChatLog chatLog) {
        return super.exportXls(request, chatLog, ChatLog.class, "chat_log");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("chat:chat_log:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, ChatLog.class);
    }




}
