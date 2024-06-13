package br.com.rafaelvieira.bytehub.infra.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "notification_exchange";
    public static final String FOLLOW_ROUTING_KEY = "follow";
    public static final String LIKE_ROUTING_KEY = "like";
    public static final String FOLLOW_QUEUE_NAME = "follow_queue";
    public static final String LIKE_QUEUE_NAME = "like_queue";

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private Integer port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(host);
        cachingConnectionFactory.setPort(port);
        cachingConnectionFactory.setUsername(username);
        cachingConnectionFactory.setPassword(password);
        return cachingConnectionFactory;
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue followQueue() {
        return new Queue(FOLLOW_QUEUE_NAME, false);
    }

    @Bean
    public Queue likeQueue() {
        return new Queue(LIKE_QUEUE_NAME, false);
    }

    @Bean
    public Binding followBinding(TopicExchange topicExchange, Queue followQueue) {
        return BindingBuilder.bind(followQueue).to(topicExchange).with(FOLLOW_ROUTING_KEY);
    }

    @Bean
    public Binding likeBinding(TopicExchange topicExchange, Queue likeQueue) {
        return BindingBuilder.bind(likeQueue).to(topicExchange).with(LIKE_ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
