package org.jeecg.modules.demo.test.entity;

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
 * @Description: achievement_standard
 * @Author: jeecg-boot
 * @Date:   2023-11-09
 * @Version: V1.0
 */
@Data
@TableName("achievement_standard")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="achievement_standard对象", description="achievement_standard")
public class AchievementStandard implements Serializable {
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
	/**区间最高值*/
	@Excel(name = "区间最高值", width = 15)
    @ApiModelProperty(value = "区间最高值")
    private BigDecimal high;
	/**区间低值*/
	@Excel(name = "区间低值", width = 15)
    @ApiModelProperty(value = "区间低值")
    private BigDecimal low;
	/**该区间段评价结果*/
	@Excel(name = "该区间段评价结果", width = 15)
    @ApiModelProperty(value = "该区间段评价结果")
    private String comment;
	/**心理状态*/
	@Excel(name = "心理状态", width = 15)
    @ApiModelProperty(value = "心理状态")
    private String psychologicalState;
	/**预留字段*/
	@Excel(name = "预留字段", width = 15)
    @ApiModelProperty(value = "预留字段")
    private String remark;
	/**预留字段 2*/
	@Excel(name = "预留字段 2", width = 15)
    @ApiModelProperty(value = "预留字段 2")
    private String remark2;
	/**成绩评判标准名称*/
	@Excel(name = "成绩评判标准名称", width = 15)
    @ApiModelProperty(value = "成绩评判标准名称")
    private String achievementName;
	/**成绩评判标准编码*/
	@Excel(name = "成绩评判标准编码", width = 15)
    @ApiModelProperty(value = "成绩评判标准编码")
    private String achievementCode;
}
