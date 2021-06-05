package concurrency.synchronizedmethods;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicAccess {

    public static void main(String[] args) {
        Counter counter = new Counter();
        Thread threadA = new CounterThreadA(counter);
        Thread threadB = new CounterThreadB(counter);
        Thread threadC = new CounterThreadC(counter);

        threadA.start();
        threadB.start();
        threadC.start();
    }

    static class CounterThreadA extends Thread {
        private final Counter counter;

        public CounterThreadA(Counter counter) {
            this.counter = counter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                counter.waitWithExecution(200);
                counter.modifyValue();
            }
        }
    }

    static class CounterThreadB extends Thread {
        private final Counter counter;

        public CounterThreadB(Counter counter) {
            this.counter = counter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                counter.waitWithExecution(200);
                counter.modifyValue();
            }
        }
    }

    static class CounterThreadC extends Thread {
        private final Counter counter;

        public CounterThreadC(Counter counter) {
            this.counter = counter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                counter.waitWithExecution(500);
                System.out.println(counter.getValue());
            }
        }
    }

    static class Counter {
        final AtomicInteger value = new AtomicInteger();

        public void modifyValue() {
            this.value.getAndIncrement();
        }

        public int getValue() {
            return value.get();
        }

        public void waitWithExecution(long millis) {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}