package es.raulsanmartin.postit.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
    @Pattern(regexp = "\\w+")
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

    private String photo = "/assets/img/avatar2.png";

    private String header = "/assets/img/profile-header-image.jpg";

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

    public List<Message> getMessages() {
        return messages;
    }

    @Override
    public String toString() {
        return "User: " + name + " <" + email + ">" + " @" + id;
    }
}
