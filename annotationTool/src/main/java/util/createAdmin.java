package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.DBConnection;

/**
 * This class is to be used only on the back end in order to create admin accounts
 * @author Yannis
 *
 */
public class createAdmin {


	public static void main(String[] args) {
		PreparedStatement ps = null;
    	DBConnection DBCon = new DBConnection();
    	Connection con = DBCon.getCon();	 	
	 	String name = "admin";
	 	String password = "admin";
	 	String rights = "admin";
	 		 	
	 	String query = "INSERT INTO users (username, password, rights) "
	 			+ "VALUES (?, ?, ?::enumrights);";
	 	
	 	String hashpass = Password.hashCode(password);
	 	
  			try {
				ps = con.prepareStatement(query);
				
				ps.setString(1, name); 
				ps.setString(2, hashpass);
				ps.setString(3, rights);
				
				ps.executeUpdate();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
  			
  		 	System.out.println("Admin with name: " + name + " and password: " + password + " has been created!");

  			
	}
	
}
