package REST;

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

import beans.Annotated;
import beans.Standard;
import beans.User;
import util.LoginDao;
import util.Queries;
import util.Queries.Rights;

@Path("/annotation")
public class AnnotationResource {
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/{id}")
	public Annotated getAnnotation(@CookieParam("token") String token, @CookieParam("adminToken") String adminToken, 
			@PathParam("id") int id){

		if (adminToken != null) {
			Cookie cookie = new Cookie("adminToken", adminToken);
			User user = LoginDao.getToken(new Cookie[] {cookie});
			
			if (user != null){
				return Queries.getAnnotated(id);
			} else {
				return null;
			}
		} 
//		else if (token != null) {
//			Cookie cookie = new Cookie("token", token);
//			User user = LoginDao.getToken(new Cookie[] {cookie});
//			
//			if (user != null){
//				return Queries.getAnnotated(id);
//			} else {
//				return null;
//			}
//		}
		return null;
		
	}
	
	
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public void instertAnnotation( @CookieParam("adminToken") String adminToken, Annotated annotation){
		
		if (adminToken != null) {
			Cookie cookie = new Cookie("adminToken", adminToken);
			User user = LoginDao.getToken(new Cookie[] {cookie});
			
			if (user != null){
				if (user.getRights().equals(Rights.admin)) {
					Queries.insertAnnotation(annotation);
				}
			}	
		} 
	}
	
	
	@PUT
	@Consumes({MediaType.APPLICATION_JSON})
	public void modifyAnnotation(@CookieParam("adminToken") String adminToken, Annotated annotated){
		
		if (adminToken != null) {
			Cookie cookie = new Cookie("adminToken", adminToken);
			User user = LoginDao.getToken(new Cookie[] {cookie});
			
			if (user != null){
				if (user.getRights().equals(Rights.admin)) {
					Queries.modifyAnnotation(annotated);
				}
			}	
		} 
	}
	
	
	
	@DELETE
	@Path("/{id}")
	public void deleteAnnotation(@CookieParam("adminToken") String adminToken, @PathParam("id") int id){
		
		
		if (adminToken != null) {
			Cookie cookie = new Cookie("adminToken", adminToken);
			User user = LoginDao.getToken(new Cookie[] {cookie});
			
			if (user != null){
				if (user.getRights().equals(Rights.admin)) {
					Queries.deleteAnnotation(id);
				}
			}	
		} 

	}
	

}
