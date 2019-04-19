package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Project;
import beans.User;
import util.Password;
import util.Queries;
import util.Queries.Rights;

/**
 * Servlet implementation class CreateProject
 */
public class CreateProject extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateProject() {
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

		String creator = request.getParameter("creator");
		String projectname = request.getParameter("projectName");
		String[] usernames = request.getParameterValues("users");
		String[] standards = request.getParameterValues("standards");
		String[] collections = request.getParameterValues("collections");
		String[] reports = request.getParameterValues("reports");

		if(collections == null) {
			collections = new String[]{};
		}
		if(reports == null) {
			reports = new String[]{};
		}
		Project project = new Project();

		project.setName(projectname);
		project.setCreator(creator);
		project.setUsernames(usernames);
		project.setStandards(standards);
		project.setCollections(collections);
		project.setReports(reports);

		int id = Queries.insertProject(project);
		
		if (id == -1){
			System.out.println("Project NOT created");
			request.setAttribute("check", false);
		} else {
			System.out.println("Project created");
			request.setAttribute("check", "Project created with id: " + id);	
		}

		request.getRequestDispatcher("manageprojects.jsp").forward(request, response);
	}


}
