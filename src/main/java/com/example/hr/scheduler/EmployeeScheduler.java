
package com.example.hr.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmployeeScheduler {

    private static final Logger logger =
            LoggerFactory.getLogger(EmployeeScheduler.class);

    @Scheduled(cron = "0 0 9 * * *")
    public void runDailyEmployeeSummary() {
        logger.info("Daily HR employee summary scheduler executed.");
    }
}
