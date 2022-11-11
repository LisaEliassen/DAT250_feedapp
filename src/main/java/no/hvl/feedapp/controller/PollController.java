package no.hvl.feedapp.controller;

import com.google.gson.Gson;
import no.hvl.feedapp.daos.FeedAppUserDAO;
import no.hvl.feedapp.daos.IOTDeviceDAO;
import no.hvl.feedapp.daos.PollDAO;
import no.hvl.feedapp.daos.VoteDAO;
import no.hvl.feedapp.dtos.PollDTO;
import no.hvl.feedapp.model.FeedAppUser;
import no.hvl.feedapp.model.Poll;
import no.hvl.feedapp.util.DatabaseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
//@Controller
public class PollController {
    Gson gson = new Gson();
    final DatabaseService dbService = new DatabaseService();
    final PollDAO pollDAO = new PollDAO(dbService);
    final FeedAppUserDAO userDAO = new FeedAppUserDAO(dbService);
    final IOTDeviceDAO deviceDAO = new IOTDeviceDAO(dbService);
    final VoteDAO voteDAO = new VoteDAO(dbService);

    @PostMapping("/polls/{userID}")
    public String createPoll(@RequestBody Poll poll, @PathVariable("userID") String userID) {
        Poll newPoll = poll.setUser(userDAO.getUserByID(Long.valueOf(userID)));
        pollDAO.create(newPoll);
        userDAO.addPoll(newPoll);
        return gson.toJson(new PollDTO(newPoll));
    }

    @GetMapping("/polls")
    public String getAllPolls() {
        List<Poll> polls = pollDAO.getAllPolls();
        if (polls.isEmpty()) {
            return gson.toJson(polls);
        }
        List<PollDTO> pollDTOS = polls.stream()
                .map(poll -> new PollDTO(poll))
                .collect(Collectors.toList());
        return gson.toJson(pollDTOS);
    }

    @GetMapping(value = "polls/{id}")
    public String getPollByID(@PathVariable("id") String id) {
        if (dbService.isNumber(id)) { // check if id is a number
            Poll poll = pollDAO.getPollByID(Long.valueOf(id));
            if (poll != null) {
                return gson.toJson(new PollDTO(poll));
            }
            return String.format("Poll with the id \"%s\" not found!", id);
        }
        else {
            return String.format("The id \"%s\" is not a number!", id);
        }
    }

    @PutMapping(value = "polls/{id}")
    public String updatePollById(
            @PathVariable("id") String id,
            @RequestBody Poll editedPoll) {

        // check if id is a number
        if (dbService.isNumber(id)) {
            Poll poll = pollDAO.update(editedPoll, Long.valueOf(id));

            if (poll != null) {
                return gson.toJson(new PollDTO(poll));
            }
            else {
                return gson.toJson("Poll not found or error in edit");
            }
        }
        else {
            return String.format("The id \"%s\" is not a number!", id);
        }
    }

    @DeleteMapping(value = "polls/{id}")
    public String deletePoll(@PathVariable("id") String id) {
        if(dbService.isNumber(id)) {
            Poll poll = pollDAO.delete(Long.valueOf(id));
            if (poll != null) {
                return gson.toJson("Success!");
            }
            else {
                return String.format("Poll with the id \"%s\" not found!", id);
            }
        }
        else {
            return String.format("The id \"%s\" is not a number!", id);
        }
    }

    @GetMapping(value = "result/{id}")
    public String getPollResultByID(@PathVariable("id") String id) {
        if (dbService.isNumber(id)) { // check if id is a number
            Poll poll = pollDAO.getPollByID(Long.valueOf(id));
            if (poll != null) {
                return gson.toJson(poll.getResult());
            }
            return String.format("Poll with the id \"%s\" not found!", id);
        }
        else {
            return String.format("The id \"%s\" is not a number!", id);
        }
    }
}
