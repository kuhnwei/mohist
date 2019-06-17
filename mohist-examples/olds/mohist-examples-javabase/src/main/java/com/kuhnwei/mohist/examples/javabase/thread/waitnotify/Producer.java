package com.kuhnwei.mohist.examples.javabase.thread.waitnotify;


/**
 * @author Kuhn Wei ,email@kuhnwei.com
 * @version 2018/5/2 10:59
 */
public class Producer implements Runnable {

    private EventStorage storage;

    public Producer(EventStorage storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i ++) {
            storage.set();
        }
    }
}
