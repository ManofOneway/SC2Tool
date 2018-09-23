package com.andrewhoover.sctool.populator;

import com.andrewhoover.sctool.core.Race;
import com.andrewhoover.sctool.data.database.*;
import com.andrewhoover.sctool.data.jackson.*;
import com.andrewhoover.sctool.ui.UiSettings;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrew on 3/25/2018.
 */
@Component
public class DataPopulator
{
    // TODO: This token should not be hard coded. Among other issues, it expires every 30 days.
    // Could be command line arg, or set in a .properties file.
    // Probably should be set in a prop file so I can try out using Spring to read it :D
    private static final String OAuthToken = "5uzpqfxrnxt9ytszbgawz87y";


    private final GraphData graphData;
    private final DataPointRepository dataPointRepository;
    private final GraphMetadataRepository graphMetadataRepository;
    private final UiSettings uiSettings;

    @Autowired
    public DataPopulator(GraphData graphData, DataPointRepository dataPointRepository,
                         GraphMetadataRepository graphMetadataRepository, UiSettings uiSettings)
    {
        this.graphData = graphData;
        this.dataPointRepository = dataPointRepository;
        this.graphMetadataRepository = graphMetadataRepository;
        this.uiSettings = uiSettings;
    }

    private List<Ladder> getLadders(League league)
    {
        List<Ladder> ladders = new ArrayList<>();
        List<Integer> ladderIds = getLadderIds(league);

        for (final Integer ladderId : ladderIds) {

            try {
                String ladder = readUrl(new URL("https://us.api.battle.net/data/sc2/ladder/" + ladderId + "?access_token=" + OAuthToken));
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure (DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                ladders.add(mapper.readValue(ladder, Ladder.class));

            } catch (Exception e) {
                System.out.print(e.getStackTrace());
                System.exit(-1);
            }
        }
        return ladders;
    }

    private List<League> getLeagues()
    {
        List<League> leagues = new ArrayList<>();

        for( int i = 0; i < 6; i++ ) {
            try {
                String league = readUrl(new URL("https://us.api.battle.net/data/sc2/league/36/201/0/" + i + "?access_token=" + OAuthToken));
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

    public void populateData()
    {
        resetGraphData();
        List<League> leagues = getLeagues();
        for( final League league : leagues ) {
            List<Ladder> ladders = getLadders(league);
            for( final Ladder ladder : ladders) {
                List<Team> teams = ladder.getTeams();
                for( final Team team : teams) {

                    Integer mmr = round(team.getRating());

                    if(team.getRace().equals("Protoss")) {
                        graphData.addMmr(mmr, Race.PROTOSS);
                    }
                    if(team.getRace().equals("Terran")) {
                        graphData.addMmr(mmr, Race.TERRAN);
                    }
                    if(team.getRace().equals("Zerg")) {
                        graphData.addMmr(mmr, Race.ZERG);
                    }
                }
            }
        }

        uiSettings.setScaleToDefaults();

        saveToDb();
    }

    private void resetGraphData() {
        graphData.setMaxNumberPlayers(0);
        graphData.setMaxMMR(0);
        graphData.setMinMMR(10000);
        graphData.setMapData(new ArrayList<>());
    }

    private void saveToDb()
    {
        dataPointRepository.deleteAll();
        dataPointRepository.flush();

        graphMetadataRepository.deleteAll();
        graphMetadataRepository.flush();

        List<DataPoint> dataPoints = new ArrayList<>(graphData.getMapData().values());

        GraphMetadata graphMetadataNew = new GraphMetadata(graphData.getMaxMMR(), graphData.getMinMMR(), graphData.getMaxNumberPlayers());
        GraphMetadata graphMetadataOld = graphMetadataRepository.getTopOne();
        int id = (graphMetadataOld != null) ? graphMetadataOld.getId() : 0;
        graphMetadataNew.setId(id);

        dataPointRepository.saveAll(dataPoints);
        dataPointRepository.flush();

        graphMetadataRepository.saveAndFlush(graphMetadataNew);
    }

    private static List<Integer> getLadderIds(League league) {

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

    private String readUrl( URL url ) {
        String response = new String();
        try {
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            InputStream is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            response = br.readLine();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return response;
    }

    public void load() {
        graphData.setMapData(dataPointRepository.findAll());

        GraphMetadata graphMetadata = graphMetadataRepository.getTopOne();
        if(graphMetadata != null)
        {
            graphData.setMinMMR(graphMetadata.getMinMmr());
            graphData.setMaxMMR(graphMetadata.getMaxMmr());
            graphData.setMaxNumberPlayers(graphMetadata.getMaxNumberPlayers());
            uiSettings.setScaleToDefaults();
        }
    }
}
