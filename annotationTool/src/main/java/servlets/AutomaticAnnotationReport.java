package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Queries;

/**
 * Servlet implementation class AutomaticAnnotationReport
 */
public class AutomaticAnnotationReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AutomaticAnnotationReport() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		int report_id = Integer.parseInt(request.getParameter("report_id"));
		int project_id = Integer.parseInt(request.getParameter("project_id"));
		System.out.println(report_id);
		System.out.println(project_id);

		
		Queries.automaticallyAnnotate(report_id, project_id);
		
		System.out.println("Automatic annotated report");
		request.setAttribute("check", "Automatic annotated report with id: " + report_id);
		request.getRequestDispatcher("projects.jsp").forward(request, response);

	}

}
