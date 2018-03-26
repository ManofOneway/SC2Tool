package com.andrewhoover.sctool.data;

import java.util.HashMap;

/**
 * Created by andrew on 3/24/2018.
 */
public class RaceData {

    private HashMap<Integer, Integer> race;
    private int raceAvg;

    public RaceData() {
        race = new HashMap<>();
        raceAvg = 0;
    }

    public HashMap<Integer, Integer> getRace() {
        return race;
    }

    public Integer getKey(Integer mmr) {
        return race.get(mmr);
    }

    public int getRaceAvg() {
        return raceAvg;
    }

    public void addRace(Integer mmr, Integer playerNumber) {
        race.put(mmr, playerNumber);
    }

    public void setRaceAvg(int raceAvg) {
            this.raceAvg = raceAvg;
        }
}
