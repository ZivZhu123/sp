package com.zz.log.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zz.log.entity.LogLogin;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zz
 * @since 2019-11-08
 */
public interface ILogLoginService extends IService<LogLogin> {

    IPage<LogLogin> listLogLogin(IPage<LogLogin> page);
}
