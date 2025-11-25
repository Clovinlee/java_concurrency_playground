package org.chris.Services;

import org.chris.Utils.Helper;

import java.time.Clock;
import java.util.concurrent.locks.ReentrantLock;

public class CounterWithLockService {
    private static final int DELAY_MILISECONDS = 500;
    private final Clock clock;
    // ReentrantReadWriteLock used for heavy-read operation lock
    private final ReentrantLock lock = new ReentrantLock();
    private int count = 0;

    public CounterWithLockService(Clock clock) {
        this.clock = clock;
    }

    public void increment() {
        this.increment(1);
    }

    public void increment(int incrementor) {
        lock.lock();
        System.out.println(Helper.now(clock) + " | LOG: Begin Counter Increment");

        try {
            this.count += incrementor;
            Thread.sleep(DELAY_MILISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }

        System.out.println(Helper.now(clock) + String.format(" | LOG: Counter Increment Completed (%s ms)", DELAY_MILISECONDS));
    }

    public int getCount() {
        return count;
    }
}
