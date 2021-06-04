package concurrency.synchronizedmethods;

public class SynchronizedAccess {

    public static void main(String[] args) {
        SynchronizedCounter counter = new SynchronizedCounter();
        Thread threadA = new SynchronizedCounterThreadA(counter);
        Thread threadB = new SynchronizedCounterThreadB(counter);
        Thread threadC = new SynchronizedCounterThreadC(counter);

        threadA.start();
        threadB.start();
        threadC.start();
    }
}

class SynchronizedCounterThreadA extends Thread {
    private SynchronizedCounter counter = null;

    public SynchronizedCounterThreadA(SynchronizedCounter counter) {
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

class SynchronizedCounterThreadB extends Thread {
    private SynchronizedCounter counter = null;

    public SynchronizedCounterThreadB(SynchronizedCounter counter) {
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

class SynchronizedCounterThreadC extends Thread {
    private SynchronizedCounter counter = null;

    public SynchronizedCounterThreadC(SynchronizedCounter counter) {
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

class SynchronizedCounter {
    private int value = 0;

    public synchronized void incrementValue() {
        this.value++;
    }

    public synchronized void decrementValue() {
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
