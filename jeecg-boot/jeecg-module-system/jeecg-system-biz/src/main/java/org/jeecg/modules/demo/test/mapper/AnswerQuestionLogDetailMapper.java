package org.jeecg.modules.demo.test.mapper;

import java.util.List;

import org.jeecg.modules.demo.chars.vo.PersonalityShortcomingsTendencies;
import org.jeecg.modules.demo.test.entity.AnswerQuestionLogDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: answer_question_log_detail
 * @Author: jeecg-boot
 * @Date:   2023-11-08
 * @Version: V1.0
 */
public interface AnswerQuestionLogDetailMapper extends BaseMapper<AnswerQuestionLogDetail> {

	/**
	 * 通过主表id删除子表数据
	 *
	 * @param mainId 主表id
	 * @return boolean
	 */
	public boolean deleteByMainId(@Param("mainId") String mainId);

  /**
   * 通过主表id查询子表数据
   *
   * @param mainId 主表id
   * @return List<AnswerQuestionLogDetail>
   */
	public List<AnswerQuestionLogDetail> selectByMainId(@Param("mainId") String mainId);

    List<PersonalityShortcomingsTendencies> getAvgSocereByPeople(@Param("username") String username);
}
