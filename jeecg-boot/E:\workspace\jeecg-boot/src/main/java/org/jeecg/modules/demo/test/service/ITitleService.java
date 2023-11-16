package org.jeecg.modules.demo.test.service;

import org.jeecg.modules.demo.test.entity.Answer;
import org.jeecg.modules.demo.test.entity.Title;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 题目表
 * @Author: jeecg-boot
 * @Date:   2023-11-08
 * @Version: V1.0
 */
public interface ITitleService extends IService<Title> {

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
