package no.hvl.feedapp.util;

public class Token {
    private String status;
    private String token;

    private Long userID;

    public Token(String token, Long userID, String status) {
        setToken(token);
        setUserID(userID);
        setStatus(status);
    }
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
