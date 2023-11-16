package org.jeecg.modules.demo.test.service.impl;

import org.jeecg.modules.demo.test.entity.AnswerQuestionLogDetail;
import org.jeecg.modules.demo.test.mapper.AnswerQuestionLogDetailMapper;
import org.jeecg.modules.demo.test.service.IAnswerQuestionLogDetailService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: answer_question_log_detail
 * @Author: jeecg-boot
 * @Date:   2023-11-08
 * @Version: V1.0
 */
@Service
public class AnswerQuestionLogDetailServiceImpl extends ServiceImpl<AnswerQuestionLogDetailMapper, AnswerQuestionLogDetail> implements IAnswerQuestionLogDetailService {
	
	@Autowired
	private AnswerQuestionLogDetailMapper answerQuestionLogDetailMapper;
	
	@Override
	public List<AnswerQuestionLogDetail> selectByMainId(String mainId) {
		return answerQuestionLogDetailMapper.selectByMainId(mainId);
	}
}
