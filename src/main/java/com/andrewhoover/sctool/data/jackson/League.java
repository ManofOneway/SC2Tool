package com.andrewhoover.sctool.data.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

/**
 * Created by andrew on 3/24/2018.
 */
public class League {
    @JsonProperty(value = "key")
    private Map<String, Integer> key;
    @JsonProperty(value = "tier")
    private List<Tier> tier;

    public void setKeys(Map<String, Integer> key) {
        this.key = key;
    }

    public void setTiers(List<Tier> tier) {
        this.tier = tier;
    }

    public Map<String, Integer> getKeys() {
        return key;
    }

    public List<Tier> getTiers() {
        return tier;
    }
}
