package NLP;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;


public class CallPython {
	
	private static String OS = System.getProperty("os.name").toLowerCase();
	
	public static boolean isWindows() {

		return (OS.indexOf("win") >= 0);

	}

	public static boolean isMac() {

		return (OS.indexOf("mac") >= 0);

	}

	public static boolean isUnix() {

		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
		
	}

	public static boolean isSolaris() {

		return (OS.indexOf("sunos") >= 0);
	}
		
	public static String Annotate(int id) {
		
		//String currentDir = System.getProperty("user.dir");
        String annotation = "";
        
        final File f = new File(CallPython.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        String pathURL = f.getParent() + "/classes/NLP/Python/Database.py";
        String path = pathURL.replaceAll("%20", " ");
        
        
        String command = "";

        if (isWindows()) {
        	path = "\"" + path + "\"";
        	command = "py " + path + " " + id;	//currentDir+"/src/main/java/NLP/Python/Database.py "
        } else if (isMac()) {
        	command = "python2 " + path + " " + id;
        } else if (isUnix()) {
        	command = "sudo python2 " + path + " " + id;
        }
		try {
			Process p = Runtime.getRuntime().exec(command);

			BufferedReader stdInput = new BufferedReader(new 
                 InputStreamReader(p.getInputStream()));
			
			BufferedReader stdErr = new BufferedReader(new 
	                 InputStreamReader(p.getErrorStream()));
			
			String st = null;
			while ((st = stdErr.readLine()) != null) {
				System.out.println(st);

			}

            // read the output from the command
            String s = null;
           
            while ((s = stdInput.readLine()) != null) {
            	annotation += s;
            }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return annotation;
	}
	
	
	public static void main(String[] args) {
		
        

	}

}
