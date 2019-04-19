package REST;

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

import beans.Standard;
import beans.User;
import util.LoginDao;
import util.Queries;
import util.Queries.Rights;

@Path("/standard")
public class StandardResource {
	

	@GET
	@Produces({MediaType.TEXT_PLAIN })
	@Path("/json/{name}")
	public String getStandardJson(@CookieParam("token") String token, @CookieParam("adminToken") String adminToken, 
			@PathParam("name") String name){

		if (adminToken != null) {
			Cookie cookie = new Cookie("adminToken", adminToken);
			User user = LoginDao.getToken(new Cookie[] {cookie});
			
			if (user != null){
				return Queries.getStandard(name).getJson();
			} else {
				return null;
			}
		} 
//		else if (token != null) {
//			Cookie cookie = new Cookie("token", token);
//			User user = LoginDao.getToken(new Cookie[] {cookie});
//			
//			if (user != null){
//				return Queries.getStandard(name).getJson();
//			} else {
//				return null;
//			}
//		}
		return null;

	}
	
	
	@GET
	@Produces({MediaType.APPLICATION_XML })
	@Path("/{name}")
	public Standard getStandard(@CookieParam("token") String token, @CookieParam("adminToken") String adminToken, 
			@PathParam("name") String name){

		if (adminToken != null) {
			Cookie cookie = new Cookie("adminToken", adminToken);
			User user = LoginDao.getToken(new Cookie[] {cookie});
			
			if (user != null){
				return Queries.getStandard(name);
			} else {
				return null;
			}
		} 
//		else if (token != null) {
//			Cookie cookie = new Cookie("token", token);
//			User user = LoginDao.getToken(new Cookie[] {cookie});
//			
//			if (user != null){
//				return Queries.getStandard(name);
//			} else {
//				return null;
//			}
//		}
		return null;
		
	}
	
	
	@GET
	@Produces({MediaType.APPLICATION_XML })
	@Path("/all")
	public List<Standard> getAllStandard(@CookieParam("token") String token, @CookieParam("adminToken") String adminToken){
		
		if (adminToken != null) {
			Cookie cookie = new Cookie("adminToken", adminToken);
			User user = LoginDao.getToken(new Cookie[] {cookie});
			
			if (user != null){
				return Queries.getAllStandards();
			} else {
				return null;
			}
		} 
//		else if (token != null) {
//			Cookie cookie = new Cookie("token", token);
//			User user = LoginDao.getToken(new Cookie[] {cookie});
//			
//			if (user != null){
//				return Queries.getAllStandards();
//			} else {
//				return null;
//			}
//		}
		return null;
	}
	
	
	
	@POST
	@Consumes({MediaType.APPLICATION_XML})
	public void instertStandard( @CookieParam("adminToken") String adminToken, Standard standard){
		
		if (adminToken != null) {
			Cookie cookie = new Cookie("adminToken", adminToken);
			User user = LoginDao.getToken(new Cookie[] {cookie});
			
			if (user != null){
				if (user.getRights().equals(Rights.admin)) {
					Queries.insertStandard(standard);
				}
			}	
		} 
	}
	
	
	@PUT
	@Path("/{old_name}")
	@Consumes({MediaType.APPLICATION_XML})
	public void modifyStandard(@CookieParam("adminToken") String adminToken, @PathParam("old_name") String old_name, 
			Standard standard){
		
		if (adminToken != null) {
			Cookie cookie = new Cookie("adminToken", adminToken);
			User user = LoginDao.getToken(new Cookie[] {cookie});
			
			if (user != null){
				if (user.getRights().equals(Rights.admin)) {
					Queries.modifyStandard(old_name, standard.getName(), standard.getJson());
				}
			}	
		} 
	}
	
	
	
	@DELETE
	@Path("/{name}")
	public void deleteStandard(@CookieParam("adminToken") String adminToken, @PathParam("name") String name){
		
		
		if (adminToken != null) {
			Cookie cookie = new Cookie("adminToken", adminToken);
			User user = LoginDao.getToken(new Cookie[] {cookie});
			
			if (user != null){
				if (user.getRights().equals(Rights.admin)) {
					Queries.deleteStandard(name);
				}
			}	
		} 

	}
	

}
