package com.miaoshao.mq;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author miaoxuebing
 * @Description: TODO
 * @date 2021/8/23 下午3:54
 */
@SpringBootTest
public class ProducerTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    //测试confirm
    @Test
    public void testConfirm(){
        //定义回调,当消息无法到达交换机，就会回调
        rabbitTemplate.setConfirmCallback((correlationData, ack, s) -> {
            System.out.println("confirm方法被执行了。。。。。。");
            if(ack){
                System.out.println("接收消息成功"+s);
            }else{
                System.out.println("接收消息失败"+s);
            }

        });
        rabbitTemplate.convertAndSend("text_exchange_confirm","confirm","测试confirm");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //测试return
    @Test
    public void testReturn(){
        //设置交换机处理失败的模式，为true消息到达不了队列。会将消息返回给生产者
        rabbitTemplate.setMandatory(true);
        //定义回调,当消息无法到达交换机，就会回调
        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(ReturnedMessage returnedMessage) {
                System.out.println("message:"+returnedMessage.getMessage());
                System.out.println("replyCode"+returnedMessage.getReplyCode());
                System.out.println("replyText"+returnedMessage.getReplyText());
                System.out.println("exchange"+returnedMessage.getExchange());
                System.out.println("routingKey"+returnedMessage.getRoutingKey());
            }
        });
        rabbitTemplate.convertAndSend("text_exchange_confirm","confirm","测试confirm");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
