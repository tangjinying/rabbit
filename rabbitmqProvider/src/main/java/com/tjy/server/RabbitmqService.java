package com.tjy.server;

import com.tjy.RabbitMqConfig;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class RabbitmqService {

    //日期格式化
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Resource
    private RabbitTemplate rabbitTemplate;

    public String sendMsg(String msg){
        try {
            String msgId = UUID.randomUUID().toString().replace("-", "").substring(0, 32);
            String sendTime = sdf.format(new Date());
            Map<String,Object> map = new HashMap<>();
            map.put("msgId",msgId);
            map.put("sendTime",sendTime);
            map.put("msg",msg);
            rabbitTemplate.convertAndSend(RabbitMqConfig.RABBITMQ_DEMO_EXCHANGE,RabbitMqConfig.RABBITMQ_DEMO_DIRECT_ROUTING,map);
            return "ok";
        } catch (AmqpException e) {
            e.printStackTrace();
            return "err";
        }
    }

}
