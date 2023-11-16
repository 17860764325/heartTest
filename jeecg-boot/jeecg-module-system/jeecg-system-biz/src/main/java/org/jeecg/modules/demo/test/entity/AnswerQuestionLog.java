package org.jeecg.modules.demo.test.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: answer_question_log
 * @Author: jeecg-boot
 * @Date:   2023-11-08
 * @Version: V1.0
 */
@ApiModel(value="answer_question_log对象", description="answer_question_log")
@Data
@TableName("answer_question_log")
public class AnswerQuestionLog implements Serializable {
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
	/**试卷编码*/
	@Excel(name = "试卷编码", width = 15)
    @ApiModelProperty(value = "试卷编码")
    private String paperCode;
	/**试卷名称*/
	@Excel(name = "试卷名称", width = 15)
    @ApiModelProperty(value = "试卷名称")
    private String paperName;
	/**总分*/
	@Excel(name = "总分", width = 15)
    @ApiModelProperty(value = "总分")
    private String totalScore;
	/**试卷分类*/
	@Excel(name = "试卷分类", width = 15)
    @ApiModelProperty(value = "试卷分类")
    private String paperType;
	/**预留字段*/
	@Excel(name = "预留字段", width = 15)
    @ApiModelProperty(value = "预留字段")
    private String remark;
	/**预留字段 1*/
	@Excel(name = "预留字段 1", width = 15)
    @ApiModelProperty(value = "预留字段 1")
    private String remark1;


    @TableField(exist = false)
    private String time;
}
