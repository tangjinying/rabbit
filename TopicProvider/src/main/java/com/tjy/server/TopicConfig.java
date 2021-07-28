package com.tjy.server;

import com.tjy.RabbitMqTopicConfig;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
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
public class TopicConfig implements BeanPostProcessor {

    @Resource
    private RabbitAdmin rabbitAdmin;

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    @Bean
    public Queue topicExchangeQueueA(){
        return new Queue(RabbitMqTopicConfig.TOPIC_EXCHANGE_QUEUE_A,true,false,false);
    }

    @Bean
    public Queue topicExchangeQueueB(){
        return new Queue(RabbitMqTopicConfig.TOPIC_EXCHANGE_QUEUE_B,true,false,false);
    }

    @Bean
    public Queue topicExchangeQueueC(){
        return new Queue(RabbitMqTopicConfig.TOPIC_EXCHANGE_QUEUE_C,true,false,false);
    }

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(RabbitMqTopicConfig.TOPIC_EXCHANGE_DEMO_NAME,true,false);
    }

    @Bean
    public Binding bindTopicA(){
        return BindingBuilder.bind(topicExchangeQueueA()).to(topicExchange()).with("a.*");
    }

    @Bean
    public Binding bindTopicB(){
        return BindingBuilder.bind(topicExchangeQueueB()).to(topicExchange()).with("a.*");
    }

    @Bean
    public Binding bindTopicC(){
        return BindingBuilder.bind(topicExchangeQueueC()).to(topicExchange()).with("rabbit.#");
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        rabbitAdmin.declareExchange(topicExchange());
        rabbitAdmin.declareQueue(topicExchangeQueueA());
        rabbitAdmin.declareQueue(topicExchangeQueueB());
        rabbitAdmin.declareQueue(topicExchangeQueueC());
        return null;
    }
}
