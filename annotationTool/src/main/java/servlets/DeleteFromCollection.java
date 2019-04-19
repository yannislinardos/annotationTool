package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Queries;

/**
 * Servlet implementation class DeleteReport
 */
public class DeleteFromCollection extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteFromCollection() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String collid = request.getParameter("collid");
		int numcollid = Integer.parseInt(collid);
		String reportid = request.getParameter("reportid");
		int numreportid = Integer.parseInt(reportid);
		
		Queries.removeReportFromCollection(numreportid, numcollid);;
		
		System.out.println("Report removed from the collection");
		request.setAttribute("check", "Report " + numreportid + " removed from the collection " + numcollid);
		
		request.getRequestDispatcher("managereports.jsp").forward(request, response);
	}

}
