package no.hvl.feedapp.util;

public class Token {
    private String status;
    private String token;
    private Long userID;
    private Long deviceID;

    public Token(String token, Long userID, Long deviceID, String status) {
        setToken(token);
        setUserID(userID);
        setDeviceID(deviceID);
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

    public Long getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(Long deviceID) {
        this.deviceID = deviceID;
    }

}
