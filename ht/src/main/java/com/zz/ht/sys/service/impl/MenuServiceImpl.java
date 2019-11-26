package com.zz.ht.sys.service.impl;

import com.zz.ht.sys.entity.Menu;
import com.zz.ht.sys.mapper.MenuMapper;
import com.zz.ht.sys.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author zz
 * @since 2019-11-07
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {
    @Override
    public List<Menu> findUserPermissions(String username) {
        return null;
    }
}
