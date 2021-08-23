package com.miaoshao.mq.rabbitmq.routing;

import com.miaoshao.mq.rabbitmq.rabbitUtils.RabbitConstant;
import com.miaoshao.mq.rabbitmq.rabbitUtils.RabbitUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author miaoxuebing
 * @Description: TODO[发送者-发送天气信息]
 * @date 2021/8/22 下午12:28
 */
public class WeatherBureau1 {

    public static void main(String[] args) throws IOException, TimeoutException {

        Map area = new LinkedHashMap<String, String>();

        area.put("aaa", "中国湖南长沙20200822天气数据");
        area.put("bbb", "中国湖北武汉20200822天气数据");

        area.put("ccc", "中国河北石家庄20200822天气数据");
        area.put("ddd", "中国湖北武汉20200823天气数据");

        Connection conn = RabbitUtils.getConnection();
        Channel channel = conn.createChannel();

        Iterator<Map.Entry<String, String>> itr = area.entrySet().iterator();
        while (itr.hasNext()) {
            //第一个参数指定了：交换机
            //第二个参数：routing-key
            Map.Entry<String, String> me = itr.next();
            channel.basicPublish(RabbitConstant.EXCHANGE_WEATHER_ROUTING, me.getKey(), null, me.getValue().getBytes());
        }
        System.out.println("消息发送成功");
        channel.close();
        conn.close();

    }
}
