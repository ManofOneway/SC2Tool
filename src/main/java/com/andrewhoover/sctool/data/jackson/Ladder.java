package com.andrewhoover.sctool.data.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by andrew on 3/24/2018.
 */
public class Ladder {
    @JsonProperty(value = "team")
    private List<Team> team;

    public List<Team> getTeams() {
        return team;
    }
}
