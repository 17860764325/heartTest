package org.jeecg.modules.demo.chat.service;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.chat.entity.ChatLog;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.demo.chat.vo.ChatLogVo;

import java.util.List;

/**
 * @Description: chat_log
 * @Author: jeecg-boot
 * @Date:   2023-11-10
 * @Version: V1.0
 */
public interface IChatLogService extends IService<ChatLog> {

    Result<List<ChatLog>> getChatLog(ChatLogVo chatLog);
}
