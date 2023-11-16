package org.jeecg.modules.demo.test.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.jeecg.common.util.YouBianCodeUtil;
import org.jeecg.modules.demo.test.entity.ExaminationAll;
import org.jeecg.modules.demo.test.entity.ExaminationPaperAchievementStandard;
import org.jeecg.modules.demo.test.entity.ExaminationTitle;
import org.jeecg.modules.demo.test.mapper.ExaminationPaperAchievementStandardMapper;
import org.jeecg.modules.demo.test.mapper.ExaminationTitleMapper;
import org.jeecg.modules.demo.test.mapper.ExaminationAllMapper;
import org.jeecg.modules.demo.test.service.IExaminationAllService;
import org.jeecg.modules.demo.test.service.IExaminationPaperAchievementStandardService;
import org.jeecg.modules.system.rule.Code;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 试卷表
 * @Author: jeecg-boot
 * @Date: 2023-11-07
 * @Version: V1.0
 */
@Service
public class ExaminationAllServiceImpl extends ServiceImpl<ExaminationAllMapper, ExaminationAll> implements IExaminationAllService {

    @Autowired
    private ExaminationAllMapper examinationAllMapper;
    @Autowired
    private ExaminationTitleMapper examinationTitleMapper;
    @Autowired
    private ExaminationPaperAchievementStandardMapper examinationPaperAchievementStandardMapper;

    private final String code = "A01";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveMain(ExaminationAll examinationAll, List<ExaminationTitle> examinationTitleList,List<ExaminationPaperAchievementStandard> examinationPaperAchievementStandardList) {
        // 查询试卷编码，如果没有那就从 A01 开始
        List<ExaminationAll> list = this.list(new LambdaQueryWrapper<ExaminationAll>().orderByDesc(ExaminationAll::getExaminationCode));
        if (CollUtil.isEmpty(list)) {
            examinationAll.setExaminationCode(code);
        } else {
            examinationAll.setExaminationCode(YouBianCodeUtil.getNextYouBianCode(list.get(0).getExaminationCode()));
        }
        examinationAllMapper.insert(examinationAll);
        if (examinationTitleList != null && examinationTitleList.size() > 0) {
            for (ExaminationTitle entity : examinationTitleList) {
                //外键设置
                entity.setExaminationCode(examinationAll.getExaminationCode());
                examinationTitleMapper.insert(entity);
            }
        }
        if (examinationPaperAchievementStandardList != null && examinationPaperAchievementStandardList.size() > 0) {
            for (ExaminationPaperAchievementStandard entity : examinationPaperAchievementStandardList) {
                //外键设置
                entity.setPaperCode(examinationAll.getExaminationCode());
                examinationPaperAchievementStandardMapper.insert(entity);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMain(ExaminationAll examinationAll, List<ExaminationTitle> examinationTitleList,List<ExaminationPaperAchievementStandard> examinationPaperAchievementStandardList) {
        examinationAllMapper.updateById(examinationAll);

        //1.先删除子表数据
        examinationTitleMapper.deleteByMainId(examinationAll.getExaminationCode());

        //2.子表数据重新插入
        if (examinationTitleList != null && examinationTitleList.size() > 0) {
            for (ExaminationTitle entity : examinationTitleList) {
                //外键设置
                entity.setExaminationCode(examinationAll.getExaminationCode());
                examinationTitleMapper.insert(entity);
            }
        }

        //1.先删除子表数据
        examinationPaperAchievementStandardMapper.deleteByMainId(examinationAll.getExaminationCode());

        //2.子表数据重新插入
        if (examinationPaperAchievementStandardList != null && examinationPaperAchievementStandardList.size() > 0) {
            for (ExaminationPaperAchievementStandard entity : examinationPaperAchievementStandardList) {
                //外键设置
                entity.setPaperCode(examinationAll.getExaminationCode());
                examinationPaperAchievementStandardMapper.insert(entity);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delMain(String id) {
        examinationTitleMapper.deleteByMainId(id);
        examinationAllMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delBatchMain(Collection<? extends Serializable> idList) {
        for (Serializable id : idList) {
            examinationTitleMapper.deleteByMainId(id.toString());
            examinationAllMapper.deleteById(id);
        }
    }

}
