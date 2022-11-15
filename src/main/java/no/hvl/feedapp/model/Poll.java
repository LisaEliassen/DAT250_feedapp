package no.hvl.feedapp.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Polls")
//@JsonIgnoreProperties({"feedappuser", "votes", "iots"})
public class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pollID;
    private String title;
    private String category;
    private String result;

    private boolean openPoll;
    private boolean publicPoll;

    @ManyToOne(targetEntity = FeedAppUser.class)
    private FeedAppUser feedappuser;

    @OneToMany(mappedBy = "poll", targetEntity = Vote.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Vote> votes = new ArrayList<>();

    @OneToMany(mappedBy = "poll", targetEntity = IOTDevice.class, cascade ={CascadeType.PERSIST, CascadeType.MERGE})
    private List<IOTDevice> iots = new ArrayList<>();


    // TODO: update Result!
    public Poll addVote(Vote vote) {
        this.votes.add(vote);
        //updateResult();
        return this;
    }

    public Poll addDevice(IOTDevice device) {
        this.iots.add(device);
        return this;
    }

    private void updateResult() {
        int yes = 0;
        int no = 0;
        /*
        for (Long voteID : this.votes) {
            // Todo: update result of result. We need to potentially use a HashMap to make things efficient.
            if (vote.getVote() == "yes") {
                yes++;
            }
            else if (vote.getVote() == "no") {
                no++;
            }
        }*/
        this.result = String.format("yes: \"%s\", no: \"%s\"", yes, no);
    }

    public Long getID() {
        return pollID;
    }

    public void setID(Long pollID) {
        this.pollID = pollID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getCategory() {
        return category;
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public FeedAppUser getFeedappuser() {
        return this.feedappuser;
    }

    public void setFeedappuser(FeedAppUser feedappuser) {
        this.feedappuser = feedappuser;
    }

    public Poll setUser(FeedAppUser user) {
        this.feedappuser = user;
        return this;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public List<IOTDevice> getIots() {
        return this.iots;
    }

    public void setIots(List<IOTDevice> iots) {
        this.iots = iots;
    }
}
