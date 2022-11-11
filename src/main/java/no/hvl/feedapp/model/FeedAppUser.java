package no.hvl.feedapp.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="FeedAppUsers")
public class FeedAppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;
    private String username;
    private String firstName;
    private String lastName;
    private boolean admin;
    private String password;

    //@JsonIgnoreProperties({"feedappuser"})
    @OneToMany(mappedBy = "feedappuser", cascade = CascadeType.MERGE)
    private List<Poll> polls = new ArrayList<>();

    //@JsonIgnoreProperties({"feedappuser"})
    @OneToMany(mappedBy = "feedappuser", cascade = CascadeType.MERGE)
    private List<Vote> votes = new ArrayList<>();

    public FeedAppUser() {

    }

    public FeedAppUser(Long id) {
        this.setID(id);
    }

    public Long getID() {
        return userID;
    }

    public void setID(Long userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
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
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public List<Poll> getPolls() {
        return polls;
    }

    public FeedAppUser addPoll(Poll poll) {
        this.polls.add(poll);
        return this;
    }

    public void setPolls(List<Poll> polls) {
        this.polls = polls;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public FeedAppUser addVote(Vote vote) {
        this.votes.add(vote);
        return this;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
