package org.jeecg.modules.demo.test.service;

import org.jeecg.modules.demo.test.entity.ExaminationTitle;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: examination_title
 * @Author: jeecg-boot
 * @Date:   2023-11-07
 * @Version: V1.0
 */
public interface IExaminationTitleService extends IService<ExaminationTitle> {

	/**
	 * 通过主表id查询子表数据
	 *
	 * @param mainId 主表id
	 * @return List<ExaminationTitle>
	 */
	public List<ExaminationTitle> selectByMainId(String mainId);
}
