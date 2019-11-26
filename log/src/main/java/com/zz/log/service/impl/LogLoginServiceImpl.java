package com.zz.log.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zz.log.entity.LogLogin;
import com.zz.log.mapper.LogLoginMapper;
import com.zz.log.service.ILogLoginService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zz
 * @since 2019-11-08
 */
@Service
public class LogLoginServiceImpl extends ServiceImpl<LogLoginMapper, LogLogin> implements ILogLoginService {
    @Resource
    private LogLoginMapper logLoginMapper;
    @Override
    public IPage<LogLogin> listLogLogin(IPage<LogLogin> page) {
        QueryWrapper<LogLogin> queryWrapper = new QueryWrapper<>();
        return logLoginMapper.selectPage(page,queryWrapper);
    }
}
