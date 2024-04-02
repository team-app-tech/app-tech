package server.apptech.global.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;
import server.apptech.prize.service.PrizeService;

@Component
@RequiredArgsConstructor
@Slf4j
public class PrizeDistributionJob implements Job {

    private final PrizeService prizeService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        log.info("PrizeDistributionJob 실행");
        Long advertisementId = (Long) context.getJobDetail().getJobDataMap().get("advertisementId");
        //포인트(상금) 배분 로직 구현
        prizeService.givePrizeToWinner(advertisementId);

    }
}
