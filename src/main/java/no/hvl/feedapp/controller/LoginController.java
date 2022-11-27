package no.hvl.feedapp.controller;

import com.google.gson.Gson;
import no.hvl.feedapp.daos.FeedAppUserDAO;
import no.hvl.feedapp.daos.IOTDeviceDAO;
import no.hvl.feedapp.model.FeedAppUser;
import no.hvl.feedapp.model.IOTDevice;
import no.hvl.feedapp.util.DatabaseService;
import no.hvl.feedapp.util.DeviceConnectRequest;
import no.hvl.feedapp.util.LoginRequest;
import no.hvl.feedapp.util.Token;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Base64;


@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class LoginController {
    private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();
    final DatabaseService dbService = new DatabaseService();

    final FeedAppUserDAO userDAO = new FeedAppUserDAO(dbService);
    final IOTDeviceDAO deviceDAO = new IOTDeviceDAO(dbService);
    Gson gson = new Gson();


    public String getToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest){
        FeedAppUser user = userDAO.getUserByUsername(loginRequest.getUsername());
        if (user != null) {
            if (user.getPassword().equals(loginRequest.getPassword())) {
                return gson.toJson(new Token(getToken(),user.getID(), null,"Login Success"));
            }
        }
        return gson.toJson(new Token(null, null, null,"Login failed"));
    }

    @PostMapping("/connectDevice")
    public String login(@RequestBody DeviceConnectRequest connectRequest){
        if (connectRequest.getDeviceID() != null) {
            IOTDevice device = deviceDAO.getDeviceByID(Long.valueOf(connectRequest.getDeviceID()));
            if (device != null) {
                return gson.toJson(new Token(getToken(),null, device.getID(), "Connection Success"));
            }
        }
        return gson.toJson(new Token(null, null, null,"Connection failed"));
    }
}
