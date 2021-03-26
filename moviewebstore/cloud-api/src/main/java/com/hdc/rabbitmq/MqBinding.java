package com.hdc.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;

public class MqBinding {

    private ExchangeConstant exchangeConstant = new ExchangeConstant();

    private QueueConstant queueConstant = new QueueConstant();


    public Binding binding1(){
        return BindingBuilder.bind(queueConstant.queue1()).to(exchangeConstant.exchange1()).with(RoutingKey.ROUTING_1);
    }

    public Binding binding2(){
        return BindingBuilder.bind(queueConstant.queue2()).to(exchangeConstant.exchange2()).with(RoutingKey.ROUTING_2);
    }

    public Binding binding3(){
        return BindingBuilder.bind(queueConstant.queue3()).to(exchangeConstant.exchange3()).with(RoutingKey.ROUTING_3);
    }


}
