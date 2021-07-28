package com.tjy.controller;

import com.tjy.server.FanoutService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/mall/rabbitmq")
public class RabbitMQController {

    @Resource
    private FanoutService fanoutService;
    /**
     * 发布消息
     *
     * @author java技术爱好者
     */
    @GetMapping("/publish")
    public String publish(@RequestParam(name = "msg") String msg) throws Exception {
        return fanoutService.sendMsgByFanoutExchange(msg);
    }
}