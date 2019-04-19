package servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Annotated;
import beans.Standard;
import util.Queries;

/**
 * Servlet implementation class DownloadAnnotations
 */
public class DownloadAnnotations extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DownloadAnnotations() {
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

		String projectid = request.getParameter("projectid");
		int pid = Integer.parseInt(projectid);

		List<Annotated> annotations = Queries.getAnnotationsFromProject(pid);

		response.setContentType("text/csv");
		response.setHeader("Content-Disposition", "attachment; filename=\"annotations-Project" + projectid + ".csv\"");
		try
		{
			OutputStream out = response.getOutputStream();
			String header = "ReportID; Standard; Annotation; Annotator \n";
		    out.write(header.getBytes());
		    // Write the content
		    for(Annotated a : annotations) {
		    	Standard s = Queries.getStandard(a.getStandard_id());
		    	String line=new String(a.getReport_id() + "; " + s.getName()+ "; \"" + a.getAnnotation() + "\"; " + a.getAnnotator() + "\n");
			    out.write(line.toString().getBytes());
		    }
		    out.flush();
		    out.close();
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}

		System.out.println("Annotations downloaded");
		request.setAttribute("check", "Annotations for project: "+ pid +" downloaded");

		request.getRequestDispatcher("projects.jsp");
	}


}

