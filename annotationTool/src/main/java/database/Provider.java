package database;

import java.util.Map;

public class Provider {
	
	public static  String DRIVER="";
	public static  String CONNECTION_URL="";
	public static  String USERNAME ="";
	public static  String PASSWORD ="";

	static {
	
		Map<String, String> map = ReadFromFile.setProvider();
		
		DRIVER=map.get("driver");
		CONNECTION_URL=map.get("url");
		USERNAME =map.get("username");
		PASSWORD =map.get("password");
	}

    
	
//	public static  String DRIVER="org.postgresql.Driver";
//	public static  String CONNECTION_URL="jdbc:postgresql://castle.ewi.utwente.nl/dpv2a136?currentSchema=annotationTool";
//	public static  String USERNAME ="dpv2a136";
//	public static  String PASSWORD ="SXgMxsey";
}
