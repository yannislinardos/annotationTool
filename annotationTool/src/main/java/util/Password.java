package util;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class Password {

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