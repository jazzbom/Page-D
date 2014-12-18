package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.Generator;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;

import dao.mongodb.DbManager;

/**
 * Servlet implementation class DoRemove
 */
@WebServlet("/DoRemove")
public class DoRemove extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoRemove() {
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
		
		response.setContentType("text/html;charset=UTF-8");
        
        
		//get all pages from form
        String name = request.getParameter("name");
        String url = request.getParameter("uri");
        
        System.out.println(name+" doRemove "+url);
        
        
        //get new checksum & time and update it to DB
        Generator g = new Generator();
        
        DbManager dbm = new DbManager();
        DB db = dbm.getDb();
        DBCollection pages = db.getCollection("pages");
        
        
        BasicDBObject findQuery = new BasicDBObject("url", url);
        pages.findAndRemove(findQuery);
        
        System.out.println(name+" Removed from DB");
		
		
	}

}
