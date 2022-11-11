package no.hvl.feedapp.daos;

import no.hvl.feedapp.model.Vote;
import no.hvl.feedapp.util.DatabaseService;

import javax.persistence.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class VoteDAO {
    private final AtomicLong id_generator;
    public static final String PERSISTENCE_UNIT_NAME = "feedapp";
    private EntityManagerFactory factory;
    @PersistenceContext
    private EntityManager em;
    private DatabaseService dbService;

    public VoteDAO(DatabaseService dbService) {
        this.id_generator = new AtomicLong();
        this.factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        this.dbService = dbService;
        this.em = factory.createEntityManager();
    }

    public Vote create(Vote vote) {
        /*if(vote.getID() == null) {
            Long ID = this.id_generator.incrementAndGet();
            vote.setID(ID);
        }*/

        dbService.persist(vote);
        return vote;
    }

    public List<Vote> getAllVotes() {
        Query query = em.createQuery("SELECT v FROM Vote v", Vote.class);
        return query.getResultList();
    }

    public Vote getVoteByID(Long id) {
        Vote vote = em.find(Vote.class, Long.valueOf(id));
        return vote;
    }

    public Vote delete(Long id) {
        Vote vote = getVoteByID(id);
        if (vote != null) {
            dbService.remove(vote);
        }
        return vote;
    }

    public Vote update(Vote vote, Long id) {
        if (getVoteByID(id) != null) {
            Vote updateVote = getVoteByID(id);
            updateVote.setVote(vote.getVote());
            dbService.merge(updateVote);
            return updateVote;
        }
        else {
            return null;
        }
    }
}
