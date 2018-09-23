package com.andrewhoover.sctool.data.database;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class GraphMetadata
{
    public GraphMetadata()
    {
        //no-op
    }

    public GraphMetadata(int maxMmr, int minMmr, int maxNumberPlayers)
    {
        this.maxMmr = maxMmr;
        this.minMmr = minMmr;
        this.maxNumberPlayers = maxNumberPlayers;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int maxMmr;

    private int minMmr;

    private int maxNumberPlayers;

    public int getMaxMmr()
    {
        return maxMmr;
    }

    public int getMinMmr()
    {
        return minMmr;
    }

    public int getMaxNumberPlayers()
    {
        return maxNumberPlayers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
