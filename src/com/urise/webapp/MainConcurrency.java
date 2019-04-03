package com.urise.webapp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class MainConcurrency {
    private static final int THREADS_NUMBER = 10000;
    //    private static int counter;
    private final AtomicInteger atomicInteger = new AtomicInteger();
    //    private static final Object LOCK = new Object();
//    private static final Lock lock = new ReentrantLock();
    private static final ThreadLocal<SimpleDateFormat> DATE_FORMAT = ThreadLocal.withInitial(() -> new SimpleDateFormat("dd.MM.yyyy HH:mm:ss"));

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName());
        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ", " + getState());
            }
        };
        thread0.start();

        new Thread(() -> System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState())).start();

        System.out.println(thread0.getState());

        final MainConcurrency mainConcurrency = new MainConcurrency();
        CountDownLatch latch = new CountDownLatch(THREADS_NUMBER);
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        //List<Thread> threads = new ArrayList<>(THREADS_NUMBER);
        for (int i = 0; i < THREADS_NUMBER; i++) {
            executorService.submit(() ->
//            Thread thread = new Thread(() ->
            {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                    System.out.println(DATE_FORMAT.get().format(new Date()));
                }
                latch.countDown();
            });
//            thread.start();
//            threads.add(thread);
        }
//        threads.forEach(t -> {
//            try {
//                t.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
        try {
            latch.await(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
        //System.out.println(counter);
        System.out.println(mainConcurrency.atomicInteger.get());

//        final String lock1 = "lock1";
//        final String lock2 = "lock2";
//        deadLock(lock1, lock2);
//        deadLock(lock2, lock1);
    }

//    private static void deadLock (Object lock1, Object lock2) {
//        new Thread(() -> {
//            System.out.println("Waiting" + lock1);
//            synchronized (lock1) {
//                System.out.println("Holding" + lock1);
//                try {
//                    Thread.sleep(10);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("Waiting" + lock2);
//                synchronized (lock2) {
//                    System.out.println("Holding" + lock2);
//                }
//            }
//        }).start();
//    }

    private void inc() {
        double a = Math.sin(13.);
        //synchronized (this) {
//            lock.lock();
//            try {
        atomicInteger.incrementAndGet();
//        counter++;
//            } finally {
//                lock.unlock();
//            }
        //}
    }
}
