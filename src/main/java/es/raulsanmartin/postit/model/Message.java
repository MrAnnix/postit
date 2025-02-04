package es.raulsanmartin.postit.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    @NotBlank
    @Size(max = 280)
    private String text;

    @ManyToOne(optional = false)
    private User user;

    @ManyToOne
    private Message responseTo;

    @ManyToMany
    @JoinTable(name="likes")
    private List<User> likes;

    @Column(nullable = false)
    private long timestamp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Message getResponseTo() {
        return responseTo;
    }

    public void setResponseTo(Message responseTo) {
        this.responseTo = responseTo;
    }

    public List<User> getLikes() {
        return likes;
    }

    public void setLikes(List<User> likes) {
        this.likes = likes;
    }

    public void addLike(User user) {
        this.likes.add(user);
    }

    public void delLike(User user) {
        this.likes.remove(user);
    }

    public Boolean isLiked(User user) {
        return likes.contains(user);
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}