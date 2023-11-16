package org.jeecg.modules.demo.test.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.minio.messages.Item;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.test.entity.AchievementStandard;
import org.jeecg.modules.demo.test.entity.AnswerQuestionLog;
import org.jeecg.modules.demo.test.entity.ExaminationPaperAchievementStandard;
import org.jeecg.modules.demo.test.mapper.AchievementStandardMapper;
import org.jeecg.modules.demo.test.service.IAchievementStandardService;
import org.jeecg.modules.demo.test.service.IExaminationPaperAchievementStandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: achievement_standard
 * @Author: jeecg-boot
 * @Date:   2023-11-09
 * @Version: V1.0
 */
@Service
public class AchievementStandardServiceImpl extends ServiceImpl<AchievementStandardMapper, AchievementStandard> implements IAchievementStandardService {


    @Autowired
    private IExaminationPaperAchievementStandardService examinationPaperAchievementStandardService;


    /**
     * @description: 根据相应的做题结果来获取对应的成绩详情
     * @param: achievementStandard
     * @return: org.jeecg.common.api.vo.Result<?>
     * @author lhr
     * @date: 2023/11/10 10:15
     */
    @Override
    public Result<?> getAchievementStand(AnswerQuestionLog answerQuestionLog) {
        // 获取试卷对应的评判标准中间表
        List<ExaminationPaperAchievementStandard> list = examinationPaperAchievementStandardService.list(
                new LambdaQueryWrapper<ExaminationPaperAchievementStandard>()
                        .eq(ExaminationPaperAchievementStandard::getPaperCode, answerQuestionLog.getPaperCode()));
        if (CollUtil.isEmpty(list)){
            return Result.error("请联系管理员绑定试卷和成绩生成标准1");
        }
        // 根据中间表查询出所有评判标准
        List<AchievementStandard> standardList = new ArrayList<>();
        list.forEach(item -> {
            AchievementStandard byId = this.getById(item.getAchievementId());
            standardList.add(byId);
        });
         if (CollUtil.isEmpty(standardList)){
            return Result.error("请联系管理员绑定试卷和成绩生成标准！");
        }
        BigDecimal score = new BigDecimal(answerQuestionLog.getTotalScore());
        // 将分数进行比较获取对应的评判标准
        List<AchievementStandard> collect = standardList.stream().filter(item -> (item.getHigh().compareTo(score) >= 0) && (item.getLow().compareTo(score) <= 0)).collect(Collectors.toList());
         if (CollUtil.isEmpty(collect)){
            return Result.error("请联系管理员绑定您分数段内的成绩生成标准");
        }
        // 将筛选出来的评判标准的第一个返回给前端
        return Result.ok(collect.get(0));
    }
}
