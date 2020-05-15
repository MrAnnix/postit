package es.raulsanmartin.postit.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.lang.Math;

import javax.net.ssl.HttpsURLConnection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import es.raulsanmartin.postit.MD5Util;

@Entity
public class User {

    @Id
    @Column(unique = true, nullable = false, length = 32)
    @NotBlank
    @Pattern(regexp = "\\w+")
    @Size(max = 32)
    private String id;

    @Column(unique = false, nullable = false, length = 64)
    @NotBlank
    @Pattern(regexp = "^[0-9a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ,.'-´`]+$")
    @Size(max = 64)
    private String name;

    @Column(unique = true, nullable = false, length = 64)
    @Email
    @NotBlank
    @Size(max = 64)
    private String email;

    @Column(nullable = false)
    @NotBlank
    @Size(min = 8)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="following",
    joinColumns=@JoinColumn(name="follower_id"),
    inverseJoinColumns=@JoinColumn(name="followed_id"))
    private List<User> following;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="following",
    joinColumns=@JoinColumn(name="followed_id"),
    inverseJoinColumns=@JoinColumn(name="follower_id"))
    private List<User> followers;

    private String photo = "/assets/img/avatar2.png";

    private String header = "/assets/img/profile-header-image.jpg";

    private String bio;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private List<Message> messages;

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

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public void addFollower(User user) {
        this.followers.add(user);
    }

    public void delFollower(User user) {
        this.followers.remove(user);
    }

    public List<User> getFollowing() {
        return following;
    }

    public void setFollowing(List<User> following) {
        this.following = following;
    }

    public void addFollowing(User user) {
        this.following.add(user);
    }

    public void delFollowing(User user) {
        this.following.remove(user);
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setProfileGravatarInfoByEmail(String email) {
        String hash = MD5Util.md5Hex(email.trim().toLowerCase());
        this.photo = "https://www.gravatar.com/avatar/" + hash;

        try {
            URL gravatarEndpoint = new URL("https://www.gravatar.com/" + hash + ".json");
            HttpsURLConnection connection = (HttpsURLConnection) gravatarEndpoint.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            
            int responseCode = connection.getResponseCode();

            if (responseCode != 200) {
                throw new Exception(responseCode + " - Something went wrong");
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String inputLine;
		    StringBuffer gravatarResponse = new StringBuffer();
    
		    while ((inputLine = in.readLine()) != null) {
		    	gravatarResponse.append(inputLine);
		    }
            in.close();

            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(gravatarResponse.toString());
            
            JSONArray jsonArr = (JSONArray) json.get("entry");
            JSONObject field = (JSONObject) jsonArr.get(0);
            String bio = (String) field.get("aboutMe");

            this.bio = bio;
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void obtainRandomHeader() {
        this.header = "/assets/img/background" + Integer.toString((int)(Math.random() * 5) + 1) + ".jpg";
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getNick() {
        return "@" + id;
    }

    public List<Message> getMessages() {
        return messages;
    }

    @Override
    public String toString() {
        return "User: " + name + " <" + email + ">" + " @" + id;
    }
}
