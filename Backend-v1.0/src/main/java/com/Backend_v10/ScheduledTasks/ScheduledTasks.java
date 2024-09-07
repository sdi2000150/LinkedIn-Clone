package com.Backend_v10.ScheduledTasks;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    @Scheduled(fixedRate = 30000) // 30000 milliseconds = 30 seconds
    public void runEveryThirtySeconds() {
        System.out.println("Hit");

    }
}