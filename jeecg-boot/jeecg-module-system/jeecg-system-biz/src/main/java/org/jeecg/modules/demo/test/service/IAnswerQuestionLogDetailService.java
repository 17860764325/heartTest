package org.jeecg.modules.demo.test.service;

import org.jeecg.modules.demo.test.entity.AnswerQuestionLogDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: answer_question_log_detail
 * @Author: jeecg-boot
 * @Date:   2023-11-08
 * @Version: V1.0
 */
public interface IAnswerQuestionLogDetailService extends IService<AnswerQuestionLogDetail> {

	/**
	 * 通过主表id查询子表数据
	 *
	 * @param mainId 主表id
	 * @return List<AnswerQuestionLogDetail>
	 */
	public List<AnswerQuestionLogDetail> selectByMainId(String mainId);
}
