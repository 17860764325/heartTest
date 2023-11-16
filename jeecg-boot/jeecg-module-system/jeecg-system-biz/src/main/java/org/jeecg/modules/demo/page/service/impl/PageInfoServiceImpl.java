package org.jeecg.modules.demo.page.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.page.entity.PageInfo;
import org.jeecg.modules.demo.page.mapper.PageInfoMapper;
import org.jeecg.modules.demo.page.service.IPageInfoService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description: page_info
 * @Author: jeecg-boot
 * @Date:   2023-11-14
 * @Version: V1.0
 */
@Service
public class PageInfoServiceImpl extends ServiceImpl<PageInfoMapper, PageInfo> implements IPageInfoService {

    @Override
    public Result<?> getAllPage(String type) {
        List<PageInfo> list = this.list(new LambdaQueryWrapper<PageInfo>().eq(PageInfo::getPageType,type));
        return Result.ok(list);
    }
}
