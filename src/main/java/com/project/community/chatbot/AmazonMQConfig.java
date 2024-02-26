package com.project.community.chatbot;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.project.community.chatbot.service.Receiver;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableRabbit
@RequiredArgsConstructor
public class AmazonMQConfig {
	
	private final ConnectionFactory connectionFactory;
	
	@Value("${spring.rabbitmq.template.default-receive-queue}")
	private String queue;
	@Value("${spring.rabbitmq.template.exchange}")
	private String exchange;
	@Value("${spring.rabbitmq.template.routing-key}")
	private String routingKey;
	
	@Bean
	Queue queue() {
		return new Queue(queue, false);
	}
	
	@Bean
	TopicExchange exchange() {
		return new TopicExchange(exchange);
	}
	
	@Bean
	Binding binding() {
		return BindingBuilder.bind(queue()).to(exchange()).with(routingKey);
	}
	
	@Bean
    SimpleMessageListenerContainer container(Receiver receiver) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(queue);
		container.setMessageListener(messageListenerAdapter(receiver));
		
		return container;
	}
	
	@Bean
	MessageListenerAdapter messageListenerAdapter(Receiver receiver) {
		MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(receiver, "receiveMessage");
		messageListenerAdapter.setMessageConverter(messageConverter());
		
		return messageListenerAdapter;
	}
	
	@Bean
	MessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
	}
}
