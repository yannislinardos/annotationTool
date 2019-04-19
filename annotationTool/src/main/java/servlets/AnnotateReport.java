package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AnnotateReport
 */
public class AnnotateReport extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AnnotateReport() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String projectid = request.getParameter("project");
		String reportid = request.getParameter("report");
		String annotationid = request.getParameter("annotation");
		String standard = request.getParameter("standard");
		//		String[] std = standards.split("-split-");
		//		for(String s : std) {
		//			System.out.println(s);
		//		}
		//		System.out.println(reportid);

//		System.out.println(projectid);
//		System.out.println(reportid);
//		System.out.println(annotationid);		
//		System.out.println(standard);

		request.setAttribute("reportid", reportid);
		request.setAttribute("standard", standard);
		request.setAttribute("projectid", projectid);
		request.setAttribute("annotationid", annotationid);

		request.getRequestDispatcher("annotating.jsp").forward(request, response);	
	}
}
