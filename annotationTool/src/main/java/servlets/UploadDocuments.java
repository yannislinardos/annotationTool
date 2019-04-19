package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import beans.Collection;
import beans.Report;
import util.Queries;

/**
 * Servlet implementation class UploadDocuments
 */
@MultipartConfig
public class UploadDocuments extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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



		List<Part> fileParts = request.getParts().stream().filter(part -> "files".equals(part.getName())).collect(Collectors.toList()); // Retrieves <input type="file" name="file" multiple="true">
		Report report = null;
		
		for (Part filePart : fileParts) {
			String fileName = getSubmittedFileName(filePart);
			InputStream fileContent = filePart.getInputStream();
			//	        System.out.println("Report name: " + fileName);
			String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
			//	        System.out.println("Report extension: " + extension);

			String fileText = null;
			if(extension.equals("pdf")) {
				fileText = getText(fileContent);

			} else {
				try {
					fileText = convertStreamToString(fileContent);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}


			report = new Report();
			report.setContent(fileText);
			report.setUploader(username);

			int reportId = Queries.insertReport(report);
			for(int collectionId : collectionIDsToAdd) {
				Queries.insertKeysInReportsCollections(collectionId, reportId);
			}

			System.out.println("Document " + fileName + " with content: " + fileText + ". Uploaded!");
		}

		if (report == null){
			System.out.println("Documents NOT added");
			request.setAttribute("check", false);

		} else{
			System.out.println("Documents added");
			request.setAttribute("check", "Documents added");
		}
		
		request.getRequestDispatcher("upload.jsp").forward(request, response);
	}

	private static String getSubmittedFileName(Part part) {
		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
				return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1); // MSIE fix.
			}
		}
		return null;
	}

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


	public static String getText(InputStream pdfFile) throws IOException {
		PDDocument doc = PDDocument.load(pdfFile);
		return new PDFTextStripper().getText(doc);
	}

}
