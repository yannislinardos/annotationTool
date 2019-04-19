package REST;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import beans.Annotated;
import beans.Report;
import beans.User;
import util.LoginDao;
import util.Queries;
import util.Queries.Rights;

@Path("/report")
public class ReportsResource {
	
	@GET
	@Produces({MediaType.TEXT_PLAIN })
	@Path("/content/{id}")
	public String getContent(@CookieParam("token") String token, @CookieParam("adminToken") String adminToken,
			@PathParam("id") int id){
		
		if (adminToken != null) {
			Cookie cookie = new Cookie("adminToken", adminToken);
			User user = LoginDao.getToken(new Cookie[] {cookie});
			
			if (user != null){
				return Queries.getReport(id).getContent();
			} else {
				return null;
			}
		} 
		return null;
	}
	
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/{id}")
	public Report getReport(@CookieParam("token") String token, @CookieParam("adminToken") String adminToken,
			@PathParam("id") int id){
		
		if (adminToken != null) {
			Cookie cookie = new Cookie("adminToken", adminToken);
			User user = LoginDao.getToken(new Cookie[] {cookie});
			
			if (user != null){
				return Queries.getReport(id);
			} else {
				return null;
			}
		} 
		return null;

	}
	
	
	
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public void instertReport(@CookieParam("adminToken") String adminToken, Report report){
		
		if (adminToken != null) {
			Cookie cookie = new Cookie("adminToken", adminToken);
			User user = LoginDao.getToken(new Cookie[] {cookie});
			
			if (user != null){
				if (user.getRights().equals(Rights.admin)) {
					Queries.insertReport(report);
				}
			}	
		} 

	}
	
	@DELETE
	@Path("/{id}")
	@Consumes({MediaType.APPLICATION_JSON})
	public void deleteReport(@CookieParam("adminToken") String adminToken, @PathParam("id") int id){

		if (adminToken != null) {
			Cookie cookie = new Cookie("adminToken", adminToken);
			User user = LoginDao.getToken(new Cookie[] {cookie});
			
			if (user != null){
				if (user.getRights().equals(Rights.admin)) {
					Queries.deleteReport(id);
				}
			}	
		} 
		
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/{id}/annotations")
	public List<Annotated> getAnnotationsProject(@CookieParam("token") String token, @CookieParam("adminToken") String adminToken, @PathParam("id") int report_id) {
		
		
		List<Annotated> annotations = Queries.getAnnotations(report_id);
		
		
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
	
}
