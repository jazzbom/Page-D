package main;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.io.*; 
import java.security.*; 
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

import jonelo.jacksum.*; 
import jonelo.jacksum.algorithm.*;

public class Generator {

	URL url;

	
	public String calculateChecksum(String name, String urlId)
	{
		String chck = "";
	
		
		try {
			// get URL content
			url = new URL(urlId);
			URLConnection conn = url.openConnection();
 
			// open the stream and put it into BufferedReader
			BufferedReader br = new BufferedReader(
                               new InputStreamReader(conn.getInputStream()));
 
			String inputLine;
			
			//save to this filename
			String fileName = name;
			File file = new File(fileName);


 
			if (!file.exists()) {
				file.createNewFile();
			}
			
 
			//use FileWriter to write file
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
 
			while ((inputLine = br.readLine()) != null) {
				bw.write(inputLine);
			}
 
			bw.close();
			br.close();
			
			
			
			AbstractChecksum checksum = null; 
			   try { 
			     // select an algorithm (md5 in this case) 
			     checksum = JacksumAPI.getChecksumInstance("crc32"); 
			     // On some systems you get a better performance for particular 
			     // algorithms if you select an alternate algorithm (see also option -A) 
			     // checksum = JacksumAPI.getChecksumInstance("md5", true); 
			   } catch (NoSuchAlgorithmException nsae) { 
			     // algorithm doesn't exist 
			   } 

			   // updates the checksum with the content of a file 
			   try { 
			     checksum.readFile(name); 
			   } catch (IOException ioe) { 
			     // ... 
			   } 
			   
			   chck = checksum.getHexValue();

 
			br.close();

 
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return chck;
		
	}
	
}
