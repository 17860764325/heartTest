package org.jeecg.modules.demo.chat.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;
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
 * @Description: chat_log
 * @Author: jeecg-boot
 * @Date:   2023-11-10
 * @Version: V1.0
 */
@Data
@TableName("chat_log")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="chat_log对象", description="chat_log")
public class ChatLog implements Serializable {
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
	/**发送方*/
	@Excel(name = "发送方", width = 15)
    @ApiModelProperty(value = "发送方")
    private String sendPerson;
	/**接收方*/
	@Excel(name = "接收方", width = 15)
    @ApiModelProperty(value = "接收方")
    private String recivePerson;
	/**内容*/
	@Excel(name = "内容", width = 15)
    @ApiModelProperty(value = "内容")
    private String about;
	/**备用字段*/
	@Excel(name = "备用字段", width = 15)
    @ApiModelProperty(value = "备用字段")
    private String remark;
	/**备用字段*/
	@Excel(name = "备用字段", width = 15)
    @ApiModelProperty(value = "备用字段")
    private String remark1;
	/**备用字段*/
	@Excel(name = "备用字段", width = 15)
    @ApiModelProperty(value = "备用字段")
    private String remark2;
	/**备用字段*/
	@Excel(name = "备用字段", width = 15)
    @ApiModelProperty(value = "备用字段")
    private String remark3;
	/**备用字段*/
	@Excel(name = "备用字段", width = 15)
    @ApiModelProperty(value = "备用字段")
    private String remark4;
	/**备用字段*/
	@Excel(name = "备用字段", width = 15)
    @ApiModelProperty(value = "备用字段")
    private String remark5;


    @TableField(exist = false)
    private String sendRealName;
    @TableField(exist = false)
    private String receiveRealName;
}
