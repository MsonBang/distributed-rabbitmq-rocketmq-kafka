package com.miaoshao.mq.rabbitmq.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author miaoxuebing
 * @Description: TODO[发布者]
 * @date 2021/8/21 下午12:52
 */
public class Produccer {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //配置主机信息、端口号、用户名、密码、虚拟机名称
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("miaoxuebing");
        connectionFactory.setPassword("123456");
        connectionFactory.setVirtualHost("miaoshaoTest");

        //通过配置获取tcp长连接
        Connection conn = connectionFactory.newConnection();

        Channel channel = conn.createChannel();
        //第一个参数：队列名称
        //第二个参数：是否持久化
        //第三个参数：是否私有化，false代表所有消费者都可以消费，true代表第一次消费的人独有消费它
        //第四个参数，是否自动删除，false打标连接结束后不会自动删除掉这个队列。
        //第五个参数，null
        channel.queueDeclare("helloworld", false, false, false, null);

        //开始发送消息
        String msg = "33666";
        //第一个参数：选择交换机，这里简单模式使用默认的，不需要填写
        //第二个参数：队列名称，指定发送给那个队列
        //第三个参数：额外属性
        //第四个参数：发送数据byte数组
        channel.basicPublish("", "helloworld", null, msg.getBytes());
        channel.close();;
        conn.close();
        System.out.println("数据发送成功！");
    }

}
