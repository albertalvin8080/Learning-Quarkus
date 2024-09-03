package org.albert.schedule;

import io.quarkus.scheduler.Scheduled;
import jakarta.inject.Singleton;

import java.util.concurrent.atomic.AtomicInteger;

@Singleton
public class CounterScheduler
{
    private final AtomicInteger atomicInteger = new AtomicInteger(0);

    public int get()
    {
        return atomicInteger.get();
    }

//    @Scheduled(every = "3s")
//    @Scheduled(cron = "*/2 * * * * ?") // Each 2 seconds.
    @Scheduled(cron = "{cron.schedule}") // Inside application.properties
    void increment()
    {
//        atomicInteger.incrementAndGet();
//        atomicInteger.getAndAdd(1);
        atomicInteger.addAndGet(1);
    }
}
