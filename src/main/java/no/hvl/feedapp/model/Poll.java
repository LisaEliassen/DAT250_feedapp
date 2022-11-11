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
    private String pollName;
    private String category;
    private String description;
    private String pollResult;

    @ManyToOne(targetEntity = FeedAppUser.class, cascade = CascadeType.MERGE)
    private FeedAppUser feedappuser;

    @OneToMany(mappedBy = "poll", targetEntity = Vote.class, cascade = CascadeType.MERGE)
    private List<Vote> votes = new ArrayList<>();

    @OneToMany(mappedBy = "poll", targetEntity = IOTDevice.class, cascade = CascadeType.MERGE)
    private List<IOTDevice> iots = new ArrayList<>();

    public Long getID() {
        return pollID;
    }

    public void setID(Long pollID) {
        this.pollID = pollID;
    }

    public String getName() {
        return this.pollName;
    }

    public void setName(String newName) {
        this.pollName = newName;
    }

    public FeedAppUser getUser() {
        return this.feedappuser;
    }

    public Poll setUser(FeedAppUser user) {
        this.feedappuser = user;
        return this;
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

    public String getResult() {
        return pollResult;
    }

    public void setResult(String pollResult) {
        this.pollResult = pollResult;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    // TODO: update Result!
    public Poll addVote(Vote vote) {
        this.votes.add(vote);
        //updateResult();
        return this;
    }

    public List<Vote> getAllVotes() {
        return this.votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }


    public List<IOTDevice> getDevices() {
        return iots;
    }

    public void setDevices(List<IOTDevice> iots) {
        this.iots = iots;
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
            // Todo: update result of pollResult. We need to potentially use a HashMap to make things efficient.
            if (vote.getVote() == "yes") {
                yes++;
            }
            else if (vote.getVote() == "no") {
                no++;
            }
        }*/
        this.pollResult = String.format("yes: \"%s\", no: \"%s\"", yes, no);
    }

}
