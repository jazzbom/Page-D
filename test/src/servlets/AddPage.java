package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.Generator;

import com.mongodb.*;

import dao.mongodb.DbManager;


/**
 * Servlet implementation class AddPage
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/AddPage" })
public class AddPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddPage() {
        super();
        // TODO Auto-generated constructor stub
        
        
        
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  
		// Set response content type
		PrintWriter pw=response.getWriter();  
		  
		response.sendRedirect("index.html");  
		  
		pw.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
        
        
		//get all pages from form
        String name = request.getParameter("name");
        String url = request.getParameter("url");

        System.out.println(name+"  "+url);
				//create and send to mongodb
        

        DbManager dbm = new DbManager();
        DB db = dbm.getDb();
        DBCollection pages = db.getCollection("pages");
        
        BasicDBObject dbPage = new BasicDBObject();
        dbPage.put("name", name);
        dbPage.put("url", url);
        dbPage.put("status", "SAFE");
        dbPage.put("mailSent", "N");
        
        Generator g = new Generator();
        
        dbPage.put("checksum", g.calculateChecksum(name, url));
       
//        Calendar rightNow = Calendar.getInstance();rightNow.getTime();
        
        dbPage.put("timestamp", "" );
        
        //dbPage.put("timestamp", new BasicDBObject("$currentDate",new BasicDBObject("timestamp","true")) );
        pages.insert(dbPage);
		
        //update with current time
        BasicDBObject updateQuery = new BasicDBObject("timestamp", "");
        pages.update(updateQuery, new BasicDBObject("$currentDate",new BasicDBObject("timestamp",true)));
        		
		//client.close();
        
        //redirect to index.html
      doGet(request,response);
      
	}

}
