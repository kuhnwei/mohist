package com.kuhnwei.mohist.examples.javabase.thread.waitnotify;

/**
 * @author Kuhn Wei ,email@kuhnwei.com
 * @version 2018/5/2 11:01
 */
public class Consumer implements Runnable {

    private EventStorage storage;

    public Consumer(EventStorage storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i ++) {
            storage.get();
        }
    }

    public static void main(String[] args) {
        EventStorage storage = new EventStorage();
        Producer producer = new Producer(storage);
        Consumer consumer = new Consumer(storage);
        Thread threadProducer = new Thread(producer);
        Thread threadConsumer = new Thread(consumer);
        threadConsumer.start();
        threadProducer.start();

    }
}
