package com.andrewhoover.sctool.data;

import com.andrewhoover.sctool.core.Race;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by andrew on 3/24/2018.
 */
public class GraphData {

    private int maxMMR = 0;
    private int minMMR = 10000;

    private int maxNumberPlayers = 0;

    private HashMap<Race, RaceData> raceData = new HashMap<>();

    public GraphData()
    {
        raceData.put(Race.PROTOSS, new RaceData());
        raceData.put(Race.ZERG, new RaceData());
        raceData.put(Race.TERRAN, new RaceData());
    }

    public void addMMR(Integer mmr) {
        if(mmr > this.getMaxMMR()) {
            this.setMaxMMR(mmr);
        }
        if(mmr < this.getMinMMR()) {
            this.setMinMMR(mmr);
        }
    }

    public void addMMRByRace(Integer mmr, Race race) {
        if(this.getRaceData().get(race).getKey(mmr) == null) {
            this.getRaceData().get(race).addRace(mmr, 1);
        } else {
            this.getRaceData().get(race).addRace(mmr, this.getRaceData().get(race).getKey(mmr) + 1);
            if(this.getRaceData().get(race).getKey(mmr) > this.getMaxNumberPlayers()) {
                this.setMaxNumberPlayers(this.getRaceData().get(race).getKey(mmr));
            }
        }
    }

    private void setAverage(RaceData raceData) {
        int totalMmr = 0;
        int totalPlayers = 0;
        for(Map.Entry<Integer, Integer> entry : raceData.getRace().entrySet()) {
            totalMmr = totalMmr + (entry.getKey() * entry.getValue());
            totalPlayers += entry.getValue();
        }
        raceData.setRaceAvg(totalMmr / totalPlayers);
    }

    public int getMaxMMR() {
        return maxMMR;
    }

    public void setMaxMMR(int maxMMR) {
        this.maxMMR = maxMMR;
    }

    public int getMinMMR() {
        return minMMR;
    }

    public void setMinMMR(int minMMR) {
        this.minMMR = minMMR;
    }

    public int getMaxNumberPlayers() {
        return maxNumberPlayers;
    }

    public void setMaxNumberPlayers(int maxNumberPlayers) {
        this.maxNumberPlayers = maxNumberPlayers;
    }

    public HashMap<Race,RaceData> getRaceData() {
        return raceData;
    }

    public void setAverages() {
        setAverage(raceData.get(Race.PROTOSS));
        setAverage(raceData.get(Race.ZERG));
        setAverage(raceData.get(Race.TERRAN));
    }
}
