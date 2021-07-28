package com.tjy;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Configuration
public class FanoutConfig implements BeanPostProcessor {

    @Resource
    private RabbitAdmin rabbitAdmin;

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    @Bean
    public Queue fanoutExchangeQueueA(){
        return new Queue(RabbitMqFanoutConfig.RABBITMQ_DEMO_FANOUT_TOPIC_A,true,false,false);
    }

    @Bean
    public Queue fanoutExchangeQueueB(){
        return new Queue(RabbitMqFanoutConfig.RABBITMQ_DEMO_FANOUT_TOPIC_B,true,false,false);
    }

    @Bean
    public FanoutExchange exchange(){
        return new FanoutExchange(RabbitMqFanoutConfig.RABBITMQ_DEMO_FANOUT_EXCHANGE,true,false);
    }

    @Bean
    public Binding bindFanoutA(){
        return BindingBuilder.bind(fanoutExchangeQueueA()).to(exchange());
    }

    @Bean
    public Binding bindFanoutB(){
        return BindingBuilder.bind(fanoutExchangeQueueB())
                .to(exchange());
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        rabbitAdmin.declareQueue(fanoutExchangeQueueA());
        rabbitAdmin.declareQueue(fanoutExchangeQueueB());
        rabbitAdmin.declareExchange(exchange());
        return null;
    }
}
