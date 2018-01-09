package com.zxw.flowrx.product;

import java.util.PriorityQueue;

/**
 * Created by xiangwuzhu on 17/11/22.
 */

public class Producer extends Thread{

    private PriorityQueue<Integer> queue = new PriorityQueue();

    public Producer(Runnable target, PriorityQueue<Integer> queue) {
        super(target);
        this.queue = queue;
    }

    @Override
    public void run() {
        super.run();
    }
}
