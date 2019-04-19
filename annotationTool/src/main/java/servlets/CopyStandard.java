package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Standard;
import util.Queries;

/**
 * Servlet implementation class CopyStandard
 */
public class CopyStandard extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CopyStandard() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		int idnum = Integer.parseInt(id);
		
		Standard standard = Queries.getStandard(idnum);
		standard.setName(name + "-copy");
		Queries.insertStandard(standard);
		
		System.out.println("Standard copied");
		request.setAttribute("check", "Standard " + name + " copied");
		
		request.getRequestDispatcher("managestandards.jsp").forward(request, response);
	}

}
