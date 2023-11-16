package org.jeecg.modules.demo.commant.entity;

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
 * @Description: commant_info
 * @Author: jeecg-boot
 * @Date:   2023-11-14
 * @Version: V1.0
 */
@Data
@TableName("commant_info")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="commant_info对象", description="commant_info")
public class CommantInfo implements Serializable {
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
	/**评论 id*/
	@Excel(name = "评论 id", width = 15)
    @ApiModelProperty(value = "评论 id")
    private String pageId;
	/**评论内容*/
	@Excel(name = "评论内容", width = 15)
    @ApiModelProperty(value = "评论内容")
    private String about;
	/**父id*/
	@Excel(name = "父id", width = 15)
    @ApiModelProperty(value = "父id")
    private String parentId;
	/** 预留字段*/
	@Excel(name = " 预留字段", width = 15)
    @ApiModelProperty(value = " 预留字段")
    private String remark;
	/** 预留字段 1*/
	@Excel(name = " 预留字段 1", width = 15)
    @ApiModelProperty(value = " 预留字段 1")
    private String remark1;
}
