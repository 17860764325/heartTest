package org.jeecg.modules.demo.test.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import org.jeecgframework.poi.excel.annotation.Excel;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 题目表
 * @Author: jeecg-boot
 * @Date:   2023-11-08
 * @Version: V1.0
 */
@Data
@TableName("title")
@ApiModel(value="title对象", description="题目表")
public class Title implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private java.lang.String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
	/**题目名称*/
    @Excel(name = "题目名称", width = 15)
    @ApiModelProperty(value = "题目名称")
    private java.lang.String titleName;
	/**题目编码*/
    @Excel(name = "题目编码", width = 15)
    @ApiModelProperty(value = "题目编码")
    private java.lang.String titleCode;
	/**题目类型*/
    @Excel(name = "题目类型", width = 15)
    @ApiModelProperty(value = "题目类型")
    private java.lang.String titleType;
	/**预留字段*/
    @Excel(name = "预留字段", width = 15)
    @ApiModelProperty(value = "预留字段")
    private java.lang.String remark;
}
