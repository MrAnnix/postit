package es.raulsanmartin.postit.model;

public class User {
    private Integer id;

    private String name;

    private String email;

    private String nick;

    private String pswd;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getNick() {
        return "@" + nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    @Override
    public String toString() {
        return "User: " + name + " <" + email + ">" + " @" + nick;
    }
}
