package com.xuexiangban.rabbitmq.direct;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Consumer {

    private static Runnable runnable = new Runnable() {


        @Override
        public void run() {
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost("192.168.130.140");
            connectionFactory.setPort(5672);
            connectionFactory.setUsername("admin");
            connectionFactory.setPassword("admin");
            connectionFactory.setVirtualHost("/");

            final String queueName = Thread.currentThread().getName();
            Connection connection = null;
            Channel channel = null;
            try {

                connection = connectionFactory.newConnection("消费者");
                channel = connection.createChannel();
                channel.basicConsume(queueName, true, new DeliverCallback() {
                    @Override
                    public void handle(String s, Delivery delivery) throws IOException {
                        System.out.println(delivery.getEnvelope().getDeliveryTag());
                        System.out.println(queueName + "收到消息是" + new String(delivery.getBody(), StandardCharsets.UTF_8));
                    }
                }, new CancelCallback() {

                    @Override
                    public void handle(String consumerTage) throws IOException {
                        System.out.println("接受失败了");
                    }

                });


                System.out.println(queueName+"开始接受信息");
                System.in.read();


            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("发送消息出现异常");
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

        ;


        public static void main(String[] args) {
            new Thread(runnable, "queue1").start();
            new Thread(runnable, "queue2").start();
            new Thread(runnable, "queue3").start();


        }
    }

