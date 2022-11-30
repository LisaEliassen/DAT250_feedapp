package no.hvl.feedapp.dtos;

import no.hvl.feedapp.model.IOTDevice;
import no.hvl.feedapp.model.Poll;
import no.hvl.feedapp.model.Vote;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PollDTO {
    private Long pollID;
    private String question;
    private String category;
    private boolean openPoll;
    private boolean publicPoll;
    private int yesCount;
    private int noCount;
    private Long userID;
    private List<Long> votes;
    private List<Long> iots;

    public PollDTO(Poll poll) {
        this.pollID = poll.getID();
        this.question = poll.getQuestion();
        this.category = poll.getCategory();
        this.openPoll = poll.isOpenPoll();
        this.publicPoll = poll.isPublicPoll();
        this.yesCount = poll.getYesCount();
        this.noCount = poll.getNoCount();
        this.userID = poll.getFeedappuser().getID();
        if (!poll.getVotes().isEmpty() && poll.getVotes() != null) {
            this.votes = poll.getVotes().stream()
                    .map(Vote::getID)
                    .collect(Collectors.toList());
        }
        else {
            this.votes = new ArrayList<>();
        }
        if (!poll.getIots().isEmpty() && poll.getIots() != null)  {
            this.iots = poll.getIots().stream()
                    .map(IOTDevice::getID)
                    .collect(Collectors.toList());
        }
        else {
            this.iots = new ArrayList<>();
        }
    }

    public Long getPollID() {
        return this.pollID;
    }

    public void setPollID(Long pollID) {
        this.pollID = pollID;
    }

    public String getQuestion() {
        return this.question;
    }

    public void setQuestion(String poll) {
        this.question = poll;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isOpenPoll() {
        return openPoll;
    }

    public void setOpenPoll(boolean openPoll) {
        this.openPoll = openPoll;
    }

    public boolean isPublicPoll() {
        return publicPoll;
    }

    public void setPublicPoll(boolean publicPoll) {
        this.publicPoll = publicPoll;
    }

    public Long getUserID() {
        return this.userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public List<Long> getVotes() {
        return this.votes;
    }

    public void setVotes(List<Long> votes) {
        this.votes = votes;
    }

    public List<Long> getIots() {
        return this.iots;
    }

    public void setIots(List<Long> iots) {
        this.iots = iots;
    }

    public int getYesCount() {
        return yesCount;
    }

    public void setYesCount(int yesCount) {
        this.yesCount = yesCount;
    }

    public int getNoCount() {
        return noCount;
    }

    public void setNoCount(int noCount) {
        this.noCount = noCount;
    }
}
