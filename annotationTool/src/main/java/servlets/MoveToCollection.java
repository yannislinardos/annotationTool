package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Queries;

/**
 * Servlet implementation class DeleteCollection
 */
public class MoveToCollection extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MoveToCollection() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String[] collections = request.getParameterValues("collections");
		String id = request.getParameter("reportid");
		int numid = Integer.parseInt(id);
		
		for(String coll : collections) {
			int collid = Queries.getCollectionID(coll);
			Queries.addReportToCollection(numid, collid);
		}
		
		String collidd = request.getParameter("collid");
		if(!collidd.equals("")) {
			int collidnum = Integer.parseInt(collidd);
			Queries.removeReportFromCollection(numid, collidnum);
		}

				
		System.out.println("Report moved");
		request.setAttribute("check", "Report "+ id +" moved");
		
		request.getRequestDispatcher("managereports.jsp").forward(request, response);
	}

}
