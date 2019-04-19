package database;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.ini4j.*;


public class ReadFromFile {

	public static Map<String, String> setProvider() {
		
		Map<String, String> map = new HashMap<String, String>() ;
		
	 	final File f = new File(ReadFromFile.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        String pathURL = f.getParent() + "/classes/database/database.ini";
        String path = pathURL.replaceAll("%20", " ");
        //path = "\"" + path + "\"";
        
	 
	 try{
            Wini ini = new Wini(new File(path));
            
            String driver = ini.get("database", "DRIVER", String.class);
            String url = ini.get("database", "CONNECTION_URL", String.class);
            String username = ini.get("database", "USERNAME", String.class);
            String password = ini.get("database", "PASSWORD", String.class);
            
            map.put("driver", driver);
            map.put("url", url);
            map.put("username", username);
            map.put("password", password);
            
            
        }catch(Exception e){
            System.err.println(e.getMessage());
            System.out.println("WINI ERROR");
        }
	 
	 return map;
		
	}
	
	
	public static void main(String[] args) {
		
	}
	
}
