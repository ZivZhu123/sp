package com.zz.ht.service;

import com.zz.ht.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "log",fallback = LogServiceHystrix.class)
public interface LogService {
    @RequestMapping(value = "/loginLog/",method = RequestMethod.GET)
    public Result loginLogs(@RequestParam Integer currpage,@RequestParam Integer pagesize);
}
