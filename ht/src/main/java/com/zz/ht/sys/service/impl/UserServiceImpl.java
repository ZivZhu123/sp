package com.zz.ht.sys.service.impl;

import com.zz.ht.sys.entity.User;
import com.zz.ht.sys.mapper.UserMapper;
import com.zz.ht.sys.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author zz
 * @since 2019-11-07
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
