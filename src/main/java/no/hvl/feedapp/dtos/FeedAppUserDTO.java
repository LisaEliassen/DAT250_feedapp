package no.hvl.feedapp.dtos;

import no.hvl.feedapp.model.FeedAppUser;
import no.hvl.feedapp.model.Poll;
import no.hvl.feedapp.model.Vote;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FeedAppUserDTO {
    private Long userID;
    private String username;
    private String firstName;
    private String lastName;
    private boolean admin;
    private String password;
    private List<Long> polls;
    private List<Long> votes;

    public FeedAppUserDTO(FeedAppUser user) {
        this.userID = user.getID();
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.admin = user.isAdmin();
        this.password = user.getPassword();
        if(!user.getVotes().isEmpty() && user.getVotes() != null) {
            this.votes = user.getVotes().stream()
                    .map(Vote::getID)
                    .collect(Collectors.toList());
        }
        else {
            System.out.println("Somehow user's votes list is either empty or null.");
            this.votes = new ArrayList<>();
        }
        if(!user.getPolls().isEmpty() && user.getPolls() != null) {
            this.polls = user.getPolls().stream()
                    .map(Poll::getID)
                    .collect(Collectors.toList());
        }
        else {
            this.polls = new ArrayList<>();
        }
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Long> getPolls() {
        return polls;
    }

    public void setPolls(List<Long> polls) {
        this.polls = polls;
    }

    public List<Long> getVotes() {
        return votes;
    }

    public void setVotes(List<Long> votes) {
        this.votes = votes;
    }
}
