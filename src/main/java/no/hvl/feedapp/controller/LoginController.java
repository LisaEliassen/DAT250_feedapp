package no.hvl.feedapp.controller;

import com.google.gson.Gson;
import no.hvl.feedapp.daos.FeedAppUserDAO;
import no.hvl.feedapp.model.FeedAppUser;
import no.hvl.feedapp.util.DatabaseService;
import no.hvl.feedapp.util.LoginRequest;
import no.hvl.feedapp.util.Token;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Base64;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {
    private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();
    final DatabaseService dbService = new DatabaseService();

    final FeedAppUserDAO userDAO = new FeedAppUserDAO(dbService);
    Gson gson = new Gson();


    public String getToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        System.out.println(base64Encoder.encodeToString(randomBytes));
        return base64Encoder.encodeToString(randomBytes);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest){
        System.out.println(loginRequest.getUsername());
        FeedAppUser user = userDAO.getUserByUsername(loginRequest.getUsername());
        if (user != null) {
            if (user.getPassword().equals(loginRequest.getPassword())) {
                System.out.println(loginRequest.getPassword());
                return gson.toJson(new Token(getToken()));
            }
        }
        return gson.toJson("status: 'failed'");
    }
}
