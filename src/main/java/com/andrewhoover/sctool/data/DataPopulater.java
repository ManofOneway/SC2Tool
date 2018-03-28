package com.andrewhoover.sctool.data;

import com.andrewhoover.sctool.core.Race;
import com.andrewhoover.sctool.data.jackson.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrew on 3/24/2018.
 */
public abstract class DataPopulater {

    abstract List<Ladder> getLadders(League league);
    abstract List<League> getLeagues();

    public GraphData populateData (GraphData graphData)
    {
        List<League> leagues = getLeagues();
        for( final League league : leagues ) {
            List<Ladder> ladders = getLadders(league);
            for( final Ladder ladder : ladders) {
                List<Team> teams = ladder.getTeams();
                for( final Team team : teams) {

                    Integer mmr = round(team.getRating());
                    graphData.addMMR(mmr);

                    if(team.getRace().equals("Protoss")) {
                        graphData.addMMRByRace(mmr, Race.PROTOSS);
                    }
                    if(team.getRace().equals("Terran")) {
                        graphData.addMMRByRace(mmr, Race.TERRAN);
                    }
                    if(team.getRace().equals("Zerg")) {
                        graphData.addMMRByRace(mmr, Race.ZERG);
                    }
                }
            }
        }
        graphData.setAverages();
        return graphData;
    }

    public static List<Integer> getLadderIds(League league) {

        List<Integer> ladderIds = new ArrayList<>();

        for (final Tier tier : league.getTiers()) {
            for (final Division division : tier.getDivisions()) {
                ladderIds.add(division.getLadderId());
            }
        }
        return ladderIds;
    }

    private Integer round(Integer rating) {
        return ((rating+5)/10)*10;
    }
}
