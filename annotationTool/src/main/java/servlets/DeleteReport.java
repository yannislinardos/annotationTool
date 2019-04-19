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
public class DeleteReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteReport() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id = request.getParameter("id");
		int numid = Integer.parseInt(id);
		
		Queries.deleteReport(numid);
		
		System.out.println("Report deleted entirely");
		request.setAttribute("check", "Report " + numid + " deleted entirely");
		
		request.getRequestDispatcher("managereports.jsp").forward(request, response);
	}

}
