package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.opencsv.CSVReader;

import beans.Collection;
import beans.Report;
import util.Queries;

/**
 * Servlet implementation class UploadCsv
 */
@MultipartConfig
public class UploadCsv extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		String comments = request.getParameter("comment");
		String username = request.getParameter("username");
		String collectionname = request.getParameter("collectionname");
		String[] collections = request.getParameterValues("collections");
		
		//////////////////////////////
		List<Integer> collectionIDsToAdd = new ArrayList<Integer>();
		////////////////////////////////////////////////////////
		
		boolean onlyNewCollection = !( collectionname == null || collectionname.equals("") ) && collections == null;  
		boolean onlySelectCollection = ( collectionname == null || collectionname.equals("") ) && collections != null;  
		boolean bothAddSelectCollection = !( collectionname == null || collectionname.equals("") ) && collections != null;  

		
		if(onlySelectCollection) {
			System.out.println("Only collections selected");
			///////////////////////////////////////////
			for(String s : collections) {
				int col_id = Queries.getCollectionID(s);
				System.out.println(s);
				collectionIDsToAdd.add(col_id);
			}
			////////////////////////////////////////////
			
			//TO DO handle the addition of collection names in reports to collection
		} else if(onlyNewCollection) {
			System.out.println("Only collection added");
			//add the collection to collections
			Collection c = new Collection();
		    c.setName(collectionname);
		    c.setUploader(username);
		    
		    /////////////////////////////
		    int col_id = Queries.insertCollection(c);
			//TO DO handle the addition of collection name in reports to collection
		    collectionIDsToAdd.add(col_id);
		    /////////////////////////////////////////////
		} else if(bothAddSelectCollection) {
			System.out.println("Both add and select");
			
			for(String s : collections) {
				int col_id = Queries.getCollectionID(s);
				System.out.println(s);
				collectionIDsToAdd.add(col_id);
			}
			
			//add the collection to collections
			Collection c = new Collection();
		    c.setName(collectionname);
		    c.setUploader(username);
		    
		    /////////////////////////////
		    int col_id = Queries.insertCollection(c);
			//TO DO handle the addition of collection name in reports to collection
		    collectionIDsToAdd.add(col_id);
		    /////////////////////////////////////////////
		} else {
			System.out.println("No action");
		}


		
	    Part filePart = request.getPart("csvfile");
//        String fileName = getSubmittedFileName(filePart);
	    InputStream fileContent = filePart.getInputStream();
	    
	    //insert reports
	    CSVReader reader = new CSVReader(new InputStreamReader(fileContent));
	    String[] tokens;
	    Report report = null;
	    boolean firstrow = true;
        while ((tokens = reader.readNext()) != null) {
        	if(firstrow) {
        		firstrow = false;
        	} else {
        		int id = Integer.parseInt(tokens[0].substring(0, tokens[0].indexOf(";")));
    			String text = tokens[0].substring(tokens[0].indexOf(";") + 1);
//    			System.out.println("ID: " + id);
//    			System.out.println("Text: " + text);
    		    
    			report = new Report();
    			report.setHospital_id(id);
    			report.setContent(text);
    			report.setUploader(username);
    			
    			int reportId = Queries.insertReport(report);
    			for(int collectionId : collectionIDsToAdd) {
    				Queries.insertKeysInReportsCollections(collectionId, reportId);
    			}
        		
        	}    
        }
        reader.close();

      if (report == null){
			System.out.println("Csv NOT added");
			request.setAttribute("check", false);

		} else{
			System.out.println("Csv added");
			request.setAttribute("check", "Csv file added");
		}
		
		request.getRequestDispatcher("upload.jsp").forward(request, response);
	}

//	private static String getSubmittedFileName(Part part) {
//	    for (String cd : part.getHeader("content-disposition").split(";")) {
//	        if (cd.trim().startsWith("filename")) {
//	            String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
//	            return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1); // MSIE fix.
//	        }
//	    }
//	    return null;
//	}
	
	public static String convertStreamToString(InputStream is) throws Exception {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();
	    String line = null;
	    while ((line = reader.readLine()) != null) {
	      sb.append(line + "\n");
	    }
	    is.close();
	    return sb.toString();
	  }
}