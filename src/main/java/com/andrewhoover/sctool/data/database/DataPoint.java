package com.andrewhoover.sctool.data.database;

import com.andrewhoover.sctool.core.Race;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DataPoint {

    public DataPoint()
    {
        //no-op
    }

    public DataPoint(Integer MMR, Integer numberOfPlayers, Race race)
    {
        this.MMR = MMR;
        this.numberOfPlayers = numberOfPlayers;
        this.race = race;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer MMR;

    private  Integer numberOfPlayers;

    private Race race;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMMR() {
        return MMR;
    }

    public Integer incrementNumPlayers()
    {
        numberOfPlayers = numberOfPlayers + 1;
        return numberOfPlayers;
    }

    public Integer getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }
}
