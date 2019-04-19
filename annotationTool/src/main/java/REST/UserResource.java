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

import beans.Project;
import beans.User;
import beans.UserForRest;
import util.LoginDao;
import util.Password;
import util.Queries;
import util.Queries.Rights;

@Path("/user")
public class UserResource {
	

	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/{username}")
	public User getUser(@CookieParam("token") String token, @CookieParam("adminToken") String adminToken, 
			@PathParam("username") String username) {
		
		if (adminToken != null) {
			Cookie cookie = new Cookie("adminToken", adminToken);
			User user = LoginDao.getToken(new Cookie[] {cookie});
			
			if (user != null){
				return Queries.getUser(username);
			} else {
				return null;
			}
		} 
//		else if (token != null) {
//			Cookie cookie = new Cookie("token", token);
//			User user = LoginDao.getToken(new Cookie[] {cookie});
//			
//			if (user != null){
//				return Queries.getUser(username);
//			} else {
//				return null;
//			}
//		}
		return null;
	}
	
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/all")
	public List<User> getAllUsers(@CookieParam("token") String token, @CookieParam("adminToken") String adminToken){
		
		if (adminToken != null) {
			Cookie cookie = new Cookie("adminToken", adminToken);
			User user = LoginDao.getToken(new Cookie[] {cookie});
			
			if (user != null){
				return Queries.getAllUsers();
			} else {
				return null;
			}
		}
//		else if (token != null) {
//			Cookie cookie = new Cookie("token", token);
//			User user = LoginDao.getToken(new Cookie[] {cookie});
//			
//			if (user != null){
//				return Queries.getAllUsers();
//			} else {
//				return null;
//			}
//		}
		return null;
	}
	
	

	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/{username}/projects")
	public List<Project> getProjects(@CookieParam("token") String token, @CookieParam("adminToken") String adminToken, 
			@PathParam("username") String username){
		
		if (adminToken != null) {
			Cookie cookie = new Cookie("adminToken", adminToken);
			User user = LoginDao.getToken(new Cookie[] {cookie});
			
			if (user != null){
				return Queries.getProjectsFromUser(username);
			} else {
				return null;
			}
		} 
//		else if (token != null) {
//			Cookie cookie = new Cookie("token", token);
//			User user = LoginDao.getToken(new Cookie[] {cookie});
//			
//			if (user != null){
//				return Queries.getProjectsFromUser(username);
//			} else {
//				return null;
//			}
//		}
		return null;
	}
		
	
	
	@PUT
	@Consumes({MediaType.APPLICATION_JSON})
	public void modifyUser(@CookieParam("adminToken") String adminToken,UserForRest userForRest){
		
		User user = userForRest.getUser();
		String password = userForRest.getPassword();
		
		String hashPass = null;
		
		if (password != ""){
			hashPass = Password.hashCode(password);
		}
		
		if (adminToken != null) {
			Cookie cookie = new Cookie("adminToken", adminToken);
			User admin = LoginDao.getToken(new Cookie[] {cookie});
			
			if (admin != null){
				if (admin.getRights().equals(Rights.admin)) {
					Queries.modifyUser(user, hashPass);
				}
			}	
		} 
	}
	

	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public void insertUser( @CookieParam("adminToken") String adminToken,UserForRest userForRest){
		
		User user = userForRest.getUser();
		String password = userForRest.getPassword();
		
		
	String hashPass = null;
		
		if (password != ""){
			hashPass = Password.hashCode(password);
		}
		
		if (adminToken != null) {
			Cookie cookie = new Cookie("adminToken", adminToken);
			User admin = LoginDao.getToken(new Cookie[] {cookie});
			
			if (admin != null){
				if (admin.getRights().equals(Rights.admin)) {
					Queries.setUser(user, hashPass);
				}
			}	
		} 
	}
	
	@DELETE
	@Path("/{username}")
	public void deleteUser(@CookieParam("adminToken") String adminToken, @PathParam("username") String username){
		
		
		if (adminToken != null) {
			Cookie cookie = new Cookie("adminToken", adminToken);
			User user = LoginDao.getToken(new Cookie[] {cookie});
			
			if (user != null){
				if (user.getRights().equals(Rights.admin)) {
					Queries.deleteUser(username);
				}
			}	
		} 

	}
	

	
}
