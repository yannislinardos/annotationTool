package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Queries;
import beans.Collection;
import beans.Report;

/**
 * Servlet implementation class UploadText
 */
public class UploadText extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Report report = new Report();
		report.setContent(request.getParameter("content"));
		report.setUploader(request.getParameter("username"));
		String username = request.getParameter("username");
		String collectionname = request.getParameter("collectionname");
		String[] collections = request.getParameterValues("collections");
//		System.out.println(collectionname);
//		System.out.println(collections);

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

		
		int reportId = Queries.insertReport(report);
		for(int collectionId : collectionIDsToAdd) {
			Queries.insertKeysInReportsCollections(collectionId, reportId);
		}
		
		if (report == null){
			System.out.println("Text NOT added");
			request.setAttribute("check", false);

		} else{
			System.out.println("Text added");
			request.setAttribute("check", "Text added");
		}
		
		request.getRequestDispatcher("upload.jsp").forward(request, response);
	}

}
