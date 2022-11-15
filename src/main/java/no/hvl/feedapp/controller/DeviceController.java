package no.hvl.feedapp.controller;

import com.google.gson.Gson;
import no.hvl.feedapp.daos.IOTDeviceDAO;
import no.hvl.feedapp.daos.PollDAO;
import no.hvl.feedapp.dtos.IOTDeviceDTO;
import no.hvl.feedapp.model.IOTDevice;
import no.hvl.feedapp.model.Poll;
import no.hvl.feedapp.util.DatabaseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
//@Controller
public class DeviceController {
    Gson gson = new Gson();

    final DatabaseService dbService = new DatabaseService();
    final IOTDeviceDAO iotDAO = new IOTDeviceDAO(dbService);
    final PollDAO pollDAO = new PollDAO(dbService);

    @PostMapping("/devices/{pollID}")
    public String createDevice(@RequestBody IOTDevice iot, @PathVariable("pollID") String pollID) {
        IOTDevice device = iot.setPoll(pollDAO.getPollByID(Long.valueOf(pollID)));
        iotDAO.create(iot);
        pollDAO.addDevice(device);
        return gson.toJson(new IOTDeviceDTO(iot));
    }

    @GetMapping("/devices")
    public String getAllDevices() {
        List<IOTDevice> devices = iotDAO.getAllDevices();
        if (!devices.isEmpty()) {
            List<IOTDeviceDTO> deviceDTOs = devices.stream()
                    .map(device -> new IOTDeviceDTO(device))
                    .collect(Collectors.toList());
            return gson.toJson(deviceDTOs);
        }
        else {
            return gson.toJson(devices);
        }
    }

    @GetMapping(value = "devices/{id}")
    public String getDeviceByID(@PathVariable("id") String id) {
        if (dbService.isNumber(id)) { // check if id is a number
            IOTDevice iot = iotDAO.getDeviceByID(Long.valueOf(id));
            if (iot != null) {
                return gson.toJson(new IOTDeviceDTO(iot));
            }
            return String.format("Device with the id \"%s\" not found!", id);
        }
        else {
            return String.format("The id \"%s\" is not a number!", id);
        }
    }

    @PutMapping(value = "devices/{id}")
    public String updateDeviceById(
            @PathVariable("id") String id,
            @RequestBody IOTDevice editedIoT) {

        // check if id is a number
        if (dbService.isNumber(id)) {
            IOTDevice iot = iotDAO.update(editedIoT, Long.valueOf(id));

            if (iot != null) {
                return gson.toJson(new IOTDeviceDTO(iot));
            }
            else {
                return gson.toJson("Device not found or error in edit");
            }
        }
        else {
            return String.format("The id \"%s\" is not a number!", id);
        }
    }

    @DeleteMapping(value = "devices/{id}")
    public String deleteDevice(@PathVariable("id") String id) {
        if(dbService.isNumber(id)) {
            IOTDevice iot = iotDAO.delete(Long.valueOf(id));
            if (iot != null) {
                return gson.toJson("Success!");
            }
            else {
                return String.format("Device with the id \"%s\" not found!", id);
            }
        }
        else {
            return String.format("The id \"%s\" is not a number!", id);
        }
    }
}
