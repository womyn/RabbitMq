package com.xuexiangban.rabbitmq.simple;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Consumer {

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

            connection = connectionFactory.newConnection("消费者");
            channel = connection.createChannel();
            channel.basicConsume("queue1", true, new DeliverCallback() {
                @Override
                public void handle(String consumerTage, Delivery message) throws IOException {
                    System.out.println("收到消息是" + new String(message.getBody(), StandardCharsets.UTF_8));
                }
                },new CancelCallback(){

@Override
                public void handle(String consumerTage) throws IOException {
                    System.out.println("接受失败了");
                }





            });


System.out.println("开始接受信息");
        System.in.read();


            }catch(Exception e){
                e.printStackTrace();
            } finally{


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
