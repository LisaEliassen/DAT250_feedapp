package no.hvl.feedapp.dtos;

import no.hvl.feedapp.model.IOTDevice;
import no.hvl.feedapp.model.Vote;

import java.util.List;
import java.util.stream.Collectors;

public class IOTDeviceDTO {
    public Long deviceID;
    public Long pollID;
    private List<Long> votes;

    public IOTDeviceDTO(IOTDevice device) {
        this.deviceID = device.getID();
        this.pollID = device.getPoll().getID();
        if(!device.getVotes().isEmpty() && device.getVotes() != null) {
            this.votes = device.getVotes().stream()
                    .map(Vote::getID)
                    .collect(Collectors.toList());
        }
    }

    public Long getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(Long deviceID) {
        this.deviceID = deviceID;
    }

    public Long getPollID() {
        return pollID;
    }

    public void setPollID(Long pollID) {
        this.pollID = pollID;
    }

    public List<Long> getVotes() {
        return votes;
    }

    public void setVotes(List<Long> votes) {
        this.votes = votes;
    }
}
