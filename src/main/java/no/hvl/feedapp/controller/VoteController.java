package no.hvl.feedapp.controller;

import com.google.gson.Gson;
import com.mongodb.client.MongoDatabase;
import no.hvl.feedapp.daos.FeedAppUserDAO;
import no.hvl.feedapp.daos.IOTDeviceDAO;
import no.hvl.feedapp.daos.PollDAO;
import no.hvl.feedapp.daos.VoteDAO;
import no.hvl.feedapp.dtos.IOTDeviceDTO;
import no.hvl.feedapp.dtos.VoteDTO;
import no.hvl.feedapp.model.IOTDevice;
import no.hvl.feedapp.model.Poll;
import no.hvl.feedapp.model.Vote;
import no.hvl.feedapp.mongodb.MongoDBService;
import no.hvl.feedapp.util.DatabaseService;
import org.bson.Document;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
//@Controller
public class VoteController {
    Gson gson = new Gson();

    final DatabaseService dbService = new DatabaseService();
    final MongoDBService mDbService = new MongoDBService();
    final VoteDAO voteDAO = new VoteDAO(dbService);
    final FeedAppUserDAO userDAO = new FeedAppUserDAO(dbService);
    final PollDAO pollDAO = new PollDAO(dbService);
    final IOTDeviceDAO deviceDAO = new IOTDeviceDAO(dbService);

    @PostMapping("/votes/{pollID}/{userID}")
    public String createUserVote(@RequestBody Vote vote,
                             @PathVariable("pollID") String pollID,
                             @PathVariable("userID") String userID) {
        if (pollID != null && pollDAO.getPollByID(Long.valueOf(pollID)) != null) {
            if (userID != null && userDAO.getUserByID(Long.valueOf(userID)) != null) {
                Vote newVote = vote.setUser(userDAO.getUserByID(Long.valueOf(userID)));
                newVote = newVote.setPoll(pollDAO.getPollByID(Long.valueOf(pollID)));
                voteDAO.create(newVote);
                userDAO.addVote(newVote);

                Document oldPoll = mDbService.createDoc(newVote.getPoll());

                Poll poll = pollDAO.addVote(newVote);

                Document newPoll = mDbService.createDoc(poll);
                mDbService.updateDocument("test", newPoll, oldPoll);

                return gson.toJson(new VoteDTO(newVote));
            }
            return String.format("No associated user found!");
        }
        return String.format("No associated poll found!");
    }

    @PostMapping("/votes/device/{pollID}/{deviceID}")
    public String createDeviceVote(@RequestBody Vote vote,
                             @PathVariable("pollID") String pollID,
                             @PathVariable("deviceID") String deviceID) {
        if (pollID != null && pollDAO.getPollByID(Long.valueOf(pollID)) != null) {
            IOTDevice device = deviceDAO.getDeviceByID(Long.valueOf(deviceID));
            List<Long> devicesForPoll = pollDAO.getPollByID(Long.valueOf(pollID))
                    .getIots()
                    .stream()
                    .map(IOTDevice::getID)
                    .collect(Collectors.toList());
            if (deviceDAO.getDeviceByID(Long.valueOf(deviceID)) != null
                    && devicesForPoll.contains(Long.valueOf(deviceID))) {
                Vote newVote = vote.setIot(deviceDAO.getDeviceByID(Long.valueOf(deviceID)));
                newVote = newVote.setPoll(pollDAO.getPollByID(Long.valueOf(pollID)));
                voteDAO.create(newVote);
                deviceDAO.addVote(newVote);

                Document oldPoll = mDbService.createDoc(newVote.getPoll());

                Poll poll = pollDAO.addVote(newVote);

                Document newPoll = mDbService.createDoc(poll);
                mDbService.updateDocument("test", newPoll, oldPoll);

                return gson.toJson(new VoteDTO(newVote));
            }
            return String.format("No associated device found!");
        }
        return String.format("No associated poll found!");
    }

    @GetMapping("/votes")
    public String getAllVotes() {
        List<Vote> votes = voteDAO.getAllVotes();
        List<VoteDTO> voteDTOs = votes.stream()
                .map(vote -> new VoteDTO(vote))
                .collect(Collectors.toList());
        return gson.toJson(voteDTOs);
    }

    @GetMapping(value = "votes/{id}")
    public String getVoteByID(@PathVariable("id") String id) {
        if (dbService.isNumber(id)) { // check if id is a number
            Vote vote = voteDAO.getVoteByID(Long.valueOf(id));
            if (vote != null) {
                return gson.toJson(new VoteDTO(vote));
            }
            return String.format("Vote with the id \"%s\" not found!", id);
        }
        else {
            return String.format("The id \"%s\" is not a number!", id);
        }
    }

    @PutMapping(value = "votes/{id}")
    public String updateVoteById(
            @PathVariable("id") String id,
            @RequestBody Vote editedVote) {

        // check if id is a number
        if (dbService.isNumber(id)) {
            Vote vote = voteDAO.update(editedVote, Long.valueOf(id));

            if (vote != null) {
                return gson.toJson(new VoteDTO(vote));
            }
            else {
                return gson.toJson("Vote not found or error in edit");
            }
        }
        else {
            return String.format("The id \"%s\" is not a number!", id);
        }
    }

    @DeleteMapping(value = "votes/{id}")
    public String deleteVote(@PathVariable("id") String id) {
        if(dbService.isNumber(id)) {
            Vote vote = voteDAO.delete(Long.valueOf(id));
            if (vote != null) {
                return gson.toJson("Success!");
            }
            else {
                return String.format("Vote with the id \"%s\" not found!", id);
            }
        }
        else {
            return String.format("The id \"%s\" is not a number!", id);
        }
    }
}
