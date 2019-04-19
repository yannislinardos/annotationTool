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
public class DeleteAnnotation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteAnnotation() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id = request.getParameter("annotationid");
		int numid = Integer.parseInt(id);
		
		Queries.deleteAnnotation(numid);
		
		System.out.println("Annotation deleted");
		request.setAttribute("check", "Annotation " + numid + " deleted");
		
		request.getRequestDispatcher("projects.jsp").forward(request, response);
	}

}
