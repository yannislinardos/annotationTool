package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import NLP.CreateNLPUser;
import util.Queries;
import util.createAdmin;
import beans.LoginBean;
import util.LoginDao;

/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		////////////////////////////////////////
		if (!Queries.userExists("admin")) {
			createAdmin.main(new String[] {});
		}
		
		if (!Queries.userExists("NLP")) {
			CreateNLPUser.main(new String[] {});
		}
		////////////////////////////////////////////
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		LoginBean bean = new LoginBean();
		bean.setUsername(username);
		bean.setPassword(password);

		boolean status=LoginDao.validate(bean);
		
		if(status){ 
			
			Queries.Rights rights = Queries.getRights(username);
			
			if (rights == Queries.Rights.admin) {
				
				Cookie token = LoginDao.tokenAdminBuilder(username);
				token.setMaxAge(60*60*4);
				response.addCookie(token);
				response.sendRedirect("Profile.jsp");
				
			} else if (rights == Queries.Rights.user) {
				
				Cookie token = LoginDao.tokenBuilder(username);
				token.setMaxAge(60*60*4);
				response.addCookie(token);
				response.sendRedirect("Profile.jsp");
			}
			
			
		} else {  
			//////////////////////////LOGIN FAILED
			request.setAttribute("failed", true);

			request.getRequestDispatcher("Login.jsp").forward(request, response);
		}  
	}

}
