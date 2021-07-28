package com.tjy;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queuesToDeclare = @Queue(RabbitMqFanoutConfig.RABBITMQ_DEMO_FANOUT_TOPIC_A))
public class FanoutConsumerA {

    @RabbitHandler
    public void process(Map<String, Object> map){
        System.out.println("队列A消费消息"+map.toString());
    }

}
