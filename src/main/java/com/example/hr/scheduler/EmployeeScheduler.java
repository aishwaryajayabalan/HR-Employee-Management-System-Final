
package com.example.hr.scheduler;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.hr.enums.EmployeeStatus;
import com.example.hr.repository.EmployeeRepository;

@Component
public class EmployeeScheduler {

    private static final Logger logger =
            LoggerFactory.getLogger(EmployeeScheduler.class);

    private final EmployeeRepository employeeRepository;

    public EmployeeScheduler(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Scheduled(cron = "0 0 9 * * *")
    public void runDailyEmployeeSummary() {

        long totalEmployees = employeeRepository.count();

        long activeEmployees =
                employeeRepository.countByStatusNative(
                        EmployeeStatus.ACTIVE.name());

        long inactiveEmployees =
                employeeRepository.countByStatusNative(
                        EmployeeStatus.INACTIVE.name());

        long employeesOnLeave =
                employeeRepository.countByStatusNative(
                        EmployeeStatus.ON_LEAVE.name());

        long terminatedEmployees =
                employeeRepository.countByStatusNative(
                        EmployeeStatus.TERMINATED.name());

        logger.info("========== Daily HR Employee Summary ==========");
        logger.info("Report generated at: {}", LocalDateTime.now());
        logger.info("Total employees: {}", totalEmployees);
        logger.info("Active employees: {}", activeEmployees);
        logger.info("Inactive employees: {}", inactiveEmployees);
        logger.info("Terminated employees: {}", terminatedEmployees);
        logger.info("Employees on leave: {}", employeesOnLeave);
        logger.info("================================================");
    }
}
