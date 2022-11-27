package no.hvl.feedapp.controller;

import com.google.gson.Gson;
import no.hvl.feedapp.daos.FeedAppUserDAO;
import no.hvl.feedapp.daos.IOTDeviceDAO;
import no.hvl.feedapp.daos.PollDAO;
import no.hvl.feedapp.daos.VoteDAO;
import no.hvl.feedapp.dtos.PollDTO;
import no.hvl.feedapp.model.FeedAppUser;
import no.hvl.feedapp.model.Poll;
import no.hvl.feedapp.mongodb.MongoDBService;
import no.hvl.feedapp.util.DatabaseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
//@Controller
public class PollController {
    Gson gson = new Gson();
    final DatabaseService dbService = new DatabaseService();
    final MongoDBService mDbService = new MongoDBService();
    final PollDAO pollDAO = new PollDAO(dbService);
    final FeedAppUserDAO userDAO = new FeedAppUserDAO(dbService);
    final IOTDeviceDAO deviceDAO = new IOTDeviceDAO(dbService);
    final VoteDAO voteDAO = new VoteDAO(dbService);

    @PostMapping("/polls/{userID}")
    public String createPoll(@RequestBody Poll poll, @PathVariable("userID") String userID) throws IOException {
        if (userDAO.getUserByID(Long.valueOf(userID)) != null) {
            Poll newPoll = poll.setUser(userDAO.getUserByID(Long.valueOf(userID)));
            pollDAO.create(newPoll);
            userDAO.addPoll(newPoll);

            if (newPoll.isOpenPoll()) {
                URL url = new URL("https://dweet.io/dweet/for/polls");
                // GET DWEETS: https://dweet.io/play/
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Accept", "application/json");
                con.setDoOutput(true);
                String pollJson = gson.toJson(new PollDTO(newPoll));
                try(OutputStream os = con.getOutputStream()) {
                    byte[] input = pollJson.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }
                try(BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    System.out.println(response.toString());
                    System.out.println("See dweets here: " + new URL("https://dweet.io/play/#!/dweets/getLatestDweet_get_2"));
                }
            }
            return gson.toJson(new PollDTO(newPoll));
        }
        return String.format("No user with id \"%s\" found!", userID);
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
            @RequestBody Poll editedPoll) throws IOException {

        // check if id is a number
        if (dbService.isNumber(id)) {
            Poll previousPoll = pollDAO.getPollByID(Long.valueOf(id));
            Poll poll = pollDAO.update(editedPoll, Long.valueOf(id));

            if ((!previousPoll.isOpenPoll() && poll.isOpenPoll()) ||
                    (previousPoll.isOpenPoll() && !poll.isOpenPoll())) {
                URL url = new URL("https://dweet.io/dweet/for/polls");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Accept", "application/json");
                con.setDoOutput(true);
                String pollJson = gson.toJson(new PollDTO(poll));
                try (OutputStream os = con.getOutputStream()) {
                    byte[] input = pollJson.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    System.out.println(response.toString());
                }
            }

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
