package org.jeecg.modules.demo.test.service.impl;

import org.jeecg.modules.demo.chars.vo.PersonalityShortcomingsTendencies;
import org.jeecg.modules.demo.test.entity.AnswerQuestionLog;
import org.jeecg.modules.demo.test.entity.AnswerQuestionLogDetail;
import org.jeecg.modules.demo.test.mapper.AnswerQuestionLogDetailMapper;
import org.jeecg.modules.demo.test.mapper.AnswerQuestionLogMapper;
import org.jeecg.modules.demo.test.service.IAnswerQuestionLogService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: answer_question_log
 * @Author: jeecg-boot
 * @Date:   2023-11-08
 * @Version: V1.0
 */
@Service
public class AnswerQuestionLogServiceImpl extends ServiceImpl<AnswerQuestionLogMapper, AnswerQuestionLog> implements IAnswerQuestionLogService {

	@Autowired
	private AnswerQuestionLogMapper answerQuestionLogMapper;
	@Autowired
	private AnswerQuestionLogDetailMapper answerQuestionLogDetailMapper;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveMain(AnswerQuestionLog answerQuestionLog, List<AnswerQuestionLogDetail> answerQuestionLogDetailList) {
		answerQuestionLogMapper.insert(answerQuestionLog);
		if(answerQuestionLogDetailList!=null && answerQuestionLogDetailList.size()>0) {
			for(AnswerQuestionLogDetail entity:answerQuestionLogDetailList) {
				//外键设置
				entity.setParintId(answerQuestionLog.getId());
				answerQuestionLogDetailMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateMain(AnswerQuestionLog answerQuestionLog,List<AnswerQuestionLogDetail> answerQuestionLogDetailList) {
		answerQuestionLogMapper.updateById(answerQuestionLog);
		
		//1.先删除子表数据
		answerQuestionLogDetailMapper.deleteByMainId(answerQuestionLog.getId());
		
		//2.子表数据重新插入
		if(answerQuestionLogDetailList!=null && answerQuestionLogDetailList.size()>0) {
			for(AnswerQuestionLogDetail entity:answerQuestionLogDetailList) {
				//外键设置
				entity.setParintId(answerQuestionLog.getId());
				answerQuestionLogDetailMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delMain(String id) {
		answerQuestionLogDetailMapper.deleteByMainId(id);
		answerQuestionLogMapper.deleteById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			answerQuestionLogDetailMapper.deleteByMainId(id.toString());
			answerQuestionLogMapper.deleteById(id);
		}
	}

	@Override
	public List<PersonalityShortcomingsTendencies> getAvgSocereByPeople(String username) {
		return answerQuestionLogDetailMapper.getAvgSocereByPeople(username);
	}

}
