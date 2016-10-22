package au.edu.unsw.comp4920.common;

import static org.quartz.JobBuilder.*;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

import au.edu.unsw.comp4920.scheduler.DealEmailJob;

public class QuartzListener implements ServletContextListener {
	private Scheduler scheduler = null;
	public CommonDAO _dao;	
	
	@Override
	public void contextInitialized(ServletContextEvent servletContext) {
		System.out.println("Context Initialized");
		
		try {
			// Setup the Job class and the Job group
			JobDetail job = newJob(DealEmailJob.class).withIdentity("CronDealEmailJob", "Group").build();
			
			// Create a Trigger that fires every 5 minutes.
			Trigger trigger = newTrigger()
		            .withIdentity("trigger1", "group1")
		            .startNow()
		            .withSchedule(simpleSchedule()
		                    .withIntervalInHours(24 * 3) // Every 3 days, the deals email is sent.
		                    .repeatForever())            
		            .build();

			// Setup the Job and Trigger with Scheduler & schedule jobs
			scheduler = new StdSchedulerFactory().getScheduler();
			scheduler.start();
			scheduler.scheduleJob(job, trigger);
		}
		catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent servletContext) {
		System.out.println("Context Destroyed");
		
		try {
			scheduler.shutdown();
		}
		catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
}
