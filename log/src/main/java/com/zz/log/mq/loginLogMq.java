package com.zz.log.mq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.primitives.Booleans;
import com.zz.log.entity.LogLogin;
import com.zz.log.service.ILogLoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component
public class loginLogMq {
    private static final Logger log= LoggerFactory.getLogger(loginLogMq.class);

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ILogLoginService logLoginService;
    /**
     * 监听消费用户日志
     * @param message
     */
    @RabbitListener(queues = "loginLogQueue",containerFactory = "singleListenerContainer")
    public void consumeUserLogQueue(@Payload byte[] message){
        try {
            Map<String,Object> s=objectMapper.readValue(message, Map.class);
            if(null!=s){
                log.info("监听消费用户日志 监听到消息： {} ",s);
                LogLogin logLogin = new LogLogin();
                logLogin.setGmtCreate(LocalDateTime.now());
                logLogin.setUserName(String.valueOf(s.get("login_name")));
                logLogin.setLoginTime(LocalDateTime.parse(String.valueOf(s.get("login_time"))));
                logLogin.setLoginMessage(String.valueOf(s.get("loginMessage")));
                logLogin.setSuccessed((Boolean)(s.get("successed")));
                logLoginService.save(logLogin);
            }

            //TODO：记录日志入数据表
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
