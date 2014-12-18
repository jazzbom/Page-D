package dao.mongodb;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class DbManager {

	private MongoClientURI uri  = new MongoClientURI("mongodb://jb:jay123@ds027521.mongolab.com:27521/m"); 
	private MongoClient client;
	private DB db;
	
	public DbManager() {
		setConnection();
	}
	
	private void setConnection()
	{
		try {
			this.client = new MongoClient(uri);
			this.db = client.getDB(uri.getDatabase());   
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public void setUri(String uri1) {
		
		this.uri  = new MongoClientURI(uri1);
		
	}

	public DB getDb() {
		return db;
	}
	
	
	
	
}
