package org.jeecg.modules.demo.commant.service;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.commant.entity.CommantInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: commant_info
 * @Author: jeecg-boot
 * @Date:   2023-11-14
 * @Version: V1.0
 */
public interface ICommantInfoService extends IService<CommantInfo> {

    Result<?> getCommantByPageId(String pageId);
}
