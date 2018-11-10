package ru.job4j;


import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.time.LocalDateTime;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;


public class Start implements Job {
    private static final Logger LOGGER = Logger.getLogger(Start.class);
    private static final Config CONFIG = new Config(); // пришлось сделать статичным

    public static void main(String[] args) {
        try {
            SchedulerFactory sf = new StdSchedulerFactory();
            Scheduler sched = sf.getScheduler();
            JobDetail job = newJob(Start.class)
                    .withIdentity("job1", "group1")
                    .build();


            CronTrigger trigger = newTrigger()
                    .withIdentity("trigger3", "group1")
                    .withSchedule(cronSchedule(CONFIG.getDataconfig("cron.time")))
                    .build();

            sched.scheduleJob(job, trigger);
            sched.start();
        } catch (SchedulerException e) {
            LOGGER.error(e.getMessage(), e);
        }


    }


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try (Parser parser = new Parser(CONFIG)) {
            LOGGER.info("Load vacansi " + LocalDateTime.now());
            parser.loadFile();
            parser.dataStatistic();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
