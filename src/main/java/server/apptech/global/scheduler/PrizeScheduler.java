package server.apptech.global.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.stereotype.Component;
import server.apptech.global.exception.ExceptionCode;
import server.apptech.global.job.JobDetailService;
import server.apptech.global.job.JobKeyService;
import server.apptech.global.job.TriggerService;

import java.time.LocalDateTime;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class PrizeScheduler {

    private final Scheduler scheduler;
    private final JobDetailService jobDetailService;
    private final TriggerService triggerService;
    private final JobKeyService jobKeyService;

    public void reservePrizeDistributionTask(Long advertisementId, LocalDateTime endDate) {

        log.info("스케줄 등록");
        JobKey jobKey = jobKeyService.getPrizeDistributionTaskJobKey(advertisementId);
        Trigger prizeDistributionTrigger = triggerService.getPrizeDistributionTrigger(endDate,jobKey);
        JobDetail prizeDistributionJobDetail = jobDetailService.getPrizeDistributionJobDetail(advertisementId, jobKey);

        // 스케쥴러등록
        try {
            scheduler.scheduleJob(prizeDistributionJobDetail, prizeDistributionTrigger);
        }catch (SchedulerException schedulerException){
            log.info("scheduler 예외발생, {}",schedulerException.getMessage());
        }
    }

    public void modifyPrizeDistributionTask(Long advertisementId, LocalDateTime endDate) {

        log.info("modifyPrizeDistributionTask 실행");
        try{
            Set<JobKey> prizeDistributionTaskJobKeys = scheduler.getJobKeys(GroupMatcher.jobGroupEquals("PrizeDistributionTask"));
            JobKey jobKey = prizeDistributionTaskJobKeys.stream().filter(jobkey -> jobkey.getName().equals(advertisementId.toString())).findFirst().orElseThrow(() -> new server.apptech.global.exception.SchedulerException(ExceptionCode.NOT_FOUND_JOB_KEY));

            log.info("PrizeDistributionTask 삭제");
            scheduler.deleteJob(jobKey);
            reservePrizeDistributionTask(advertisementId, endDate);
        } catch (SchedulerException schedulerException) {
            log.info("scheduler 예외발생, {}",schedulerException.getMessage());
        }
    }

}
