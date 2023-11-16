package org.jeecg.modules.demo.test.service;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.test.entity.AchievementStandard;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.demo.test.entity.AnswerQuestionLog;

/**
 * @Description: achievement_standard
 * @Author: jeecg-boot
 * @Date:   2023-11-09
 * @Version: V1.0
 */
public interface IAchievementStandardService extends IService<AchievementStandard> {

    Result<?> getAchievementStand(AnswerQuestionLog answerQuestionLog);
}
