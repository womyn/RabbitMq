package com.xuexiangban.rabbitmq.simple;


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

            String queueName = "queue1";
            channel.queueDeclare(queueName, false, false, false, null);
            String message = "Hello xuexiangban!!!";
            channel.basicPublish("", queueName, null, message.getBytes());

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