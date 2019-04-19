package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.xml.sax.SAXException;

import beans.Annotated;
import beans.Standard;
import beans.ToXML;
import util.Queries;

/**
 * Servlet implementation class toXML
 */
public class toXMLServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public toXMLServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

//	/**
//	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
//	 */
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
//	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.setContentType("text/html");
		String report = request.getParameter("report1");
		String reportid = request.getParameter("reportid");
		String projectid = request.getParameter("projectid");
		String standardid = request.getParameter("standardid");
		String username = request.getParameter("username");
		String annotationid = request.getParameter("annotationid");
		
		String button = request.getParameter("button");
		
		System.out.println(report);

		
		String xml = "XML not found";
		if(report.contains("<span")){
		//ToXML toXmL = new ToXML(report);
		try {
			xml = ToXML.begin(report);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		} 
		//System.out.println("XML " + xml);
		Annotated annotation = new Annotated();
		annotation.setAnnotation(xml);
		annotation.setAnnotator(username);
		annotation.setHtml(report);
		annotation.setProject_id(Integer.parseInt(projectid));
		annotation.setReport_id(Integer.parseInt(reportid));
		annotation.setStandard_id(Integer.parseInt(standardid));
		if(annotationid.equals("notset")) {
			int annotationpk = Queries.insertAnnotation(annotation);
			annotation.setPk(annotationpk);
			System.out.println("Annotation added");
		} else {
			annotation.setPk(Integer.parseInt(annotationid));
			Queries.modifyAnnotation(annotation);
			System.out.println("Annotation modified");
		}
		
		annotationid = Integer.toString(annotation.getPk());
		Standard s = Queries.getStandard(annotation.getStandard_id());
		standardid = s.getName();
		
		request.setAttribute("check", "Annotation saved");

		if(button.equals("progress")) {
			
//			System.out.println(projectid);
//			System.out.println(reportid);
//			System.out.println(annotationid);		
//			System.out.println(standardid);

			
			request.setAttribute("reportid", reportid);
			request.setAttribute("standard", standardid);
			request.setAttribute("projectid", projectid);
			request.setAttribute("annotationid", annotationid);
			request.getRequestDispatcher("annotating.jsp").forward(request, response);
		} else {	
			request.getRequestDispatcher("projects.jsp").forward(request, response);
		}
	}

}
