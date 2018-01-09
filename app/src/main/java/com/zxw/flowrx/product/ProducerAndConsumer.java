package com.zxw.flowrx.product;

import java.util.PriorityQueue;

/**
 * Created by xiangwuzhu on 17/11/22.
 */

public class ProducerAndConsumer {

    private static int queueSize = 10;
    private static PriorityQueue<Integer> queue = new PriorityQueue(queueSize);


    public static void main(String[] args){

        Producer producer = new Producer(new Runnable() {
            @Override
            public void run() {

            }
        }, queue);

        Consumer consumer = new Consumer();

    }

}
