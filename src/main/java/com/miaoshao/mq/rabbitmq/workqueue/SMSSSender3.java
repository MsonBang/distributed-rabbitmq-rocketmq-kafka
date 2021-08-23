package com.miaoshao.mq.rabbitmq.workqueue;

import com.miaoshao.mq.rabbitmq.rabbitUtils.RabbitConstant;
import com.miaoshao.mq.rabbitmq.rabbitUtils.RabbitUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author miaoxuebing
 * @Description: TODO[消费者3]
 * @date 2021/8/22 上午11:21
 */
public class SMSSSender3 {
    public static void main(String[] args) throws IOException {
        Connection conn = RabbitUtils.getConnection();

        Channel channel = conn.createChannel();
        //第一个参数：队列名称
        //第二个参数：是否持久化
        //第三个参数：是否私有化，false代表所有消费者都可以消费，true代表第一次消费的人独有消费它
        //第四个参数，是否自动删除，false打标连接结束后不会自动删除掉这个队列。
        //第五个参数，null
        channel.queueDeclare(RabbitConstant.QUEUE_SMS, false, false, false, null);

        //处理完成一个取下一个
        channel.basicQos(1);

        //开始从mq消息服务器获取数据
        //第一个参数是：队列名称
        //第二个参数：是否自动确认收到消息，false带包手动编程来确认是否消费完。这是mq推荐做法。
        channel.basicConsume(RabbitConstant.QUEUE_SMS, false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String jsonSMS = new String(body);
                System.out.println("SMSSender3-短信发送成功：" + jsonSMS);
                /*try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });
    }
}
