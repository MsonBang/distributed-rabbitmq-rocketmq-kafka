package com.miaoshao.mq.rabbitmq.workqueue;

import com.google.gson.Gson;
import com.miaoshao.mq.rabbitmq.rabbitUtils.RabbitConstant;
import com.miaoshao.mq.rabbitmq.rabbitUtils.RabbitUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


/**
 * @author miaoxuebing
 * @Description: TODO[生产者]
 * @date 2021/8/22 上午11:11
 */
public class OrderSystem {

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection conn = RabbitUtils.getConnection();

        Channel channel = conn.createChannel();

        channel.queueDeclare(RabbitConstant.QUEUE_SMS, false, false, false, null);

        for (int i = 0; i <= 100; i++) {
            SMS sms = new SMS("乘客" + i, "139000001" + i, "您的车票已经预订成功");
            String jsonSMS = new Gson().toJson(sms);
            channel.basicPublish("", RabbitConstant.QUEUE_SMS, null, jsonSMS.getBytes());
        }
        System.out.println("数据发送成功");
        channel.close();
        conn.close();
    }

}

