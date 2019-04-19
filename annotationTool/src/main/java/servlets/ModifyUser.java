package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Password;
import util.Queries;
import util.Queries.Rights;
import beans.User;

/**
 * Servlet implementation class ModifyUser
 */
public class ModifyUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyUser() {
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
		Rights rights = request.getParameter("rights").equals("user") ? Rights.user : Rights.admin;
				
		User user = new User();
		user.setUsername(username);
		user.setEmail(email);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setRights(rights);
		
		User result = Queries.modifyUser(user, hashPass);
		
		if (result == null){
			System.out.println("User NOT modified");
			request.setAttribute("check", false);

		} else{
			System.out.println("User modified");
			request.setAttribute("check", "User " + result.getUsername() + " modified");
		}
		
		request.getRequestDispatcher("manageusers.jsp").forward(request, response);
	}
		
	

}
