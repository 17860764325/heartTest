package org.jeecg.modules.demo.commant.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.commant.entity.CommantInfo;
import org.jeecg.modules.demo.commant.mapper.CommantInfoMapper;
import org.jeecg.modules.demo.commant.service.ICommantInfoService;
import org.jeecg.modules.demo.commant.vo.CommantVo;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description: commant_info
 * @Author: jeecg-boot
 * @Date: 2023-11-14
 * @Version: V1.0
 */
@Service
public class CommantInfoServiceImpl extends ServiceImpl<CommantInfoMapper, CommantInfo> implements ICommantInfoService {


    @Autowired
    private ISysUserService sysUserService;

    /**
     * @description: 通过文章 id 查询改文章的所有评论
     * @param: pageId
     * @return: org.jeecg.common.api.vo.Result<?>
     * @author lhr
     * @date: 2023/11/14 22:03
     */
    @Override
    public Result<?> getCommantByPageId(String pageId) {
        // 获取所有评论
        List<CommantInfo> list = this.list(new LambdaQueryWrapper<CommantInfo>().eq(CommantInfo::getPageId, pageId));
        if(CollUtil.isEmpty(list)){
            return  Result.ok();
        }
        // stream 找出 父 id 为空的评论 ，循环赋值 children
        List<CommantInfo> collectNoParentId = list.stream().filter(item -> StrUtil.isEmpty(item.getParentId())).collect(Collectors.toList());
        if(CollUtil.isEmpty(collectNoParentId)){
            return  Result.ok();
        }
        List<CommantVo> commantVos = returnResult(collectNoParentId, list);
        // 返回数据
        return Result.ok(commantVos);

    }

    /**
     * @description: 最根部循环
     * @param: list
     * @return: java.util.List<org.jeecg.modules.demo.commant.vo.CommantVo>
     * @author lhr
     * @date: 2023/11/14 23:13
     */
    public List<CommantVo> returnResult(List<CommantInfo> listRoot,List<CommantInfo> list) {
        List<CommantVo> listRootResult = new ArrayList<>();
        for (CommantInfo commantInfo : listRoot) {
            CommantVo commantVo = returnResult(commantInfo, list);
            listRootResult.add(commantVo);
        }
        return listRootResult;
    }

    /**
     * @description: 获取children
     * @param: commantInfo
     * @return: java.util.List<org.jeecg.modules.demo.commant.vo.CommantVo>
     * @author lhr
     * @date: 2023/11/14 22:57
     */
    public CommantVo returnResult(CommantInfo commantInfo, List<CommantInfo> list) {
        CommantVo commandVo = createCommandVo(commantInfo);
        // children
        List<CommantInfo> collect1 = list.stream().filter(item -> StrUtil.isNotEmpty((item.getParentId()))).collect(Collectors.toList());
        List<CommantInfo> collect = collect1.stream().filter(item -> item.getParentId().equals(commantInfo.getId())).collect(Collectors.toList());
        if (CollUtil.isEmpty(collect)) {
            return commandVo;
        } else {
            List<CommantVo> listChildren = new ArrayList<>();
            for (CommantInfo info : collect) {
                listChildren.add(returnResult(info, list));
            }
            commandVo.setChildren(listChildren);
            return commandVo;
        }
    }

    private CommantVo createCommandVo(CommantInfo commantInfo) {
        CommantVo commantVo = new CommantVo();
        commantVo.setCreateTime(commantInfo.getCreateTime());
        // id
        commantVo.setId(commantInfo.getId());
        // 内容
        commantVo.setAbout(commantInfo.getAbout());
        // 文章 id
        commantVo.setPageId(commantInfo.getPageId());
        // 父 id
        commantVo.setParentId(commantInfo.getParentId());
        // 头像
        SysUser one = sysUserService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, commantInfo.getCreateBy()));
        commantVo.setAvatar(one.getAvatar());
        // 发表人
        commantVo.setCreateBy(one.getRealname());
        return commantVo;
    }
}
