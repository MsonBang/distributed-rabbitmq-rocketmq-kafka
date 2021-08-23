package com.miaoshao.mq.rabbitmq.springboot;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author miaoxuebing
 * @Description: TODO
 * @date 2021/8/23 下午3:36
 */
@Component
public class RabbitmqListener {

    //定义方法进行信息的监听
    /*@RabbitListener(queues = "boot_queue")
    public void ListennerQueue(Message message){
        System.out.println("监听到到的消息为："+message.getBody());
    }*/
}
