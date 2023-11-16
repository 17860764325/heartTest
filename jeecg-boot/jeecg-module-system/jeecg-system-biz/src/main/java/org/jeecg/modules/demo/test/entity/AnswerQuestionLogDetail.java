package org.jeecg.modules.demo.test.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import java.util.Date;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.UnsupportedEncodingException;

/**
 * @Description: answer_question_log_detail
 * @Author: jeecg-boot
 * @Date:   2023-11-08
 * @Version: V1.0
 */
@ApiModel(value="answer_question_log_detail对象", description="answer_question_log_detail")
@Data
@TableName("answer_question_log_detail")
public class AnswerQuestionLogDetail implements Serializable {
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
	/**问题名称*/
	@Excel(name = "问题名称", width = 15)
    @ApiModelProperty(value = "问题名称")
    private String titleName;
	/**题目编码*/
	@Excel(name = "题目编码", width = 15)
    @ApiModelProperty(value = "题目编码")
    private String titleCode;
	/**答案编码*/
	@Excel(name = "答案编码", width = 15)
    @ApiModelProperty(value = "答案编码")
    private String answerCode;
	/**得分*/
	@Excel(name = "得分", width = 15)
    @ApiModelProperty(value = "得分")
    private String socre;
	/**答题时间*/
	@Excel(name = "答题时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "答题时间")
    private Date makeTime;
	/**预留字段*/
	@Excel(name = "预留字段", width = 15)
    @ApiModelProperty(value = "预留字段")
    private String remark;
	/**预留字段*/
	@Excel(name = "预留字段", width = 15)
    @ApiModelProperty(value = "预留字段")
    private String remark1;
	/**主表数据的 id*/
    @ApiModelProperty(value = "主表数据的 id")
    private String parintId;
}
