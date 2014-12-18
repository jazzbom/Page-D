package servlets;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import jobs.JobA;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Application Lifecycle Listener implementation class QuartzInitialiser
 *
 */
@WebListener
public class QuartzInitialiser implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public QuartzInitialiser() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
    	
    	
    	String key = "org.quartz.impl.StdSchedulerFactory.KEY";
        ServletContext servletContext = arg0.getServletContext();
        StdSchedulerFactory factory = (StdSchedulerFactory) servletContext.getAttribute(key);
        try {
			factory.initialize();
		} catch (SchedulerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
			Scheduler scheduler;
			try {
				scheduler = factory.getDefaultScheduler();
			
			
			JobKey jobKeyA = new JobKey("jobA", "group1");
	    	JobDetail jobA = JobBuilder.newJob(JobA.class)
			.withIdentity(jobKeyA).build();

			    // Trigger the job to run now, and then repeat every 40 seconds

	    	// Trigger the job to run on the next round minute
	    	Trigger trigger1 = TriggerBuilder
	    			.newTrigger()
	    			.withIdentity("dummyTriggerName1", "group1")
	    			.withSchedule(
	    				CronScheduleBuilder.cronSchedule("0/8 * * * * ?"))
	    			.build();
	    	//  0 0 12 ? * WED
	    	scheduler.scheduleJob(jobA, trigger1);
			
			// and start it off
            scheduler.start();

          
            
           // Thread.sleep(15000);
            
           // scheduler.shutdown();

			} catch (SchedulerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
	
    	
    	
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    }
	
}
