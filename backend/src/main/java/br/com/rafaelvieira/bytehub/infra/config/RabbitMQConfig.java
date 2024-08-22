package br.com.rafaelvieira.bytehub.infra.config;

import lombok.Getter;
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

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private Integer port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Getter
    @Value("${rabbitmq.exchange}")
    private String exchangeName;

    @Getter
    @Value("${rabbitmq.routing-key.follow}")
    private String followRoutingKey;

    @Getter
    @Value("${rabbitmq.routing-key.like}")
    private String likeRoutingKey;

    @Getter
    @Value("${rabbitmq.queue.follow}")
    private String followQueueName;

    @Getter
    @Value("${rabbitmq.queue.like}")
    private String likeQueueName;

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
        return new TopicExchange(exchangeName, true, false);
    }

    @Bean
    public Queue followQueue() {
        return new Queue(followQueueName, false);
    }

    @Bean
    public Queue likeQueue() {
        return new Queue(likeQueueName, false);
    }

    @Bean
    public Binding followBinding(TopicExchange topicExchange) {
        return BindingBuilder
                .bind(followQueue())
                .to(topicExchange)
                .with(followRoutingKey);
    }

    @Bean
    public Binding likeBinding(TopicExchange topicExchange) {
        return BindingBuilder
                .bind(likeQueue())
                .to(topicExchange)
                .with(likeRoutingKey);
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
