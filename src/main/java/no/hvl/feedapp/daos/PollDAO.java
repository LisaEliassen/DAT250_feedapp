package no.hvl.feedapp.daos;

import no.hvl.feedapp.model.IOTDevice;
import no.hvl.feedapp.model.Poll;
import no.hvl.feedapp.model.Vote;
import no.hvl.feedapp.util.DatabaseService;

import javax.persistence.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class PollDAO {
    private final AtomicLong id_generator;
    public static final String PERSISTENCE_UNIT_NAME = "feedapp";
    private EntityManagerFactory factory;
    @PersistenceContext
    private EntityManager em;

    private DatabaseService dbService;

    public PollDAO(DatabaseService dbService) {
        this.id_generator = new AtomicLong();
        this.factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        this.em = factory.createEntityManager();
        this.dbService = dbService;
    }

    public Poll create(Poll poll) {
        dbService.persist(poll);
        return poll;
    }

    public Poll addVote(Vote vote, Long pollID) {
        if (getPollByID(pollID) != null) {
            Poll poll = this.getPollByID(pollID).addVote(vote);
            dbService.merge(poll);
            return poll;
        }
        return null;
    }

    public Poll addDevice(IOTDevice device) {
        if (getPollByID(device.getPoll().getID()) != null) {
            Poll poll = this.getPollByID(device.getPoll().getID()).addDevice(device);
            dbService.merge(poll);
            return poll;
        }
        return null;
    }

    public List<Poll> getAllPolls() {
        Query query = em.createQuery("SELECT p FROM Poll p", Poll.class);
        List<Poll> allPolls = query.getResultList();
        return allPolls;
    }

    public Poll getPollByID(Long id) {
        Poll poll = em.find(Poll.class, Long.valueOf(id));
        return poll;
    }

    public Poll delete(Long id) {
        Poll poll = getPollByID(id);
        if (poll != null) {
            dbService.remove(poll);
        }
        return poll;
    }

    public Poll update(Poll poll, Long id) {
        EntityTransaction tx = em.getTransaction();

        if (getPollByID(id) != null) {
            Poll updatePoll = getPollByID(id);
            updatePoll.setName(poll.getName());
            updatePoll.setDescription(poll.getDescription());
            updatePoll.setCategory(poll.getCategory());
            updatePoll.setResult(poll.getResult());
            updatePoll.setVotes(poll.getAllVotes());
            dbService.merge(updatePoll);
            return updatePoll;
        }
        else {
            return null;
        }
    }
}
