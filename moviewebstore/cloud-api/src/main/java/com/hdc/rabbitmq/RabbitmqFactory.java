package com.hdc.rabbitmq;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Data
@NoArgsConstructor
public class RabbitmqFactory {
    /**
     * 主机ip
     */
    public String host;

    /**
     * rabbitmq端口号
     */
    public int port;

    /**
     * 用户名
     */
    public String username;

    /**
     * 密码
     */
    public String password;

    /**
     * 虚拟主机
     */
    public String virtualHost;

    public RabbitmqFactory(String host, int port, String username, String password, String virtualHost){
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.virtualHost = virtualHost;
        connectionFactory();
    }

    public CachingConnectionFactory connectionFactory(){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        return connectionFactory;
    }

    public RabbitTemplate getConnection() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        return template;
    }

}
