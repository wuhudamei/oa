package cn.damei.service.completionSiteData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
@Service
@EnableScheduling
@Lazy(false)
public class CompletionSiteDataQuartzService  {
    @Autowired
    private CompletionSiteDataService completionSiteDataService;
    @Scheduled(cron = "0 0 2 * * ?")
    public void getCompletionSiteDataQuartz() {
        completionSiteDataService.orderByActualEndDate();
    }
}
