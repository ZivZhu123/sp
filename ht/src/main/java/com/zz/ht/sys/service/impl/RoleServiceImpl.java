package com.zz.ht.sys.service.impl;

import com.zz.ht.sys.entity.Role;
import com.zz.ht.sys.mapper.RoleMapper;
import com.zz.ht.sys.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author zz
 * @since 2019-11-07
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    @Override
    public List<Role> findUserRole(String username) {
        return this.baseMapper.findUserRole(username);
    }

}
