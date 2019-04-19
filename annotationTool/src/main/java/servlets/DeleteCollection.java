package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Queries;

/**
 * Servlet implementation class DeleteCollection
 */
public class DeleteCollection extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteCollection() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id = request.getParameter("id");
		
		int numid = Integer.parseInt(id);
		
		Queries.deleteCollection(numid);
		
		System.out.println("Collection deleted");
		request.setAttribute("check", "Collection " + numid + " deleted");
		
		request.getRequestDispatcher("managereports.jsp").forward(request, response);
	}

}
