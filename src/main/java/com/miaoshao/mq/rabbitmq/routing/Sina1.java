package com.miaoshao.mq.rabbitmq.routing;

import com.miaoshao.mq.rabbitmq.rabbitUtils.RabbitConstant;
import com.miaoshao.mq.rabbitmq.rabbitUtils.RabbitUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author miaoxuebing
 * @Description: TODO[新浪接收消息，消费者]
 * @date 2021/8/22 下午12:34
 */
public class Sina1 {

    public static void main(String[] args) throws IOException {

        Connection connection = RabbitUtils.getConnection();

        final Channel channel = connection.createChannel();

        //申明队列信息
        channel.queueDeclare(RabbitConstant.QUEUE_SINA, false, false, false, null);

        //指定队列与交换机的关系和routing-key之间的关系
        channel.queueBind(RabbitConstant.QUEUE_SINA, RabbitConstant.EXCHANGE_WEATHER_ROUTING, "ccc");
        channel.queueBind(RabbitConstant.QUEUE_SINA, RabbitConstant.EXCHANGE_WEATHER_ROUTING, "ddd");

        //消费一条再去取一条
        channel.basicQos(1);

        //获取接收到消息
        channel.basicConsume(RabbitConstant.QUEUE_SINA, false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("新浪微博收到天气信息：" + new String(body));
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });
    }
}
