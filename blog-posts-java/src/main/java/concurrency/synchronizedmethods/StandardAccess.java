package concurrency.synchronizedmethods;

public class StandardAccess {

    public static void main(String[] args) {
        Counter counter = new Counter();
        Thread threadA = new CounterThreadA(counter);
        Thread threadB = new CounterThreadB(counter);
        Thread threadC = new CounterThreadC(counter);

        threadA.start();
        threadB.start();
        threadC.start();
    }
}

class CounterThreadA extends Thread {
    private Counter counter = null;

    public CounterThreadA(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for(int i=0; i<10; i++){
            this.counter.waitWithExecution(500);
            this.counter.incrementValue();
        }
    }
}

class CounterThreadC extends Thread {
    private Counter counter = null;

    public CounterThreadC(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for(int i=0; i<10; i++){
            this.counter.waitWithExecution(500);
            this.counter.decrementValue();
        }
    }
}

class CounterThreadB extends Thread {
    private Counter counter = null;

    public CounterThreadB(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for(int i=0; i<10; i++){
            this.counter.waitWithExecution(200);
            System.out.println(this.counter.getValue());
        }
    }
}

class Counter {
    private int value = 0;

    public void incrementValue() {
        this.value++;
    }

    public void decrementValue() {
        this.value--;
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
