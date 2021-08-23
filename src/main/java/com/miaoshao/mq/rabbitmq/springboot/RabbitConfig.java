package com.miaoshao.mq.rabbitmq.springboot;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author miaoxuebing
 * @Description: TODO
 * @date 2021/8/23 下午2:43
 */
@Configuration
public class RabbitConfig {
    //定义交换机名称
    public static final String EXCHANGE_NAME = "boot_topic_exchange";
    //定义队列名称
    public static final String QUEUE_NAME = "boot_queue";

    //申明交换机
    @Bean("bootExchange")
    public Exchange bootExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_NAME).durable(true).build();
    }

    //申明队列
    @Bean("bootQueue")
    public Queue bootQueue() {
        return QueueBuilder.durable(QUEUE_NAME).build();
    }

    //队列与交换机绑定
    @Bean
    public Binding bindQueueExchangge(@Qualifier("bootQueue") Queue queue,
                                      @Qualifier("bootExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("boot.#").noargs();
    }
}
