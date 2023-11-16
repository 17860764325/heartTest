package org.jeecg.modules.demo.test.service.impl;

import org.jeecg.modules.demo.test.entity.Answer;
import org.jeecg.modules.demo.test.mapper.AnswerMapper;
import org.jeecg.modules.demo.test.service.IAnswerService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 答案表
 * @Author: jeecg-boot
 * @Date:   2023-11-08
 * @Version: V1.0
 */
@Service
public class AnswerServiceImpl extends ServiceImpl<AnswerMapper, Answer> implements IAnswerService {
	
	@Autowired
	private AnswerMapper answerMapper;
	
	@Override
	public List<Answer> selectByMainId(String mainId) {
		return answerMapper.selectByMainId(mainId);
	}
}
