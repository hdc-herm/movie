package com.hdc.rabbitmq;

import org.springframework.amqp.core.Queue;

public final class QueueConstant {

    public static final String  QUEUE_01 = "QUEUE_01";

    public static final String  QUEUE_02 = "QUEUE_02";

    public static final String  QUEUE_03 = "QUEUE_03";

    public  Queue queue1(){
        Queue queue = new Queue(QUEUE_01);
        return queue;
    }
    public  Queue queue2(){
        Queue queue = new Queue(QUEUE_02);
        return queue;
    }
    public  Queue queue3(){
        Queue queue = new Queue(QUEUE_03);
        return queue;
    }
}
