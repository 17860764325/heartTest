package org.jeecg.modules.demo.chat.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.chat.entity.ChatLog;
import org.jeecg.modules.demo.chat.mapper.ChatLogMapper;
import org.jeecg.modules.demo.chat.service.IChatLogService;
import org.jeecg.modules.demo.chat.vo.ChatLogVo;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: chat_log
 * @Author: jeecg-boot
 * @Date:   2023-11-10
 * @Version: V1.0
 */
@Service
public class ChatLogServiceImpl extends ServiceImpl<ChatLogMapper, ChatLog> implements IChatLogService {

     @Autowired
    private ISysUserService sysUserService;

    /**
     * @description: 获取聊天记录
     * @param: chatLog
     * @return: org.jeecg.common.api.vo.Result<java.util.List<org.jeecg.modules.demo.chat.entity.ChatLog>>
     * @author lhr
     * @date: 2023/11/10 21:23
     */
    @Override
    public Result<List<ChatLog>> getChatLog(ChatLogVo chatLog) {
        // 查询两个人的所有聊天记录，并且正序排序
        // 查询发送人的
        List<ChatLog> sendList = this.list(new LambdaQueryWrapper<ChatLog>().eq(ChatLog::getSendPerson, chatLog.getSendPerson()).eq(ChatLog::getRecivePerson, chatLog.getRecivePerson()));
        // 查询接收人的
        List<ChatLog> reciveList = this.list(new LambdaQueryWrapper<ChatLog>().eq(ChatLog::getSendPerson, chatLog.getRecivePerson()).eq(ChatLog::getRecivePerson, chatLog.getSendPerson()));
        // 所有信息
        List<ChatLog> listAll = new ArrayList<>();
        listAll.addAll(sendList);
        listAll.addAll(reciveList);
        if (CollUtil.isEmpty(listAll)){
        return Result.OK(reciveList);
        }
        // 赋值真实名字
        for (ChatLog chatLog1 : listAll) {
            SysUser send = sysUserService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, chatLog1.getSendPerson()));
            SysUser recevie = sysUserService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, chatLog1.getRecivePerson()));
            chatLog1.setSendRealName(send.getRealname());
            chatLog1.setReceiveRealName(recevie.getRealname());
        }
        // 将所有信息按照时间正序排序
        List<ChatLog> collect = listAll.stream().sorted(Comparator.comparing(ChatLog::getCreateTime)).collect(Collectors.toList());
        return Result.OK(collect);
    }
}
