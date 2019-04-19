package REST;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import util.LoginDao;
import util.Queries;
import util.Queries.Rights;
import beans.Annotated;
import beans.Project;
import beans.Report;
import beans.Standard;
import beans.User;

@Path("/project")
public class ProjectResource {


	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/{project_name}")
	public Project getProject(@CookieParam("token") String token, @CookieParam("adminToken") String adminToken, 
			@PathParam("project_name") String project_name) {
		
		if (adminToken != null) {
			Cookie cookie = new Cookie("adminToken", adminToken);
			User user = LoginDao.getToken(new Cookie[] {cookie});
			
			if (user != null){
				return Queries.getProject(project_name);
			} else {
				return null;
			}
		} 
//		else if (token != null) {
//			Cookie cookie = new Cookie("token", token);
//			User user = LoginDao.getToken(new Cookie[] {cookie});
//			
//			if (user != null){
//				return Queries.getProject(project_name);
//			} else {
//				return null;
//			}
//		}
		return null;
	}
	

	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/all")
	public List<Project> getAllProjects(@CookieParam("token") String token, @CookieParam("adminToken") String adminToken) {
		
		if (adminToken != null) {
			Cookie cookie = new Cookie("adminToken", adminToken);
			User user = LoginDao.getToken(new Cookie[] {cookie});
			
			if (user != null){
				return Queries.getAllProjects();
			} else {
				return null;
			}
		} 
//		else if (token != null) {
//			Cookie cookie = new Cookie("token", token);
//			User user = LoginDao.getToken(new Cookie[] {cookie});
//			
//			if (user != null){
//				return Queries.getAllProjects();
//			} else {
//				return null;
//			}
//		}
		return null;
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/{project_name}/reports")
	public List<Report> getReportsProject(@CookieParam("token") String token, @CookieParam("adminToken") String adminToken, 
			@PathParam("project_name") String project_name) {
		
		List<Report> reports = new ArrayList<>();
		
		int project_id = Queries.getProjectID(project_name);
		String[] reportIDs = Queries.getReportsFromProject(project_id);
		
		for (String s : reportIDs) {
			int id = Integer.parseInt(s);
			reports.add(Queries.getReport(id));
		}
		
		if (adminToken != null) {
			Cookie cookie = new Cookie("adminToken", adminToken);
			User user = LoginDao.getToken(new Cookie[] {cookie});
			
			if (user != null){
				return reports;
			} else {
				return null;
			}
		} 
//		else if (token != null) {
//			Cookie cookie = new Cookie("token", token);
//			User user = LoginDao.getToken(new Cookie[] {cookie});
//			
//			if (user != null){
//				return reports;
//			} else {
//				return null;
//			}
//		}
		return null;
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/{project_name}/reports/{id}")
	public Report getReportsProject(@CookieParam("token") String token, @CookieParam("adminToken") String adminToken, 
			@PathParam("project_name") String project_name, @PathParam("id") String report_id) {
				
		int project_id = Queries.getProjectID(project_name);
		String[] reportIDs = Queries.getReportsFromProject(project_id);
		
		boolean inProject = false;
		for (String s : reportIDs) {
			if (s.equals(report_id)) {
				inProject = true;
				break;
			}
		}
		
		Report ret = null;
		if (inProject){
			ret = Queries.getReport(Integer.parseInt(report_id));
		}
		
		if (adminToken != null) {
			Cookie cookie = new Cookie("adminToken", adminToken);
			User user = LoginDao.getToken(new Cookie[] {cookie});
			
			if (user != null){
				return ret;
			} else {
				return null;
			}
		} 
//		else if (token != null) {
//			Cookie cookie = new Cookie("token", token);
//			User user = LoginDao.getToken(new Cookie[] {cookie});
//			
//			if (user != null){
//				return ret;
//			} else {
//				return null;
//			}
//		}
		return null;
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/{project_name}/reports/{id}/annotations")
	public List<Annotated> getAnnotationsProject(@CookieParam("token") String token, @CookieParam("adminToken") String adminToken, 
			@PathParam("project_name") String project_name, @PathParam("id") int report_id) {
		
		int project_id = Queries.getProjectID(project_name);
		
		List<Annotated> annotations = Queries.getAnnotations(report_id, project_id);
		
		
		if (adminToken != null) {
			Cookie cookie = new Cookie("adminToken", adminToken);
			User user = LoginDao.getToken(new Cookie[] {cookie});
			
			if (user != null){
				return annotations;
			} else {
				return null;
			}
		} 
//		else if (token != null) {
//			Cookie cookie = new Cookie("token", token);
//			User user = LoginDao.getToken(new Cookie[] {cookie});
//			
//			if (user != null){
//				return annotations;
//			} else {
//				return null;
//			}
//		}
		return null;
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/{project_name}/users")
	public List<User> getUsersProject(@CookieParam("token") String token, @CookieParam("adminToken") String adminToken, 
			@PathParam("project_name") String project_name) {
						
		int project_id = Queries.getProjectID(project_name);
		
		String[] usernames = Queries.getUsersFromProject(project_id);
		
		List<User> users = new ArrayList<>();
		
		for (String s : usernames) {
			
			users.add(Queries.getUser(s));
		}
		
		if (adminToken != null) {
			Cookie cookie = new Cookie("adminToken", adminToken);
			User user = LoginDao.getToken(new Cookie[] {cookie});
			
			if (user != null){
				return users;
			} else {
				return null;
			}
		} 
//		else if (token != null) {
//			Cookie cookie = new Cookie("token", token);
//			User user = LoginDao.getToken(new Cookie[] {cookie});
//			
//			if (user != null){
//				return users;
//			} else {
//				return null;
//			}
//		}
		return null;
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/{project_name}/standards")
	public List<Standard> getStandardsProject(@CookieParam("token") String token, @CookieParam("adminToken") String adminToken, 
			@PathParam("project_name") String project_name) {
						
		int project_id = Queries.getProjectID(project_name);
		
		String[] standard_names = Queries.getStandardsFromProject(project_id);
		
		List<Standard> standards = new ArrayList<>();
		
		for (String s : standard_names) {
			
			standards.add(Queries.getStandard(s));
		}
		
		if (adminToken != null) {
			Cookie cookie = new Cookie("adminToken", adminToken);
			User user = LoginDao.getToken(new Cookie[] {cookie});
			
			if (user != null){
				return standards;
			} else {
				return null;
			}
		} 
//		else if (token != null) {
//			Cookie cookie = new Cookie("token", token);
//			User user = LoginDao.getToken(new Cookie[] {cookie});
//			
//			if (user != null){
//				return standards;
//			} else {
//				return null;
//			}
//		}
		return null;
	}
	
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public void instertProject( @CookieParam("adminToken") String adminToken, Project project){
		
		if (adminToken != null) {
			Cookie cookie = new Cookie("adminToken", adminToken);
			User user = LoginDao.getToken(new Cookie[] {cookie});
			
			if (user != null){
				if (user.getRights().equals(Rights.admin)) {
					Queries.insertProject(project);
				}
			}	
		} 
	}
	
	
	@PUT
	@Path("/{old_name}")
	@Consumes({MediaType.APPLICATION_JSON})
	public void modifyProject(@CookieParam("adminToken") String adminToken, @PathParam("old_name") String old_name, 
			Project project){
		
		if (adminToken != null) {
			Cookie cookie = new Cookie("adminToken", adminToken);
			User user = LoginDao.getToken(new Cookie[] {cookie});
			
			if (user != null){
				if (user.getRights().equals(Rights.admin)) {
					Queries.modifyProject(old_name, project);
					Queries.insertCollectionsToProject(project.getName(), project.getCollections());
					Queries.insertReportsToProject(project.getName(), project.getReports());
					Queries.insertStandardToProject(project.getName(), project.getStandards());
					Queries.insertUsersToProject(project.getName(), project.getUsernames());
				}
			}	
		} 
	}
	
	
	
	@DELETE
	@Path("/{name}")
	public void deleteProject(@CookieParam("adminToken") String adminToken, @PathParam("name") String name){
		
		
		if (adminToken != null) {
			Cookie cookie = new Cookie("adminToken", adminToken);
			User user = LoginDao.getToken(new Cookie[] {cookie});
			
			if (user != null){
				if (user.getRights().equals(Rights.admin)) {
					Queries.deleteProject(name);
				}
			}	
		} 

	}

}
