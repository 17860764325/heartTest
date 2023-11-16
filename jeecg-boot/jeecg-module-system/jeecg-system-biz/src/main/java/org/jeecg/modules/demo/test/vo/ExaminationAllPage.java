package org.jeecg.modules.demo.test.vo;

import java.util.List;

import org.jeecg.modules.demo.test.entity.AchievementStandard;
import org.jeecg.modules.demo.test.entity.ExaminationAll;
import org.jeecg.modules.demo.test.entity.ExaminationPaperAchievementStandard;
import org.jeecg.modules.demo.test.entity.ExaminationTitle;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelEntity;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 试卷表
 * @Author: jeecg-boot
 * @Date:   2023-11-07
 * @Version: V1.0
 */
@Data
@ApiModel(value="examination_allPage对象", description="试卷表")
public class ExaminationAllPage {

	/**id*/
	@ApiModelProperty(value = "id")
    private String id;
	/**创建人*/
	@ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "创建日期")
    private Date createTime;
	/**更新人*/
	@ApiModelProperty(value = "更新人")
    private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "更新日期")
    private Date updateTime;
	/**所属部门*/
	@ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
	/**试卷名称*/
	@Excel(name = "试卷名称", width = 15)
	@ApiModelProperty(value = "试卷名称")
    private String examinationPaper;
	/**试卷编码*/
	@Excel(name = "试卷编码", width = 15)
	@ApiModelProperty(value = "试卷编码")
    private String examinationCode;
	/**试卷介绍*/
	@Excel(name = "试卷介绍", width = 15)
	@ApiModelProperty(value = "试卷介绍")
    private String examinationAbout;
	/**开始时间*/
	@Excel(name = "开始时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(value = "开始时间")
    private Date startTime;
	/**结束时间*/
	@Excel(name = "结束时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(value = "结束时间")
    private Date endTime;
	/**试卷类型*/
	@Excel(name = "试卷类型", width = 15)
	@ApiModelProperty(value = "试卷类型")
    private String examinationType;
	/**限制时间*/
	@Excel(name = "限制时间", width = 15)
	@ApiModelProperty(value = "限制时间")
    private String limitTime;
	/**及格分数*/
	@Excel(name = "及格分数", width = 15)
	@ApiModelProperty(value = "及格分数")
    private java.math.BigDecimal score;
	/**预留字段*/
	@Excel(name = "预留字段", width = 15)
	@ApiModelProperty(value = "预留字段")
    private String remark;
	
	@ExcelCollection(name="examination_title")
	@ApiModelProperty(value = "examination_title")
	private List<ExaminationTitle> examinationTitleList;

	@ExcelCollection(name="examination_paper_achievement_standard")
	@ApiModelProperty(value = "examination_paper_achievement_standard")
	private List<ExaminationPaperAchievementStandard> examinationPaperAchievementStandardList;
	
}
