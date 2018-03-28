package com.andrewhoover.sctool.data;

import com.andrewhoover.sctool.data.jackson.Ladder;
import com.andrewhoover.sctool.data.jackson.League;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrew on 3/25/2018.
 */
public class DataPopulaterUrlSaveFiles extends DataPopulaterUrl
{
    public List<Ladder> getLadders(League league)
    {
        List<Ladder> ladders = new ArrayList<>();
        List<Integer> ladderIds = getLadderIds(league);

        for (final Integer ladderId : ladderIds) {

            try {
                String ladder;
                ladder = readUrl(new URL("https://us.api.battle.net/data/sc2/ladder/" + ladderId + "?access_token=" + OAuthToken));
                BufferedWriter writer = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "/jsons/ladder" + ladderId + ".json"));
                writer.write(ladder);
                writer.close();
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                ladders.add(mapper.readValue(ladder, Ladder.class));

            } catch (Exception e) {
                System.out.print(e.getStackTrace());
                System.exit(-1);
            }
        }
        return ladders;
    }

    public List<League> getLeagues()
    {
        List<League> leagues = new ArrayList<>();

        for( int i = 0; i < 6; i++ ) {
            try {
                String league;
                league = readUrl(new URL("https://us.api.battle.net/data/sc2/league/35/201/0/" + i + "?access_token=" + OAuthToken));
                BufferedWriter writer = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "/jsons/league" + i + ".json"));
                writer.write(league);
                writer.close();
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                leagues.add(mapper.readValue(league, League.class));

            } catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
        return leagues;
    }
}
