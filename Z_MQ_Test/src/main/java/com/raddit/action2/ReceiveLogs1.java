package com.raddit.action2;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class ReceiveLogs1 {
	private static final String EXCHANGE_NAME = "logs";

	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		// fanout表示分发，所有的消费者得到同样的队列信息
		/*channel.exchangeDeclare(EXCHANGE_NAME, "fanout");*/
		// 產生一個所及的隊列名稱
		String queueName = channel.queueDeclare().getQueue();
		// 对队列进行绑定
		channel.queueBind(queueName, EXCHANGE_NAME, "");
		System.out.println("ReceiveLogs1 Waiting for messages");
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println("ReceiveLogs1 Received '" + message + "'");
			}
		};
		channel.basicConsume(queueName, true, consumer);// 队列会自动删除
	}
}
