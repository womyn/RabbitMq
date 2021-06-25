package com.xuexiangban.rabbitmq.all;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {
    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.130.140");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        connectionFactory.setVirtualHost("/");


        Connection connection = null;
        Channel channel = null;
        try {

            connection = connectionFactory.newConnection("生产者");

            channel = connection.createChannel();

            String message = "Hello xuexiangban!!!";
            String exchangeName="direct_message_exchange";

            String exchangeType="direct";

            channel.exchangeDeclare(exchangeName,exchangeType,true);
        //声明队列
            channel.queueDeclare("queue5",true,false,false,null);
            channel.queueDeclare("queue6",true,false,false,null);
            channel.queueDeclare("queue7",true,false,false,null);
        //绑定
            channel.queueBind("queue5",exchangeName,"order");
            channel.queueBind("queue6",exchangeName,"order");
            channel.queueBind("queue7",exchangeName,"course");

            channel.basicPublish(exchangeName, "order", null, message.getBytes());
            System.out.println("消息发送成功");


        } catch (Exception e) {
            e.printStackTrace();
        } finally {


            if (channel != null && channel.isOpen()) {

                try {
                    channel.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }


                if (connection != null && connection.isOpen()) {

                    try {
                        connection.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }


                }


            }


        }
    }
}