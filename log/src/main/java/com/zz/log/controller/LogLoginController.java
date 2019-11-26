package com.zz.log.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zz.log.entity.LogLogin;
import com.zz.log.service.ILogLoginService;
import com.zz.log.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zz
 * @since 2019-11-08
 */
@RestController
@RequestMapping("/loginLog")
@Api(tags = "登录日志")
public class LogLoginController {

    @Autowired
    private ILogLoginService logLoginService;


    @GetMapping("/")
    @ApiOperation("分页查询 登录日志")
    public Result loginLogs(@RequestParam Integer currpage,@RequestParam Integer pagesize){
        Page pageBean = new Page<LogLogin>(currpage, pagesize);
        IPage<LogLogin> p=logLoginService.listLogLogin(pageBean);
        return Result.success(p);
    }
}
