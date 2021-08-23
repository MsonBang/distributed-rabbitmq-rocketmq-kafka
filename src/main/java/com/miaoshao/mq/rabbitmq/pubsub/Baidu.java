package com.miaoshao.mq.rabbitmq.pubsub;

import com.miaoshao.mq.rabbitmq.rabbitUtils.RabbitConstant;
import com.miaoshao.mq.rabbitmq.rabbitUtils.RabbitUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author miaoxuebing
 * @Description: TODO[百度接收消息，消费者]
 * @date 2021/8/22 下午12:43
 */
public class Baidu {

    public static void main(String[] args) throws IOException {

        Connection connection = RabbitUtils.getConnection();

        final Channel channel = connection.createChannel();

        //申明队列信息
        channel.queueDeclare(RabbitConstant.QUEUE_BAIDU, false, false, false, null);

        //队列绑定交换机 参数1：队列名称   参数2：交换机名称  参数3：路由key
        channel.queueBind(RabbitConstant.QUEUE_BAIDU, RabbitConstant.EXCHANGE_WEATHER, "");

        //消费一条再去取一条
        channel.basicQos(1);

        channel.basicConsume(RabbitConstant.QUEUE_BAIDU, false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("百度微博收到天气信息：" + new String(body));
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });

    }
}
