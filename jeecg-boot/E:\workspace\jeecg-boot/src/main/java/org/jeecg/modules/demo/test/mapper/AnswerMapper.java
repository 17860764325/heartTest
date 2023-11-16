package org.jeecg.modules.demo.test.mapper;

import java.util.List;
import org.jeecg.modules.demo.test.entity.Answer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 答案表
 * @Author: jeecg-boot
 * @Date:   2023-11-08
 * @Version: V1.0
 */
public interface AnswerMapper extends BaseMapper<Answer> {

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
    * @return List<Answer>
    */
	public List<Answer> selectByMainId(@Param("mainId") String mainId);

}
