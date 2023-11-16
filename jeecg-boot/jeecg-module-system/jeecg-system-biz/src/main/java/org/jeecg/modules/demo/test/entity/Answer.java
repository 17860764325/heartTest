package org.jeecg.modules.demo.test.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import org.jeecg.common.aspect.annotation.Dict;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.UnsupportedEncodingException;

/**
 * @Description: 答案表
 * @Author: jeecg-boot
 * @Date:   2023-11-08
 * @Version: V1.0
 */
@Data
@TableName("answer")
@ApiModel(value="answer对象", description="答案表")
public class Answer implements Serializable {
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
	/**答案名称*/
	@Excel(name = "答案名称", width = 15)
    @ApiModelProperty(value = "答案名称")
    private String answerName;
	/**答案名称*/
	@Excel(name = "答案名称", width = 15)
    @ApiModelProperty(value = "答案名称")
    private String answerCode;
	/**分值*/
	@Excel(name = "分值", width = 15)
    @ApiModelProperty(value = "分值")
    private String answerScore;
	/**问题 id*/
    @ApiModelProperty(value = "问题 id")
    private String questionId;
	/**预留字段*/
	@Excel(name = "预留字段", width = 15)
    @ApiModelProperty(value = "预留字段")
    private String remark;
	/**预留字段 1*/
	@Excel(name = "预留字段 1", width = 15)
    @ApiModelProperty(value = "预留字段 1")
    private String remark1;
}
