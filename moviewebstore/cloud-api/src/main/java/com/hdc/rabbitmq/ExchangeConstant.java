package com.hdc.rabbitmq;

import org.springframework.amqp.core.DirectExchange;

public final class ExchangeConstant {

    public static final String  EXCHANGE_01 = "EXCHANGE_01";

    public static final String  EXCHANGE_02 = "EXCHANGE_02";

    public static final String  EXCHANGE_03 = "EXCHANGE_03";

    public DirectExchange exchange1(){
        DirectExchange directExchange = new DirectExchange(EXCHANGE_01);
        return directExchange;
    }
    public  DirectExchange exchange2(){

        DirectExchange directExchange = new DirectExchange(EXCHANGE_02);
        return directExchange;
    }
    public  DirectExchange exchange3(){
        DirectExchange directExchange = new DirectExchange(EXCHANGE_03);
        return directExchange;
    }
}
