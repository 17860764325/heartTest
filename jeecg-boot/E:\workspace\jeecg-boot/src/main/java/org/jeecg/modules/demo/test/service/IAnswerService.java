package org.jeecg.modules.demo.test.service;

import org.jeecg.modules.demo.test.entity.Answer;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 答案表
 * @Author: jeecg-boot
 * @Date:   2023-11-08
 * @Version: V1.0
 */
public interface IAnswerService extends IService<Answer> {

  /**
   * 通过主表id查询子表数据
   *
   * @param mainId
   * @return List<Answer>
   */
	public List<Answer> selectByMainId(String mainId);
}
