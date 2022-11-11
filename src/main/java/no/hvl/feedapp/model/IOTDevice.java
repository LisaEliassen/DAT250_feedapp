package no.hvl.feedapp.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="IOTDevices")
public class IOTDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deviceID;

    @ManyToOne(targetEntity = Poll.class)
    @JoinColumn(name = "pollID")
    private Poll poll;

    @OneToMany(mappedBy="iot", cascade = CascadeType.MERGE)
    private List<Vote> votes;

    public Long getID() {
        return deviceID;
    }

    public void setID(Long deviceID) {
        this.deviceID = deviceID;
    }

    public Poll getPoll() {
        return this.poll;
    }

    public IOTDevice setPoll(Poll poll) {
        this.poll = poll;
        return this;
    }

    public List<Vote> getVotes() {
        return this.votes;
    }

    public void setVote(List<Vote> votes) {
        this.votes = votes;
    }

    public IOTDevice addVote(Vote vote) {
        this.votes.add(vote);
        return this;
    }
}
