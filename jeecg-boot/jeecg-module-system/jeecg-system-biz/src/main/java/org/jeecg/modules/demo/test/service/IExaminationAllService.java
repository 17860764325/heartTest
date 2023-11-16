package org.jeecg.modules.demo.test.service;

import org.jeecg.modules.demo.test.entity.ExaminationPaperAchievementStandard;
import org.jeecg.modules.demo.test.entity.ExaminationTitle;
import org.jeecg.modules.demo.test.entity.ExaminationAll;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 试卷表
 * @Author: jeecg-boot
 * @Date:   2023-11-07
 * @Version: V1.0
 */
public interface IExaminationAllService extends IService<ExaminationAll> {

	/**
	 * 添加一对多
	 *
	 * @param examinationAll
	 * @param examinationTitleList
	 */
	public void saveMain(ExaminationAll examinationAll,List<ExaminationTitle> examinationTitleList,List<ExaminationPaperAchievementStandard> examinationPaperAchievementStandardList) ;
	
	/**
	 * 修改一对多
	 *
	 * @param examinationAll
	 * @param examinationTitleList
	 */
	public void updateMain(ExaminationAll examinationAll,List<ExaminationTitle> examinationTitleList,List<ExaminationPaperAchievementStandard> examinationPaperAchievementStandardList);
	
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
	
}
