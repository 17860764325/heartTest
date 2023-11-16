package org.jeecg.modules.demo.test.service.impl;

import org.jeecg.modules.demo.test.entity.ExaminationPaperAchievementStandard;
import org.jeecg.modules.demo.test.mapper.ExaminationPaperAchievementStandardMapper;
import org.jeecg.modules.demo.test.service.IExaminationPaperAchievementStandardService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: examination_paper_achievement_standard
 * @Author: jeecg-boot
 * @Date:   2023-11-09
 * @Version: V1.0
 */
@Service
public class ExaminationPaperAchievementStandardServiceImpl extends ServiceImpl<ExaminationPaperAchievementStandardMapper, ExaminationPaperAchievementStandard> implements IExaminationPaperAchievementStandardService {
	
	@Autowired
	private ExaminationPaperAchievementStandardMapper examinationPaperAchievementStandardMapper;
	
	@Override
	public List<ExaminationPaperAchievementStandard> selectByMainId(String mainId) {
		return examinationPaperAchievementStandardMapper.selectByMainId(mainId);
	}
}
