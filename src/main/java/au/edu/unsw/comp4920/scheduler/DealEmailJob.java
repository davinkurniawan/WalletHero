package au.edu.unsw.comp4920.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class DealEmailJob implements Job {
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		System.out.println("DealEmailJob is being executed!");
		
		
		
	}
}