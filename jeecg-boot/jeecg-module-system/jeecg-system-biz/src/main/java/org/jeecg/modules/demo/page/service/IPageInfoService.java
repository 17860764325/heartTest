package org.jeecg.modules.demo.page.service;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.page.entity.PageInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: page_info
 * @Author: jeecg-boot
 * @Date:   2023-11-14
 * @Version: V1.0
 */
public interface IPageInfoService extends IService<PageInfo> {

    Result<?> getAllPage(String type);
}
