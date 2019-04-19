package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.LoginDao;
import util.Password;
import util.Queries;
import beans.User;

/**
 * Servlet implementation class ModifyMyself
 */
public class ModifyMyself extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyMyself() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String hashPass = null;
		if (password != ""){
			hashPass = Password.hashCode(password);
		}
		
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
				
		User user = new User();
		user.setUsername(username);
		user.setEmail(email);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		
		User result = Queries.modifyMyself(user, hashPass);
		
		
		if (hashPass != null) {
			
			Queries.Rights rights = Queries.getRights(username);
				
				if (rights == Queries.Rights.admin) {
					
					Cookie token = LoginDao.tokenAdminBuilder(username);
					token.setMaxAge(60*60*4);
					response.addCookie(token);
					
				} else if (rights == Queries.Rights.user) {
					
					Cookie token = LoginDao.tokenBuilder(username);
					token.setMaxAge(60*60*4);
					response.addCookie(token);
				}
				
			
		}
		
		if (result == null){
			request.setAttribute("check", false);
			System.out.println("Modify NOT myself");
		} else{
			request.setAttribute("check", "My user modified");
			System.out.println("Modify myself");

		}
		
		request.getRequestDispatcher("Profile.jsp").forward(request, response);
	}
		
	

}
