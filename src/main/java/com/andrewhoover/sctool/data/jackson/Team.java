package com.andrewhoover.sctool.data.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by andrew on 3/24/2018.
 */
public class Team {
    @JsonProperty(value = "rating")
    private Integer rating;
    private String race;
    private String name;
    @JsonProperty(value = "member")
    private void unpackNested(List<Object> members)
    {
        LinkedHashMap<String, Object> memberData = (LinkedHashMap<String, Object>)members.get(0);
        List<Object> playedRaceCount = (List<Object>)memberData.get("played_race_count");
        LinkedHashMap<String, LinkedHashMap<String, String>> playedRace = (LinkedHashMap<String, LinkedHashMap<String, String>>)playedRaceCount.get(0);
        this.race = playedRace.get("race").get("en_US");

        LinkedHashMap<String, String> legacyLink = (LinkedHashMap<String, String>)memberData.get("legacy_link");
        this.name = legacyLink.get("name");
    }

    public Integer getRating() {
        return rating;
    }

    public String getRace() {
        return race;
    }

    public String getName() {
        return name;
    }
}
