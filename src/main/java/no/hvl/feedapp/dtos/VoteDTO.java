package no.hvl.feedapp.dtos;

import no.hvl.feedapp.model.Vote;

public class VoteDTO {

    private Long voteID;
    private String vote;
    private Long userID;
    private Long pollID;
    private Long deviceID;

    public VoteDTO(Vote vote) {
        this.voteID = vote.getID();
        this.vote = vote.getVote();
        if (vote.getUser() != null) {
            this.userID = vote.getUser().getID();
        }
        this.pollID = vote.getPoll().getID();
        if (vote.getIot() != null) {
            this.deviceID = vote.getIot().getID();
        }
    }

    public Long getVoteID() {
        return voteID;
    }

    public void setVoteID(Long voteID) {
        this.voteID = voteID;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Long getPollID() {
        return pollID;
    }

    public void setPollID(Long pollID) {
        this.pollID = pollID;
    }

    public Long getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(Long deviceID) {
        this.deviceID = deviceID;
    }
}
