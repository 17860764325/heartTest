package org.jeecg.modules.demo.test.mapper;

import java.util.List;
import org.jeecg.modules.demo.test.entity.ExaminationPaperAchievementStandard;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: examination_paper_achievement_standard
 * @Author: jeecg-boot
 * @Date:   2023-11-09
 * @Version: V1.0
 */
public interface ExaminationPaperAchievementStandardMapper extends BaseMapper<ExaminationPaperAchievementStandard> {

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
   * @return List<ExaminationPaperAchievementStandard>
   */
	public List<ExaminationPaperAchievementStandard> selectByMainId(@Param("mainId") String mainId);
}
