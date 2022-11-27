package no.hvl.feedapp.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="FeedAppUsers")
public class FeedAppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userID;
    private String username;
    private String firstName;
    private String lastName;
    private boolean admin;
    private String password;

    //@JsonIgnoreProperties({"feedappuser"})
    @OneToMany(mappedBy = "feedappuser", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Poll> polls = new ArrayList<>();

    //@JsonIgnoreProperties({"feedappuser"})
    @OneToMany(mappedBy = "feedappuser", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Vote> votes = new ArrayList<>();

    public Long getID() {
        return this.userID;
    }

    public void setID(Long userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String newName) {
        this.firstName = newName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isAdmin() {
        return this.admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public List<Poll> getPolls() {
        return this.polls;
    }

    public FeedAppUser addPoll(Poll poll) {
        this.polls.add(poll);
        return this;
    }

    public void setPolls(List<Poll> polls) {
        this.polls = polls;
    }

    public List<Vote> getVotes() {
        return this.votes;
    }

    public FeedAppUser addVote(Vote vote) {
        this.votes.add(vote);
        return this;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void removePoll(Poll poll) {
        this.polls.remove(poll);
    }

    public void removeVote(Vote vote) {
        this.votes.remove(vote);
    }
}
