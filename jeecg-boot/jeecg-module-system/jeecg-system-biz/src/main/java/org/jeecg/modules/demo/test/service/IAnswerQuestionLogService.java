package org.jeecg.modules.demo.test.service;

import org.jeecg.modules.demo.chars.vo.PersonalityShortcomingsTendencies;
import org.jeecg.modules.demo.test.entity.AnswerQuestionLogDetail;
import org.jeecg.modules.demo.test.entity.AnswerQuestionLog;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: answer_question_log
 * @Author: jeecg-boot
 * @Date:   2023-11-08
 * @Version: V1.0
 */
public interface IAnswerQuestionLogService extends IService<AnswerQuestionLog> {

	/**
	 * 添加一对多
	 *
	 * @param answerQuestionLog
	 * @param answerQuestionLogDetailList
	 */
	public void saveMain(AnswerQuestionLog answerQuestionLog,List<AnswerQuestionLogDetail> answerQuestionLogDetailList) ;
	
	/**
	 * 修改一对多
	 *
	 * @param answerQuestionLog
	 * @param answerQuestionLogDetailList
	 */
	public void updateMain(AnswerQuestionLog answerQuestionLog,List<AnswerQuestionLogDetail> answerQuestionLogDetailList);
	
	/**
	 * 删除一对多
	 *
	 * @param id
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 *
	 * @param idList
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);

	public List<PersonalityShortcomingsTendencies> getAvgSocereByPeople(String username);
	
}
