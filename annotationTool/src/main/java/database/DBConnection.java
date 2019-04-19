package database;

import java.sql.*;

public class DBConnection {
	
	public Connection con = null;
	
	public Connection getCon() {
		if(con==null){
				try{
					//System.out.println("before init class");
					Class.forName(Provider.DRIVER);
					con = DriverManager.getConnection(Provider.CONNECTION_URL, Provider.USERNAME, Provider.PASSWORD);
					con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
	    			con.setAutoCommit(true);
				
				} catch(Exception e){
					System.out.println("ConnnectionError!!!");
				}
		
		}
		return con;	
	}
	
	public static void main(String[] args){
		
		System.out.println("Testing....");
		
		DBConnection db = new DBConnection();
		
		Connection connect = db.getCon();
		
		System.out.println(connect);
		
	}
	
}
