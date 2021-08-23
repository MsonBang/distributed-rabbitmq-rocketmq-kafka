package com.miaoshao.mq.rabbitmq.pubsub;

import com.miaoshao.mq.rabbitmq.rabbitUtils.RabbitConstant;
import com.miaoshao.mq.rabbitmq.rabbitUtils.RabbitUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @author miaoxuebing
 * @Description: TODO[发送者-发送天气信息]
 * @date 2021/8/22 下午12:28
 */
public class WeatherBureau {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection conn = RabbitUtils.getConnection();
        String input = new Scanner(System.in).next();
        Channel channel = conn.createChannel();

        //第一个参数指定了：交换机
        //第二个参数：队列信息，这里不用指定，在消费者端进行绑定
        channel.basicPublish(RabbitConstant.EXCHANGE_WEATHER, "", null, input.getBytes());
        channel.close();
        conn.close();

    }
}
