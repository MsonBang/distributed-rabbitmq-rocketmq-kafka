package com.miaoshao.mq;

import com.miaoshao.mq.rabbitmq.springboot.RabbitConfig;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.security.RunAs;

/**
 * @author miaoxuebing
 * @Description: TODO
 * @date 2021/8/23 下午3:07
 */
@SpringBootTest
public class ConfigTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Test
    public void send(){
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME,"boot.test","测试spring自动创建发送4");
    }

}
