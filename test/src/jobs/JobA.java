package jobs;

import java.io.IOException;
import java.net.UnknownHostException;

import main.Generator;
import main.SendMail;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;




import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import dao.mongodb.DbManager;
 
public class JobA implements Job {
	DbManager dbm = new DbManager();
	 SendMail s = new SendMail();
	
	@Override
	public void execute(JobExecutionContext context)
		throws JobExecutionException {
		
		task();
		
		
	}
	
	
	public void task()
	{
		
		System.out.println("Job A is runing");      
		DB db = dbm.getDb();
        DBCollection pageCollection = db.getCollection("pages");
        
        //retrieve all documents from collection, iterate & compare checksum with most recent
        DBCursor cursor = pageCollection.find();
        
        System.out.println("runing");
       
        
        try {
        	   while(cursor.hasNext()) {
        	     // System.out.println(cursor.next());
        	      BasicDBObject obj =  (BasicDBObject)cursor.next();
           	      
        	      System.out.println("n "+obj.getString("name"));
        	      
        	      String name = (String)obj.get("name");
        	      String url  = obj.getString("url");
        	      String status = obj.getString("status");
        	 
        	 
        	      
        	      //cal checksum of current page
        	      Generator g = new Generator();
        	      String checksum = g.calculateChecksum(name, url);
        	      
        	      System.out.println(obj.getString("checksum")+" - "+checksum);
        	      
        	      //compare with masterDB checksum
        	      if(!checksum.equals(obj.getString("checksum")) )
        	      {
        	    	  System.out.println("DANGER "+obj.getString("checksum")+" != "+checksum);
        	    	  //update masterDB status to DANGER
        	    	  BasicDBObject updateQuery = new BasicDBObject("url", url);
        	    	  pageCollection.update(updateQuery, new BasicDBObject("$set", new BasicDBObject("status","DANGER"))); 
        	    	  
        	    	  //Check if user previously notified else send mail to user
        	    	  if(obj.getString("mailSent").equals("N"))
        	    	  {
        	    		  String mailSub = "Page D Alert - Malicious Activity detected";
            	    	  String mailMsg = "Dear user, \n Please look at the details below: "
            	    	  		+ "\n 1. The page "+name+" has been modified without authorisation. "
            	    	  		+ "\n Name tag: "+name+"\n"
            	    	  		+ " URL: "+url+"\n"
            	    	  		+ " Regards, Page D";
            	    	  s.sendMail(mailSub, mailMsg);
            	    	  
            	    	  pageCollection.update(updateQuery, new BasicDBObject("$set", new BasicDBObject("mailSent","Y"))); 
            	    	  
        	    	  }
        	    	 
        	    	  
        	    	  
        	    	
        	      }
        	      if(status.equals("DANGER") && checksum.equals(obj.getString("checksum")))
        	      {
        	    	  BasicDBObject updateQuery = new BasicDBObject("url", url);
        	    	  pageCollection.update(updateQuery, new BasicDBObject("$set", new BasicDBObject("status","SAFE/DANGER")));
        	    	  
        	    	  //send mail to user stating content changed reverted to original
        	    	  
        	      }
        	      
        	      
        	      
        	   }
        	} finally {
        	   cursor.close();
        	}
	}
	
	

 
}