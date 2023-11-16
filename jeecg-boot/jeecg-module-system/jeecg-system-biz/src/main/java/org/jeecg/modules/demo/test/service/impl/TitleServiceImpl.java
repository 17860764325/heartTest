package org.jeecg.modules.demo.test.service.impl;

import org.jeecg.modules.demo.test.entity.Title;
import org.jeecg.modules.demo.test.entity.Answer;
import org.jeecg.modules.demo.test.mapper.AnswerMapper;
import org.jeecg.modules.demo.test.mapper.TitleMapper;
import org.jeecg.modules.demo.test.service.ITitleService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 题目表
 * @Author: jeecg-boot
 * @Date:   2023-11-08
 * @Version: V1.0
 */
@Service
public class TitleServiceImpl extends ServiceImpl<TitleMapper, Title> implements ITitleService {

	@Autowired
	private TitleMapper titleMapper;
	@Autowired
	private AnswerMapper answerMapper;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delMain(String id) {
		answerMapper.deleteByMainId(id);
		titleMapper.deleteById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			answerMapper.deleteByMainId(id.toString());
			titleMapper.deleteById(id);
		}
	}
	
}
