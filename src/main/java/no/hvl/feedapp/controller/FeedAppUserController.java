package no.hvl.feedapp.controller;

import com.google.gson.Gson;
import no.hvl.feedapp.daos.FeedAppUserDAO;
import no.hvl.feedapp.dtos.FeedAppUserDTO;
import no.hvl.feedapp.dtos.IOTDeviceDTO;
import no.hvl.feedapp.model.FeedAppUser;
import no.hvl.feedapp.model.IOTDevice;
import no.hvl.feedapp.util.DatabaseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
//@Controller
public class FeedAppUserController {
    Gson gson = new Gson();

    final DatabaseService dbService = new DatabaseService();
    final FeedAppUserDAO userDAO = new FeedAppUserDAO(dbService);

    @PostMapping("/users")
    public String createUser(@RequestBody FeedAppUser user) {
        userDAO.create(user);

        return gson.toJson(new FeedAppUserDTO(user));
    }

    @GetMapping("/users")
    public String getAllUsers() {
        List<FeedAppUser> users = userDAO.getAllUsers();
        List<FeedAppUserDTO> userDTOs = users.stream()
                .map(user -> new FeedAppUserDTO(user))
                .collect(Collectors.toList());
        return gson.toJson(userDTOs);
    }

    @GetMapping(value = "users/{id}")
    public String getUserByID(@PathVariable("id") String id) {
        if (dbService.isNumber(id)) { // check if id is a number
            FeedAppUser user = userDAO.getUserByID(Long.valueOf(id));
            if (user != null) {
                return gson.toJson(new FeedAppUserDTO(user));
            }
            return String.format("User with the id \"%s\" not found!", id);
        }
        else {
            return String.format("The id \"%s\" is not a number!", id);
        }
    }

    @GetMapping(value = "users/username/{username}")
    public String getUserByUsername(@PathVariable("username") String username) {

        FeedAppUser user = userDAO.getUserByUsername(username);
        if (user != null) {
            return gson.toJson(new FeedAppUserDTO(user));
        }
        return String.format("User with the username \"%s\" not found!", username);
    }

    @PutMapping(value = "users/{id}")
    public String updateUserById(
            @PathVariable("id") String id,
            @RequestBody FeedAppUser editedUser) {

        // check if id is a number
        if (dbService.isNumber(id)) {
            FeedAppUser user = userDAO.update(editedUser, Long.valueOf(id));

            if (user != null) {
                return gson.toJson(new FeedAppUserDTO(user));
            }
            else {
                return gson.toJson("User not found or error in edit");
            }
        }
        else {
            return String.format("The id \"%s\" is not a number!", id);
        }
    }

    @DeleteMapping(value = "users/{id}")
    public String deleteUser(@PathVariable("id") String id) {
        if(dbService.isNumber(id)) {
            FeedAppUser user = userDAO.delete(Long.valueOf(id));
            if (user != null) {
                return gson.toJson("Success!");
            }
            else {
                return String.format("User with the id \"%s\" not found!", id);
            }
        }
        else {
            return String.format("The id \"%s\" is not a number!", id);
        }
    }
}
