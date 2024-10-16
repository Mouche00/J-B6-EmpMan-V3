package listeners;

import services.interfaces.JobOfferService;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@WebListener
public class JobOfferDeadlineListener implements ServletContextListener {

    @Inject
    private JobOfferService jobOfferService;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        SchedulerHolder.getScheduler().scheduleAtFixedRate(() -> {
            jobOfferService.checkDeadlines();
        }, 0, 1, TimeUnit.HOURS);

        System.out.println("JobOfferExpiryScheduler initialized.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        SchedulerHolder.shutdownScheduler();
    }

    private static class SchedulerHolder {
        private static final ScheduledExecutorService SCHEDULER = Executors.newSingleThreadScheduledExecutor();

        public static ScheduledExecutorService getScheduler() {
            return SCHEDULER;
        }

        public static void shutdownScheduler() {
            if (SCHEDULER != null && !SCHEDULER.isShutdown()) {
                SCHEDULER.shutdownNow();
                System.out.println("JobOfferExpiryScheduler shutdown.");
            }
        }
    }
}
