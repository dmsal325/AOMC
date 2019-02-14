package com.aomc.coop.utils.rabbitMQ;

import com.aomc.coop.config.RabbitMQConfig;
import com.aomc.coop.model.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RabbitMQUtil {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public Message sendRabbitMQ(Message message) {
        System.out.println("요청이 왔습니다" + message);
//        System.out.println("channelIdx는 " + channelIdx);

        Map<String, Message> map = new HashMap<>();
        map.put("msg", message);
//        map.put("channelIdx", ms);

        //큐에보냄
        if(message != null) {
            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.QUEUE_NAME, map);
            System.out.println("rabbitMQ send");
        }

        return  message;
    }

    public void receiveRabbitMQ(Message message) {
        System.out.println("rabbitMQ receive = " + message);
        //SendTo("/topic/message")
        this.simpMessagingTemplate.convertAndSend("/topic/message", message);
    }
}
