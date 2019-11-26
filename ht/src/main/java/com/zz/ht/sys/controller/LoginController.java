package com.zz.ht.sys.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.zz.ht.service.LogService;
import com.zz.ht.sys.entity.User;
import com.zz.ht.sys.service.IUserService;
import com.zz.ht.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.ConnectException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("sys")
@Api(tags = "登录接口")
public class LoginController {

    private static final Logger log= LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private IUserService userService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private RedisTemplate redisTemplate;

    @Resource
    private LogService logService;
    /**
     * 验证登录
     * @param username
     * @param password
     * @param rememberMe
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/login")
    @ApiOperation("验证登录")
    public ResponseEntity login(
              String username,
              String password,
            boolean rememberMe, HttpServletRequest request) throws Exception {
        /*if (!CaptchaUtil.verify(verifyCode, request)) {
            throw new FebsException("验证码错误！");
        }*/
        //password = MD5Util.encrypt(username.toLowerCase(), password);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
        Map<String,Object> logMap=new HashMap<>();
        logMap.put("login_name",username);
        logMap.put("login_time",LocalDateTime.now());
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
            logMap.put("successed",true);
            logMap.put("loginMessage","登录成功");
            /*rabbitTemplate.setExchange("logUserDirectExchange");
            rabbitTemplate.setRoutingKey("logUserRoutingKey");*/
            return ResponseEntity.ok().body(token);
        } catch (UnknownAccountException | IncorrectCredentialsException | LockedAccountException e) {
            logMap.put("successed",false);
            logMap.put("loginMessage","用户不存在|密码错误");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (AuthenticationException e) {
            logMap.put("successed",false);
            logMap.put("loginMessage","权限认证失败");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("认证失败！");
        }catch (Exception e){
            logMap.put("successed",false);
            logMap.put("loginMessage","系统异常"+e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("系统异常！"+e.getMessage());
        } finally {
            sendLogMessage(logMap);
        }

    }
    @PostMapping("register")
    @ApiOperation("注册")
    public ResponseEntity add(@RequestBody User user){
        user.setCreateTime(LocalDateTime.now());
        boolean result=userService.save(user);
        return result? ResponseEntity.ok().build(): ResponseEntity.status(HttpStatus.BAD_REQUEST).body("注册失败！");
    }

    @GetMapping("loginLogs")
    @ApiOperation("分页查询 登录日志")
    public Result loginLogs(Integer currpage,Integer pagesize){
        return logService.loginLogs(currpage,pagesize);
    }

    public void sendLogMessage(Map map){
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setExchange("loginLogDirectExchange");
        rabbitTemplate.setRoutingKey("loginLogRoutingKey");
        try {
            Message message= MessageBuilder.withBody(objectMapper.writeValueAsBytes(map)).setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();
            message.getMessageProperties().setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, MessageProperties.CONTENT_TYPE_JSON);
            rabbitTemplate.convertAndSend(message);
        }catch (Exception e){
            log.error("消息发送失败");
            e.printStackTrace();
        }

    }
}
