package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.User;
import util.Password;
import util.Queries;
import util.Queries.Rights;

/**
 * Servlet implementation class CreateUser
 */

public class CreateUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateUser() {
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
		

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String hashPass = Password.hashCode(password);

		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String stringRights = request.getParameter("rights");
		
		Rights rights = stringRights.equals("Administrator") ? Rights.admin : Rights.user;
		
		User user = new User();
		user.setUsername(username);
		user.setEmail(email);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setRights(rights);
		
		User result = Queries.setUser(user, hashPass);
		
		if (result == null){
			System.out.println("User NOT created");
			request.setAttribute("check", false);

		} else{
			System.out.println("User created");
			request.setAttribute("check", "User created with username: " + result.getUsername());
		}
//		request.setAttribute("t", o);
		request.getRequestDispatcher("manageusers.jsp").forward(request, response);
	}

}
