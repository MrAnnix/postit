package es.raulsanmartin.postit.model;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.security.SecureRandom;

public class User {
    private String id;    //Nickname w/o @

    private String name;  //User full name

    private String email; //User email

    private byte[] pswd;  //User hashed password

    private byte[] salt;  //User salt to hash password with PBKDF2 algorithm, so pswd=hash(salt+realPassword)

    private String photo; //User profle photo

    private String header;//User profile header photo

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //PASSWORD HANDLING
    public Boolean checkPassword(String password) {
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 10000, 512);
            SecretKey key = skf.generateSecret(spec);
            return Arrays.equals(key.getEncoded(), pswd);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public void setPassword(String password) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[8];
        random.nextBytes(salt);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 10000, 512);
            SecretKey key = skf.generateSecret(spec);
            this.salt = salt;
            this.pswd = key.getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getNick() {
        return "@" + id;
    }

    @Override
    public String toString() {
        return "User: " + name + " <" + email + ">" + " @" + id;
    }
}
