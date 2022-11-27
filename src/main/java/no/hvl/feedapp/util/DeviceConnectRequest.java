package no.hvl.feedapp.util;

public class DeviceConnectRequest {
    private String deviceID;
    private String pollID;
    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getPollID() {
        return pollID;
    }

    public void setPollID(String pollID) {
        this.pollID = pollID;
    }
}
