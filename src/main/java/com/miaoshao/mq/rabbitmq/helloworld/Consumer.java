package com.miaoshao.mq.rabbitmq.helloworld;

import com.miaoshao.mq.rabbitmq.rabbitUtils.RabbitConstant;
import com.miaoshao.mq.rabbitmq.rabbitUtils.RabbitUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author miaoxuebing
 * @Description: TODO[消费者]
 * @date 2021/8/21 下午1:05
 */
public class Consumer {

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection conn = RabbitUtils.getConnection();

        Channel channel = conn.createChannel();
        //第一个参数：队列名称
        //第二个参数：是否持久化
        //第三个参数：是否私有化，false代表所有消费者都可以消费，true代表第一次消费的人独有消费它
        //第四个参数，是否自动删除，false打标连接结束后不会自动删除掉这个队列。
        //第五个参数，null
        channel.queueDeclare(RabbitConstant.QUEUE_HELLWORLD, false, false, false, null);

        //开始从mq消息服务器获取数据
        //第一个参数是：队列名称
        //第二个参数：是否自动确认收到消息，false带包手动编程来确认是否消费完。这是mq推荐做法。
        channel.basicConsume(RabbitConstant.QUEUE_HELLWORLD, false, new Receiver(channel));
    }
}


class Receiver extends DefaultConsumer {

    private Channel channel;

    public Receiver(Channel channel) {
        super(channel);
        this.channel = channel;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        String msg = new String(body);
        System.out.println("接收到的消息为：" + msg);
        System.out.println("消息的标记信息：" + envelope.getDeliveryTag());
        //false值确定消费完了当前的这一个标签。true表示所有的标签都消费完了。
        channel.basicAck(envelope.getDeliveryTag(), false);
    }
}
