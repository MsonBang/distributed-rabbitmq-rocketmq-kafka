package com.miaoshao.mq.rabbitmq.rabbitUtils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author miaoxuebing
 * @Description: TODO[工具类]
 * @date 2021/8/22 上午11:02
 */
public class RabbitUtils {

    private static ConnectionFactory connectionFactory = new ConnectionFactory();

    static {
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("miaoxuebing");
        connectionFactory.setPassword("123456");
        connectionFactory.setVirtualHost("miaoshaoTest");
    }

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = connectionFactory.newConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
