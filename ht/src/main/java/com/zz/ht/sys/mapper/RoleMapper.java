package com.zz.ht.sys.mapper;

import com.zz.ht.sys.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author zz
 * @since 2019-11-07
 */
@Component
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 根据用户名返回拥有的角色
     * @param username
     * @return 角色
     */
    List<Role> findUserRole(String username);

}
