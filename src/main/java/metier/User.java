package metier;

import org.bson.Document;

import javax.print.Doc;
import java.util.Date;

public class User {
    private String email;
    private String password;
    private String type;
    private String accessToken;
    private Date createdAt;
    private Date updatedAt;
    public User() {
    }

    public User(String email, String password, String type, String accessToken, Date createdAt, Date updatedAt) {
        this.email = email;
        this.password = password;
        this.type = type;
        this.accessToken = accessToken;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Document userToDocument() {
        return new Document()
                .append("email", this.email)
                .append("password", this.password)
                .append("type", this.type)
                .append("accessToken", this.accessToken)
                .append("createdAt", this.createdAt)
                .append("updatedAt", this.updatedAt);
    }
}
