package org.jeecg.modules.demo.test.vo;

import java.util.List;
import org.jeecg.modules.demo.test.entity.Title;
import org.jeecg.modules.demo.test.entity.Answer;
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
 * @Description: 题目表
 * @Author: jeecg-boot
 * @Date:   2023-11-08
 * @Version: V1.0
 */
@Data
@ApiModel(value="titlePage对象", description="题目表")
public class TitlePage {

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
	/**题目名称*/
	@Excel(name = "题目名称", width = 15)
	@ApiModelProperty(value = "题目名称")
    private String titleName;
	/**题目编码*/
	@Excel(name = "题目编码", width = 15)
	@ApiModelProperty(value = "题目编码")
    private String titleCode;
	/**题目类型*/
	@Excel(name = "题目类型", width = 15)
	@ApiModelProperty(value = "题目类型")
    private String titleType;
	/**预留字段*/
	@Excel(name = "预留字段", width = 15)
	@ApiModelProperty(value = "预留字段")
    private String remark;

	@ExcelCollection(name="答案表")
	@ApiModelProperty(value = "答案表")
	private List<Answer> answerList;

}
