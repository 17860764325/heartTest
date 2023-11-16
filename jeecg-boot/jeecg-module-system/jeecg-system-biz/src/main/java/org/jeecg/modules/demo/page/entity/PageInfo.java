package org.jeecg.modules.demo.page.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: page_info
 * @Author: jeecg-boot
 * @Date:   2023-11-14
 * @Version: V1.0
 */
@Data
@TableName("page_info")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="page_info对象", description="page_info")
public class PageInfo implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;
	/**createBy*/
    @ApiModelProperty(value = "createBy")
    private String createBy;
	/**createTime*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "createTime")
    private Date createTime;
	/**updateBy*/
    @ApiModelProperty(value = "updateBy")
    private String updateBy;
	/**updateTime*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "updateTime")
    private Date updateTime;
	/**columnName6*/
	@Excel(name = "columnName6", width = 15)
    @ApiModelProperty(value = "columnName6")
    private Integer columnName6;
	/**文章编码*/
	@Excel(name = "文章编码", width = 15)
    @ApiModelProperty(value = "文章编码")
    private String pageCode;
	/**文章名称*/
	@Excel(name = "文章名称", width = 15)
    @ApiModelProperty(value = "文章名称")
    private String pageName;
	/**文章类型*/
	@Excel(name = "文章类型", width = 15)
    @ApiModelProperty(value = "文章类型")
    private String pageType;
	/**文章标题*/
	@Excel(name = "文章标题", width = 15)
    @ApiModelProperty(value = "文章标题")
    private String title;
	/**作者*/
	@Excel(name = "作者", width = 15)
    @ApiModelProperty(value = "作者")
    private String createPeople;
	/** 内容*/
	@Excel(name = " 内容", width = 15)
    @ApiModelProperty(value = " 内容")
    private String about;
	/** 预留字段*/
	@Excel(name = " 预留字段", width = 15)
    @ApiModelProperty(value = " 预留字段")
    private String remark;
	/** 预留zi'd*/
	@Excel(name = " 预留zi'd", width = 15)
    @ApiModelProperty(value = " 预留zi'd")
    private Integer remark1;
}
