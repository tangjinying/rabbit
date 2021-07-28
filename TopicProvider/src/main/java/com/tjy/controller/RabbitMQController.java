package com.tjy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.tjy.server.TopicService;

import javax.annotation.Resource;

@RestController
@RequestMapping("/mall/rabbitmq")
public class RabbitMQController {
    @Resource
    private TopicService rabbitMQService;

    @GetMapping("/aaa")
    public String aaa(){
        return "nihao";
    }
    
    /**
     * 通配符交换机发送消息
     *
     * @author java技术爱好者
     */
    @GetMapping("/topicSend")
    public String topicSend(@RequestParam(name = "msg") String msg, @RequestParam(name = "routingKey") String routingKey) throws Exception {
        return rabbitMQService.sendMsgByFanoutExchange(msg, routingKey);
    }
}