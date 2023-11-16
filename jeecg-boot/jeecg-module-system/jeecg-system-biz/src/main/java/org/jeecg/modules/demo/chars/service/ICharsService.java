package org.jeecg.modules.demo.chars.service;

import org.jeecg.common.api.vo.Result;

/**
 * @Description: chat_log
 * @Author: jeecg-boot
 * @Date:   2023-11-10
 * @Version: V1.0
 */
public interface ICharsService {


    String getHistory(String pageType);

    String personalityShortcomingsTendencies();

    String activeStatistics();

    String getPeopleFromAll(String type);
}
