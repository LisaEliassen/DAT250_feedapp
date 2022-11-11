package no.hvl.feedapp.dtos;

import no.hvl.feedapp.model.IOTDevice;
import no.hvl.feedapp.model.Poll;
import no.hvl.feedapp.model.Vote;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PollDTO {
    private Long pollID;
    private String pollName;
    private String category;
    private String description;
    private String pollResult;
    private Long userID;
    private List<Long> votes = new ArrayList<>();
    private List<Long> iots;

    public PollDTO(Poll poll) {
        this.pollID = poll.getID();
        this.pollName = poll.getName();
        this.category = poll.getCategory();
        this.description = poll.getDescription();
        this.pollResult = poll.getResult();
        this.userID = poll.getUser().getID();
        if (!poll.getVotes().isEmpty() && poll.getVotes() != null) {
            this.votes = poll.getVotes().stream()
                    .map(Vote::getID)
                    .collect(Collectors.toList());
        }
        else {
            this.iots = new ArrayList<>();
        }
        if (!poll.getDevices().isEmpty() && poll.getDevices() != null)  {
            this.iots = poll.getDevices().stream()
                    .map(IOTDevice::getID)
                    .collect(Collectors.toList());
        }
        else {
            this.iots = new ArrayList<>();
        }
    }


    public Long getPollID() {
        return pollID;
    }

    public void setPollID(Long pollID) {
        this.pollID = pollID;
    }

    public String getPollName() {
        return pollName;
    }

    public void setPollName(String pollName) {
        this.pollName = pollName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPollResult() {
        return pollResult;
    }

    public void setPollResult(String pollResult) {
        this.pollResult = pollResult;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public List<Long> getVotes() {
        return votes;
    }

    public void setVotes(List<Long> votes) {
        this.votes = votes;
    }

    public List<Long> getIots() {
        return iots;
    }

    public void setIots(List<Long> iots) {
        this.iots = iots;
    }
}
