package com.cloud.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author zhuz
 * @date 2020/8/3
 */
public class QuartzJobFactory implements Job, ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(QuartzJobFactory.class);

    public ApplicationContext appCtx;

    @Override
    public void execute(JobExecutionContext context) {
        try {
            ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
            TaskUtils taskUtils = appCtx.getBean("taskUtils", TaskUtils.class);
            taskUtils.invokeMethod(scheduleJob);
        } catch (Exception e) {
            logger.error("QuartzJobFactory execute error", e);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.appCtx = applicationContext;
    }


}
