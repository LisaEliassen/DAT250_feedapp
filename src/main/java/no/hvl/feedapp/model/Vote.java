package no.hvl.feedapp.model;

import javax.persistence.*;

@Entity
@Table(name="Votes")
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voteID;
    private String vote;

    @ManyToOne(targetEntity = FeedAppUser.class)
    @JoinColumn(name = "feedappuser")
    private FeedAppUser feedappuser;

    @ManyToOne(targetEntity = Poll.class)
    @JoinColumn(name = "pollID")
    private Poll poll;

    @ManyToOne(targetEntity = IOTDevice.class)
    private IOTDevice iot;

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public FeedAppUser getUser() {
        return feedappuser;
    }

    public Vote setUser(FeedAppUser feedappuser) {
        this.feedappuser = feedappuser;
        return this;
    }

    public Poll getPoll() {
        return poll;
    }

    public Vote setPoll(Poll poll) {
        this.poll = poll;
        return this;
    }

    public Long getID() {
        return voteID;
    }

    public void setID(Long voteID) {
        this.voteID = voteID;
    }

    public IOTDevice getIot() {
        return iot;
    }

    public Vote setIot(IOTDevice iot) {
        this.iot = iot;
        return this;
    }
}
