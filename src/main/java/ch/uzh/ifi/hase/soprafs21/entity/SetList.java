/*package ch.uzh.ifi.hase.soprafs21.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

import ch.uzh.ifi.hase.soprafs21.constant.Set;
import ch.uzh.ifi.hase.soprafs21.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Entity
@Table(name = "SETLIST")
public class SetList implements Serializable {
    private static final long serialVersionUID = 1L;

    private final UserRepository userRepository;

    @Id
    @GeneratedValue
    private Long setListId;
    @ManyToMany(targetEntity=User.class, fetch=FetchType.EAGER)
    private List<User> players;
    @Column
    private Hashtable<String, Set> setList = new Hashtable<>();
    @ManyToMany(targetEntity=Set.class, fetch=FetchType.EAGER)
    private List<Set> sets = new LinkedList<Set>() {{
        add(Set.CARDS);
        add(Set.FORMS);
        add(Set.LACES);
        add(Set.TILES);
        add(Set.STICKS);
        }};
    @Column
    private Queue<Set> inactiveSets;

    @Autowired
    public SetList(@Qualifier("userRepository") UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public SetList(@Qualifier("userRepository") UserRepository userRepository, List<User> players){
        this.userRepository = userRepository;
        this.players = players;
        initializeSetList();
    }

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
            Set temp = inactiveSets.remove();
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
}*/
