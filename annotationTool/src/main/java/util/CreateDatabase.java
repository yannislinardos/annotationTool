package util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.text.RandomStringGenerator;


public class CreateDatabase {
	
	
	public static Connection getCon(Map<String, String> credentials) {
		
		Connection con = null;
	
		String url = "jdbc:postgresql://" + credentials.get("url") + "/" + credentials.get("db_name") + "?currentSchema=annotationTool";
		
		if(con==null){
				try{
					Class.forName("org.postgresql.Driver");
					con = DriverManager.getConnection(url, credentials.get("username"), 
							credentials.get("password"));
					con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
	    			con.setAutoCommit(true);
				
				} catch(Exception e){
					System.out.println("ConnnectionError!!!");
				}
		
		}
		
		return con;	
	}
	
	
	
	public static Map<String,String> getDBCredentials() {
		
		Map<String,String> credentials = new HashMap<String,String>();
		
		Scanner reader = new Scanner(System.in); 
		
		System.out.println("Enter the database server url: ");
		String url_basic = reader.nextLine();
		credentials.put("url", url_basic);
		
		System.out.println("Enter the name of the database: ");
		String db_name = reader.nextLine();
		credentials.put("db_name", db_name);
		
		System.out.println("Enter the database username: ");
		String username = reader.nextLine();
		credentials.put("username", username);
		
		System.out.println("Enter the database password: ");
		String password = reader.nextLine();
		credentials.put("password", password);
		
		reader.close();
		
		return credentials;
		
		
	}
	

	public static void createDB(Map<String, String> credentials) {
		
        final File f = new File(CreateDatabase.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        String path = f.getParent();
        
        String command = "psql -U " + credentials.get("username") + " -d " + credentials.get("db_name") +
        		" -h " + credentials.get("url") + " -f " + path + "/" + "CreateDatabase.sql";

        try {
            String line;
            Process p = Runtime.getRuntime().exec(command);
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            
            while ((line = input.readLine()) != null) {
              System.out.println(line);
            }
            input.close();
          }
        
          catch (Exception err) {
            err.printStackTrace();
          }
        
	}

	
	public static void createINI(Map<String, String> credentials) {
		
		  final File f = new File(CreateDatabase.class.getProtectionDomain().getCodeSource().getLocation().getPath());
	      String path = f.getParent();
	      
	      PrintWriter writer;
		try {
			writer = new PrintWriter(path+"/database.ini", "UTF-8");
			
		      writer.println("[database]");
		      writer.println("DRIVER=org.postgresql.Driver");
		      
		      String javaUrl = "jdbc:postgresql://" + credentials.get("url") + "/" + 
		    		  credentials.get("db_name") + "?currentSchema=annotationTool";
		      writer.println("CONNECTION_URL=" + javaUrl);
		      
		      writer.println("USERNAME=" + credentials.get("username"));
		      writer.println("PASSWORD=" + credentials.get("password"));
		      
		      writer.println("");
		      writer.println("[databasePython]");
		      
		      writer.println("url=" + credentials.get("url"));
		      writer.println("database=" + credentials.get("db_name"));
		      
		      
		      writer.close();
			
			
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	public static String generateRandomSpecialCharacters(int length) {
	    RandomStringGenerator pwdGenerator = new RandomStringGenerator.Builder().withinRange(33, 45).build();
	    return pwdGenerator.generate(length);
	}
	
	
	public static void createNLPUser(Map<String, String> credentials) {
		
		PreparedStatement ps = null;
    	
    	Connection con = getCon(credentials);	 	
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
	}
	
	
	public static void createAdmin(Map<String, String> credentials) {
		PreparedStatement ps = null;
   
    	Connection con = getCon(credentials);	 	
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
	
	
	public static class Password {

	    public static SecretKeySpec aes = new SecretKeySpec("thebestsecretkey".getBytes(), "AES");
	    public static int iterationNumber = 15;
	    public static int hashSize = 20;
	    public static int saltSize = 20;
	    public static byte[] hashFunction(String pass, byte[] salt) {

	        char[] password = pass.toCharArray();

	        try {
	            PBEKeySpec spec = new PBEKeySpec(password, salt, iterationNumber, hashSize * 8);
	            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
	            return skf.generateSecret(spec).getEncoded();
	        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

	    /**
	     *  It encrypts/decrypts the salt
	     * @param salt
	     * @param encrypt true if we want to encrypt it, false to decrypt it
	     * @return
	     */
	    public static byte[] encryptSalt(byte[] salt, boolean encrypt) {
	        try {
	            Cipher cipher = Cipher.getInstance("AES");
	            if (encrypt) {
	                cipher.init(Cipher.ENCRYPT_MODE, aes);
	            } else {
	                cipher.init(Cipher.DECRYPT_MODE, aes);
	            }
	            return cipher.doFinal(salt);
	        } catch (NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

	    public static byte[] generateSalt() {
	        byte[] salt = new byte[saltSize];
	        new SecureRandom().nextBytes(salt);
	        return salt;
	    }
	    /**
	     * It creates the String to be stored in the database
	     * @param password
	     * @return
	     */
	    public static String hashCode(String password) {

	        byte[] salt = generateSalt();
	        byte[] hash = hashFunction(password, salt);
	        // stored information: hashPassword&&&&&encryptedSalt
	        return DatatypeConverter.printBase64Binary(hash)
	                + "&&&&&" + DatatypeConverter.printBase64Binary(encryptSalt(salt, true));
	    }

	    public static boolean checkPassword(String givenPassword, String correctHashPassword) {

	        String[] split = correctHashPassword.split("&&&&&");
	        byte[] correctHash = DatatypeConverter.parseBase64Binary(split[0]);
	        byte[] salt = encryptSalt(DatatypeConverter.parseBase64Binary(split[1]), false);
	        byte[] givenHash = hashFunction(givenPassword, salt);

	        for (int i = 0; i < givenHash.length && i < correctHash.length; i++) {
	            if (givenHash[i] != correctHash[i]) {
	                return false;
	            }
	        }
	        return true;
	    }

	    public static String hashString(String string) {
	        String ret = null;
	        try {
	            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
	            byte[] bytes = messageDigest.digest(string.getBytes());
	            StringBuilder hex = new StringBuilder(bytes.length * 2);
	            for (byte b : bytes) {
	                hex.append(b);
	            }
	            ret = hex.toString();
	        } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	        }
	        return ret;
	    }

	}
	
	public static void main(String[] args) {
		
		Map<String,String> credentials = getDBCredentials();
		
		createDB(credentials);
		
		createINI(credentials);
		
		createAdmin(credentials);
		
		createNLPUser(credentials);
		
	}

}
