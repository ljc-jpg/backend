package com.cloud.quartz;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author zhuzheng
 * @Description: 方法一次执行不完  下次轮转时等待改方法执行完后才执行下一次操作
 * @date 2020年4月2日 下午5:05:47
 */
@DisallowConcurrentExecution
public class QuartzJobFactoryDisallowConcurrentExecution implements Job, ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(QuartzJobFactory.class);

    public ApplicationContext appCtx;

    @Override
    public void execute(JobExecutionContext context) {
        try {
            ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
            TaskUtils taskUtils = appCtx.getBean("taskUtils", TaskUtils.class);
            taskUtils.invokeMethod(scheduleJob);
        } catch (Exception e) {
            logger.error("QuartzJobFactoryDisallowConcurrentExecution error", e);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.appCtx = applicationContext;
    }

}
