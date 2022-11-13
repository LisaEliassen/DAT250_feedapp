package no.hvl.feedapp.daos;

import no.hvl.feedapp.model.FeedAppUser;
import no.hvl.feedapp.model.IOTDevice;
import no.hvl.feedapp.model.Poll;
import no.hvl.feedapp.model.Vote;
import no.hvl.feedapp.util.DatabaseService;

import javax.persistence.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class IOTDeviceDAO {

    private final AtomicLong id_generator;
    public static final String PERSISTENCE_UNIT_NAME = "feedapp";
    private EntityManagerFactory factory;
    @PersistenceContext
    private EntityManager em;
    private DatabaseService dbService;
    public IOTDeviceDAO(DatabaseService dbService) {
        this.id_generator = new AtomicLong();
        this.factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        this.em = factory.createEntityManager();
        this.dbService = dbService;
    }

    public IOTDevice create(IOTDevice iot) {
        dbService.persist(iot);
        return iot;
    }

    public IOTDevice addVote(Vote vote) {
        if (getDeviceByID(vote.getIot().getID()) != null) {
            IOTDevice device = this.getDeviceByID(vote.getIot().getID()).addVote(vote);
            dbService.merge(device);
            return device;
        }
        return null;
    }
    public List<IOTDevice> getAllDevices() {
        List<IOTDevice> allDevices = dbService.getAll(IOTDevice.class, "SELECT d FROM IOTDevice d");
        return allDevices;
    }

    public IOTDevice getDeviceByID(Long id) {
        IOTDevice device = (IOTDevice) dbService.find(IOTDevice.class, id);
        return device;
    }

    public IOTDevice delete(Long id) {
        IOTDevice iot = getDeviceByID(id);
        if (iot != null) {
           dbService.remove(iot);
        }
        return iot;    }

    public IOTDevice update(IOTDevice device, Long id) {
        if (getDeviceByID(id) != null) {
            IOTDevice updateDevice = getDeviceByID(id);
            updateDevice.setPoll(device.getPoll());
            dbService.merge(updateDevice);

            return updateDevice;
        }
        else {
            return null;
        }
    }
}
