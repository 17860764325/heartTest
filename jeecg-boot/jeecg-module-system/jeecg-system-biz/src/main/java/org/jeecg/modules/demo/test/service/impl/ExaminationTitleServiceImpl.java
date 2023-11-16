package org.jeecg.modules.demo.test.service.impl;

import org.jeecg.modules.demo.test.entity.ExaminationTitle;
import org.jeecg.modules.demo.test.mapper.ExaminationTitleMapper;
import org.jeecg.modules.demo.test.service.IExaminationTitleService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: examination_title
 * @Author: jeecg-boot
 * @Date:   2023-11-07
 * @Version: V1.0
 */
@Service
public class ExaminationTitleServiceImpl extends ServiceImpl<ExaminationTitleMapper, ExaminationTitle> implements IExaminationTitleService {
	
	@Autowired
	private ExaminationTitleMapper examinationTitleMapper;
	
	@Override
	public List<ExaminationTitle> selectByMainId(String mainId) {
		return examinationTitleMapper.selectByMainId(mainId);
	}
}
