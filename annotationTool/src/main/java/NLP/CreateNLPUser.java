package NLP;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.text.RandomStringGenerator;

import database.DBConnection;
import util.Password;

public class CreateNLPUser {
	
	public static String generateRandomSpecialCharacters(int length) {
	    RandomStringGenerator pwdGenerator = new RandomStringGenerator.Builder().withinRange(33, 45).build();
	    return pwdGenerator.generate(length);
	}

	public static void main(String[] args) {
		PreparedStatement ps = null;
    	DBConnection DBCon = new DBConnection();
    	Connection con = DBCon.getCon();	 	
	 	String name = "NLP";
	 	String password = generateRandomSpecialCharacters(20);
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
  			
  		 	System.out.println("NLP with name: " + name + " and password: " + password + " has been created!");

  			
	}

}
