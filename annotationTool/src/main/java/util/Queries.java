package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import NLP.CallPython;
import beans.*;
import database.*;

public class Queries {

	public enum Rights {admin, user};

	public static String sanitize(String input) {

		if (input == null) {
			input = "";
		}

		String safe = Jsoup.clean(input, Whitelist.basic());

		return safe;
	}


	public static boolean userExists(String input) {
		String username = input;
		boolean ret = false;
		PreparedStatement ps = null;
		ResultSet rs = null;

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();

		try {

			ps = con.prepareStatement(  
					"select * from users where username=?");

			ps.setString(1,username);  

			rs=ps.executeQuery();  

			ret = rs.next();  
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) { /* ignored */}
			}
		}

		return ret;
	}

	public static String getPassword(String input) {
		String username = input;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String hashPassword = null;
		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();


		if (!userExists(username)) {
			return null;
		}

		else {
			try {

				ps = con.prepareStatement(  
						"select password from users where username=?");

				ps.setString(1,username);  

				rs = ps.executeQuery();  

				rs.next();
				hashPassword = rs.getString("password");

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException e) { /* ignored */}
				}
				if (ps != null) {
					try {
						ps.close();
					} catch (SQLException e) { /* ignored */}
				}
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) { /* ignored */}
				}
			}
			return hashPassword;
		}

	}

	public static Rights getRights(String input) {
		String username = input;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String stringRights = null;
		Rights rights = null;
		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();


		if (!userExists(username)) {
			return null;
		}

		else {
			try {

				ps = con.prepareStatement(  
						"select rights from users where username=?");

				ps.setString(1,username);  

				rs = ps.executeQuery();  

				rs.next();
				stringRights = rs.getString("rights");

				if(stringRights.equals("admin")){
					rights = Rights.admin;
				} else if (stringRights.equals("user")) {
					rights = Rights.user;
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException e) { /* ignored */}
				}
				if (ps != null) {
					try {
						ps.close();
					} catch (SQLException e) { /* ignored */}
				}
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) { /* ignored */}
				}
			}
			return rights;
		} 	
	}


	public static User getUser(String input) {
		String username = input;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String stringRights = null;
		Rights rights = null;
		String firstName = null;
		String lastName = null;
		String email = null;
		int id = -1;

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();


		if (!userExists(username)) {
			return null;
		}

		else {
			try {

				ps = con.prepareStatement(  
						"select * from users where username=?");

				ps.setString(1,username);  

				rs = ps.executeQuery();  

				rs.next();
				stringRights = rs.getString("rights");
				firstName = rs.getString("firstName");
				lastName = rs.getString("lastName");
				email = rs.getString("email");
				id = rs.getInt("user_id");

				if(stringRights.equals("admin")){
					rights = Rights.admin;
				} else if (stringRights.equals("user")) {
					rights = Rights.user;
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException e) { /* ignored */}
				}
				if (ps != null) {
					try {
						ps.close();
					} catch (SQLException e) { /* ignored */}
				}
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) { /* ignored */}
				}
			}

			User user = new User();
			user.setUsername(username);
			user.setEmail(email);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setRights(rights);
			user.setId(id);


			return user;
		}

	}

	public static User setUser(User user, String hashPass) {

		PreparedStatement ps = null;
		ResultSet rs = null;

		String username = sanitize(user.getUsername());

		Rights rights = user.getRights();
		String firstName = sanitize(user.getFirstName());
		String lastName = sanitize(user.getLastName());
		String email = sanitize(user.getEmail());

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();

		String query =  "INSERT INTO AnnotationTool.users (username, password, rights, firstname, lastname, email) "
				+ "VALUES (?,?,?::enumRights,?,?, ?);";

		if (userExists(username)) {
			return null;
		}

		else {
			try {

				ps = con.prepareStatement(query);

				ps.setString(1,username);  
				ps.setString(2,hashPass); 
				ps.setString(3,rights.toString()); 
				ps.setString(4,firstName); 
				ps.setString(5,lastName);
				ps.setString(6,email); 

				ps.executeUpdate(); 


			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException e) { /* ignored */}
				}
				if (ps != null) {
					try {
						ps.close();
					} catch (SQLException e) { /* ignored */}
				}
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) { /* ignored */}
				}
			}	

		}	
		return user;
	}
	
	

	public static User modifyMyself(User user, String hashPass) {

		PreparedStatement ps = null;
		ResultSet rs = null;

		String username = sanitize(user.getUsername());

		String firstName = sanitize(user.getFirstName());
		String lastName = sanitize(user.getLastName());
		String email = sanitize(user.getEmail());

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();

		if (!userExists(username)) {
			return null;
		}

		else {
			try {

				if (hashPass != null){
					String query =  "UPDATE AnnotationTool.users "
							+ "SET  password = ?,"
							+ "firstname = ?,"
							+ "lastname = ?,"
							+ "email = ?"
							+ " WHERE username = ?;";

					ps = con.prepareStatement(query);

					ps.setString(1,hashPass); 
					ps.setString(2,firstName); 
					ps.setString(3,lastName);
					ps.setString(4,email); 
					ps.setString(5,username);

					ps.executeUpdate(); 
				} else {
					String query =  "UPDATE AnnotationTool.users "
							+ "SET firstname = ?,"
							+ "lastname = ?,"
							+ "email = ?"
							+ " WHERE username = ?;";

					ps = con.prepareStatement(query);

					ps.setString(1,firstName); 
					ps.setString(2,lastName);
					ps.setString(3,email); 
					ps.setString(4,username);

					ps.executeUpdate(); 
				}


			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException e) { /* ignored */}
				}
				if (ps != null) {
					try {
						ps.close();
					} catch (SQLException e) { /* ignored */}
				}
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) { /* ignored */}
				}
			}	

		}	
		return user;
	}

	
	public static User modifyUser(User user, String hashPass) {

		PreparedStatement ps = null;
		ResultSet rs = null;

		String username = sanitize(user.getUsername());
		String firstName = sanitize(user.getFirstName());
		String lastName = sanitize(user.getLastName());
		String email = sanitize(user.getEmail());
		Rights rights = user.getRights();

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();

		if (!userExists(username)) {
			return null;
		}

		else {
			try {

				if (hashPass != null){
					String query =  "UPDATE AnnotationTool.users "
							+ "SET  password = ?,"
							+ "firstname = ?,"
							+ "lastname = ?,"
							+ "email = ?,"
							+ "rights = ?::enumrights"
							+ " WHERE username = ?;";

					ps = con.prepareStatement(query);

					ps.setString(1,hashPass); 
					ps.setString(2,firstName); 
					ps.setString(3,lastName);
					ps.setString(4,email); 
					ps.setString(5, rights == Rights.user ? "user" : "admin");
					ps.setString(6,username);

					ps.executeUpdate(); 
				} else {
					String query =  "UPDATE AnnotationTool.users "
							+ "SET firstname = ?,"
							+ "lastname = ?,"
							+ "email = ?,"
							+ "rights = ?::enumrights"
							+ " WHERE username = ?;";

					ps = con.prepareStatement(query);

					ps.setString(1,firstName); 
					ps.setString(2,lastName);
					ps.setString(3,email); 
					ps.setString(4, rights == Rights.user ? "user" : "admin");
					ps.setString(5,username);

					ps.executeUpdate(); 
				}


			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException e) { /* ignored */}
				}
				if (ps != null) {
					try {
						ps.close();
					} catch (SQLException e) { /* ignored */}
				}
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) { /* ignored */}
				}
			}	

		}	
		return user;
	}



	public static List<User> getAllUsers(){

		List<User> users = new ArrayList<User>();

		PreparedStatement ps = null;
		ResultSet results = null;

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();

		String query = "select * from AnnotationTool.users;";

		try {
			ps = con.prepareStatement(query);

			results = ps.executeQuery();

			while(results.next()){

				User user = new User();

				user.setUsername(results.getString("username"));
				user.setFirstName(results.getString("firstname"));
				user.setLastName(results.getString("lastname"));
				user.setEmail(results.getString("email"));
				user.setRights(results.getString("rights").equals("user") ? Rights.user : Rights.admin);

				users.add(user);

			}


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (results != null) {
				try {
					results.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) { /* ignored */}
			}
		}
		return users;

	}
	
	
	public static int getCollectionID(String collectionName){

		int id = -1;

		String collname = collectionName;

		PreparedStatement ps = null;
		ResultSet results = null;

		String query = "select collection_id from AnnotationTool.collection where name = ?;";

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();

		try {
			ps = con.prepareStatement(query);

			ps.setString(1,collname);

			results = ps.executeQuery();

			results.next();

			id = results.getInt("collection_id");


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (results != null) {
				try {
					results.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) { /* ignored */}
			}
		}

		return id;
	}
	
	
	public static List<Integer> getCollectionID(String[] collectionNames){

		List<Integer> collectionIDs = new ArrayList<>();

		for (String name : collectionNames){
			
			collectionIDs.add(getCollectionID(name));
			
		}
		
		return collectionIDs;
	}


	public static List<Integer> getReportIDs(int collectionID){

		List<Integer> reportIDs = new ArrayList<Integer>();

		PreparedStatement ps = null;
		ResultSet results = null;

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();

		String query = "select * from AnnotationTool.reports_to_collections where collection_id = ?;" ;


		try {
			ps = con.prepareStatement(query);

			ps.setInt(1, collectionID);

			results = ps.executeQuery();

			while(results.next()){
				reportIDs.add(results.getInt("report_id"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (results != null) {
				try {
					results.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) { /* ignored */}
			}
		}

		return reportIDs;

	}
	
	
	public static List<Integer> getReportIDs(int[] collectionIDs){

		List<Integer> reportIDs = new ArrayList<Integer>();

		for (int coll_id : collectionIDs) {
			
				List<Integer> new_ids = getReportIDs(coll_id);
				
				for (int x : new_ids){
					   if (!reportIDs.contains(x))
						   reportIDs.add(x);
					}
		}

		return reportIDs;

	}
	
	public static int[] convertIntegers(List<Integer> integers)
	{
	    int[] ret = new int[integers.size()];
	    for (int i=0; i < ret.length; i++)
	    {
	        ret[i] = integers.get(i).intValue();
	    }
	    return ret;
	}
	
	
	public static List<Integer> getReportIDs(String[] collection_names){
		
		return getReportIDs(convertIntegers(getCollectionID(collection_names)));

	}
	

	public static Report getReport(int id){
		Report report = new Report();

		String query = "select * from AnnotationTool.reports where reports_pk = ?;" ;

		PreparedStatement ps = null;
		ResultSet results = null;

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();

		try {
			ps = con.prepareStatement(query);

			ps.setInt(1,id);

			results = ps.executeQuery();
			
			results.next();
			
			report.setPk(results.getInt("reports_pk"));
			report.setContent(results.getString("report_content"));
			report.setDate(results.getString("date"));
			report.setHospital_id(results.getInt("hospital_report_id"));
			report.setUploader(results.getString("uploader"));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (results != null) {
				try {
					results.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) { /* ignored */}
			}
		}


		return report;
	}

	
	public static List<Report> getAllReportsWithoutCollection(){

		List<Report> reports = new ArrayList<Report>();

		String query = "SELECT * FROM   AnnotationTool.reports l" 
				+" WHERE  NOT EXISTS (" 
				+" SELECT" 
				+" FROM AnnotationTool.reports_to_collections"
				+" WHERE  report_id = l.reports_pk);";		

		PreparedStatement ps = null;
		ResultSet rs = null;

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();

		try {
			ps = con.prepareStatement(query);

			rs=ps.executeQuery();  

			while (rs.next()) {

				Report report = new Report();

				report.setPk(rs.getInt("reports_pk"));
				report.setUploader(rs.getString("uploader"));
				report.setDate(rs.getString("date"));
				report.setContent(rs.getString("report_content"));
				report.setHospital_id(rs.getInt("hospital_report_id"));

				reports.add(report);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) { /* ignored */}
			}
		}


		return reports;

	}
	

	public static List<Collection> getAllCollections(){

		List<Collection> collections = new ArrayList<Collection>();

		PreparedStatement ps = null;
		ResultSet results = null;

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();

		String query = "select * from AnnotationTool.collection;";

		try {
			ps = con.prepareStatement(query);

			results = ps.executeQuery();

			while(results.next()){

				Collection collection = new Collection();

				collection.setId(results.getInt("collection_id"));
				collection.setName(results.getString("name"));
				collection.setUploader(results.getString("uploader"));

				collections.add(collection);

			}


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (results != null) {
				try {
					results.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) { /* ignored */}
			}
		}

		return collections;

	}

	
	public static void deleteUser(String username){

		PreparedStatement ps = null;

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();

		String query = "DELETE FROM AnnotationTool.users WHERE username = ?;";

		try {
			ps = con.prepareStatement(query);

			ps.setString(1,username);

			ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) { /* ignored */}
			}
		}

	}

	public static int insertReport(Report report) {

		int id = -1;

		PreparedStatement ps = null;
		ResultSet rs = null;

		int hospital_id = report.getHospital_id();
		String uploader = sanitize(report.getUploader());
		String content = sanitize(report.getContent());

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();

		String query_report =  "INSERT INTO AnnotationTool.reports (hospital_report_id, uploader, date, "
				+ "report_content) "
				+ "VALUES (?,?,CURRENT_TIMESTAMP,?);";

		try {

			ps = con.prepareStatement(query_report, Statement.RETURN_GENERATED_KEYS);

			ps.setInt(1,hospital_id);  
			ps.setString(2,uploader); 
			ps.setString(3,content); 

			ps.executeUpdate(); 

			rs = ps.getGeneratedKeys();

			rs.next();

			id = rs.getInt("reports_pk");


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) { /* ignored */}
			}
		}	

		return id;
	}

	public static int insertCollection(Collection collection) {

		int id = -1;

		PreparedStatement ps = null;
		ResultSet rs = null;

		String uploader = sanitize(collection.getUploader());
		String name = sanitize(collection.getName());

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();

		String query =  "INSERT INTO AnnotationTool.collection (name, uploader, date) "
				+ "VALUES (?,?,CURRENT_TIMESTAMP);";

		try {

			ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			ps.setString(1,name); 
			ps.setString(2,uploader); 

			ps.executeUpdate(); 

			rs = ps.getGeneratedKeys();

			rs.next();

			id = rs.getInt(1);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) { /* ignored */}
			}
		}	

		return id;
	}


	public static void deleteReport(int id) {
		
		String query = "delete from AnnotationTool.reports"
				+ " where reports_pk = ?;";
		
		PreparedStatement ps = null;

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();
		
		
		try {
			ps = con.prepareStatement(query);
			
			ps.setInt(1, id);
			
			ps.executeUpdate(); 
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) { /* ignored */}
			}
		}	
		
	}
	
	
	public static void deleteCollection(int id) {
		
		String query = "delete from AnnotationTool.collection"
				+ " where collection_id = ?;";
		
		PreparedStatement ps = null;

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();
		
		
		try {
			ps = con.prepareStatement(query);
			
			ps.setInt(1, id);
			
			ps.executeUpdate(); 
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) { /* ignored */}
			}
		}	
		
	}
	
	
	public static void removeReportFromCollection(int report_id, int collection_id) {
			
			String query = "delete from AnnotationTool.reports_to_collections"
					+ " where collection_id = ? and report_id = ?;";
			
			PreparedStatement ps = null;
	
			DBConnection DBCon = new DBConnection();
			Connection con = DBCon.getCon();
			
			
			try {
				ps = con.prepareStatement(query);
				
				ps.setInt(1, collection_id);
				ps.setInt(2, report_id);
				
				ps.executeUpdate(); 
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (ps != null) {
					try {
						ps.close();
					} catch (SQLException e) { /* ignored */}
				}
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) { /* ignored */}
				}
			}	
			
		}
	
		
	public static void addReportToCollection(int report_id, int collection_id) {
		
		String query = "insert into AnnotationTool.reports_to_collections (collection_id, report_id) "
					+ "VALUES (?,?);";
		
		PreparedStatement ps = null;
	
		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();
		
		
		try {
			ps = con.prepareStatement(query);
			
			ps.setInt(1, collection_id);
			ps.setInt(2, report_id);
			
			ps.executeUpdate(); 
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) { /* ignored */}
			}
		}	
		
	}
	
	
	public static void moveFromCollection(int report_id, int old_col, int new_col) {
		
		addReportToCollection(report_id, new_col);
		
		removeReportFromCollection(report_id, old_col);
		
	}
	

	public static int insertProjectInProjects(Project project) {
		int id = -1;
		PreparedStatement ps = null;
		ResultSet results = null;

		String projectname = sanitize(project.getName());
		String creator = sanitize(project.getCreator());

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();

		String query = "insert into AnnotationTool.projects (name, creator, date) "
				+ "VALUES (?,?, CURRENT_TIMESTAMP);";

		try {
				
			ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			ps.setString(1,projectname);
			ps.setString(2,creator);

			ps.executeUpdate(); 
			
			results = ps.getGeneratedKeys();

			results.next();

			id = results.getInt("project_pk");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (results != null) {
				try {
					results.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) { /* ignored */}
			}
		}
		
		return id;
		
	}
	
	
	public static int getProjectID (String name) {
		int id = -1;
		
		PreparedStatement ps = null;
		ResultSet results = null;

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();

		String query = "select project_pk from AnnotationTool.projects "
				+ "WHERE name = ?;";

		try {

			ps = con.prepareStatement(query);

			ps.setString(1,name);

			results = ps.executeQuery(); 
			
			
			if (results.next()) {
				id = results.getInt("project_pk");

			}
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (results != null) {
				try {
					results.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) { /* ignored */}
			}
		}
		
		return id;
		
	}
	
	
	public static void insertUsersToProject(String project_name, String[] users) {
		
		insertUsersToProject(getProjectID(sanitize(project_name)), users);
	}
	
	
	public static void insertUsersToProject(int project_id, String[] users) {
			
		
			String query = "insert into AnnotationTool.project_users (project_id, user_id) "
					+ "VALUES (?,(SELECT user_id from AnnotationTool.users where username = ?));";
		
			PreparedStatement ps = null;
	
			DBConnection DBCon = new DBConnection();
			Connection connection = DBCon.getCon();
			
			try {
				
				connection.setAutoCommit(false);  
				ps = connection.prepareStatement(query);    
				
				for (String user : users) {
				    
					ps.setInt(1, project_id);
					ps.setString(2, sanitize(user));
					
				    ps.addBatch();
				}
				
				ps.executeBatch();
				connection.commit(); 
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (ps != null) {
					try {
						ps.close();
					} catch (SQLException e) { /* ignored */}
				}
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) { /* ignored */}
				}
			}	
			
		}
	
	
	public static void insertStandardToProject(String project_name, String[] standard_names) {
		
		insertStandardToProject(getProjectID(project_name), standard_names);
		
	}
	
	
	public static void insertStandardToProject(int project_id, String[] standard_names) {
		

		String query = "insert into AnnotationTool.project_standards (project_id, standard_id) "
				+ "VALUES (?,(SELECT standard_pk from AnnotationTool.standards where standard_name = ?));";
	
		PreparedStatement ps = null;

		DBConnection DBCon = new DBConnection();
		Connection connection = DBCon.getCon();
		
		try {
			
			connection.setAutoCommit(false);  
			ps = connection.prepareStatement(query);    
			
			for (String standard : standard_names) {
			    
				ps.setInt(1, project_id);
				ps.setString(2, sanitize(standard));
				
			    ps.addBatch();
			}
			
			ps.executeBatch();
			connection.commit(); 
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) { /* ignored */}
			}
		}	
		
	}
	
	
	public static void insertReportsToProject(String project_name, int[] report_ids) {
			
		insertReportsToProject(getProjectID(project_name), report_ids);
			
		}
	
	
	public static void insertReportsToProject(String project_name, String[] report_ids) {
		
		insertReportsToProject(getProjectID(project_name), stringArrayToInt(report_ids));
			
		}
		
	
	public static boolean inStringArray(String[] array, String string) {
		
		for (String s : array) {
			if (s.equals(string)) {
				return true;
			} 
		}
		
		return false;
	}
	
	
	public static void insertReportsToProject(int project_id, int[] report_ids) {
			
	
			String query = "insert into AnnotationTool.project_reports (project_id, report_id) "
					+ "VALUES (?,?);";
		
			PreparedStatement ps = null;
	
			DBConnection DBCon = new DBConnection();
			Connection connection = DBCon.getCon();
			
			Project project = getProject(project_id);
			
			String[] already_there = project.getReports();
			
			try {
				
				connection.setAutoCommit(false);  
				ps = connection.prepareStatement(query);    
				
				for (int report_id : report_ids) {
				    
					if (!inStringArray(already_there, Integer.toString(report_id))){
						ps.setInt(1, project_id);
						ps.setInt(2, report_id);
						
					    ps.addBatch();
					}

				}
				
				ps.executeBatch();
				connection.commit(); 
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (ps != null) {
					try {
						ps.close();
					} catch (SQLException e) { /* ignored */}
				}
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) { /* ignored */}
				}
			}	
			
		}
	
	
	public static void changeProjectCreator(String project_name, String new_creator) {
		changeProjectCreator(getProjectID(project_name), sanitize(new_creator));
	}
	
	
	public static void changeProjectCreator(int project_id, String new_creator) {
		
		
		String query = "update AnnotationTool.projects set creator = ? where project_pk = ?;";
	
		PreparedStatement ps = null;

		DBConnection DBCon = new DBConnection();
		Connection connection = DBCon.getCon();
		
		try {
			
			ps = connection.prepareStatement(query);   
			
			ps.setString(1, sanitize(new_creator));
			ps.setInt(2, project_id);
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) { /* ignored */}
			}
		}	
		
	}
	
	
	public static void changeProjectName(String old_name, String new_name) {
		changeProjectName(getProjectID(sanitize(old_name)), sanitize(new_name));
	}
	
	
	public static void changeProjectName(int project_id, String new_name) {
		
		
		String query = "update AnnotationTool.projects set name = ? where project_pk = ?;";
	
		PreparedStatement ps = null;

		DBConnection DBCon = new DBConnection();
		Connection connection = DBCon.getCon();
		
		try {
			
			ps = connection.prepareStatement(query);   
			
			ps.setString(1, sanitize(new_name));
			ps.setInt(2, project_id);
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) { /* ignored */}
			}
		}	
		
	}
	

		
	public static void insertCollectionsToProject(String project_name, String[] collection_names) {
		
		int[] report_ids = convertIntegers(getReportIDs(collection_names));
		
		insertReportsToProject(sanitize(project_name), report_ids);
		 
	}
	
	
	public static int[] stringArrayToInt(String[] strings) {
		
		int[] integers = new int[strings.length];
		
		for (int i=0; i < strings.length; i++)
	    {
			integers[i] = Integer.parseInt(strings[i]);
	    }
		
		return integers;
	}
	
	
	public static int insertProject(Project project){
		
		int project_id = insertProjectInProjects(project);
		
		insertUsersToProject(project_id, project.getUsernames());
		
		insertUsersToProject(project_id, new String[] {project.getCreator()});
		
		insertCollectionsToProject(sanitize(project.getName()), project.getCollections());
				
		insertReportsToProject(sanitize(project.getName()), stringArrayToInt(project.getReports()));
		
		insertStandardToProject(project_id, project.getStandards());
		
		return project_id;
		
	}
	
	
	public static void deleteProject(String name) {
		PreparedStatement ps = null;

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();

		String query = "DELETE FROM AnnotationTool.projects"
				+ " WHERE name = ?;";

		try {
			ps = con.prepareStatement(query);

			ps.setString(1,sanitize(name));

			ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) { /* ignored */}
			}
		}

	}
	
	
	public static List<Project> getAllProjectsBasic(){

		List<Project> projects = new ArrayList<Project>();

		PreparedStatement ps = null;
		ResultSet results = null;

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();

		String query = "select * from AnnotationTool.projects;";

		try {
			ps = con.prepareStatement(query);

			results = ps.executeQuery();

			while(results.next()){

				Project project = new Project();

				project.setPk(results.getInt("project_pk"));
				project.setName(results.getString("name"));
				project.setCreator(results.getString("creator"));
				project.setDate(results.getString("date"));
				project.setFinished(results.getBoolean("finished"));


				projects.add(project);
			}


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (results != null) {
				try {
					results.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) { /* ignored */}
			}
		}

		return projects;

	}
	
	
	public static String[] getUsersFromProject(int project_id){
		
		List<String> usersList = new ArrayList<>();
		
		PreparedStatement ps = null;
		ResultSet results = null;

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();

		String query =  "select username from("
				+  "select AnnotationTool.users.username, AnnotationTool.users.user_id, AnnotationTool.project_users.project_id"
				+ " from AnnotationTool.users"
				+ " inner join AnnotationTool.project_users on AnnotationTool.users.user_id = "
				+ "AnnotationTool.project_users.user_id) as inner_table"
				+ " where inner_table.project_id = ?;";

		try {
			ps = con.prepareStatement(query);
			
			ps.setInt(1, project_id);

			results = ps.executeQuery();

			while(results.next()){
				
				usersList.add(results.getString("username"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (results != null) {
				try {
					results.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) { /* ignored */}
			}
		}
		
		String[] usersArray = usersList.toArray(new String[0]);
		
		return usersArray;
	}
	
	
	public static String[] getStandardsFromProject(int project_id){
			
			List<String> standardList = new ArrayList<>();
			
			PreparedStatement ps = null;
			ResultSet results = null;
	
			DBConnection DBCon = new DBConnection();
			Connection con = DBCon.getCon();
	
			String query =  "select standard_name from("
					+ " select AnnotationTool.standards.standard_name, "
					+ " AnnotationTool.standards.standard_pk, AnnotationTool.project_standards.project_id "
					+ " from AnnotationTool.standards inner join "
					+ " AnnotationTool.project_standards on "
					+ " AnnotationTool.standards.standard_pk = "
					+ " AnnotationTool.project_standards.standard_id) as inner_table "
					+ " where inner_table.project_id = ?;";
	
			try {
				ps = con.prepareStatement(query);
				
				ps.setInt(1, project_id);
	
				results = ps.executeQuery();
	
				while(results.next()){
					
					standardList.add(results.getString("standard_name"));
				}
	
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (results != null) {
					try {
						results.close();
					} catch (SQLException e) { /* ignored */}
				}
				if (ps != null) {
					try {
						ps.close();
					} catch (SQLException e) { /* ignored */}
				}
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) { /* ignored */}
				}
			}
			
			String[] standardArray = standardList.toArray(new String[0]);
			
			return standardArray;
		}
	
	
	public static String[] getReportsFromProject(int project_id){
		
		List<Integer> reportList = new ArrayList<>();
		
		PreparedStatement ps = null;
		ResultSet results = null;

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();

		String query =  "select report_id from AnnotationTool.project_reports where project_id = ?;";

		try {
			ps = con.prepareStatement(query);
			
			ps.setInt(1, project_id);

			results = ps.executeQuery();

			while(results.next()){
				
				reportList.add(results.getInt("report_id"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (results != null) {
				try {
					results.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) { /* ignored */}
			}
		}
		
		String[] reportArray = new String[reportList.size()];
		
		for (int i = 0; i < reportArray.length; i++){
			reportArray[i] = Integer.toString(reportList.get(i));
		}
				
		return reportArray;
		
	}
	
	
	public static List<Project> getAllProjects(){
		
		List<Project> projects = getAllProjectsBasic();
		
		for (Project project : projects) {
			
			project.setUsernames(getUsersFromProject(project.getPk()));
			project.setStandards(getStandardsFromProject(project.getPk()));
			project.setReports(getReportsFromProject(project.getPk()));
			
		}
		
		return projects;
		
	}
	
	
	public static List<Integer> getProjectIDsFromUser(String username){
		
		List<Integer> projectIDs = new ArrayList<>();
		
		PreparedStatement ps = null;
		ResultSet results = null;

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();

		String query =  "select project_id from (select AnnotationTool.project_users.project_id, "
				+ " AnnotationTool.users.username from AnnotationTool.project_users "
				+ " inner join AnnotationTool.users on"
				+ " AnnotationTool.project_users.user_id = AnnotationTool.users.user_id)"
				+ " as inner_table where inner_table.username = ?;";

		try {
			ps = con.prepareStatement(query);
			
			ps.setString(1, username);

			results = ps.executeQuery();

			while(results.next()){
				
				projectIDs.add(results.getInt("project_id"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (results != null) {
				try {
					results.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) { /* ignored */}
			}
		}
		
		return projectIDs;
		
	}
	
	
	public static String[] StringListToArray(List<String> list){
		
		String[] array = new String[list.size()];
		
		for (int i = 0; i < list.size(); i++) {
			array[i] = list.get(i);
		}
		
		return array;
		
	}
	
	
	public static Project getProject(String name){
		
		return getProject(getProjectID(sanitize(name)));
	}
	
	
	public static Project getProject(int id){
		Project project = new Project();
		
		PreparedStatement ps = null;
		ResultSet results = null;

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();

		String query =  "select * from AnnotationTool.projects, AnnotationTool.project_reports, "
				+ " (select AnnotationTool.standards.standard_name, AnnotationTool.project_standards.project_id from "
				+ " AnnotationTool.standards inner join  AnnotationTool.project_standards "
				+ " on AnnotationTool.standards.standard_pk = AnnotationTool.project_standards.standard_id)"
				+ "  as inner_table1, "
				+ " (select AnnotationTool.users.username, AnnotationTool.project_users.project_id from AnnotationTool.users "
				+ " inner join  AnnotationTool.project_users on "
				+ " AnnotationTool.users.user_id = AnnotationTool.project_users.user_id) as inner_table2"
				+ " where project_pk = ?"
				+ " and AnnotationTool.project_reports.project_id = ? "
				+ " and inner_table1.project_id = ? "
				+ " and inner_table2.project_id = ?;";
				
		try {
			ps = con.prepareStatement(query);
			
			ps.setInt(1, id);
			ps.setInt(2, id);
			ps.setInt(3, id);
			ps.setInt(4, id);

			results = ps.executeQuery();
			
			List<String> reports = new ArrayList<>();
			List<String> standards = new ArrayList<>();
			List<String> usernames = new ArrayList<>();
			
			while(results.next()){
				
				project.setFinished(results.getBoolean("finished"));
				
				if (project.getName() == null) {
					project.setName(results.getString("name"));
				}
				
				if (project.getCreator() == null) {
					project.setCreator(results.getString("creator"));
				}
				
				if (project.getDate() == null) {
					project.setDate(results.getString("date"));
				}
				
				if (project.getPk() == -1) {
					project.setPk(results.getInt("project_pk"));
				}
				
				if (!reports.contains(Integer.toString(results.getInt("report_id")))) {
					reports.add(Integer.toString(results.getInt("report_id")));
				}
				
				if (!standards.contains(results.getString("standard_name"))) {
					standards.add(results.getString("standard_name"));
				}
				
				if (!usernames.contains(results.getString("username"))) {
					usernames.add(results.getString("username"));
				}
				
			}
			project.setReports(StringListToArray(reports));
			project.setStandards(StringListToArray(standards));
			project.setUsernames(StringListToArray(usernames));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (results != null) {
				try {
					results.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) { /* ignored */}
			}
		}
		
		
		return project;
		
	}
	
	
	
	public static List<Project> getProjectsFromUser(String username){
		
		List<Project> projects = new ArrayList<>();
		
		List<Integer> projectIDs = getProjectIDsFromUser(sanitize(username));
		
		for (int id : projectIDs) {
			
			projects.add(getProject(id));
			
		}

		return projects;
		
	}
	
	
	public static List<Project> getProjectsFromUser(String username, boolean finished){
		
		List<Project> projects = new ArrayList<>();
		List<Integer> projectIDs = getProjectIDsFromUser(sanitize(username));
		
		for (int id : projectIDs) {
			
			Project project = getProject(id);
			
			if (project.isFinished() == finished){
				projects.add(project);
			}
			
		}

		return projects;
		
	}
	
	
	public static void removeUserFromProject(String username, String project_name){

		PreparedStatement ps = null;
		
		int user_id = getUser(sanitize(username)).getId();
		int project_id = getProjectID(project_name);

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();

		String query = "DELETE FROM AnnotationTool.project_users WHERE user_id = ? and project_id = ?;";

		try {
			ps = con.prepareStatement(query);

			ps.setInt(1,user_id);
			ps.setInt(2, project_id);

			ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) { /* ignored */}
			}
		}

	}
	
	public static int getStandarID(String standard_name) {
		
		int id = -1;
		PreparedStatement ps = null;
		ResultSet results = null;

		String query = "select standard_pk from AnnotationTool.standards where standard_name = ?;";

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();

		try {
			ps = con.prepareStatement(query);

			ps.setString(1,sanitize(standard_name));

			results = ps.executeQuery();
			
			results.next();

			id = results.getInt("standard_pk");


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (results != null) {
				try {
					results.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) { /* ignored */}
			}
		}

		return id;
	}
	
	
	public static void removeStandardFromProject(String standard_name, String project_name){

		PreparedStatement ps = null;
		
		int standard_id = getStandarID(standard_name);
		int project_id = getProjectID(project_name);

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();

		String query = "DELETE FROM AnnotationTool.project_standards WHERE standard_id = ? and project_id = ?;";

		try {
			ps = con.prepareStatement(query);

			ps.setInt(1,standard_id);
			ps.setInt(2, project_id);

			ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) { /* ignored */}
			}
		}

	}
	
	public static void removeStandardFromProject(int standard_id, String project_name){

		PreparedStatement ps = null;
		
		int project_id = getProjectID(project_name);

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();

		String query = "DELETE FROM AnnotationTool.project_standards WHERE standard_id = ? and project_id = ?;";

		try {
			ps = con.prepareStatement(query);

			ps.setInt(1,standard_id);
			ps.setInt(2, project_id);

			ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) { /* ignored */}
			}
		}

	}
	
	
	public static void removeReportFromProject(String report_id, String project_name){
		
		removeReportFromProject(Integer.parseInt(report_id), project_name);
		
	}
	
	
	public static void removeReportFromProject(int report_id, String project_name){

		PreparedStatement ps = null;
		
		int project_id = getProjectID(project_name);

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();

		String query = "DELETE FROM AnnotationTool.project_reports WHERE report_id = ? and project_id = ?;";

		try {
			ps = con.prepareStatement(query);

			ps.setInt(1,report_id);
			ps.setInt(2, project_id);

			ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) { /* ignored */}
			}
		}

	}
	
	public static void removeReportFromProject(int report_id, int project_id){

		PreparedStatement ps = null;
		
		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();

		String query = "DELETE FROM AnnotationTool.project_reports WHERE report_id = ? and project_id = ?;";

		try {
			ps = con.prepareStatement(query);

			ps.setInt(1,report_id);
			ps.setInt(2, project_id);

			ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) { /* ignored */}
			}
		}

	}
	
	public static void changeProjectStatus(String name, boolean finished) {
		
		PreparedStatement ps = null;

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();

		String query = "UPDATE AnnotationTool.projects"
				+ " SET finished = ?"
				+ " WHERE name = ?;";

		try {
			ps = con.prepareStatement(query);

			ps.setBoolean(1, finished);
			ps.setString(2,name);

			ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) { /* ignored */}
			}
		}
	}
	
	
	public static Standard getStandard(int id){
		Standard standard = new Standard();
		

		String query = "select * from AnnotationTool.standards where standard_pk = ?;" ;

		PreparedStatement ps = null;
		ResultSet results = null;

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();

		try {
			ps = con.prepareStatement(query);

			ps.setInt(1,id);

			results = ps.executeQuery();
			
			results.next();
			
			standard.setPk(results.getInt("standard_pk"));
			standard.setName(results.getString("standard_name"));
			standard.setJson(results.getString("standard_json"));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (results != null) {
				try {
					results.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) { /* ignored */}
			}
		}


		return standard;
	}

	
	public static Standard getStandard(String name){
		Standard standard = new Standard();
		

		String query = "select * from AnnotationTool.standards where standard_name = ?;" ;

		PreparedStatement ps = null;
		ResultSet results = null;

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();

		try {
			ps = con.prepareStatement(query);

			ps.setString(1,name);

			results = ps.executeQuery();
			
			results.next();
			
			standard.setPk(results.getInt("standard_pk"));
			standard.setName(results.getString("standard_name"));
			standard.setJson(results.getString("standard_json"));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (results != null) {
				try {
					results.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) { /* ignored */}
			}
		}


		return standard;
	}
	

	
	public static void deleteStandard(int id) {
		PreparedStatement ps = null;

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();

		String query = "DELETE FROM AnnotationTool.standards"
				+ " WHERE standard_pk = ?;";

		try {
			ps = con.prepareStatement(query);

			ps.setInt(1,id);

			ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) { /* ignored */}
			}
		}


	}
	

	public static void modifyStandard(String old_name, String new_name, String json) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();


		try {

			String query =  "UPDATE AnnotationTool.standards "
					+ "SET  standard_name = ?,"
					+ "standard_json = ?"
					+ " WHERE standard_name = ?;";

			ps = con.prepareStatement(query);

			ps.setString(1,sanitize(new_name)); 
			ps.setString(2,json); 
			ps.setString(3,sanitize(old_name));

			ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) { /* ignored */}
			}
		}	


	}


	
	public static int insertStandard(Standard standard) {
		
		int id = -1;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String name = sanitize(standard.getName());
		String json = standard.getJson();

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();

		String query =  "INSERT INTO AnnotationTool.standards (standard_name, standard_json) "
				+ "VALUES (?,?);";

		try {

			ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			ps.setString(1,name);
			ps.setString(2, json);

			ps.executeUpdate(); 
			rs = ps.getGeneratedKeys();

			rs.next();

			id = rs.getInt("standard_pk");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) { /* ignored */}
			}
		}	
		
		return id;
	}
	
	
	public static List<Standard> getAllStandards(){

		List<Standard> standards = new ArrayList<Standard>();

		PreparedStatement ps = null;
		ResultSet results = null;

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();

		String query = "select * from AnnotationTool.standards order by standard_name asc;";

		try {
			ps = con.prepareStatement(query);

			results = ps.executeQuery();

			while(results.next()){

				Standard standard = new Standard();

				standard.setPk(results.getInt("standard_pk"));
				standard.setName(results.getString("standard_name"));
				standard.setJson(results.getString("standard_json"));

				standards.add(standard);

			}


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return standards;

	}


	public static void deleteStandard(String name) {
		PreparedStatement ps = null;

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();

		String query = "DELETE FROM AnnotationTool.standards"
				+ " WHERE standard_name = ?;";

		try {
			ps = con.prepareStatement(query);

			ps.setString(1,sanitize(name));

			ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) { /* ignored */}
			}
		}


	}


	public static void modifyStandard(int pk, String name, String json) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		int standard_pk = pk;
		String standard_name = sanitize(name);
		String standard_json = sanitize(json);


		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();


		try {

			String query =  "UPDATE AnnotationTool.standards "
					+ "SET  standard_name = ?,"
					+ "standard_json = ?"
					+ " WHERE standard_pk = ?;";

			ps = con.prepareStatement(query);

			ps.setString(1,standard_name); 
			ps.setString(2,standard_json); 
			ps.setInt(3,standard_pk);

			ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) { /* ignored */}
			}
		}	


	}


	public static void insertKeysInReportsCollections(int collection_id, int report_id) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();

		String query =  "INSERT INTO AnnotationTool.reports_to_collections (collection_id, report_id) "
				+ "VALUES (?,?);";

		try {

			ps = con.prepareStatement(query);

			ps.setInt(1, collection_id);
			ps.setInt(2, report_id);

			ps.executeUpdate(); 

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) { /* ignored */}
			}
		}	
	}

	public static int getStandardKey(String standardname) {
		int id = 0;
		PreparedStatement ps = null;
		ResultSet results = null;

		String query = "select standard_pk from AnnotationTool.standards where standard_name = ?;";

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();

		try {
			ps = con.prepareStatement(query);

			ps.setString(1,standardname);

			results = ps.executeQuery();
			results.next();

			id = results.getInt("standard_pk");


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (results != null) {
				try {
					results.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) { /* ignored */}
			}
		}

		return id;
	}


	public static int insertAnnotation(Annotated annotation) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int id = -1;

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();

		String query =  "INSERT INTO AnnotationTool.annotated (annotator, standard_id, annotation, report_id, "
				+ "html, project_id, date) "
				+ "VALUES (?,?,?,?,?,?,CURRENT_TIMESTAMP);";

		try {
			
			ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, sanitize(annotation.getAnnotator()));
			ps.setInt(2, annotation.getStandard_id());
			ps.setString(3, annotation.getAnnotation());
			ps.setInt(4, annotation.getReport_id());
			ps.setString(5, annotation.getHtml());
			ps.setInt(6, annotation.getProject_id());

			ps.executeUpdate(); 
			
			rs = ps.getGeneratedKeys();

			rs.next();
			
			id = rs.getInt("annotated_pk");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) { /* ignored */}
			}
		}	
		return id;
	}

	public static void deleteAnnotation(int id){

		PreparedStatement ps = null;

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();

		String query = "DELETE FROM AnnotationTool.annotated WHERE annotated_pk = ?;";

		try {
			ps = con.prepareStatement(query);

			ps.setInt(1,id);

			ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) { /* ignored */}
			}
		}

	}
	
	
	public static List<Annotated> getAnnotations(int report_id, int project_id){
		
		List<Annotated> annotations = new ArrayList<>();

		PreparedStatement ps = null;
		ResultSet rs = null;

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();
		
		String query = "SELECT * FROM AnnotationTool.annotated "
				+ " where report_id = ? and project_id = ?;";

		try {
			ps = con.prepareStatement(query);

			ps.setInt(1,report_id);
			ps.setInt(2,project_id);


			rs = ps.executeQuery();
			
			while (rs.next()) {
				
				Annotated an = new Annotated();
				
				an.setAnnotation(rs.getString("annotation"));
				an.setAnnotator(rs.getString("annotator"));
				an.setStandard_id(rs.getInt("standard_id"));
				an.setDate(rs.getString("date"));
				an.setHtml(rs.getString("html"));
				an.setProject_id(rs.getInt("project_id"));
				an.setReport_id(rs.getInt("report_id"));
				an.setPk(rs.getInt("annotated_pk"));
				
				annotations.add(an);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) { /* ignored */}
			}
		}

		return annotations;
	}
	
	
	public static List<Annotated> getAnnotations(int report_id){
			
			List<Annotated> annotations = new ArrayList<>();
	
			PreparedStatement ps = null;
			ResultSet rs = null;
	
			DBConnection DBCon = new DBConnection();
			Connection con = DBCon.getCon();
			
			String query = "SELECT * FROM AnnotationTool.annotated "
					+ " where report_id = ?;";
	
			try {
				ps = con.prepareStatement(query);
	
				ps.setInt(1,report_id);
	
				rs = ps.executeQuery();
				
				while (rs.next()) {
					
					Annotated an = new Annotated();
					
					an.setAnnotation(rs.getString("annotation"));
					an.setAnnotator(rs.getString("annotator"));
					an.setStandard_id(rs.getInt("standard_id"));
					an.setDate(rs.getString("date"));
					an.setHtml(rs.getString("html"));
					an.setProject_id(rs.getInt("project_id"));
					an.setReport_id(rs.getInt("report_id"));
					an.setPk(rs.getInt("annotated_pk"));
					
					annotations.add(an);
				}
	
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException e) { /* ignored */}
				}
				if (ps != null) {
					try {
						ps.close();
					} catch (SQLException e) { /* ignored */}
				}
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) { /* ignored */}
				}
			}
	
			return annotations;
		}
	
	
	
	public static List<Annotated> getAnnotationsFromProject(int project_id){
		
		List<Annotated> annotations = new ArrayList<>();
	
		PreparedStatement ps = null;
		ResultSet rs = null;
	
		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();
		
		String query = "SELECT * FROM AnnotationTool.annotated "
				+ " where project_id = ?;";
	
		try {
			ps = con.prepareStatement(query);
	
			ps.setInt(1,project_id);
	
	
			rs = ps.executeQuery();
			
			while (rs.next()) {
				
				Annotated an = new Annotated();
				
				an.setAnnotation(rs.getString("annotation"));
				an.setAnnotator(rs.getString("annotator"));
				an.setStandard_id(rs.getInt("standard_id"));
				an.setDate(rs.getString("date"));
				an.setHtml(rs.getString("html"));
				an.setProject_id(rs.getInt("project_id"));
				an.setReport_id(rs.getInt("report_id"));
				an.setPk(rs.getInt("annotated_pk"));
				
				annotations.add(an);
			}
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) { /* ignored */}
			}
		}
	
		return annotations;
	}

	
	public static void modifyAnnotation(Annotated annotation) {
		
		
		PreparedStatement ps = null;

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();
		
		
		String query =  "UPDATE AnnotationTool.annotated "
				+ "SET  annotation = ?,"
				+ "annotator = ?,"
				+ "standard_id = ?,"
				+ "date = CURRENT_TIMESTAMP,"
				+ "html = ?,"
				+ "project_id=?,"
				+ "report_id = ?"
				+ " WHERE annotated_pk = ?;";
		
		try {
			ps = con.prepareStatement(query);

			ps.setString(1,annotation.getAnnotation());
			ps.setString(2,annotation.getAnnotator());
			ps.setInt(3, annotation.getStandard_id());
			ps.setString(4, annotation.getHtml());
			ps.setInt(5, annotation.getProject_id());
			ps.setInt(6, annotation.getReport_id());
			ps.setInt(7, annotation.getPk());

			ps.executeUpdate();


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) { /* ignored */}
			}
		}
	}
	
	
	public static Annotated getAnnotated(int id) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		Annotated an = new Annotated();

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();


		try {

			ps = con.prepareStatement(  
					"select * from annotationTool.annotated where annotated_pk=?");

			ps.setInt(1,id);  

			rs = ps.executeQuery();  

			if (rs.next()) {
				
				an.setAnnotation(rs.getString("annotation"));
				an.setAnnotator(rs.getString("annotator"));
				an.setStandard_id(rs.getInt("standard_id"));
				an.setDate(rs.getString("date"));
				an.setHtml(rs.getString("html"));
				an.setProject_id(rs.getInt("project_id"));
				an.setReport_id(rs.getInt("report_id"));
				an.setPk(rs.getInt("annotated_pk"));
			} else {
				return null;
			}
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) { /* ignored */}
			}
		}

		return an;
		
	}
	
	public static void modifyProject(String old_name, Project project) {
		
		changeProjectCreator(old_name, project.getCreator());
		changeProjectStatus(old_name, project.isFinished());
		changeProjectName(old_name, project.getName());
		
	}
	
	public static int automaticallyAnnotate(int report_id, int project_id) {
		
		Annotated an = new Annotated();
		int id = -1;
		
		String xml = CallPython.Annotate(report_id);
		
		Standard birads = getStandard("BIRADS");
		
		an.setAnnotation(xml);
		an.setAnnotator("NLP");
		an.setStandard_id(birads.getPk());
		an.setProject_id(project_id);
		an.setReport_id(report_id);
		System.out.println("XML   " + xml);
		an.setHtml(util.XmlToHtml.convert(xml, birads.getJson()));
		
		id = insertAnnotation(an);

		
		return id;
	}
	
	
	public static boolean isAutoAnnotated(int report_id, int project_id) {
				
		
		PreparedStatement ps = null;
		ResultSet rs = null;

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();


		try {

			ps = con.prepareStatement(  
					"select * from annotationTool.annotated where report_id=? and project_id = ? and annotator = 'NLP';");

			ps.setInt(1,report_id);  
			ps.setInt(2, project_id);

			rs = ps.executeQuery();  

			return rs.next();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) { /* ignored */}
			}
		}
		
		return false;
		
	}

	
	public static boolean isAutoProject(int project_id) {
		
		String[] reports = getReportsFromProject(project_id);
		
		for (String s : reports) {
			
			if (!isAutoAnnotated(Integer.parseInt(s), project_id)) {
				return false;
			}
		}
		
		return true;
	}
	
	
	public static int countReports(String username) {
		
		int ret = 0;
		
		
		PreparedStatement ps = null;
		ResultSet rs = null;

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();


		try {

			ps = con.prepareStatement(  
					"select count(*) from AnnotationTool.reports where uploader = ?;");

			ps.setString(1,username);  

			rs = ps.executeQuery();  

			if (rs.next()) {
				ret = rs.getInt("count");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) { /* ignored */}
			}
		}
		
		
		return ret;
	}
	
	
	public static int countAnnotations(String username) {
		
		int ret = 0;
		
		
		PreparedStatement ps = null;
		ResultSet rs = null;

		DBConnection DBCon = new DBConnection();
		Connection con = DBCon.getCon();


		try {

			ps = con.prepareStatement(  
					"select count(*) from AnnotationTool.annotated where annotator = ?;");

			ps.setString(1,username);  

			rs = ps.executeQuery();  

			if (rs.next()) {
				ret = rs.getInt("count");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) { /* ignored */}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) { /* ignored */}
			}
		}
		
		
		return ret;
	}
	
	public static void main(String[] args){
	
		System.out.println(getProject(50).getPk());
		
	}

}