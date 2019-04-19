package util;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class CreateINI {
	
	
	public static void main(String args[]) {
		
		Scanner reader = new Scanner(System.in);  
		
		System.out.println("Insert the database connection url");
		String url = reader.nextLine();
		
		System.out.println("Insert the name of the database");
		String dbName = reader.nextLine();
		
		System.out.println("Insert your database username");
		String username = reader.nextLine();
		
		System.out.println("Insert your database password");
		String password = reader.nextLine();
		
		String morp = "";
		String driver = "";
		String driverForUrl = "";
		
		do {
			
			System.out.println("If you use MySQL insert m, if you use PostgreSQL isnert p ");
			morp = reader.nextLine();
			
			if (morp.equals("m") || morp.equals("M")) {
				
				driver = "com.mysql.jdbc.Driver";
				driverForUrl = "jdbc:mysql://";
				
			} else if (morp.equals("p") || morp.equals("P")) {
				
				driver = "org.postgresql.Driver";
				driverForUrl = "jdbc:postgresql://";
				
			} else {
				
				System.out.println("Invalid input");
			} 
			
		} while (!(morp.equals("m") || morp.equals("M") || morp.equals("p") || morp.equals("P")));
			

		String javaURL = driverForUrl + url + "/" + dbName + "?currentSchema=annotationTool";
		
		
		reader.close();
		
		try {
			PrintWriter writer = new PrintWriter("database.ini", "UTF-8");
			
			writer.println("[database]");
			writer.println("DRIVER="+driver);
			writer.println("CONNECTION_URL="+javaURL);
			writer.println("USERNAME="+username);
			writer.println("PASSWORD="+password);
			writer.println("");
			writer.println("[databasePython]");
			writer.println("url="+url);
			writer.println("database="+dbName);
			
			writer.close();
			
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}

}
