package com.raddit.action3;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

public class ReceiveLogsDirect2 {
	  // 交换器名称
    private static final String EXCHANGE_NAME = "direct_logs";
    // 路由关键字
    private static final String[] routingKeys = new String[]{"info" ,"warning"};
    public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.exchangeDeclare(EXCHANGE_NAME, "direct");
		//获取匿名队列名称
		String queueName=channel.queueDeclare().getQueue();
		for (String routingKey : routingKeys) {
			channel.queueBind(queueName, EXCHANGE_NAME, routingKey);
			System.out.println("ReceiveLogsDirect2 exchange:"+EXCHANGE_NAME+"," +
                    " queue:"+queueName+", BindRoutingKey:" + routingKey);
		}
	    System.out.println("ReceiveLogsDirect2  Waiting for messages");
	    DefaultConsumer consumer = new DefaultConsumer(channel){
	    	
	    	@Override
	    	public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
	    			throws IOException {
	    		// TODO Auto-generated method stub
	    		super.handleDelivery(consumerTag, envelope, properties, body);
	    		String message = new String(body, "UTF-8");
	    		 System.out.println("ReceiveLogsDirect2 Received '" + envelope.getRoutingKey() + "':'" + message + "'");
	    	}
	    };
	    channel.basicConsume(queueName, true,consumer);
	}
}
