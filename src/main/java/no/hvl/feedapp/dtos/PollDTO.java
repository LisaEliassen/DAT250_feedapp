package no.hvl.feedapp.dtos;

import no.hvl.feedapp.model.IOTDevice;
import no.hvl.feedapp.model.Poll;
import no.hvl.feedapp.model.Vote;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PollDTO {
    private Long pollID;
    private String title;
    private String category;
    private boolean isOpen;
    private boolean isPublic;

    private int yesCount;

    private int noCount;
    private Long userID;
    private List<Long> votes;
    private List<Long> iots;

    public PollDTO(Poll poll) {
        this.pollID = poll.getID();
        this.title = poll.getTitle();
        this.category = poll.getCategory();
        this.isOpen = poll.isOpenPoll();
        this.isPublic = poll.isPublicPoll();
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

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String poll) {
        this.title = poll;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
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
}
