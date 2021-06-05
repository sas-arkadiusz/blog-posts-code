package concurrency.synchronizedmethods;

public class SynchronizedStatements {

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
        private Counter counter;

        public CounterThreadA(Counter counter) {
            this.counter = counter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                counter.waitWithExecution(200);
                counter.modifyValue(i * i);
            }
        }
    }

    static class CounterThreadB extends Thread {
        private Counter counter;

        public CounterThreadB(Counter counter) {
            this.counter = counter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                counter.waitWithExecution(200);
                counter.modifyValue(i * (-1));
            }
        }
    }

    static class CounterThreadC extends Thread {
        private Counter counter;

        public CounterThreadC(Counter counter) {
            this.counter = counter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                counter.waitWithExecution(500);
                System.out.println(counter.getValue());
            }
        }
    }

    static class Counter {
        private int value = 0;

        public void modifyValue(int value) {
            synchronized (this) {
                this.value += value;
            }
        }

        public int getValue() {
            return value;
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