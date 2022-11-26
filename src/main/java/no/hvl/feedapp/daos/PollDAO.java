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

    public Poll addVote(Vote vote) {
        if (getPollByID(vote.getPoll().getID()) != null) {
            Poll poll = this.getPollByID(vote.getPoll().getID()).addVote(vote);
            System.out.println(poll.getYesCount());
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
        List<Poll> allPolls = dbService.getAll(Poll.class, "SELECT p FROM Poll p");
        return allPolls;
    }

    public Poll getPollByID(Long id) {
        Poll poll = (Poll) dbService.find(Poll.class, id);
        return poll;
    }

    public Poll delete(Long id) {
        Poll poll = getPollByID(id);
        System.out.println(poll);
        if (poll != null) {
            dbService.remove(poll);
        }
        return poll;
    }

    public Poll update(Poll poll, Long id) {
        EntityTransaction tx = em.getTransaction();

        if (getPollByID(id) != null) {
            Poll updatePoll = getPollByID(id);
            updatePoll.setTitle(poll.getTitle());
            updatePoll.setOpenPoll(poll.isOpenPoll());
            updatePoll.setPublicPoll(poll.isPublicPoll());
            updatePoll.setCategory(poll.getCategory());
            //updatePoll.setYesCount(poll.getYesCount());
            //updatePoll.setNoCount(poll.getNoCount());
            updatePoll.setVotes(poll.getVotes());
            dbService.merge(updatePoll);
            return updatePoll;
        }
        else {
            return null;
        }
    }
}
