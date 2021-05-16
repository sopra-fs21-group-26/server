package ch.uzh.ifi.hase.soprafs21.constant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SET")


public enum Set {
    LACES, CARDS, TILES, FORMS, STICKS;

    @Id
    @GeneratedValue
    private Long id;
}
