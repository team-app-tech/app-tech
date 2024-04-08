package server.apptech.global.job;

import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobDetailService {

    public JobDetail getPrizeDistributionJobDetail(Long advertisementId, JobKey jobKey) {
        //JobData
        JobDataMap jobDataMap = getJobDataMap(advertisementId);
        // Job
        return JobBuilder
                .newJob(PrizeDistributionJob.class)
                .withIdentity(jobKey)
                .usingJobData(jobDataMap)
                .build();
    }
    private static JobDataMap getJobDataMap(Long advertisementId) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("advertisementId" , advertisementId);
        return jobDataMap;
    }
}
