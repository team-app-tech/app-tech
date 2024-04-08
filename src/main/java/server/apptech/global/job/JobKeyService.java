package server.apptech.global.job;

import lombok.RequiredArgsConstructor;
import org.quartz.JobKey;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobKeyService {


    public JobKey getPrizeDistributionTaskJobKey(Long advertisementId) {
        return new JobKey(advertisementId.toString(), "PrizeDistributionTask");
    }
}
