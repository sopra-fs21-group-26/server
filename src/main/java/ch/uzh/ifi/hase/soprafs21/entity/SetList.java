package ch.uzh.ifi.hase.soprafs21.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

import ch.uzh.ifi.hase.soprafs21.constant.Set;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//Max' Implementation
//SetList with all the sets, list with corresponding usernames, constructor sets up setlist (if game.setlist is null)
//
//Set up the setList/usernameList (save it in game), save active set in users, save all inactive sets in user
//rotate sets --> check if set is in inactive set, set this to activce if yes!
//Service: check if game.getSetList = null
//if yes --> use constructor of Setlist (in this also user entity.activeset save, game.setSetlist...)
//if no --> rotate the  setlist in this class (check activesets...), game.setSetlist(Setist), save game
//controller : return getSetListDTO


//jakobs Implementation

@Transactional
@Entity
@Service
@Table(name = "SETLIST")
public class SetList implements Serializable {
    private static final long serialVersionUID = 1L;

    @OneToOne(targetEntity=UserRepository.class, fetch=FetchType.EAGER)
    private final UserRepository userRepository;

    @Autowired
    public SetList(@Qualifier("userRepository") UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public SetList(@Qualifier("userRepository") UserRepository userRepository, List<User> players){
        this.userRepository = userRepository;
        this.players = players;
        initializeSetList();
    }

    @Id
    @GeneratedValue
    private Long setListId;
    @ManyToMany(targetEntity=User.class, fetch=FetchType.EAGER)
    private List<User> players;
    @Column
    private Hashtable<String, Set> setList = new Hashtable<>();
    @ManyToMany(targetEntity=Set.class, fetch=FetchType.EAGER)
    private List<Set> sets = new LinkedList<>() {{
        add(Set.CARDS);
        add(Set.FORMS);
        add(Set.LACES);
        add(Set.TILES);
        add(Set.STICKS);
        }};
    @ManyToMany(targetEntity=Set.class, fetch=FetchType.EAGER)
    private List<Set> inactiveSets;

    public void initializeSetList() {
        int index = 0;
        for (User player : this.players){
            String name = player.getUsername();
            player.setActiveSet(this.sets.get(index));
            userRepository.save(player);
            userRepository.flush();
            Set set = player.getActiveSet();
            this.setList.put(name, set);
            index += 1;
        }
        while (index < 5) {
            this.inactiveSets.add(this.sets.get(index));
            index += 1;
        }
    }

    public void rotateSetList() {
        if (this.players.size() == 5) {
            Set temp = players.get(4).getActiveSet();
            for (User player : this.players){
                Set oldSet = player.getActiveSet();
                String name = player.getUsername();
                player.setActiveSet(temp);
                userRepository.save(player);
                userRepository.flush();
                Set set = player.getActiveSet();
                this.setList.put(name, set);
                temp = oldSet;
            }
        }
        else {
            Set temp = inactiveSets.remove(inactiveSets.size()-1);
            for (User player : this.players){
                Set oldSet = player.getActiveSet();
                String name = player.getUsername();
                player.setActiveSet(temp);
                userRepository.save(player);
                userRepository.flush();
                Set set = player.getActiveSet();
                this.setList.put(name, set);
                temp = oldSet;
            }
            inactiveSets.add(temp);
        }

    }

    public Hashtable<String, Set> getSetList() {
        return this.setList;
    }
}
