package org.jeecg.modules.demo.page.controller;

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
import org.jeecg.modules.demo.page.entity.PageInfo;
import org.jeecg.modules.demo.page.service.IPageInfoService;

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
 * @Description: page_info
 * @Author: jeecg-boot
 * @Date:   2023-11-14
 * @Version: V1.0
 */
@Api(tags="page_info")
@RestController
@RequestMapping("/page/pageInfo")
@Slf4j
public class PageInfoController extends JeecgController<PageInfo, IPageInfoService> {
	@Autowired
	private IPageInfoService pageInfoService;
	
	/**
	 * 分页列表查询
	 *
	 * @param pageInfo
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "page_info-分页列表查询")
	@ApiOperation(value="page_info-分页列表查询", notes="page_info-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<PageInfo>> queryPageList(PageInfo pageInfo,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="1000") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<PageInfo> queryWrapper = QueryGenerator.initQueryWrapper(pageInfo, req.getParameterMap());
		Page<PageInfo> page = new Page<PageInfo>(pageNo, pageSize);
		IPage<PageInfo> pageList = pageInfoService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param pageInfo
	 * @return
	 */
	@AutoLog(value = "page_info-添加")
	@ApiOperation(value="page_info-添加", notes="page_info-添加")
	@RequiresPermissions("page:page_info:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody PageInfo pageInfo) {
		pageInfoService.save(pageInfo);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param pageInfo
	 * @return
	 */
	@AutoLog(value = "page_info-编辑")
	@ApiOperation(value="page_info-编辑", notes="page_info-编辑")
	@RequiresPermissions("page:page_info:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody PageInfo pageInfo) {
		pageInfoService.updateById(pageInfo);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "page_info-通过id删除")
	@ApiOperation(value="page_info-通过id删除", notes="page_info-通过id删除")
	@RequiresPermissions("page:page_info:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		pageInfoService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "page_info-批量删除")
	@ApiOperation(value="page_info-批量删除", notes="page_info-批量删除")
	@RequiresPermissions("page:page_info:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.pageInfoService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "page_info-通过id查询")
	@ApiOperation(value="page_info-通过id查询", notes="page_info-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<PageInfo> queryById(@RequestParam(name="id",required=true) String id) {
		PageInfo pageInfo = pageInfoService.getById(id);
		if(pageInfo==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(pageInfo);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param pageInfo
    */
    @RequiresPermissions("page:page_info:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, PageInfo pageInfo) {
        return super.exportXls(request, pageInfo, PageInfo.class, "page_info");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("page:page_info:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, PageInfo.class);
    }

	@GetMapping("/getAllPage/{type}")
	public Result<?> getAllPage(@PathVariable("type") String type){
		return pageInfoService.getAllPage(type);
	}

}
