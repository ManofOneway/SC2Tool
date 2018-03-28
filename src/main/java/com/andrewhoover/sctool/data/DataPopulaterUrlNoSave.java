package com.andrewhoover.sctool.data;

import com.andrewhoover.sctool.data.jackson.Ladder;
import com.andrewhoover.sctool.data.jackson.League;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrew on 3/25/2018.
 */
public class DataPopulaterUrlNoSave extends DataPopulaterUrl
{
    public List<Ladder> getLadders(League league)
    {
        List<Ladder> ladders = new ArrayList<>();
        List<Integer> ladderIds = getLadderIds(league);

        for (final Integer ladderId : ladderIds) {

            try {
                String ladder;
                ladder = readUrl(new URL("https://us.api.battle.net/data/sc2/ladder/" + ladderId + "?access_token=" + OAuthToken));
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
