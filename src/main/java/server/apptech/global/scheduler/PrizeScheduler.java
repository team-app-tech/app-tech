package server.apptech.global.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Component;
import server.apptech.global.job.JobDetailService;
import server.apptech.global.job.TriggerService;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class PrizeScheduler {

    private final Scheduler scheduler;
    private final JobDetailService jobDetailService;
    private final TriggerService triggerService;

    public void reservePrizeDistributionTask(Long advertisementId, LocalDateTime endDate) {

        log.info("스케줄 등록");
        JobDetail prizeDistributionJobDetail = jobDetailService.getPrizeDistributionJobDetail(advertisementId);
        Trigger prizeDistributionTrigger = triggerService.getPrizeDistributionTrigger(endDate);

        // 스케쥴러등록
        try {
            scheduler.scheduleJob(prizeDistributionJobDetail, prizeDistributionTrigger);
        }catch (SchedulerException schedulerException){
            log.info("예외발생");
        }

    }

}
