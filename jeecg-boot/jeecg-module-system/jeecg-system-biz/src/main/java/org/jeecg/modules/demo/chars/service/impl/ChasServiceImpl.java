package org.jeecg.modules.demo.chars.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.demo.chars.service.ICharsService;
import org.jeecg.modules.demo.chars.vo.ChatLogVo;
import org.jeecg.modules.demo.chars.vo.PersonalityShortcomingsTendencies;
import org.jeecg.modules.demo.chat.entity.ChatLog;
import org.jeecg.modules.demo.chat.mapper.ChatLogMapper;
import org.jeecg.modules.demo.chat.service.IChatLogService;
import org.jeecg.modules.demo.commant.entity.CommantInfo;
import org.jeecg.modules.demo.commant.service.ICommantInfoService;
import org.jeecg.modules.demo.test.entity.AnswerQuestionLog;
import org.jeecg.modules.demo.test.service.IAnswerQuestionLogService;
import org.jeecg.modules.system.entity.SysDepart;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.entity.SysUserDepart;
import org.jeecg.modules.system.service.ISysDepartService;
import org.jeecg.modules.system.service.ISysUserDepartService;
import org.jeecg.modules.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description: chat_log
 * @Author: jeecg-boot
 * @Date: 2023-11-10
 * @Version: V1.0
 */
@Service
public class ChasServiceImpl implements ICharsService {

    @Autowired
    private IAnswerQuestionLogService answerQuestionLogService;
    @Autowired
    private ICommantInfoService commantInfoService;
    @Autowired
    private IChatLogService chatLogService;
    @Autowired
    private ISysUserDepartService sysUserDepartService;
    @Autowired
    private ISysDepartService sysDepartService;


    /**
     * @description: 获取历史成绩
     * @param:
     * @return: org.jeecg.common.api.vo.Result<?>
     * @author lhr
     * @date: 2023/11/16 20:08
     */
    @Override
    public String getHistory(String pageType) {
        // 获取当前登录人的信息
        LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        // 根据当前登录人信息查询他的历史考试成绩组织数据返回
        List<AnswerQuestionLog> list = answerQuestionLogService.list(new LambdaQueryWrapper<AnswerQuestionLog>()
                .eq(AnswerQuestionLog::getCreateBy, user.getUsername())
                .eq(AnswerQuestionLog::getPaperType, pageType)
                .orderByAsc(AnswerQuestionLog::getCreateTime));
        for (AnswerQuestionLog answerQuestionLog : list) {
            answerQuestionLog.setTime(DateUtil.format(answerQuestionLog.getCreateTime(), "yyyy年MM月dd日 HH时mm分ss秒"));
        }
        String result = JSONUtil.parse(list).toString();
        System.out.println(result);
        return result;
    }

    /**
     * @description: 性格缺点倾向
     * @param:
     * @return: java.lang.String
     * @author lhr
     * @date: 2023/11/16 21:10
     */
    @Override
    public String personalityShortcomingsTendencies() {
        // 看各种类型的试卷的平均分
        LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        List<PersonalityShortcomingsTendencies> list = answerQuestionLogService.getAvgSocereByPeople(user.getUsername());
        return JSONUtil.parse(list).toString();
    }

    /**
     * @description: 活跃统计
     * @param:
     * @return: java.lang.String
     * @author lhr
     * @date: 2023/11/16 21:35
     */
    @Override
    public String activeStatistics() {
        List<PersonalityShortcomingsTendencies> result = new ArrayList<>();
        LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        List<AnswerQuestionLog> list = answerQuestionLogService.list(new LambdaQueryWrapper<AnswerQuestionLog>().eq(AnswerQuestionLog::getCreateBy, user.getUsername()));
        PersonalityShortcomingsTendencies personalityShortcomingsTendencies = new PersonalityShortcomingsTendencies();
        personalityShortcomingsTendencies.setPaperType("测试");
        personalityShortcomingsTendencies.setScore(String.valueOf(list.size()));
        result.add(personalityShortcomingsTendencies);
        List<CommantInfo> list1 = commantInfoService.list(new LambdaQueryWrapper<CommantInfo>().eq(CommantInfo::getCreateBy, user.getUsername()));
        PersonalityShortcomingsTendencies personalityShortcomingsTendencies1 = new PersonalityShortcomingsTendencies();
        personalityShortcomingsTendencies1.setPaperType("文章评论");
        personalityShortcomingsTendencies1.setScore(String.valueOf(list1.size()));
        result.add(personalityShortcomingsTendencies1);
        List<ChatLog> list2 = chatLogService.list(new LambdaQueryWrapper<ChatLog>().eq(ChatLog::getSendPerson, user.getUsername()));
        Map<String, List<ChatLog>> collect = list2.stream().collect(Collectors.groupingBy(ChatLog::getRecivePerson));
        PersonalityShortcomingsTendencies personalityShortcomingsTendencies2 = new PersonalityShortcomingsTendencies();
        personalityShortcomingsTendencies2.setPaperType("咨询");
        personalityShortcomingsTendencies2.setScore(String.valueOf(collect.size()));
        result.add(personalityShortcomingsTendencies2);
        return JSONUtil.parse(result).toString();
    }

    /**
     * @description: 各学生部门，专业，班级人员的数据
     * @param: type
     * @return: java.lang.String
     * @author lhr
     * @date: 2023/11/17 00:13
     */
    @Override
    public String getPeopleFromAll(String type) {
        // 查询所有专业的部门
        List<SysDepart> zhuanyeList = sysDepartService.list(new LambdaQueryWrapper<SysDepart>().like(SysDepart::getDepartName, "专业"));
        List<String> collectForChildren = zhuanyeList.stream().map(SysDepart::getId).collect(Collectors.toList());
        // 根据专业查询所有班级部门
        List<SysDepart> childrenList = sysDepartService.list(new LambdaQueryWrapper<SysDepart>().in(SysDepart::getParentId, collectForChildren));
        zhuanyeList.addAll(childrenList);
        List<String> collect = zhuanyeList.stream().map(SysDepart::getId).collect(Collectors.toList());
        List<SysUserDepart> classList = sysUserDepartService.list(new LambdaQueryWrapper<SysUserDepart>().in(SysUserDepart::getDepId, collect));
        // 根据班级查询人员

        // 将所有人员绑定到专业上


    }
}
