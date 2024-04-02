package server.apptech.global.job;

import lombok.RequiredArgsConstructor;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TriggerService {

    public Trigger getPrizeDistributionTrigger(LocalDateTime triggerDate){
        return TriggerBuilder.newTrigger()
                .withIdentity("myTrigger")
//                .startAt(Date.from(triggerDate.atZone(ZoneId.systemDefault()).toInstant()))
                .startAt(java.sql.Timestamp.valueOf(triggerDate.plusDays(1)))
                .build();
    }

}
