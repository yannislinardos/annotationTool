package servlets;

import java.io.IOException;
import java.util.List;

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
public class ModifyProject extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ModifyProject() {
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
		
		String id = request.getParameter("id");
		String creator = request.getParameter("creator");
		String projectname = request.getParameter("projectName");
		String[] usernames = request.getParameterValues("users");
		String[] standards = request.getParameterValues("standards");
		String[] collections = request.getParameterValues("collections");
		String[] reports = request.getParameterValues("reports");
		String finished = request.getParameter("setfinished");
		for(String r:reports) {
			System.out.println("hahaha" + r);
		}
		System.out.println("IDDD" + id);

		int numid = Integer.parseInt(id);
		Project already = Queries.getProject(numid);
		System.out.println("NUMMMMIDDD" + numid);

		
		System.out.println("lala" + already.getPk());
		
		System.out.println("lala" + already.getName());


		
		if(finished.contentEquals("true")) {
			Queries.changeProjectStatus(projectname, true);
		} else {
			Queries.changeProjectStatus(projectname, false);
		}
		
		for(String alr : already.getUsernames()) {
			Queries.removeUserFromProject(alr, already.getName());
		}
		Queries.insertUsersToProject(already.getName(), usernames);
		
		for(String alr : already.getStandards()) {
			Queries.removeStandardFromProject(alr, already.getName());
		}
		Queries.insertStandardToProject(already.getName(), standards);

		for(String alr : already.getReports()) {
			Queries.removeReportFromProject(alr, already.getName());
		}
		Queries.insertReportsToProject(already.getName(), reports);
		
		if(collections != null) {
			Queries.insertCollectionsToProject(already.getName(), collections);
		}
		
		Queries.changeProjectName(numid, projectname);
		Queries.changeProjectCreator(numid, creator);
		

//		Project project = new Project();
//		project.setName(projectname);
//		project.setCreator(creator);
//		project.setUsernames(usernames);
//		project.setStandards(standards);
//		project.setCollections(collections);
//		project.setReports(reports);
//
//		Queries.insertProject(project);
//		
		
		System.out.println("Project modified");
		request.setAttribute("check", "Project " + id + " modified");

		request.getRequestDispatcher("manageprojects.jsp").forward(request, response);
	}


}
