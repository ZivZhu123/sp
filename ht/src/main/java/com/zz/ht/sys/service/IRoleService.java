package com.zz.ht.sys.service;

import com.zz.ht.sys.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author zz
 * @since 2019-11-07
 */
public interface IRoleService extends IService<Role> {

    /**
     * 根据用户名返回拥有的角色
     * @param username
     * @return 角色
     */
    List<Role> findUserRole(String username);
}
