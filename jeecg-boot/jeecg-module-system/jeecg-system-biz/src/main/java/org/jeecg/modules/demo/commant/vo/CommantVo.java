package org.jeecg.modules.demo.commant.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @author lihaoran
 * @date 2023/11/14 22:04
 */
@Data
public class CommantVo {


    private String id;
    // 文章 id
    private String pageId;
    // 评论人
    private String about;
    // 评论人
    private String createBy;
    // 创建时间
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    // 所有子评论
    private List<CommantVo> children;
    // 父 id
    private String parentId;
    // 头像
    private String avatar;
}
