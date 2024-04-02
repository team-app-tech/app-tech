package server.apptech.global.job;

import lombok.RequiredArgsConstructor;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobDetailService {

    public JobDetail getPrizeDistributionJobDetail(Long advertisementId) {
        //JobData
        JobDataMap jobDataMap = getJobDataMap(advertisementId);
        // Job
        return JobBuilder
                .newJob(PrizeDistributionJob.class)
                .withIdentity("PrizeDistributionJob")
                .usingJobData(jobDataMap)
                .build();
    }
    private static JobDataMap getJobDataMap(Long advertisementId) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("advertisementId" , advertisementId);
        return jobDataMap;
    }
}
