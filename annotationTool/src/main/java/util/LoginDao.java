package util;

import java.security.SecureRandom;

import javax.servlet.http.Cookie;
import javax.xml.bind.DatatypeConverter;
//import org.apache.tomcat.util.codec.binary.Base64;
import beans.LoginBean;
import beans.User;

public class LoginDao {

    public static boolean validate(LoginBean bean) {

        String email = bean.getUsername();
        String givenPassword = bean.getPassword();

        if (!Queries.userExists(email)) {
            return false;
        } else {
            String correctHashPassword = Queries.getPassword(email);

            if (correctHashPassword == null) {
                return false;
            }

            return Password.checkPassword(givenPassword, correctHashPassword);
        }

    }


    public static Cookie tokenBuilder(String email) {

        String fromDatabase = Queries.getPassword(email);
        String[] split = fromDatabase.split("&&&&&");
        String passwordSalt = split[1];
        byte[] cookieSaltByte = new byte[Password.saltSize];
        new SecureRandom().nextBytes(cookieSaltByte);
        String cookieSalt = DatatypeConverter.printBase64Binary(cookieSaltByte);
        String token = Password.hashString(passwordSalt + "&&&&&" + cookieSalt) + "&&&&&" + email + "&&&&&" + cookieSalt;
        Cookie cookie = new Cookie("token", token);
        return cookie;
    }
    
    public static Cookie tokenAdminBuilder(String email) {
    	
    	String fromDatabase = Queries.getPassword(email);
    	String[] split = fromDatabase.split("&&&&&");
    	String passwordSalt = split[1];
        byte[] cookieSaltByte = new byte[Password.saltSize];
        new SecureRandom().nextBytes(cookieSaltByte);
        String cookieSalt = DatatypeConverter.printBase64Binary(cookieSaltByte);
        String token = Password.hashString(passwordSalt + "&&&&&" + cookieSalt) + "&&&&&" + email + "&&&&&" + cookieSalt;
        Cookie cookie = new Cookie("adminToken", token);
        return cookie;
    }
    

    public static String getUsernameCookie(String cookieData) {

        String[] splitCookie = cookieData.split("&&&&&");
        String passAndCookieSalt = splitCookie[0];
        String username = splitCookie[1];
        String cookieSalt = splitCookie[2];
        
        String databaseData = "";
        if(Queries.getPassword(username) != null) {
        	databaseData = Queries.getPassword(username);
        }
        String[] splitDatabase = databaseData.split("&&&&&");
        String passwordSalt = splitDatabase[1];
        
        if (Password.hashString(passwordSalt + "&&&&&" + cookieSalt).equals(passAndCookieSalt)) {
        	return username;
        } else {
        	return null;
        }
    }
	

    public static User getToken(Cookie[] cookies) {
    	//Rights rights = null;
        String cookieData = null;
        String username = null;
        
        if (cookies != null) {
        	
            for (Cookie cookie : cookies) {
            	
                if (cookie.getName().equals("token")) {
                    cookieData = cookie.getValue();
                    //rights = Rights.USER;
                } else if (cookie.getName().equals("adminToken")) {
                    cookieData = cookie.getValue();
                    //rights = Rights.ADMIN;
                }
            }
            if (cookieData != null) {
                username = getUsernameCookie(cookieData);
            }
        }     
        
        return Queries.getUser(username);
    }
}