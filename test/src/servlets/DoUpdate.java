package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jobs.JobA;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;

import dao.mongodb.DbManager;
import main.Generator;

/**
 * Servlet implementation class DoUpdate
 */
@WebServlet("/DoUpdate")
public class DoUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoUpdate() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
        
        
		//get all pages from form
        String name = request.getParameter("name");
        String url = request.getParameter("uri");
        
        System.out.println(name+" doupdate "+url);
        
        
        //get new checksum & time and update it to DB
        Generator g = new Generator();
        
        DbManager dbm = new DbManager();
        DB db = dbm.getDb();
        DBCollection pages = db.getCollection("pages");
        
        
        
        //update with current time
        BasicDBObject updateQuery = new BasicDBObject("url", url);
        pages.update(updateQuery, new BasicDBObject("$set", new BasicDBObject("checksum",g.calculateChecksum(name, url))));
        pages.update(updateQuery, new BasicDBObject("$set", new BasicDBObject("status","SAFE")));
        pages.update(updateQuery, new BasicDBObject("$set", new BasicDBObject("mailSent","N")));
		//update time
        pages.update(updateQuery, new BasicDBObject("$currentDate",new BasicDBObject("timestamp",true)));
        
        //explicit task run to refresh data with status
        JobA job = new JobA();
        job.task();
        
      
	}

}
