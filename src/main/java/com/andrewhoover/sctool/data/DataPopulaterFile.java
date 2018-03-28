package com.andrewhoover.sctool.data;

import com.andrewhoover.sctool.data.jackson.Ladder;
import com.andrewhoover.sctool.data.jackson.League;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrew on 3/24/2018.
 */
public class DataPopulaterFile extends DataPopulater {

    public List<Ladder> getLadders(League league)
    {
        List<Ladder> ladders = new ArrayList<>();
        List<Integer> ladderIds = getLadderIds(league);

        for (final Integer ladderId : ladderIds) {

            try {
                String ladder;
                ladder = readFile(System.getProperty("user.dir") + "/jsons/ladder" + ladderId + ".json");
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
                league = readFile(System.getProperty("user.dir") + "/jsons/league" + i + ".json");
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

    private static String readFile( String path ) {
        String response = new String();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            response = br.readLine();
            br.close();
        } catch (Exception e){
            System.out.print(e.getStackTrace());
            System.out.print("\nNo json files found! Click \"Download\" first to generate files.");
            System.exit(-1);
        }
        return response;
    }
}
