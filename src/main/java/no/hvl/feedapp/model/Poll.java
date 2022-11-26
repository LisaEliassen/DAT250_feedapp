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
    private String title = "";
    private String category = "";
    private int yesCount = 0;
    private int noCount = 0;
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


        if (vote.getVote().equals("Yes")) {
            System.out.println("The vote equals Yes");
            this.addYesVote();
            return this;
        }
        else if (vote.getVote().equals("No")) {
            this.addNoVote();
            return this;
        }
        System.out.println("Neither yes or no");
        return this;
    }

    public Poll addDevice(IOTDevice device) {
        this.iots.add(device);
        return this;
    }

    public Long getID() {
        return pollID;
    }

    public Poll setID(Long pollID) {
        this.pollID = pollID;
        return this;
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

    public int getYesCount() {
        return this.yesCount;
    }

    public void setYesCount(int yesCount) {
        this.yesCount = yesCount;
    }

    public void addYesVote() {
       this.yesCount = getYesCount() + 1;
    }

    public int getNoCount() {
        return this.noCount;
    }

    public void setNoCount(int noCount) {
        this.noCount = noCount;
    }

    public void addNoVote() {
        this.noCount = getNoCount() + 1;
    }

    public String getResult() {
        return "yes: " + getYesCount() + ", no: " + getNoCount();
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
