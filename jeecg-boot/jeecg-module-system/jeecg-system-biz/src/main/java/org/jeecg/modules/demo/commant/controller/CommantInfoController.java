package org.jeecg.modules.demo.commant.controller;

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
import org.jeecg.common.aspect.annotation.PermissionData;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.commant.entity.CommantInfo;
import org.jeecg.modules.demo.commant.service.ICommantInfoService;

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
 * @Description: commant_info
 * @Author: jeecg-boot
 * @Date:   2023-11-14
 * @Version: V1.0
 */
@Api(tags="commant_info")
@RestController
@RequestMapping("/commant/commantInfo")
@Slf4j
public class CommantInfoController extends JeecgController<CommantInfo, ICommantInfoService> {
	@Autowired
	private ICommantInfoService commantInfoService;
	
	/**
	 * 分页列表查询
	 *
	 * @param commantInfo
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "commant_info-分页列表查询")
	@ApiOperation(value="commant_info-分页列表查询", notes="commant_info-分页列表查询")
	@GetMapping(value = "/list")
	@PermissionData(pageComponent="article/commant/CommantInfoList")
	public Result<IPage<CommantInfo>> queryPageList(CommantInfo commantInfo,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<CommantInfo> queryWrapper = QueryGenerator.initQueryWrapper(commantInfo, req.getParameterMap());
		Page<CommantInfo> page = new Page<CommantInfo>(pageNo, pageSize);
		IPage<CommantInfo> pageList = commantInfoService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param commantInfo
	 * @return
	 */
	@AutoLog(value = "commant_info-添加")
	@ApiOperation(value="commant_info-添加", notes="commant_info-添加")
	@RequiresPermissions("commant:commant_info:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody CommantInfo commantInfo) {
		commantInfoService.save(commantInfo);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param commantInfo
	 * @return
	 */
	@AutoLog(value = "commant_info-编辑")
	@ApiOperation(value="commant_info-编辑", notes="commant_info-编辑")
	@RequiresPermissions("commant:commant_info:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody CommantInfo commantInfo) {
		commantInfoService.updateById(commantInfo);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "commant_info-通过id删除")
	@ApiOperation(value="commant_info-通过id删除", notes="commant_info-通过id删除")
	@RequiresPermissions("commant:commant_info:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		commantInfoService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "commant_info-批量删除")
	@ApiOperation(value="commant_info-批量删除", notes="commant_info-批量删除")
	@RequiresPermissions("commant:commant_info:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.commantInfoService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "commant_info-通过id查询")
	@ApiOperation(value="commant_info-通过id查询", notes="commant_info-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<CommantInfo> queryById(@RequestParam(name="id",required=true) String id) {
		CommantInfo commantInfo = commantInfoService.getById(id);
		if(commantInfo==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(commantInfo);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param commantInfo
    */
    @RequiresPermissions("commant:commant_info:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CommantInfo commantInfo) {
        return super.exportXls(request, commantInfo, CommantInfo.class, "commant_info");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("commant:commant_info:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, CommantInfo.class);
    }

	@ApiOperation(value="通过文章id查询评论列表", notes="通过文章id查询评论列表")
		@GetMapping("/getCommantByPageId/{pageId}")
	public Result<?> getCommantByPageId(@PathVariable("pageId") String pageId){
		return commantInfoService.getCommantByPageId(pageId);
	}

}
