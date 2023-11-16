package org.jeecg.modules.demo.test.service;

import org.jeecg.modules.demo.test.entity.ExaminationPaperAchievementStandard;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: examination_paper_achievement_standard
 * @Author: jeecg-boot
 * @Date:   2023-11-09
 * @Version: V1.0
 */
public interface IExaminationPaperAchievementStandardService extends IService<ExaminationPaperAchievementStandard> {

	/**
	 * 通过主表id查询子表数据
	 *
	 * @param mainId 主表id
	 * @return List<ExaminationPaperAchievementStandard>
	 */
	public List<ExaminationPaperAchievementStandard> selectByMainId(String mainId);
}
