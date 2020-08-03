package com.cloud.quartz;

import com.cloud.dao.QuartzMapper;
import com.cloud.model.Quartz;
import com.cloud.util.ActiveEnum;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuz
 * @date 2020/8/3
 */
@Component
public class InitQuartzJob implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(InitQuartzJob.class);

    public static SchedulerFactoryBean schedulerFactoryBean = null;

    @Resource
    private QuartzMapper quartzMapper;

    public static ApplicationContext appCtx;

    @Scheduled(fixedRate = 30000)
    public void run() {
        schedulerFactoryBean = appCtx.getBean(SchedulerFactoryBean.class);
        // 这里从数据库中获取任务信息数据
        List<Quartz> list = quartzMapper.selectAll();
        List<ScheduleJob> jobList = new ArrayList<>();
        for (Quartz quartz : list) {
            ScheduleJob job = new ScheduleJob();
            job.setJobId(quartz.getQuartzId() + "");
            // 任务组
            job.setJobGroup(quartz.getGroupName());
            // 任务名称
            job.setJobName(quartz.getTaskName());
            // 任务发布状态
            job.setJobStatus(quartz.getStatus() + "");
            // 运行状态
            job.setIsConcurrent("0");
            job.setCronExpression(quartz.getCron());
            // 一个以所给名字注册的bean的实例
            job.setBeanClass(quartz.getAddress());
            // 参数
            job.setJobData(quartz.getParam());
            logger.info("schedulerFactoryBean:{}" + quartz.getTaskName());
            jobList.add(job);
        }
        for (ScheduleJob job : jobList) {
            try {
                addJob(job);
            } catch (SchedulerException e) {
                logger.error("run error " + job.toString(), e);
            }
        }
    }

    /**
     * @param job
     * @Description:添加定时任务
     * @author zhu zheng
     * @date 2020/4/3
     */
    public static void addJob(ScheduleJob job) throws SchedulerException {
        if (job == null) {
            return;
        }
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        // 不存在，创建一个
        if (null == trigger) {
            if (ActiveEnum.ONE_EVENT.getValue().equals(job.getJobStatus())) {
                Class clazz = ScheduleJob.CONCURRENT_IS.equals(job.getIsConcurrent()) ? QuartzJobFactory.class
                        : QuartzJobFactoryDisallowConcurrentExecution.class;

                JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(job.getJobName(), job.getJobGroup())
                        .usingJobData("data", job.getJobData()).build();

                jobDetail.getJobDataMap().put("scheduleJob", job);

                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

                trigger = TriggerBuilder.newTrigger().withDescription(job.getJobId())
                        .withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).build();

                logger.info("add job:{}" + triggerKey);
                scheduler.scheduleJob(jobDetail, trigger);
            }
        } else {
            if (ActiveEnum.ONE_EVENT.getValue().equals(job.getJobStatus()) && !trigger.getCronExpression().equals(job.getCronExpression())) {
                logger.info("origin cron:{} now cron:{}" + trigger.getCronExpression() + job.getCronExpression());
                // Trigger已存在，那么更新相应的定时设置
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
                // 按新的cronExpression表达式重新构建trigger
                trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).usingJobData("data", job.getJobData())
                        .withSchedule(scheduleBuilder).build();
                // 按新的trigger重新设置job执行
                scheduler.rescheduleJob(triggerKey, trigger);
            } else if (ActiveEnum.ZERO_EVENT.getValue().equals(job.getJobStatus())) {
                logger.info("delete job:{}" + triggerKey);
                scheduler.unscheduleJob(triggerKey);
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        appCtx = applicationContext;
    }


}
