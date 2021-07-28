package com.tjy.demo;

import com.tjy.RabbitMqConfig;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queuesToDeclare = @Queue(RabbitMqConfig.RABBITMQ_DEMO_TOPIC))
public class RabbitmqDemoConsumer {

    @RabbitHandler
    public void process(Map map){
        System.out.println("消费者从rabbitmq服务端消费消息"+map.toString());
    }

}
