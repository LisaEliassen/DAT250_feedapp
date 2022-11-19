package no.hvl.feedapp.daos;

import no.hvl.feedapp.dtos.FeedAppUserDTO;
import no.hvl.feedapp.model.FeedAppUser;
import no.hvl.feedapp.model.Poll;
import no.hvl.feedapp.model.Vote;
import no.hvl.feedapp.util.DatabaseService;

import javax.persistence.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class FeedAppUserDAO {

    private final AtomicLong id_generator;
    public static final String PERSISTENCE_UNIT_NAME = "feedapp";
    private EntityManagerFactory factory;
    @PersistenceContext
    private EntityManager em;
    private DatabaseService dbService;

    public FeedAppUserDAO(DatabaseService dbService) {
        this.id_generator = new AtomicLong();
        this.factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        //this.em = factory.createEntityManager();
        this.dbService = dbService;
    }

    public FeedAppUser create(FeedAppUser user) {
        dbService.persist(user);
        return user;
    }

    public FeedAppUser addPoll(Poll poll) {
        FeedAppUser user = getUserByID(poll.getFeedappuser().getID());
        if(user != null && poll != null) {
            System.out.println(String.format("Adding poll to user with ID \"%s\"", poll.getFeedappuser().getID()));
            user.addPoll(poll);
            dbService.merge(user);
            return user;
        }
        return null;
    }

    public FeedAppUser addVote(Vote vote) {
        FeedAppUser user = getUserByID(vote.getUser().getID());
        if(user != null && vote != null) {
            user.addVote(vote);
            dbService.merge(user);
            return user;
        }
        return null;
    }

    public List<FeedAppUser> getAllUsers() {
        List<FeedAppUser> allUsers = dbService.getAll(FeedAppUser.class, "SELECT u FROM FeedAppUser u");
        //Query query = em.createQuery("SELECT u FROM FeedAppUser u", FeedAppUser.class);
        return allUsers;
        //return query.getResultList();
    }

    public FeedAppUser getUserByID(Long id) {
        //FeedAppUser user = em.find(FeedAppUser.class, id);
        FeedAppUser user = (FeedAppUser) dbService.find(FeedAppUser.class, id);
        return user;
    }

    public FeedAppUser getUserByUsername(String username) {
        List<FeedAppUser> allUsers = getAllUsers();
        for (FeedAppUser user : allUsers) {
            if (user.getUsername() != null) {
                if (user.getUsername().strip().equals(username.strip())) {
                    return user;
                }
            }
        }
        return null;
    }

    public FeedAppUser delete(Long id) {
        FeedAppUser user = getUserByID(id);
        if (user != null) {
            dbService.remove(user);
        }
        return user;
    }

    public FeedAppUser update(FeedAppUser user, Long id) {
        if (getUserByID(id) != null) {
            FeedAppUser updateUser = getUserByID(id);
            updateUser.setFirstName(user.getFirstName());
            updateUser.setLastName(user.getLastName());
            updateUser.setUsername(user.getUsername());
            updateUser.setPolls(user.getPolls());
            updateUser.setVotes(user.getVotes());
            updateUser.setPassword(user.getPassword());

            dbService.merge(user);
            return updateUser;
        }
        else {
            return null;
        }
    }
}
