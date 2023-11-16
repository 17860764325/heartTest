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
 * @Description: examination_paper_achievement_standard
 * @Author: jeecg-boot
 * @Date:   2023-11-09
 * @Version: V1.0
 */
@ApiModel(value="examination_paper_achievement_standard对象", description="examination_paper_achievement_standard")
@Data
@TableName("examination_paper_achievement_standard")
public class ExaminationPaperAchievementStandard implements Serializable {
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
    @ApiModelProperty(value = "updateTime")
    private String updateTime;
	/**试卷编码*/
    @ApiModelProperty(value = "试卷编码")
    private String paperCode;
	/**成绩标准 id*/
	@Excel(name = "成绩标准 id", width = 15)
    @ApiModelProperty(value = "成绩标准 id")
    private String achievementId;
	/**评判标准名称*/
	@Excel(name = "评判标准名称", width = 15)
    @ApiModelProperty(value = "评判标准名称")
    private String achievementName;
	/**预留字段*/
	@Excel(name = "预留字段", width = 15)
    @ApiModelProperty(value = "预留字段")
    private String remark;
	/**预留字段 2*/
	@Excel(name = "预留字段 2", width = 15)
    @ApiModelProperty(value = "预留字段 2")
    private String remark1;
}
