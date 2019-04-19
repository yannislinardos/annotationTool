package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Queries;

/**
 * Servlet implementation class AutomaticAnnotationBatch
 */
public class AutomaticAnnotationBatch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AutomaticAnnotationBatch() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		int project_id = Integer.parseInt(request.getParameter("project_id"));
		
		String[] reportIDs = Queries.getReportsFromProject(project_id);
		
		for (String s : reportIDs) {
			
			int report_id = Integer.parseInt(s);
			if(!Queries.isAutoAnnotated(report_id, project_id)) {
				Queries.automaticallyAnnotate(report_id, project_id);

			}

		}
		
		System.out.println("Automatic annotated all reports");
		request.setAttribute("check", "Automatic annotated all reports");
		request.getRequestDispatcher("projects.jsp").forward(request, response);
	
	}

}