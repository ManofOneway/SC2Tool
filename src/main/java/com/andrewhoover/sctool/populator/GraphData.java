package com.andrewhoover.sctool.populator;

import com.andrewhoover.sctool.core.Race;
import com.andrewhoover.sctool.data.database.DataPoint;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by andrew on 3/24/2018.
 */
@Component
public class GraphData {

    private int maxMMR = 0;
    private int minMMR = 10000;

    private int maxNumberPlayers = 0;

    private HashMap<String, DataPoint> dataPointMap = new HashMap<>();

    public void addMmr(Integer mmr, Race race)
    {
        String key = mmr.toString() + race.toString();
        if(dataPointMap.containsKey(key))
        {
            int numPlayers = dataPointMap.get(key).incrementNumPlayers();
            if(numPlayers > maxNumberPlayers)
            {
                maxNumberPlayers = numPlayers;
            }
        }
        else
        {
            dataPointMap.put(key, new DataPoint(mmr, 1, race));
        }

        addMMR(mmr);
    }

    private void addMMR(Integer mmr) {
        if(mmr > this.getMaxMMR()) {
            maxMMR = mmr;
        }
        if(mmr < this.getMinMMR()) {
            minMMR =mmr;
        }
    }

    public int getMaxMMR() {
        return maxMMR;
    }

    public int getMinMMR() {
        return minMMR;
    }

    public int getMaxNumberPlayers() {
        return maxNumberPlayers;
    }

    public HashMap<String, DataPoint> getRaceData(Race race)
    {
        List<DataPoint> dataPointList = dataPointMap.values().stream()
                                                             .filter(dataPoint -> dataPoint.getRace() == race)
                                                             .collect(Collectors.toList());
        HashMap<String, DataPoint> dataPointRace = new HashMap<>();
        for(DataPoint dataPoint : dataPointList)
        {
            dataPointRace.put(dataPoint.getMMR().toString() + dataPoint.getRace().toString(), dataPoint);
        }
        return dataPointRace;
    }

    public HashMap<String, DataPoint> getMapData()
    {
        return dataPointMap;
    }

    public void setMapData(List<DataPoint> data) {
        HashMap<String, DataPoint> dataMap = new HashMap<>();
        for (DataPoint point : data) {
            dataMap.put(point.getMMR().toString() + point.getRace().toString(), point);
        }
        dataPointMap = dataMap;
    }

    public void setMinMMR(int minMmr) {
        this.minMMR = minMmr;
    }

    public void setMaxMMR(int maxMMR)
    {
        this.maxMMR = maxMMR;
    }

    public void setMaxNumberPlayers(int maxNumberPlayers)
    {
        this.maxNumberPlayers = maxNumberPlayers;
    }
}
